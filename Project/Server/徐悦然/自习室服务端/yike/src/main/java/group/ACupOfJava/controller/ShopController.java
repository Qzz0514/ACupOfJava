package group.ACupOfJava.controller;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import group.ACupOfJava.pojo.Shop;
import group.ACupOfJava.service.ShopService;
import group.ACupOfJava.util.JedisUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public List<Shop> find() {
        return shopService.shopList();
    }


    @RequestMapping("receive")
    @ResponseBody
    public void receive(@RequestParam(value = "image", required = false) String name, HttpServletResponse response, HttpSession session) {
        List<Shop> shops = shopService.shopList();
        try {
            for (Shop shop : shopService.shopList()) {
                if (name.equals(shop.getImage())) {
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

    @RequestMapping("findMyLikes")
    @ResponseBody
    public List<Shop> findMyLikes(@RequestParam(value = "user_id", required = false) int userId) {
        for (Shop shop : shopService.myShopList(userId)) {
            System.out.println(shop.getShopId());
        }
        return shopService.myShopList(userId);
    }


    @RequestMapping("myReceive")
    @ResponseBody
    public void myRevice(String image, int user_id, HttpServletResponse response, HttpSession session) {
        List<Shop> shops = shopService.myShopList(user_id);
        try {
            for (Shop shop : shops) {

                if (image.equals(shop.getImage())) {
                    File file = new File(session.getServletContext().getRealPath("/images/") + shop.getImage());
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

    //商店详情页面
    @RequestMapping("shopDetail")
    @ResponseBody
    public Shop shopDetail(@RequestParam(value = "shop_id") int shopId) {
        return shopService.shopDetail(shopId);
    }

    @RequestMapping("shopDetailImage")
    @ResponseBody
    public void shopDetailImage(@RequestParam(value = "image", required = false) String name, HttpServletResponse response, HttpSession session) {
        List<Shop> shops = shopService.shopList();
        try {
            for (Shop shop : shopService.shopList()) {
                if (name.equals(shop.getImage())) {
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

    //加入收藏
    @RequestMapping("addCollection")
    @ResponseBody
    public void addCollection(@RequestParam(value = "user_id") int userId, @RequestParam(value = "shop_id") int shopId) {
        Map<String, Integer> map = new HashMap<>();
        map.put("user_id", userId);
        map.put("shop_id", shopId);
        int row = shopService.addCollection(map);

        System.out.println(row);


    }

    //实现热门:
    // （1）更新Stars

    @RequestMapping("updateStars")
    @ResponseBody
    public void updateStars(@RequestParam("shop_id") int shopId, @RequestParam("newStars") int newStars) {
        Map<String, Integer> map = new HashMap<>();
        map.put("shop_id", shopId);
        map.put("newStars", newStars);
        shopService.updateStars(map);

    }

    //实现热门：
    //(2)根据Stars从大到小排序
    @RequestMapping("hot")
    @ResponseBody
    public List<Shop> hot(){
        return shopService.hotList();
    }


    //实现近期
    //更新Likes
    @RequestMapping("updateLikes")
    @ResponseBody
    public void updateLikes(@RequestParam("shop_id") int shopId, @RequestParam("newLikes") int newStars) {
        Map<String, Integer> map = new HashMap<>();
        map.put("shop_id", shopId);
        map.put("newLikes", newStars);
        shopService.updateLikes(map);

    }

    //实现近期
    //更新Likes


    //与用户建立聊天关系的商家列表
    @RequestMapping("talkList")
    @ResponseBody
    public List<Shop> talkList(@RequestParam("user_id") int userId){
        Jedis jedis = JedisUtil.geyJedis();
        Set<String> friends = jedis.smembers("friends_" + userId);
        List<String> list = new ArrayList<>(friends);
        return shopService.talkList(list);
    }

    @RequestMapping("talkImage")
    @ResponseBody
    public void talkImage(String image, int user_id, HttpServletResponse response, HttpSession session) {
        Jedis jedis = JedisUtil.geyJedis();
        Set<String> friends = jedis.smembers("friends_" + user_id);
        List<String> list = new ArrayList<>(friends);
        List<Shop> shops = shopService.talkList(list);
        try {
            for (Shop shop : shops) {

                if (image.equals(shop.getImage())) {
                    File file = new File(session.getServletContext().getRealPath("/images/") + shop.getImage());
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
}


