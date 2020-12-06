package group.ACupOfJava.pojo;

/**
 * ClassName:ImageBox
 * Packeage:group.ACupOfJava.pojo
 *
 * @Date:2020/12/5 22:29
 */
//用于存放轮播图的图片
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
