package cn.org.hentai.simulator.web.service;

import cn.org.hentai.simulator.web.entity.ScheduleTask;
import cn.org.hentai.simulator.web.entity.ScheduleTaskExample;
import cn.org.hentai.simulator.web.mapper.ScheduleTaskMapper;
import cn.org.hentai.simulator.web.vo.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by houcheng on 2018/11/25.
 */
@Service
public class ScheduleTaskService
{
    @Autowired
    ScheduleTaskMapper mapper;

    /**
     *
     * @param task
     * @return
     */
    public int create(ScheduleTask task)
    {
        return mapper.insert(task);
    }

    /**
     *
     * @param task
     * @return
     */
    public int update(ScheduleTask task)
    {
        return mapper.updateByPrimaryKey(task);
    }

    /**
     *
     * @param task
     * @return
     */
    public int remove(ScheduleTask task)
    {
        return removeById(task.getId());
    }

    /**
     *
     * @param taskId
     * @return
     */
    public int removeById(Long taskId)
    {
        return mapper.deleteByPrimaryKey(taskId);
    }

    public List<ScheduleTask> find()
    {
        return mapper.selectByExample(new ScheduleTaskExample());
    }

    /**
     *
     * @param routeId
     * @param vehicleId
     * @param driverId
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public Page<ScheduleTask> find(Long routeId, Long vehicleId, Long driverId, int pageIndex, int pageSize)
    {
        Page<ScheduleTask> page = new Page<>(pageIndex, pageSize);
        ScheduleTaskExample.Criteria criteria = new ScheduleTaskExample().createCriteria();
        if (routeId != null) {
            criteria.andRouteIdEqualTo(routeId);
        }
        if (vehicleId != null) {
            criteria.andVehicleIdEqualTo(vehicleId);
        }
        if (driverId != null) {
            criteria.andDriverIdEqualTo(driverId);
        }
        criteria.example().setPageInfo(pageIndex, pageSize);

        page.setList(mapper.findTask(criteria.example()));
        page.setRecordCount(mapper.countByExample(criteria.example()));
        return page;
    }

    /**
     *
     * @param id
     * @return
     */
    public ScheduleTask getById(Long id) {
        return this.mapper.selectByPrimaryKey(id);
    }
}
