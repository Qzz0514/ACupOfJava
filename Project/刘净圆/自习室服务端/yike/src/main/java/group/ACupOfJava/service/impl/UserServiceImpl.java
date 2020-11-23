package group.ACupOfJava.service.impl;

import group.ACupOfJava.dao.UserDao;
import group.ACupOfJava.pojo.User;
import group.ACupOfJava.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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
    public User loginUser(Map<String,String> map) {
        return userDao.loginUser(map);
    }
}
