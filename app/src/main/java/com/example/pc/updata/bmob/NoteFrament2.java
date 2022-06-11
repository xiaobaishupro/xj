package com.example.pc.updata.bmob;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;
import java.util.Date;

import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2021/11/15.
 */
@Entity
public class NoteFrament2  implements Serializable {

    static final long serialVersionUID = 42L;

    @Id()
    private Long id;

    String titel;
    Date date;
    String img;
    String content;
    private int type;
    private String bindId;
    private String bindUserId;
    private String objectId;
    private String createdAt;
    private String updatedAt;
    @Generated(hash = 593284881)
    public NoteFrament2(Long id, String titel, Date date, String img,
            String content, int type, String bindId, String bindUserId,
            String objectId, String createdAt, String updatedAt) {
        this.id = id;
        this.titel = titel;
        this.date = date;
        this.img = img;
        this.content = content;
        this.type = type;
        this.bindId = bindId;
        this.bindUserId = bindUserId;
        this.objectId = objectId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @Generated(hash = 175759941)
    public NoteFrament2() {
    }
    public void setBindId(String bindId) {
        this.bindId = bindId;
    }

    public String getBindUserId() {
        return bindUserId;
    }

    public void setBindUserId(String bindUserId) {
        this.bindUserId = bindUserId;
    }

    public String getBindId() {
        return bindId;
    }

    public int getType() {
        return type;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getObjectId() {
        return this.objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return this.updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }


}
