package antiesys.antiepidemic.controller;

import antiesys.antiepidemic.pojo.*;
import antiesys.antiepidemic.service.AdminService;
import antiesys.antiepidemic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@SessionAttributes(value = {"user","manager","userlist","goodslist","admuser","good","msglist","reportList"})
public class AdminPageHandler {
    @Autowired
    AdminService adminService;
    @Autowired
    UserService userService;

    /**
     * 管理员登录
     * @param adminId 管理员ID
     * @param adminPW 管理员密码
     * @param session session
     * @param model 模型
     * @return 管理员主界面
     */
    @RequestMapping(path="/adminLogin")
    public String AdminLogin(@RequestParam(name = "adminId") int adminId, @RequestParam(name = "adminPW") String adminPW, HttpSession session, Model model){
        Manager manager=new Manager();
        manager.setAdminId(adminId);
        manager.setAdminPW(adminPW);
        boolean isLogin = adminService.AdminLogin(manager);
        session.setAttribute("adminId", manager.getAdminId());
        model.addAttribute("manager",manager);
        if(!isLogin) {
            return "ManagerLoginPage";
        }
        return "ManagerMainPage";
    }

    /**
     * 用户登录
     * @param userId 用户ID
     * @param userPW 用户密码
     * @param model 模型
     * @return 用户主界面
     */
    @RequestMapping(path="/userLogin" )
    public String UserLogin(@RequestParam(name = "userId") int userId,@RequestParam(name = "userPW") String userPW, Model model){
        boolean isLogin = userService.UserLogin(userId, userPW);
        if(!isLogin){
            return "redirect:/UserLoginPage.html";
        }
        Users user=userService.FindUserOne(userId);
        model.addAttribute("user",user);
        return "UserMainPage";
    }

    /**
     * 用户注册
     * @param userName 用户名
     * @param userSex 性别
     * @param userAge 年龄
     * @param userPhone 电话
     * @param userIdCard 身份证
     * @param userId 用户ID
     * @param model 模型
     * @return 用户主界面
     */
    @RequestMapping(path="/userRegister")
    public String UserRegister(@RequestParam(name = "userName") String userName, @RequestParam(name = "userSex") String userSex, @RequestParam(name = "userAge") int userAge, @RequestParam(name = "userPhone") Long userPhone, @RequestParam(name = "userIdCard") String userIdCard, @RequestParam(name = "userId") int userId, Model model){
        Users users=new Users();
        users.setUserName(userName);
        users.setUserSex(userSex);
        users.setUserAge(userAge);
        users.setUserPhone(userPhone);
        users.setUserIdCard(userIdCard);
        users.setUserId(userId);
        boolean isRegister = userService.UserRegister(users);

        if(!isRegister) {
            return "RegistrationOfOutsidersPage";
        }
        Users user=userService.FindUserOne(users.getUserId());
        model.addAttribute("user",user);
        return "OutsidersLogin";
    }

    /**
     * 跳转发布防控信息
     * @return 发布防控信息界面
     */
    @RequestMapping("/ManagerReleaseInformationPage_Release")
    public String ManagerReleaseInformationPage_Release(){
        return "views/Manager/ManagerReleaseInformationPage-Release";
    }

    /**
     * 跳转物品修改
     * @param goodsId 物品ID
     * @param model 模型
     * @return 物品修改界面
     */
    @RequestMapping("/ManagerMaterialInformationDisplayPage_Modifacation")
    public String ManagerMaterialInformationDisplayPage_Modifacation(@RequestParam(name = "goodsId") int goodsId,Model model){
        Goods goods=adminService.FindGoodsOne(goodsId);
        model.addAttribute("good",goods);
        return "views/Manager/ManagerMaterialInformationDisplayPage-Modifacation";
    }

    /**
     * 跳转修改用户密码
     * @return 修改用户密码界面
     */
    @RequestMapping("/ManagerModifyUserPasswordPage")
    public String ManagerModifyUserPasswordPage(){
        return "views/Manager/ManagerModifyUserPasswordPage";
    }

    /**
     * 跳转录入统计信息
     * @return 录入统计信息界面
     */
    @RequestMapping("/ManagerEnterStatisticsPage")
    public String ManagerEnterStatisticsPage(){
        return "views/Manager/ManagerEnterStatisticsPage";
    }

    /**
     * 跳转添加物资信息
     * @return 添加物资信息界面
     */
    @RequestMapping("/ManagerAddItemPage")
    public String ManagerAddItemPage(){
        return "views/Manager/ManagerAddItemPage";
    }

    /**
     * 跳转修改用户界面
     * @param userId 用户ID
     * @param model 模型
     * @return 修改用户界面
     */
    @RequestMapping("/ManagerModifyUserInformationPage")
    public String ManagerModifyUserInformationPage1(@RequestParam(name = "userId") int userId,Model model){
        Users user = adminService.FindUserOne(userId);

        if(user == null) {
            return "ErrorPage";
        }

        model.addAttribute("admuser",user);
        return "views/Manager/ManagerModifyUserInformationPage";
    }

    /**
     * 跳转查看志愿安排界面
     * @param model 模型
     * @return 查看志愿安排界面
     */
    @RequestMapping(path="/ManagerForVolunteerPage")
    public String ManagerForVolunteerPage(Model model){
        List<Volunte> volunteList=adminService.SelectVolunteAgree();
        Map<String,Volunte> volunag=new HashMap<>();
        if(volunteList != null){
            for(int i=0;i<volunteList.size();i++){
                volunag.put(volunteList.get(i).getTaskTime(),volunteList.get(i));
            }
        }
        model.addAttribute("volunag",volunag);
        return "views/Manager/ManagerForVolunteerPage";
    }

}
