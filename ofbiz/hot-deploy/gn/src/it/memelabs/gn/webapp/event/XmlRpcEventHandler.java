package it.memelabs.gn.webapp.event;/*
Licensed to the Apache Software Foundation (ASF) under one
or more contributor license agreements.  See the NOTICE file
distributed with this work for additional information
regarding copyright ownership.  The ASF licenses this file
to you under the Apache License, Version 2.0 (the
"License"); you may not use this file except in compliance
with the License.  You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing,
software distributed under the License is distributed on an
"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
KIND, either express or implied.  See the License for the
specific language governing permissions and limitations
under the License.
*/

import it.memelabs.gn.services.EnrichedXmlRpcException;
import it.memelabs.gn.services.OfbizErrors;
import it.memelabs.gn.services.event.EventMessageContainer;
import it.memelabs.gn.services.event.EventMessageUtil;
import it.memelabs.gn.util.GnServiceException;
import it.memelabs.gn.xmlrpc.ObjectArrayParserProxy;
import it.memelabs.gn.xmlrpc.TimestampParser;
import it.memelabs.gn.xmlrpc.TimestampSerializer;
import javolution.util.FastList;
import javolution.util.FastMap;
import org.apache.ws.commons.util.NamespaceContextImpl;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.XmlRpcHandler;
import org.apache.xmlrpc.XmlRpcRequest;
import org.apache.xmlrpc.common.ServerStreamConnection;
import org.apache.xmlrpc.common.TypeFactoryImpl;
import org.apache.xmlrpc.common.XmlRpcHttpRequestConfig;
import org.apache.xmlrpc.common.XmlRpcStreamConfig;
import org.apache.xmlrpc.parser.ObjectArrayParser;
import org.apache.xmlrpc.parser.TypeParser;
import org.apache.xmlrpc.serializer.TypeSerializer;
import org.apache.xmlrpc.server.AbstractReflectiveHandlerMapping;
import org.apache.xmlrpc.server.XmlRpcHttpServer;
import org.apache.xmlrpc.server.XmlRpcHttpServerConfig;
import org.apache.xmlrpc.server.XmlRpcNoSuchHandlerException;
import org.apache.xmlrpc.util.HttpUtil;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.DelegatorFactory;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.transaction.GenericTransactionException;
import org.ofbiz.entity.transaction.TransactionUtil;
import org.ofbiz.service.*;
import org.ofbiz.webapp.control.ConfigXMLReader.Event;
import org.ofbiz.webapp.control.ConfigXMLReader.RequestMap;
import org.ofbiz.webapp.event.EventHandler;
import org.ofbiz.webapp.event.EventHandlerException;
import org.xml.sax.SAXException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Status;
import javax.transaction.Transaction;
import java.io.*;
import java.sql.Timestamp;
import java.util.*;

import static it.memelabs.gn.util.GnServiceUtil.getErrorCode;
import static it.memelabs.gn.util.GnServiceUtil.getErrorData;
import static org.ofbiz.base.util.UtilGenerics.checkMap;

/**
 * XmlRpcEventHandler
 */
public class XmlRpcEventHandler extends XmlRpcHttpServer implements EventHandler {

    public static final String module = XmlRpcEventHandler.class.getName();
    protected Delegator delegator;
    // protected Delegator oldDelegator;
    protected LocalDispatcher dispatcher;

    protected Boolean enabledForExtensions = null;
    protected Boolean enabledForExceptions = null;

    public void init(ServletContext context) throws EventHandlerException {
        String delegatorName = context.getInitParameter("entityDelegatorName");
        this.delegator = DelegatorFactory.getDelegator(delegatorName);
        this.dispatcher = GenericDispatcher.getLocalDispatcher(delegator.getDelegatorName(), delegator);
        this.setHandlerMapping(new ServiceRpcHandler());

        String extensionsEnabledString = context.getInitParameter("xmlrpc.enabledForExtensions");
        if (UtilValidate.isNotEmpty(extensionsEnabledString)) {
            enabledForExtensions = Boolean.valueOf(extensionsEnabledString);
        }
        String exceptionsEnabledString = context.getInitParameter("xmlrpc.enabledForExceptions");
        if (UtilValidate.isNotEmpty(exceptionsEnabledString)) {
            enabledForExceptions = Boolean.valueOf(exceptionsEnabledString);
        }

        this.setTypeFactory(getCustomTypeFactory(this));

        EventMessageUtil.init(delegator);

    }

    /**
     * @param pController
     * @return
     */
    protected TypeFactoryImpl getCustomTypeFactory(XmlRpcEventHandler pController) {

        return new TypeFactoryImpl(pController) {
            final TimestampSerializer timestampSerializer = new TimestampSerializer();

            @Override
            public TypeParser getParser(XmlRpcStreamConfig pConfig, NamespaceContextImpl pContext, String pURI, String pLocalName) {
                if (TimestampSerializer.TIMESTAMP_TAG.equals(pLocalName)) {
                    return new TimestampParser();
                }
                TypeParser parser = super.getParser(pConfig, pContext, pURI, pLocalName);
                if (parser instanceof ObjectArrayParser) {
                    ObjectArrayParserProxy objectArrayParserProxy = new ObjectArrayParserProxy(parser);
                    parser = objectArrayParserProxy;
                }
                return parser;
            }

            @Override
            public TypeSerializer getSerializer(XmlRpcStreamConfig pConfig, Object pObject) throws SAXException {
                if (pObject instanceof Timestamp || pObject instanceof Date) {
                    return timestampSerializer;
                } else
                    return super.getSerializer(pConfig, pObject);

            }
        };
    }

    /**
     * @see org.ofbiz.webapp.event.EventHandler#invoke(org.ofbiz.webapp.control.ConfigXMLReader.Event, org.ofbiz.webapp.control.ConfigXMLReader.RequestMap, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    public String invoke(Event event, RequestMap requestMap, HttpServletRequest request, HttpServletResponse response) throws EventHandlerException {
        String report = request.getParameter("echo");
        if (report != null) {
            StringBuilder buf = new StringBuilder();
            try {
                // read the inputstream buffer
                String line;
                BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    buf.append(line).append("\n");
                }
            } catch (Exception e) {
                throw new EventHandlerException(e.getMessage(), e);
            }
            Debug.logInfo("Echo: " + buf.toString(), module);

            // echo back the request
            try {
                response.setContentType("text/xml");
                Writer out = response.getWriter();
                out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
                out.write("<methodResponse>");
                out.write("<params><param>");
                out.write("<value><string><![CDATA[");
                out.write(buf.toString());
                out.write("]]></string></value>");
                out.write("</param></params>");
                out.write("</methodResponse>");
                out.flush();
            } catch (Exception e) {
                throw new EventHandlerException(e.getMessage(), e);
            }
        } else {
            try {
                this.execute(this.getXmlRpcConfig(request), new HttpStreamConnection(request, response));
            } catch (XmlRpcException e) {
                Debug.logError(e, module);
                throw new EventHandlerException(e.getMessage(), e);
            }
        }

        return null;
    }

    @Override
    protected void setResponseHeader(ServerStreamConnection con, String header, String value) {
        ((HttpStreamConnection) con).getResponse().setHeader(header, value);
    }

    protected XmlRpcHttpRequestConfig getXmlRpcConfig(HttpServletRequest req) {
        CustomXmlRpcHttpRequestConfigImpl result = new CustomXmlRpcHttpRequestConfigImpl();
        XmlRpcHttpServerConfig serverConfig = (XmlRpcHttpServerConfig) getConfig();

        result.setBasicEncoding(serverConfig.getBasicEncoding());
        result.setContentLengthOptional(serverConfig.isContentLengthOptional());
        result.setEnabledForExtensions(serverConfig.isEnabledForExtensions());
        result.setGzipCompressing(HttpUtil.isUsingGzipEncoding(req.getHeader("Content-Encoding")));
        result.setGzipRequesting(HttpUtil.isUsingGzipEncoding(req.getHeaders("Accept-Encoding")));
        result.setEncoding(req.getCharacterEncoding());
        //result.setEnabledForExceptions(serverConfig.isEnabledForExceptions());

        result.setRequest(req);

        HttpUtil.parseAuthorization(result, req.getHeader("Authorization"));

        // context overrides
        if (enabledForExtensions != null) {
            result.setEnabledForExtensions(enabledForExtensions);
        }
        if (enabledForExceptions != null) {
            result.setEnabledForExceptions(enabledForExceptions);
        }

        return result;
    }

    class OfbizRpcAuthHandler implements AbstractReflectiveHandlerMapping.AuthenticationHandler {

        public boolean isAuthorized(XmlRpcRequest xmlRpcReq) throws XmlRpcException {
            XmlRpcHttpRequestConfig config = (XmlRpcHttpRequestConfig) xmlRpcReq.getConfig();

            ModelService model;
            try {
                model = dispatcher.getDispatchContext().getModelService(xmlRpcReq.getMethodName());
            } catch (GenericServiceException e) {
                throw new XmlRpcException(e.getMessage(), e);
            }

            if (model != null && model.auth) {
                String username = config.getBasicUserName();
                String password = config.getBasicPassword();

                // check the account
                Map<String, Object> context = FastMap.newInstance();
                context.put("login.username", username);
                context.put("login.password", password);

                Map<String, Object> resp;
                try {
                    resp = dispatcher.runSync("userLogin", context);
                } catch (GenericServiceException e) {
                    throw new XmlRpcException(e.getMessage(), e);
                }

                if (ServiceUtil.isError(resp)) {
                    return false;
                }
            }

            return true;
        }
    }

    class ServiceRpcHandler extends AbstractReflectiveHandlerMapping implements XmlRpcHandler {

        public ServiceRpcHandler() {
            this.setAuthenticationHandler(new OfbizRpcAuthHandler());
        }

        @Override
        public XmlRpcHandler getHandler(String method) throws XmlRpcNoSuchHandlerException, XmlRpcException {
            ModelService model = null;
            try {
                model = dispatcher.getDispatchContext().getModelService(method);
            } catch (GenericServiceException e) {
                Debug.logWarning(e, module);
            }
            if (model == null) {
                throw new XmlRpcNoSuchHandlerException("No such service [" + method + "]");
            }
            return this;
        }

        public Object execute(XmlRpcRequest xmlRpcReq) throws XmlRpcException {
            DispatchContext dctx = dispatcher.getDispatchContext();
            String serviceName = xmlRpcReq.getMethodName();
            ModelService model = null;
            try {
                model = dctx.getModelService(serviceName);
            } catch (GenericServiceException e) {
                throw new XmlRpcException(e.getMessage(), e);
            }

            // check remote invocation security
            if (model == null || !model.export) {
                throw new XmlRpcException("Unknown method");
            }

            // prepare the context -- single parameter type struct (map)
            Map<String, Object> context = this.getContext(xmlRpcReq, serviceName);

            //manage contextId
            String contextId = ((CustomXmlRpcHttpRequestConfigImpl) xmlRpcReq.getConfig()).getRequest().getHeader("contextId");
            if (contextId != null && contextId.length() > 0 && context.get("userLogin") != null) {
                GenericValue userLogin = (GenericValue) context.get("userLogin");
                userLogin.set("activeContextId", contextId);
            }

            // add in auth parameters
            CustomXmlRpcHttpRequestConfigImpl config = (CustomXmlRpcHttpRequestConfigImpl) xmlRpcReq.getConfig();
            String username = config.getBasicUserName();
            String password = config.getBasicPassword();

            if (UtilValidate.isNotEmpty(username)) {
                context.put("login.username", username);
                context.put("login.password", password);
            }

            if ("gnLogin".equals(serviceName) && UtilValidate.isNotEmpty(context.get("login.username")) &&
                    UtilValidate.isNotEmpty(context.get("login.password"))) {
                context.put("login_password", context.get("login.password"));
            }

            // add the locale to the context
            context.put("locale", Locale.getDefault());

            // invoke the service
            Map<String, Object> resp;
            try {
                CommunicationEventSessionHelper.clean();
                AuditEventSessionHelper.init(dispatcher, context);
                EventMessageContainer.clean();
                ErrorEvent.clean();

                Transaction parentTx = manageReadOnlyCallTransaction(serviceName, context);
                resp = dispatcher.runSync(serviceName, context);
                if (!isServiceReadOnly(serviceName, context)) {
                    // manage communicationEvents created during service running
                    manageCommunicationEvents(serviceName, resp, context);
                    manageAuditEvents(serviceName, resp, context);
                    manageEvents(serviceName, resp, context);
                }
                manageReadOnlyEndTransaction(serviceName, context, parentTx);

            } catch (ServiceValidationException e) {
                throw new XmlRpcException(OfbizErrors.INVALID_PARAMETERS.ordinal(), e.getMessage(), e);
            } catch (GnServiceException e) {
                throw new XmlRpcException(e.getCode(), e.getMessage(), e);
            } catch (ServiceAuthException e) {
                if (ErrorEvent.contains(OfbizErrors.ACCESS_DENIED)) {
                    throw new XmlRpcException(OfbizErrors.ACCESS_DENIED.ordinal(), e.getMessage(), e);
                } else {
                    throw new XmlRpcException(OfbizErrors.NOT_AUTHENTICATED.ordinal(), e.getMessage(), e);
                }
            } catch (GenericServiceException e) {
                throw new XmlRpcException(e.getMessage(), e);
            } finally {
                CommunicationEventSessionHelper.clean();
                ErrorEvent.clean();
                EventMessageContainer.clean();
                AuditEventSessionHelper.clean();
            }

            if (ServiceUtil.isError(resp)) {
                Debug.logError(ServiceUtil.getErrorMessage(resp), module);
                throw new EnrichedXmlRpcException(getErrorCode(resp).ordinal(), ServiceUtil.getErrorMessage(resp), getErrorData(resp));
            }

            // return only defined parameters
            return model.makeValid(resp, ModelService.OUT_PARAM, false, null);
        }

        /**
         * @param serviceName
         * @param context
         * @return
         * @throws GenericServiceException
         */
        private boolean isServiceReadOnly(String serviceName, Map<String, Object> context) throws GenericServiceException {
            Iterator<ModelServiceIface> it = dispatcher.getDispatchContext().getModelService(serviceName).implServices.iterator();
            boolean readOnly = false;
            while (it.hasNext() && !readOnly) {
                if (it.next().getService().equals("gnReadOnlyInterface"))
                    readOnly = "Y".equals(context.get("readOnly"));
            }
            return readOnly;
        }

        /**
         * @param serviceName
         * @param context
         * @return
         * @throws GenericServiceException
         */
        private Transaction manageReadOnlyCallTransaction(String serviceName, Map<String, Object> context) throws GenericServiceException {
            try {
                boolean readOnly = isServiceReadOnly(serviceName, context);
                Transaction parentTx = null;
                if (readOnly) {
                    Debug.log(String.format("service[%s] is marked readOnly", serviceName), module);
                    if (Status.STATUS_NO_TRANSACTION != TransactionUtil.getStatus()) {
                        parentTx = TransactionUtil.suspend();
                    }
                    TransactionUtil.begin();
                }
                return parentTx;
            } catch (GenericTransactionException e) {
                throw new GnServiceException(OfbizErrors.SERVICE_EXCEPTION, e.getMessage());
            }
        }

        /**
         * @param serviceName
         * @param context
         * @param parentTx    @return
         * @throws GenericServiceException
         */
        private void manageReadOnlyEndTransaction(String serviceName, Map<String, Object> context, Transaction parentTx) throws GenericServiceException {
            try {
                boolean readOnly = isServiceReadOnly(serviceName, context);
                if (readOnly) {
                    TransactionUtil.rollback(new Throwable("Transaction rolled back because service is readOnly"));
                    if (parentTx != null) TransactionUtil.resume(parentTx);
                }
            } catch (GenericTransactionException e) {
                throw new GnServiceException(OfbizErrors.SERVICE_EXCEPTION, e.getMessage());
            }
        }

        /**
         * @param serviceName
         * @param resp
         * @param context
         * @throws GenericServiceException
         */
        protected void manageCommunicationEvents(String serviceName, Map<String, Object> resp, Map<String, Object> context) throws GenericServiceException {
            GenericValue userLogin = (GenericValue) context.get("userLogin");
            boolean canSend = dispatcher.getDispatchContext().getModelService(serviceName).getAllParamNames().contains("communicationEventToSendList");
            if (canSend) {
                Debug.log("Service: " + serviceName + " implement gnSendCommunicationEventInterface");
                //get ids of generated CommunicationEvents
                List<String> ids = CommunicationEventSessionHelper.getCommunicationEventIds();
                if (ids != null && ids.size() > 0 && userLogin != null) {
                    //load communicationEvents
                    Map<String, Object> result = dispatcher.runSync("gnFindCommunicationEvent", UtilMisc.toMap("userLogin", userLogin, "communicationEventIds", ids));
                    @SuppressWarnings("unchecked")
                    List<Map<String, Object>> communicationEventList = (List<Map<String, Object>>) result.get("communicationEventList");
                    if (communicationEventList == null)
                        communicationEventList = FastList.newInstance();
                    resp.put("communicationEventToSendList", communicationEventList);
                    resp.put("communicationEventToSendListSize", communicationEventList.size());
                    Debug.log(CommunicationEventSessionHelper.getString());
                } else {
                    resp.put("communicationEventToSendList", FastList.newInstance());
                    resp.put("communicationEventToSendListSize", 0);
                }
            }
            /* } catch (GenericServiceException e) {
                 Debug.logError(e, module);
             }*/
        }

        /**
         * Service should be implement interface gnAuditEventInterface to return audit data
         *
         * @param serviceName
         * @param resp
         * @param context
         * @throws GenericServiceException
         */
        protected void manageAuditEvents(String serviceName, Map<String, Object> resp, Map<String, Object> context) throws GenericServiceException {
            resp.put("auditEvents", AuditEventSessionHelper.getAuditEventMap());
        }

        protected void manageEvents(String serviceName, Map<String, Object> resp, Map<String, Object> context) throws GenericServiceException {

            if (!EventMessageContainer.getEvents().isEmpty() && dispatcher.getDispatchContext().getModelService(serviceName).getParam("eventMessages") == null) {
                ModelParam mp = new ModelParam();
                //<attribute name="eventMessage" type="java.util.Map" mode="IN" optional="false"/>
                mp.name = "eventMessages";
                mp.type = java.util.List.class.getName();
                mp.mode = "OUT";
                mp.optional = true;
                dispatcher.getDispatchContext().getModelService(serviceName).addParam(mp);
            }
            resp.put("eventMessages", EventMessageContainer.getEventsAsMap());
        }

        protected Map<String, Object> getContext(XmlRpcRequest xmlRpcReq, String serviceName) throws XmlRpcException {
            ModelService model;
            try {
                model = dispatcher.getDispatchContext().getModelService(serviceName);
            } catch (GenericServiceException e) {
                throw new XmlRpcException(e.getMessage(), e);
            }

            // context placeholder
            Map<String, Object> context = FastMap.newInstance();

            if (model != null) {
                int parameterCount = xmlRpcReq.getParameterCount();

                // more than one parameter; use list notation based on service def order
                if (parameterCount > 1) {
                    int x = 0;
                    for (String name : model.getParameterNames("IN", true, true)) {
                        context.put(name, xmlRpcReq.getParameter(x));
                        x++;

                        if (x == parameterCount) {
                            break;
                        }
                    }

                    // only one parameter; if its a map use it as the context; otherwise make sure the service takes one param
                } else if (parameterCount == 1) {
                    Object param = xmlRpcReq.getParameter(0);
                    if (param instanceof Map<?, ?>) {
                        context = checkMap(param, String.class, Object.class);
                    } else {
                        if (model.getDefinedInCount() == 1) {
                            String paramName = model.getInParamNames().iterator().next();
                            context.put(paramName, xmlRpcReq.getParameter(0));
                        } else {
                            throw new XmlRpcException("More than one parameter defined on service; cannot call via RPC with parameter list");
                        }
                    }
                }

                CustomXmlRpcHttpRequestConfigImpl config = (CustomXmlRpcHttpRequestConfigImpl) xmlRpcReq.getConfig();
                HttpServletRequest req = config.getRequest();
                context.put("request", req);

                if (req.getAttribute("userLogin") != null)
                    context.put("userLogin", req.getAttribute("userLogin"));

                if (req.getAttribute("userLogin") == null)
                    context.put("userLogin", req.getSession().getAttribute("userLogin"));

                // do map value conversions
                context = model.makeValid(context, ModelService.IN_PARAM);
            }

            return context;
        }
    }

    class HttpStreamConnection implements ServerStreamConnection {

        protected HttpServletRequest request;
        protected HttpServletResponse response;

        protected HttpStreamConnection(HttpServletRequest req, HttpServletResponse res) {
            this.request = req;
            this.response = res;
        }

        public HttpServletRequest getRequest() {
            return request;
        }

        public HttpServletResponse getResponse() {
            return response;
        }

        public InputStream newInputStream() throws IOException {
            return request.getInputStream();
        }

        public OutputStream newOutputStream() throws IOException {
            response.setContentType("text/xml");
            return response.getOutputStream();
        }

        public void close() throws IOException {
            response.getOutputStream().close();
        }
    }
}
