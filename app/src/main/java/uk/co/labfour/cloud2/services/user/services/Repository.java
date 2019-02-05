package uk.co.labfour.cloud2.services.user.services;

import uk.co.labfour.cloud2.entity.identity.Identity;
import uk.co.labfour.cloud2.entity.primitive.SimpleObject;
import uk.co.labfour.cloud2.microservice.ServiceContext;
import uk.co.labfour.cloud2.microservice.ServiceVariables;
import uk.co.labfour.cloud2.persistence.nosql.*;
import uk.co.labfour.cloud2.persistence.nosql.mongodb.MongoDbAdapter;
import uk.co.labfour.cloud2.persistence.redis.RedisRepoCache;
import uk.co.labfour.cloud2.services.user.model.*;
import uk.co.labfour.error.BEarer;
import uk.co.labfour.error.BException;

import java.util.UUID;

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
		
		// Activities
		GenericDriverAdapter driverTmp = new MongoDbAdapter(si.getVar(ServiceVariables.MONGODB_CONNECTION_STRING), dbName, "activities");
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


		driverTmp = new MongoDbAdapter(si.getVar(ServiceVariables.MONGODB_CONNECTION_STRING), dbName, "contact");
		GenericObjectHelper<Activity> contactHelper = new GenericObjectHelper<Activity>(cache, driverTmp, Contact.class) {

			@Override
			public void addKeysHook() {
				addKey(DEFAULT_PRIMARY_KEY);

			}

			@Override
			public void initHook() {
				try {
					((MongoDbAdapter)getDriver()).createIndex("email", true);
				} catch (BException e) {
					e.printStackTrace();
				}
			}
			@Override
			public void del(Object obj) {
				Contact contact = (Contact)obj;
				del(DEFAULT_PRIMARY_KEY, contact.getUuidAsString());
				//del(Market.MARKET_KEY, market.getMarketKey());
			}
		};
		objectHelperCollection.put(Contact.class, contactHelper);

		driverTmp = new MongoDbAdapter(si.getVar(ServiceVariables.MONGODB_CONNECTION_STRING), dbName, "alarm");
		GenericObjectHelper<Activity> alarmHelper = new GenericObjectHelper<Activity>(cache, driverTmp, Alarm.class) {

			@Override
			public void addKeysHook() {
				addKey(DEFAULT_PRIMARY_KEY);

			}

			@Override
			public void initHook() {
				/*try {
					((MongoDbAdapter)getDriver()).createIndex("email", true);
				} catch (BException e) {
					e.printStackTrace();
				}*/
			}
			@Override
			public void del(Object obj) {
				Alarm alarm = (Alarm)obj;
				del(DEFAULT_PRIMARY_KEY, alarm.getUuidAsString());
				//del(Market.MARKET_KEY, market.getMarketKey());
			}
		};
		objectHelperCollection.put(Alarm.class, alarmHelper);


		driverTmp = new MongoDbAdapter(si.getVar(ServiceVariables.MONGODB_CONNECTION_STRING), dbName, "projects");
		GenericObjectHelper<Project> projectHelper = new GenericObjectHelper<Project>(cache, driverTmp, Project.class) {

			@Override
			public void addKeysHook() {
				addKey(DEFAULT_PRIMARY_KEY);

			}

			@Override
			public void initHook() {
				/*try {
					((MongoDbAdapter)getDriver()).createIndex("email", true);
				} catch (BException e) {
					e.printStackTrace();
				}*/
			}
			@Override
			public void del(Object obj) {
				Project project = (Project)obj;
				del(DEFAULT_PRIMARY_KEY, project.getUuidAsString());
				//del(Market.MARKET_KEY, market.getMarketKey());
			}
		};
		objectHelperCollection.put(Project.class, projectHelper);
		

		driverTmp = new MongoDbAdapter(si.getVar(ServiceVariables.MONGODB_CONNECTION_STRING), dbName, "skills");
		GenericObjectHelper<Skill> skillHelper = new GenericObjectHelper<Skill>(cache, driverTmp, Skill.class) {

			@Override
			public void addKeysHook() {
				addKey(DEFAULT_PRIMARY_KEY);

			}

			@Override
			public void initHook() {
				/*try {
					((MongoDbAdapter)getDriver()).createIndex("email", true);
				} catch (BException e) {
					e.printStackTrace();
				}*/
			}
			@Override
			public void del(Object obj) {
				Skill skill = (Skill)obj;
				del(DEFAULT_PRIMARY_KEY, skill.getUuidAsString());
				//del(Market.MARKET_KEY, market.getMarketKey());
			}
		};
		objectHelperCollection.put(Skill.class, skillHelper);

		driverTmp = new MongoDbAdapter(si.getVar(ServiceVariables.MONGODB_CONNECTION_STRING), dbName, "roadpmaps");
		GenericObjectHelper<RoadMapElement> roadMapElemHelper = new GenericObjectHelper<RoadMapElement>(cache, driverTmp, RoadMapElement.class) {

			@Override
			public void addKeysHook() {
				addKey(DEFAULT_PRIMARY_KEY);

			}

			@Override
			public void initHook() {
				/*try {
					((MongoDbAdapter)getDriver()).createIndex("email", true);
				} catch (BException e) {
					e.printStackTrace();
				}*/
			}
			@Override
			public void del(Object obj) {
				RoadMapElement roadMapElement = (RoadMapElement)obj;
				del(DEFAULT_PRIMARY_KEY, roadMapElement.getUuidAsString());
				//del(Market.MARKET_KEY, market.getMarketKey());
			}
		};
		objectHelperCollection.put(RoadMapElement.class, roadMapElemHelper);
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

	public <T> BEarer<GenericFilter> buildFilterPerUuid(String uuid, Repository repository, Class<T> clazz) {

		return buildFilterSingleField(GenericObjectHelper.DEFAULT_PRIMARY_KEY, uuid, repository, clazz);
	}

	public <T> BEarer<GenericFilter> buildFilterSingleField(String fieldName, String fieldValue, Repository repository, Class<T> clazz) {

		BEarer<GenericFilterBuilder> filterBuilderOp = repository.getFilterBuilder(clazz);
		if (filterBuilderOp.isOk()) {
			GenericFilter filter = filterBuilderOp.get().eq(fieldName, fieldValue);
			return new BEarer<GenericFilter>().setSuccess().set(filter);
		} else {
			return BEarer.createGenericError(this, filterBuilderOp.getDescription());
		}
	}
}
