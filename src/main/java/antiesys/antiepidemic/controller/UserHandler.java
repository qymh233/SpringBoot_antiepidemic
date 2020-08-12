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
/*
* 101010201
* 111111
* */
@Controller
@RequestMapping("/user")
@SessionAttributes(value = {"user","umsglist","usermessage"})
public class UserHandler {
    @Autowired
    UserService userService;

    //查询用户信息

    /**
     * 查询用户信息
     * @param model 模型
     * @return 个人信息页面
     */
    @RequestMapping(path="/findUsesOne")
    public String FindUserOne(Model model){

        Users users = (Users)model.getAttribute("user");

        if(users == null)
            return "ErrorPage";

        return "views/UserViewPersonalInformationPage";
    }
    /**
     * 获取用户信息
     * @param userId 用户ID
     * @param request request
     * @param model 模型
     * @return 个人信息界面
     */
    @RequestMapping(path="/findUsesOneUpdate")
    public String findUsesOneUpdate(@RequestParam(name = "userId") int userId, HttpServletRequest request, Model model){

        Users users = userService.FindUserOne(userId);

        if(users == null)
            return "ErrorPage";

        request.setAttribute("user", users);

        return "UserModifyPersonalInformationPage";
    }
    /**
     * 修改用户信息
     * @param userName 用户名
     * @param userAge 年龄
     * @param userPhone 电话
     * @param userSex 性别
     * @param model 模型
     * @return 个人信息界面
     */
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

    /**
     * 修改用户页面
     * @param model 模型
     * @return 修改用户信息界面
     */
    @RequestMapping(path="/changeUserPage")
    public String ChangeUserPage(Model model){

        return "views/UserModifyPersonalInformationPage";
    }
    /**
     * 修改用户密码界面
     * @param model 模型
     * @return 修改用户密码界面
     */
    @RequestMapping(path="/ChangePassWordPage")
    public String ChangePassWordPage(Model model){

        return "views/UserChangePasswordPage";
    }
    /**
     * 修改用户密码
     * @param newPW 新密码
     * @param model 模型
     * @return 用户登录界面
     */
    @RequestMapping(path="/changePassWord")
    public String ChangePassWord(@RequestParam(name = "newPW") String newPW, Model model){
        Users user=(Users) model.getAttribute("user");
        boolean isChange = userService.ChangePassword(user.getUserId(), user.getUserPW(), newPW);

        if(!isChange)
            return "views/UserChangePasswordPage";

        return "redirect:/UserLoginPage.html";
    }
    //查询疫情防控信息
    /**
     * 查询疫情防控信息
     * @param model 模型
     * @return 疫情防控信息界面
     */
    @RequestMapping(path="/releaseInformation")
    public String ReleaseInformation(Model model){

        List<Message> messageList=userService.FindMessageAll();
        model.addAttribute("umsglist",messageList);
        return "views/UserViewEpidemicPreventionInformationPage";
    }
    /**
     * 查看具体防控信息
     * @param meId 信息ID
     * @param model 模型
     * @return 具体防疫信息界面
     */
    @RequestMapping(path = "/taskCompletion")
    public String TaskCompletion(@RequestParam(name = "meId") Integer meId, Model model){

        Message message=userService.FindMessageOne(meId);
        model.addAttribute("usermessage",message);

        return "views/UserRecordPage-Details";
    }

    @RequestMapping(path = "/UserFindOneReportPage")
    public String UserFindOneReportPage(@RequestParam(name = "meId") Integer meId, Model model){

        Message message=userService.FindMessageOne(meId);
        model.addAttribute("usermessage",message);

        return "views/UserRecordPage-Details";
    }

}
