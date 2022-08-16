package com.zerortt.pm;

import com.zerortt.pm.model.*;

import uk.co.labfour.cloud2.persistence.nosql.GenericFilter;
import uk.co.labfour.cloud2.services.user.services.Repository;
import uk.co.labfour.error.BEarer;
import uk.co.labfour.logger.MyLogger;
import uk.co.labfour.logger.MyLoggerFactory;
import uk.co.labfour.util.datetime.Iso8601;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

public class PMEngine {
    private Repository repository;
    private ConcurrentHashMap<String, Issue> dirtyIssues;
    private MyLogger logger;

    public PMEngine(Repository repository) {
        this.dirtyIssues = new ConcurrentHashMap<>();
        this.repository = repository;
        this.logger = MyLoggerFactory.getInstance();
    }

//    public BEarer addToDirty(Issue issue) {
//        dirtyIssues.put(issue.getId(), issue);
//        return BEarer.createSuccess();
//    }

    public BEarer save() {
        AtomicReference<BEarer> saveOp = new AtomicReference<>();
        dirtyIssues.forEach((issueId, issue) -> {
            saveOp.set(repository.save(issue));
            if (saveOp.get().isOk()) {
                dirtyIssues.remove(issueId);
            }
        });

        return saveOp.get();
    }

    public BEarer<Vector<Issue>> findIssue(IdentityData idata) {
        var filterBuilder = repository.getFilterBuilder(Issue.class);

        if (filterBuilder.isOk()) {
            var filter = filterBuilder.get().eq(idata.getKey(), idata.getValue());

            return repository.read(filter, Issue.class);
        } else {
            return BEarer.createGenericError(PMEngine.class.getCanonicalName(), filterBuilder.getDescription());
        }
    }

    public <T> BEarer<Vector<T>> find(IdentityData idata, Class<T> classOfT) {
        var filterBuilder = repository.getFilterBuilder(classOfT);

        if (filterBuilder.isOk()) {
            var filter = filterBuilder.get().eq(idata.getKey(), idata.getValue());

            return repository.read(filter, classOfT);
        } else {
            return BEarer.createGenericError(PMEngine.class.getCanonicalName(), filterBuilder.getDescription());
        }
    }

    public BEarer<Vector<Issue>> findIssue(GenericFilter filter) {

        return repository.read(filter, Issue.class);
    }

}
