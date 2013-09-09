package org.menesty.tradeplatform.web.util;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.menesty.tradeplatform.persistent.domain.Catalog;
import org.menesty.tradeplatform.persistent.domain.Identifiable;
import org.menesty.tradeplatform.service.BaseService;
import org.menesty.tradeplatform.web.PlatformApplication;
import org.menesty.tradeplatform.web.data.model.EntityLoadableDetachableModel;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Menesty
 * Date: 8/24/13
 * Time: 10:53 AM
 */
public class EntityModelUtil {

    public static <T extends Identifiable, S extends BaseService<T>> IModel<T> getCompoundModel(T entity, final Class<S> service) {
        if (entity == null) return new AbstractReadOnlyModel<T>(){
            @Override
            public T getObject() {
                return null;
            }
        };
        if (entity.getId() != null)
            return new CompoundPropertyModel<T>(new EntityLoadableDetachableModel<T>(entity) {
                @Override
                protected T load() {
                    return PlatformApplication.get().getService(service).loadById(getId());
                }

            }){
                @Override
                public boolean equals(Object o) {
                    if (this == o) return true;
                    if (o == null || getClass() != o.getClass()) return false;
                    CompoundPropertyModel<T> other = (CompoundPropertyModel<T>) o;
                    return new EqualsBuilder().append(getObject().getId(), other.getObject().getId()).isEquals();
                }

                @Override
                public int hashCode() {
                    return getObject().getId().hashCode();
                }
            };

        return new CompoundPropertyModel<>(entity);
    }

    public static <T extends Identifiable, S extends BaseService<T>> List<IModel<T>> getCompoundModel(List<T> list, final Class<S> service) {
        List<IModel<T>> result = new ArrayList<>(list.size());
        for(T entity: list){
            result.add(getCompoundModel(entity, service));
        }
        return result;
    }
}
