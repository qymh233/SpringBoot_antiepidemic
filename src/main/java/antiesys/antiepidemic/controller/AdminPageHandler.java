package antiesys.antiepidemic.controller;

import antiesys.antiepidemic.pojo.Goods;
import antiesys.antiepidemic.pojo.Manager;
import antiesys.antiepidemic.pojo.Users;
import antiesys.antiepidemic.service.AdminService;
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
@SessionAttributes(value = {"manager","nummap","userlist","goodslist","admuser","good"})
public class AdminPageHandler {
    @Autowired
    AdminService adminService;
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
    @RequestMapping("/ManagerReleaseInformationPage_Release")
    public String ManagerReleaseInformationPage_Release(){
        return "views/ManagerGetInformationPage";
    }
    @RequestMapping("/ManagerRecordPage")
    public String ManagerRecordPage(){
        return "views/ManagerRecordPage";
    }
    @RequestMapping("/ManagerNotificationPreventionTimePage")
    public String ManagerNotificationPreventionTimePage(){
        return "views/ManagerNotificationPreventionTimePage";
    }
    @RequestMapping("/ManagerMaterialInformationDisplayPage_Modifacation")
    public String ManagerMaterialInformationDisplayPage_Modifacation(@RequestParam(name = "goodsId") int goodsId,Model model){
        Goods goods=adminService.FindGoodsOne(goodsId);
        model.addAttribute("good",goods);
        return "views/ManagerMaterialInformationDisplayPage-Modifacation";
    }
    @RequestMapping("/ManagerModifyUserPasswordPage")
    public String ManagerModifyUserPasswordPage(){
        return "views/ManagerModifyUserPasswordPage";
    }
    @RequestMapping("/ManagerViewUserInformationPage")
    public String ManagerViewUserInformationPage(Model model){
        List<Users> users;
        users = adminService.FindUserAll();

        if(users == null)
            return "ErrorPage";

        List<Users> newUsersList = new ArrayList<>();

            for (int i = 0; i < users.size(); i++) {
                newUsersList.add(users.get(i));
            }
        model.addAttribute("userlist",newUsersList);
        return "views/ManagerViewUserInformationPage";
    }
    @RequestMapping("/ManagerEnterStatisticsPage")
    public String ManagerEnterStatisticsPage(){
        return "views/ManagerEnterStatisticsPage";
    }
    @RequestMapping("/ManagerAddItemPage")
    public String ManagerAddItemPage(){
        return "views/ManagerAddItemPage";
    }
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
    @RequestMapping("/ManagerModifyUserInformationPage")
    public String ManagerModifyUserInformationPage(Model model){
        return "views/ManagerModifyUserInformationPage";
    }
    @RequestMapping("/ManagerModifyUserInformationPage1")
    public String ManagerModifyUserInformationPage1(@RequestParam(name = "userId") int userId,Model model){
        Users user = adminService.FindUserOne(userId);

        if(user == null)
            return "ErrorPage";

        model.addAttribute("admuser",user);
        return "views/ManagerModifyUserInformationPage";
    }
}
