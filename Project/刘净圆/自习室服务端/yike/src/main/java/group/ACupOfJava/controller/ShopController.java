package group.ACupOfJava.controller;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import group.ACupOfJava.pojo.Shop;
import group.ACupOfJava.pojo.User;
import group.ACupOfJava.service.ShopService;
import org.apache.ibatis.annotations.Result;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.PropertyVetoException;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassName:ShopController
 * Packeage:group.ACupOfJava.controller
 *
 * @Date:2020/11/22 10:48
 */
@Controller
@RequestMapping("/shop")
public class ShopController {

    @Autowired
    private ShopService shopService;



    @RequestMapping("find")
    @ResponseBody
    public String find(HttpServletRequest request, HttpServletResponse response) {
        Gson gson = new Gson();
        List<Shop> shops = shopService.shopList();
        System.out.println(shops);
        String str = gson.toJson(shops);
        return str;
    }

    @RequestMapping("uploadImages")
    @ResponseBody
    public void uploadImages(HttpServletResponse response) {
        try {
            for (Shop shop :shopService.shopList()){
                //files.add(new File(shop.getImage()+""));
                File file = new File("D:\\software-course\\project training\\ACupOfJava\\Project\\刘净圆\\自习室服务端\\yike\\webapp\\images\\"+shop.getImage());
                System.out.println(file.getAbsolutePath());
                OutputStream os = response.getOutputStream();
                FileInputStream fis = new FileInputStream(file);
                int len = 0;
                while ((len = fis.read())!=-1) {
                    os.write(len);

                }
                fis.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("receive")
    @ResponseBody
    public void receive(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter("image");
        List<Shop> shops = shopService.shopList();
        try {
            for (Shop shop :shopService.shopList()){
                if (name.equals(shop.getImage())) {
                    //files.add(new File(shop.getImage()+""));
                    File file = new File("D:\\software-course\\project training\\ACupOfJava\\Project\\刘净圆\\自习室服务端\\yike\\webapp\\images\\"+shop.getImage());
                    System.out.println(file.getAbsolutePath());
                    OutputStream os = response.getOutputStream();
                    FileInputStream fis = new FileInputStream(file);
                    int len = 0;
                    while ((len = fis.read())!=-1) {
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

    @RequestMapping("findMyLikes")
    @ResponseBody
    public String findMyLikes(){
        Gson gson = new Gson();
        List<Shop> shops = shopService.myShopList(1);
        System.out.println(shops);
        String str = gson.toJson(shops);
        return str;
    }


    @RequestMapping("myRevice")
    @ResponseBody
    public void myRevice(HttpServletRequest request, HttpServletResponse response){
        String name = request.getParameter("image");
        List<Shop> shops = shopService.myShopList(1);
        try {
            for (Shop shop : shops) {

                if (name.equals(shop.getImage())) {
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




    @Test
    public void test1(){
        List<Shop> shops = shopService.shopList();
        for (Shop shop : shops) {
            System.out.println(shop.toString());
        }
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
        List<Shop> query = jdbcTemplate.query("select * from shop",new BeanPropertyRowMapper<Shop>(Shop.class));
        //List<Shop> query = jdbcTemplate.query("SELECT shop.* from user,shop,collection where user.user_id = collection.user_id and shop.shop_id = collection.shop_id and user.user_id = 1", new BeanPropertyRowMapper<Shop>(Shop.class));
        for (Shop shop : query) {
            System.out.println(shop.toString());

        }
        //jdbcTemplate.update("insert into shop (name,image,location,starttime,endtime,likes,stars) values ('shop1','img1.jpg','sjz','2020-11-1','2020-12-31',1,1)");
        /*jdbcTemplate.update("insert into shop (name,image,location,starttime,endtime,likes,stars) values ('shop2','img2.jpg','sjz','2020-11-1','2020-12-31',1,1)");
        jdbcTemplate.update("insert into shop (name,image,location,starttime,endtime,likes,stars) values ('shop3','img3.jpg','sjz','2020-11-1','2020-12-31',1,1)");
        jdbcTemplate.update("insert into shop (name,image,location,starttime,endtime,likes,stars) values ('shop4','img4.jpg','sjz','2020-11-1','2020-12-31',1,1)");
        jdbcTemplate.update("insert into shop (name,image,location,starttime,endtime,likes,stars) values ('shop5','img5.jpg','sjz','2020-11-1','2020-12-31',1,1)");
        jdbcTemplate.update("insert into shop (name,image,location,starttime,endtime,likes,stars) values ('shop6','img6.jpg','sjz','2020-11-1','2020-12-31',1,1)");*/
        //jdbcTemplate.update("delete from shop");
    }
}


