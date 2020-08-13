package antiesys.antiepidemic.pojo;

import java.util.Date;

public class Volunte {
    private Integer userId;
    private String userName;
    private Date puDate;//申请时间
    private String taskTime;//申请任务时间
    private String stat;
    private Integer meID;

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

    public Date getPuDate() {
        return puDate;
    }

    public void setPuDate(Date puDate) {
        this.puDate = puDate;
    }

    public String getTaskTime() {
        return taskTime;
    }

    public void setTaskTime(String taskTime) {
        this.taskTime = taskTime;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    public Integer getMeID() {
        return meID;
    }

    public void setMeID(Integer meID) {
        this.meID = meID;
    }

    @Override
    public String toString() {
        return "Volunte{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", puDate=" + puDate +
                ", taskTime=" + taskTime +
                ", stat='" + stat + '\'' +
                ", meID=" + meID +
                '}';
    }
}
