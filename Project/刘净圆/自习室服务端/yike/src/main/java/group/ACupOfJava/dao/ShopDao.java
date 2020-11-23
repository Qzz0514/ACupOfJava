package group.ACupOfJava.dao;

import group.ACupOfJava.pojo.Shop;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.junit.Test;

import java.util.List;

/**
 * ClassName:ShopDao
 * Packeage:group.ACupOfJava.dao
 *
 * @Date:2020/11/22 10:35
 */
public interface ShopDao {
    @Select("select * from shop")
    public List<Shop> shopList();

}
