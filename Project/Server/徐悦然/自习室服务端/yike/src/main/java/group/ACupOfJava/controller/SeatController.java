package group.ACupOfJava.controller;

import group.ACupOfJava.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * ClassName:SeatController
 * Packeage:group.ACupOfJava.controller
 *
 * @Date:2020/11/26 8:37
 */
@Controller

public class SeatController {
    @Autowired
    private SeatService seatService;


}
