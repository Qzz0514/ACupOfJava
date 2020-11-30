package group.ACupOfJava.service;

import group.ACupOfJava.pojo.Shop;

import java.util.List;

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
}
