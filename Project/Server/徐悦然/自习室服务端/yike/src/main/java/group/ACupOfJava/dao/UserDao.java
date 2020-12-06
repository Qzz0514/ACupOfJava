package group.ACupOfJava.dao;

import group.ACupOfJava.pojo.Shop;
import group.ACupOfJava.pojo.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface UserDao {
    public List<User> find();
    public User findById(String email);
    public void addImgPath(User user);


    //登录
    @Select("select * from user where email = #{email} and password = #{password}")
    public User loginUser(Map<String,String> map);

    //与用户聊过天的商家列表
    public List<Shop> talkList(int user_id);

    //创建聊天关系
    public int addTalkRelation(Map<String,Integer> map);

    //接收消息
    public int receiveMessage(Map<String, Integer> map);

    @Select("insert into user(name,password,email) values( #{name} , #{password} , #{email})")
    public User addUser(Map<String,String> map);

    @Select("selevt * from user where name = #{email}")
    public User getUserInfo(String email);

    //返回用户的id
    @Select("select user_id from user where email = #{email}")
    public int getCurrentUserId(String email);

    public User findUserById(int user_id);
}
