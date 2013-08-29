package org.menesty.tradeplatform.service.impl;

import org.menesty.tradeplatform.persistent.domain.Product;
import org.menesty.tradeplatform.persistent.domain.ProductItem;
import org.menesty.tradeplatform.persistent.repository.ProductRepository;
import org.menesty.tradeplatform.service.ProductItemService;
import org.menesty.tradeplatform.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created with IntelliJ IDEA.
 * User: Menesty
 * Date: 7/20/13
 * Time: 9:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductItemService productItemService;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public ProductItem save(ProductItem productItem) {
        if(productItem.getId() == null && productItem.getProduct() == null){
            Product product = productRepository.save(new Product());
            productItem.setProduct(product);
        }
        return productItemService.save(productItem);
    }
}
