package org.menesty.tradeplatform.service.impl;

import com.google.common.collect.Lists;
import com.mysema.query.BooleanBuilder;
import com.mysema.query.types.path.BooleanPath;
import com.mysema.query.types.path.EntityPathBase;
import org.menesty.tradeplatform.persistent.domain.Company;
import org.menesty.tradeplatform.persistent.domain.CompanyEntity;
import org.menesty.tradeplatform.persistent.domain.QCompany;
import org.menesty.tradeplatform.persistent.repository.CompanyEntityRepository;
import org.menesty.tradeplatform.service.CompanyEntityService;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * User: Menesty
 * Date: 8/13/13
 * Time: 12:04 PM
 */
public abstract class CompanyEntityServiceImpl<Entity extends CompanyEntity, Q extends EntityPathBase<Entity>> extends BaseServiceImpl<Entity, Q> implements CompanyEntityService<Entity> {

    @Override
    protected abstract CompanyEntityRepository<Entity> getRepository();

    @Override
    public List<Entity> loadByCompany(Company company, Pageable pageable) {
        return loadByCompany(company.getId(), pageable);
    }

    @Override
    public List<Entity> loadByCompany(long companyId, Pageable pageable) {
        return getRepository().findByCompanyIdAndDeletedFalse(companyId, pageable);
    }

    @Override
    public Long countByCompany(Company company) {
        return countByCompany(company.getId());
    }

    @Override
    public Long countByCompany(long companyId) {
        QCompany qcompany = getFieldPath("company");
        BooleanPath deleted = getFieldPath("deleted");
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(deleted.isFalse());
        builder.and(qcompany.id.eq(companyId));
        return getRepository().count(builder);
    }

    @Override
    public List<Entity> loadByCompany(Company company) {
        return loadByCompany(company.getId());
    }

    @Override
    public List<Entity> loadByCompany(Long companyId) {
        BooleanBuilder builder = new BooleanBuilder();
        BooleanPath deleted = getFieldPath("deleted");
        builder.and(deleted.isFalse());
        builder.and(this.<QCompany>getFieldPath("company").id.eq(companyId));
        return Lists.newArrayList(getRepository().findAll(builder));
    }

    @Override
    public Entity loadById(Long companyId, Long entityId) {
        if (companyId == null || entityId == null) return null;
        return getRepository().findByCompanyIdAndId(companyId, entityId);
    }
}
