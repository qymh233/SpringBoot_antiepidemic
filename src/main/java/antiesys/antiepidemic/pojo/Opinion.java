package antiesys.antiepidemic.pojo;

import java.util.Date;

public class Opinion {
    /**
     * 标题
     */
    private String title;
    /**
     * 发布者
     */
    private Integer userId;

    private String userName;
    /**
     * 具体内容
     */
    private String cont;
    /**
     * 发布时间
     */
    private Date puDate;
    /**
     * 编号
     */
    private Integer meID;
    private String stat;
    //管理员id
    private Integer adminId;
    //管理员回复
    private String adCont;
    //回复时间
    private Date adDate;

    public Integer getMeID() {
        return meID;
    }

    public void setMeID(Integer meID) {
        this.meID = meID;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCont() {
        return cont;
    }

    public void setCont(String cont) {
        this.cont = cont;
    }

    public Date getPuDate() {
        return puDate;
    }

    public void setPuDate(Date puDate) {
        this.puDate = puDate;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public String getAdCont() {
        return adCont;
    }


    public void setAdCont(String adCont) {
        this.adCont = adCont;
    }

    public Date getAdDate() {
        return adDate;
    }

    @Override
    public String toString() {
        return "Opinion{" +
                "title='" + title + '\'' +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", cont='" + cont + '\'' +
                ", puDate=" + puDate +
                ", meID=" + meID +
                ", stat='" + stat + '\'' +
                ", adminId=" + adminId +
                ", adCont='" + adCont + '\'' +
                ", adDate=" + adDate +
                '}';
    }

    public void setAdDate(Date adDate) {
        this.adDate = adDate;
    }

}
