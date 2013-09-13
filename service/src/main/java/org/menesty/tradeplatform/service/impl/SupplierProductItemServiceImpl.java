package org.menesty.tradeplatform.service.impl;

import com.mysema.query.BooleanBuilder;
import org.menesty.tradeplatform.persistent.domain.QSupplierProductItem;
import org.menesty.tradeplatform.persistent.domain.SupplierProductItem;
import org.menesty.tradeplatform.persistent.repository.SupplierProductItemRepository;
import org.menesty.tradeplatform.service.SupplierProductItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierProductItemServiceImpl extends CompanyEntityServiceImpl<SupplierProductItem, QSupplierProductItem> implements SupplierProductItemService {

    @Autowired
    private SupplierProductItemRepository supplierProductItemRepository;


    @Override
    protected SupplierProductItemRepository getRepository() {
        return supplierProductItemRepository;
    }

    @Override
    public List<SupplierProductItem> loadBySupplier(Long companyId, Long supplierId, Pageable pageable) {
        return getRepository().findByCompanyIdAndSupplierIdAndDeletedFalse(companyId, supplierId, pageable);
    }

    @Override
    public long countBySupplier(Long companyId, Long supplierId) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(QSupplierProductItem.supplierProductItem.supplier.id.eq(supplierId));
        builder.and(QSupplierProductItem.supplierProductItem.company.id.eq(companyId));
        return getRepository().count(builder);
    }
}
