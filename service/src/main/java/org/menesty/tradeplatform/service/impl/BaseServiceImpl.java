package org.menesty.tradeplatform.service.impl;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.types.path.BooleanPath;
import com.mysema.query.types.path.EntityPathBase;
import com.mysema.util.ReflectionUtils;
import org.menesty.tradeplatform.persistent.domain.Identifiable;
import org.menesty.tradeplatform.persistent.domain.QIdentifiable;
import org.menesty.tradeplatform.persistent.repository.BaseRepository;
import org.menesty.tradeplatform.service.BaseService;
import org.springframework.data.domain.Pageable;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * User: Menesty
 * Date: 7/13/13
 * Time: 10:14 AM
 */
public abstract class BaseServiceImpl<Entity extends Identifiable, Q extends EntityPathBase<Entity>> implements BaseService<Entity> {

    private transient Q entityPathBaseInstance;

    @Override
    public Entity save(Entity entity) {
        return getRepository().save(entity);
    }

    @Override
    public void delete(Entity entity, boolean soft) {
        if (soft) {
            entity.setDeleted(true);
            save(entity);
            return;
        }
        getRepository().delete(entity);
    }

    @Override
    public void delete(Entity entity) {
        delete(entity, true);
    }

    @Override
    public Entity loadById(long id) {
        return getRepository().findOne(id);
    }

    @Override
    public List<Entity> load(Pageable pageable) {
        return getRepository().findByDeletedFalse(pageable);
    }

    @Override
    public Long count() {
        BooleanPath deleted = getFieldPath("deleted");
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(deleted.isFalse());
        return getRepository().count(builder);
    }

    protected abstract BaseRepository<Entity> getRepository();


    protected Class<Entity> getEntityClass() {
        return (Class<Entity>) ReflectionUtils.getTypeParameterAsClass(((ParameterizedType) getClass().getGenericSuperclass()), 0);
    }

    protected Class<EntityPathBase<Entity>> getQEntityPathClass() {
        return (Class<EntityPathBase<Entity>>) ReflectionUtils.getTypeParameterAsClass(((ParameterizedType) getClass().getGenericSuperclass()), 1);
    }

    public <T> T getFieldPath(String fieldName) {

        Q entityPathBaseInstance = getEntityPathBaseInstance();
        if (entityPathBaseInstance != null) {
            Field field = ReflectionUtils.getFieldOrNull(entityPathBaseInstance.getClass(), fieldName);
            if (field != null) {
                try {
                    return (T) field.get(entityPathBaseInstance);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    private Q getEntityPathBaseInstance() {
        if (entityPathBaseInstance == null) {
            Class<EntityPathBase<Entity>> qPathClass = getQEntityPathClass();
            Class<Entity> entityClass = getEntityClass();
            Field entityPathBaseInstanceField = ReflectionUtils.getFieldOrNull(qPathClass, entityClass.getSimpleName().toLowerCase());
            try {
                entityPathBaseInstance = (Q) entityPathBaseInstanceField.get(qPathClass);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return entityPathBaseInstance;
    }
}
