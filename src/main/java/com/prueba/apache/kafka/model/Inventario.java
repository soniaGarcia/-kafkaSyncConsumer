package com.prueba.apache.kafka.model;

import com.google.gson.GsonBuilder;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author sonia.cabrera
 */
@Document(collection = "inventario")
public class Inventario {

    @Id
    public ObjectId id;

    public String codigo;
    public Integer cantidad;
    public Integer precio;
    
    

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson(this);
    }

}
