package org.menesty.tradeplatform.web.data.model;

import org.apache.wicket.model.LoadableDetachableModel;
import org.menesty.tradeplatform.persistent.domain.Identifiable;

/**
 * User: Menesty
 * Date: 8/13/13
 * Time: 10:36 AM
 */
public abstract class EntityLoadableDetachableModel<Entity extends Identifiable> extends LoadableDetachableModel<Entity> {

    private final Long id;

    public EntityLoadableDetachableModel(Long id) {
        this.id = id;
    }

    public EntityLoadableDetachableModel(Entity object) {
        this(object.getId());
        setObject(object);
    }


    public Long getId(){
        return id;
    }

}
