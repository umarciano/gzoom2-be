package it.memelabs.smartnebula.generator;

import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.XMLMapperGenerator;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator;

/**
 * @author Andrea Fossi.
 */
public class XmlGenerator extends XMLMapperGenerator {
    protected XmlElement getSqlMapElement() {
        XmlElement answer = super.getSqlMapElement();
        addAliasBaseColumnListElement(answer);
        return answer;
    }

    protected void addAliasBaseColumnListElement(XmlElement parentElement) {
        if (introspectedTable.getRules().generateBaseColumnList()) {
            AbstractXmlElementGenerator elementGenerator = new AliasColumnListElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }

    @Override
    protected void addUpdateByPrimaryKeySelectiveElement(XmlElement parentElement) {
        super.addUpdateByPrimaryKeySelectiveElement(parentElement);
    }

    @Override
    protected void addUpdateByPrimaryKeyWithBLOBsElement(XmlElement parentElement) {
        super.addUpdateByPrimaryKeyWithBLOBsElement(parentElement);
    }

    @Override
    protected void addUpdateByPrimaryKeyWithoutBLOBsElement(XmlElement parentElement) {
        super.addUpdateByPrimaryKeyWithoutBLOBsElement(parentElement);
    }
}
