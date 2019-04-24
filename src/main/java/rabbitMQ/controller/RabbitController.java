package rabbitMQ.controller;


import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import rabbitMQ.config.MsgProducer;

@RestController
@RequestMapping("/rabbit")
public class RabbitController {
	
	@Resource
	private MsgProducer msgProducer;
	
	@ResponseBody
	@RequestMapping("/test")
	public String test(){
		msgProducer.sendMsg("QUEUE_A_MESSAGE");
		return "hello world!";
	}
	
}
