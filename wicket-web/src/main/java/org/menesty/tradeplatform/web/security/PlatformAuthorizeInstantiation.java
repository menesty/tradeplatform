package org.menesty.tradeplatform.web.security;

import org.menesty.tradeplatform.persistent.domain.security.Authority;

import java.lang.annotation.*;

/**
 * User: Menesty
 * Date: 8/10/13
 * Time: 12:04 AM
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.PACKAGE, ElementType.TYPE })
@Documented
@Inherited
public  @interface PlatformAuthorizeInstantiation {

    Authority[] value() default { };
}
