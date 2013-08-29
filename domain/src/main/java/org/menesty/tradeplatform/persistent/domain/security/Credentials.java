package org.menesty.tradeplatform.persistent.domain.security;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * User: Menesty
 * Date: 4/27/13
 * Time: 8:47 AM
 */
@Embeddable
public class Credentials {
    @NotNull
    @Column(updatable = false)
    private String userName;
    @NotNull
    @Size(min = 6)
    private String password;

    private Date expiredDate;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isCredentialsNonExpired() {
        //TODO[P1] need use Date utils
        return expiredDate != null ? false : true;
    }

    public Date getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }
}
