package group.ACupOfJava.pojo;

/**
 * ClassName:Seat
 * Packeage:group.ACupOfJava.pojo
 *
 * @Date:2020/11/26 8:35
 */
public class Seat {
    private int id;
    private int user_id;
    private int room_id;
    private int row;
    private int col;
    private String starttime;
    private String endtime;


    @Override
    public String toString() {
        return "Seat{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", room_id=" + room_id +
                ", row=" + row +
                ", col=" + col +
                ", starttime='" + starttime + '\'' +
                ", endtime='" + endtime + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
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

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }
}
