package org.menesty.tradeplatform.service;

import org.menesty.tradeplatform.persistent.domain.ProductItem;

/**
 * Created with IntelliJ IDEA.
 * User: Menesty
 * Date: 7/20/13
 * Time: 9:44 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ProductService {

    ProductItem save(ProductItem productItem);
}
