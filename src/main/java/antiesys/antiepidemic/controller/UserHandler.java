package antiesys.antiepidemic.controller;

import antiesys.antiepidemic.pojo.Message;
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

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/user")
@SessionAttributes(value = {"user","umsglist","usermessage"})
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
    //查询疫情防控信息
    @RequestMapping(path="/releaseInformation")
    public String ReleaseInformation(Model model){

        List<Message> messageList=userService.FindMessageAll();
        model.addAttribute("umsglist",messageList);
        return "views/UserViewEpidemicPreventionInformationPage";
    }
    //查看具体防控信息
    @RequestMapping(path = "/taskCompletion")
    public String TaskCompletion(@RequestParam(name = "meId") Integer meId, Model model){

        Message message=userService.FindMessageOne(meId);
        model.addAttribute("usermessage",message);

        return "views/UserRecordPage-Details";
    }

}
