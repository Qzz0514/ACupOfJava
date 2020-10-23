package yalantis.com.sidemenu.sample.entity;

public class Cake {
    private int cId;
    private String cName;
    private String cImage;
    private int cPrice;
    private int cSize;//蛋糕尺寸
    private int cStep;//蛋糕层数
    private int cStar;//蛋糕星级
    private String cDescription;

    public Cake() {
    }

    public Cake(String cName, String cImage, int cPrice, int cSize, int cStep, int cStar, String cDescription) {
        this.cName = cName;
        this.cImage = cImage;
        this.cPrice = cPrice;
        this.cSize = cSize;
        this.cStep = cStep;
        this.cStar = cStar;
        this.cDescription = cDescription;
    }

    public int getcId() {
        return cId;
    }

    public void setcId(int cId) {
        this.cId = cId;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getcImage() {
        return cImage;
    }

    public void setcImage(String cImage) {
        this.cImage = cImage;
    }

    public int getcPrice() {
        return cPrice;
    }

    public void setcPrice(int cPrice) {
        this.cPrice = cPrice;
    }

    public int getcSize() {
        return cSize;
    }

    public void setcSize(int cSize) {
        this.cSize = cSize;
    }

    public int getcStep() {
        return cStep;
    }

    public void setcStep(int cStep) {
        this.cStep = cStep;
    }

    public int getcStar() {
        return cStar;
    }

    public void setcStar(int cStar) {
        this.cStar = cStar;
    }

    public String getcDescription() {
        return cDescription;
    }

    public void setcDescription(String cDescription) {
        this.cDescription = cDescription;
    }

    @Override
    public String toString() {
        return "Cake{" +
                "cId=" + cId +
                ", cName='" + cName + '\'' +
                ", cImage='" + cImage + '\'' +
                ", cPrice=" + cPrice +
                ", cSize=" + cSize +
                ", cStep=" + cStep +
                ", cStar=" + cStar +
                ", cDescription='" + cDescription + '\'' +
                '}';
    }


}