package org.menesty.tradeplatform.persistent.repository.impl;

import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.types.EntityPath;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.Predicate;
import com.mysema.query.types.path.PathBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.QueryDslJpaRepository;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.querydsl.EntityPathResolver;
import org.springframework.data.querydsl.SimpleEntityPathResolver;
import java.util.List;
import javax.persistence.EntityManager;
import java.io.Serializable;

public class PlatformQueryDslJpaRepository<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements PlatformQueryDslPredicateExecutor<T>{

    private static final EntityPathResolver DEFAULT_ENTITY_PATH_RESOLVER = SimpleEntityPathResolver.INSTANCE;

    private final EntityPath<T> path;
    private final PathBuilder<T> builder;
    private final Querydsl querydsl;

    /**
     * Creates a new {@link QueryDslJpaRepository} from the given domain class and {@link EntityManager}. This will use
     * the {@link SimpleEntityPathResolver} to translate the given domain class into an {@link EntityPath}.
     *
     * @param entityInformation must not be {@literal null}.
     * @param entityManager must not be {@literal null}.
     */
    public PlatformQueryDslJpaRepository(JpaEntityInformation<T, ID> entityInformation, EntityManager entityManager) {
        this(entityInformation, entityManager, DEFAULT_ENTITY_PATH_RESOLVER);
    }

    /**
     * Creates a new {@link QueryDslJpaRepository} from the given domain class and {@link EntityManager} and uses the
     * given {@link EntityPathResolver} to translate the domain class into an {@link EntityPath}.
     *
     * @param entityInformation must not be {@literal null}.
     * @param entityManager must not be {@literal null}.
     * @param resolver must not be {@literal null}.
     */
    public PlatformQueryDslJpaRepository(JpaEntityInformation<T, ID> entityInformation, EntityManager entityManager,
                                 EntityPathResolver resolver) {

        super(entityInformation, entityManager);

        this.path = resolver.createPath(entityInformation.getJavaType());
        this.builder = new PathBuilder<T>(path.getType(), path.getMetadata());
        this.querydsl = new Querydsl(entityManager, builder);
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.querydsl.QueryDslPredicateExecutor#findOne(com.mysema.query.types.Predicate)
     */
    public T findOne(Predicate predicate) {
        return createQuery(predicate).uniqueResult(path);
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.querydsl.QueryDslPredicateExecutor#findAll(com.mysema.query.types.Predicate)
     */
    public java.util.List<T> findAll(Predicate predicate) {
        return createQuery(predicate).list(path);
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.querydsl.QueryDslPredicateExecutor#findAll(com.mysema.query.types.Predicate, com.mysema.query.types.OrderSpecifier<?>[])
     */
    public java.util.List<T> findAll(Predicate predicate, OrderSpecifier<?>... orders) {
        return createQuery(predicate).orderBy(orders).list(path);
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.querydsl.QueryDslPredicateExecutor#findAll(com.mysema.query.types.Predicate, org.springframework.data.domain.Pageable)
     */
    public Page<T> findAll(Predicate predicate, Pageable pageable) {

        JPQLQuery countQuery = createQuery(predicate);
        JPQLQuery query = querydsl.applyPagination(pageable, createQuery(predicate));

        return new PageImpl<T>(query.list(path), pageable, countQuery.count());
    }

    @Override
    public List<T> find(Predicate predicate, Pageable pageable) {
        JPQLQuery query = querydsl.applyPagination(pageable, createQuery(predicate));
        return query.list(path);
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.querydsl.QueryDslPredicateExecutor#count(com.mysema.query.types.Predicate)
     */
    public long count(Predicate predicate) {
        return createQuery(predicate).count();
    }

    /**
     * Creates a new {@link JPQLQuery} for the given {@link Predicate}.
     *
     * @param predicate
     * @return the Querydsl {@link JPQLQuery}.
     */
    protected JPQLQuery createQuery(Predicate... predicate) {
        return querydsl.createQuery(path).where(predicate);
    }


}
