package antiesys.antiepidemic.pojo;

public class Manager {
    private int adminId;
    private String adminPW;

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public String getAdminPW() {
        return adminPW;
    }

    public void setAdminPW(String adminPW) {
        this.adminPW = adminPW;
    }

    @Override
    public String toString() {
        return "Manager{" +
                "adminId=" + adminId +
                ", adminPW='" + adminPW + '\'' +
                '}';
    }
}
