package uk.co.labfour.cloud2.services.user.core;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.OPTIONS;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import uk.co.labfour.bjson.BJsonArray;
import uk.co.labfour.bjson.BJsonException;
import uk.co.labfour.bjson.BJsonObject;
import uk.co.labfour.cloud2.microservice.ServiceContext;
import uk.co.labfour.cloud2.microservice.ServiceStub;
import uk.co.labfour.cloud2.protocol.BaseRequest;
import uk.co.labfour.cloud2.protocol.BaseResponse;
import uk.co.labfour.cloud2.services.user.MyServiceContext;
import uk.co.labfour.cloud2.services.user.model.CommunicationChannels;
import uk.co.labfour.cloud2.services.user.model.PushNotification;
import uk.co.labfour.cloud2.services.user.oauth.AuthorizationRequest;
import uk.co.labfour.cloud2.services.user.oauth.AuthorizationResponse;
import uk.co.labfour.cloud2.services.user.oauth.TokenRequest;
import uk.co.labfour.cloud2.services.user.oauth.TokenResponse;
import uk.co.labfour.cloud2.services.user.services.Repository;
import uk.co.labfour.error.BException;
import uk.co.labfour.logger.MyLogger;
import uk.co.labfour.logger.MyLoggerFactory;

import java.util.Enumeration;
import java.util.Map;

@RequestMapping("/")
@CrossOrigin(origins = "*", methods = {OPTIONS, POST, PUT, GET }, maxAge = 3600)
@RestController
public class Rest {
	
	MyLogger log = MyLoggerFactory.getInstance();
	ServiceContext si;


	@RequestMapping(path = "/oauth/authorize", method = RequestMethod.GET)
	private ResponseEntity<?> oauthAuthorizationRequest(HttpServletRequest request) {

		HttpHeaders httpHeaders = new HttpHeaders();


        AuthorizationRequest authorizationRequest = AuthorizationRequest.build(request.getParameterMap());
		if (authorizationRequest.isValid()) {
            System.out.println("request valid");

            AuthorizationResponse authorizationResponse = new AuthorizationResponse(authorizationRequest);
            authorizationResponse.setCode("0987654321");
            httpHeaders.setLocation(authorizationResponse.buildLocation());
            return new ResponseEntity<>("", httpHeaders, HttpStatus.FOUND);

		} else {
            System.out.println("invalid request");
        }

		return new ResponseEntity<>("accepted", httpHeaders, HttpStatus.ACCEPTED);

	}

    @RequestMapping(path = "/oauth/token", method = RequestMethod.POST)
    private ResponseEntity<?> oauthTokenRequest(HttpServletRequest request) {

        HttpHeaders httpHeaders = new HttpHeaders();

		/*System.out.println("authType: " + request.getAuthType());
		Enumeration<String> attr = request.getAttributeNames();
		while (attr.hasMoreElements()) {
			System.out.println("attribute: " + attr.nextElement());
		}
		Map<String, String[]> par = request.getParameterMap();

		for (String s: par.keySet()) {
			System.out.print("k: " + s);
			for (String v: (String[])par.get(s)) {
				System.out.println(" v: " + v);
			}
		}*/

        TokenRequest tokenRequest = TokenRequest.build(request.getParameterMap());
        if (tokenRequest.isValid()) {
            System.out.println("request valid");
            // authenticate client
            // generate token
            TokenResponse tokenResponse = new TokenResponse();
            tokenResponse.setAccessToken("PIPPPOOooooooooooo");
            tokenResponse.setTokenType("booo");
            tokenResponse.setExpiresIn(1333);
            tokenResponse.setRefreshToken("");

            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            return new ResponseEntity<>(tokenResponse.build().toString(), httpHeaders, HttpStatus.OK);

        } else {
            System.out.println("invalid request");
        }

        return new ResponseEntity<>("accepted", httpHeaders, HttpStatus.OK);

    }


    @RequestMapping(path = "/videos/favorites", method = RequestMethod.GET)
    private ResponseEntity<?> videoFav(HttpServletRequest request) {

        String Authorization = request.getHeader("Authorization");

        if (null != Authorization) {
            System.out.println("auth: " + Authorization);
        } else {
            System.out.println("no auth");
        }


        BJsonObject  feed = new BJsonObject();
        try {
            BJsonObject  video = new BJsonObject();
            video.put("id", "1333");
            BJsonArray tags = new BJsonArray();
            tags.add("comedy");
            tags.add("new");
            video.put("tags", tags);
            video.put("title", "HAL 9000");
            video.put("url", "http://127.0.0.1");

            BJsonArray list = new BJsonArray();
            list.add(video);

            feed.put("list", list);
            feed.put("limit", 1);
            feed.put("hasMore", false);

        } catch (BJsonException e) {
            e.printStackTrace();
        }



        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(feed.toString(), httpHeaders, HttpStatus.OK);

    }


	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> add(@RequestBody String input, HttpServletRequest request) {
		
		HttpHeaders httpHeaders = new HttpHeaders();

		BaseRequest brequest;
		BaseResponse response = null;
		
		try {
			brequest = BaseRequest.parseJson(input);

			ServiceStub serviceStub = si.getServiceStub();

			response = serviceStub.doStartServingRequestSync(si.getAaaClient(), si.getAaaTransport(), brequest);
			
		} catch (BException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String sResponse = "";
		try {
			sResponse = BaseResponse.toJsonString(response);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BJsonException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		return new ResponseEntity<>(sResponse, httpHeaders, HttpStatus.CREATED);
		
    }
	 
	
 	@PostConstruct
	public void init() {
 		log.i(this, "PostConstruct SpringApp start");
 		
 		 		
 		try {
 			si = MyServiceContext.getInstance();
 			si.getServiceStub().start();
 			test();
		} catch (BException e) {
			e.printStackTrace();
		}
 		log.i(this, "PostConstruct SpringApp end");
	}

	private void test() throws BException {
		PushNotification pn = new PushNotification();
		pn.deviceId = "id";
		pn.deviceDescription = "N8";
		pn.data.appId="1234567890987654321";
		pn.data.gateway="gcm";
		pn.data.token="234rtg4ecguhyfgdw4wzf";
		pn.capabilities.add("text");
		pn.capabilities.add("textWithUrl");
		CommunicationChannels communicationChannels = new CommunicationChannels();
		communicationChannels.pushNotifications.add(pn);
		Repository.getInstance(si).save(communicationChannels);
	}
	
	@PreDestroy
	public void destory() {
		log.i(this, "PreDestroy SpringApp start");
		try {
            si.getServiceStub().stop();
		} catch (BException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.i(this, "PreDestroy SpringApp end");
	}

}
