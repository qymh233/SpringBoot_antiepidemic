package antiesys.antiepidemic.mapper;


import antiesys.antiepidemic.pojo.Report;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@Mapper
public interface ReportInter {
    //插入一条报表数据
    int InsertReport(@Param("report") Report report);
    //删除一条报表数据
    int DeleteReport(@Param("orderNum")int orderNum);
    //更新一条报表数据
    int UpdateReport(@Param("report") Report report);
    //查询一条报表数据
    List<Report> SelectOne(@Param("userId")int userId);
    //查询所有报表数据
    List<Report> SelectReport();
    //查询记录条数，便于添加记录，自动增加orderNum
    int SelectNum();
    //查询一个用户最后得记录，可能在查体温的时候有用
    Report SelectLastReport(@Param("userId")int userId);
}
