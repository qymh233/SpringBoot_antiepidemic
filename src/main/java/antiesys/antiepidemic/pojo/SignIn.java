package antiesys.antiepidemic.pojo;

import java.util.Date;

public class SignIn {
    private Integer userId;
    private String userName;
    private String temperature;
    private String remarks;
    private Date inTime;
    private Integer meId;

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getMeId() {
        return meId;
    }

    public void setMeId(Integer meId) {
        this.meId = meId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Date getInTime() {
        return inTime;
    }

    public void setInTime(Date inTime) {
        this.inTime = inTime;
    }

    @Override
    public String toString() {
        return "SignIn{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", temperature='" + temperature + '\'' +
                ", remarks='" + remarks + '\'' +
                ", inTime=" + inTime +
                '}';
    }
}
