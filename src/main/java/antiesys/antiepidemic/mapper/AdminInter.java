package antiesys.antiepidemic.mapper;

import antiesys.antiepidemic.pojo.Manager;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface AdminInter {
    //查询一个管理员
    Manager SelectOne(@Param("adminId")int adminId);
}
