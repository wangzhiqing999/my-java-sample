package cn.wzq.studykaptcha.controller;


import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;



@Controller
public class TestController {

	
	@Autowired
    DefaultKaptcha defaultKaptcha;
	
	

	
	
	  /**
     * 显示验证码
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/defaultKaptcha")
    public void defaultKaptcha(HttpServletRequest request, HttpServletResponse response) throws Exception {
        byte[] captchaChallengeAsJpeg = null;
        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
        try {
            //生产验证码字符串并保存到session中
            String createText = defaultKaptcha.createText();
            request.getSession().setAttribute("verifyCode", createText);
            //使用生产的验证码字符串返回一个BufferedImage对象并转为byte写入到byte数组中
            BufferedImage challenge = defaultKaptcha.createImage(createText);
            ImageIO.write(challenge, "jpg", jpegOutputStream);
        } catch (IllegalArgumentException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        //定义response输出类型为image/jpeg类型，使用response输出流输出图片的byte数组
        captchaChallengeAsJpeg = jpegOutputStream.toByteArray();
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        ServletOutputStream responseOutputStream =
                response.getOutputStream();
        responseOutputStream.write(captchaChallengeAsJpeg);
        responseOutputStream.flush();
        responseOutputStream.close();
    }
    
    
    
	
    /**
     * 映射 test.html
     * @return
     */
    @GetMapping({"","/","/index"})
    public String index() {
        return "/index";
    }
	
	
	    
	
	
    
    /**
     * 验证码输入验证
     * @param model
     * @param session
     * @param verifyCode
     * @return
     */
    @GetMapping("/verifyCode")
    public String imgverifyControllerDefaultKaptcha(Model model, HttpSession session, String verifyCode) {
        String captchaId = (String) session.getAttribute("verifyCode");
        System.out.println("验证码是：" + captchaId);
        System.out.println("用户输入的是：" + verifyCode);
        if (!captchaId.equals(verifyCode)) {
            System.out.println("输入错误");
            model.addAttribute("info", "错误的验证码");
        } else {
            System.out.println("输入正确");
            model.addAttribute("info", "正确");
        }
        return "/index";
    }    
    
    
    
	
}
