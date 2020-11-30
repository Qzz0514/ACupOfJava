package group.ACupOfJava.service.impl;

import group.ACupOfJava.dao.UserDao;
import group.ACupOfJava.pojo.Shop;
import group.ACupOfJava.pojo.User;
import group.ACupOfJava.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public List<User> find() {
        return userDao.find();
    }

    @Override
    public User findById(String email) {
        return userDao.findById(email);
    }

    @Override
    public void addImgPath(User user) {
        userDao.addImgPath(user);
    }

    @Override
    public int getCurrentUserId(String email){return userDao.getCurrentUserId(email);}

    @Override
    public User loginUser(Map<String,String> map) {
        return userDao.loginUser(map);
    }

    public List<Shop> talkList(int user_id){
        return userDao.talkList(user_id);
    }

    @Override
    public int addTalkRelation(Map<String, Integer> map) {
        return userDao.addTalkRelation(map);
    }


    public User addUser(Map<String,String> map){
        return userDao.addUser(map);
    }

    public User getUserInfo(String email){return userDao.getUserInfo(email);}

}
