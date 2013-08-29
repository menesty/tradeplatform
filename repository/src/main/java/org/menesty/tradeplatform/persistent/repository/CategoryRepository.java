package org.menesty.tradeplatform.persistent.repository;

import org.menesty.tradeplatform.persistent.domain.Category;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * User: Menesty
 * Date: 4/30/13
 * Time: 6:59 AM
 */
public interface CategoryRepository extends CompanyEntityRepository<Category> {

    @Modifying
    @Transactional
    @Query("update Category set children = :children where id=:categoryId")
    void updateHasChildren(@Param("categoryId") long categoryId, @Param("children") boolean children);
}
