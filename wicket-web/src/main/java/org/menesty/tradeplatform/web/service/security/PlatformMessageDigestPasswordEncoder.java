package org.menesty.tradeplatform.web.service.security;

import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;

/**
 * User: Menesty
 * Date: 8/8/13
 * Time: 11:10 PM
 */
public class PlatformMessageDigestPasswordEncoder extends MessageDigestPasswordEncoder {
    public PlatformMessageDigestPasswordEncoder() {
        super("sha-256");
    }
}
