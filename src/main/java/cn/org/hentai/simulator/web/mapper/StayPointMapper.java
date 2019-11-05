package cn.org.hentai.simulator.web.mapper;

import cn.org.hentai.simulator.web.entity.Mapper;
import cn.org.hentai.simulator.web.entity.StayPoint;
import cn.org.hentai.simulator.web.entity.StayPointExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StayPointMapper {
    /**
     */
    long countByExample(StayPointExample example);

    /**
     */
    int deleteByExample(StayPointExample example);

    /**
     */
    int deleteByPrimaryKey(Long id);

    /**
     */
    int insert(StayPoint record);

    /**
     */
    int insertSelective(StayPoint record);

    /**
     */
    List<StayPoint> selectByExample(StayPointExample example);

    /**
     */
    StayPoint selectByPrimaryKey(Long id);

    /**
     */
    int updateByExampleSelective(@Param("record") StayPoint record, @Param("example") StayPointExample example);

    /**
     */
    int updateByExample(@Param("record") StayPoint record, @Param("example") StayPointExample example);

    /**
     */
    int updateByPrimaryKeySelective(StayPoint record);

    /**
     */
    int updateByPrimaryKey(StayPoint record);
}