package antiesys.antiepidemic.controller;

import antiesys.antiepidemic.pojo.*;
import antiesys.antiepidemic.service.AdminService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


@Controller
@RequestMapping("/admin")
@SessionAttributes(value = {"manager","nummap","admuser","goodslist","good","userlist","msglist","adminmessage"})
public class AdminHandler {

    @Autowired
    AdminService adminService;
    /**
     * 管理员签到页面跳转
     * @return 管理员签到页面
     */
    @RequestMapping("/adminSignInPage")
    public String adminSignInPage(){
        return "views/ManagerSignInPage";
    }
    /**
     * 管理员签到
     * @param temperature 体温
     * @param model 模型
     * @return 签到结果
     */
    @RequestMapping(path="/adminSignIn", produces="text/html;charset=utf-8")
    @ResponseBody
    public String AdminSignIn(@RequestParam(name = "temperature") String temperature, Model model){
        boolean isSignIn = adminService.AdminSignIn(temperature);

        if(!isSignIn)
            return "签到失败";

        return "签到成功";
    }
    /**
     * 添加物品
     * @param goodsName 物品名称
     * @param goodsNum 物品数量
     * @param goodsSource 物品来源
     * @param model 模型
     * @return 物资显示页面
     */
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
    /**
     * 删除物品
     * @param goodsId 物品ID
     * @param model 模型
     * @return
     */
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
    /**
     * 修改物品
     * @param goodsName 物品名称
     * @param goodsNum 物品数量
     * @param goodsSource 物品来源
     * @param intime 入库时间
     * @param model 模型
     * @return 物品信息显示界面
     */
    @RequestMapping(path="/changeGoods")
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
    /**
     * 查询一个物品
     * @param goodsId 物品ID
     * @param request request
     * @param model 模型
     * @return 物品更新页面
     */
    @RequestMapping(path="/findGoodsOne")
    public String FindGoodsOne(@RequestParam(name = "goodsId") int goodsId, HttpServletRequest request, Model model){

        Goods goods = null;

        goods = adminService.FindGoodsOne(goodsId);

        if(goods == null)
            return "ErrorPage";

        request.setAttribute("good", goods);

        return "ManagerMaterialUpdatePage";
    }
    /**
     * 查询所有物品
     * @param request request
     * @param model 模型
     * @return 物品显示界面
     */
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
    /**
     * 查询一个用户
     * @param userId 用户ID
     * @param request request
     * @param model 模型
     * @return
     */
    @RequestMapping(path="/findUseOne")
    public String FindUserOne(@RequestParam(name = "userId") Integer userId, HttpServletRequest request, Model model){

        Users user = adminService.FindUserOne(userId);

        if(user == null)
            return "ErrorPage";

        model.addAttribute("admuser",user);

        return "views/ManagerViewUserInformationPage-Details";
    }
    /**
     * 查询所有用户
     * @param request request
     * @param model 模型
     * @return
     */
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
    /**
     * 修改用户信息
     * @param userName 用户名
     * @param userAge 年龄
     * @param userPhone 电话
     * @param userSex 性别
     * @param model 模型
     * @return 用户信息界面
     */
    @RequestMapping(path="/changeUser")
    public String ChangeUser(@RequestParam(name = "userName") String userName,@RequestParam(name = "userAge") Integer userAge,@RequestParam(name = "userPhone") Long userPhone,@RequestParam(name = "userSex") String userSex, Model model){

        Users user=(Users) model.getAttribute("admuser");
        if(!userName.equals("")&&userName!=null)
            user.setUserName(userName);
        if(userAge!=null){
            user.setUserAge(userAge);
        }
        if(userPhone!=null)
            user.setUserPhone(userPhone);

        //System.out.println(userSex);
        if(userSex!=null)
            user.setUserSex(userSex);
        int numbers = adminService.ChangeUser(user);

        if(numbers == 0)
            return "views/ManagerModifyUserInformationPage";
        //重新获取数据
        List<Users> userlist= adminService.FindUserAll();
        model.addAttribute("userlist",userlist);

        return "views/ManagerViewUserInformationPage";
    }
    /**
     * 修改用户密码
     * @param mpassword 管理员密码
     * @param newPassword 新的用户密码
     * @param model 模型
     * @return 管理员查看用户信息界面
     */
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
    /**
     * 添加报表信息
     * @param userId 用户ID
     * @param temperature 体温
     * @param remarks 备注
     * @param indoor 是进还是出
     * @param model 模型
     * @return 管理员输入状态界面
     */
    @RequestMapping(path="/addReport", produces="text/html;charset=utf-8")
    public String AddReport(@RequestParam("userId") Integer userId, @RequestParam("temperature") String temperature,@RequestParam("remarks") String remarks,@RequestParam("indoor") String indoor, Model model){
        Report report=new Report();
        if(userId!=null)
            report.setUserId(userId);
        if(temperature!=null&&!temperature.equals(""))
            report.setTemperature(temperature);
        if(remarks!=null&&!remarks.equals(""))
            report.setRemarks(remarks);
        if(indoor.equals("进")){
            report.setInTime(new Date());
        }
        else
            report.setOutTime(new Date());

        System.out.println(report.getInTime());

        int numbers = adminService.AddReport(report);

        if(numbers == 0)
            return "views/ManagerEnterStatisticsPage";

        return "views/ManagerEnterStatisticsPage";
    }
    /**
     * 删除报表信息
     * @param reportId 报表ID
     * @param model 模型
     * @return 删除结果界面
     */
    @RequestMapping(path="/deleteReport")
    public String DeleteReport(@RequestParam(name = "reportId") int reportId, Model model){

        int numbers = adminService.DeleteReport(reportId);

        if(numbers == 0)
            return "deleteReportFail";

        return "deleteReportSuccess";
    }
    /**
     * 查询报表信息
     * @param userId 用户ID
     * @param model 模型
     * @return 查询结果界面
     */
    @RequestMapping(path="/findReportOne")
    public String FindReportOne(@RequestParam(name = "userId") int userId, Model model){

        List<Report> reportList;

        reportList = adminService.FindReportOne(userId);

        if(reportList == null)
            return "findReportOneAdminFail";

        model.addAttribute("reportList", reportList);

        return "views/ManagerFindOneReportPage";
    }

    /**
     * 按时间查找出入记录
     * @param indoor 出入选项
     * @param beginTime 起始时间
     * @param endTime 终止时间
     * @param model 模型
     * @return 信息显示界面
     */
    @RequestMapping(path="/findReportTime")
    public String FindReportTime(String indoor, String beginTime, String endTime, Model model) {
        System.out.println(beginTime);
        System.out.println(endTime);
        System.out.println(indoor);
        List<Report> reportList = adminService.FindReportTime(indoor, beginTime, endTime);

        model.addAttribute("reportList", reportList);
        return "views/ManagerFindTimeReportPage";
    }
    /**
     * 查询所有报表信息
     * @param request request
     * @param model 模型
     * @return 报表显示界面
     */
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
    /**
     * 发布疫情防控信息
     * @param title 信息标题
     * @param content 消息内容
     * @param model 模型
     * @return 防控信息界面
     */
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
        //重新获取数据
        List<Message> messageList=adminService.FindMessageAll();
        model.addAttribute("msglist",messageList);
        return "views/ManagerRecordPage";
    }
    /**
     * 查看具体防疫信息
     * @param meId 信息ID
     * @param model 模型
     * @return 具体信息界面
     */
    @RequestMapping(path = "/taskCompletion")
    public String TaskCompletion(@RequestParam(name = "meId") Integer meId, Model model){

        Message message=adminService.FindMessageOne(meId);
        model.addAttribute("adminmessage",message);

        return "views/ManagerRecordPage-Details";
    }
    /**
     * 修改防控信息状态
     * @param meId 信息ID
     * @param model 模型
     * @return 记录界面
     */
    @RequestMapping(path = "/updateCompletion")
    public String updateCompletion(@RequestParam(name = "meId") Integer meId, Model model){
        adminService.ChangeMessage(meId);

        List<Message> messageList=adminService.FindMessageAll();
        model.addAttribute("msglist",messageList);
        return "views/ManagerRecordPage";
    }
}
