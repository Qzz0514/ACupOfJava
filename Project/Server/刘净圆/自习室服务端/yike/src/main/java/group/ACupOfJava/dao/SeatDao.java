package group.ACupOfJava.dao;

import group.ACupOfJava.pojo.Room;
import group.ACupOfJava.pojo.Seat;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * ClassName:SeatDao
 * Packeage:group.ACupOfJava.dao
 *
 * @Date:2020/11/26 8:35
 */
public interface SeatDao {
    public void takeSeats(List<Seat> seats);


    @Select("select * from room where room_id=#{room_id}")
    public Room getRoomRowAndCol(String room_id);
}
