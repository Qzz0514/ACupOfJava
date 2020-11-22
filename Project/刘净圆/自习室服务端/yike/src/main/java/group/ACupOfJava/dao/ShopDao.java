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
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "name",property = "name"),
            @Result(column = "image",property = "image"),
            @Result(column = "location",property = "location"),
            @Result(column = "starttime",property = "starttime"),
            @Result(column = "endtime",property = "endtime"),
            @Result(column = "likes",property = "likes"),
            @Result(column = "stars",property = "stars")
    })
    public List<Shop> shopList();

}
