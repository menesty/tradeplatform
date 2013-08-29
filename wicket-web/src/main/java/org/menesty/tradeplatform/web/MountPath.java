package org.menesty.tradeplatform.web;

import java.lang.annotation.*;

/**
 * User: Menesty
 * Date: 8/5/13
 * Time: 8:57 AM
 */
@Target({ElementType.TYPE , ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MountPath {
    String path();
}
