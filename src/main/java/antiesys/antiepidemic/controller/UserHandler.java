package antiesys.antiepidemic.controller;

import antiesys.antiepidemic.pojo.*;
import antiesys.antiepidemic.service.UserService;
import antiesys.antiepidemic.util.ObjectToJson;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
@SessionAttributes(value = {"volunag","OneVolunter","ssign","usigninlist","user","umsglist","usermessage","ureportList","opilist","Oneopinion","myoplist","oppage"})
@Scope("session")
public class UserHandler {
    @Autowired
    UserService userService;

    List<SignIn> signInList;
    List<Message> messageList;
    List<Report> reportList;
    List<Opinion> opinionList;
    List<Volunte> volunteList;

    /**
     * 查询用户信息
     * @param model 模型
     * @return 个人信息页面
     */
    @RequestMapping(path="/findUsesOne")
    public String FindUserOne(Model model){

        Users users = (Users)model.getAttribute("user");

        if(users == null) {
            return "ErrorPage";
        }

        return "views/User/UserViewPersonalInformationPage";
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
        if(!userName.equals("")&&userName!=null) {
            user.setUserName(userName);
        }
        if(userAge!=null){
            user.setUserAge(userAge);
        }
        if(userPhone!=null) {
            user.setUserPhone(userPhone);
        }
        if(!userSex.equals(" ")&&userSex!=null) {
            user.setUserSex(userSex);
        }
        int numbers = userService.ChangeUser(user);

        if(numbers == 0) {
            return "views/User/UserModifyPersonalInformationPage";
        }

        return "views/User/UserViewPersonalInformationPage";
    }

    /**
     * 修改用户页面
     * @param model 模型
     * @return 修改用户信息界面
     */
    @RequestMapping(path="/changeUserPage")
    public String ChangeUserPage(Model model){

        return "views/User/UserModifyPersonalInformationPage";
    }

    /**
     * 修改用户密码界面
     * @param model 模型
     * @return 修改用户密码界面
     */
    @RequestMapping(path="/ChangePassWordPage")
    public String ChangePassWordPage(Model model){

        return "views/User/UserChangePasswordPage";
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

        if(!isChange) {
            return "views/User/UserChangePasswordPage";
        }

        return "redirect:/UserLoginPage.html";
    }

    /**
     * 查询疫情防控信息
     * @param model 模型
     * @return 疫情防控信息界面
     */
    @RequestMapping(path="/releaseInformation")
    public String ReleaseInformation(Model model){

        messageList=userService.FindMessageAll();
        return "views/User/UserViewEpidemicPreventionInformationPage";
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

        return "views/User/UserRecordPage-Details";
    }

    @RequestMapping(path = "/messageList")
    @ResponseBody
    public JSONObject messageList(Integer page, Integer limit){

        List<Message> messageListSub;
        JSONObject result = new JSONObject();
        result.put("code", 0);
        if(messageList == null) {
            return result;
        }
        messageListSub = (List<Message>) ObjectToJson.listSub(messageList, page, limit);
        ObjectToJson.filterObject(result, messageListSub, messageList.size());
        return result;
    }

    /**
     * 用户查看自己的统计报表信息
     * @param meId 信息ID
     * @param model 模型
     * @return 统计报表显示界面
     */
    @RequestMapping(path = "/UserFindOneReportPage")
    public String UserFindOneReportPage(@RequestParam(name = "meId") Integer meId, Model model){

        reportList= userService.FindReportAll(meId);

        if(reportList == null) {
            return "views/User/UserRecordPage-Details";
        }

        return "views/User/UserFindOneReportPage";
    }

    @RequestMapping(path = "/reportList")
    @ResponseBody
    public JSONObject reportList(Integer page, Integer limit){

        List<Report> reportListSub;
        JSONObject result = new JSONObject();
        result.put("code", 0);
        if(reportList == null) {
            return result;
        }
        reportListSub = (List<Report>) ObjectToJson.listSub(reportList, page, limit);
        ObjectToJson.filterObject(result, reportListSub, reportList.size());
        return result;
    }

    /**
     * 跳转意见反馈界面
     * @param model 模型
     * @return 意见反馈界面
     */
    @RequestMapping(path="/feedback")
    public String feedback(Model model){
        return "views/User/UserFeedbackPage";
    }

    /**
     * 意见反馈界面
     * @param title 反馈标题
     * @param content 反馈内容
     * @param model 模型
     * @return 用户查看反馈界面
     */
    @RequestMapping(path = "/releaseOpinion")
    public String ReleaseOpinion(@RequestParam(name = "title") String title, @RequestParam(name = "content") String content,Model model){
        Users users=(Users)model.getAttribute("user");
        Integer i=users.getUserId();
        Opinion opinion=new Opinion();
        opinion.setTitle(title);
        opinion.setCont(content);
        opinion.setUserId(i);
        opinion.setUserName(users.getUserName());
        boolean t=userService.AddOpinion(opinion);
        if(t==false) {
            return "views/User/UserFeedbackPage";
        }
        //重新获取数据
        opinionList=userService.FindOpinionAll();
        return "views/User/UserViewOpinionInformationPage";
    }

    /**
     * 跳转全部意见反馈界面
     * @param model 模型
     * @return 全部意见反馈界面
     */
    @RequestMapping(path="/UserViewOpinionInformationPage")
    public String UserViewOpinionInformationPage(Model model){
        opinionList=userService.FindOpinionAll();
        model.addAttribute("opilist",opinionList);
        model.addAttribute("oppage","UserViewOpinionInformationPage");
        return "views/User/UserViewOpinionInformationPage";
    }

    /**
     * 查看具体反馈
     * @param meId 信息ID
     * @param model 模型
     * @return 具体信息显示界面
     */
    @RequestMapping(path = "/taskOpinion")
    public String taskOpinion(@RequestParam(name = "meId") Integer meId, Model model){

        Opinion opinion=userService.FindOpinionOne(meId);
        model.addAttribute("Oneopinion",opinion);

        return "views/User/UserViewOneOpinionInformationPage";
    }

    /**
     * 查看个人反馈
     * @param model 模型
     * @return 个人反馈信息界面
     */
    @RequestMapping(path = "/selfOpinion")
    public String selfOpinion(Model model){
        Users users=(Users)model.getAttribute("user");
        opinionList=userService.SelectOpinionOne(users.getUserId());
        model.addAttribute("oppage","selfOpinion");
        return "views/User/UserViewOpinionInformationPage";
    }

    /**
     * 跳转上一次反馈页面
     * @param model 模型
     * @return 上一次反馈页面
     */
    @RequestMapping(path="/ReturnPage")
    public String ReturnPage(Model model){
        return "views/User/UserViewOpinionInformationPage";
    }

    @RequestMapping(path = "/opinionList")
    @ResponseBody
    public JSONObject opinionList(Integer page, Integer limit){

        List<Opinion> opinionListSub;
        JSONObject result = new JSONObject();
        result.put("code", 0);
        if(opinionList == null) {
            return result;
        }
        opinionListSub = (List<Opinion>) ObjectToJson.listSub(opinionList, page, limit);
        ObjectToJson.filterObject(result, opinionListSub, opinionList.size());
        return result;
    }

    /**
     * 跳转签到页面
     * @param model 模型
     * @return 签到页面
     */
    @RequestMapping(path="/GotoSignIn")
    public String GotoSignIn(Model model){
        return "views/User/UserSignInPage";
    }

    /**
     * 签到结果界面
     * @param temperature 温度
     * @param remake 备注
     * @param model 模型
     * @return 签到结果界面
     */
    @RequestMapping(path="/UserSignIn")
    public String UserSignIn(@RequestParam(name = "temperature") String temperature,@RequestParam(name = "remake") String remake,Model model){
        SignIn signIn=new SignIn();
        signIn.setTemperature(temperature);
        signIn.setRemarks(remake);
        Users users=(Users)model.getAttribute("user");
        signIn.setUserId(users.getUserId());
        signIn.setUserName(users.getUserName());
        boolean i=userService.AddSignIn(signIn);
        if(i==false) {
            return "ErrorPage";
        }
        List<SignIn> signInList=userService.SelectSignInOne(users.getUserId());
        model.addAttribute("usigninlist",signInList);
        return "views/User/UserViewSignInPage";
    }

    /**
     * 查看个人所有签到记录
     * @param model 模型
     * @return 个人签到记录显示界面
     */
    @RequestMapping(path="/findSignInAll")
    public String findSignInAll(Model model) {
        Users users=(Users)model.getAttribute("user");
        signInList=userService.SelectSignInOne(users.getUserId());
        return "views/User/UserViewSignInPage";
    }

    /**
     * 查询指定时间的签到记录
     * @param beginTime 起始时间
     * @param endTime 结束时间
     * @param model 模型
     * @return 签到记录显示界面
     */
    @RequestMapping(path="/findSignInTime")
    public String findSignInTime(String beginTime, String endTime, Model model) {
        Users users=(Users)model.getAttribute("user");

        System.out.println(beginTime);

        if("".equals(beginTime) || beginTime == null){
            signInList = userService.SelectSignInOne(users.getUserId());
        }else{
            signInList = userService.FindSignInTime(beginTime, endTime,users.getUserId());
        }

        return "views/User/UserViewSignInPage";
    }

    @RequestMapping(path = "/signInList")
    @ResponseBody
    public JSONObject signInList(Integer page, Integer limit){

        List<SignIn> signInListSub;
        JSONObject result = new JSONObject();
        result.put("code", 0);
        if(signInList == null) {
            return result;
        }
        signInListSub = (List<SignIn>) ObjectToJson.listSub(signInList, page, limit);
        ObjectToJson.filterObject(result, signInListSub, signInList.size());
        return result;
    }

    /**
     * 跳转申请结果页面
     * @param model 模型
     * @return 申请结果页面
     */
    @RequestMapping(path="/GotoVolunterEnd")
    public String GotoVolunterEnd(Model model){
        Users users=(Users)model.getAttribute("user");
        volunteList=userService.FindVolunterOne(users.getUserId());
        return "views/User/GotoVolunterEnd";
    }

    /**
     * 跳转申请志愿页面
     * @param model 模型
     * @return 申请结果页面
     */
    @RequestMapping(path="/UserApplyForVolunteerPage")
    public String UserApplyForVolunteerPage(Model model){
        List<Volunte> volunteList=userService.SelectVolunteAgree();
        Map<String,Volunte> volunag=new HashMap<>();
        for(int i=0;i<volunteList.size();i++){
            volunag.put(volunteList.get(i).getTaskTime(),volunteList.get(i));
        }
        model.addAttribute("volunag",volunag);
        return "views/User/UserApplyForVolunteerPage";
    }

    /**
     * 跳转志愿服务安排页面
     * @param model 模型
     * @return 志愿服务安排页面
     */
    @RequestMapping(path="/UserForVolunteerPage")
    public String UserForVolunteerPage(Model model){
        List<Volunte> volunteList=userService.SelectVolunteAgree();
        Map<String,Volunte> volunag=new HashMap<>();
        for(int i=0;i<volunteList.size();i++){
            volunag.put(volunteList.get(i).getTaskTime(),volunteList.get(i));
        }
        model.addAttribute("volunag",volunag);
        return "views/User/UserForVolunteerPage";
    }

    /**
     * 跳转申请志愿页面
     * @param model 模型
     * @return 申请结果页面
     */
    @RequestMapping(path="/UserApplyForVolunteer")
    public String UserApplyForVolunteer(@RequestParam(name = "taskTime") String taskTime,Model model){
        Users users=(Users)model.getAttribute("user");
        List<Volunte> voluntes=userService.FindVolunterOne(users.getUserId());
        for (Volunte value : voluntes) {
            if (value.getTaskTime().equals(taskTime)) {
                return "views/User/GotoVolunterEnd";
            }
        }
        Volunte volunte=new Volunte();
        volunte.setTaskTime(taskTime);
        volunte.setUserId(users.getUserId());
        volunte.setUserName(users.getUserName());
        userService.InsertVolunte(volunte);
        volunteList=userService.FindVolunterOne(users.getUserId());
        return "views/User/GotoVolunterEnd";
    }

    @RequestMapping(path = "/volunteList")
    @ResponseBody
    public JSONObject volunteList(Integer page, Integer limit){

        List<Volunte> volunteListSub;
        JSONObject result = new JSONObject();
        result.put("code", 0);
        if(volunteList == null) {
            return result;
        }
        volunteListSub = (List<Volunte>) ObjectToJson.listSub(volunteList, page, limit);
        ObjectToJson.filterObject(result, volunteListSub, volunteList.size());
        return result;
    }

}
