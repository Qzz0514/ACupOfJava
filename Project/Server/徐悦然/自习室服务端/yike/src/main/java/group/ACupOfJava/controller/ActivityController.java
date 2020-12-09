package group.ACupOfJava.controller;

import com.sun.org.apache.regexp.internal.RE;
import group.ACupOfJava.pojo.Activitys;
import group.ACupOfJava.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * ClassName:ActivityController
 * Packeage:group.ACupOfJava.controller
 *
 * @Date:2020/12/6 8:27
 */
@Controller
@RequestMapping("/activity")
public class ActivityController {
    @Autowired
    private ActivityService activityService;

    @RequestMapping("findActivity")
    @ResponseBody
    public Activitys findActivity(@RequestParam(value = "shop_id") int shopId) {
        return activityService.findActivtyById(shopId);
    }



}
