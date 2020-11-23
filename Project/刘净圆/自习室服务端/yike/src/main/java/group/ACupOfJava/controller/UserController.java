package group.ACupOfJava.controller;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import group.ACupOfJava.pojo.Shop;
import group.ACupOfJava.pojo.User;
import group.ACupOfJava.service.UserService;
import group.ACupOfJava.service.impl.UserServiceImpl;
import group.ACupOfJava.util.StringUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.misc.ASCIICaseInsensitiveComparator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.beans.PropertyVetoException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {


    @Autowired
    private UserService userService;

    @RequestMapping("find")
    @ResponseBody
    public String find() {
        System.out.println(userService.find());
        Map<String, String> map = new HashMap<>();
        map.put("email","1233456");
        map.put("password","123");
        User user = userService.loginUser(map);
        System.out.println(user);
        return "ok";
    }

    //登录验证
    @RequestMapping("login")
    @ResponseBody
    public void login(HttpServletRequest request, HttpServletResponse response) {

        try {
            request.setCharacterEncoding("utf-8");
            HttpSession session = request.getSession();
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            request.setAttribute("email", email);
            request.setAttribute("password", password);
            if (StringUtil.isEmpty(email) || StringUtil.isEmpty(password)) {
                request.setAttribute("error","账户或密码为空");
                request.getRequestDispatcher("login.jsp").forward(request, response);

            }
            else {
                Map<String, String> map = new HashMap<>();
                map.put("email",email);
                map.put("password",password);
                User currentuser = userService.loginUser(map);
                if (currentuser == null) {
                    request.setAttribute("error", "用户名或密码错误");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                } else {
                    session.setAttribute("currentUser", currentuser);
                    response.sendRedirect("xxx");

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }





    @Test
    public void Test() throws PropertyVetoException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        ComboPooledDataSource source = new ComboPooledDataSource();
        source.setDriverClass("com.mysql.jdbc.Driver");
        source.setJdbcUrl("jdbc:mysql://123.57.63.212:3306/yike");
        source.setUser("root");
        source.setPassword("0814Xyr2000@me");
        jdbcTemplate.setDataSource(source);
        List<User> query = jdbcTemplate.query("select * from user", new BeanPropertyRowMapper<User>(User.class));
        System.out.println(query.get(0));
        //jdbcTemplate.update("insert into shop (name,image,location,starttime,endtime,likes,stars) values ('shop1','img1.jpg','sjz','2020-11-1','2020-12-31',1,1)");
        /*jdbcTemplate.update("insert into shop (name,image,location,starttime,endtime,likes,stars) values ('shop2','img2.jpg','sjz','2020-11-1','2020-12-31',1,1)");
        jdbcTemplate.update("insert into shop (name,image,location,starttime,endtime,likes,stars) values ('shop3','img3.jpg','sjz','2020-11-1','2020-12-31',1,1)");
        jdbcTemplate.update("insert into shop (name,image,location,starttime,endtime,likes,stars) values ('shop4','img4.jpg','sjz','2020-11-1','2020-12-31',1,1)");
        jdbcTemplate.update("insert into shop (name,image,location,starttime,endtime,likes,stars) values ('shop5','img5.jpg','sjz','2020-11-1','2020-12-31',1,1)");
        jdbcTemplate.update("insert into shop (name,image,location,starttime,endtime,likes,stars) values ('shop6','img6.jpg','sjz','2020-11-1','2020-12-31',1,1)");*/
        //jdbcTemplate.update("delete from shop");
    }


    @Test
    public void test2(){

    }




}
