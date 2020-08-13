package antiesys.antiepidemic.mapper;

import antiesys.antiepidemic.pojo.SignIn;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface SignInInter {

    /**
     * 插入一条报表数据
     * @param signIn 报表对象
     * @return 插入结果
     */
    int InsertSignIn(@Param("signIn") SignIn signIn);
    /**
     * 查询一个用户所有报表信息
     * @param userId 用户ID
     * @return 查询到的报表列表
     */
    List<SignIn> SelectOne(@Param("userId")Integer userId);
    /**
     * 查询所有报表数据
     * @return 查询到的报表列表
     */
    List<SignIn> SelectSignIn();
    List<SignIn> SelectSignInInTimeUser(String beginTime, String endTime,@Param("userId")Integer userId);
    List<SignIn> SelectSignInInTime(String beginTime, String endTime);
}
