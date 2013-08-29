package org.menesty.tradeplatform.service.impl;

import org.menesty.tradeplatform.persistent.domain.ProductItem;
import org.menesty.tradeplatform.persistent.domain.QProductItem;
import org.menesty.tradeplatform.persistent.repository.BaseRepository;
import org.menesty.tradeplatform.persistent.repository.ProductItemRepository;
import org.menesty.tradeplatform.service.ProductItemService;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: Menesty
 * Date: 4/29/13
 * Time: 8:45 AM
 * To change this template use File | Settings | File Templates.
 */
@Service
public class ProductItemServiceImpl extends BaseServiceImpl<ProductItem, QProductItem> implements ProductItemService {
    private ProductItemRepository productItemRepository;

    @Override
    protected BaseRepository<ProductItem> getRepository() {
        return productItemRepository;
    }
}
