package uk.co.labfour.cloud2.services.user.services;

import java.util.UUID;

import uk.co.labfour.cloud2.entity.identity.Identity;
import uk.co.labfour.cloud2.entity.primitive.SimpleObject;
import uk.co.labfour.cloud2.microservice.ServiceContext;
import uk.co.labfour.cloud2.microservice.ServiceVariables;
import uk.co.labfour.cloud2.persistence.nosql.GenericObjectHelper;
import uk.co.labfour.cloud2.persistence.nosql.GenericRepository;
import uk.co.labfour.cloud2.persistence.nosql.GenericDriverAdapter;
import uk.co.labfour.cloud2.persistence.nosql.mongodb.MongoDbAdapter;
import uk.co.labfour.cloud2.persistence.redis.RedisRepoCache;
import uk.co.labfour.cloud2.services.user.model.Activity;
import uk.co.labfour.cloud2.services.user.model.CommunicationChannels;
import uk.co.labfour.error.BException;

public class Repository extends GenericRepository {
	private ServiceContext si;
	
	
	private Repository(ServiceContext si) throws BException {
		this.si = si;

		log.i(instance, "Redis connection string " + si.getVar(ServiceVariables.REDIS_CONNECTION_STRING));
		RedisRepoCache redis = RedisRepoCache.getInstance();

		redis.connect(si.getVar(ServiceVariables.REDIS_CONNECTION_STRING));
		cache = redis;
		initDB();
		populateDB2();
	}

	public static Repository getInstance(ServiceContext si) throws BException {
		if (null == instance) {
			instance = new Repository(si);			
		}
	
		return (Repository)instance;
	}
	
	public void initDB() {
		String dbName = "activities";
		
		log.i(this, "MongoDB connection string " + si.getVar(ServiceVariables.MONGODB_CONNECTION_STRING));
		
		// User
		GenericDriverAdapter driverTmp = new MongoDbAdapter(si.getVar(ServiceVariables.MONGODB_CONNECTION_STRING), dbName, "users");
		GenericObjectHelper<Activity> userHelper = new GenericObjectHelper<Activity>(cache, driverTmp, Activity.class) {
			
			@Override
			public void addKeysHook() {
				addKey(DEFAULT_PRIMARY_KEY);

			}
			
			@Override
			public void initHook() {
				/*try {
					((MongoDbAdapter)getDriver()).createIndex(Market.MARKET_KEY, true);
				} catch (BException e) {
					e.printStackTrace();
				}*/
			}
			@Override
			public void del(Object obj) {
				Activity market = (Activity)obj;
				del(DEFAULT_PRIMARY_KEY, market.getUuidAsString());
				//del(Market.MARKET_KEY, market.getMarketKey());
			}
		};
		objectHelperCollection.put(Activity.class, userHelper);


		// Communication channels
		driverTmp = new MongoDbAdapter(si.getVar(ServiceVariables.MONGODB_CONNECTION_STRING), dbName, "CommunicationChannels");
		GenericObjectHelper<CommunicationChannels> commChHelper = new GenericObjectHelper<CommunicationChannels>(cache, driverTmp, CommunicationChannels.class) {

			@Override
			public void addKeysHook() {
				addKey(DEFAULT_PRIMARY_KEY);
			}

			@Override
			public void initHook() {
				/*try {
					((MongoDbAdapter)getDriver()).createIndex(Market.MARKET_KEY, true);
				} catch (BException e) {
					e.printStackTrace();
				}*/
			}

			@Override
			public void del(Object obj) {
				CommunicationChannels cmmCh = (CommunicationChannels)obj;
				del(DEFAULT_PRIMARY_KEY, cmmCh.getUuidAsString());
			}
		};
		objectHelperCollection.put(CommunicationChannels.class, commChHelper);


	}

	public void populateDB2() throws BException { }

	public <T> T load(String id, Class<T> T) throws BException {
		return load(GenericObjectHelper.DEFAULT_PRIMARY_KEY, id, T);
	}
	
	public <T> T load(Identity identity, Class<T> T) throws BException {
		return load(GenericObjectHelper.DEFAULT_PRIMARY_KEY, identity.getUuid().toString(), T);
	}
	
	public <T> T load(UUID uuid, Class<T> T) throws BException {
		return load(GenericObjectHelper.DEFAULT_PRIMARY_KEY, uuid.toString(), T);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T load(String keyName, String keyValue, Class<T> T) throws BException {
		return (T)objectHelperCollection.get(T).loadByKey(keyName, keyValue);
	}
	
	public Activity loadUser(String uuid) throws BException {
		return (Activity)objectHelperCollection.get(Activity.class).loadByID(uuid);
	}
	
	
	public void save(SimpleObject obj) throws BException {
		//Class<?> clazz = obj.getClass();
		
		GenericObjectHelper<?> goh = objectHelperCollection.get(obj.getClass());
		
		goh.save(obj, goh.getPrimaryKey(), obj.getUuidAsString());
		
	}

	@Override
	public void delete(SimpleObject obj) throws BException {
		GenericObjectHelper<?> goh = objectHelperCollection.get(obj.getClass());

		goh.delete(obj, goh.getPrimaryKey(), obj.getUuidAsString());
	}


	public <T> void save(Object obj, Class<T> T, String primaryKeyValue) throws BException {
		
		@SuppressWarnings("unchecked")
		GenericObjectHelper<?> goh = (GenericObjectHelper<T>)objectHelperCollection.get(obj.getClass());
		
		goh.save(obj, goh.getPrimaryKey(), primaryKeyValue);
	}
	

	
	
	
	
	
}
