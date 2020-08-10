package antiesys.antiepidemic.pojo;


import java.util.Date;

public class Report {
    private int orderNum;
    private int userId;
    private String userName;
    private Long userPhone;
    private Date inTime;
    private Date outTime;
    private String temperature;
    private String remarks;

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(Long userPhone) {
        this.userPhone = userPhone;
    }

    public Date getInTime() {
        return inTime;
    }

    public void setInTime(Date inTime) {
        this.inTime = inTime;
    }

    public Date getOutTime() {
        return outTime;
    }

    public void setOutTime(Date outTime) {
        this.outTime = outTime;
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

    @Override
    public String toString() {
        return "Report{" +
                "orderNum=" + orderNum +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userPhone=" + userPhone +
                ", inTime=" + inTime +
                ", outTime=" + outTime +
                ", temperature='" + temperature + '\'' +
                ", remarks='" + remarks + '\'' +
                '}';
    }
}
