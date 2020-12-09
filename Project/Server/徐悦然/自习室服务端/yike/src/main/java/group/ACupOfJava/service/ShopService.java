package group.ACupOfJava.service;

import group.ACupOfJava.pojo.ImageBox;
import group.ACupOfJava.pojo.Shop;
import group.ACupOfJava.pojo.User;

import java.util.List;
import java.util.Map;
import java.util.Set;

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
    public int addLikes(Map<String,Integer> map);
    public int updateLikes(Map<String, Integer> map);
    public List<Shop> hotList();
    public List<Shop> talkList(List<String> list);
    public List<Shop> recentList(int id);

<<<<<<< HEAD
=======
    public List<ImageBox> findbannerImagesById(int shop_id);
    public List<ImageBox> bannerImages(int shop_id);
    public List<Shop> selectCity(String location);




>>>>>>> 29b4465a9eced31f7d87ed69c4d0b00b790f556e


}
