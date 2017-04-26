package it.memelabs.smartnebula.lmm.querydsl;

import com.mysema.codegen.CodeWriter;
import com.querydsl.codegen.BeanSerializer;
import com.querydsl.codegen.EntityType;
import com.querydsl.codegen.Serializer;
import com.querydsl.codegen.SerializerConfig;
import it.memelabs.smartnebula.lmm.persistence.main.dto.AbstractIdentity;

import java.io.IOException;

/**
 * @author Andrea Fossi.
 */
public class CustomSerializer2 implements Serializer {

    @Override
    public void serialize(EntityType model, SerializerConfig serializerConfig, CodeWriter writer) throws IOException {
        model.getPropertyNames();
        BeanSerializer beanSerializer = new BeanSerializer();
        if (model.getPropertyNames().contains("description")
                && model.getPropertyNames().contains("modifiedStamp")
                && model.getPropertyNames().contains("createdStamp")) {
            beanSerializer.setPrintSupertype(true);
            //model.addSupertype(new Supertype(new ClassType(AbstractIdentity.class)));
            beanSerializer.addInterface(AbstractIdentity.class);
        } else {
            beanSerializer.setPrintSupertype(false);
        }
        beanSerializer.serialize(model, serializerConfig, writer);
    }
}
