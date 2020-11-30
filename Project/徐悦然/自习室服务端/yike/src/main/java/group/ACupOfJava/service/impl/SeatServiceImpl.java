package group.ACupOfJava.service.impl;

import group.ACupOfJava.dao.SeatDao;
import group.ACupOfJava.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
