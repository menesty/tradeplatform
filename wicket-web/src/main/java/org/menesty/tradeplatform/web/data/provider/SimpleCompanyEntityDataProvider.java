package org.menesty.tradeplatform.web.data.provider;

import org.menesty.tradeplatform.persistent.domain.CompanyEntity;
import org.menesty.tradeplatform.service.CompanyEntityService;
import org.menesty.tradeplatform.web.data.model.PageableImpl;

import java.util.Iterator;

/**
 * User: Menesty
 * Date: 8/13/13
 * Time: 11:54 AM
 */
public abstract class SimpleCompanyEntityDataProvider<Entity extends CompanyEntity> extends SimpleDataProvider<Entity> {

    private final long companyId;

    protected SimpleCompanyEntityDataProvider(long companyId) {
        this.companyId = companyId;
    }


    @Override
    public Iterator<Entity> iterator(long first, long count) {
        return getService().loadByCompany(companyId, new PageableImpl(first, count)).iterator();
    }

    @Override
    public long size() {
        return getService().countByCompany(companyId);
    }


    @Override
    public abstract CompanyEntityService<Entity> getService();
}
