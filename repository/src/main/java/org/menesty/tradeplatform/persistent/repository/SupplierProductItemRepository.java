package org.menesty.tradeplatform.persistent.repository;

import org.menesty.tradeplatform.persistent.domain.SupplierProductItem;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SupplierProductItemRepository extends CompanyEntityRepository<SupplierProductItem> {

    List<SupplierProductItem> findByCompanyIdAndSupplierIdAndDeletedFalse(Long companyId, Long supplierId, Pageable pageable);
}
