package group.ACupOfJava.service.impl;
import group.ACupOfJava.dao.ShopDao;
import group.ACupOfJava.pojo.Shop;
import group.ACupOfJava.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * ClassName:ShopSerivceImpl
 * Packeage:group.ACupOfJava.service.impl
 *
 * @Date:2020/11/22 10:47
 */
@Service
public class ShopSerivceImpl implements ShopService {
    @Autowired
    private ShopDao shopDao;

    @Override
    public List<Shop> shopList() {
        return shopDao.shopList();
    }

    public List<Shop> myShopList(int id) {
        return shopDao.myShopList(id);
    }

    @Override
    public Shop shopDetail(int shop_id) {
        return shopDao.shopDetail(shop_id);
    }

    @Override
    public int addCollection(Map<String, Integer> map) {
        return shopDao.addCollection(map);
    }

    public int updateStars(Map<String, Integer> map) {
        return shopDao.updateStars(map);
    }

    @Override
    public List<Shop> hotList() {
        return shopDao.hotList();
    }

    @Override
    public List<Shop> talkList(int id) {
        return shopDao.talkList(id);
    }


}
