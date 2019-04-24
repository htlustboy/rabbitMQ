package rabbitMQ.webService.client;

import java.io.UnsupportedEncodingException;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import rabbitMQ.webService.AppService;

public class Test {
	private static String address = "http://10.10.1.146:9999/service/user?WSDL";
	
	public static void main(String[] args) {
		test1();
	}

	private static void test1() {
		
		JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
		jaxWsProxyFactoryBean.setAddress(address);
		jaxWsProxyFactoryBean.getOutInterceptors().add(new ClientInterceptor("hutao","1234562"));
		jaxWsProxyFactoryBean.setServiceClass(AppService.class);
		AppService appService = (AppService) jaxWsProxyFactoryBean.create();
		String id = "1";
		Object obj;
		try {
			obj = appService.getUser(id);
			System.out.println(obj.toString());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
	}
}
