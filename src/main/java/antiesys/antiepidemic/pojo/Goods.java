package antiesys.antiepidemic.pojo;


import java.util.Date;

public class Goods {
    private int goodsId;
    private String goodsName;
    private int goodsNum;
    private String goodsSource;
    private Date goodsInTime;
    private Date goodsOutTime;

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public int getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(int goodsNum) {
        this.goodsNum = goodsNum;
    }

    public String getGoodsSource() {
        return goodsSource;
    }

    public void setGoodsSource(String goodsSource) {
        this.goodsSource = goodsSource;
    }

    public Date getGoodsInTime() {
        return goodsInTime;
    }

    public void setGoodsInTime(Date goodsInTime) {
        this.goodsInTime = goodsInTime;
    }

    public Date getGoodsOutTime() {
        return goodsOutTime;
    }

    public void setGoodsOutTime(Date goodsOutTime) {
        this.goodsOutTime = goodsOutTime;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "goodsId='" + goodsId + '\'' +
                ", goodsName=" + goodsName +
                ", goodsNum='" + goodsNum + '\'' +
                ", goodsSource=" + goodsSource +
                ", goodsInTime=" + goodsInTime +
                ", goodsOutTime=" + goodsOutTime +
                '}';
    }
}
