package org.menesty.tradeplatform.catalog;

import javax.xml.bind.annotation.*;
import java.util.List;
@XmlRootElement(name="Product")
public class Product {

    private Long id;

    private String name;

    private Long categoryId;

    private List<ProductItem> items;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    @XmlElementWrapper(name = "items")
    @XmlElement(name = "item")
    public List<ProductItem> getItems() {
        return items;
    }

    public void setItems(List<ProductItem> items) {
        this.items = items;
    }
}
