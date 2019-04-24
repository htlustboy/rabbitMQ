package rabbitMQ.config;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MsgReceiver {
	
	@RabbitListener(queues=RabbitConfig.QUEUE_A)
	public void processA(String message){
		System.out.println(message);
	}
}
