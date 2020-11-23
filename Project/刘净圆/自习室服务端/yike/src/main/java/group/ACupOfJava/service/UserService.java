package group.ACupOfJava.service;

import group.ACupOfJava.pojo.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
public interface UserService {
    public List<User> find();
    public User loginUser(Map<String,String> map);
}
