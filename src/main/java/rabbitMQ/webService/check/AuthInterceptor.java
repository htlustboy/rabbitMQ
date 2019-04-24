package rabbitMQ.webService.check;

import java.util.List;

import javax.xml.soap.SOAPException;

import org.apache.cxf.binding.soap.SoapHeader;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.headers.Header;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;



public class AuthInterceptor extends AbstractPhaseInterceptor<SoapMessage>{
	
	private static final String USERNAME = "hutao";
	private static final String PASSWORD = "123456";

	public AuthInterceptor() {
		//定义在哪个阶段拦截
		super(Phase.PRE_PROTOCOL);
	}

	@Override
	public void handleMessage(SoapMessage soapMessage) throws Fault {
		List<Header> headers = null;
        String username=null;
        String password=null;
        try {
           headers = soapMessage.getHeaders();
        } catch (Exception e) {
           System.err.println(String.format("getSOAPHeader error: {}", e.getMessage()));
        }

        if (headers == null) {
            throw new Fault(new IllegalArgumentException("找不到Header，无法验证用户信息"));
        }
        //获取用户名,密码
        for (Header header : headers) {
            SoapHeader soapHeader = (SoapHeader) header;
            Element e = (Element) soapHeader.getObject();
            NodeList usernameNode = e.getElementsByTagName("username");
            NodeList pwdNode = e.getElementsByTagName("password");
             username=usernameNode.item(0).getTextContent();
             password=pwdNode.item(0).getTextContent();
            if( StringUtils.isEmpty(username)||StringUtils.isEmpty(password)){
                throw new Fault(new IllegalArgumentException("用户信息为空"));
            }
        }
        //校验用户名密码
        if(!(username.equals(USERNAME) && password.equals(PASSWORD))){
            SOAPException soapExc = new SOAPException("认证失败");
            System.out.println("用户认证信息错误");
            throw new Fault(soapExc);
        }
		
	}
}
