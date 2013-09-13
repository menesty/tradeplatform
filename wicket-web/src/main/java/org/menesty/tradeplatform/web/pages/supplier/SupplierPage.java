package org.menesty.tradeplatform.web.pages.supplier;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.menesty.tradeplatform.persistent.domain.Supplier;
import org.menesty.tradeplatform.persistent.domain.security.Authority;
import org.menesty.tradeplatform.service.SupplierService;
import org.menesty.tradeplatform.web.MountPath;
import org.menesty.tradeplatform.web.pages.layout.BaseLayout;
import org.menesty.tradeplatform.web.security.PlatformAuthorizeInstantiation;
import org.menesty.tradeplatform.web.security.SecureAuthenticatedSession;
import org.menesty.tradeplatform.web.util.EntityModelUtil;

@MountPath(path = "/suppliers/#{action}/#{id}")
@PlatformAuthorizeInstantiation({Authority.ROLE_COMPANY_ADMIN, Authority.ROLE_COMPANY_STAFF})
public class SupplierPage extends BaseLayout{
    @SpringBean
    private SupplierService supplierService;

    public SupplierPage(PageParameters parameters){
        super(parameters);


        ActionType action = ActionType.get(parameters.get("action").toString(), ActionType.LIST);
        switch (action) {
            case LIST:
                add(new SupplierListPanel("view") {

                    @Override
                    public void onDelete(AjaxRequestTarget target, Supplier supplier) {
                        supplierService.delete(supplier);
                        target.add(this);
                    }
                }.setOutputMarkupId(true));
                break;
            case EDIT:
            case ADD:
                Supplier entity = supplierService.loadById(SecureAuthenticatedSession.get().getCompanyId(), getPageParameters().get("id").toLong(0));
                if (entity == null) {
                    entity = new Supplier();
                    entity.setCompany(SecureAuthenticatedSession.get().getCompany());
                }
                add(new SupplierManagePanel("view", EntityModelUtil.getCompoundModel(entity, SupplierService.class)) {
                    @Override
                    public void onSave(AjaxRequestTarget target, Supplier entity) {
                        supplierService.save(entity);
                        setResponsePage(SupplierPage.class);
                    }

                    @Override
                    public void onCancel(AjaxRequestTarget target) {

                    }
                });
                break;

        }
    }
}
