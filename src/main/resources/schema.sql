/* 模拟器相关表 */
CREATE TABLE if not exists `x_route` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(100) DEFAULT NULL COMMENT 'name',
  `minSpeed` int(11) DEFAULT NULL COMMENT 'minSpeed（公里每小时）',
  `maxSpeed` int(11) DEFAULT NULL COMMENT 'maxSpeed',
  `mileages` int(11) DEFAULT NULL COMMENT '里程（公里）',
  `fingerPrint` varchar(100) DEFAULT NULL COMMENT '线路指纹',
  PRIMARY KEY (`id`)
) COMMENT='线路';

CREATE TABLE if not exists `x_route_point` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `routeId` bigint(20) DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  `latitude` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) COMMENT='线路轨迹点';

create index if not exists idx_route_id on x_route_point(routeId);

CREATE TABLE if not exists `x_schedule_task` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `routeId` bigint(20) DEFAULT NULL COMMENT '线路ID',
  `driverId` bigint(20) DEFAULT NULL COMMENT '驾驶员ID',
  `vehicleId` bigint(20) DEFAULT NULL COMMENT '车辆ID',
  `fromTime` varchar(20) DEFAULT NULL COMMENT '行程的开始时间的最早时间',
  `endTime` varchar(20) DEFAULT NULL COMMENT '行程的开始时间的最晚时间',
  `ratio` int(11) DEFAULT NULL COMMENT '概率，',
  `daysInterval` int(11) DEFAULT NULL COMMENT '每隔几天运行一次',
  `driveCount` int(11) DEFAULT NULL COMMENT '行驶次数计数',
  `lastDriveTime` datetime DEFAULT NULL COMMENT '最后行驶开始时间',
  `runCount` int(11) DEFAULT NULL COMMENT '运行次数',
  PRIMARY KEY (`id`)
) COMMENT='线路行程计划任务';

create index if not exists idx_schedule_route_id on x_schedule_task(routeId);
create index if not exists idx_schedule_driver_id on x_schedule_task(driverId);
create index if not exists idx_schedule_vehicle_id on x_schedule_task(vehicleId);

CREATE TABLE if not exists `x_stay_point` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `routeid` bigint(20) DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  `latitude` double DEFAULT NULL,
  `minTime` int(11) DEFAULT NULL,
  `maxTime` int(11) DEFAULT NULL,
  `ratio` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) COMMENT='线路停留点';

create index if not exists idx_stay_point_route_id on x_stay_point(routeId);

CREATE TABLE if not exists `x_trouble_segment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `routeId` bigint(20) DEFAULT NULL,
  `startIndex` int(11) DEFAULT NULL,
  `endIndex` int(11) DEFAULT NULL,
  `eventCode` varchar(20) DEFAULT NULL,
  `ratio` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) COMMENT='线路问题路段';

create index if not exists idx_trouble_segment_route_id on x_trouble_segment(routeId);
