package uk.co.labfour.cloud2.services.user.services;

import uk.co.labfour.bjson.BJsonException;
import uk.co.labfour.bjson.BJsonObject;
import uk.co.labfour.cloud2.aaa.common.IService;
import uk.co.labfour.cloud2.aaa_client.AAAOperations;
import uk.co.labfour.cloud2.microservice.ServiceContext;
import uk.co.labfour.cloud2.protocol.BaseRequest;
import uk.co.labfour.cloud2.protocol.BaseResponse;
import uk.co.labfour.cloud2.services.user.model.Activity;
import uk.co.labfour.error.BException;
import uk.co.labfour.logger.MyLogger;
import uk.co.labfour.logger.MyLoggerFactory;

import java.util.UUID;

public class Core implements IService {
	public static final String USER_NAME_FLD = "name";
	public static final String USER_DESCRIPTION_FLD = "description";
    public static final String USER_EMAIL_FLD = "email";
	public static final String USER_UID_FLD = "uid";
    public static final String USER_PWD_FLD = "pwd";
    public static final String USER_GENDER_FLD = "gender";
    public static final String USER_SURNAME_FLD = "surname";
    public static final String ENTITY_UUID_FLD = "entityUuid";
	
	//MongoDbAdapter db;
	MyLogger log = MyLoggerFactory.getInstance();
	ServiceContext serviceContext;
	
	String dbName;
	String collectionName;
	
	private void setup() { }
	
	public Core(ServiceContext serviceContext) throws BException {
		this.serviceContext = serviceContext;
		
		setup();
		Repository.getInstance(serviceContext);
	}
	
	public BaseResponse doExec(BaseRequest request) throws BException {
		return doGetMarketCatalog(request);
	}




	public BaseResponse doGetMarketCatalog(BaseRequest request) throws BException {
		
		BaseResponse response =  new BaseResponse(request);

		try {
		    BJsonObject payload = request.getPayload();
			if (payload.has(USER_NAME_FLD) && payload.has(USER_UID_FLD) && payload.has(USER_PWD_FLD) && payload.has(USER_EMAIL_FLD)) {

			    String name = payload.getElementAsString(USER_NAME_FLD);
                String email = payload.getElementAsString(USER_EMAIL_FLD);
                String uid = payload.getElementAsString(USER_UID_FLD);
                String pwd = payload.getElementAsString(USER_PWD_FLD);

                BaseResponse entityCreationResponse = AAAOperations.createComplexEntityForUser(serviceContext, name, uid, pwd);

                if (!entityCreationResponse.containsError()) {
                    String entityUuid = entityCreationResponse.getPayload().getElementAsString(ENTITY_UUID_FLD);

                    Activity activity = new Activity(UUID.fromString(entityUuid));


                    response.getPayload().put(ENTITY_UUID_FLD, entityUuid);
                } else {
                    response.setError(entityCreationResponse.getErrCode(), entityCreationResponse.getErrDescription());
                }
            } else {
                new BException("Missing data");
            }

		} catch (BException | BJsonException e) {
			e.printStackTrace();
		}
		
		return response;
	}
}
