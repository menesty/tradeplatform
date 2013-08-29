package org.menesty.tradeplatform.persistent.listener;

import org.menesty.tradeplatform.persistent.domain.Trackable;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Date;

/**
 * User: Menesty
 * Date: 8/22/13
 * Time: 9:29 AM
 */
public class TrackableListener {
    @PrePersist
    @PreUpdate
    public void persist(Trackable entity){
         if(entity.getId() == null || entity.getId() == 0)
             entity.setCreatedDate(new Date());
        entity.setUpdatedDate(new Date());
    }
}
