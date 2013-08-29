package org.menesty.tradeplatform.web.data.provider;

import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.menesty.tradeplatform.persistent.domain.Identifiable;
import org.menesty.tradeplatform.service.BaseService;
import org.menesty.tradeplatform.web.data.model.EntityLoadableDetachableModel;
import org.menesty.tradeplatform.web.data.model.PageableImpl;
import org.springframework.data.domain.Page;

import java.util.Iterator;

/**
 * User: Menesty
 * Date: 8/11/13
 * Time: 2:30 PM
 */
public abstract class SimpleDataProvider<T extends Identifiable> extends SortableDataProvider<T, String> {

    @Override
    public Iterator<T> iterator(long first, long count) {
        return getService().load(new PageableImpl((int) first, (int) count)).iterator();
    }

    @Override
    public long size() {
        return getService().count();
    }

    @Override
    public IModel<T> model(T object) {
        return new CompoundPropertyModel<>(new EntityLoadableDetachableModel<T>(object) {
            @Override
            protected T load() {
                return getService().loadById(getId());
            }
        });
    }

    public abstract BaseService<T> getService();
}
