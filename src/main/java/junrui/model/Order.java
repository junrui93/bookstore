package junrui.model;

import java.util.Date;

public class Order {
    private Integer id;

    private Integer userId;

    private Integer publId;

    private Integer number;

    private Date addTs;

    private Date removeTs;

    private Date commitTs;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPublId() {
        return publId;
    }

    public void setPublId(Integer publId) {
        this.publId = publId;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Date getAddTs() {
        return addTs;
    }

    public void setAddTs(Date addTs) {
        this.addTs = addTs;
    }

    public Date getRemoveTs() {
        return removeTs;
    }

    public void setRemoveTs(Date removeTs) {
        this.removeTs = removeTs;
    }

    public Date getCommitTs() {
        return commitTs;
    }

    public void setCommitTs(Date commitTs) {
        this.commitTs = commitTs;
    }
}