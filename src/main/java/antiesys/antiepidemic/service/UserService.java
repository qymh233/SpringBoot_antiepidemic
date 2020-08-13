package antiesys.antiepidemic.service;



import antiesys.antiepidemic.pojo.*;

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

    /**
     * 查询所有的报表信息
     * @param meID 信息ID
     * @return 查询到的信息列表
     */
    List<Report> FindReportAll(Integer meID);
    /**
     * 添加反馈信息
     * @param opinion 报表信息对象
     * @return 添加结果
     */
    boolean AddOpinion(Opinion opinion);
    /**
     * 删除反馈信息
     * @param meID 信息ID
     * @return 删除结果
     */
    boolean DeleteOpinion(Integer meID);
    //查询一个信息
    /**
     * 查询一个反馈信息
     * @param meID 信息ID
     * @return 查询到的信息对象
     */
    Opinion FindOpinionOne(Integer meID);
    /**
     * 查询所有反馈信息
     * @return 查询到的报表信息列表
     */
    List<Opinion> FindOpinionAll();
    /**
     * 修改反馈信息
     * @param MeID 信息ID
     * @return 修改结果
     */
    int ChangeOpinion(Integer MeID);

    /**
     * 查询个人反馈信息
     * @param UserId 用户ID
     * @return 查询到的信息列表
     */
    List<Opinion> SelectOpinionOne(Integer UserId);
    /**
     * 添加签到信息
     * @param signIn 报表信息对象
     * @return 添加结果
     */
    boolean AddSignIn(SignIn signIn);
    //查询个人签到信息
    List<SignIn> SelectSignInOne(Integer userId);
    /**
     * 查询指定时间段的签到信息
     * @param beginTime 起始时间
     * @param inTime 终止时间
     */
    List<SignIn> FindSignInTime(String beginTime, String inTime,Integer userId);

}
