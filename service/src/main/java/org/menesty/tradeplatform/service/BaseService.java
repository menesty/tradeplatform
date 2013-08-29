package org.menesty.tradeplatform.service;

import com.mysema.query.types.path.EntityPathBase;
import org.menesty.tradeplatform.persistent.domain.Identifiable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Menesty
 * Date: 7/13/13
 * Time: 10:10 AM
 * To change this template use File | Settings | File Templates.
 */
public interface BaseService<Entity extends Identifiable> {
    Entity save(Entity entity);

    void delete(Entity entity, boolean soft);

    void delete(Entity entity);

    Entity loadById(long id);

    List<Entity> load(Pageable pageable);

    Long count();
}
