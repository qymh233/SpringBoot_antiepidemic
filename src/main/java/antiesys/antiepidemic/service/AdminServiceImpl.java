package antiesys.antiepidemic.service;

import antiesys.antiepidemic.mapper.*;
import antiesys.antiepidemic.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.*;

@Service
public class AdminServiceImpl implements AdminService{
    @Autowired
    UserInter userInter;
    @Autowired
    AdminInter adminInter;
    @Autowired
    GoodsInter goodsInter;
    @Autowired
    ReportInter reportInter;
    @Autowired
    MessageInter messageInter;
    @Autowired
    OpinionInter opinionInter;
    @Autowired
    SignInInter signInInter;
    @Autowired
    VolunteInter volunteInter;

    @Override
    public Manager selectManagerById(int adminId){
        return adminInter.SelectOne(adminId);
    }

    @Override
    public boolean AdminLogin(Manager admin) {
        Manager m=adminInter.SelectOne(admin.getAdminId());
        //如果不存在
        if(m==null){
            return false;
        }
        //判断密码
        return admin.getAdminPW().equals(m.getAdminPW());
    }

    @Override
    public boolean AdminSignIn(String emperature) {
    	Double emp= Double.valueOf(emperature.toString());
        return !(emp <= 35.2) && !(emp >= 37.4);
    }

    @Override
    public boolean AddGoods(Goods goods) {
        if(goods==null){
            return  false;
        }
        goods.setGoodsInTime(new Date());
        //添加物品
        int t=goodsInter.InsertGoods(goods);
        return t != 0;
    }

    @Override
    public boolean DeleteGoods(int goodsId) {
        Goods g=goodsInter.SelectOne(goodsId);
        //物品不存在
        if(g==null){
            return false;
        }
        int t=goodsInter.DeleteGoods(goodsId);
        return t != 0;
    }

    @Override
    public boolean ChangeGoods(Goods goods) {
        //判断物品存在
        Goods g=goodsInter.SelectOne(goods.getGoodsId());
        if(g==null){
            return false;
        }
        //更新物品
        int t=goodsInter.UpdateGoods(goods);
        return t != 0;
    }

    @Override
    public Goods FindGoodsOne(int goodsId) {
        //直接查询
        Goods goods=goodsInter.SelectOne(goodsId);
        return goods;
    }

    @Override
    public List<Goods> FindGoodsAll() {
        List<Goods> goodsList=goodsInter.SelectGoods();
        if(goodsList==null||goodsList.isEmpty()){
            return  null;
        }
        return goodsList;
    }

    @Override
    public Users FindUserOne(int userId) {
        Users user=userInter.SelectOne(userId);
        return user;
    }

    @Override
    public List<Users> FindUserAll() {
        List<Users> users=userInter.SelectUsers();
        if(users==null||users.isEmpty()){
            return null;
        }
        return users;
    }

    @Override
    public int ChangeUser(Users user) {
        //判断用户是否存在
        Users u=userInter.SelectOne(user.getUserId());
        if(u==null){
            return  0;
        }
        user.setUserIdCard(u.getUserIdCard());
        user.setUserPW(u.getUserPW());
        user.setTemperature(u.getTemperature());
        //修改信息
        int t=userInter.UpdateUser(user);
        return t;
    }

    @Override
    public boolean ChangePassword(Manager admin, int userId, String newPW) {
    	Users u=userInter.SelectOne(userId);
    	u.setUserPW(newPW);
    	int t=userInter.UpdateUser(u);
        return t != 0;
    }

    @Override
    public int AddReport(Report report) {
        //报表不做存在判断,但需要在这里添加报表序号
        int num=reportInter.SelectNum();
        num=num+1;
        report.setOrderNum(num);
        int t=reportInter.InsertReport(report);
        return t;
    }

    @Override
    public int DeleteReport(int reportId) {
        //直接进行删除
        int t=reportInter.DeleteReport(reportId);
        return t;
    }

    @Override
    public List<Report> FindReportOne(int userId) {
        //判断用户是否存在
        Users u=userInter.SelectOne(userId);
        if(u==null){
            return null;
        }
        //查询报表
        List<Report> userList=reportInter.SelectOne(userId);
        if(userList==null||userList.isEmpty()){
            return null;
        }
        return userList;
    }

    @Override
    public List<Report> FindReportTime(String indoor, String beginTime, String endTime){

        List<Report> reportList;
        if("进".equals(indoor)) {
            reportList=reportInter.SelectReportInTime(beginTime, endTime);
        }else{
            reportList=reportInter.SelectReportOutTime(beginTime, endTime);
        }

        if(reportList==null||reportList.isEmpty()){
            return null;
        }
        return reportList;
    }

    @Override
    public List<Report> FindReportTimeOne(Integer userId, String indoor, String beginTime, String endTime){

        List<Report> reportList;
        if("进".equals(indoor)) {
            reportList=reportInter.SelectReportInTimeOne(userId, beginTime, endTime);
        }else{
            reportList=reportInter.SelectReportOutTimeOne(userId, beginTime, endTime);
        }

        if(reportList==null||reportList.isEmpty()){
            return null;
        }
        return reportList;
    }

    @Override
    public List<Report> FindReportAll() {
        List<Report> reports=reportInter.SelectReport();
        if(reports==null||reports.isEmpty()){
            return null;
        }
        return reports;
    }

    @Override
    public boolean FindNumber(Map<Integer, Integer> map, int serialNum) {
    	Set set=map.keySet();
    	int sig=0;
        for (Object o : set) {
            int key = (int) o;
            if ((Integer) map.get(key) == serialNum) {
                sig = 1;
                break;
            }
        }
        return sig != 0;
    }

    @Override
    public boolean AddMessage(Message message) {
        if(message==null){
            return  false;
        }
        message.setPuDate(new Date());
        message.setStat("未完成");
        //添加信息
        int t=messageInter.InsertMessage(message);
        return t != 0;
    }

    @Override
    public boolean DeleteMessage(Integer meID) {
        Message g=messageInter.SelectOne(meID);
        //信息不存在
        if(g==null){
            return false;
        }
        int t=messageInter.DeleteMessage(meID);
        return t != 0;
    }

    @Override
    public Message FindMessageOne(Integer meID) {
        //直接查询
        Message message=messageInter.SelectOne(meID);
        return message;
    }

    @Override
    public List<Message> FindMessageAll() {
        List<Message> messageList=messageInter.SelectMessage();
        if(messageList==null||messageList.isEmpty()){
            return  null;
        }
        return messageList;
    }

    @Override
    public int ChangeMessage(Integer MeID) {
        //判断信息是否存在
        Message u=messageInter.SelectOne(MeID);
        if(u==null){
            return  0;
        }
        u.setStat("已完成");
        //修改信息
        int t=messageInter.UpdateMessage(u);
        return t;
    }

    @Override
    public List<Opinion> FindOpinionAll() {
        List<Opinion> opinionList=opinionInter.SelectOpinion();
        if(opinionList==null||opinionList.isEmpty()){
            return  null;
        }
        return opinionList;
    }

    @Override
    public Opinion FindOpinionOne(Integer meID) {
        //直接查询
        Opinion opinion=opinionInter.SelectOne(meID);
        return opinion;
    }

    @Override
    public int UpdateOpinion(Opinion opinion){
        //判断信息是否存在
        Opinion o=opinionInter.SelectOne(opinion.getMeID());
        if(o==null){
            return  0;
        }
        opinion.setStat("已处理");
        //修改信息
        int t=opinionInter.UpdateOpinion(opinion);
        return t;
    }

    @Override
    public List<Opinion> SelectOpinionOne(Integer userId) {
        List<Opinion> opinionList=opinionInter.SelectOpinionOne(userId);
        if(opinionList==null||opinionList.isEmpty()){
            return  null;
        }
        return opinionList;
    }

    @Override
    public List<SignIn> FindSignInAll(){
        List<SignIn> signInList = signInInter.SelectSignIn();
        if(signInList==null||signInList.isEmpty()){
            return  null;
        }
        return signInList;
    }

    @Override
    public List<SignIn> FindSignInOne(Integer userId){
        List<SignIn> signInList = signInInter.SelectOne(userId);
        if(signInList==null||signInList.isEmpty()){
            return  null;
        }
        return signInList;
    }

    @Override
    public List<SignIn> FindSignInTime(String beginTime, String endTime){
        List<SignIn> signInList = signInInter.SelectSignInInTime(beginTime, endTime);
        if(signInList==null||signInList.isEmpty()){
            return  null;
        }
        return signInList;
    }

    @Override
    public List<SignIn> FindSignInTimeOne(Integer userId, String beginTime, String endTime){
        List<SignIn> signInList = signInInter.SelectSignInInTimeUser(beginTime, endTime, userId);
        if(signInList==null||signInList.isEmpty()){
            return  null;
        }
        return signInList;
    }

    @Override
    public List<Volunte> FindIncompleteVolunte(){
        List<Volunte> volunteList = volunteInter.SelectVolunteIncomplete();
        if(volunteList == null || volunteList.isEmpty()){
            return null;
        }
        return volunteList;
    }

    @Override
    public Volunte FindVolunteOne(Integer meId){

        return volunteInter.SelectOne(meId);
    }

    @Override
    public int UpdateVolunteStat(Volunte volunte, String stat){
        //判断信息是否存在
        volunte.setStat(stat);
        //修改信息
        int t=volunteInter.UpdateVolunte(volunte);
        return t;
    }

    @Override
    public List<Volunte> SelectVolunteAgree() {
        List<Volunte> reportList=volunteInter.SelectVolunteAgree();
        if(reportList==null||reportList.isEmpty()){
            return null;
        }
        return reportList;
    }

    @Override
    public int VolunteComplete(){
        int t = volunteInter.CompleteAll();
        if(t==0){
            return 0;
        }
        return 1;
    }

    @Override
    public int RefuseVolunteAll(String taskTime){
        int t = volunteInter.RefuseAll(taskTime);
        if(t==0){
            return 0;
        }
        return 1;
    }
}
