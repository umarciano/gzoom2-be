package it.memelabs.smartnebula.lmm.querydsl;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Generated;

import com.google.common.collect.Lists;
import com.mysema.codegen.CodeWriter;
import com.mysema.codegen.model.ClassType;
import com.mysema.codegen.model.Type;
import com.querydsl.codegen.EntityType;
import com.querydsl.codegen.Serializer;
import com.querydsl.codegen.SerializerConfig;
/**
 * @author SIVI
 */
public class CustomSerializer implements Serializer {

    private final List<Type> interfaces = Lists.newArrayList();

    @Override
    public void serialize(EntityType model, SerializerConfig serializerConfig, CodeWriter writer) throws IOException {
        // package
        if (!model.getPackageName().isEmpty()) {
            writer.packageDecl(model.getPackageName());
        }

        // imports
        Set<String> importedClasses = getAnnotationTypes(model);
        for (Type iface : interfaces) {
            importedClasses.add(iface.getFullName());
            for (Type ifaceParam : iface.getParameters()) {
                String ifaceParamFullName = ifaceParam.getFullName();
                if (!ifaceParamFullName.startsWith("java.lang.")) {
                    importedClasses.add(ifaceParamFullName);
                }
            }
        }
        importedClasses.add(Generated.class.getName());
        writer.importClasses(importedClasses.toArray(new String[importedClasses.size()]));

        // header
        for (Annotation annotation : model.getAnnotations()) {
            writer.annotation(annotation);
        }

        writer.line("@Generated(\"", getClass().getName(), "\")");

        Type[] ifaces = interfaces.toArray(new Type[interfaces.size()]);
        writer.beginInterface(model, ifaces);

        writer.end();
    }

    public void addInterface(Class<?> iface) {
        interfaces.add(new ClassType(iface));
    }

    public void addInterface(Type type) {
        interfaces.add(type);
    }

    private Set<String> getAnnotationTypes(EntityType model) {
        Set<String> imports = new HashSet<String>();
        for (Annotation annotation : model.getAnnotations()) {
            imports.add(annotation.annotationType().getName());
        }
        return imports;
    }
}
