package com.prueba.apache.kafka.repository;

import com.prueba.apache.kafka.model.Inventario;
import com.prueba.apache.kafka.model.QInventario;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author sonia.cabrera
 */
public interface InventarioRepository 
        extends CrudRepository<Inventario, String>, QuerydslPredicateExecutor<Inventario>, QuerydslBinderCustomizer<QInventario>{
    
    @Override
	default public void customize(QuerydslBindings bindings, QInventario root) {

	}
}
