package group.ACupOfJava.controller;

import group.ACupOfJava.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * ClassName:RoomController
 * Packeage:group.ACupOfJava.controller
 *
 * @Date:2020/11/26 8:32
 */
@Controller
@RequestMapping("/room")
public class RoomController {
    @Autowired
    private RoomService roomService;

}
