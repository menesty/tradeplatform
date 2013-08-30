package org.menesty.tradeplatform.persistent.repository.impl;

import com.mysema.query.types.Predicate;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

public interface PlatformQueryDslPredicateExecutor<T> extends QueryDslPredicateExecutor<T> {

    public List<T> find(Predicate predicate, Pageable pageable);
}
