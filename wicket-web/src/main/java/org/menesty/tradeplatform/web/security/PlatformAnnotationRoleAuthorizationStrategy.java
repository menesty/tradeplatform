package org.menesty.tradeplatform.web.security;

import org.apache.wicket.Component;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authroles.authorization.strategies.role.AbstractRoleAuthorizationStrategy;
import org.apache.wicket.authroles.authorization.strategies.role.IRoleCheckingStrategy;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.request.component.IRequestableComponent;
import org.menesty.tradeplatform.persistent.domain.security.Authority;

/**
 * User: Menesty
 * Date: 8/10/13
 * Time: 12:01 AM
 */
public class PlatformAnnotationRoleAuthorizationStrategy extends AbstractRoleAuthorizationStrategy {

    public PlatformAnnotationRoleAuthorizationStrategy(IRoleCheckingStrategy roleCheckingStrategy) {
        super(roleCheckingStrategy);
    }

    @Override
    public <T extends IRequestableComponent> boolean isInstantiationAuthorized(Class<T> componentClass) {
        boolean authorized = true;

        // Check class annotation first because it is more specific than package annotation
        final PlatformAuthorizeInstantiation classAnnotation = componentClass.getAnnotation(PlatformAuthorizeInstantiation.class);
        if (classAnnotation != null) {
            authorized = hasAny(new Roles(getRoles(classAnnotation.value())));
        } else {
            // Check package annotation if there is no one on the the class
            final Package componentPackage = componentClass.getPackage();
            if (componentPackage != null) {
                final PlatformAuthorizeInstantiation packageAnnotation = componentPackage.getAnnotation(PlatformAuthorizeInstantiation.class);
                if (packageAnnotation != null) {
                    authorized = hasAny(new Roles(getRoles(packageAnnotation.value())));
                }
            }
        }

        return authorized;

    }

    private String[] getRoles(Authority[] authorities) {
        String[] result = new String[authorities.length];
        int i = 0;
        for (Authority authority : authorities) {
            result[i] = authority.toString();
            i++;
        }
        return result;
    }

    @Override
    public boolean isActionAuthorized(Component component, Action action) {
        return true;
    }
}
