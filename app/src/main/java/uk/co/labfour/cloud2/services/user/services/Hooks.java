package uk.co.labfour.cloud2.services.user.services;

import uk.co.labfour.bjson.BJsonDeSerializer;
import uk.co.labfour.bjson.BJsonObject;
import uk.co.labfour.cloud2.persistence.nosql.GenericFilter;
import uk.co.labfour.error.BEarer;

public abstract class Hooks<T> {

    public abstract BEarer<GenericFilter> prepareFilter(Repository repository, BJsonDeSerializer bJsonDeSerializer, BJsonObject payload, Class<T> clazz);

    public abstract BEarer pre(T object);

    public abstract BEarer post(T object);

}
