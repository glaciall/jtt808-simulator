package cn.org.hentai.simulator.web.mapper;

import cn.org.hentai.simulator.web.entity.DriverPhoto;
import cn.org.hentai.simulator.web.entity.DriverPhotoExample;
import cn.org.hentai.simulator.web.entity.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface DriverPhotoMapper {
    /**
     *
     * @mbg.generated Mon Dec 03 11:07:54 CST 2018
     */
    long countByExample(DriverPhotoExample example);

    /**
     *
     * @mbg.generated Mon Dec 03 11:07:54 CST 2018
     */
    int deleteByExample(DriverPhotoExample example);

    /**
     *
     * @mbg.generated Mon Dec 03 11:07:54 CST 2018
     */
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbg.generated Mon Dec 03 11:07:54 CST 2018
     */
    int insert(DriverPhoto record);

    /**
     *
     * @mbg.generated Mon Dec 03 11:07:54 CST 2018
     */
    int insertSelective(DriverPhoto record);

    /**
     *
     * @mbg.generated Mon Dec 03 11:07:54 CST 2018
     */
    List<DriverPhoto> selectByExample(DriverPhotoExample example);

    /**
     *
     * @mbg.generated Mon Dec 03 11:07:54 CST 2018
     */
    DriverPhoto selectByPrimaryKey(Long id);

    /**
     *
     * @mbg.generated Mon Dec 03 11:07:54 CST 2018
     */
    int updateByExampleSelective(@Param("record") DriverPhoto record, @Param("example") DriverPhotoExample example);

    /**
     *
     * @mbg.generated Mon Dec 03 11:07:54 CST 2018
     */
    int updateByExample(@Param("record") DriverPhoto record, @Param("example") DriverPhotoExample example);

    /**
     *
     * @mbg.generated Mon Dec 03 11:07:54 CST 2018
     */
    int updateByPrimaryKeySelective(DriverPhoto record);

    /**
     *
     * @mbg.generated Mon Dec 03 11:07:54 CST 2018
     */
    int updateByPrimaryKey(DriverPhoto record);

    /**
     *
     * @mbg.generated Mon Dec 03 11:07:54 CST 2018
     */
    int batchInsert(@Param("list") List<DriverPhoto> list);

    /**
     *
     * @mbg.generated Mon Dec 03 11:07:54 CST 2018
     */
    int batchInsertSelective(@Param("list") List<DriverPhoto> list, @Param("selective") DriverPhoto.Column... selective);
}