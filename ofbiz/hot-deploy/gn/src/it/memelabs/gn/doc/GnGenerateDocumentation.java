package it.memelabs.gn.doc;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.ofbiz.base.util.Debug;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ModelService;
import org.ofbiz.service.ServiceUtil;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * 12/02/13
 *
 * @author Andrea Fossi
 */
public class GnGenerateDocumentation {


    public static final String module = GnGenerateDocumentation.class.getName();
    public static final String TEMPLATE_PATH = "hot-deploy/gn/src/it/memelabs/gn/doc/";
    public static final String BUILD_PATH = "hot-deploy/gn/build/doc/services";

    public static Map<String, Object> gnGenerateDocumentation(DispatchContext ctx, Map<String, ? extends Object> context) throws Exception {
        new File("/" + BUILD_PATH).mkdirs();
        LocalDispatcher dispatcher = ctx.getDispatcher();
        Set<String> serviceNames = dispatcher.getDispatchContext().getAllServiceNames();
        Set<String> processedService = new TreeSet<String>();
        for (String serviceName : serviceNames) {
            //filter
            if (serviceName.toLowerCase().startsWith("gn")) {
                ModelService modelService = dispatcher.getDispatchContext().getModelService(serviceName);
                generateServiceDocumentation(modelService, serviceName);
                processedService.add(serviceName);
            }
        }
        generateIndex(processedService);
        copyFiles();
        return ServiceUtil.returnSuccess();
    }

    public static void generateServiceDocumentation(ModelService modelService, String serviceName) throws Exception {
         /*  first, get and initialize an engine  */
        VelocityEngine ve = new VelocityEngine();
        ve.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, TEMPLATE_PATH);
        ve.setProperty(Velocity.FILE_RESOURCE_LOADER_CACHE, false);
        ve.init();
         /*  next, get the Template  */
        Template t = ve.getTemplate("serviceTemplateHtml.vm");
        /*  create a context and add data */
        VelocityContext context = new VelocityContext();

        Map<String, String> params = new HashMap<String, String>();
        for (String pName : modelService.getAllParamNames()) {
            String description = modelService.getParam(pName).description;
            if (description == null) description = "";
            params.put(pName, description);
        }

        context.put("serviceName", serviceName);
        context.put("modelService", modelService);
        context.put("paramDescriptions", params);
        context.put("export", modelService.export);
        /* now render the template into a StringWriter */
        StringWriter writer = new StringWriter();
        t.merge(context, writer);

        /* show the World */
        // System.out.println(writer.toString());
        writeFile(serviceName, writer.toString());
    }

    private static void writeFile(String serviceName, String body) throws IOException {
        String target = new File(".").getAbsolutePath() + "/" + BUILD_PATH + "/" + serviceName + ".html";
        Debug.log("writing file: " + target, module);
        OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(target), "UTF-8");
        out.write(body);
        out.close();
    }

    private static void copyFiles() throws Exception {
        String src = new File(".").getAbsolutePath() + "/" + TEMPLATE_PATH + "/";
        String dest = new File(".").getAbsolutePath() + "/" + BUILD_PATH + "/";
        FileUtils.copyFile(src + "index.html", dest + "index.html");
        FileUtils.copyFile(src + "blank.html", dest + "blank.html");
    }

    private static void generateIndex(Set<String> services) throws Exception {
        VelocityEngine ve = new VelocityEngine();
        ve.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, TEMPLATE_PATH);
        ve.setProperty(Velocity.FILE_RESOURCE_LOADER_CACHE, false);
        ve.init();
         /*  next, get the Template  */
        Template t = ve.getTemplate("serviceIndexTemplateHtml.vm");
        /*  create a context and add data */
        VelocityContext context = new VelocityContext();

        context.put("services", services);
        /* now render the template into a StringWriter */
        StringWriter writer = new StringWriter();
        t.merge(context, writer);

        /* show the World */
        //System.out.println(writer.toString());
        writeFile("serviceIndex", writer.toString());
    }

}
