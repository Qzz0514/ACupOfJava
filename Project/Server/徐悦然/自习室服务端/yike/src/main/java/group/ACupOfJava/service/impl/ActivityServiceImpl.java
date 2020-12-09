package group.ACupOfJava.service.impl;

import group.ACupOfJava.dao.ActivityDao;
import group.ACupOfJava.pojo.Activitys;
import group.ACupOfJava.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ClassName:ActivityServiceImpl
 * Packeage:group.ACupOfJava.service.impl
 *
 * @Date:2020/12/6 8:27
 */
@Service
public class ActivityServiceImpl implements ActivityService {
    @Autowired
    private ActivityDao activityDao;

    @Override
    public Activitys findActivtyById(int shop_id) {
        return activityDao.findActivtyById(shop_id);
    }
}
