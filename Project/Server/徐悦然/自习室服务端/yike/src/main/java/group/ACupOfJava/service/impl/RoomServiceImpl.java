package group.ACupOfJava.service.impl;

import group.ACupOfJava.dao.RoomDao;
import group.ACupOfJava.pojo.Room;
import group.ACupOfJava.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ClassName:RoomServiceImpl
 * Packeage:group.ACupOfJava.service.impl
 *
 * @Date:2020/11/26 8:30
 */
@Service
public class RoomServiceImpl implements RoomService {
    @Autowired
    private RoomDao roomDao;

    public List<Room> roomList(int shop_id) {
        return roomDao.roomList(shop_id);
    }

}
