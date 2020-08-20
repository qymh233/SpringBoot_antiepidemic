package antiesys.antiepidemic.mapper;


import antiesys.antiepidemic.pojo.Users;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@Mapper
public interface UserInter {
     /**
      * 插入一个用户
      * @param user 用户对象
      * @return 插入结果
      */
     int InsertUser(@Param("user") Users user);
     /**
      * 删除一个用户
      * @param userId 用户ID
      * @return 删除结果
      */
     int DeleteUser(@Param("userId")int userId);
     /**
      * 更新一个用户
      * @param user 用户对象
      * @return 更新结果
      */
     int UpdateUser(@Param("user")Users user);
     /**
      * 查询一个用户
      * @param userId 用户ID
      * @return 查询到的用户对象
      */
     Users SelectOne(@Param("userId")int userId);
     /**
      * 查询所有用户
      * @return 查询到的用户列表
      */
     List<Users> SelectUsers();
}
