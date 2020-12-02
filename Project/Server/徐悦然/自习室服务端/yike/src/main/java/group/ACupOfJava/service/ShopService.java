package group.ACupOfJava.service;

import group.ACupOfJava.pojo.Shop;

import java.util.List;
import java.util.Map;

/**
 * ClassName:ShopService
 * Packeage:group.ACupOfJava.service
 *
 * @Date:2020/11/22 10:46
 */
public interface ShopService {
    public List<Shop> shopList();
    public List<Shop> myShopList(int id);
    public Shop shopDetail(int shop_id);
    public int addCollection(Map<String,Integer> map);
    public int updateStars(Map<String, Integer> map);
    public List<Shop> hotList();
    

}
