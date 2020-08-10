package antiesys.antiepidemic.service;



import antiesys.antiepidemic.pojo.Message;
import antiesys.antiepidemic.pojo.Report;
import antiesys.antiepidemic.pojo.Users;

import java.util.List;
import java.util.Map;

public interface UserService {
    //外来人员注册
    boolean	UserRegister(Users user);
    //用户登录
    boolean UserLogin(int userId,String userPW);
    //查询用户信息
    Users FindUserOne(int userId);
    //修改用户信息
    int	ChangeUser(Users user);
    //修改密码
    boolean	ChangePassword(int userId,String userPW,String newPW);
    //生成序列号
    int GetNumber(Map<Integer,Integer> map);
    //查询一个用户报表信息
    Report FindReportOne(int userId);

    //查询一个信息
    Message FindMessageOne(Integer meID);
    //查询所有信息
    List<Message> FindMessageAll();
}
