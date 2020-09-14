package com.prueba.apache.kafka.config;

import com.prueba.apache.kafka.mensajeDTO.ResultMsj;
import com.prueba.apache.kafka.mensajeDTO.VehiculoMsj;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;

/**
 *
 * @author sonia.cabrera
 */
@Configuration
public class KafkaConfig {

    @Bean
    public ReplyingKafkaTemplate<String, VehiculoMsj, ResultMsj> replyingKafkaTemplate(ProducerFactory<String, VehiculoMsj> pf,
            ConcurrentKafkaListenerContainerFactory<String, ResultMsj> factory) {
        ConcurrentMessageListenerContainer<String, ResultMsj> replyContainer = factory.createContainer("result");
        replyContainer.getContainerProperties().setMissingTopicsFatal(false);
        replyContainer.getContainerProperties().setGroupId("vehiculo-result-group");
        
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "http://172.17.0.8:9092");
//        props.put(ConsumerConfig.GROUP_ID_CONFIG, "vehiculo-result-group");
//        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        
        
        replyContainer.getContainerProperties().getConsumerProperties().putAll(props);
        return new ReplyingKafkaTemplate<>(pf, replyContainer);
    }

    @Bean
    public KafkaTemplate<String, ResultMsj> replyTemplate(ProducerFactory<String, ResultMsj> pf,
            ConcurrentKafkaListenerContainerFactory<String, ResultMsj> factory) {
        KafkaTemplate<String, ResultMsj> kafkaTemplate = new KafkaTemplate<>(pf);
        factory.getContainerProperties().setMissingTopicsFatal(false);
        factory.setReplyTemplate(kafkaTemplate);
        return kafkaTemplate;
    }
}
