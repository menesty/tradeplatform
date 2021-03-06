package org.menesty.tradeplatform.service;

import org.menesty.tradeplatform.persistent.domain.Catalog;
import org.menesty.tradeplatform.persistent.domain.Category;
import org.menesty.tradeplatform.persistent.domain.Company;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Menesty
 * Date: 4/30/13
 * Time: 7:01 AM
 * To change this template use Filee | Settings | File Templates.
 */
public interface CategoryService extends CompanyEntityService<Category> {

    List<Category> getChildren(Long companyId, Long catalogId, Long parentId, Pageable pageable);

    List<Category> getChildren(Company company, Catalog catalog, Category parent, Pageable pageable);

    long count(Long companyId, Long catalogId, Long parentId);

    long count(Company company, Catalog catalog, Category parent);

    List<Category> getChildren(Company company, Catalog catalog, Category parent);

    List<Category> getChildren(Company company, Category parent);

    List<Category> getChildrenByCatalog(Company company, Catalog catalog);
}
