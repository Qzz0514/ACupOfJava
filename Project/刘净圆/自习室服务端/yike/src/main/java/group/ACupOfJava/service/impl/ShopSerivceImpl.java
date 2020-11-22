package group.ACupOfJava.service.impl;
import group.ACupOfJava.dao.ShopDao;
import group.ACupOfJava.pojo.Shop;
import group.ACupOfJava.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
