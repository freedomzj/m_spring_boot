package com.config;
import javax.jms.ConnectionFactory;
import javax.jms.QueueConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

@Configuration
public class ActiveMQConfigurationForJmsCamelRouteConsumeAndForward {
	    public static final String LOCAL_Q = "localQ";
	    public static final String REMOTE_Q = "remoteQ";

	    @Bean
	    public BrokerService broker() throws Exception {
	        final BrokerService broker = new BrokerService();
	        broker.addConnector("tcp://localhost:5671");
	        broker.setBrokerName("broker");
	        broker.setUseJmx(false);
	        return broker;
	    }

	    @Bean
	    public BrokerService broker2() throws Exception {
	        final BrokerService broker = new BrokerService();
	        broker.addConnector("tcp://localhost:5672");
	        broker.setBrokerName("broker2");
	        broker.setUseJmx(false);
	        return broker;
	    }

	    @Bean
	    @Primary
	    public ConnectionFactory jmsConnectionFactory() {
	        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:5671");
	        return connectionFactory;
	    }

	    @Bean
	    public QueueConnectionFactory jmsConnectionFactory2() {
	        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:5672");
	        return connectionFactory;
	    }

	    @Bean
	    @Primary
	    public JmsTemplate jmsTemplate() {
	        JmsTemplate jmsTemplate = new JmsTemplate();
	        jmsTemplate.setConnectionFactory(jmsConnectionFactory());
	        jmsTemplate.setDefaultDestinationName(LOCAL_Q);
	        return jmsTemplate;
	    }

	    @Bean
	    public JmsTemplate jmsTemplate2() {
	        JmsTemplate jmsTemplate = new JmsTemplate();
	        jmsTemplate.setConnectionFactory(jmsConnectionFactory2());
	        jmsTemplate.setDefaultDestinationName(REMOTE_Q);
	        return jmsTemplate;
	    }

	    @Bean
	    public JmsListenerContainerFactory<?> jmsListenerContainerFactory(ConnectionFactory connectionFactory,
	            DefaultJmsListenerContainerFactoryConfigurer configurer) {
	        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
	        configurer.configure(factory, connectionFactory);
	        return factory;
	    }

	    @Bean
	    public JmsListenerContainerFactory<?> jmsListenerContainerFactory2(
	            @Qualifier("jmsConnectionFactory2") ConnectionFactory connectionFactory,
	            DefaultJmsListenerContainerFactoryConfigurer configurer) {
	        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
	        configurer.configure(factory, connectionFactory);
	        return factory;
	    }
	}

