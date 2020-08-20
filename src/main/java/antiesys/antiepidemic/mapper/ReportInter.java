package antiesys.antiepidemic.mapper;


import antiesys.antiepidemic.pojo.Report;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@Mapper
public interface ReportInter {
    /**
     * 插入一条报表数据
     * @param report 报表对象
     * @return 插入结果
     */
    int InsertReport(@Param("report") Report report);
    /**
     * 删除一条报表数据
     * @param orderNum 记录数
     * @return 删除结果
     */
    int DeleteReport(@Param("orderNum")int orderNum);
    //更新一条报表数据
    //int UpdateReport(@Param("report") Report report);
    /**
     * 查询一条报表信息
     * @param userId 用户ID
     * @return 查询到的报表列表
     */
    List<Report> SelectOne(@Param("userId")int userId);
    /**
     * 查询所有报表数据
     * @return 查询到的报表列表
     */
    List<Report> SelectReport();

    List<Report> SelectReportInTime(String beginTime, String endTime);
    List<Report> SelectReportOutTime(String beginTime, String endTime);
    List<Report> SelectReportInTimeOne(Integer userId, String beginTime, String endTime);
    List<Report> SelectReportOutTimeOne(Integer userId, String beginTime, String endTime);
    /**
     * 查询记录条数，便于添加记录，自动增加orderNum
     * @return 查询到的记录条数
     */
    int SelectNum();
    /**
     * 查询一个用户最后得记录，可能在查体温的时候有用
     * @param userId 用户ID
     * @return 查询到的报表对象
     */
    Report SelectLastReport(@Param("userId")int userId);

}
