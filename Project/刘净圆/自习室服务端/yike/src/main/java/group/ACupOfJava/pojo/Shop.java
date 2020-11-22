package group.ACupOfJava.pojo;

/**
 * ClassName:Shop
 * Packeage:group.ACupOfJava.pojo
 *
 * @Date:2020/11/22 10:42
 */
public class Shop {
    private int id;
    private String name;
    private String image;
    private String location;
    private String startime;
    private String endtime;
    private int star;
    private int like;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStartime() {
        return startime;
    }

    public void setStartime(String startime) {
        this.startime = startime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    @Override
    public String toString() {
        return "Shop{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", location='" + location + '\'' +
                ", startime='" + startime + '\'' +
                ", endtime='" + endtime + '\'' +
                ", star=" + star +
                ", like=" + like +
                '}';
    }
}
