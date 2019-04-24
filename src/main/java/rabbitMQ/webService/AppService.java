package rabbitMQ.webService;

import java.io.UnsupportedEncodingException;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface AppService {
	
	@WebMethod
	String getUserName(@WebParam(name="id")String id) throws UnsupportedEncodingException;
	
	@WebMethod
	public Object getUser(String id) throws UnsupportedEncodingException;
}
