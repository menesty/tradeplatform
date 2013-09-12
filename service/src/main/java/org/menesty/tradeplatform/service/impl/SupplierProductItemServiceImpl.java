package org.menesty.tradeplatform.service.impl;

import org.menesty.tradeplatform.persistent.domain.QSupplierProductItem;
import org.menesty.tradeplatform.persistent.domain.SupplierProductItem;
import org.menesty.tradeplatform.persistent.repository.CompanyEntityRepository;
import org.menesty.tradeplatform.persistent.repository.SupplierProductItemRepository;
import org.menesty.tradeplatform.service.SupplierProductItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SupplierProductItemServiceImpl extends CompanyEntityServiceImpl<SupplierProductItem, QSupplierProductItem> implements SupplierProductItemService {

    @Autowired
    private SupplierProductItemRepository supplierProductItemRepository;


    @Override
    protected CompanyEntityRepository<SupplierProductItem> getRepository() {
        return supplierProductItemRepository;
    }
}
