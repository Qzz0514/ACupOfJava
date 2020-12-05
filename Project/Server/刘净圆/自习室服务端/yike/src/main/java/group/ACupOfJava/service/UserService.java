package group.ACupOfJava.service;

import group.ACupOfJava.pojo.Shop;
import group.ACupOfJava.pojo.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    public List<User> find();
    public User findById(String email);
    public void addImgPath(User user);

    public User loginUser(Map<String, String> map);
    public User addUser(Map<String, String> map);
    public User getUserInfo(String email);
    public int getCurrentUserId(String email);

    public List<Shop> talkList(int user_id);

    public int addTalkRelation(Map<String,Integer> map);
}
