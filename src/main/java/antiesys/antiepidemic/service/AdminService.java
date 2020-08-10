package antiesys.antiepidemic.service;

import antiesys.antiepidemic.pojo.*;

import java.util.List;
import java.util.Map;

public interface AdminService {

    Manager selectManagerById(int adminId);
    //管理员登录
    boolean AdminLogin(Manager admin);
    //签到
    boolean AdminSignIn(String emperature);
    //添加物品
    boolean AddGoods(Goods goods);
    //删除物品
    boolean DeleteGoods(int goodsId);
    //修改物品
    boolean ChangeGoods(Goods goods);
    //查询一个物品
    Goods FindGoodsOne(int goodsId);
    //查询所有物品
    List<Goods> FindGoodsAll();
    //查询一个用户信息
    Users FindUserOne(int userId);
    //查询所有用户信息
    List<Users> FindUserAll();
    //修改用户信息
    int ChangeUser(Users user);
    //修改用户密码
    boolean ChangePassword(Manager admin,int userId,String newPW);
    //添加报表信息
    int AddReport(Report report);
    //删除报表信息
    int DeleteReport(int reportId);
    //查询一个用户报表信息
    List<Report> FindReportOne(int userId);
    //查询所有报表信息
    List<Report> FindReportAll();
    //查询序列号
    boolean FindNumber(Map<Integer,Integer> map, int serialNum);

    //添加信息
    boolean AddMessage(Message message);
    //删除信息
    boolean DeleteMessage(Integer meID);
    //查询一个信息
    Message FindMessageOne(Integer meID);
    //查询所有信息
    List<Message> FindMessageAll();
}
