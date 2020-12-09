package net.onest.moment.entity;

public class ImageBox {
    private int imgId;
    private String imgName;
    private int shopId;

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    @Override
    public String toString() {
        return "ImageBox{" +
                "imgId=" + imgId +
                ", imgName='" + imgName + '\'' +
                ", shopId=" + shopId +
                '}';
    }
}