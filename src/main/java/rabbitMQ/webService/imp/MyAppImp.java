package rabbitMQ.webService.imp;

import java.io.UnsupportedEncodingException;

import javax.jws.WebService;

import rabbitMQ.webService.AppService;

@WebService(serviceName="myApp",name="myService",targetNamespace="http://10.10.1.146:9999/",endpointInterface="rabbitMQ.webService.AppService")
public class MyAppImp implements AppService{
	
	@Override
	public String getUserName(String id) throws UnsupportedEncodingException {
		System.out.println("===============================================");
		return id+":lustboy";
	}

	@Override
	public Object getUser(String id) throws UnsupportedEncodingException {
		System.out.println("===============================================");
		return id+"{hello:world}";
	}

}
