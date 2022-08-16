package uk.co.labfour.cloud2.services.user.services;

import com.zerortt.pm.model.IdentityData;
import com.zerortt.pm.model.Note;
import com.zerortt.pm.model.Program;
import uk.co.labfour.cloud2.microservice.ServiceContext;
import uk.co.labfour.cloud2.microservice.ServiceVariables;
import uk.co.labfour.cloud2.persistence.nosql.*;
import uk.co.labfour.cloud2.persistence.nosql.mongodb.MongoDbAdapter;
import uk.co.labfour.cloud2.persistence.redis.RedisRepoCache;
import uk.co.labfour.cloud2.pmbok.jira.JiraContextV3;
import uk.co.labfour.cloud2.pmbok.jira.model.JiraIssue;
import uk.co.labfour.cloud2.pmbok.pm.PMEngine;
import uk.co.labfour.cloud2.pmbok.pm.model.*;
import uk.co.labfour.cloud2.services.user.model.Project;
import uk.co.labfour.cloud2.services.user.model.resource.Person;
import uk.co.labfour.error.BEarer;
import uk.co.labfour.error.BException;

import java.util.HashSet;
import java.util.concurrent.TimeUnit;

public class Repository extends GenericRepository2 {
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

        return (Repository) instance;
    }

    public void initDB() {
        GenericDriverAdapter driverTmp;

        /*String dbName = "activities";

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
				*//*try {
					((MongoDbAdapter)getDriver()).createIndex(Market.MARKET_KEY, true);
				} catch (BException e) {
					e.printStackTrace();
				}*//*
            }

            @Override
            public void del(Object obj) {
                Activity market = (Activity) obj;
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
                    ((MongoDbAdapter) getDriver()).createIndex("email", true);
                } catch (BException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void del(Object obj) {
                Contact contact = (Contact) obj;
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
				*//*try {
					((MongoDbAdapter)getDriver()).createIndex("email", true);
				} catch (BException e) {
					e.printStackTrace();
				}*//*
            }

            @Override
            public void del(Object obj) {
                Alarm alarm = (Alarm) obj;
                del(DEFAULT_PRIMARY_KEY, alarm.getUuidAsString());
                //del(Market.MARKET_KEY, market.getMarketKey());
            }
        };
        objectHelperCollection.put(Alarm.class, alarmHelper);


        driverTmp = new MongoDbAdapter(si.getVar(ServiceVariables.MONGODB_CONNECTION_STRING), dbName, "projects");
        GenericObjectHelper<Task> projectHelper = new GenericObjectHelper<Task>(cache, driverTmp, Task.class) {

            @Override
            public void addKeysHook() {
                addKey(DEFAULT_PRIMARY_KEY);

            }

            @Override
            public void initHook() {
				*//*try {
					((MongoDbAdapter)getDriver()).createIndex("email", true);
				} catch (BException e) {
					e.printStackTrace();
				}*//*
            }

            @Override
            public void del(Object obj) {
                Task project = (Task) obj;
                del(DEFAULT_PRIMARY_KEY, project.getUuidAsString());
                //del(Market.MARKET_KEY, market.getMarketKey());
            }
        };
        objectHelperCollection.put(Task.class, projectHelper);


        driverTmp = new MongoDbAdapter(si.getVar(ServiceVariables.MONGODB_CONNECTION_STRING), dbName, "skills");
        GenericObjectHelper<Skill> skillHelper = new GenericObjectHelper<Skill>(cache, driverTmp, Skill.class) {

            @Override
            public void addKeysHook() {
                addKey(DEFAULT_PRIMARY_KEY);

            }

            @Override
            public void initHook() {
				*//*try {
					((MongoDbAdapter)getDriver()).createIndex("email", true);
				} catch (BException e) {
					e.printStackTrace();
				}*//*
            }

            @Override
            public void del(Object obj) {
                Skill skill = (Skill) obj;
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
				*//*try {
					((MongoDbAdapter)getDriver()).createIndex("email", true);
				} catch (BException e) {
					e.printStackTrace();
				}*//*
            }

            @Override
            public void del(Object obj) {
                RoadMapElement roadMapElement = (RoadMapElement) obj;
                del(DEFAULT_PRIMARY_KEY, roadMapElement.getUuidAsString());
                //del(Market.MARKET_KEY, market.getMarketKey());
            }
        };
        objectHelperCollection.put(RoadMapElement.class, roadMapElemHelper);

        *//*
         * JIRA
         * *//*

        String jiraDBName = "jira";
        driverTmp = new MongoDbAdapter(si.getVar(ServiceVariables.MONGODB_CONNECTION_STRING), jiraDBName, "issues");
        GenericObjectHelper<JiraIssue> jiraIssueHelper = new GenericObjectHelper<JiraIssue>(cache, driverTmp, JiraIssue.class) {

            @Override
            public void addKeysHook() {
                addKey(JiraEngine.EXTERNAL_KEY);
            }

            @Override
            public void initHook() {
                try {
                    ((MongoDbAdapter) getDriver()).createIndex(JiraEngine.EXTERNAL_KEY, false);
                } catch (BException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void del(Object obj) {
                JiraIssue jiraIssue = (JiraIssue) obj;
                del(JiraEngine.EXTERNAL_KEY, jiraIssue.key);
                //del(Market.MARKET_KEY, market.getMarketKey());
            }
        };
        jiraIssueHelper.setPrimaryKey(JiraEngine.EXTERNAL_KEY);
        objectHelperCollection.put(JiraIssue.class, jiraIssueHelper);


        *//*
         * JIRA
         * *//*

        String pmDBName = "pm";
        driverTmp = new MongoDbAdapter(si.getVar(ServiceVariables.MONGODB_CONNECTION_STRING), pmDBName, "issues");
        GenericObjectHelper<Issue> pmIssueHelper = new GenericObjectHelper<Issue>(cache, driverTmp, Issue.class) {

            @Override
            public void addKeysHook() {
                addKey("extReference1.issueId");
                addKey("extReference1.projectId");
            }

            @Override
            public void initHook() {
                try {
//                    ((MongoDbAdapter) getDriver()).createIndex("_id", true);
                    ((MongoDbAdapter) getDriver()).createIndex("extReference1.issueId", true);
                    ((MongoDbAdapter) getDriver()).createIndex("extReference1.projectId", false);
                } catch (BException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void del(Object obj) {
                Issue issue = (Issue) obj;
                del("_id", issue.getPrimaryKey());
                //del(Market.MARKET_KEY, market.getMarketKey());
            }
        }
                .setPrimaryKey("_id");

        objectHelperCollection.put(Issue.class, pmIssueHelper);*/

        GenericObjectHelper2<JiraIssue> jiraissueHelper = new GenericObjectHelper2<JiraIssue>()
                .setDbDriver(new MongoDbAdapter(si.getVar(ServiceVariables.MONGODB_CONNECTION_STRING), "jira", "issues"))
                .setCacheDriver(cache)
                .setClass(JiraIssue.class)
                .setPrimaryKey(JiraIssue.PRIMARY_KEY, issue -> issue.getPK())
                .addIndex("_id", false, issue -> issue.id)
                .init(this.objectHelperCollection);

        GenericObjectHelper2<Issue> pmIssueHelper2 = new GenericObjectHelper2<Issue>()
                .setDbDriver(new MongoDbAdapter(si.getVar(ServiceVariables.MONGODB_CONNECTION_STRING), "pm", "issues"))
                .setCacheDriver(cache)
                .setClass(Issue.class)
                .setPrimaryKey(Issue.PRIMARY_KEY, issue -> issue.getPK())
                .addIndex(Issue.extSys1Reference1_ISSUEID, false, (issue) -> issue.extSys1Reference1.issueId)
                .addIndex(Issue.extSys1Reference1_PROJECTID, false, (issue) -> issue.extSys1Reference1.projectId)
                .addIndex(Issue.links_ISSUEID, false)
                .init(this.objectHelperCollection);

        GenericObjectHelper2<Person> pmContactHelper = new GenericObjectHelper2<Person>()
                .setDbDriver(new MongoDbAdapter(si.getVar(ServiceVariables.MONGODB_CONNECTION_STRING), "pm", "person"))
                .setCacheDriver(cache)
                .setClass(Person.class)
                .setPrimaryKey(Person.PRIMARY_KEY, person -> person.getPK())
                .addIndex(Person.EMAIL_ADDRESS_INDEX, false, (person) -> person.emailAddress)
                .addIndex(Person.EXTSYS1ID1_INDEX, false, (person) -> person.extSys1Id1)
                .init(this.objectHelperCollection);

        GenericObjectHelper2<Project> pmProject = new GenericObjectHelper2<Project>()
                .setDbDriver(new MongoDbAdapter(si.getVar(ServiceVariables.MONGODB_CONNECTION_STRING), "pm", "project"))
                .setCacheDriver(cache)
                .setClass(Project.class)
                .setPrimaryKey(Project.PRIMARY_KEY, project -> project.getUuidAsString())
                .init(this.objectHelperCollection);

        var pmIssueNew = new GenericObjectHelper2<com.zerortt.pm.model.Issue>()
                .setDbDriver(new MongoDbAdapter(si.getVar(ServiceVariables.MONGODB_CONNECTION_STRING), "pm", "issueNew"))
                .setCacheDriver(cache)
                .setClass(com.zerortt.pm.model.Issue.class)
                .setPrimaryKey(com.zerortt.pm.model.Issue.KEY, com.zerortt.pm.model.Issue::value)
                .init(this.objectHelperCollection);

        var pmProgramNew = new GenericObjectHelper2<com.zerortt.pm.model.Program>()
                .setDbDriver(new MongoDbAdapter(si.getVar(ServiceVariables.MONGODB_CONNECTION_STRING), "pm", "programNew"))
                .setCacheDriver(cache)
                .setClass(com.zerortt.pm.model.Program.class)
                .setPrimaryKey(Program.KEY, Program::value)
                .init(this.objectHelperCollection);

        var pmProjectNew = new GenericObjectHelper2<com.zerortt.pm.model.Project>()
                .setDbDriver(new MongoDbAdapter(si.getVar(ServiceVariables.MONGODB_CONNECTION_STRING), "pm", "projectNew"))
                .setCacheDriver(cache)
                .setClass(com.zerortt.pm.model.Project.class)
                .setPrimaryKey(com.zerortt.pm.model.Project.KEY, com.zerortt.pm.model.Project::value)
                .init(this.objectHelperCollection);

        var pmNoteNew = new GenericObjectHelper2<com.zerortt.pm.model.Note>()
                .setDbDriver(new MongoDbAdapter(si.getVar(ServiceVariables.MONGODB_CONNECTION_STRING), "pm", "NoteNew"))
                .setCacheDriver(cache)
                .setClass(com.zerortt.pm.model.Note.class)
                .setPrimaryKey(Note.KEY, Note::value)
                .init(this.objectHelperCollection);

    }

    public void populateDB2() throws BException {
        JiraContextV3 context = new JiraContextV3(
                si.getVar("JIRA_V3_ENDPOINT"),
                si.getVar("JIRA_V3_USER"),
                si.getVar("JIRA_V3_APIKEY"));

        var pmEngine = new PMEngine(this);

        var programId = "d3e30571-57c2-4baf-8ef2-40f58feb6611";
        var program = new Program(programId);
        program.name = "test program";
        save(program);

        var projectId = "e46024a4-5c2b-4a56-a5f2-34f85fcf8541";
        var project = new com.zerortt.pm.model.Project(projectId);
        project.name = "test project";
        project.programs.add(program.identityData());
        save(project);

        var issueId2 = "7498a026-b0b4-451a-9f09-4dc4042137d2";
        var issue2 = new com.zerortt.pm.model.Issue(issueId2);
        issue2.name = "test new issue";
        issue2.programs.add(program.identityData());
        issue2.projects.add(project.identityData());
        save(issue2);

        var noteId = "ddf26f2f-5a83-445e-b80a-6d0874cf33ae";
        var note = new Note(noteId);
        note.name = "this is a note";
        note.programs.add(program.identityData());
        note.projects.add(project.identityData());
        note.issues.add(issue2.identityData());
        save(note);

        noteId = "07f51da7-d0a2-4b2b-a7aa-da4c06590609";
        note = new Note(noteId);
        note.name = "this is another note";
        note.programs.add(program.identityData());
        note.projects.add(project.identityData());
        save(note);

        var idata = new IdentityData(Note.class.getCanonicalName(), "projects.value", projectId);
        var notes = pmEngine.find(idata, Note.class);




        /*Person mike = new Person();

        mike.setPK("zapotek6@gmail.com");
        mike.displayName = "Mike the Bike";
        mike.emailAddress = mike.getPK();
        mike.extSys1Id1 = "mirko.bonadei";
        save(mike);

        mike.setPK("allan.taschini@comelit.it");
        mike.displayName = "Allan";
        mike.emailAddress = mike.getPK();
        mike.extSys1Id1 = "allan.taschini";
        save(mike);

        mike.setPK("emanuele.panigati@kalpa.it");
        mike.displayName = "Emanuele";
        mike.emailAddress = mike.getPK();
        mike.extSys1Id1 = "emanuele.panigati";
        save(mike);

        mike.setPK("mauro.tintori@comelit.it");
        mike.displayName = "Mauro";
        mike.emailAddress = mike.getPK();
        mike.extSys1Id1 = "mauro.tintori";
        save(mike);

        mike.setPK("andrea.invernizzi@comelit.it");
        mike.displayName = "Andrea";
        mike.emailAddress = mike.getPK();
        mike.extSys1Id1 = "andrea.invernizzi";
        save(mike);

        mike.setPK("monica.magoni@comelit.it");
        mike.displayName = "Monica";
        mike.emailAddress = mike.getPK();
        mike.extSys1Id1 = "monica.magoni";
        save(mike);

        mike.setPK("dario.crona@comelit.it");
        mike.displayName = "Dario";
        mike.emailAddress = mike.getPK();
        mike.extSys1Id1 = "dario.corna";
        save(mike);

        mike.setPK("giuseppe.palese@comelit.it");
        mike.displayName = "Giuseppe";
        mike.emailAddress = mike.getPK();
        mike.extSys1Id1 = "giuseppe.palese";
        save(mike);

        mike.setPK("stefano.quartana@comelit.it");
        mike.displayName = "Stefano";
        mike.emailAddress = mike.getPK();
        mike.extSys1Id1 = "stefano.quartana";
        save(mike);

        mike.setPK("andrea.mutti@comelit.it");
        mike.displayName = "Andrea Mutti";
        mike.emailAddress = mike.getPK();
        mike.extSys1Id1 = "andrea.mutti";
        save(mike);

        mike.setPK("paolo.ferrari@comelit.it");
        mike.displayName = "Paolo Ferrari";
        mike.emailAddress = mike.getPK();
        mike.extSys1Id1 = "paolo.ferrrari";
        save(mike);

        mike.setPK("stefano.achenza@comelit.it");
        mike.displayName = "Stefano";
        mike.emailAddress = mike.getPK();
        mike.extSys1Id1 = "stefano.achenza";
        save(mike);

        mike.setPK("giovanni.dicairano@comelit.it");
        mike.displayName = "Giovanni";
        mike.emailAddress = mike.getPK();
        mike.extSys1Id1 = "giovanni.dicairano";
        save(mike);

        mike.setPK("walter.dalmut@corley.it");
        mike.displayName = "Walter";
        mike.emailAddress = mike.getPK();
        mike.extSys1Id1 = "walter.dalmut";
        save(mike);

        mike.setPK("roberto.vitali.qua@comelit.it");
        mike.displayName = "RobertoQ";
        mike.emailAddress = mike.getPK();
        mike.extSys1Id1 = "roberto.vitali.qua";
        save(mike);

        context.setRemoveScreenProtectedFieldsFromIssueHandle(issue -> {
            issue.fields.creator = null;
            issue.fields.updated = null;
            issue.fields.created = null;
            issue.fields.status = null;
            issue.fields.aggregatetimeoriginalestimate = null;
            issue.fields.aggregatetimeestimate = null;
            issue.fields.aggregateprogress = null;
            issue.fields.aggregatetimespent = null;
            issue.fields.timeoriginalestimate = null;
            issue.fields.timeestimate = null;
            issue.fields.timespent = null;
            issue.fields.progress = null;
            issue.fields.workratio = null;
            issue.fields.timetracking = null;
            issue.fields.subtasks = null;

            return issue;
        });

        var jiraEngine = new JiraEngine(context);
        var pmEngine = new PMEngine(this);
        var jiraToPM = new JiraToPM(this, jiraEngine, pmEngine);

        var findIssue = pmEngine.findIssue("a80c14f0-e47e-49c3-a99c-b99e6f115d23");

        GenericFilter filter = null;
        findIssue = pmEngine.findIssue(filter);

        var renameKitOP = pmEngine.createStory("supply a DVR Kit for development",
                "stefano.achenza@comelit.it",
                "2020-02-24T01:00:00.000+0100",
                "2020-02-25T23:59:00.000+0100");

        var developOP = pmEngine.createStory("develop & test fwu on new DVR",
                "monica.magoni@comelit.it",
                "2020-02-24T01:00:00.000+0100",
                "2020-02-25T23:59:00.000+0100");

        pmEngine.linkFinishToStart(developOP.get(), renameKitOP.get());

        var validationOP = pmEngine.createStory("Q&A validation",
                "roberto.vitali.qua@comelit.it",
                "2020-02-24T01:00:00.000+0100",
                "2020-02-25T23:59:00.000+0100");

        pmEngine.linkFinishToStart(renameKitOP.get(), validationOP.get());

        var prepareProductionEnvOP = pmEngine.createStory("Prepare Production env",
                "monica.magoni@comelit.it",
                "2020-02-24T01:00:00.000+0100",
                "2020-02-25T23:59:00.000+0100");

        pmEngine.linkFinishToStart(validationOP.get(), prepareProductionEnvOP.get());

        var betaProgramOP = pmEngine.createStory("Beta program",
                "stefano.achenza@comelit.it",
                "2020-02-24T01:00:00.000+0100",
                "2020-02-25T23:59:00.000+0100");

        pmEngine.linkFinishToStart(prepareProductionEnvOP.get(), betaProgramOP.get());

        var finalValidationOP = pmEngine.createStory("Final Validation",
                "roberto.vitalia.qua@comelit.it",
                "2020-02-24T01:00:00.000+0100",
                "2020-02-25T23:59:00.000+0100");

        pmEngine.linkFinishToStart(validationOP.get(), finalValidationOP.get());


//        var extref = new ExternalReference();
//        extref.issueId = ""
//        createTaskOpe.get().extSys1Reference1 = ;


//        createTaskOpe.get().waitingForLinks();
        var saveOp = pmEngine.save();*/

        var issueId = "f6e65a8a-18c0-4651-abd2-50a717407af3";

        var findIssue = pmEngine.findIssue("");

        if (findIssue.isOk() && findIssue.get().isEmpty()) {
            var issue = pmEngine.createIssue(issueId, "Comitato Sicurezza", IssueType.EPIC, IssueStatus.INPROGRESS, new HashSet<>(),
                    new ExternalReference(), "mirko.bonadei@comelit.it", "mirko.bonadei@comelit.it",
                    "2020-02-24T01:00:00.000+0100", "", "2020-02-24T01:00:00.000+0100",
                    false, false, true, false, false, new Review());

            if (issue.isOkAndNotNull()) {
                pmEngine.addToDirty(issue.get());
            }
        }

        var issues = pmEngine.findIssueByLinkedIssue("98f91070-d665-4768-9be0-3dfe5cd24e44");

        if (issues.isOk()) {
            issues.get().forEach(issue -> {
                if (issue.timeTracking.unit == TimeUnit.MINUTES) {
                    issue.timeTracking.estimate = 100;
                }

                System.out.println(issue._id);
            });
        }

        var saveOp = pmEngine.save();
/*
//        test project key = TES
        BEarer<List<JiraIssue>> jiraIssues = jiraEngine.getTasks("TES");

        if (jiraIssues.isOkAndNotNull()) {
            jiraIssues.get().forEach(jiraIssue -> {
                save(jiraIssue);
                Issue issue = jiraToPM.createIssueFromJiraIssue(jiraIssue);
                pmEngine.updateIssueStatus(issue);
                pmEngine.save();
//                save(issue);
            });
        }*/

		/*BEarer<JiraIssue> getTaskOpe = jira.getTask("TES-1");

		if (getTaskOpe.isOkAndNotNull()) {
			System.out.println("Task project key: " + getTaskOpe.get().fields.project.key);

			JiraIssue issue = getTaskOpe.get();

			save(issue);

			issue = context.removeScreenProtectedFieldsFromIssue(issue);
			BEarer updateOpe = jira.updateTask(issue);

			if (updateOpe.isOk()) {
				System.out.println("Update OK");
			} else {
				System.out.println("Update ERR " + updateOpe.getDescription());
			}

		}*/


    }

    /*public <T> T load(String id, Class<T> T) throws BException {
        String keyName = objectHelperCollection.get(T).getPrimaryKey();
        return load(keyName, id, T);
//		return load(GenericObjectHelper.DEFAULT_PRIMARY_KEY, id, T);
    }

    public <T> T load(Identity identity, Class<T> T) throws BException {
        return load(GenericObjectHelper.DEFAULT_PRIMARY_KEY, identity.getUuid().toString(), T);
    }

    public <T> T load(UUID uuid, Class<T> T) throws BException {
        return load(GenericObjectHelper.DEFAULT_PRIMARY_KEY, uuid.toString(), T);
    }

    public Activity loadUser(String uuid) throws BException {
        return (Activity) objectHelperCollection.get(Activity.class).loadByID(uuid);
    }

    @SuppressWarnings("unchecked")
	*//*public <T> T load( String keyValue, Class<T> T) throws BException {
		String keyName = objectHelperCollection.get(T).getPrimaryKey();
		return (T)objectHelperCollection.get(T).loadByKey(keyName, keyValue);
	}*//*


    public <T> T load(String keyName, String keyValue, Class<T> T) throws BException {
        return (T) objectHelperCollection.get(T).loadByKey(keyName, keyValue);
    }


    public void save(SimpleObject obj) throws BException {
        //Class<?> clazz = obj.getClass();

        GenericObjectHelper<?> goh = objectHelperCollection.get(obj.getClass());
        goh.save(obj, goh.getPrimaryKey(), obj.getUuidAsString());

    }

    public BEarer save(IZeroObject obj) {
        GenericObjectHelper<?> goh = objectHelperCollection.get(obj.getClass());
        try {
            goh.save(obj, obj.getPrimaryKeyName(), obj.getPrimaryKey());
            return BEarer.createSuccess();
        } catch (BException e) {
            return BEarer.createGenericError(this.getClass().getCanonicalName(), e.getMessage());
        }

    }

    @Override
    public void delete(SimpleObject obj) throws BException {
        GenericObjectHelper<?> goh = objectHelperCollection.get(obj.getClass());

        goh.delete(obj, goh.getPrimaryKey(), obj.getUuidAsString());
    }

    public <T> void save(Object obj, Class<T> T, String primaryKeyValue) throws BException {

        @SuppressWarnings("unchecked")
        GenericObjectHelper<?> goh = (GenericObjectHelper<T>) objectHelperCollection.get(obj.getClass());

        goh.save(obj, goh.getPrimaryKey(), primaryKeyValue);
    }

    public <T> BEarer<GenericFilter> buildFilterPerUuid(String uuid, Repository repository, Class<T> clazz) {

        return buildFilterSingleField(GenericObjectHelper.DEFAULT_PRIMARY_KEY, uuid, repository, clazz);
    }

    */

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
