package org.menesty.tradeplatform.service;

import org.menesty.tradeplatform.persistent.domain.Company;
import org.menesty.tradeplatform.persistent.domain.CompanyEntity;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * User: Menesty
 * Date: 8/13/13
 * Time: 12:00 PM
 */
public interface CompanyEntityService<Entity extends CompanyEntity> extends BaseService<Entity> {

    public List<Entity> loadByCompany(Company company, Pageable pageable);

    public List<Entity> loadByCompany(long companyId, Pageable pageable);

    public Long countByCompany(Company company);

    public Long countByCompany(long companyId);

    List<Entity> loadByCompany(Company company);

    List<Entity> loadByCompany(Long companyId);
}
