package uk.co.labfour.cloud2.services.user;

import uk.co.labfour.cloud2.aaa.common.IAAAClient;
import uk.co.labfour.cloud2.aaa_client.AAAClient;
import uk.co.labfour.cloud2.microservice.ServiceContext;
import uk.co.labfour.cloud2.microservice.ServiceStub;
import uk.co.labfour.cloud2.microservice.ServiceVariables;
import uk.co.labfour.cloud2.persistence.nosql.GenericRepository;
import uk.co.labfour.cloud2.protocol.BaseResponse;
import uk.co.labfour.cloud2.services.user.services.Core;
import uk.co.labfour.error.BException;
import uk.co.labfour.net.proto.mqtt.transport.MqttTransport;
import uk.co.labfour.net.transport.IAsyncTransportCallback;
import uk.co.labfour.net.transport.IGenericTransport;

public class MyServiceContext extends ServiceContext {
	private static MyServiceContext instance;

    public static MyServiceContext getInstance() throws BException {
		if (null == instance) {
			instance = new MyServiceContext();
		}	
		return instance;
	}
	
	@Override
	protected void _1_setupSettings() {
		addVar(ServiceContext.SVC_UUID, "00000000-0000-0000-0000-000000000014");
		addVar(SVC_APIKEY, "02983465yr2hfzo287943uredq8ywetd0,hq87gry0w9e8ygr9h");
        addVar(ServiceVariables.MQTT_PROTO, "tcp");
        addVar(ServiceVariables.MQTT_HOST, "localhost");
        addVar(ServiceVariables.MQTT_PORT, "1883");
        addVar(ServiceVariables.MQTT_TOPIC, "user");
        addVar(ServiceVariables.MQTT_CLIENTID, "SRV01-USER");
        addVar(ServiceVariables.MQTT_UID, "guest");
        addVar(ServiceVariables.MQTT_PWD, "guest");
        addVar(ServiceVariables.MQTT_CONNECTION_STRING, null);

        addVar(ServiceVariables.MONGODB_HOST, "localhost");
        addVar(ServiceVariables.MONGODB_PORT, "27017");
        addVar(ServiceVariables.MONGODB_CONNECTION_STRING, null);

        addVar(ServiceVariables.REDIS_HOST, "localhost");
        addVar(ServiceVariables.REDIS_PORT, "6379");
        addVar(ServiceVariables.REDIS_DB, "0");
        addVar(ServiceVariables.REDIS_CONNECTION_STRING, null);
		
	}

    @Override
    protected IGenericTransport _2_setupAAATransport() throws BException {
        String aaaAddress = getAAAClientAddress();

        log.i(this, "AAA Client listening on " + aaaAddress + " address.");

        MqttTransport mqtt = new MqttTransport();

        mqtt.setup(getVar(ServiceVariables.MQTT_CONNECTION_STRING), getVar(ServiceVariables.MQTT_CLIENTID), getVar(ServiceVariables.MQTT_UID), getVar(ServiceVariables.MQTT_PWD));

        /*mqtt.subscribe(getMqttTopic(), new IAsyncTransportCallback() {

            public void callback(String message) throws BException {
                log.i(this, "msg on " + getMqttTopic());
                demux.doStartServingRequestAsync(getAaaClient(), mqtt, BaseRequest.parseJson(message));
            }
        });*/

        mqtt.subscribe(aaaAddress, new IAsyncTransportCallback() {

            public void callback(String message) throws BException {
                getAaaClient().doProcess(BaseResponse.parseJson(message));
            }
        });

        return mqtt;
    }

    @Override
    protected IAAAClient _3_setupAAAClient() {
        AAAClient aaaClient = new AAAClient(getAaaTransport(), getAAAClientAddress());
        return aaaClient;
    }

    @Override
    protected GenericRepository _4_setupRepository() {
        return null;
    }

    @Override
    protected void initComponents() {

    }

    @Override
	protected void populateServices() throws BException {
		addService( "user",
					null,
					new Core(this), 
					"user.add",
					"/user/add");
	}
	
}
