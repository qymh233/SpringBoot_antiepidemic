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


    @RequestMapping("/")
    public String mypage(){
        return "login";
    }
    @RequestMapping("/he")
    public String mypag(){
        return "hello";
    }
    @RequestMapping("/u")
    public String upage(){
        return "UserLoginPage";
    }
    @RequestMapping("/m")
    public String mpage(){
        return "ManagerLoginPage";
    }

}
