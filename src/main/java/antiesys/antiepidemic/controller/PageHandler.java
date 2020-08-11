package antiesys.antiepidemic.controller;

import antiesys.antiepidemic.pojo.Manager;
import antiesys.antiepidemic.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller

public class PageHandler {


    /**
     * 系统主界面
     * @return 系统主界面
     */
    @RequestMapping("/")
    public String mypage(){
        return "SystemMainPage";
    }
    @RequestMapping("/he")
    public String mypag(){
        return "hello";
    }

    /**
     * 用户登录
     * @return 用户登录界面
     */
    @RequestMapping("/u")
    public String upage(){
        return "UserLoginPage";
    }

    /**
     * 管理员登录
     * @return 管理员登录界面
     */
    @RequestMapping("/m")
    public String mpage(){ return "ManagerLoginPage"; }

    /**
     * 外来人员注册
     * @return 外来人员登录界面
     */
    @RequestMapping("/r")
    public String rpage(){ return "RegistrationOfOutsidersPage"; }


}
