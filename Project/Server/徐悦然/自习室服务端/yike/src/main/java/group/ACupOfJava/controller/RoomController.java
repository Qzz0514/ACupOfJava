package group.ACupOfJava.controller;
import group.ACupOfJava.pojo.Room;
import group.ACupOfJava.service.RoomService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;


@Controller
@RequestMapping("/room")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @RequestMapping("roomList")
    @ResponseBody
    public List<Room> roomList(@RequestParam(value = "shop_id") int shop_id){
        return roomService.roomList(shop_id);
    }





}
