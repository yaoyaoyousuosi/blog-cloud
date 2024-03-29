package org.cloud.blog.producer.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueConfig {

    @Bean
    public Queue phoneQueue(){
        return new Queue("phone", false, false, false, null);
    }

    @Bean
    public DirectExchange phoneExchange(){
        return ExchangeBuilder.directExchange("phoneExchange").durable(true).build();
    }

    @Bean
    public Binding phoneBinding(){
        return BindingBuilder.bind(phoneQueue()).to(phoneExchange()).with("phone");
    }
}
