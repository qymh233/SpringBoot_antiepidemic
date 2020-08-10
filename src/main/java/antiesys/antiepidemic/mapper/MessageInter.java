package antiesys.antiepidemic.mapper;

import antiesys.antiepidemic.pojo.Message;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface MessageInter {
    //插入一个信息
    int InsertMessage(Message message);
    //删除一个信息
    int DeleteMessage(@Param("meID")int meID);
    //查询一个信息
    Message SelectOne(@Param("meID")int meID);
    //查询所有信息
    List<Message> SelectMessage();
    //查询记录条数，便于添加记录，自动增加orderNum
    int SelectNum();
}
