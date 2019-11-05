package cn.org.hentai.simulator.web.mapper;

import cn.org.hentai.simulator.web.entity.Mapper;
import cn.org.hentai.simulator.web.entity.RoutePoint;
import cn.org.hentai.simulator.web.entity.RoutePointExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RoutePointMapper {
    /**
     */
    long countByExample(RoutePointExample example);

    /**
     */
    int deleteByExample(RoutePointExample example);

    /**
     */
    int deleteByPrimaryKey(Long id);

    /**
     */
    int insert(RoutePoint record);

    int batchInsert(List<RoutePoint> points);

    /**
     */
    int insertSelective(RoutePoint record);

    /**
     */
    List<RoutePoint> selectByExample(RoutePointExample example);

    /**
     */
    RoutePoint selectByPrimaryKey(Long id);

    /**
     */
    int updateByExampleSelective(@Param("record") RoutePoint record, @Param("example") RoutePointExample example);

    /**
     */
    int updateByExample(@Param("record") RoutePoint record, @Param("example") RoutePointExample example);

    /**
     */
    int updateByPrimaryKeySelective(RoutePoint record);

    /**
     */
    int updateByPrimaryKey(RoutePoint record);
}