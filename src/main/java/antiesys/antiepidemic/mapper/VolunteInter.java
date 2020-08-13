package antiesys.antiepidemic.mapper;

import antiesys.antiepidemic.pojo.Volunte;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface VolunteInter {

    /**
     * 插入一条申请信息
     * @param volunte 信息对象
     * @return 插入结果
     */
    int InsertVolunte(Volunte volunte);

    /**
     * 查询一条用户申请信息
     * @param meID 信息ID
     * @return 查询到的信息对象
     */
    Volunte SelectOne(@Param("meID")int meID);

    /**
     * 查询所有信息
     * @return 查询到的信息列表
     */
    List<Volunte> SelectVolunte();

    /**
     * 更新一个申请信息
     * @param volunte 信息对象
     * @return 更新结果
     */
    int UpdateVolunte(@Param("volunte") Volunte volunte);

    /**
     * 查询个人申请信息
     * @param userId 信息ID
     * @return 查询到的信息对象
     */
    List<Volunte> SelectVolunteOne(@Param("userId")int userId);

    /**
     * 查询同意信息
     * @return 查询到的信息对象
     */
    List<Volunte> SelectVolunteAgree();

    /**
     * 查询状态未完成的志愿申请
     * @return 查询到的信息对象
     */
    List<Volunte> SelectVolunteIncomplete();

    /**
     * 一键拒绝
     * @return 查询到的信息对象
     */
    List<Volunte> UpdateAll();
}
