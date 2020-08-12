package antiesys.antiepidemic.service;



import antiesys.antiepidemic.pojo.Message;
import antiesys.antiepidemic.pojo.Report;
import antiesys.antiepidemic.pojo.Users;

import java.util.List;
import java.util.Map;

public interface UserService {
    /**
     * 外来人员注册
     * @param user 用户对象
     * @return 注册是否成功
     */
    boolean	UserRegister(Users user);
    /**
     * 用户登录
     * @param userId 用户ID
     * @param userPW 密码
     * @return 用户是否登录成功
     */
    boolean UserLogin(int userId,String userPW);
    /**
     * 查询用户信息
     * @param userId 用户ID
     * @return 查询到的用户对象
     */
    Users FindUserOne(int userId);
    /**
     * 修改用户信息
     * @param user 用户对象
     * @return 修改结果
     */
    int	ChangeUser(Users user);
    /**
     * 修改密码
     * @param userId 用户ID
     * @param userPW 旧密码
     * @param newPW 新密码
     * @return 修改是否成功
     */
    boolean	ChangePassword(int userId,String userPW,String newPW);
    /**
     * 生成序列号
     * @param map 序列号和用户名的关联
     * @return 生成的序列号
     */
    int GetNumber(Map<Integer,Integer> map);
    /**
     * 查询一个用户的报表信息
     * @param userId 用户ID
     * @return 查询到的报表信息
     */
    Report FindReportOne(int userId);
    /**
     * 查询一条防疫信息
     * @param meID 信息ID
     * @return 查询到的信息
     */
    Message FindMessageOne(Integer meID);
    /**
     * 查询所有的防疫信息
     * @return 查询到的信息列表
     */
    List<Message> FindMessageAll();
    //查询用户所有报表
    public List<Report> FindReportAll(Integer meID);
}
