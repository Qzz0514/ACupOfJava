package group.ACupOfJava.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.inject.internal.cglib.core.$ReflectUtils;
import com.google.inject.internal.util.$Nullable;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import group.ACupOfJava.pojo.Shop;
import group.ACupOfJava.pojo.User;
import group.ACupOfJava.service.UserService;
import group.ACupOfJava.util.ImageUtil;
import group.ACupOfJava.util.JedisUtil;
import group.ACupOfJava.util.MailUtil;
import group.ACupOfJava.util.StringUtil;
import org.json.JSONObject;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.Jedis;

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
        for (Shop shop : shops) {
            if (image.equals(shop.getImage())) {
                ImageUtil.dowmloadImage("images", shop.getImage(), response, session);
            }
        }
    }

    //用户注册
    @RequestMapping("register")
    @ResponseBody
    public String register(String name, String email, String password){
        try {
            if (StringUtil.isEmpty(email) || StringUtil.isEmpty(password) ||StringUtil.isEmpty(name) || !email.contains("@")) {
                return "error";
            } else {
                List<User> users = userService.find();
                List<String> emails = new ArrayList<>();
                for (User user: users) {
                    emails.add(user.getEmail());
                }
                if (!emails.contains(email)){

                    Jedis jedis = JedisUtil.geyJedis();
                    // 加密
                    String md5Str = DigestUtils.md5DigestAsHex((email + name + password).getBytes());
                    // 存数据
                    jedis.hset(md5Str, "email", email);
                    jedis.hset(md5Str, "name", name);
                    jedis.hset(md5Str, "password", password);
                    // 存储有效时间
                    jedis.expire(md5Str, 60 * 5); // 五分钟的有效时间

                    // 调用邮箱验证
                    MailUtil.sendMail(email,
                            "此邮件仅为邮箱注册使用，如非本人，无视即可。<br> 开启验证：" +
                                    "<a href=\"http://123.57.63.212:8080/yike/user/makesure?code=" + md5Str + "\" />点击激活账号</a>");
                    return "ready to makesure";
                } else {
                    return "this mail has been registerd!";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("makesure")
    @ResponseBody
    public String makesureMail(String code) {
        // 找信息，看时间
        Jedis jedis = JedisUtil.geyJedis();
        Map<String, String> mesMap = jedis.hgetAll(code);
        jedis.del(code);
        if (mesMap.isEmpty()) {
            // 已经过期
            return "retry register";
        } else {
            User addUser = userService.addUser(mesMap);
            return "register success";
        }
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


    //上传用户头像
    @RequestMapping("uploadUserImage")
    @ResponseBody
    public void uploadUserImage(@RequestParam(value = "image", required = false) String name, HttpServletResponse response, HttpSession session) {
        for (User user:userService.find()) {
            if (name.equals(user.getImage())) {
                ImageUtil.dowmloadImage("headers",user.getImage(),response,session);
            }
        }
    }

    //通过id查找用户
    @RequestMapping("findUserById")
    @ResponseBody
    public User findUserById(@RequestParam(value = "user_id") int userId) {
        User user = userService.findUserById(userId);
        return user;
    }

    @RequestMapping("transferVoice")
    @ResponseBody
    public String uploadVoice(MultipartFile file, HttpSession session) {
        try {
            file.transferTo(new File(session.getServletContext().getRealPath("/voice/") + file.getName()));
            return "up_success";
        } catch (IOException e) {
            e.printStackTrace();
            return "up_failed";
        }

    }


    @RequestMapping("downVoice")
    @ResponseBody
    public void downVoice(String path, HttpSession session, HttpServletResponse response) throws IOException {
        String realPath = session.getServletContext().getRealPath("voice/") + path;
        InputStream in = new FileInputStream(realPath);
        OutputStream out = response.getOutputStream();

        byte[] b = new byte[1024];
        int len;

        while ((len = in.read(b)) != -1) {
            out.write(b, 0, len);
        }

        in.close();
        out.close();
    }


    @RequestMapping("getLastMes")
    @ResponseBody
    public List<String> getLastMes(@RequestParam("user_id") int userId, @RequestParam("shop_id") List<String> shops) {
        List<String> lastMes = new ArrayList<>();
        Jedis jedis = JedisUtil.geyJedis();
        for (String shopId : shops) {
            String key1 = userId + "_" + shopId;
            String key2 = shopId + "_" + userId;

            List<String> m1 = jedis.lrange(key1, -1, -1);
            String mes1 = null;
            if (m1.size() > 0) {
                mes1 = m1.get(0);
            }
            List<String> m2 = jedis.lrange(key2, -1, -1);
            String mes2 = null;
            if (m2.size() > 0) {
                mes2 = m2.get(0);
            }

            if (mes1 == null && mes2 == null) lastMes.add(null);
            else if (mes1 == null) lastMes.add(mes2);
            else if (mes2 == null) lastMes.add(mes1);
            else {
                JSONObject trans1 = new JSONObject(mes1);
                JSONObject trans2 = new JSONObject(mes2);

                long time1 = trans1.getLong("time");
                long time2 = trans2.getLong("time");

                if (time1 > time2) lastMes.add(mes1);
                else lastMes.add(mes2);
            }
        }
        jedis.close();
        return lastMes;
    }

    @RequestMapping("getAllMes")
    public List<String> getAllMes(@RequestParam("user_id") int userId, @RequestParam("shop_id") int shopId) {
        Jedis jedis = JedisUtil.geyJedis();
        String key = userId + "_" + shopId;
        List<String> mes = jedis.lrange(key, 0, -1);
        jedis.close();
        return mes;
    }


    @RequestMapping("getHead")
    @ResponseBody
    public void getHeadImg(String path, HttpSession session, HttpServletResponse response) throws IOException {
        String realPath = session.getServletContext().getRealPath("/headers/") + path;
        OutputStream out = response.getOutputStream();
        InputStream in = new FileInputStream(realPath);

        byte[] b = new byte[1024];
        int len;

        while ((len = in.read(b)) != -1) {
            out.write(b, 0, len);
        }

        in.close();
        out.close();
    }


}
