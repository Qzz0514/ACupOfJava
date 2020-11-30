package yalantis.com.sidemenu.sample.entity;

import java.util.List;

public class Order {
    private int oId;
    private List<Cake> cakeItemList;
    private int oPrice;
    private String oStatus;
    public Order() {

    }
    public Order(List<Cake> cakeItemList, int oPrice, String oStatus) {
        this.cakeItemList = cakeItemList;
        this.oPrice = oPrice;
        this.oStatus = oStatus;
    }

    public int getoId() {
        return oId;
    }

    public void setoId(int oId) {
        this.oId = oId;
    }

    public int getoPrice() {
        return oPrice;
    }

    public void setoPrice(int oPrice) {
        this.oPrice = oPrice;
    }

    public List<Cake> getCakeItemList() {
        return cakeItemList;
    }

    public void setCakeItemList(List<Cake> cakeItemList) {
        this.cakeItemList = cakeItemList;
    }

    public String getoStatus() {
        return oStatus;
    }

    public void setoStatus(String oStatus) {
        this.oStatus = oStatus;
    }

    @Override
    public String toString() {
        return "Order{" +
                "oId=" + oId +
                ", cakeItemList=" + cakeItemList.toString() +
                ", oPrice='" + oPrice + '\'' +
                ", oStatus='" + oStatus + '\'' +
                '}';
    }
}
