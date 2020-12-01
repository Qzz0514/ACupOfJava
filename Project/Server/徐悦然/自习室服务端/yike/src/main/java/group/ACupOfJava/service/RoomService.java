package group.ACupOfJava.service;

import group.ACupOfJava.pojo.Room;

import java.util.List;

/**
 * ClassName:RoomService
 * Packeage:group.ACupOfJava.service
 *
 * @Date:2020/11/26 8:30
 */
public interface RoomService {
    public List<Room> roomList(int shop_id);
}
