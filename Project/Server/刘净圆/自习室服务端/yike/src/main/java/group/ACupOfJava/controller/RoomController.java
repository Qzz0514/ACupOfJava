package group.ACupOfJava.controller;
import group.ACupOfJava.pojo.Room;
import group.ACupOfJava.service.RoomService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
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

    @Test
    public void test() {

        String md5Str = DigestUtils.md5DigestAsHex("appkey=8b9e6dee5f36787f67b2e300&timestamp=1607251926000&random_str=022cd9fd995849b58b3ef0e943421ed9&key=923672eb00b7ef78356d39b2".getBytes());
        System.out.println(md5Str);
    }



}
