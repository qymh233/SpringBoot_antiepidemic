package antiesys.antiepidemic.mapper;

import antiesys.antiepidemic.pojo.Goods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@Mapper
public interface GoodsInter {
    //插入一个物品
    int InsertGoods(Goods goods);
    //删除一个物品
    int DeleteGoods(@Param("goodsId")int goodsId);
    //更新一个物品
    int UpdateGoods(@Param("goods")Goods goods);
    //查询一个物品
    Goods SelectOne(@Param("goodsId")int goodsId);
    //查询所有物品
    List<Goods> SelectGoods();
    //查询记录条数，便于添加记录，自动增加orderNum
    int SelectNum();
}
