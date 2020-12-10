package group.ACupOfJava.util;

import group.ACupOfJava.pojo.Shop;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.Collections;
import java.util.List;

/**
 * ClassName:ImageUlti
 * Packeage:group.ACupOfJava.util
 *
 * @Date:2020/12/9 14:13
 */
public class ImageUtil {
    public static void dowmloadImage(String imageDir,String imageName ,HttpServletResponse response, HttpSession session) {
        try {
            File file = new File(session.getServletContext().getRealPath("/"+imageDir+"/") + imageName);
            OutputStream os = response.getOutputStream();
            FileInputStream fis = new FileInputStream(file);
            int len = 0;
            while ((len = fis.read()) != -1) {
                os.write(len);
            }
            fis.close();
            os.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
