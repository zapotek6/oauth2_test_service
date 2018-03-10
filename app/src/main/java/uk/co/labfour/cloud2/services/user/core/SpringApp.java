package uk.co.labfour.cloud2.services.user.core;

import java.io.IOException;
import java.util.HashMap;

import com.google.gson.Gson;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import uk.co.labfour.bjson.BJsonParser;
import uk.co.labfour.cloud2.microservice.ServiceStub;
import uk.co.labfour.cloud2.services.user.MyServiceContext;
import uk.co.labfour.error.BException;
import uk.co.labfour.logger.MyLogger;
import uk.co.labfour.logger.MyLoggerFactory;


@SpringBootApplication
public class SpringApp {
	MyLogger logger = MyLoggerFactory.getInstance();
	public static final String PAR_START_SPRING = "--web";
	public static final String PAR_SPRING_PORT = "--port";
	public static final int DEFAULT_SPRING_PORT = 8080;
	
	
	private void launchStandalone() throws BException {
		ServiceStub serviceStub;
		MyServiceContext si = MyServiceContext.getInstance();

		try {
			si.getServiceStub().start();
		} catch (BException e) {
			e.printStackTrace();
		}
		
		try {
			System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void launchSprintBoot(int port) {
		logger.d(this, "Spring Boot port: " + port);
		HashMap<String, Object> props = new HashMap<String, Object>();
		props.put("server.port", port);
		new SpringApplicationBuilder().properties(props).sources(SpringApp.class).registerShutdownHook(true).run();
	}
	
	private void printUsage() {
		logger.w(null, "Use program [--web [--port portnumber]]");
	}
	
	private boolean isThereAnotherParameter(int i, String[] args) {
		if ( i+1 < args.length) {
			return true;
		} else {
			return false;
		}
	}
	
	private void exitOnError() {
		printUsage();
		System.exit(1);
	}
	
	private void parseArguments(String[] args) throws BException {
		boolean launchSpring = false;
		int springPort = DEFAULT_SPRING_PORT;
		
		if (0 < args.length) {
			logger.i(null, "Input parameters: ");
			for (int i=0; i < args.length; ++i) {
				logger.i(null, args[i]);
				
				if (0 == args[i].compareTo(PAR_START_SPRING)) {
					launchSpring = true;
				}
				
				if (0 == args[i].compareTo(PAR_SPRING_PORT)) {
					if (isThereAnotherParameter(i, args)) {
						springPort = Integer.parseInt(args[++i]);
					} else {
						exitOnError();
					}
					
				}
			}	
		} else {
			//exitOnError();
		}
		
		if (launchSpring) {
			logger.i(null, "Launching Spring Boot");
			launchSprintBoot(springPort);
		} else {
			logger.i(null, "Launching as standalone application without web access");
			launchStandalone();
		}
	}

	public static void main(String[] args) {
		SpringApp app = new SpringApp();

		try {
			app.parseArguments(args);
		} catch (BException e) {
			e.printStackTrace();
		}
	}
}
