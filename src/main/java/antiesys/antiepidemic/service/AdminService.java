package antiesys.antiepidemic.service;

import antiesys.antiepidemic.pojo.*;

import java.util.List;
import java.util.Map;

public interface AdminService {

    /**
     * 获取管理员信息
     * @param adminId 管理员ID
     * @return 查询到的管理员对象
     */
    Manager selectManagerById(int adminId);
    /**
     * 管理员登录
     * @param admin 管理员对象
     * @return 登录结果
     */
    boolean AdminLogin(Manager admin);
    /**
     * 签到
     * @param emperature 体温
     * @return 签到结果
     */
    boolean AdminSignIn(String emperature);
    /**
     * 添加物品
     * @param goods 物品对象
     * @return 添加结果
     */
    boolean AddGoods(Goods goods);
    /**
     * 删除物品
     * @param goodsId 物品ID
     * @return 删除结果
     */
    boolean DeleteGoods(int goodsId);
    /**
     * 修改物品
     * @param goods 物品对象
     * @return 修改结果
     */
    boolean ChangeGoods(Goods goods);
    /**
     * 查询一个物品
     * @param goodsId 物品ID
     * @return 查询到的物品对象
     */
    Goods FindGoodsOne(int goodsId);
    /**
     * 查询所有物品
     * @return 查询的物品对象列表
     */
    List<Goods> FindGoodsAll();
    /**
     * 查询一个用户信息
     * @param userId 用户ID
     * @return 查询到的用户对象
     */
    Users FindUserOne(int userId);
    /**
     * 查询所有用户信息
     * @return 查询到的用户对象列表
     */
    List<Users> FindUserAll();
    /**
     * 修改用户信息
     * @param user 用户对象
     * @return 修改结果
     */
    int ChangeUser(Users user);
    /**
     * 修改用户密码
     * @param admin 管理员对象
     * @param userId 用户ID
     * @param newPW 新密码
     * @return
     */
    boolean ChangePassword(Manager admin,int userId,String newPW);
    /**
     * 添加报表信息
     * @param report 报表对象
     * @return 添加结果
     */
    int AddReport(Report report);
    //删除报表信息
    /**
     * 删除报表信息
     * @param reportId 报表ID
     * @return 删除结果
     */
    int DeleteReport(int reportId);
    //查询一个用户报表信息
    /**
     * 查询一个用户报表信息
     * @param userId 用户ID
     * @return 查询到的报表信息列表
     */
    List<Report> FindReportOne(int userId);
    //查询所有报表信息
    /**
     * 查询所有报表信息
     * @return 查询到的报表信息列表
     */
    List<Report> FindReportAll();
    /**
     * 查询序列号
     * @param map 用户ID与序列号
     * @param serialNum 序列号
     * @return 对应结果
     */
    boolean FindNumber(Map<Integer,Integer> map, int serialNum);
    /**
     * 添加报表信息
     * @param message 报表信息对象
     * @return 添加结果
     */
    boolean AddMessage(Message message);
    /**
     * 删除报表信息
     * @param meID 信息ID
     * @return 删除结果
     */
    boolean DeleteMessage(Integer meID);
    //查询一个信息
    /**
     * 查询一个报表信息
     * @param meID 信息ID
     * @return 查询到的信息对象
     */
    Message FindMessageOne(Integer meID);
    /**
     * 查询所有报表信息
     * @return 查询到的报表信息列表
     */
    List<Message> FindMessageAll();
    /**
     * 修改报表信息
     * @param MeID 信息ID
     * @return 修改结果
     */
    int ChangeMessage(Integer MeID);
    /**
     * 查询指定时间段的出入信息
     * @param indoor 进出选项
     * @param beginTime 起始时间
     * @param inTime 终止时间
     * @return 查询到的信息列表
     */
    List<Report> FindReportTime(String indoor, String beginTime, String inTime);

    List<Report> FindReportTimeOne(Integer userId, String indoor, String beginTime, String inTime);

    /**
     * 查询所有反馈信息
     * @return 查询到的报表信息列表
     */
    List<Opinion> FindOpinionAll();
    /**
     * 寻找一条反馈信息
     * @param meID 信息ID
     * @return 查找到的信息对象
     */
    Opinion FindOpinionOne(Integer meID);
    /**
     * 更新意见反馈对象，添加回复
     * @param opinion 意见反馈对象
     * @return 更新结果
     */
    int UpdateOpinion(Opinion opinion);
    /**
     * 查找指定用户的反馈
     * @param UserId 用户ID
     * @return 查找到的反馈列表
     */
    List<Opinion> SelectOpinionOne(Integer userId);

    /**
     * 查询所有的用户签到记录
     * @return 查询到的记录列表
     */
    List<SignIn> FindSignInAll();

    /**
     * 查询单个用户的签到记录
     * @param userId 用户ID
     * @return 查询到的记录列表
     */
    List<SignIn> FindSignInOne(Integer userId);

    /**
     * 查询某段时间的签到记录
     * @param beginTime 起始时间
     * @param endTime 结束时间
     * @return 查询到的记录列表
     */
    List<SignIn> FindSignInTime(String beginTime, String endTime);

    /**
     * 查询某段时间的单用户的签到记录
     * @param userId 用户ID
     * @param beginTime 起始时间
     * @param endTime 结束时间
     * @return 查询到的记录列表
     */
    List<SignIn> FindSignInTimeOne(Integer userId, String beginTime, String endTime);
}
