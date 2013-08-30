package org.menesty.tradeplatform.persistent.repository;

import org.menesty.tradeplatform.persistent.domain.Identifiable;
import org.menesty.tradeplatform.persistent.repository.impl.PlatformQueryDslPredicateExecutor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

/**
 * User: Menesty
 * Date: 4/27/13
 * Time: 9:58 AM
 */
@NoRepositoryBean
public interface BaseRepository<T extends Identifiable> extends JpaRepository<T, Long>, PlatformQueryDslPredicateExecutor<T> {

    List<T> findByDeletedFalse(Pageable pageable);
}
