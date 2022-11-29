package org.cloud.blog.consumer.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueConfig {

    @Bean
    public Queue phoneQueue(){
        return new Queue("phone", true, false, false, null);
    }

    @Bean
    public DirectExchange phoneExchange(){
        return ExchangeBuilder.directExchange("phoneExchange").build();
    }

    @Bean
    public Binding phoneBinding(){
        return BindingBuilder.bind(phoneQueue()).to(phoneExchange()).with("phone");
    }
}
