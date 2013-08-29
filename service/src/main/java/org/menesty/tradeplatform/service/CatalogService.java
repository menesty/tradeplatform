package org.menesty.tradeplatform.service;

import org.menesty.tradeplatform.persistent.domain.Catalog;

/**
 * Created with IntelliJ IDEA.
 * User: Menesty
 * Date: 7/6/13
 * Time: 9:28 PM
 * To change this template use File | Settings | File Templates.
 */
public interface CatalogService extends CompanyEntityService<Catalog> {

    Catalog loadById(Long companyId, Long catalogId);
}
