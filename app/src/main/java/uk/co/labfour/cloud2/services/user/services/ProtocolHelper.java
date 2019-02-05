package uk.co.labfour.cloud2.services.user.services;

import uk.co.labfour.bjson.*;
import uk.co.labfour.cloud2.entity.identity.Identity;
import uk.co.labfour.cloud2.entity.primitive.IBaseObject;
import uk.co.labfour.cloud2.persistence.nosql.GenericFilter;
import uk.co.labfour.cloud2.protocol.BaseResponse;
import uk.co.labfour.cloud2.protocol.Constants;
import uk.co.labfour.error.BEarer;
import uk.co.labfour.error.BException;

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
            if (payload.has(Constants.FIELD_UUID)) {
                String uuid = payload.getElementAsString(Constants.FIELD_UUID);

                filterOp = repository.buildFilterPerUuid(uuid, repository, clazz);

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
                    repository.save(object, clazz, object.getUuidAsString());
                    identities.add(object.getIdentity());
                } else {
                    repository.save(object, clazz, object.getUuidAsString());
                    identities.add(object.getIdentity());
                }

            }

            response.getPayload().put(Constants.FIELD_RESULTS, bJsonDeSerializer.toJson(identities));

        } catch (BException | BJsonException e) {
            return BEarer.createGenericError(ProtocolHelper.class.toString(), e.getMessage());
        }

        return BEarer.createSuccess();
    }


    public static <T> BEarer<BJsonElement> loadAsJson(Repository repository, GenericFilter filter, BJsonDeSerializer bJsonDeSerializer, Class<T> clazz) {

        try {

            BEarer<Vector<T>> queryEntitiesOp = repository.load(filter, clazz);

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

}
