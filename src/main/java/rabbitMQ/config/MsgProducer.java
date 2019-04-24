package rabbitMQ.config;

import java.util.UUID;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MsgProducer implements ConfirmCallback{
	
	private RabbitTemplate rabbitTemplate;
	
	@Autowired
	public MsgProducer(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
		rabbitTemplate.setConfirmCallback(this);
	}
	
	public void sendMsg(String message){
		CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString().replaceAll("-", ""));
        //把消息放入ROUTINGKEY_A对应的队列当中去，对应的是队列A
		rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_A, RabbitConfig.ROUTINGKEY_A, message, correlationData);
	}
	
	/**
	 * 回调
	 */
	@Override
	public void confirm(CorrelationData correlationData, boolean ack, String cause) {
		System.out.println("回调ID："+correlationData);
		if(ack){
			System.out.println("消息成功消费");
		}else{
			System.out.println("消息消费失败："+ cause);
		}
	}

}
