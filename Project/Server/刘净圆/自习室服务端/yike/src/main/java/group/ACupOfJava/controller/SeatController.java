package group.ACupOfJava.controller;

import com.google.gson.JsonObject;
import group.ACupOfJava.pojo.Room;
import group.ACupOfJava.pojo.Seat;
import group.ACupOfJava.service.SeatService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/seat")
public class SeatController {

    @Autowired
    private SeatService seatService;

    @RequestMapping("occupy")
    @ResponseBody
    public void occupySeats(@RequestBody List<Seat> seats) {
        seatService.takeSeats(seats);
    }

    @RequestMapping("getRoomRowAndCol")
    @ResponseBody
    public Room getRoomRowAndCol(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();
        String room_id = request.getParameter("roomid");
        request.setAttribute("roomid",room_id);
        Room room = seatService.getRoomRowAndCol(room_id);
        System.out.println(room.toString());
        return room;
    }

}
