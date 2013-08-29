package org.menesty.tradeplatform.persistent.repository;

import org.menesty.tradeplatform.persistent.domain.CompanyEntity;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * User: Menesty
 * Date: 7/6/13
 * Time: 9:05 PM
 */
public interface CompanyEntityRepository<Entity extends CompanyEntity> extends BaseRepository<Entity> {

    List<Entity> findByCompanyId(Long companyId, Pageable pageable);

    Entity findByCompanyIdAndId(Long companyId, Long entityId);


}
