package group.ACupOfJava.controller;

import com.google.gson.Gson;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import group.ACupOfJava.pojo.Shop;
import group.ACupOfJava.pojo.User;
import group.ACupOfJava.service.UserService;
import group.ACupOfJava.util.StringUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.beans.PropertyVetoException;
import java.io.*;
import java.util.*;

@Controller
@RequestMapping("/user")
public class UserController {



    @Autowired
    private UserService userService;


    @RequestMapping("find")
    @ResponseBody
    public List<User> find() {
        return userService.find();
    }


    @RequestMapping(value = "updateHead", method = RequestMethod.POST)
    @ResponseBody
    public int updateHead(String email, MultipartFile file, HttpServletRequest request) {
        String filePath = userService.findById(email).getImage();
        // 如果没有图片路径赋予一个
        if (filePath == null) {
            // 获取后缀名
            String suffixName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            filePath = System.currentTimeMillis() + suffixName;
            User user = new User();
            user.setEmail(email);
            user.setImage(filePath);
            userService.addImgPath(user);
        }
        // 保存
        try {
            file.transferTo(new File(request.getSession().getServletContext().getRealPath("/headers/") + filePath));
            return 1;
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }




    //登录验证
    @RequestMapping("login")
    @ResponseBody
    public String login(String email, String password) {
        try {
            Map<String, String> map = new HashMap<>();
            map.put("email",email);
            map.put("password",password);
            User currentuser = userService.loginUser(map);

            if (StringUtil.isEmpty(email) || StringUtil.isEmpty(password) || currentuser == null) {
                System.out.println("登陆失败");
                return "login failed";
            }else{
                System.out.println("登陆成功");
                Gson gson = new Gson();
                String userJson = gson.toJson(currentuser);
                return userJson+"";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("talk")
    @ResponseBody
    public List<Shop> talkList(@RequestParam("user_id") int userId) {
        return userService.talkList(userId);
    }

    @RequestMapping("talkReceive")
    @ResponseBody
    public void myRevice(String image, @RequestParam("user_id") int userId, HttpServletResponse response, HttpSession session){
        List<Shop> shops = userService.talkList(userId);
        try {
            for (Shop shop : shops) {

                if (image.equals(shop.getImage())) {
                    File file = new File(session.getServletContext().getRealPath("/images/") + shop.getImage());
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

    //用户注册
    @RequestMapping("register")
    @ResponseBody
    public String register(String name, String email, String password){
        try {

            if (StringUtil.isEmpty(email) || StringUtil.isEmpty(password) ||StringUtil.isEmpty(name)) {
                return "error";
            }
            else {
                List<User> users = userService.find();
                List<String> emails = new ArrayList<>();
                for (User user: users) {
                    emails.add(user.getEmail());
                }
                if (!emails.contains(email)){
                    Map<String,String> map = new HashMap<>();
                    map.put("name",name);
                    map.put("email",email);
                    map.put("password",password);
                    User addUser = userService.addUser(map);
//                    System.out.println(addUser);
                    return "register success";
                }else {
                    return "this mail has been registerd!";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //建立聊天关系
    @RequestMapping("addTalkRelation")
    @ResponseBody
    public void addTalkRelation(@RequestParam("user_id") int userId, @RequestParam("shop_id") int shopId) {
        Map<String, Integer> map = new HashMap<>();
        map.put("user_id", userId);
        map.put("shop_id", shopId);
        int row = userService.addTalkRelation(map);
        System.out.println(row);
    }





}
