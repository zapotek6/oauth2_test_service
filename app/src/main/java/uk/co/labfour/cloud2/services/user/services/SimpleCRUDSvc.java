package uk.co.labfour.cloud2.services.user.services;

import uk.co.labfour.bjson.BJsonDeSerializer;
import uk.co.labfour.bjson.BJsonException;
import uk.co.labfour.bjson.BJsonObject;
import uk.co.labfour.cloud2.aaa.common.IService;
import uk.co.labfour.cloud2.entity.primitive.IBaseObject;
import uk.co.labfour.cloud2.microservice.ServiceContext;
import uk.co.labfour.cloud2.protocol.BaseRequest;
import uk.co.labfour.cloud2.protocol.BaseResponse;
import uk.co.labfour.error.BException;
import uk.co.labfour.fin.binance.common.command.ActionEnvelope;
import uk.co.labfour.logger.MyLogger;
import uk.co.labfour.logger.MyLoggerFactory;

public class SimpleCRUDSvc<T extends IBaseObject> implements IService {
    Hooks<T> getHook;
    Hooks<T> addHook;
    Hooks<T> updateHook;
    Hooks<T> deleteHook;

	Class<T> clazz;
    MyLogger log = MyLoggerFactory.getInstance();
	ServiceContext si;

	Repository repository;
	
	private void setup() { }
	
	public SimpleCRUDSvc(ServiceContext si, Repository repository, Class<T> clazz) throws BException {
		this.si = si;
		this.clazz = clazz;
        this.repository = repository;
        setup();
	}

    public Hooks<T> getGetHook() {
        return getHook;
    }

    public SimpleCRUDSvc<T> setGetHook(Hooks<T> getHook) {
        this.getHook = getHook;
        return this;
    }

    public Hooks<T> getAddHook() {
        return addHook;
    }

    public SimpleCRUDSvc<T> setAddHook(Hooks<T> addHook) {
        this.addHook = addHook;
        return this;
    }

    public Hooks<T> getUpdateHook() {
        return updateHook;
    }

    public SimpleCRUDSvc<T> setUpdateHook(Hooks<T> updateHook) {
        this.updateHook = updateHook;
        return this;
    }

    public Hooks<T> getDeleteHook() {
        return deleteHook;
    }

    public SimpleCRUDSvc<T> setDeleteHook(Hooks<T> deleteHook) {
        this.deleteHook = deleteHook;
        return this;
    }

    public BaseResponse doExec(BaseRequest request) throws BException {
		return doProcessCommand(request);
	}

	public <T extends IBaseObject> BaseResponse doProcessCommand(BaseRequest request) {

		BaseResponse response = new BaseResponse(request);

        BJsonDeSerializer bJsonDeSerializer = repository.getGenericObjectHelper(clazz).get().getbJsonDeSerializer();
                //BJsonDeSerializerFactory.getInstance(false);

		BJsonObject payload = request.getPayload();
		try {
			if (payload.has(ActionEnvelope.ACTION_FIELD)) {
				String api = payload.getElementAsString(ActionEnvelope.ACTION_FIELD);

				switch (api) {
                    case "get":
                        ProtocolHelper.get(response, repository, bJsonDeSerializer, payload, clazz, getHook);
                        break;
                    case "add":
						ProtocolHelper.update(response, payload, repository, bJsonDeSerializer, clazz, addHook);
						break;
                    case "update":
						ProtocolHelper.update(response, payload, repository, bJsonDeSerializer, clazz, updateHook);
                        break;
                    case "delete":

                        response.setError(1, "Not implemented");
                        break;
					default:
						response.setError(1, "Invalid action");
				}
			}
		} catch (BJsonException e) {
			e.printStackTrace();
		}
		
		return response;
	}


}



