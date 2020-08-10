package antiesys.antiepidemic.pojo;

import java.util.Date;

public class Message {
    private String title;//标题
    private Integer puBer;//发布者
    private String cont;//具体内容
    private Date puDate;//发布时间
    private Integer meID;//编号
    private String stat;//状态

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

    public Integer getPuBer() {
        return puBer;
    }

    public void setPuBer(Integer puBer) {
        this.puBer = puBer;
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

    @Override
    public String toString() {
        return "Message{" +
                "title='" + title + '\'' +
                ", puBer='" + puBer + '\'' +
                ", cont='" + cont + '\'' +
                ", puDate=" + puDate +
                ", meID=" + meID +
                ", stat='" + stat + '\'' +
                '}';
    }
}
