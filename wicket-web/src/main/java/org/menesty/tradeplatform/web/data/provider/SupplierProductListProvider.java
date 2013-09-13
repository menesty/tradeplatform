package org.menesty.tradeplatform.web.data.provider;

import org.apache.wicket.model.IModel;
import org.menesty.tradeplatform.persistent.domain.Supplier;
import org.menesty.tradeplatform.persistent.domain.SupplierProductItem;
import org.menesty.tradeplatform.service.SupplierProductItemService;
import org.menesty.tradeplatform.web.data.model.PageableImpl;

import java.util.Iterator;

public class SupplierProductListProvider extends SimpleDataProvider<SupplierProductItem> {

    private Long companyId;

    private Long supplierId;

    public SupplierProductListProvider(Long companyId, Long supplierId) {
        this.supplierId = supplierId;
        this.companyId = companyId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public void setSupplier(Supplier entity) {
        setSupplierId(entity == null ? null : entity.getId());
    }

    public void setsetSupplierId(IModel<Supplier> entity) {
        setSupplier(entity == null ? null : entity.getObject());
    }


    @Override
    public Iterator<SupplierProductItem> iterator(long first, long count) {
        return getService(getServiceClass()).loadBySupplier(companyId, supplierId, new PageableImpl(first, count)).iterator();
    }

    @Override
    public long size() {
        return getService(getServiceClass()).countBySupplier(companyId, supplierId);
    }


    @Override
    public Class<SupplierProductItemService> getServiceClass() {
        return SupplierProductItemService.class;
    }


}
