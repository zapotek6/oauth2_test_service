package uk.co.labfour.cloud2.services.user;

import uk.co.labfour.bjson.BJsonDeSerializer;
import uk.co.labfour.bjson.BJsonException;
import uk.co.labfour.bjson.BJsonObject;
import uk.co.labfour.cloud2.aaa.common.IAAAClient;
import uk.co.labfour.cloud2.aaa_client.AAAClient;
import uk.co.labfour.cloud2.microservice.ServiceContext;
import uk.co.labfour.cloud2.microservice.ServiceVariables;
import uk.co.labfour.cloud2.persistence.nosql.GenericFilter;
import uk.co.labfour.cloud2.persistence.nosql.GenericRepository;
import uk.co.labfour.cloud2.protocol.BaseResponse;
import uk.co.labfour.cloud2.protocol.Constants;
import uk.co.labfour.cloud2.services.user.model.*;
import uk.co.labfour.cloud2.services.user.services.Hooks;
import uk.co.labfour.cloud2.services.user.services.Repository;
import uk.co.labfour.cloud2.services.user.services.SimpleCRUDSvc;
import uk.co.labfour.error.BEarer;
import uk.co.labfour.error.BException;
import uk.co.labfour.fsm.IStateMachine;
import uk.co.labfour.logger.MyLoggerFactory;
import uk.co.labfour.net.proto.mqtt.client.Mqtt;
import uk.co.labfour.net.proto.mqtt.client.MqttClientAsync;
import uk.co.labfour.net.transport.IGenericTransport2;
import uk.co.labfour.net.transport.MqttTransport2;

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
        addVar(ServiceVariables.MQTT_TOPIC, "activity");
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
    protected IGenericTransport2 _2_setupAAATransport() throws BException {
        String aaaAddress = getAAAClientAddress();

        log.i(this, "AAA Client listening on " + aaaAddress + " address.");

        MqttClientAsync client = new MqttClientAsync(log)
                .setServerUri(getVar(ServiceVariables.MQTT_CONNECTION_STRING))
                .setClientId(getVar(ServiceVariables.MQTT_CLIENTID))
                .setCredentials(getVar(ServiceVariables.MQTT_UID),
                        getVar(ServiceVariables.MQTT_PWD));

        setMqtt(new Mqtt(IStateMachine.RunMode.THREAD, log, 1000).
                setClient(client));

        MqttTransport2 mqttTransport2 = new MqttTransport2(getMqtt(), MyLoggerFactory.getInstance());

        BEarer listenOp = mqttTransport2.listen(aaaAddress, (topic, message) -> {
            try {
                getAaaClient().doProcess(BaseResponse.parseJson(new String(message)));
                return true;
            } catch (BException e) {
                e.printStackTrace();
                return false;
            }
        });

        return mqttTransport2;
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

        Hooks<Activity> addHook = new Hooks<Activity>() {
            @Override
            public BEarer<GenericFilter> prepareFilter(Repository repository, BJsonDeSerializer bJsonDeSerializer, BJsonObject payload, Class<Activity> clazz) {
                return null;
            }

            @Override
            public BEarer pre(Activity object) {
                object.setCreated(System.currentTimeMillis());
                object.setUpdated(System.currentTimeMillis());
                return BEarer.createSuccess();
            }

            @Override
            public BEarer post(Activity object) {
                return BEarer.createSuccess();
            }
        };

        Hooks<Activity> updateHook = new Hooks<Activity>() {
            @Override
            public BEarer<GenericFilter> prepareFilter(Repository repository, BJsonDeSerializer bJsonDeSerializer, BJsonObject payload, Class<Activity> clazz) {
                return null;
            }

            @Override
            public BEarer pre(Activity object) {
                object.setUpdated(System.currentTimeMillis());
                return BEarer.createSuccess();
            }

            @Override
            public BEarer post(Activity object) {
                return BEarer.createSuccess();
            }
        };

        addService("activity",
                null,
                new SimpleCRUDSvc<>(this, Repository.getInstance(this), Activity.class)
                        .setAddHook(addHook)
                        .setUpdateHook(updateHook),
                "activity.add",
                "/activity/add");

        Hooks<Contact> addHookContact = new Hooks<Contact>() {
            @Override
            public BEarer<GenericFilter> prepareFilter(Repository repository, BJsonDeSerializer bJsonDeSerializer, BJsonObject payload, Class<Contact> clazz) {
                return null;
            }

            @Override
            public BEarer pre(Contact object) {
                object.setCreated(System.currentTimeMillis());
                object.setUpdated(System.currentTimeMillis());
                return BEarer.createSuccess();
            }

            @Override
            public BEarer post(Contact object) {
                return BEarer.createSuccess();
            }
        };

        Hooks<Contact> updateHookContact = new Hooks<Contact>() {
            @Override
            public BEarer<GenericFilter> prepareFilter(Repository repository, BJsonDeSerializer bJsonDeSerializer, BJsonObject payload, Class<Contact> clazz) {
                return null;
            }

            @Override
            public BEarer pre(Contact object) {
                object.setUpdated(System.currentTimeMillis());
                return BEarer.createSuccess();
            }

            @Override
            public BEarer post(Contact object) {
                return BEarer.createSuccess();
            }
        };

        addService("contact",
                null,
                new SimpleCRUDSvc<>(this, Repository.getInstance(this), Contact.class)
                        .setAddHook(addHookContact)
                        .setUpdateHook(updateHookContact),
                "contact",
                "/contact");


        Hooks<Alarm> getHookAlarm = new Hooks<Alarm>() {
            @Override
            public BEarer<GenericFilter> prepareFilter(Repository repository, BJsonDeSerializer bJsonDeSerializer, BJsonObject payload, Class<Alarm> clazz) {
                try {
                    if (payload.has(Constants.FIELD_UUID)) {
                        String uuid = payload.getElementAsString(Constants.FIELD_UUID);

                        BEarer<GenericFilter> filterOp = repository.buildFilterSingleField("owner.uuid", uuid, repository, clazz);

                        if (filterOp.isOk()) {
                            return filterOp;
                        } else {
                            return BEarer.createGenericError(this, filterOp.getDescription());
                        }
                    } else {
                        return new BEarer<GenericFilter>().setSuccess().set(null);
                    }
                } catch (BJsonException e) {
                    return BEarer.createGenericError(this, e.getMessage());
                }
            }

            @Override
            public BEarer pre(Alarm object) {
                return BEarer.createSuccess();
            }

            @Override
            public BEarer post(Alarm object) {
                return BEarer.createSuccess();
            }
        };


        addService("alarm",
                null,
                new SimpleCRUDSvc<>(this, Repository.getInstance(this), Alarm.class)
                .setGetHook(getHookAlarm),
                "alarm",
                "/alarm");



        Hooks<Project> addHookProject = new Hooks<Project>() {
            @Override
            public BEarer<GenericFilter> prepareFilter(Repository repository, BJsonDeSerializer bJsonDeSerializer, BJsonObject payload, Class<Project> clazz) {
                return null;
            }

            @Override
            public BEarer pre(Project object) {
                object.setCreated(System.currentTimeMillis());
                object.setUpdated(System.currentTimeMillis());
                return BEarer.createSuccess();
            }

            @Override
            public BEarer post(Project object) {
                return BEarer.createSuccess();
            }
        };

        Hooks<Project> updateHookProject = new Hooks<Project>() {
            @Override
            public BEarer<GenericFilter> prepareFilter(Repository repository, BJsonDeSerializer bJsonDeSerializer, BJsonObject payload, Class<Project> clazz) {
                return null;
            }

            @Override
            public BEarer pre(Project object) {
                object.setUpdated(System.currentTimeMillis());
                return BEarer.createSuccess();
            }

            @Override
            public BEarer post(Project object) {
                return BEarer.createSuccess();
            }
        };

        addService("project",
                null,
                new SimpleCRUDSvc<>(this, Repository.getInstance(this), Project.class)
                        .setAddHook(addHookProject)
                        .setUpdateHook(updateHookProject),
                "project",
                "/project");


        Hooks<Skill> addHookSkill = new Hooks<Skill>() {
            @Override
            public BEarer<GenericFilter> prepareFilter(Repository repository, BJsonDeSerializer bJsonDeSerializer, BJsonObject payload, Class<Skill> clazz) {
                return null;
            }

            @Override
            public BEarer pre(Skill object) {
                object.setCreated(System.currentTimeMillis());
                object.setUpdated(System.currentTimeMillis());
                return BEarer.createSuccess();
            }

            @Override
            public BEarer post(Skill object) {
                return BEarer.createSuccess();
            }
        };

        Hooks<Skill> updateHookSkill = new Hooks<Skill>() {
            @Override
            public BEarer<GenericFilter> prepareFilter(Repository repository, BJsonDeSerializer bJsonDeSerializer, BJsonObject payload, Class<Skill> clazz) {
                return null;
            }

            @Override
            public BEarer pre(Skill object) {
                object.setUpdated(System.currentTimeMillis());
                return BEarer.createSuccess();
            }

            @Override
            public BEarer post(Skill object) {
                return BEarer.createSuccess();
            }
        };

        addService("skill",
                null,
                new SimpleCRUDSvc<>(this, Repository.getInstance(this), Skill.class)
                        .setAddHook(addHookSkill)
                        .setUpdateHook(updateHookSkill),
                "skill",
                "/skill");


        // ROAD MAP ELEMENT
        Hooks<RoadMapElement> addHookRoadMapElement = new Hooks<RoadMapElement>() {
            @Override
            public BEarer<GenericFilter> prepareFilter(Repository repository, BJsonDeSerializer bJsonDeSerializer, BJsonObject payload, Class<RoadMapElement> clazz) {
                return null;
            }

            @Override
            public BEarer pre(RoadMapElement object) {
                object.setCreated(System.currentTimeMillis());
                object.setUpdated(System.currentTimeMillis());
                return BEarer.createSuccess();
            }

            @Override
            public BEarer post(RoadMapElement object) {
                return BEarer.createSuccess();
            }
        };

        Hooks<RoadMapElement> updateHookRoadMapElement = new Hooks<RoadMapElement>() {
            @Override
            public BEarer<GenericFilter> prepareFilter(Repository repository, BJsonDeSerializer bJsonDeSerializer, BJsonObject payload, Class<RoadMapElement> clazz) {
                return null;
            }

            @Override
            public BEarer pre(RoadMapElement object) {
                object.setUpdated(System.currentTimeMillis());
                return BEarer.createSuccess();
            }

            @Override
            public BEarer post(RoadMapElement object) {
                return BEarer.createSuccess();
            }
        };

        Hooks<RoadMapElement> getHookRoadMapElement = new Hooks<RoadMapElement>() {
            @Override
            public BEarer<GenericFilter> prepareFilter(Repository repository, BJsonDeSerializer bJsonDeSerializer, BJsonObject payload, Class<RoadMapElement> clazz) {
                try {
                    if (payload.has("projectUuid")) {
                        String uuid = payload.getElementAsString("projectUuid");

                        BEarer<GenericFilter> filterOp = repository.buildFilterSingleField("project.uuid", uuid, repository, clazz);

                        if (filterOp.isOk()) {
                            return filterOp;
                        } else {
                            return BEarer.createGenericError(this, filterOp.getDescription());
                        }
                    } else if (payload.has(Constants.FIELD_UUID)) {
                        String uuid = payload.getElementAsString(Constants.FIELD_UUID);

                        BEarer<GenericFilter> filterOp = repository.buildFilterPerUuid(uuid, repository, clazz);

                        if (filterOp.isOk()) {
                            return filterOp;
                        } else {
                            return BEarer.createGenericError(this, filterOp.getDescription());
                        }
                    }  else {
                        return new BEarer<GenericFilter>().setSuccess().set(null);
                    }
                } catch (BJsonException e) {
                    return BEarer.createGenericError(this, e.getMessage());
                }
            }

            @Override
            public BEarer pre(RoadMapElement object) {
                return BEarer.createSuccess();
            }

            @Override
            public BEarer post(RoadMapElement object) {
                return BEarer.createSuccess();
            }
        };

        addService("roadmapelement",
                null,
                new SimpleCRUDSvc<>(this, Repository.getInstance(this), RoadMapElement.class)
                        .setGetHook(getHookRoadMapElement)
                        .setAddHook(addHookRoadMapElement)
                        .setUpdateHook(updateHookRoadMapElement),
                "roadmapelement",
                "/roadmapelement");
    }





}
