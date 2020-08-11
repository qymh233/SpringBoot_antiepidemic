package antiesys.antiepidemic.controller;

import antiesys.antiepidemic.pojo.Goods;
import antiesys.antiepidemic.pojo.Manager;
import antiesys.antiepidemic.pojo.Message;
import antiesys.antiepidemic.pojo.Users;
import antiesys.antiepidemic.service.AdminService;
import antiesys.antiepidemic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@SessionAttributes(value = {"user","manager","userlist","goodslist","admuser","good","msglist"})
public class AdminPageHandler {
    @Autowired
    AdminService adminService;
    @Autowired
    UserService userService;
    //管理员登录
    @RequestMapping(path="/adminLogin")
    public String AdminLogin(@RequestParam(name = "adminId") int adminId, @RequestParam(name = "adminPW") String adminPW, HttpSession session, Model model){
        Manager manager=new Manager();
        manager.setAdminId(adminId);
        manager.setAdminPW(adminPW);
        boolean isLogin = adminService.AdminLogin(manager);

        session.setAttribute("adminId", manager.getAdminId());

        model.addAttribute("manager",manager);
        Map<Integer,Integer> nummap=new HashMap<>();
        model.addAttribute("nummap",nummap);

        if(!isLogin)
            return "ManagerLoginPage";

        return "ManagerMainPage";
    }
    //用户登录
    @RequestMapping(path="/userLogin")
    public String UserLogin(@RequestParam(name = "userId") int userId, @RequestParam(name = "userPW") String userPW, Model model){

        boolean isLogin = userService.UserLogin(userId, userPW);

        if(!isLogin)
            return "UserLoginPage";
        Users user=userService.FindUserOne(userId);
        model.addAttribute("user",user);
        return "UserMainPage";
    }
    //用户注册
    @RequestMapping(path="/userRegister")
    public String UserRegister(@RequestParam(name = "userName") String userName, @RequestParam(name = "userSex") String userSex, @RequestParam(name = "userAge") int userAge, @RequestParam(name = "userPhone") Long userPhone, @RequestParam(name = "userIdCard") String userIdCard, @RequestParam(name = "userId") int userId, Model model){

        Users user=new Users();
        user.setUserName(userName);
        user.setUserSex(userSex);
        user.setUserAge(userAge);
        user.setUserPhone(userPhone);
        user.setUserIdCard(userIdCard);
        user.setUserId(userId);
        boolean isRegister = userService.UserRegister(user);

        if(!isRegister)
            return "UserLoginPage";
        Users users=userService.FindUserOne(userId);
        model.addAttribute("user",users);
        return "UserMainPage";
    }
    //跳转发布防控疫情
    @RequestMapping("/ManagerReleaseInformationPage_Release")
    public String ManagerReleaseInformationPage_Release(){
        return "views/ManagerReleaseInformationPage-Release";
    }
    //跳转防控任务完成情况
    @RequestMapping("/ManagerRecordPage")
    public String ManagerRecordPage(Model model){
        List<Message> messageList=adminService.FindMessageAll();
        model.addAttribute("msglist",messageList);
        return "views/ManagerRecordPage";
    }
    //跳转物品修改
    @RequestMapping("/ManagerMaterialInformationDisplayPage_Modifacation")
    public String ManagerMaterialInformationDisplayPage_Modifacation(@RequestParam(name = "goodsId") int goodsId,Model model){
        Goods goods=adminService.FindGoodsOne(goodsId);
        model.addAttribute("good",goods);
        return "views/ManagerMaterialInformationDisplayPage-Modifacation";
    }
    //跳转修改用户密码
    @RequestMapping("/ManagerModifyUserPasswordPage")
    public String ManagerModifyUserPasswordPage(){
        return "views/ManagerModifyUserPasswordPage";
    }
    //查看用户信息
    @RequestMapping("/ManagerViewUserInformationPage")
    public String ManagerViewUserInformationPage(Model model){
        List<Users> users= adminService.FindUserAll();

        if(users == null)
            return "ErrorPage";
        model.addAttribute("userlist",users);
        return "views/ManagerViewUserInformationPage";
    }
    //跳转录入统计信息
    @RequestMapping("/ManagerEnterStatisticsPage")
    public String ManagerEnterStatisticsPage(){
        return "views/ManagerEnterStatisticsPage";
    }
    //跳转添加物资信息
    @RequestMapping("/ManagerAddItemPage")
    public String ManagerAddItemPage(){
        return "views/ManagerAddItemPage";
    }
    //跳转查看所有物资信息
    @RequestMapping("/ManagerMaterialInformationDisplayPage")
    public String ManagerMaterialInformationDisplayPage(Model model){
        List<Goods> goods;

        goods = adminService.FindGoodsAll();

        if(goods == null)
            return "ErrorPage";

        List<Goods> newGoodsList = new ArrayList<>();

            for (int i = 0; i < goods.size(); i++) {
                newGoodsList.add(goods.get(i));
            }
        model.addAttribute("goodslist",newGoodsList);
        return "views/ManagerMaterialInformationDisplayPage";
    }
    //跳转查看单个用户信息
    @RequestMapping("/ManagerModifyUserInformationPage")
    public String ManagerModifyUserInformationPage(Model model){
        return "views/ManagerModifyUserInformationPage";
    }
    //跳转修改用户页面
    @RequestMapping("/ManagerModifyUserInformationPage1")
    public String ManagerModifyUserInformationPage1(@RequestParam(name = "userId") int userId,Model model){
        Users user = adminService.FindUserOne(userId);

        if(user == null)
            return "ErrorPage";

        model.addAttribute("admuser",user);
        return "views/ManagerModifyUserInformationPage";
    }
}
