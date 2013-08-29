package org.menesty.tradeplatform.persistent.domain;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Menesty
 * Date: 4/28/13
 * Time: 7:21 AM
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class ProductItem extends CompanyEntity {

    private String name;
    @Digits(fraction = 2, integer = 8)
    @Column(precision = 10,scale = 2)
    private BigDecimal price;

    private int discount;
    @Lob
    private String description;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    private String artNumber;
    @OneToMany
    private List<Attachment> attachments = new ArrayList<Attachment>();

    private boolean visible;

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }


    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public String getArtNumber() {
        return artNumber;
    }

    public void setArtNumber(String artNumber) {
        this.artNumber = artNumber;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
