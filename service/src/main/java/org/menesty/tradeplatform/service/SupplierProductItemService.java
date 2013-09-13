package org.menesty.tradeplatform.service;

import org.menesty.tradeplatform.persistent.domain.SupplierProductItem;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface SupplierProductItemService extends CompanyEntityService<SupplierProductItem> {

    List<SupplierProductItem> loadBySupplier(Long companyId, Long supplierId, Pageable pageable);

    long countBySupplier(Long companyId, Long supplierId);
}
