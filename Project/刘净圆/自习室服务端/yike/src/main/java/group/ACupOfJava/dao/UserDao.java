package group.ACupOfJava.dao;

import group.ACupOfJava.pojo.Shop;
import group.ACupOfJava.pojo.User;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface UserDao {

    public List<User> find();

    //登录
    @Select("select * from user where email = #{email} and password = #{password}")
    public User loginUser(Map<String,String> map);

    //与用户聊过天的商家列表
    public List<Shop> talkList(int user_id);

    //创建聊天关系
    public int addTalkRelation(Map<String,Integer> map);

    //接收消息
    public int receiveMessage(Map<String, Integer> map);



}
