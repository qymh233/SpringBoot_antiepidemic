package antiesys.antiepidemic.mapper;

import antiesys.antiepidemic.pojo.Opinion;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface OpinionInter {
    /**
     * 插入一条反馈信息
     * @param opinion 信息对象
     * @return 插入结果
     */
    int InsertOpinion(Opinion opinion);
    /**
     * 删除一条反馈信息
     * @param meID 信息ID
     * @return 删除结果
     */
    int DeleteOpinion(@Param("meID")int meID);
    /**
     * 查询一条反馈信息
     * @param meID 信息ID
     * @return 查询到的信息对象
     */
    Opinion SelectOne(@Param("meID")int meID);
    /**
     * 查询所有信息
     * @return 查询到的信息列表
     */
    List<Opinion> SelectOpinion();
    /**
     * 查询记录条数，便于添加记录，自动增加orderNum
     * @return 查询到的记录条数
     */
    int SelectNum();
    /**
     * 更新一个反馈信息
     * @param opinion 信息对象
     * @return 更新结果
     */
    int UpdateOpinion(@Param("opinion") Opinion opinion);
    /**
     * 查询个人反馈信息
     * @param UserId 信息ID
     * @return 查询到的信息对象
     */
    List<Opinion> SelectOpinionOne(@Param("UserId")int UserId);
}
