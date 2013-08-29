package org.menesty.tradeplatform.web.util;

import org.apache.wicket.model.CompoundPropertyModel;
import org.menesty.tradeplatform.persistent.domain.Identifiable;
import org.menesty.tradeplatform.service.BaseService;
import org.menesty.tradeplatform.web.PlatformApplication;
import org.menesty.tradeplatform.web.data.model.EntityLoadableDetachableModel;

/**
 * User: Menesty
 * Date: 8/24/13
 * Time: 10:53 AM
 */
public class EntityModelUtil {

    public static <T extends Identifiable, S extends BaseService<T>> CompoundPropertyModel<T> get(T entity, final Class<S> service) {
        if (entity == null) return null;
        if (entity.getId() != null)
            return new CompoundPropertyModel<>(new EntityLoadableDetachableModel<T>(entity) {
                @Override
                protected T load() {
                    return PlatformApplication.get().getService(service).loadById(getId());
                }
            });

        return new CompoundPropertyModel<>(entity);
    }
}
