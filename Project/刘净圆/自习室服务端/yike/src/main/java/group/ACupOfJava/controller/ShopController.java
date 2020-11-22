package group.ACupOfJava.controller;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import group.ACupOfJava.pojo.Shop;
import group.ACupOfJava.service.ShopService;
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


    @Test
    public void test1(){
        List<Shop> shops = shopService.shopList();
        for (Shop shop : shops) {
            System.out.println(shop.toString());
        }
    }
}


