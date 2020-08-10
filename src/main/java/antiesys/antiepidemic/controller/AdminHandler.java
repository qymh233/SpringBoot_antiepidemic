package antiesys.antiepidemic.controller;

import antiesys.antiepidemic.pojo.*;
import antiesys.antiepidemic.service.AdminService;
import antiesys.antiepidemic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;


@Controller
@RequestMapping("/admin")
@SessionAttributes(value = {"manager","nummap","admuser","goodslist","good","userlist","msglist","adminmessage"})
public class AdminHandler {

    @Autowired
    AdminService adminService;

    @RequestMapping("/adminSignInPage")
    public String adminSignInPage(){
        return "views/ManagerSignInPage";
    }
    //管理员签到
    @RequestMapping(path="/adminSignIn", produces="text/html;charset=utf-8")
    @ResponseBody
    public String AdminSignIn(@RequestParam(name = "temperature") String temperature, Model model){
        boolean isSignIn = adminService.AdminSignIn(temperature);

        if(!isSignIn)
            return "签到失败";

        return "签到成功";
    }
    //添加物品
    @RequestMapping(path="/addGoods")
    public String AddGoods(@RequestParam(name = "goodsName") String goodsName, @RequestParam(name = "goodsNum") Integer goodsNum,@RequestParam(name = "goodsSource") String goodsSource,Model model){


        Goods goods=new Goods();
        if(goodsName!=null&&!goodsName.equals(""))
            goods.setGoodsName(goodsName);
        if(goodsNum!=null)
            goods.setGoodsNum(goodsNum);
        if(goodsSource!=null&&!goodsSource.equals(""))
            goods.setGoodsSource(goodsSource);

        boolean isAdd = adminService.AddGoods(goods);

        if(!isAdd) {
            return "views/ManagerMaterialInformationDisplayPage";
        }

        List<Goods> mygoods = adminService.FindGoodsAll();
        List<Goods> newGoodsList = new ArrayList<>();
        for (int i = 0; i < mygoods.size(); i++) {
            newGoodsList.add(mygoods.get(i));
        }
        model.addAttribute("goodslist",newGoodsList);

        return "views/ManagerMaterialInformationDisplayPage";
    }
    //删除物品
    @RequestMapping(path="/deleteGoods")
    public String DeleteGoods(@RequestParam(name = "goodsId") int goodsId, Model model){

        boolean isDelete = adminService.DeleteGoods(goodsId);
        List<Goods> goods = adminService.FindGoodsAll();

        List<Goods> newGoodsList = new ArrayList<>();

        for (int i = 0; i < goods.size(); i++) {
            newGoodsList.add(goods.get(i));
        }
        model.addAttribute("goodslist",newGoodsList);

        if(!isDelete)
            return "views/ManagerMaterialInformationDisplayPage";


        return "views/ManagerMaterialInformationDisplayPage";
    }
    //修改物品
    @RequestMapping(path="/changeGoods", produces="text/html;charset=utf-8")
    public String ChangeGoods(@RequestParam(name = "goodsName") String goodsName,@RequestParam(name = "goodsNum") Integer goodsNum,@RequestParam(name = "goodsSource") String goodsSource,@RequestParam(name = "intime") String intime, Model model){
        Goods goods=(Goods)model.getAttribute("good");
        if(!goodsName.equals("")&&goodsName!=null)
            goods.setGoodsName(goodsName);
        if(goodsNum!=null)
            goods.setGoodsNum(goodsNum);
        if(!goodsSource.equals("")&&goodsSource!=null)
            goods.setGoodsSource(goodsSource);
        if(intime.equals("入库"))
            goods.setGoodsInTime(new Date());
        else
            goods.setGoodsOutTime(new Date());
        boolean isChange = adminService.ChangeGoods(goods);

        if(!isChange)
            return "views/ManagerMaterialInformationDisplayPage";

        List<Goods> mygoods = adminService.FindGoodsAll();
        List<Goods> newGoodsList = new ArrayList<>();
        for (int i = 0; i < mygoods.size(); i++) {
            newGoodsList.add(mygoods.get(i));
        }
        model.addAttribute("goodslist",newGoodsList);

        return "views/ManagerMaterialInformationDisplayPage";
    }
    //查询一个物品
    @RequestMapping(path="/findGoodsOne")
    public String FindGoodsOne(@RequestParam(name = "goodsId") int goodsId, HttpServletRequest request, Model model){

        Goods goods = null;

        goods = adminService.FindGoodsOne(goodsId);

        if(goods == null)
            return "ErrorPage";

        request.setAttribute("good", goods);

        return "ManagerMaterialUpdatePage";
    }
    //查询所有物品
    @RequestMapping(path="/findGoodsAll")
    public String FindGoodsAll(HttpServletRequest request, Model model){

        List<Goods> goods;

        goods = adminService.FindGoodsAll();

        if(goods == null)
            return "ErrorPage";

        List<Goods> newGoodsList = new ArrayList<>();


            for (int i = 0; i < goods.size(); i++) {
                newGoodsList.add(goods.get(i));
                System.out.println(newGoodsList.get(i));
            }

        request.setAttribute("goods", newGoodsList);

        return "ManagerMaterialInformationDisplayPage";
    }
    //查询一个用户
    @RequestMapping(path="/findUseOne")
    public String FindUserOne(@RequestParam(name = "userId") Integer userId, HttpServletRequest request, Model model){

        Users user = adminService.FindUserOne(userId);

        if(user == null)
            return "ErrorPage";

        model.addAttribute("admuser",user);

        return "views/ManagerViewUserInformationPage-Details";
    }
    //查询所有用户
    @RequestMapping(path="/findUserAll")
    public String FindUserAll(HttpServletRequest request, Model model){

        List<Users> users;
        users = adminService.FindUserAll();

        if(users == null)
            return "ErrorPage";

        List<Users> newUsersList = new ArrayList<>();

        if(users.size() > 4){
            for (int i = 0; i < 4; i++) {
                newUsersList.add(users.get(i));
            }
        }

        request.setAttribute("users", newUsersList);

        return "ManagerShowUserListPage";
    }
    //修改用户信息
    @RequestMapping(path="/changeUser", produces="text/html;charset=utf-8")
	@ResponseBody
    public String ChangeUser(@RequestParam(name = "userName") String userName,@RequestParam(name = "userAge") Integer userAge,@RequestParam(name = "userPhone") Long userPhone,@RequestParam(name = "userSex") String userSex, Model model){

        Users user=(Users) model.getAttribute("admuser");
        if(!userName.equals("")&&userName!=null)
            user.setUserName(userName);
        if(userAge!=null){
            user.setUserAge(userAge);
        }
        if(userPhone!=null)
            user.setUserPhone(userPhone);
        if(userSex!=null)
            user.setUserSex(userSex);
        int numbers = adminService.ChangeUser(user);

        if(numbers == 0)
            return "修改成功";

        return "修改失败";
    }
    //修改用户密码
    @RequestMapping(path="/changePassword", produces="text/html;charset=utf-8")
    public String ChangePassword(@RequestParam(name = "mpassword") String mpassword, @RequestParam(name = "newPassword") String newPassword, Model model){
        Manager manager=(Manager)model.getAttribute("manager");
        if(!mpassword.equals(manager.getAdminPW())){
            return "views/ManagerModifyUserPasswordPage";
        }
        Users users=(Users)model.getAttribute("admuser");
        int userId=users.getUserId();
        boolean isChange = adminService.ChangePassword(manager, userId, newPassword);

        if(!isChange)
            return "views/ManagerModifyUserPasswordPage";

        List<Users> userl= adminService.FindUserAll();
        List<Users> newUsersList = new ArrayList<>();
        for (int i = 0; i < userl.size(); i++) {
            newUsersList.add(userl.get(i));
        }
        model.addAttribute("userlist",newUsersList);

        return "views/ManagerViewUserInformationPage";
    }
    //添加报表信息
    @RequestMapping(path="/addReport", produces="text/html;charset=utf-8")
    @ResponseBody
    public String AddReport(@RequestParam("userId") Integer userId, @RequestParam("temperature") String temperature,@RequestParam("remarks") String remarks,@RequestParam("indoor") String indoor, Model model){
        Report report=new Report();
        if(userId!=null)
            report.setUserId(userId);
        if(temperature!=null&&!temperature.equals(""))
            report.setTemperature(temperature);
        if(remarks!=null&&!remarks.equals(""))
            report.setRemarks(remarks);
        if(indoor.equals("进")){
            System.out.println(indoor);
            report.setInTime(new Date());
        }
        else
            report.setOutTime(new Date());

        int numbers = adminService.AddReport(report);

        if(numbers == 0)
            return "录入失败";

        return "录入成功";
    }
    //删除报表信息
    @RequestMapping(path="/deleteReport")
    public String DeleteReport(@RequestParam(name = "reportId") int reportId, Model model){

        int numbers = adminService.DeleteReport(reportId);

        if(numbers == 0)
            return "deleteReportFail";

        return "deleteReportSuccess";
    }
    //查询报表信息
    @RequestMapping(path="/findReportOne")
    public String FindReportOne(@RequestParam(name = "userId") int userId, HttpServletRequest request, Model model){

        List<Report> userList = null;

        userList = adminService.FindReportOne(userId);

        if(userList == null)
            return "findReportOneAdminFail";

        request.setAttribute("userList", userList);

        return "findReportOneAdminSuccess";
    }
    //查询所有报表信息
    @RequestMapping(path="/findReportAll")
    public String FindReportAll(HttpServletRequest request, Model model){

        List<Report> userList;

        userList = adminService.FindReportAll();

        if(userList == null)
            return "ErrorPage";

        List<Report> newUserList = new ArrayList<>();


            for(int i = 0;i < userList.size();i++){
                newUserList.add(userList.get(i));
            }


        request.setAttribute("userList", newUserList);

        return "ManagerShowReportPage";
    }
    //验证序列号
    @RequestMapping(path="/findNumber", produces="text/html;charset=utf-8")
	@ResponseBody
    public String FindNumber(@RequestParam(name = "serialNum") int serialNum, HttpSession session, Model model){

        int userId = 0;

        Map<Integer,Integer> map = (HashMap)session.getServletContext().getAttribute("map");

        if(map != null) {
            userId = map.get(serialNum);
            session.getServletContext().removeAttribute("map");
        }

        return "用户" + userId + "验证成功";
    }
    //TODO 以下三个修改信息格式
    //发布疫情防控信息
    @RequestMapping(path = "/releaseInformation")
    public String ReleaseInformation(@RequestParam(name = "title") String title, @RequestParam(name = "content") String content,Model model){
        Manager manager=(Manager)model.getAttribute("manager");
        Integer i=manager.getAdminId();
        Message message=new Message();
        message.setTitle(title);
        message.setCont(content);
        message.setPuBer(i);
        boolean t=adminService.AddMessage(message);
        if(t==false)
            return "views/ManagerReleaseInformationPage-Release";
        List<Message> messageList=adminService.FindMessageAll();
        model.addAttribute("msglist",messageList);
        return "views/ManagerRecordPage";
    }
    //查看具体防控信息
    @RequestMapping(path = "/taskCompletion")
    public String TaskCompletion(@RequestParam(name = "meId") Integer meId, Model model){

        Message message=adminService.FindMessageOne(meId);
        model.addAttribute("adminmessage",message);

        return "views/ManagerRecordPage-Details";
    }
    //修改防控信息状态
    @RequestMapping(path = "/updateCompletion")
    public String updateCompletion(@RequestParam(name = "meId") Integer meId, Model model){
        adminService.ChangeMessage(meId);

        List<Message> messageList=adminService.FindMessageAll();
        model.addAttribute("msglist",messageList);
        return "views/ManagerRecordPage";
    }
    @RequestMapping(path="/adminExit")
    public String toMain(HttpSession session, Model model){

        session.removeAttribute("adminId");

        return "redirect:/index.jsp";
    }
}
