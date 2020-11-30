package group.ACupOfJava.pojo;

/**
 * ClassName:Room
 * Packeage:group.ACupOfJava.pojo
 *
 * @Date:2020/11/26 8:30
 */

public class Room {
    private int room_id;
    private String name;
    private int price;
    private int row;//行
    private int col;//列
    private int total;//座位数=行*列

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Room{" +
                "room_id=" + room_id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", row=" + row +
                ", col=" + col +
                ", total=" + total +
                '}';
    }
}
