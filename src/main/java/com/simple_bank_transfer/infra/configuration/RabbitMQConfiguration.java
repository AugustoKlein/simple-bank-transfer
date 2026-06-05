package com.simple_bank_transfer.infra.configuration;

import lombok.Data;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class RabbitMQConfiguration {

    @Value("${spring.custom-config.rabbitmq.queue}")
    private String queue;

    @Value("${spring.custom-config.rabbitmq.exchange}")
    private String exchange;

    @Value("${spring.custom-config.rabbitmq.routing-key}")
    private String routingKey;

    @Bean
    public Queue setupQueue() {
        return new Queue(queue, true);
    }

    @Bean
    public DirectExchange setupExchange() {
        return new DirectExchange(exchange);
    }

    @Bean
    public Binding setupBiding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKey);
    }

    @Bean
    public JacksonJsonMessageConverter jsonMessageConverter() {
        return new JacksonJsonMessageConverter();
    }
}
