package it.memelabs.smartnebula.generator;

import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.ibatis2.Ibatis2FormattingUtilities;
import org.mybatis.generator.config.Context;

import java.util.List;

/**
 * @author Andrea Fossi.
 */
public class Plugin extends PluginAdapter {

    @Override
    public boolean validate(List<String> list) {
        return true;
    }

    @Override
    public void setContext(Context context) {
        super.setContext(context);
    }


    @Override
    public boolean sqlMapBaseColumnListElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        // element.getAttributes().remove(0);
        // element.addAttribute(new Attribute("id","AAA"));
        return super.sqlMapBaseColumnListElementGenerated(element, introspectedTable);
    }

    @Override
    public boolean sqlMapGenerated(GeneratedXmlFile sqlMap, IntrospectedTable introspectedTable) {
        return super.sqlMapGenerated(sqlMap, introspectedTable);
    }


    @Override
    public boolean modelExampleClassGenerated(TopLevelClass topLevelClass,
                                              IntrospectedTable introspectedTable) {

        InnerClass criteria = null;
        // first, find the Criteria inner class
        for (InnerClass innerClass : topLevelClass.getInnerClasses()) {
            if ("GeneratedCriteria".equals(innerClass.getType().getShortName())) { //$NON-NLS-1$
                criteria = innerClass;
                break;
            }
        }

        if (criteria == null) {
            // can't find the inner class for some reason, bail out.
            return true;
        }

        for (IntrospectedColumn introspectedColumn : introspectedTable
                .getNonBLOBColumns()) {
            if (!introspectedColumn.isJdbcCharacterColumn()
                    || !introspectedColumn.isStringColumn()) {
                continue;
            }

            Method method = new Method();
            method.setVisibility(JavaVisibility.PUBLIC);
            method.addParameter(new Parameter(introspectedColumn
                    .getFullyQualifiedJavaType(), "value")); //$NON-NLS-1$

            StringBuilder sb = new StringBuilder();
            sb.append(introspectedColumn.getJavaProperty());
            sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
            sb.insert(0, "and"); //$NON-NLS-1$
            sb.append("LikeInsensitive"); //$NON-NLS-1$
            method.setName(sb.toString());
            method.setReturnType(FullyQualifiedJavaType.getCriteriaInstance());

            sb.setLength(0);
            sb.append("addCriterion(\"lower("); //$NON-NLS-1$
            sb.append(Ibatis2FormattingUtilities
                    .getAliasedActualColumnName(introspectedColumn));
            sb.append(") like\", (value != null) ? value.toLowerCase() : value , \""); //$NON-NLS-1$
            sb.append(introspectedColumn.getJavaProperty());
            sb.append("\");"); //$NON-NLS-1$
            method.addBodyLine(sb.toString());
            method.addBodyLine("return (Criteria) this;"); //$NON-NLS-1$

            criteria.addMethod(method);
        }

        for (IntrospectedColumn introspectedColumn : introspectedTable
                .getNonBLOBColumns()) {
            if (!introspectedColumn.isJdbcCharacterColumn()
                    || !introspectedColumn.isStringColumn()) {
                continue;
            }

            Method method = new Method();
            method.setVisibility(JavaVisibility.PUBLIC);
            method.addParameter(new Parameter(introspectedColumn
                    .getFullyQualifiedJavaType(), "value")); //$NON-NLS-1$

            StringBuilder sb = new StringBuilder();
            sb.append(introspectedColumn.getJavaProperty());
            sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
            sb.insert(0, "and"); //$NON-NLS-1$
            sb.append("EqualToInsensitive"); //$NON-NLS-1$
            method.setName(sb.toString());
            method.setReturnType(FullyQualifiedJavaType.getCriteriaInstance());

            sb.setLength(0);
            sb.append("addCriterion(\"lower("); //$NON-NLS-1$
            sb.append(Ibatis2FormattingUtilities
                    .getAliasedActualColumnName(introspectedColumn));
            sb.append(") =\", (value != null) ? value.toLowerCase() : value , \""); //$NON-NLS-1$
            sb.append(introspectedColumn.getJavaProperty());
            sb.append("\");"); //$NON-NLS-1$
            method.addBodyLine(sb.toString());
            method.addBodyLine("return (Criteria) this;"); //$NON-NLS-1$

            criteria.addMethod(method);
        }

        return true;
    }

    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        boolean identity = false;
        boolean identifiable = false;
        for (Method m : topLevelClass.getMethods()) {
            if (m.getName().equalsIgnoreCase("getId") && m.getReturnType().getShortName().equals("Long")) {
                FullyQualifiedJavaType zzz = new FullyQualifiedJavaType("it.memelabs.smartnebula.lmm.persistence.main.dto.AbstractIdentity");
                topLevelClass.addSuperInterface(zzz);
                identity = true;
            }
            if (identity && m.getName().equalsIgnoreCase("getDescription") && m.getReturnType().getShortName().equals("String")) {
                FullyQualifiedJavaType zzz = new FullyQualifiedJavaType("it.memelabs.smartnebula.lmm.persistence.main.dto.AbstractIdentifiable");
                topLevelClass.addSuperInterface(zzz);
                identifiable = true;
            }
        }
        return identifiable | identity | super.modelBaseRecordClassGenerated(topLevelClass, introspectedTable);
    }
}
