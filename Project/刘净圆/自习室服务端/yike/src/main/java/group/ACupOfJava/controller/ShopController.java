package group.ACupOfJava.controller;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import group.ACupOfJava.pojo.Shop;
import group.ACupOfJava.service.ShopService;
import org.apache.ibatis.annotations.Result;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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



    @Test
    public void test1(){
        List<Shop> shops = shopService.shopList();
        for (Shop shop : shops) {
            System.out.println(shop.toString());
        }
    }
}


