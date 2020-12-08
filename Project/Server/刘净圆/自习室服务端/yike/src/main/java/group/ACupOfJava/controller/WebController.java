package group.ACupOfJava.controller;

import group.ACupOfJava.pojo.User;
import group.ACupOfJava.service.UserService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;

import java.util.Map;

@Controller
@RequestMapping("/Web")
public class WebController {

    @Autowired
    private UserService webService;

    @RequestMapping("login")
    public void login(HttpServletRequest request,HttpServletResponse response){
        HttpSession session = request.getSession();
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        session.setAttribute("email", email);
        session.setAttribute("password", password);
        Map<String,String> map = new HashMap<>();
        map.put("email",email);
        map.put("password",password);
        User tag = webService.loginUser(map);
        System.out.println(tag);
        if (tag!=null){
            System.out.println("success");
            try {
                session.setAttribute("user_id", tag.getUserId());
                response.sendRedirect("http://123.57.63.212:8080/yike/seatorder.jsp");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            System.out.println("fail");;
        }
    }

}
