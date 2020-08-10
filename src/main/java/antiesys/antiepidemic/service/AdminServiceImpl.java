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

    //获取管理员信息
    public Manager selectManagerById(int adminId){
        return adminInter.SelectOne(adminId);
    }
    //管理员登录
    @Override
    public boolean AdminLogin(Manager admin) {
        Manager m=adminInter.SelectOne(admin.getAdminId());
        //如果不存在
        if(m==null){
            return false;
        }
        //判断密码
        if(!admin.getAdminPW().equals(m.getAdminPW())){
            return false;
        }
        return true;
    }
    //签到
    @Override
    public boolean AdminSignIn(String emperature) {
    	Double emp= Double.valueOf(emperature.toString());
    	if(emp<=35.2||emp>=37.4) {
    		return false;
    	}
    	return true;
    }
    //添加物品
    @Override
    public boolean AddGoods(Goods goods) {
        if(goods==null){
            return  false;
        }
        goods.setGoodsInTime(new Date());
        //添加物品
        int t=goodsInter.InsertGoods(goods);
        if(t==0){
            return false;
        }
        return true;
    }
    //删除物品
    @Override
    public boolean DeleteGoods(int goodsId) {
        Goods g=goodsInter.SelectOne(goodsId);
        //物品不存在
        if(g==null){
            return false;
        }
        int t=goodsInter.DeleteGoods(goodsId);
        if(t==0){
            return false;
        }
        return true;
    }
    //修改物品
    @Override
    public boolean ChangeGoods(Goods goods) {
        //判断物品存在
        Goods g=goodsInter.SelectOne(goods.getGoodsId());
        if(g==null){
            return false;
        }
        //更新物品
        int t=goodsInter.UpdateGoods(goods);
        if(t==0){
            return false;
        }
        return true;
    }
    //查询一个物品
    @Override
    public Goods FindGoodsOne(int goodsId) {
        //直接查询
        Goods goods=goodsInter.SelectOne(goodsId);
        if(goods==null){
            return  null;
        }
        return goods;
    }
    //查询所有物品
    @Override
    public List<Goods> FindGoodsAll() {
        List<Goods> goodsList=goodsInter.SelectGoods();
        if(goodsList==null||goodsList.isEmpty()){
            return  null;
        }
        return goodsList;
    }
    //查询一个用户信息
    @Override
    public Users FindUserOne(int userId) {
        Users user=userInter.SelectOne(userId);
        if(user==null){
            return null;
        }
        return user;
    }
    //查询所有用户信息
    @Override
    public List<Users> FindUserAll() {
        List<Users> users=userInter.SelectUsers();
        if(users==null||users.isEmpty()){
            return null;
        }
        return users;
    }
    //修改用户信息
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
        if(t==0){
            return 0;
        }
        return t;
    }
    //修改用户密码
    @Override
    public boolean ChangePassword(Manager admin, int userId, String newPW) {
    	Users u=userInter.SelectOne(userId);
    	u.setUserPW(newPW);
    	int t=userInter.UpdateUser(u);
    	if(t==0){
            return false;
        }
        return true;
    }
    //添加报表信息
    @Override
    public int AddReport(Report report) {
        //报表不做存在判断,但需要在这里添加报表序号
        int num=reportInter.SelectNum();
        num=num+1;
        report.setOrderNum(num);
        int t=reportInter.InsertReport(report);
        if(t==0){
            return  0;
        }
        return t;
    }
    //删除报表信息
    @Override
    public int DeleteReport(int reportId) {
        //直接进行删除
        int t=reportInter.DeleteReport(reportId);
        if(t==0){
            return 0;
        }
        return t;
    }
    //查询一个用户报表信息
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
    //查询所有报表信息
    @Override
    public List<Report> FindReportAll() {
        List<Report> reports=reportInter.SelectReport();
        if(reports==null||reports.isEmpty()){
            return null;
        }
        return reports;
    }
    //查询序列号
    @Override
    public boolean FindNumber(Map<Integer, Integer> map, int serialNum) {
    	Set set=map.keySet();
    	int sig=0;
    	for(Iterator iter=set.iterator();iter.hasNext();) {
    		int key=(int)iter.next();
    		if((Integer)map.get(key)==serialNum) {
    			sig=1;
    			break;
    		}
    	}
    	if(sig==0) {
    		return false;
    	}
    	return true;
    }
    //添加信息
    @Override
    public boolean AddMessage(Message message) {
        if(message==null){
            return  false;
        }
        message.setPuDate(new Date());
        message.setStat("未完成");
        //添加信息
        int t=messageInter.InsertMessage(message);
        if(t==0){
            return false;
        }
        return true;
    }
    //删除信息
    @Override
    public boolean DeleteMessage(Integer meID) {
        Message g=messageInter.SelectOne(meID);
        //信息不存在
        if(g==null){
            return false;
        }
        int t=messageInter.DeleteMessage(meID);
        if(t==0){
            return false;
        }
        return true;
    }
    //查找信息
    @Override
    public Message FindMessageOne(Integer meID) {
        //直接查询
        Message message=messageInter.SelectOne(meID);
        if(message==null){
            return  null;
        }
        return message;
    }
    //查找所有信息
    @Override
    public List<Message> FindMessageAll() {
        List<Message> messageList=messageInter.SelectMessage();
        if(messageList==null||messageList.isEmpty()){
            return  null;
        }
        return messageList;
    }
    //修改信息
    @Override
    public int ChangeMessage(Integer MeID) {
        //判断用户是否存在
        Message u=messageInter.SelectOne(MeID);
        if(u==null){
            return  0;
        }
        u.setStat("已完成");
        //修改信息
        int t=messageInter.UpdateMessage(u);
        if(t==0){
            return 0;
        }
        return t;
    }
}
