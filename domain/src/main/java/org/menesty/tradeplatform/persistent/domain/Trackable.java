package org.menesty.tradeplatform.persistent.domain;

import org.menesty.tradeplatform.persistent.listener.TrackableListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * User: Menesty
 * Date: 8/22/13
 * Time: 9:26 AM
 */
@MappedSuperclass
@EntityListeners(TrackableListener.class)
public class Trackable extends Identifiable {
    @Column(updatable = false)
    private Date createdDate;
    private Date updatedDate;

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }
}
