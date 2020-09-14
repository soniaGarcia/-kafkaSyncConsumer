package com.prueba.apache.kafka.rest;

//import com.prueba.apache.kafka.helper.ProductorKafka;
import com.prueba.apache.kafka.model.Inventario;
import com.prueba.apache.kafka.repository.InventarioRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Sonia
 */
@RestController
@RequestMapping(value = "/inventario")
public class InventarioRest {

    @Autowired
    InventarioRepository inventarioRepository;
    
//     @Autowired
//    private ProductorKafka productorKafka;

    @RequestMapping(method = RequestMethod.GET)
    public List<Inventario> getInventario() {
        Iterable<Inventario> iterator = inventarioRepository.findAll();
        List<Inventario> result = new ArrayList<>();
        iterator.forEach(result::add);
        return result;
    }

}
