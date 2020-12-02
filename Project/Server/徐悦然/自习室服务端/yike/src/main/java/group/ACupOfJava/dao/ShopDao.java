package group.ACupOfJava.dao;

import group.ACupOfJava.pojo.Shop;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * ClassName:ShopDao
 * Packeage:group.ACupOfJava.dao
 *
 * @Date:2020/11/22 10:35
 */


public interface ShopDao {
    @Select("select * from shop")
    public List<Shop> shopList();

    @Select("SELECT shop.* from user,shop,collection where user.user_id = collection.user_id and shop.shop_id = collection.shop_id and user.user_id = #{id}")
    public List<Shop> myShopList(int id);

    public Shop shopDetail(int shop_id);

    public int addCollection(Map<String,Integer> map);

    public int updateStars(Map<String, Integer> map);

    public List<Shop> hotList();



}
