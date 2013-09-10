package org.menesty.tradeplatform.persistent.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity
public class SupplierProductItem extends BaseProductItem {
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Supplier supplier;

    @OneToOne
    private ProductItem productItem;

    public ProductItem getProductItem() {
        return productItem;
    }

    public void setProductItem(ProductItem productItem) {
        this.productItem = productItem;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }
}
