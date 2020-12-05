package group.ACupOfJava.service.impl;

import group.ACupOfJava.dao.SeatDao;
import group.ACupOfJava.pojo.Room;
import group.ACupOfJava.pojo.Seat;
import group.ACupOfJava.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ClassName:SeatServiceImpl
 * Packeage:group.ACupOfJava.service.impl
 *
 * @Date:2020/11/26 8:36
 */
@Service
public class SeatServiceImpl implements SeatService {
    @Autowired
    private SeatDao seatDao;

    @Override
    public void takeSeats(List<Seat> seats) {
        seatDao.takeSeats(seats);
    }

    @Override
    public Room getRoomRowAndCol(String room_id) {
        return seatDao.getRoomRowAndCol(room_id);
    }

}
