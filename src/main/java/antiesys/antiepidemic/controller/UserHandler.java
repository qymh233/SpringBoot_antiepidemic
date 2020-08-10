package antiesys.antiepidemic.controller;

import antiesys.antiepidemic.pojo.Report;
import antiesys.antiepidemic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import antiesys.antiepidemic.pojo.Users;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/user")
@SessionAttributes(value = {"user","nummap","number"})
public class UserHandler {
    @Autowired
    UserService userService;

    //查询用户信息
    @RequestMapping(path="/findUsesOne")
    public String FindUserOne(Model model){

        Users users = (Users)model.getAttribute("user");

        if(users == null)
            return "ErrorPage";

        return "views/UserViewPersonalInformationPage";
    }

    //更新时获取用户信息
    @RequestMapping(path="/findUsesOneUpdate")
    public String findUsesOneUpdate(@RequestParam(name = "userId") int userId, HttpServletRequest request, Model model){

        Users users = userService.FindUserOne(userId);

        if(users == null)
            return "ErrorPage";

        request.setAttribute("user", users);

        return "UserModifyPersonalInformationPage";
    }

    //修改用户信息
    @RequestMapping(path="/changeUser")
    public String ChangeUser(@RequestParam(name = "userName") String userName,@RequestParam(name = "userAge") Integer userAge,@RequestParam(name = "userPhone") Long userPhone,@RequestParam(name = "userSex") String userSex,Model model){
        Users user=(Users) model.getAttribute("user");
        if(!userName.equals("")&&userName!=null)
            user.setUserName(userName);
        if(userAge!=null){
            user.setUserAge(userAge);
        }
        if(userPhone!=null)
            user.setUserPhone(userPhone);
        if(!userSex.equals(" ")&&userSex!=null)
            user.setUserSex(userSex);
        int numbers = userService.ChangeUser(user);

        if(numbers == 0)
            return "views/UserModifyPersonalInformationPage";

        return "views/UserViewPersonalInformationPage";
    }
    //修改用户信息页面
    @RequestMapping(path="/changeUserPage")
    public String ChangeUserPage(Model model){

        return "views/UserModifyPersonalInformationPage";
    }
    //修改用户密码页面
    @RequestMapping(path="/ChangePassWordPage")
    public String ChangePassWordPage(Model model){

        return "views/UserChangePasswordPage";
    }
    //修改用户密码
    @RequestMapping(path="/changePassWord")
    public String ChangePassWord(@RequestParam(name = "newPW") String newPW, Model model){
        Users user=(Users) model.getAttribute("user");
        boolean isChange = userService.ChangePassword(user.getUserId(), user.getUserPW(), newPW);

        if(!isChange)
            return "views/UserChangePasswordPage";

        return "UserLoginPage";
    }
    //用户注册
    @RequestMapping(path="/userRegister", produces="text/html;charset=utf-8")
    @ResponseBody
    public String UserRegister(Users user, Model model){

        int id = (int)(Math.random()*1000000000);
        String num = "" + (int)(Math.random()*1000000);

        user.setUserPW(num);
        user.setUserId(id);

        boolean isRegister = userService.UserRegister(user);

        if(!isRegister)
            return "注册失败";

        return "注册成功 您的id为："+ id +",您的初始密码为：" + num;
    }
    //用户登录
    @RequestMapping(path="/userLogin")
    public String UserLogin(@RequestParam(name = "userId") int userId, @RequestParam(name = "userPW") String userPW, HttpSession session, Model model){

        boolean isLogin = userService.UserLogin(userId, userPW);

        if(!isLogin)
            return "UserLoginPage";
        session.setAttribute("userId", userId);
        Users user=userService.FindUserOne(userId);
        int num = (int)(Math.random()*1000);
        model.addAttribute("number",num);
        model.addAttribute("user",user);
        return "UserMainPage";
    }
    @RequestMapping("/getnumpage")
    public String getnum(Model model){
        Users user=(Users)model.getAttribute("user");

        int num=(Integer)model.getAttribute("number");
        Map<Integer,Integer> nummap =(Map<Integer, Integer>)model.getAttribute("nummap");

        nummap.put(num,user.getUserId());

        model.addAttribute("nummap",nummap);
        return "views/UserSerialNumber";
    }
//    //生成序列号
//    @RequestMapping(path="/getNumber", produces="text/html;charset=utf-8")
//    @ResponseBody
//    public String GetNumber(Model model){
//
//        return  "您的序列号为："+ num;
//    }
    //查询疫情防控信息
    @RequestMapping(path="/releaseInformation")
    public ModelAndView ReleaseInformation(HttpSession session, Model model){

        ModelAndView modelAndView = new ModelAndView();

        String information = (String)session.getAttribute("information");

        if(information == null)
            modelAndView.setViewName("ErrorPage");

        modelAndView.setViewName("UserViewEpidemicPreventionInformationPage");
        modelAndView.addObject("information",information);

        return modelAndView;
    }
    //退出controller
    @RequestMapping(path = "/userExit")
    public String toMain(HttpSession session, Model model){
        session.removeAttribute("userId");
        return "redirect:/index.jsp";
    }

}
