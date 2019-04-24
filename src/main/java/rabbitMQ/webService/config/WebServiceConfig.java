package rabbitMQ.webService.config;

import javax.xml.ws.Endpoint;

import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import rabbitMQ.webService.AppService;
import rabbitMQ.webService.check.AuthInterceptor;
import rabbitMQ.webService.imp.MyAppImp;

@Configuration
public class WebServiceConfig {
	
	@Bean
	public ServletRegistrationBean dispatcherServlet(){
		return new ServletRegistrationBean(new CXFServlet(), "/service/*");
	}
	
	@Bean(name=Bus.DEFAULT_BUS_ID)
	public SpringBus springBus(){
		return new SpringBus();
	}
	
	/**
	 * 实现类交给spring管理
	 * @return
	 */
	@Bean
	public AppService appService(){
		return new MyAppImp();
	}
	
	/**
	 * 终端路径
	 * @return
	 */
	@Bean
	public Endpoint endpoint(){
		EndpointImpl endPoint = new EndpointImpl(springBus(), appService());
		endPoint.getInInterceptors().add(new AuthInterceptor());
		endPoint.publish("/user");
		return endPoint;
		
	}
}
