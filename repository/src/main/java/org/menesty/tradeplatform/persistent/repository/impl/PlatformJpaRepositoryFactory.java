package org.menesty.tradeplatform.persistent.repository.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.JpaQueryLookupStrategy;
import org.springframework.data.jpa.repository.query.QueryExtractor;
import org.springframework.data.jpa.repository.support.*;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.data.repository.query.QueryLookupStrategy;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import java.io.Serializable;

import static org.springframework.data.querydsl.QueryDslUtils.QUERY_DSL_PRESENT;

public class PlatformJpaRepositoryFactory extends RepositoryFactorySupport {

    private final EntityManager entityManager;
    private final QueryExtractor extractor;
    private final LockModeRepositoryPostProcessor lockModePostProcessor;

    /**
     * Creates a new {@link JpaRepositoryFactory}.
     *
     * @param entityManager must not be {@literal null}
     */
    public PlatformJpaRepositoryFactory(EntityManager entityManager) {

        Assert.notNull(entityManager);

        this.entityManager = entityManager;
        this.extractor = PersistenceProvider.fromEntityManager(entityManager);
        this.lockModePostProcessor = LockModeRepositoryPostProcessor.INSTANCE;

        addRepositoryProxyPostProcessor(lockModePostProcessor);
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.repository.core.support.RepositoryFactorySupport#getTargetRepository(org.springframework.data.repository.core.RepositoryMetadata)
     */
    @Override
    protected Object getTargetRepository(RepositoryMetadata metadata) {
        return getTargetRepository(metadata, entityManager);
    }

    /**
     * Callback to create a {@link JpaRepository} instance with the given {@link EntityManager}
     *
     * @param <T>
     * @param <ID>
     * @param entityManager
     * @return
     * @see #getTargetRepository(RepositoryMetadata)
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    protected <T, ID extends Serializable> JpaRepository<?, ?> getTargetRepository(RepositoryMetadata metadata,
                                                                                   EntityManager entityManager) {

        Class<?> repositoryInterface = metadata.getRepositoryInterface();
        JpaEntityInformation<?, Serializable> entityInformation = getEntityInformation(metadata.getDomainType());

        SimpleJpaRepository<?, ?> repo;
        if (isPlatformQueryDslExecutor(repositoryInterface))
            repo = new PlatformQueryDslJpaRepository(entityInformation, entityManager);
        else
            repo = isQueryDslExecutor(repositoryInterface) ? new QueryDslJpaRepository(
                    entityInformation, entityManager) : new SimpleJpaRepository(entityInformation, entityManager);
        repo.setLockMetadataProvider(lockModePostProcessor.getLockMetadataProvider());

        return repo;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.springframework.data.repository.support.RepositoryFactorySupport#
     * getRepositoryBaseClass()
     */
    @Override
    protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
        if (isPlatformQueryDslExecutor(metadata.getRepositoryInterface()))
            return PlatformQueryDslJpaRepository.class;
        else if (isQueryDslExecutor(metadata.getRepositoryInterface()))
            return QueryDslJpaRepository.class;
        else
            return SimpleJpaRepository.class;

    }

    /**
     * Returns whether the given repository interface requires a QueryDsl specific implementation to be chosen.
     *
     * @param repositoryInterface
     * @return
     */
    private boolean isQueryDslExecutor(Class<?> repositoryInterface) {

        return QUERY_DSL_PRESENT && QueryDslPredicateExecutor.class.isAssignableFrom(repositoryInterface);
    }


    private boolean isPlatformQueryDslExecutor(Class<?> repositoryInterface) {
        return QUERY_DSL_PRESENT && PlatformQueryDslPredicateExecutor.class.isAssignableFrom(repositoryInterface);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.springframework.data.repository.support.RepositoryFactorySupport#
     * getQueryLookupStrategy
     * (org.springframework.data.repository.query.QueryLookupStrategy.Key)
     */
    @Override
    protected QueryLookupStrategy getQueryLookupStrategy(QueryLookupStrategy.Key key) {

        return JpaQueryLookupStrategy.create(entityManager, key, extractor);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.springframework.data.repository.support.RepositoryFactorySupport#
     * getEntityInformation(java.lang.Class)
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T, ID extends Serializable> JpaEntityInformation<T, ID> getEntityInformation(Class<T> domainClass) {

        return (JpaEntityInformation<T, ID>) JpaEntityInformationSupport.getMetadata(domainClass, entityManager);
    }
}
