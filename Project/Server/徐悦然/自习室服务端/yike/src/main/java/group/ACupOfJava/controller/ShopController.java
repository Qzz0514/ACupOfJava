package group.ACupOfJava.controller;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import group.ACupOfJava.pojo.Shop;
import group.ACupOfJava.service.ShopService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.beans.PropertyVetoException;
import java.io.*;
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
    public List<Shop> find() {
        return shopService.shopList();
    }



    @RequestMapping("receive")
    @ResponseBody
    public void receive(@RequestParam(value = "image", required = false) String name, HttpServletResponse response, HttpSession session) {
        List<Shop> shops = shopService.shopList();
        try {
            for (Shop shop :shopService.shopList()){
                if (name.equals(shop.getImage())) {
                    File file = new File(session.getServletContext().getRealPath("/images/")+shop.getImage());
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
    public List<Shop> findMyLikes(@RequestParam(value = "user_id", required = false) int userId) {
        return  shopService.myShopList(userId);
    }


    @RequestMapping("myReceive")
    @ResponseBody
    public void myRevice(String image, int user_id, HttpServletResponse response, HttpSession session){
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







}


