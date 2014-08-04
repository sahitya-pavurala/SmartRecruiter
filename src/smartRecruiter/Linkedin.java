package smartRecruiter;

import org.scribe.builder.*;
import org.scribe.builder.api.*;
import org.scribe.model.*;
import org.scribe.oauth.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.File;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class Linkedin implements Connector{
	private static Token accessToken;
    private static Token requestToken;
    private static OAuthService service;

    public Linkedin() {
        this.service = new ServiceBuilder().provider(LinkedInApi.class).apiKey(CONSUMER_KEY).apiSecret(CONSUMER_SECRET)
        		.callback("http://localhost:8090/SmartRecruiter/dummy.jsp")
                .build();
    }

    @Override
    public String authenticate() {
        // obtain auth token
        this.requestToken = this.service.getRequestToken();
        // after this step we need to make user obtain permission e.g. from form on the LinkedIn site
        return AUTHORIZE_URL + this.requestToken.getToken();
    }

    @Override
    public String[] authorize(final String code) throws Exception {
        final Verifier verifier = new Verifier(code);
        this.accessToken = this.service.getAccessToken(this.requestToken, verifier);
        final String token;
        final String secret;
        if (this.accessToken == null || (token = this.accessToken.getToken()) == null
                || (secret = this.accessToken.getSecret()) == null) {
            throw new Exception("Failed to obtain valid authentication response");
        }
        return new String[] { token, secret };
    }

    @Override
    public SiteResponse get(final String url) {
        final OAuthRequest request = new OAuthRequest(Verb.GET, url);
        this.service.signRequest(this.accessToken, request);
        return new ScribeResponseImpl(request.send());
    }

    @Override
    public void init(final String authToken, final String authSecret) {
        this.accessToken = new Token(authToken, authSecret);
    }

    @Override
    public int signOut() {
        return get(SIGN_OUT_URL).getCode();
    }

    public class ScribeResponseImpl implements SiteResponse {
        private final Response response;

        public ScribeResponseImpl(final Response response) {
            super();
            this.response = response;
        }

        @Override
        public String getBody() {
            return this.response.getBody();
        }

        @Override
        public int getCode() {
            return this.response.getCode();
        }

        @Override
        public String getHeader(final String name) {
            return this.response.getHeader(name);
        }

        @Override
        public Map getHeaders() {
            return this.response.getHeaders();
        }

        @Override
        public InputStream getStream() {
            return this.response.getStream();
        }
    }
    
    public String generateAuthUrl() throws Exception {
    	Connector connector = new Linkedin();
    	
    	final String authUrl = connector.authenticate();
    	return authUrl;
         
       //Token accessToken = new Token("d9f9dcbe-747d-4352-a530-117d0abd642b", "d4735160-a4f1-4b42-9386-f1adb592b06");
       
        
        //System.out.println("Got the Access Token!");
        
        
        
    	/*final String[] tokens = connector.authorize("76518");
    	for(String token: tokens) {
    		System.out.println(token);
    	}*/
    	
        
    }
    
    
    public Token verifyToken(String token, HttpServletRequest request){
    	Verifier verifier = new Verifier(token);
        Token accessToken = service.getAccessToken(requestToken, verifier);
        HttpSession session = request.getSession(true);
        session.setAttribute("service", service);
        return accessToken;
    }
    
    
    public String test(Token accessToken){
    	OAuthRequest request = new OAuthRequest(Verb.GET, "http://api.linkedin.com/v1/people/~");
        service.signRequest(accessToken, request);
        Response response = request.send();
        System.out.println(response.getBody());
       return response.getBody();
    }
    
    
    
    static CandidateInfoBean parseProfileInfo(String data) throws ParserConfigurationException, SAXException, IOException {
    	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    	DocumentBuilder db = null;
	    db = dbf.newDocumentBuilder();
	    InputSource is = new InputSource();
	    is.setCharacterStream(new StringReader(data));
    	CandidateInfoBean cand = new CandidateInfoBean();
        Document doc = db.parse(is);
        String message = doc.getDocumentElement().getTextContent();
        StringTokenizer st = new StringTokenizer(message, "\n");
        int count = 1;
        while(st.hasMoreTokens()){
        	if(count  == 1){
        		cand.setFirstName(st.nextToken().trim());
        	}else if(count == 2){
        		cand.setLastName(st.nextToken().trim());
        	}else if(count == 5){
        		cand.setLinkToProfile(st.nextToken().trim());
        	}else{
        		st.nextToken();
        	}
        	count++;
        }
		return cand;
	}

	/**
	 * I take an employee element and read the values in, create
	 * an Employee object and return it
	 */
	private static String getElement(Element guidEl) {

		// for each <employee> element get text or int values of
		// name ,id, age and name
		String name = getTextValue(guidEl, "first-name");
		// System.out.println("Name:   "+name);

		return name;
	}

	/**
	 * I take a xml element and the tag name, look for the tag and get
	 * the text content
	 * i.e for <?xml version="1.0" encoding="utf-8"?><guid xmlns="http://social.yahooapis.com/v1/schema.rng"
	 * xmlns:yahoo="http://www.yahooapis.com/v1/base.rng"
	 * yahoo:uri="http://social.yahooapis.com/v1/me/guid"><value>4QWOBUCQHETEL34LSRUKJEV5W4</value></guid> xml snippet if
	 * the Element points to employee node and tagName is 'name' I will return John
	 */
	private static String getTextValue(Element ele, String tagName) {
		String textVal = null;
		NodeList nl = ele.getElementsByTagName(tagName);
		if (nl != null && nl.getLength() > 0) {
			Element el = (Element) nl.item(0);
			textVal = el.getFirstChild().getNodeValue();
		}

		return textVal;
	}
	
}
