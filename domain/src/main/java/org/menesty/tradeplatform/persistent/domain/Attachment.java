package org.menesty.tradeplatform.persistent.domain;

import javax.persistence.Entity;

/**
 * Created with IntelliJ IDEA.
 * User: Menesty
 * Date: 4/29/13
 * Time: 8:32 AM
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class Attachment extends Identifiable {

    private String fileName;

    private long size;

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
