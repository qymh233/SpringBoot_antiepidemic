package antiesys.antiepidemic.mapper;

import antiesys.antiepidemic.pojo.Manager;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface AdminInter {
    /**
     * 查询一个管理员
     * @param adminId 管理员ID
     * @return 查询到的管理员对象
     */
    Manager SelectOne(@Param("adminId")int adminId);
}
