package antiesys.antiepidemic.controller;

import antiesys.antiepidemic.pojo.*;
import antiesys.antiepidemic.service.AdminService;
import antiesys.antiepidemic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
        Map<Integer,Integer> nummap=new HashMap<>();
        model.addAttribute("nummap",nummap);

        if(!isLogin)
            return "ManagerLoginPage";

        return "ManagerMainPage";
    }
    /**
     * 用户登录
     * @param userId 用户ID
     * @param userPW 用户密码
     * @param model 模型
     * @return 用户主界面
     */
    @RequestMapping(path="/userLogin")
    public String UserLogin(@RequestParam(name = "userId") int userId, @RequestParam(name = "userPW") String userPW, Model model){
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

        if(!isRegister)
            return "RegistrationOfOutsidersPage";
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
        return "views/ManagerReleaseInformationPage-Release";
    }
    /**
     * 跳转防控任务完成情况
     * @param model 模型
     * @return 防控任务完成情况界面
     */
    @RequestMapping("/ManagerRecordPage")
    public String ManagerRecordPage(Model model){
        List<Message> messageList=adminService.FindMessageAll();
        model.addAttribute("msglist",messageList);
        return "views/ManagerRecordPage";
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
        return "views/ManagerMaterialInformationDisplayPage-Modifacation";
    }
    /**
     * 跳转修改用户密码
     * @return 修改用户密码界面
     */
    @RequestMapping("/ManagerModifyUserPasswordPage")
    public String ManagerModifyUserPasswordPage(){
        return "views/ManagerModifyUserPasswordPage";
    }
    /**
     * 查看用户信息
     * @param model 模型
     * @return 查看用户信息界面
     */
    @RequestMapping("/ManagerViewUserInformationPage")
    public String ManagerViewUserInformationPage(Model model){
        List<Users> users= adminService.FindUserAll();

        if(users == null)
            return "ErrorPage";
        model.addAttribute("userlist",users);
        return "views/ManagerViewUserInformationPage";
    }
    /**
     * 跳转录入统计信息
     * @return 录入统计信息界面
     */
    @RequestMapping("/ManagerEnterStatisticsPage")
    public String ManagerEnterStatisticsPage(){
        return "views/ManagerEnterStatisticsPage";
    }
    /**
     * 跳转添加物资信息
     * @return 添加物资信息界面
     */
    @RequestMapping("/ManagerAddItemPage")
    public String ManagerAddItemPage(){
        return "views/ManagerAddItemPage";
    }
    /**
     * 跳转查看所有物资信息
     * @param model 模型
     * @return 查看物资信息界面
     */
    @RequestMapping("/ManagerMaterialInformationDisplayPage")
    public String ManagerMaterialInformationDisplayPage(Model model){
        List<Goods> goods= adminService.FindGoodsAll();

        if(goods == null)
            return "ErrorPage";
        model.addAttribute("goodslist",goods);
        return "views/ManagerMaterialInformationDisplayPage";
    }
    /**
     * 跳转查看单个用户信息
     * @param model 模型
     * @return 用户信息界面
     */
    @RequestMapping("/ManagerModifyUserInformationPage")
    public String ManagerModifyUserInformationPage(Model model){
        return "views/ManagerModifyUserInformationPage";
    }
    /**
     * 跳转修改用户界面
     * @param userId 用户ID
     * @param model 模型
     * @return 修改用户界面
     */
    @RequestMapping("/ManagerModifyUserInformationPage1")
    public String ManagerModifyUserInformationPage1(@RequestParam(name = "userId") int userId,Model model){
        Users user = adminService.FindUserOne(userId);

        if(user == null)
            return "ErrorPage";

        model.addAttribute("admuser",user);
        return "views/ManagerModifyUserInformationPage";
    }
    /**
     * 跳转报表页面
     * @param model 模型
     * @return 报表页面
     */
    @RequestMapping("/ManagerGenerateStatisticalReportPage")
    public String ManagerGenerateStatisticalReportPage(Model model){
        List<Report> reportList=adminService.FindReportAll();
        model.addAttribute("reportList",reportList);
        return "views/ManagerGenerateStatisticalReportPage";
    }

    /**
     * 跳转单人出入记录界面
     * @param model 模型
     * @return 担任出入记录界面
     */
    @RequestMapping("/ManagerFindOneReportPage")
    public String ManagerFineOneReportPage(Model model){
        List<Report> reportList=null;
        model.addAttribute("reportList", reportList);
        return "views/ManagerFindOneReportPage";
    }

    /**
     * 跳转查找某一时间段内出入记录的页面
     * @param model 模型
     * @return 查找某一时间段内出入记录的页面
     */
    @RequestMapping("/ManagerFindTimeReportPage")
    public String ManagerFinfTimeReportPage(Model model){
        List<Report> reportList=null;
        model.addAttribute("reportList", reportList);
        return "views/ManagerFindTimeReportPage";
    }

    /**
     * 跳转显示用户反馈界面
     * @param model 模型
     * @return 显示用户反馈界面
     */
    @RequestMapping("/ManagerFeedbackDisplayPage")
    public String ManagerFeedbackDisplayPage(Model model){
        List<Opinion>  opinionList = adminService.FindOpinionAll();
        model.addAttribute("opinionList", opinionList);
        return "views/ManagerFeedbackDisplayPage";
    }
    /**
     * 跳转显示用户签到信息界面
     * @param model 模型
     * @return 显示用户签到信息界面
     */
    @RequestMapping("/ManagerCheckSignInPage")
    public String ManagerCheckSignInPage(Model model){
        List<SignIn>  signInList = adminService.FindSignInAll();
        model.addAttribute("signInList", signInList);
        return "views/ManagerCheckSignInPage";
    }
}
