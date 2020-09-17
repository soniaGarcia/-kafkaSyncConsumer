package com.prueba.apache.kafka.helper;

import com.prueba.apache.kafka.mensajeDTO.ResultMsj;
import com.prueba.apache.kafka.mensajeDTO.VehiculoMsj;
import com.prueba.apache.kafka.model.Inventario;
import com.prueba.apache.kafka.model.QInventario;
import com.prueba.apache.kafka.repository.InventarioRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Component
public class ConsumidorKafka {

    @Autowired
    InventarioRepository inventarioRepository;

    @KafkaListener(topics = "vehiculo", groupId = "vehiculo-result-group")
    @SendTo
    public ResultMsj handle(VehiculoMsj message) {
        System.out.println("Actualizando inventario para "+message.getCodigo()+"...");
        BooleanExpression bol = QInventario.inventario.codigo.eq(message.codigo);
        Optional<Inventario> inv = inventarioRepository.findOne(bol);
        int ban =0;
        if (inv.isPresent()) {
            Inventario obtenido = inv.get();
            if (obtenido.id != null) {
                obtenido.cantidad = obtenido.cantidad + 1;
                inventarioRepository.save(obtenido);
                ban=1;
            }
        } else {
            Inventario persisInv = new Inventario();
            persisInv.cantidad = 1;
            persisInv.codigo = message.codigo;
            persisInv.precio = message.precio;
            inventarioRepository.save(persisInv);
        }

        ResultMsj result = new ResultMsj();
        result.setCodigo("00");
        result.setDescripcion("Exito. Registro "+(ban ==0 ? "Ingresado":"Actualizado")+ "Codigo: "+message.getCodigo());
        return result;
    }

}
