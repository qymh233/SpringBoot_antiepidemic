package antiesys.antiepidemic.controller;

import antiesys.antiepidemic.pojo.*;
import antiesys.antiepidemic.service.AdminService;
import antiesys.antiepidemic.util.NullToEmpty;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.*;


/**
 * @author 李伦霆 秦海祺
 */
@Controller
@RequestMapping("/admin")
@SessionAttributes(value = {"manager","nummap","admuser","goodslist","good","userlist","msglist","adminmessage"})
public class AdminHandler {

    @Autowired
    AdminService adminService;

    List<SignIn> signInList;
    List<Report> reportList;
    List<Goods> goodsList;
    List<Message> messageList;
    List<Users> userList;
    List<Opinion> opinionList;
    List<Volunte> volunteList;

    /**
     * 管理员签到页面跳转
     * @return 管理员签到页面
     */
    @RequestMapping("/adminSignInPage")
    public String adminSignInPage(){
        return "views/Manager/ManagerSignInPage";
    }

    /**
     * 管理员签到
     * @param temperature 体温
     * @return 签到结果
     */
    @RequestMapping(path="/adminSignIn", produces="text/html;charset=utf-8")
    @ResponseBody
    public String AdminSignIn(@RequestParam(name = "temperature") String temperature){
        boolean isSignIn = adminService.AdminSignIn(temperature);

        if(!isSignIn) {
            return "签到失败";
        }

        return "签到成功";
    }

    /**
     * 跳转查看所有物资信息
     * @return 查看物资信息界面
     */
    @RequestMapping("/ManagerMaterialInformationDisplayPage")
    public String ManagerMaterialInformationDisplayPage(){
        goodsList = adminService.FindGoodsAll();
        return "views/Manager/ManagerMaterialInformationDisplayPage";
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
        if(goodsName!=null&&!goodsName.equals("")) {
            goods.setGoodsName(goodsName);
        }
        if(goodsNum!=null) {
            goods.setGoodsNum(goodsNum);
        }
        if(goodsSource!=null&&!goodsSource.equals("")) {
            goods.setGoodsSource(goodsSource);
        }

        boolean isAdd = adminService.AddGoods(goods);

        if(!isAdd) {
            return "views/Manager/ManagerMaterialInformationDisplayPage";
        }

        goodsList = adminService.FindGoodsAll();

        return "views/Manager/ManagerMaterialInformationDisplayPage";
    }

    /**
     * 删除物品
     * @param goodsId 物品ID
     * @param model 模型
     * @return 物资信息显示界面
     */
    @RequestMapping(path="/deleteGoods")
    public String DeleteGoods(@RequestParam(name = "goodsId") int goodsId, Model model){

        boolean isDelete = adminService.DeleteGoods(goodsId);
        goodsList = adminService.FindGoodsAll();

        if(!isDelete)
            return "views/Manager/ManagerMaterialInformationDisplayPage";

        return "views/Manager/ManagerMaterialInformationDisplayPage";
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
        if(!goodsName.equals("")&&goodsName!=null) {
            goods.setGoodsName(goodsName);
        }
        if(!goodsSource.equals("")&&goodsSource!=null) {
            goods.setGoodsSource(goodsSource);
        }
        if(intime.equals("入库")) {
            Integer i=goods.getGoodsNum()+goodsNum;
            goods.setGoodsNum(i);
            goods.setGoodsInTime(new Date());
        } else {
            Integer i=goods.getGoodsNum()-goodsNum;
            goods.setGoodsNum(i);
            goods.setGoodsOutTime(new Date());
        }
        boolean isChange = adminService.ChangeGoods(goods);

        if(!isChange) {
            return "views/Manager/ManagerMaterialInformationDisplayPage";
        }

        goodsList = adminService.FindGoodsAll();

        return "views/Manager/ManagerMaterialInformationDisplayPage";
    }

    @RequestMapping(path = "/goodsList")
    @ResponseBody
    public JSONObject goodsList(Integer page, Integer limit){

        List<Goods> goodsListSub;
        if(((page - 1) * limit + limit) <= goodsList.size()) {
            goodsListSub = goodsList.subList((page - 1) * limit, (page - 1) * limit + limit);
        }else{
            goodsListSub = goodsList.subList((page - 1) * limit, goodsList.size());
        }

        JSONObject result = new JSONObject();
        JSONArray jsonArray = JSONArray.fromObject(goodsListSub);
        NullToEmpty.filterNull(jsonArray);
        result.put("code", 0);
        result.put("msg", "");
        result.put("count", goodsList.size());
        result.put("data", jsonArray);
        return result;
    }

    /**
     * 查看用户信息
     * @return 查看用户信息界面
     */
    @RequestMapping("/ManagerViewUserInformationPage")
    public String ManagerViewUserInformationPage(){
        userList = adminService.FindUserAll();
        return "views/Manager/ManagerViewUserInformationPage";
    }

    /**
     * 查询一个用户
     * @param userId 用户ID
     * @param model 模型
     * @return 用户详细信息显示界面
     */
    @RequestMapping(path="/findUseOne")
    public String FindUserOne(@RequestParam(name = "userId") Integer userId, Model model){

        Users user = adminService.FindUserOne(userId);

        if(user == null) {
            return "ErrorPage";
        }

        model.addAttribute("admuser",user);

        return "views/Manager/ManagerViewUserInformationPage-Details";
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
        if(!userName.equals("")&&userName!=null) {
            user.setUserName(userName);
        }
        if(userAge!=null){
            user.setUserAge(userAge);
        }
        if(userPhone!=null) {
            user.setUserPhone(userPhone);
        }

        if(userSex!=null) {
            user.setUserSex(userSex);
        }
        int numbers = adminService.ChangeUser(user);

        if(numbers == 0) {
            return "views/Manager/ManagerModifyUserInformationPage";
        }
        userList = adminService.FindUserAll();

        return "views/Manager/ManagerViewUserInformationPage";
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
            return "views/Manager/ManagerModifyUserPasswordPage";
        }
        Users users=(Users)model.getAttribute("admuser");
        int userId=users.getUserId();
        boolean isChange = adminService.ChangePassword(manager, userId, newPassword);

        if(!isChange) {
            return "views/Manager/ManagerModifyUserPasswordPage";
        }

        userList = adminService.FindUserAll();

        return "views/Manager/ManagerViewUserInformationPage";
    }

    @RequestMapping(path = "/userList")
    @ResponseBody
    public JSONObject userList(Integer page, Integer limit){

        List<Users> userListSub;
        if(((page - 1) * limit + limit) <= userList.size()) {
            userListSub = userList.subList((page - 1) * limit, (page - 1) * limit + limit);
        }else{
            userListSub = userList.subList((page - 1) * limit, userList.size());
        }

        JSONObject result = new JSONObject();
        JSONArray jsonArray = JSONArray.fromObject(userListSub);
        NullToEmpty.filterNull(jsonArray);
        result.put("code", 0);
        result.put("msg", "");
        result.put("count", userList.size());
        result.put("data", jsonArray);
        return result;
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
        if(userId!=null) {
            report.setUserId(userId);
        }
        if(temperature!=null&&!"".equals(temperature)) {
            report.setTemperature(temperature);
        }
        if(remarks!=null&&!"".equals(remarks)) {
            report.setRemarks(remarks);
        }
        if("进".equals(indoor)){
            report.setInTime(new Date());
        }
        else {
            report.setOutTime(new Date());
        }

        //System.out.println(report.getInTime());

        int numbers = adminService.AddReport(report);

        if(numbers == 0)
            return "views/Manager/ManagerEnterStatisticsPage";

        return "views/Manager/ManagerEnterStatisticsPage";
    }

    /**
     * 查询统计报表信息
     * @param indoor 进出选项
     * @param beginTime 起始时间
     * @param endTime 结束时间
     * @param userId 用户ID
     * @param model 模型
     * @return 生成统计报表界面
     */
    @RequestMapping(path = "/findReport")
    public String FindReport(String indoor, @RequestParam(name = "beginTime")String beginTime,@RequestParam(name = "endTime") String endTime, @RequestParam(name = "userId") Integer userId, Model model){

        if(indoor == null && userId == null){
            reportList = adminService.FindReportAll();
        }else if(userId != null && indoor == null){
            reportList = adminService.FindReportOne(userId);
        }else if(userId == null){
            reportList = adminService.FindReportTime(indoor, beginTime, endTime);
        }else{
            reportList = adminService.FindReportTimeOne(userId, indoor, beginTime, endTime);
        }

        return "views/Manager/ManagerGenerateStatisticalReportPage";
    }

    @RequestMapping(path = "/reportList")
    @ResponseBody
    public JSONObject reportList(Integer page, Integer limit){

        List<Report> reportListSub;
        if(((page - 1) * limit + limit) <= reportList.size()) {
            reportListSub = reportList.subList((page - 1) * limit, (page - 1) * limit + limit);
        }else{
            reportListSub = reportList.subList((page - 1) * limit, reportList.size());
        }

        JSONObject result = new JSONObject();
        JSONArray jsonArray = JSONArray.fromObject(reportListSub);
        NullToEmpty.filterNull(jsonArray);
        result.put("code", 0);
        result.put("msg", "");
        result.put("count", reportList.size());
        result.put("data", jsonArray);
        return result;
    }

    /**
     * 跳转报表页面
     * @return 报表页面
     */
    @RequestMapping("/ManagerGenerateStatisticalReportPage")
    public String ManagerGenerateStatisticalReportPage(){
        reportList=adminService.FindReportAll();
        return "views/Manager/ManagerGenerateStatisticalReportPage";
    }

    /**
     * 跳转防控任务完成情况
     * @return 防控任务完成情况界面
     */
    @RequestMapping("/ManagerRecordPage")
    public String ManagerRecordPage(){
        messageList=adminService.FindMessageAll();
        return "views/Manager/ManagerRecordPage";
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
        if(!t) {
            return "views/Manager/ManagerReleaseInformationPage-Release";
        }
        //重新获取数据
        messageList=adminService.FindMessageAll();
        return "views/Manager/ManagerRecordPage";
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

        return "views/Manager/ManagerRecordPage-Details";
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

        messageList=adminService.FindMessageAll();
        return "views/Manager/ManagerRecordPage";
    }

    @RequestMapping(path = "/recordList")
    @ResponseBody
    public JSONObject recordList(Integer page, Integer limit){

        List<Message> messageListSub;
        if(((page - 1) * limit + limit) <= messageList.size()) {
            messageListSub = messageList.subList((page - 1) * limit, (page - 1) * limit + limit);
        }else{
            messageListSub = messageList.subList((page - 1) * limit, messageList.size());
        }

        JSONObject result = new JSONObject();
        JSONArray jsonArray = JSONArray.fromObject(messageListSub);
        NullToEmpty.filterNull(jsonArray);
        result.put("code", 0);
        result.put("msg", "");
        result.put("count", messageList.size());
        result.put("data", jsonArray);
        return result;
    }

    /**
     * 跳转显示用户反馈界面
     * @return 显示用户反馈界面
     */
    @RequestMapping("/ManagerFeedbackDisplayPage")
    public String ManagerFeedbackDisplayPage(){
        opinionList = adminService.FindOpinionAll();
        return "views/Manager/ManagerFeedbackDisplayPage";
    }

    /**
     * 查找一个人的意见反馈
     * @param meID 反馈ID
     * @param model 模型
     * @return 回复界面
     */
    @RequestMapping(path = "/findOpinionOne")
    public String findOpinionOne(@RequestParam(name = "meID") Integer meID, Model model){

        Opinion opinion = adminService.FindOpinionOne(meID);
        model.addAttribute("opinion", opinion);

        return "views/Manager/ManagerAddReplyPage";
    }

    /**
     * 添加回复
     * @param meID 信息ID
     * @param reply 回复内容
     * @param adminId 管理员ID
     * @param model 模型
     * @return 意见反馈显示界面
     */
    @RequestMapping(path = "/addReply")
    public String addReply(@RequestParam(name = "meID") Integer meID, @RequestParam(name = "reply")String reply, @RequestParam(name = "adminId")Integer adminId, Model model){
        Opinion opinion = adminService.FindOpinionOne(meID);
        opinion.setAdCont(reply);
        opinion.setAdDate(new Date());
        opinion.setAdminId(adminId);
        adminService.UpdateOpinion(opinion);

        opinionList = adminService.FindOpinionAll();
        return "views/Manager/ManagerFeedbackDisplayPage";
    }

    /**
     * 查找指定用户的反馈
     * @param userId 用户ID
     * @param model 模型
     * @return 反馈显示界面
     */
    @RequestMapping(path = "/findOpinion")
    public String findOpinion(@RequestParam(name = "userId")Integer userId, Model model){
        if(userId == null){
            opinionList = adminService.FindOpinionAll();
        }else{
            opinionList = adminService.SelectOpinionOne(userId);
        }
        return "views/Manager/ManagerFeedbackDisplayPage";
    }

    @RequestMapping(path = "/opinionList")
    @ResponseBody
    public JSONObject opinionList(Integer page, Integer limit){

        List<Opinion> opinionListSub;
        if(((page - 1) * limit + limit) <= opinionList.size()) {
            opinionListSub = opinionList.subList((page - 1) * limit, (page - 1) * limit + limit);
        }else{
            opinionListSub = opinionList.subList((page - 1) * limit, opinionList.size());
        }

        JSONObject result = new JSONObject();
        JSONArray jsonArray = JSONArray.fromObject(opinionListSub);
        NullToEmpty.filterNull(jsonArray);
        result.put("code", 0);
        result.put("msg", "");
        result.put("count", opinionList.size());
        result.put("data", jsonArray);
        return result;
    }

    /**
     * 跳转显示用户签到信息界面
     * @return 显示用户签到信息界面
     */
    @RequestMapping("/ManagerCheckSignInPage")
    public String ManagerCheckSignInPage(){
        signInList = adminService.FindSignInAll();
        return "views/Manager/ManagerCheckSignInPage";
    }

    /**
     * 根据条件查询用户的签到记录
     * @param beginTime 起始时间
     * @param endTime 结束时间
     * @param userId 用户ID
     * @return 记录显示页面
     */
    @RequestMapping(path = "/findSignIn")
    public String findSignIn(@RequestParam(name = "beginTime")String beginTime,@RequestParam(name = "endTime") String endTime, @RequestParam(name = "userId")Integer userId){

        if(userId == null && "".equals(beginTime)){
            signInList = adminService.FindSignInAll();
        }else if(userId != null && "".equals(beginTime)){
            signInList = adminService.FindSignInOne(userId);
        }else if(userId == null){
            signInList = adminService.FindSignInTime(beginTime, endTime);
        }else{
            signInList = adminService.FindSignInTimeOne(userId, beginTime, endTime);
        }

        return "views/Manager/ManagerCheckSignInPage";
    }

    @RequestMapping(path = "/signInList")
    @ResponseBody
    public JSONObject signInList(Integer page, Integer limit){

        List<SignIn> signInListSub;
        if(((page - 1) * limit + limit) <= signInList.size()) {
            signInListSub = signInList.subList((page - 1) * limit, (page - 1) * limit + limit);
        }else{
            signInListSub = signInList.subList((page - 1) * limit, signInList.size());
        }

        JSONObject result = new JSONObject();
        JSONArray jsonArray = JSONArray.fromObject(signInListSub);
        NullToEmpty.filterNull(jsonArray);
        result.put("code", 0);
        result.put("msg", "");
        result.put("count", signInList.size());
        result.put("data", jsonArray);
        return result;
    }

    /**
     * 跳转处理志愿申请界面
     * @return 处理志愿申请界面
     */
    @RequestMapping("/ManagerCheckForVolunteerPage")
    public String ManagerCheckForVolunteerPage(){
        volunteList = adminService.FindIncompleteVolunte();
        return "views/Manager/ManagerCheckForVolunteerPage";
    }

    /**
     * 更新志愿申请状态
     * @param meId 信息ID
     * @param stat 状态
     * @param model 模型
     * @return 处理志愿申请界面
     */
    @RequestMapping(path = "/updateVolunte")
    public String updateVolunte(@RequestParam(name = "meId")Integer meId, @RequestParam(name = "stat")String stat, Model model){
        Volunte volunte = adminService.FindVolunteOne(meId);;
        adminService.UpdateVolunteStat(volunte, stat);

        volunteList = adminService.FindIncompleteVolunte();
        return "views/Manager/ManagerCheckForVolunteerPage";
    }

    @RequestMapping(path = "/volunteList")
    @ResponseBody
    public JSONObject volunteList(Integer page, Integer limit){

        List<Volunte> volunteListSub;
        if(((page - 1) * limit + limit) <= volunteList.size()) {
            volunteListSub = volunteList.subList((page - 1) * limit, (page - 1) * limit + limit);
        }else{
            volunteListSub = volunteList.subList((page - 1) * limit, volunteList.size());
        }

        JSONObject result = new JSONObject();
        JSONArray jsonArray = JSONArray.fromObject(volunteListSub);
        NullToEmpty.filterNull(jsonArray);
        result.put("code", 0);
        result.put("msg", "");
        result.put("count", volunteList.size());
        result.put("data", jsonArray);
        return result;
    }
}
