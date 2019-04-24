package rabbitMQ.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;


@Configuration
public class RabbitConfig {
	
	@Value("${mq.rabbit.host}")
	private String host;
	
	@Value("${mq.rabbit.port}")
	private int port;
	
	@Value("${mq.rabbit.username}")
	private String username;
	
	@Value("${mq.rabbit.password}")
	private String password;
	
	@Value("${mq.rabbit.virtualHost}")
	private String virtualHost;
	
	public static final String EXCHANGE_A = "rabbit-mq-exchange_A";
	public static final String EXCHANGE_B = "rabbit-mq-exchange_B";
	public static final String EXCHANGE_C = "rabbit-mq-exchange_C";
	
	public static final String QUEUE_A = "QUEUE_A";
	public static final String QUEUE_B = "QUEUE_B";
	public static final String QUEUE_C = "QUEUE_C";
	
    public static final String ROUTINGKEY_A = "spring-boot-routingKey_A";
    public static final String ROUTINGKEY_B = "spring-boot-routingKey_B";
    public static final String ROUTINGKEY_C = "spring-boot-routingKey_C";
    
    @Bean
    public ConnectionFactory connectionFactory(){
    	CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host, port);
    	connectionFactory.setUsername(username);
    	connectionFactory.setPassword(password);
    	connectionFactory.setVirtualHost(virtualHost);;
    	connectionFactory.setPublisherConfirms(true);
    	return connectionFactory;
    }
    
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public RabbitTemplate rabbitTemplate(){
    	RabbitTemplate template = new RabbitTemplate(connectionFactory());
    	return template;
    }
    
    /**
     * 针对消费者配置
     * 1.设置交换机类型
     * 2.将队列绑定到交换机
     * 
     * FanoutExchange:将消息分发到所有的绑定队列，无routingkey的概念
     * HeadersEchange:通过添加属性KEY-VALUE的方式匹配
     * DirextExchange:按照routingkey分发到指定队列
     * TopicExchange:多关键字匹配
     * @return
     */
    @Bean
    public DirectExchange defaultExchange(){
    	return new DirectExchange(EXCHANGE_A);
    }
    
    /**
     * 获取队列A
     * @return
     */
    @Bean
    public Queue queueA(){
    	return new Queue(QUEUE_A, true); //队列持久
    }
    
    /**
     * 获取队列B
     * @return
     */
    @Bean
    public Queue queueB(){
    	return new Queue(QUEUE_B, true); //队列持久
    }
    
    @Bean
    public Binding binding(){
    	return BindingBuilder.bind(queueA()).to(defaultExchange()).with(RabbitConfig.ROUTINGKEY_A);
    }
    
    @Bean
    public Binding bindingB(){
    	return BindingBuilder.bind(queueB()).to(defaultExchange()).with(RabbitConfig.ROUTINGKEY_B);
    }
}
