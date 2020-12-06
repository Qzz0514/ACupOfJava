package net.onest.moment.entity;

public class Activities {

    private int storeImg;
    private int priceImg;
    private int locationImg;
    private int timeImg;
    private int endTimeImg;
    private int starImg;
    private int collectImg;
    private int arrow;
    private String storeName;
    private int star;
    private int collect;
    private String price;
    private String time;
    private String location;

    public Activities(int storeImg, String storeName, String time, String location, int star, int collect, int priceImg, int locationImg, int timeImg, int starImg, int collectImg, int arrow) {
        this.storeImg = storeImg;
        this.storeName = storeName;
        this.time = time;
        this.location = location;
        this.star = star;
        this.collect = collect;
        this.priceImg = priceImg;
        this.locationImg = locationImg;
        this.timeImg = timeImg;
        this.starImg = starImg;
        this.collectImg =collectImg;
        this.arrow = arrow;
    }


    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public int getCollect() {
        return collect;
    }

    public void setCollect(int collect) {
        this.collect = collect;
    }
    public int getStoreImg() {
        return storeImg;
    }

    public void setStoreImg(int storeImg) {
        this.storeImg = storeImg;
    }

    public int getPriceImg() {
        return priceImg;
    }

    public void setPriceImg(int priceImg) {
        this.priceImg = priceImg;
    }

    public int getLocationImg() {
        return locationImg;
    }

    public void setLocationImg(int locationImg) {
        this.locationImg = locationImg;
    }

    public int getTimeImg() {
        return timeImg;
    }

    public void setTimeImg(int timeImg) {
        this.timeImg = timeImg;
    }

    public int getStarImg() {
        return starImg;
    }

    public void setStarImg(int starImg) {
        this.starImg = starImg;
    }

    public int getCollectImg() {
        return collectImg;
    }

    public void setCollectImg(int collectImg) {
        this.collectImg = collectImg;
    }

    public int getArrow() {
        return arrow;
    }

    public void setArrow(int arrow) {
        this.arrow = arrow;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String openTime) {
        this.time = openTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Activities{" +
                "storeImg=" + storeImg +
                ", priceImg=" + priceImg +
                ", locationImg=" + locationImg +
                ", timeImg=" + timeImg +
                ", starImg=" + starImg +
                ", collectImg=" + collectImg +
                ", arrow=" + arrow +
                ", storeName='" + storeName + '\'' +
                ", star=" + star +
                ", collect=" + collect +
                ", price='" + price + '\'' +
                ", openTime='" + time + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
