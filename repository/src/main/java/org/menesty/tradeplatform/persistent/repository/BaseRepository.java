package org.menesty.tradeplatform.persistent.repository;

import org.menesty.tradeplatform.persistent.domain.Identifiable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Menesty
 * Date: 4/27/13
 * Time: 9:58 AM
 * To change this template use File | Settings | File Templates.
 */
public interface BaseRepository<T extends Identifiable> extends JpaRepository<T, Long>, QueryDslPredicateExecutor<T> {

    List<T> findByDeletedFalse(Pageable pageable);
}
