package cn.org.hentai.simulator.web.service;

import cn.org.hentai.simulator.web.entity.DriverPhoto;
import cn.org.hentai.simulator.web.entity.DriverPhotoExample;
import cn.org.hentai.simulator.web.mapper.DriverPhotoMapper;
import cn.org.hentai.simulator.web.vo.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @Description:
 * @author:
 * @date: 2018/12/3 11:18
 * @version: V1.0
 */
@Service
public class XDriverPhotoService {

    @Autowired
    DriverPhotoMapper mapper;

    public int create(DriverPhoto entity) {
        return this.mapper.insert(entity);
    }

    public int update(DriverPhoto entity) {
        return this.mapper.updateByPrimaryKey(entity);
    }

    public int removeById(Long id) {
        return this.mapper.deleteByPrimaryKey(id);
    }

    public int remove(DriverPhoto entity) {
        return this.removeById(entity.getId());
    }

    public List<DriverPhoto> findAll() {
        return this.mapper.selectByExample(new DriverPhotoExample());
    }

    public List<DriverPhoto> findByDriverId(Long driverId) {
        return this.mapper.selectByExample(new DriverPhotoExample().createCriteria().andDriverIdEqualTo(driverId).example());
    }



    public Page<DriverPhoto> findPaginate(Long driverId, String photo, int pageIndex, int pageSize) {
        Page<DriverPhoto> page = new Page<DriverPhoto>(pageIndex, pageSize);
        DriverPhotoExample.Criteria criteria = new DriverPhotoExample().createCriteria();
        if (!StringUtils.isEmpty(photo)) {
            criteria.andPhotoLike("%" + photo + "%");
        }
        if (driverId != null) {
            criteria.andDriverIdEqualTo(driverId);
        }

        criteria.example().setPageInfo(pageIndex, pageSize);
        // 根据驾驶员正序
        page.setList(this.mapper.selectByExample(criteria.example().orderBy("driverId")));
        page.setRecordCount(this.mapper.countByExample(criteria.example()));

        return page;
    }

}
