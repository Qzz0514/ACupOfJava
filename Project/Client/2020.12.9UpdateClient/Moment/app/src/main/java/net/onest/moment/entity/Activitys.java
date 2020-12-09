package net.onest.moment.entity;

/**
 * ClassName:Activitys
 * Packeage:group.ACupOfJava.pojo
 *
 * @Date:2020/12/6 9:22
 */
public class Activitys {
    private int aId;
    private String name;
    private String content;
    private int num;
    private String contentChild;
    private String contentChildPrice;
    private int contentChildCount;
    private int total;

    public int getaId() {
        return aId;
    }

    public void setaId(int aId) {
        this.aId = aId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getContentChild() {
        return contentChild;
    }

    public void setContentChild(String contentChild) {
        this.contentChild = contentChild;
    }

    public String getContentChildPrice() {
        return contentChildPrice;
    }

    public void setContentChildPrice(String contentChildPrice) {
        this.contentChildPrice = contentChildPrice;
    }

    public int getContentChildCount() {
        return contentChildCount;
    }

    public void setContentChildCount(int contentChildCount) {
        this.contentChildCount = contentChildCount;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Activitys{" +
                "aId=" + aId +
                ", name='" + name + '\'' +
                ", content='" + content + '\'' +
                ", num=" + num +
                ", contentChild='" + contentChild + '\'' +
                ", contentChildPrice='" + contentChildPrice + '\'' +
                ", contentChildCount=" + contentChildCount +
                ", total=" + total +
                '}';
    }
}
