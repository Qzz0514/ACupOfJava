package group.ACupOfJava.dao;

import group.ACupOfJava.pojo.Room;

import java.util.List;

/**
 * ClassName:RoomDao
 * Packeage:group.ACupOfJava.dao
 *
 * @Date:2020/11/26 8:26
 */
public interface RoomDao  {
    public List<Room> roomList(int shop_id);


}
