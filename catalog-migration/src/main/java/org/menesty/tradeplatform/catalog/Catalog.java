package org.menesty.tradeplatform.catalog;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "catalog")
public class Catalog {
    private Long id;

    private String name;

    private String description;

    private List<Category> categories;

    private String imageBase;


    private List<Product> products;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public String getImageBase() {
        return imageBase;
    }

    public void setImageBase(String imageBase) {
        this.imageBase = imageBase;
    }
    @XmlElementWrapper(name = "products")
    @XmlElement(name = "product")
    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
