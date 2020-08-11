package antiesys.antiepidemic.mapper;

import antiesys.antiepidemic.pojo.Goods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@Mapper
public interface GoodsInter {
    /**
     * 插入一个物品
     * @param goods 物品对象
     * @return 插入结果
     */
    int InsertGoods(Goods goods);
    /**
     * 删除一个物品
     * @param goodsId 物品ID
     * @return 删除结果
     */
    int DeleteGoods(@Param("goodsId")int goodsId);
    /**
     * 更新一个物品
     * @param goods 物品对象
     * @return 更新结果
     */
    int UpdateGoods(@Param("goods")Goods goods);
    /**
     * 查询一个物品
     * @param goodsId 物品ID
     * @return 查询到的物品对象
     */
    Goods SelectOne(@Param("goodsId")int goodsId);
    /**
     * 查询所有物品
     * @return 查询到的物品列表
     */
    List<Goods> SelectGoods();
    /**
     * 查询记录条数，便于添加记录，自动增加orderNum
     * @return 查询到的记录条数
     */
    int SelectNum();
}
