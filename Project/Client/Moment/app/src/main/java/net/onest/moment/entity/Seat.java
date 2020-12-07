package net.onest.moment.entity;

/*qzz：座位实体类*/
public class Seat {

    private int id;
    private int user_id;
    private int room_id;
    private int row;
    private int col;
    private String startTime;
    private String endTime;

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

    public String getStartTime() {
        return startTime;
    }
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Seat(int id, int user_id, int room_id, int row, int col, String startTime, String endTime) {
        this.id = id;
        this.user_id = user_id;
        this.room_id = room_id;
        this.row = row;
        this.col = col;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Seat() {

    }

    @Override
    public String toString() {
        return "Seat{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", room_id=" + room_id +
                ", row=" + row +
                ", col=" + col +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                '}';
    }
}
