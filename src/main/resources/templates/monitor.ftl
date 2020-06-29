<!DOCTYPE html>
<html lang="en">
<head>
<#include "inc/resource.ftl">
    <title>行程任务详情</title>
    <style type="text/css">
        .content
        {
            padding: 0px 300px 300px 0px;
            position: relative;
        }
        .x-map
        {
            background-color: #000000;
            width: 100%;
            height: 100%;
        }
        .x-log-panel
        {
            position: absolute;
            left: 0px;
            bottom: 0px;
            width: 100%;
            height: 300px;
            border-top: solid 1px #cccccc;
            z-index: 200;
            background-color: #ffffff;
        }
        .x-info-panel
        {
            position: absolute;
            right: 0px;
            top: 0px;
            width: 300px;
            height: 100%;
            border-left: solid 1px #cccccc;
            z-index: 100;
            background-color: #ffffff;
            padding-bottom: 300px;
        }
        .x-panel
        {
            position: relative;
            padding: 50px 10px 10px 10px;
            width: 100%;
            height: 100%;
        }
        .x-panel > h3
        {
            height: 40px;
            border-bottom: solid 1px #cccccc;
            line-height: 40px;
            font-size: 18px;
            margin: 0px;
            padding: 0px 0px 0px 10px;
            position: absolute;
            top: 0px;
            left: 0px;
            width: 100%;
        }
        .x-panel > h3 > button
        {
            float: right;
            margin: 8px 18px 0px 0px;
        }
        .x-panel .x-panel-body
        {
            width: 100%;
            height: 100%;
            overflow-y: auto;
            overflow-x: hidden;
        }
        .x-panel .x-panel-body hr
        {
            border-width: 1px 0px 0px 0px;
            border-color: #cccccc;
        }
        .x-panel .x-panel-body .x-col-3, .x-panel .x-panel-body .x-col-7 { height: 24px; line-height: 24px; }
        .x-panel .x-panel-body .x-col-3
        {
            color: #999999;
        }
        .anchorBL { display: none !important; }
    </style>
</head>
<body>
<div class="container">
<#include "inc/sidebar.ftl">
    <div class="content">
        <div class="x-map" id="map"></div>
        <div class="x-log-panel">
            <div class="x-panel">
                <h3>行程任务日志</h3>
                <div class="x-panel-body">
                    <table class="table table-bordered table-striped table-condensed table-hover">
                        <thead>
                        <tr>
                            <th>#</th>
                            <th>类型</th>
                            <th>时间</th>
                            <th>内容</th>
                        </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <div class="x-info-panel">
            <div class="x-panel">
                <h3>行程任务详情<button class="btn btn-sm btn-blue" id="btn-stop">终止</button></h3>
                <div class="x-panel-body">
                    <div class="x-row">
                        <div class="x-col-3 text-right">线路：</div>
                        <div class="x-col-7" id="routeName">--</div>
                        <div class="x-clearfix"></div>
                    </div>
                    <div class="x-row">
                        <div class="x-col-3 text-right">线路里程：</div>
                        <div class="x-col-7" id="routeMileages">--</div>
                        <div class="x-clearfix"></div>
                    </div>
                    <div class="x-row">
                        <div class="x-col-3 text-right">车牌号：</div>
                        <div class="x-col-7" id="vehicleNumber">--</div>
                        <div class="x-clearfix"></div>
                    </div>
                    <div class="x-row">
                        <div class="x-col-3 text-right">终端ID：</div>
                        <div class="x-col-7" id="deviceSn">--</div>
                        <div class="x-clearfix"></div>
                    </div>
                    <div class="x-row">
                        <div class="x-col-3 text-right">SIM卡号：</div>
                        <div class="x-col-7" id="simNumber">--</div>
                        <div class="x-clearfix"></div>
                    </div>
                    <div class="x-row">
                        <div class="x-col-3 text-right">启动时间：</div>
                        <div class="x-col-7" id="startTime">--</div>
                        <div class="x-clearfix"></div>
                    </div>
                    <hr />
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<#include "inc/footer.ftl">
<script type="text/javascript" src="//api.map.baidu.com/getscript?v=2.0&ak=${baiduMapKey}&services=&t=20180917142401"></script>
<script type="text/javascript" src="${context}/static/js/BMapLib.AutoCar.js"></script>
<script type="text/javascript">

    var taskId = '${id}';
    var map = null;
    var vehicle = null;

    $(document).ready(function()
    {
        setCurrentMenu('list-monitor');

        map = new BMap.Map('map', { enableClick : false });
        map.centerAndZoom('北京市', 9);
        map.enableScrollWheelZoom(true);
        map.setMapStyle({ style : 'midnight' });

        BMapLib.EventBinding.setClickListener(onVehicleClick);

        loadTaskInfo();
    });

    function onVehicleClick()
    {

    }

    function loadTaskInfo()
    {
        $.post('${context}/monitor/info', { id : taskId }, function(result)
        {
            if (result.error && result.error.code) return alert(result.error.reason);
            var info = result.data;
            $('#routeName').html(info.routeName);
            $('#routeMileages').html((info.routeMileages / 1000).toFixed(2) + ' km');
            $('#vehiclenumber').html(info.vehicleNumber);
            $('#deviceSn').html(info.deviceSn);
            $('#simNumber').html(info.simNumber);
            $('#startTime').html(new Date(info.startTime).format('yyyy-MM-dd hh:mm:ss'));

            var startPoint = new BMap.Point(info.longitude, info.latitude);
            map.centerAndZoom(startPoint, 14);

            vehicle = new BMapLib.AutoCar(map, startPoint, {
                label : info.vehicleNumber,
                labelColor : '#0099ff',
                icon : new BMap.Icon('${context}/static/img/vehicle.png', new BMap.Size(40, 40)),
                iconOffset : new BMap.Size(0, 0),
                enableRotation : true,
                autoView : true
            });

            vehicle.show();
        });
    }

    function moveTo(lng, lat, time)
    {

    }

</script>
</html>