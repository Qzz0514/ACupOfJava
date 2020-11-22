package group.ACupOfJava.controller;

import group.ACupOfJava.pojo.Shop;
import group.ACupOfJava.service.ShopService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    public List<Shop> find(){

        return shopService.shopList();
    }


    @Test
    public void test1(){
        List<Shop> shops = shopService.shopList();
        for (Shop shop : shops) {
            System.out.println(shop.toString());
        }
    }
}


