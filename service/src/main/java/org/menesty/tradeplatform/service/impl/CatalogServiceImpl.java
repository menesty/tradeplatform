package org.menesty.tradeplatform.service.impl;

import org.menesty.tradeplatform.persistent.domain.Catalog;
import org.menesty.tradeplatform.persistent.domain.QCatalog;
import org.menesty.tradeplatform.persistent.repository.CatalogRepository;
import org.menesty.tradeplatform.persistent.repository.CompanyEntityRepository;
import org.menesty.tradeplatform.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User: Menesty
 * Date: 7/6/13
 * Time: 9:28 PM
 */
@Service
public class CatalogServiceImpl extends CompanyEntityServiceImpl<Catalog, QCatalog> implements CatalogService {
    @Autowired
    private CatalogRepository catalogRepository;

    @Override
    protected CompanyEntityRepository<Catalog> getRepository() {
        return catalogRepository;
    }


    public static void main(String... arg) {
        new CatalogServiceImpl().count();
    }

    @Override
    public Catalog loadById(Long companyId, Long catalogId) {
        if (catalogId == null || catalogId == null) return null;
        return catalogRepository.findByCompanyIdAndId(companyId, catalogId);
    }
}
