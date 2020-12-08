package group.ACupOfJava.dao;

import group.ACupOfJava.pojo.Activitys;

/**
 * ClassName:ActivityDao
 * Packeage:group.ACupOfJava.dao
 *
 * @Date:2020/12/6 8:25
 */

public interface ActivityDao {
    public Activitys findActivtyById(int shop_id);

}
