package antiesys.antiepidemic.mapper;

import antiesys.antiepidemic.pojo.Message;
import antiesys.antiepidemic.pojo.Users;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface MessageInter {
    /**
     * 插入一条防疫信息
     * @param message 信息对象
     * @return 插入结果
     */
    int InsertMessage(Message message);
    /**
     * 删除一条翻译信息
     * @param meID 信息ID
     * @return 删除结果
     */
    int DeleteMessage(@Param("meID")int meID);
    /**
     * 查询一条防疫信息
     * @param meID 信息ID
     * @return 查询到的信息对象
     */
    Message SelectOne(@Param("meID")int meID);
    /**
     * 查询所有信息
     * @return 查询到的信息列表
     */
    List<Message> SelectMessage();
    /**
     * 查询记录条数，便于添加记录，自动增加orderNum
     * @return 查询到的记录条数
     */
    int SelectNum();
    /**
     * 更新一个物品
     * @param message 信息对象
     * @return 更新结果
     */
    int UpdateMessage(@Param("message") Message message);
}
