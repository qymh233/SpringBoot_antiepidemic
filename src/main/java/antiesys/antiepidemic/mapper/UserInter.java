package antiesys.antiepidemic.mapper;


import antiesys.antiepidemic.pojo.Users;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@Mapper
public interface UserInter {
     //插入一个用户
     int InsertUser(@Param("user") Users user);
     //删除一个用户
     int DeleteUser(@Param("userId")int userId);
     //更新一个用户
     int UpdateUser(@Param("user")Users user);
     //查询一个用户
     Users SelectOne(@Param("userId")int userId);
     //查询所有用户
     List<Users> SelectUsers();
}
