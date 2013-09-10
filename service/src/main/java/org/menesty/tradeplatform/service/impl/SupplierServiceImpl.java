package org.menesty.tradeplatform.service.impl;

import org.menesty.tradeplatform.persistent.domain.QSupplier;
import org.menesty.tradeplatform.persistent.domain.Supplier;
import org.menesty.tradeplatform.persistent.repository.CompanyEntityRepository;
import org.menesty.tradeplatform.persistent.repository.SupplierRepository;
import org.menesty.tradeplatform.service.SupplierService;
import org.springframework.stereotype.Service;

@Service
public class SupplierServiceImpl extends CompanyEntityServiceImpl<Supplier, QSupplier> implements SupplierService {

    private SupplierRepository supplierRepository;

    @Override
    protected CompanyEntityRepository<Supplier> getRepository() {
        return supplierRepository;
    }
}
