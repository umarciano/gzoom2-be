package it.mapsgroup.gzoom.querydsl.generator;

import com.mysema.codegen.CodeWriter;
import com.mysema.codegen.model.ClassType;
import com.mysema.codegen.model.SimpleType;
import com.querydsl.codegen.*;
import it.mapsgroup.gzoom.querydsl.AbstractIdentity;

import java.io.IOException;

/**
 * @author Andrea Fossi.
 */
public class CustomSerializer2 implements Serializer {

    @Override
    public void serialize(EntityType model, SerializerConfig serializerConfig, CodeWriter writer) throws IOException {
        model.getPropertyNames();
        BeanSerializer beanSerializer = new BeanSerializer();
        if (model.getPropertyNames().contains("createdStamp")
                && model.getPropertyNames().contains("createdTxStamp")
                && model.getPropertyNames().contains("lastUpdatedStamp")
                && model.getPropertyNames().contains("lastUpdatedTxStamp")
                ) {
            beanSerializer.setPrintSupertype(true);
            //model.addSupertype(new Supertype(new ClassType(AbstractIdentity.class)));
            beanSerializer.addInterface(AbstractIdentity.class);
        } else {
            beanSerializer.setPrintSupertype(false);
        }
        beanSerializer.serialize(model, serializerConfig, writer);
    }
}
