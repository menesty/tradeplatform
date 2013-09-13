package org.menesty.tradeplatform.web.pages.supplier.product;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.menesty.tradeplatform.persistent.domain.Supplier;
import org.menesty.tradeplatform.persistent.domain.SupplierProductItem;
import org.menesty.tradeplatform.persistent.domain.security.Authority;
import org.menesty.tradeplatform.service.SupplierProductItemService;
import org.menesty.tradeplatform.service.SupplierService;
import org.menesty.tradeplatform.web.MountPath;
import org.menesty.tradeplatform.web.pages.layout.BaseLayout;
import org.menesty.tradeplatform.web.pages.supplier.SupplierPage;
import org.menesty.tradeplatform.web.security.PlatformAuthorizeInstantiation;
import org.menesty.tradeplatform.web.security.SecureAuthenticatedSession;
import org.menesty.tradeplatform.web.util.EntityModelUtil;

@MountPath(path = "/supplier/#{supplierId}/products/#{action}/#{id}")
@PlatformAuthorizeInstantiation({Authority.ROLE_COMPANY_ADMIN, Authority.ROLE_COMPANY_STAFF})
public class SupplierProductPage extends BaseLayout {
    @SpringBean
    private SupplierService supplierService;
    @SpringBean
    private SupplierProductItemService supplierProductItemService;

    public SupplierProductPage(PageParameters pageParameters) {
        super(pageParameters);

        final Supplier supplier = supplierService.loadById(SecureAuthenticatedSession.get().getCompanyId(), pageParameters.get("supplierId").toOptionalLong());
        if (supplier == null) {
            setResponsePage(SupplierPage.class);
            return;
        }
        setDefaultModel(EntityModelUtil.getCompoundModel(supplier, SupplierService.class));
        ActionType action = ActionType.get(pageParameters.get("action").toString(), ActionType.LIST);

        switch (action) {
            case LIST:
                add(new SupplierProductItemListPanel("view") {

                    @Override
                    public void onDelete(AjaxRequestTarget target, SupplierProductItem supplier) {
                        supplierProductItemService.delete(supplier);
                        target.add(this);
                    }
                }.setOutputMarkupId(true).setDefaultModel(getDefaultModel()));
                break;
            case EDIT:
            case ADD:
                SupplierProductItem entity = supplierProductItemService.loadById(SecureAuthenticatedSession.get().getCompanyId(), getPageParameters().get("id").toLong(0));
                if (entity == null) {
                    entity = new SupplierProductItem();
                    entity.setSupplier(supplier);
                    entity.setCompany(SecureAuthenticatedSession.get().getCompany());
                }
                add(new SupplierProductItemManagePanel("view", EntityModelUtil.getCompoundModel(entity, SupplierProductItemService.class)) {
                    @Override
                    public void onSave(AjaxRequestTarget target, SupplierProductItem entity) {
                        supplierProductItemService.save(entity);
                        PageParameters pageParameters = new PageParameters();
                        pageParameters.add("supplierId", supplier.getId());
                        setResponsePage(SupplierProductPage.class, pageParameters);
                    }

                    @Override
                    public void onCancel(AjaxRequestTarget target) {

                    }
                });
                break;

        }
    }

}
