package org.menesty.tradeplatform.web.data.provider;

import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.IModel;
import org.menesty.tradeplatform.persistent.domain.Identifiable;
import org.menesty.tradeplatform.service.BaseService;
import org.menesty.tradeplatform.web.PlatformApplication;
import org.menesty.tradeplatform.web.data.model.PageableImpl;
import org.menesty.tradeplatform.web.util.EntityModelUtil;

import java.util.Iterator;

/**
 * User: Menesty
 * Date: 8/11/13
 * Time: 2:30 PM
 */
public abstract class SimpleDataProvider<T extends Identifiable> extends SortableDataProvider<T, String> {

    @Override
    public Iterator<T> iterator(long first, long count) {
        return getService(getServiceClass()).load(new PageableImpl(first, count)).iterator();
    }

    @Override
    public long size() {
        return getService(getServiceClass()).count();
    }

    @Override
    public IModel<T> model(T object) {
        return EntityModelUtil.getCompoundModel(object, getServiceClass());
    }

    public abstract <Service extends BaseService<T>> Class<Service> getServiceClass();


    protected <Service extends BaseService<T>> Service getService(Class<Service> serviceClass) {
        return PlatformApplication.get().getService(serviceClass);
    }
}
