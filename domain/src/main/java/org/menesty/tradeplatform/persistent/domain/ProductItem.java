package org.menesty.tradeplatform.persistent.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 * User: Menesty
 * Date: 4/28/13
 * Time: 7:21 AM
 */
@Entity
public class ProductItem extends BaseProductItem {

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    private boolean visible;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
