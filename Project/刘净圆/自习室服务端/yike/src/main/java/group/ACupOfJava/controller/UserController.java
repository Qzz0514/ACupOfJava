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
import java.io.*;
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
        map.put("email", "1233456");
        map.put("password", "123");
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
                request.setAttribute("error", "账户或密码为空");
                request.getRequestDispatcher("login.jsp").forward(request, response);

            } else {
                Map<String, String> map = new HashMap<>();
                map.put("email", email);
                map.put("password", password);
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

    @RequestMapping("talk")
    @ResponseBody
    public List<Shop> talkList(HttpServletRequest request) {
        int  user_id = Integer.parseInt(request.getParameter("user_id"));
        return userService.talkList(user_id);
    }

    @RequestMapping("talkReceive")
    @ResponseBody
    public void myRevice(String image, int user_id , HttpServletResponse response){
        List<Shop> shops = userService.talkList(user_id);
        try {
            for (Shop shop : shops) {

                if (image.equals(shop.getImage())) {
                    //files.add(new File(shop.getImage()+""));
                    File file = new File("D:\\software-course\\project training\\ACupOfJava\\Project\\刘净圆\\自习室服务端\\yike\\webapp\\images\\" + shop.getImage());
                    System.out.println(file.getAbsolutePath());
                    OutputStream os = response.getOutputStream();
                    FileInputStream fis = new FileInputStream(file);
                    int len = 0;
                    while ((len = fis.read()) != -1) {
                        os.write(len);
                    }
                    fis.close();
                    os.close();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("addTalkRelation")
    @ResponseBody
    public void addTalkRelation(HttpServletRequest request) {

        Integer user_id = Integer.parseInt(request.getParameter("user_id"));
        Integer shop_id = Integer.parseInt(request.getParameter("shop_id"));
        Map<String, Integer> map = new HashMap<>();
        map.put("user_id", user_id);
        map.put("shop_id", shop_id);
        int row = userService.addTalkRelation(map);
        System.out.println(row);
    }



    @Test
    public void Test() throws PropertyVetoException {

        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        ComboPooledDataSource source = new ComboPooledDataSource();
        source.setDriverClass("com.mysql.jdbc.Driver");
        /*source.setJdbcUrl("jdbc:mysql://123.57.63.212:3306/yike");
        source.setUser("root");
        source.setPassword("0814Xyr2000@me");*/
        source.setJdbcUrl("jdbc:mysql://localhost:3306/yike");
        source.setUser("root");
        source.setPassword("");
        jdbcTemplate.setDataSource(source);
        List<User> query = jdbcTemplate.query("select * from user", new BeanPropertyRowMapper<User>(User.class));
        System.out.println(query.get(0));
        //jdbcTemplate.update("insert into shop (name,image,location,starttime,endtime,likes,stars) values ('shop1','img1.jpg','sjz','2020-11-1 00:00','2020-12-31 00:00',1,1)");
        /*
        jdbcTemplate.update("insert into shop (name,image,location,starttime,endtime,likes,stars) values ('shop2','img2.jpg','sjz','2020-11-1 00:00','2020-12-31 00:00',1,1)");
        jdbcTemplate.update("insert into shop (name,image,location,starttime,endtime,likes,stars) values ('shop3','img3.jpg','sjz','2020-11-1 00:00','2020-12-31 00:00',1,1)");
        jdbcTemplate.update("insert into shop (name,image,location,starttime,endtime,likes,stars) values ('shop4','img4.jpg','sjz','2020-11-1 00:00','2020-12-31 00:00',1,1)");
        jdbcTemplate.update("insert into shop (name,image,location,starttime,endtime,likes,stars) values ('shop5','img5.jpg','sjz','2020-11-1 00:00','2020-12-31 00:00',1,1)");
        jdbcTemplate.update("insert into shop (name,image,location,starttime,endtime,likes,stars) values ('shop6','img6.jpg','sjz','2020-11-1 00:00','2020-12-31 00:00',1,1)");*/
        //jdbcTemplate.update("delete from shop");

        //
    }


    @Test
    public void test2() {

    }


}
