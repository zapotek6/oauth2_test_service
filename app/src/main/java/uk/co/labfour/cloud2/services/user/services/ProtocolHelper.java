package uk.co.labfour.cloud2.services.user.services;

import uk.co.labfour.bjson.*;
import uk.co.labfour.cloud2.entity.identity.Identity;
import uk.co.labfour.cloud2.entity.primitive.IBaseObject;
import uk.co.labfour.cloud2.entity.primitive.IPrimaryKey;
import uk.co.labfour.cloud2.persistence.nosql.GenericFilter;
import uk.co.labfour.cloud2.persistence.nosql.GenericObjectHelper2;
import uk.co.labfour.cloud2.persistence.nosql.GenericRepository2;
import uk.co.labfour.cloud2.protocol.BaseResponse;
import uk.co.labfour.cloud2.protocol.Constants;
import uk.co.labfour.error.BEarer;

import java.util.Vector;

import static uk.co.labfour.cloud2.protocol.Constants.FIELD_ELEMS;

public class ProtocolHelper {

    public static <T> void get(BaseResponse response, Repository repository, BJsonDeSerializer bJsonDeSerializer, BJsonObject payload, Class<T> clazz, Hooks<T> cb) throws BJsonException {
        BEarer<BJsonElement> queryOp;
        GenericFilter filter = null;
        BEarer<GenericFilter> filterOp;

        if (null != cb) {
            filterOp = cb.prepareFilter(repository, bJsonDeSerializer, payload, clazz);
            if (filterOp.isOk()) {
                filter = filterOp.get();
            } else {
                response.setError(500, filterOp.getDescription());
                return;
            }
        } else {
            String primaryKeyName = repository.getGenericObjectHelper(clazz).get().getPrimaryKey();
            if (payload.has(primaryKeyName)) {
                String primarykeyValue = payload.getElementAsString(primaryKeyName);

                filterOp = repository.buildFilterSingleField(primaryKeyName, primarykeyValue, repository, clazz);

                if (filterOp.isOk()) {
                    filter = filterOp.get();
                }
            }
        }

        queryOp = loadAsJson(repository,
                filter,
                bJsonDeSerializer,
                clazz);

        if (queryOp.isOk()) {
            response.setPayload(queryOp.get().getAsBJsonObject());
        }

    }


    public static <T extends IBaseObject> BEarer update(BaseResponse response, BJsonObject payload, Repository repository, BJsonDeSerializer bJsonDeSerializer, Class<T> clazz, Hooks<T> cb) {

        Vector<Identity> identities = new Vector<>();
        BJsonArray jArray = null;

        try {
            jArray = payload.getElementAsBJsonArray(Constants.FIELD_ELEMS);


            for (BJsonElement jArrayElement: jArray) {
                BJsonObject jObject = jArrayElement.getAsBJsonObject();

                T object = bJsonDeSerializer.fromJson(jObject, clazz);

                if (null != cb && cb.pre(object).isOk()) {
                    repository.save(object);
                    identities.add(object.getIdentity());
                } else {
                    repository.save(object);
                    identities.add(object.getIdentity());
                }
            }

            response.getPayload().put(Constants.FIELD_RESULTS, bJsonDeSerializer.toJson(identities));

        } catch (BJsonException e) {
            return BEarer.createGenericError(ProtocolHelper.class.toString(), e.getMessage());
        }

        return BEarer.createSuccess();
    }

    public static <T> BEarer update2(BaseResponse response, BJsonObject payload, Repository repository, BJsonDeSerializer bJsonDeSerializer, Class<T> clazz, Hooks<T> cb) {

        Vector<String> identities = new Vector<>();
        BJsonArray jArray = null;

        try {
            jArray = payload.getElementAsBJsonArray(Constants.FIELD_ELEMS);


            for (BJsonElement jArrayElement: jArray) {
                BJsonObject jObject = jArrayElement.getAsBJsonObject();

                T object = bJsonDeSerializer.fromJson(jObject, clazz);

                if (null != cb && cb.pre(object).isOk()) {
                    ProtocolHelper.addIdentities(repository, object, identities, clazz);
                    repository.save(object);
                } else {
                    ProtocolHelper.addIdentities(repository, object, identities, clazz);
                    repository.save(object);
                }
            }

            response.getPayload().put(Constants.FIELD_RESULTS, bJsonDeSerializer.toJson(identities));

        } catch (BJsonException e) {
            return BEarer.createGenericError(ProtocolHelper.class.toString(), e.getMessage());
        }

        return BEarer.createSuccess();
    }


    public static <T> BEarer<BJsonElement> loadAsJson(Repository repository, GenericFilter filter, BJsonDeSerializer bJsonDeSerializer, Class<T> clazz) {

        try {

            BEarer<Vector<T>> queryEntitiesOp = repository.read(filter, clazz);

            if (queryEntitiesOp.isOk()) {
                BJsonElement elm = bJsonDeSerializer.toJsonTree(queryEntitiesOp.get());
                BJsonObject obj = new BJsonObject();
                obj.put(FIELD_ELEMS, elm.getAsBJsonArray());

                return new BEarer<BJsonElement>().setSuccess().set(obj);
            } else {
                return BEarer.createGenericError(ProtocolHelper.class.toString(), queryEntitiesOp.getDescription());
            }

        } catch (BJsonException e) {
            return BEarer.createGenericError(ProtocolHelper.class.toString(), e.getMessage());
        }
    }

    public static BEarer<String> getPK(Repository repository, Object object, Class<?> clazz) {
        var goh = repository.getGenericObjectHelper(clazz);
        if (goh.isOk()) {
            var helper = goh.get();
            BEarer<String> pk = helper.readIndex(object, helper.getPrimaryKey(), clazz);
            if (pk.isOk()) {
                return new BEarer<String>()
                        .set(pk.get())
                        .setSuccess();
            } else {
                return pk;
            }
        } else {
            return BEarer.createGenericError(ProtocolHelper.class.getClass().getCanonicalName(), goh.getDescription(), String.class);
        }
    }

    public static void addIdentities(Repository repository, Object object, Vector<String> identities, Class<?> clazz) {
        var pk = ProtocolHelper.getPK(repository, object, clazz);
        if (pk.isOk()) {
            identities.add(pk.get());
        }
    }
}
