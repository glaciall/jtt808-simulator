<!DOCTYPE html>
<html lang="en">
<head>
    <#include "inc/resource.ftl">
    <title>创建新线路</title>
    <style type="text/css">
        .content
        {
            position: relative;
            padding-left: 400px;
        }
        .content #panel
        {
            position: absolute;
            top: 0px;
            left: 0px;
            width: 400px;
            height: 100%;
            background-color: #ffffff;
            border-right: solid 1px #666666;
        }
        #panel h4 { height: 30px; line-height: 30px; margin: 20px 0px 10px 0px; padding-left: 10px; font-size: 16px; border-bottom: solid 1px #cccccc; }
        #panel h4:first-child { margin-top: 0px !important; height: 40px; line-height: 40px; padding-right: 10px; box-sizing: border-box; }
        #panel h4:first-child button { margin-top: 7px; }
        #panel h4 a { float: right; margin-right: 10px; }
        #panel h4 button { float: right; }
        #panel .x-row { height: 30px; line-height: 30px; }
        #panel .x-col-7 input[type=text] { min-width: 200px; }
        #panel #btn-locate-point { margin-right: 5px; }
        #map { width: 100%; height: 100%; }
        .anchorBL { display: none !important; }
    </style>
</head>
<body>
<div class="container">
    <#include "inc/sidebar.ftl">
    <div class="content">
        <div id="panel">
            <h4>
                线路设置
                <button id="btn-save" class="btn btn-sm btn-blue">保存</button>
                <button id="btn-plan" class="btn btn-sm btn-orange">线路规划</button>
                <button id="btn-measure" class="btn btn-sm btn-gray">测距</button>
            </h4>
            <div class="x-row">
                <div class="x-col-3 text-right">线路总里程：</div>
                <div class="x-col-7">
                    <span id="route-distance">-</span>
                </div>
                <div class="x-clearfix"></div>
            </div>
            <div class="x-row">
                <div class="x-col-3 text-right">行程耗时估计：</div>
                <div class="x-col-7"><span id="route-duration">-</span></div>
                <div class="x-clearfix"></div>
            </div>
            <div class="x-row">
                <div class="x-col-3 text-right">名称：</div>
                <div class="x-col-7"><input type="text" id="name" placeholder="比如：北京~上海 早班线路" value="" /></div>
                <div class="x-clearfix"></div>
            </div>
            <div class="x-row">
                <div class="x-col-3 text-right">最低时速：</div>
                <div class="x-col-7"><input type="text" id="minSpeed" placeholder="公里/小时" value=""/></div>
                <div class="x-clearfix"></div>
            </div>
            <div class="x-row">
                <div class="x-col-3 text-right">最高时速：</div>
                <div class="x-col-7"><input type="text" id="maxSpeed" placeholder="公里/小时" value=""/></div>
                <div class="x-clearfix"></div>
            </div>

            <h4>停留点<a href="javascript:;" id="btn-add-point"><i class="fa fa-crosshairs"></i> 添加</a></h4>
            <div class="x-row text-center">
                <div class="x-col-2">#</div>
                <div class="x-col-2">最短</div>
                <div class="x-col-2">最长</div>
                <div class="x-col-2">概率</div>
                <div class="x-col-2">-</div>
                <div class="x-clearfix"></div>
            </div>
            <div id="x-stay-points"></div>

            <h4>问题路段<a href="javascript:;" id="btn-add-segment"><i class="fa fa-wheelchair-alt"></i> 添加</a></h4>
            <div class="x-row text-center">
                <div class="x-col-2">#</div>
                <div class="x-col-4">安全事件</div>
                <div class="x-col-2">概率</div>
                <div class="x-col-2">-</div>
                <div class="x-clearfix"></div>
            </div>
            <div id="x-trouble-segments"></div>
        </div>
        <div id="map"></div>
    </div>
</div>
</body>
<#include "inc/footer.ftl">
<script type="text/javascript" src="//api.map.baidu.com/getscript?v=2.0&ak=${baiduMapKey}&services=&t=20180917142401"></script>
<script type="text/javascript" src="//api.map.baidu.com/library/DistanceTool/1.2/src/DistanceTool_min.js"></script>
<script type="text/javascript">
    var map = null;
    var ruler = null;

    var eventTypes = [
        { code : 'e-illegal', name : '违规驾驶' },
        { code : 'e-danger', name : '危险驾驶' },
        { code : 'e-tire', name : '疲劳驾驶' },
        { code : 'e-adas', name : '车辆运行监测' },
        { code : 'e-vhitch', name : '车辆故障' },
        { code : 'e-dhitch', name : '车机故障' },
        { code : 'e-other', name : '其它' }
    ];

    var nid = 0x01;
    function id() { return nid++; }

    var route = {
        mileages : 0,
        points : [],
        stayPoints : [],
        troubleSegments : [],

        // 添加停留点
        addStayPoint : function(lng, lat, marker)
        {
            this.stayPoints.push({ id : id(), longitude : lng, latitude : lat, minTime : 5, maxTime : 15, ratio : 20, marker : marker });
            this.__generate_stay_point_rows();
        },

        // 停留点定位
        locateStayPoint : function(id)
        {
            for (var i = 0; i , this.stayPoints.length; i++)
            {
                var item = this.stayPoints[i];
                if (item.id == id)
                {
                    map.centerAndZoom(new BMap.Point(item.longitude, item.latitude), 18);
                    item.marker.setAnimation(BMAP_ANIMATION_BOUNCE);
                    setTimeout(function() { item.marker.setAnimation(null); }, 2000);
                    break;
                }
            }
        },

        // 删除停留点
        removeStayPoint : function(id)
        {
            var list = [];
            var points = this.stayPoints.length;
            var stayPoint = null;
            for (var i = 0; i < points; i++)
            {
                var item = this.stayPoints[i];
                if (item.id == id)
                {
                    stayPoint = item;
                    continue;
                }
                list.push(item);
            }
            this.stayPoints = list;

            if (points != list.length) this.__generate_stay_point_rows();
            return stayPoint;
        },

        // 修改停留点信息
        setStayPointValues : function(id, values, updateView)
        {
            for (var i = 0; i < this.stayPoints.length; i++)
            {
                var item = this.stayPoints[i];
                if (item.id == id)
                {
                    for (var j in values) item[j] = values[j];
                    break;
                }
            }
            if (updateView) this.__generate_stay_point_rows();
        },

        // 添加问题路段
        addTroubleSegment : function(sindex, eindex)
        {
            this.troubleSegments.push({ id : id(), startIndex : sindex, endIndex : eindex, eventCode : 'e-danger', ratio : 20 });
            this.__generate_segment_rows();
        },

        // 删除问题路段
        removeTroubleSegment : function(id)
        {
            var list = [];
            var polyline = null;
            for (var i = 0; i < this.troubleSegments.length; i++)
            {
                var item = this.troubleSegments[i];
                if (item.id == id)
                {
                    polyline = item.polyline;
                    continue;
                }
                list.push(item);
            }
            this.troubleSegments = list;
            this.__generate_segment_rows();

            return polyline;
        },

        // 修改问题路段信息
        setTroubleSegmentValues : function(id, values, updateView)
        {
            for (var i = 0; i < this.troubleSegments.length; i++)
            {
                var item = this.troubleSegments[i];
                if (item.id == id)
                {
                    for (var j in values) {
                        item[j] = values[j];
                    }
                    // 重新赋值
                    this.troubleSegments[i] = item;
                    break;
                }
            }
            if (updateView) this.__generate_segment_rows();
        },

        locateSegment : function(id)
        {
            for (var i = 0; i , this.troubleSegments.length; i++)
            {
                var item = this.troubleSegments[i];
                if (item.id == id)
                {
                    var points = [];
                    for (var i = item.startIndex; i <= item.endIndex; i++)
                    {
                        var p = this.points[i];
                        points.push(new BMap.Point(p.lng, p.lat));
                    }
                    map.setViewport(points);
                    return;
                }
            }
        },

        // 生成问题路段行
        __generate_segment_rows : function()
        {
            var shtml = '';
            for (var i = 0; i < this.troubleSegments.length; i++)
            {
                var item = this.troubleSegments[i];
                shtml += '<div class="x-row text-center" x-id="' + item.id + '">';
                shtml += '  <div class="x-col-2">' + item.id + '</div>';
                shtml += '  <div class="x-col-4">' + this.__generate_event_types(item.eventCode) + '</div>';
                shtml += '  <div class="x-col-2"><input type="text" id="ratio" value="' + v(item.ratio) + '" placeholder="0~100" /></div>';
                shtml += '  <div class="x-col-2">';
                shtml += '      <a href="#" id="btn-locate-segment" x-id="' + item.id + '"><i class="fa fa-map-pin"></i></a>';
                shtml += '      <a href="#" id="btn-remove-segment" x-id="' + item.id + '"><i class="fa fa-times"></i></a>';
                shtml += '  </div>';
                shtml += '  <div class="x-clearfix"></div>';
                shtml += '</div>';
            }
            $('#x-trouble-segments').html(shtml);
        },

        __generate_event_types : function(currentValue)
        {
            var shtml = '<select id="eventCode">';
            shtml += '<option value="">请选择事件分类</option>';
            for (var i = 0; i < eventTypes.length; i++)
            {
                var type = eventTypes[i];
                shtml += '<option value="' + type.code + '" ' + (currentValue == type.code ? 'selected' : '') + '>' + type.name + '</option>';
            }
            shtml += '</select>';
            return shtml;
        },

        // 生成停留点行
        __generate_stay_point_rows : function()
        {
            var shtml = '';
            for (var i = 0; i < this.stayPoints.length; i++)
            {
                var item = this.stayPoints[i];
                shtml += '<div class="x-row text-center" x-id="' + item.id + '">';
                shtml += '  <div class="x-col-2">' + item.id + '</div>';
                shtml += '  <div class="x-col-2"><input type="text" id="minTime" value="' + v(item.minTime) + '" placeholder="分钟" /></div>';
                shtml += '  <div class="x-col-2"><input type="text" id="maxTime" value="' + v(item.maxTime) + '" placeholder="分钟" /></div>';
                shtml += '  <div class="x-col-2"><input type="text" id="ratio" value="' + v(item.ratio) + '" placeholder="0~100" /></div>';
                shtml += '  <div class="x-col-2">';
                shtml += '      <a href="javascript:;" id="btn-locate-point" x-id="' + item.id + '"><i class="fa fa-map-pin"></i></a>';
                shtml += '      <a href="javascript:;" id="btn-remove-point" x-id="' + item.id + '"><i class="fa fa-times"></i></a>';
                shtml += '  </div>';
                shtml += '  <div class="x-clearfix"></div>';
                shtml += '</div>';
            }
            $('#x-stay-points').html(shtml);
        },
    };

    var mode = 'plan';

    function v(v)
    {
        return v == null || typeof(v) == 'undefined' ? '' : v;
    }

    $(document).ready(function()
    {
        map = new BMap.Map('map', { enableMapClick : false });
        map.centerAndZoom('中国', 6);
        map.setMapStyle({ style : 'midnight' });
        map.enableScrollWheelZoom();
        ruler = new BMapLib.DistanceTool(map);

        map.addEventListener('click', function(e)
        {
            var point = e.point;
            if (mode != 'point') return;

            var marker = new BMap.Marker(new BMap.Point(point.lng, point.lat));
            map.addOverlay(marker);

            route.addStayPoint(point.lng, point.lat, marker);
        });

        $('#btn-measure').click(function()
        {
            ruler.open();
        });

        $(document).on('click', 'a[id=btn-locate-point]', function()
        {
            var pid = $(this).attr('x-id');
            route.locateStayPoint(pid);
        });

        $(document).on('click', 'a[id=btn-locate-segment]', function()
        {
            var pid = $(this).attr('x-id');
            route.locateSegment(pid);
        });

        $('#btn-plan').click(function()
        {
            var saddress = prompt("起点", "北京"), eaddress = prompt("终点", "广州");
            if (saddress.length == 0 || eaddress.length == 0)
                return toastr('warning', '请输入起点以及终点地址！');

            $('#route-duration').html('-');
            $('#route-distance').html('-');

            map.clearOverlays();
            route.points = [];
            route.stayPoints = [];
            route.troubleSegments = [];

            var transit = new BMap.DrivingRoute(map, {
                renderOptions: {
                    map: map,
                    enableDragging : true
                },
                onSearchComplete : function(result)
                {
                    if (result.getNumPlans() == 0) return;
                    var plan = result.getPlan(0);
                    var duration = plan.getDuration();
                    var distance = plan.getDistance();
                    $('#route-duration').html(duration);
                    $('#route-distance').html(distance);

                    route.mileages = plan.getDistance(false);
                },
                onPolylinesSet : function(routes)
                {
                    var points = [];
                    for (var i = 0; i < routes.length; i++)
                    {
                        var line = routes[i].getPolyline();
                        line.setStrokeColor('#ffffff');
                        points = points.concat(line.getPath());
                    }
                    if (points.length == 0) return toastr('warning', '无法完成从 ' + saddress + ' 到 ' + eaddress + ' 的线路规划！');

                    // 点集合
                    for (var i = 0; i < points.length; i++)
                    {
                        points[i].index = i;
                    }

                    route.points = points;
                    route.stayPoints = [];
                    route.troubleSegments = [];
                    route.__generate_stay_point_rows();
                    route.__generate_segment_rows();
                    isMarkMode = false;
                }
            });
            transit.search(saddress, eaddress);
        });

        $('#btn-add-point').click(function()
        {
            // 点一下，加个点，然后添加停留点
            if (route.points.length == 0) return;
            markMode();
            mode = 'point';
            toastr('success', '请在地图线路附近点击以设置停留点，尽量不超过线路小蓝点50米的距离');
        });

        // 停留点的设值
        $(document).on('change', '#x-stay-points input', function()
        {
            var el = $(this);
            var id = el.parents('.x-row').attr('x-id');
            var val = $.trim(el.val());
            var type = el.attr('id');
            var values = { };
            values[type] = val;
            route.setStayPointValues(id, values, false);
        });

        // 删除停留点
        $(document).on('click', 'a[id=btn-remove-point]', function()
        {
            var id = $(this).attr('x-id');
            var point = route.removeStayPoint(id);
            map.removeOverlay(point.marker);
        });

        // 设置问题路段
        $('#btn-add-segment').click(function()
        {
            if (route.points.length == 0) return;
            startPoint = null;
            endPoint = null;
            markMode();
            mode = 'segment';
            toastr('success', '请分别点击线路上的小蓝点，以设置问题路段的起点与终点。');
        });

        // 设置路段值
        $(document).on('change', '#x-trouble-segments input[type=text]', function()
        {
            var el = $(this);
            var type = el.attr('id');
            var val = $.trim(el.val());
            var id = el.parents('.x-row').attr('x-id');
            var values = {};
            values[type] = val;
            route.setTroubleSegmentValues(id, values, false);
        });

        $(document).on('change', 'select[id=eventCode]', function()
        {
            var el = $(this);
            var val = $.trim(el.val());
            var id = el.parents('.x-row').attr('x-id');
            route.setTroubleSegmentValues(id, { eventCode : val }, false);
        });

        // 删除路段
        $(document).on('click', 'a[id=btn-remove-segment]', function()
        {
            var id = $(this).attr('x-id');
            var polyline = route.removeTroubleSegment(id);
            map.removeOverlay(polyline);
        });

        // 保存线路信息
        $('#btn-save').click(function()
        {
            var name = $.trim($('#when').val());
            var minSpeed = $.trim($('#minSpeed').val());
            var maxSpeed = $.trim($('#maxSpeed').val());
            var pointsJson = toJson(route.points);
            var stayPointsJson = toJson(route.stayPoints);
            var segmentsJson = toJson(route.troubleSegments);

            if (name.length == 0) return toastr('warning', '请输入线路名称');
            if (isNaN(minSpeed) || isNaN(maxSpeed) || minSpeed.length == 0 || maxSpeed.length == 0) return toastr('warning', '请输入速度区间'), false;
            if (route.points.length == 0) return toastr('warning', '你尚未设定线路轨迹'), false;
            if (route.stayPoints.length == 0 && !confirm('你未设定停留点，是否继续保存？')) return false;
            if (route.troubleSegments.length == 0 && !confirm('你未设定问题路段，是否继续保存？')) return false;

            $.post('${context}/route/save', { name : name, minSpeed : minSpeed, maxSpeed : maxSpeed,
                mileages : route.mileages, pointsJsonText : pointsJson, stayPointsJsonText : stayPointsJson, segmentsJsonText : segmentsJson }, function(result)
            {
                if (result.error && result.error.code) return alert(result.error.reason);
                alert('保存成功');
                window.close();
            });
        });
    });

    function toJson(list)
    {
        var xx = [];
        for (var i = 0; i < list.length; i++)
        {
            var obj = list[i];
            var oo = { };
            for (var j in obj)
            {
                if (j.match(/^(index|marker|polyline)$/gi)) continue;
                oo[j] = obj[j];
            }
            xx.push(oo);
        }
        return JSON.stringify(xx);
    }

    var startPoint = null;
    var endPoint = null;

    var isMarkMode = false;
    function markMode()
    {
        if (isMarkMode) return;
        isMarkMode = true;
        map.clearOverlays();
        var pc = new BMap.PointCollection(route.points, { color : '#0099ff' });
        pc.addEventListener('click', function(e)
        {
            if (mode != 'segment') return;

            if (startPoint == null)
            {
                startPoint = e.point;
                return;
            }
            if (startPoint && endPoint == null) endPoint = e.point;

            var indexes = [startPoint.index, endPoint.index];
            indexes.sort();
            route.addTroubleSegment(indexes[0], indexes[1]);
            var segment = route.troubleSegments[route.troubleSegments.length - 1];
            var points = [];
            for (var i = indexes[0], l = indexes[1]; i <= l; i++)
            {
                points.push(route.points[i]);
            }
            var line = new BMap.Polyline(points, { strokeWeight : 5, strokeColor : '#ff6600', strokeOpacity : 1 });
            map.addOverlay(line);
            route.setTroubleSegmentValues(segment.id, { polyline : line }, false);

            mode = '';
        });
        map.addOverlay(pc);

        var routeLine = new BMap.Polyline(route.points, { strokeColor : '#ffffff', opacity : 1 });
        map.addOverlay(routeLine);
    }

</script>
</html>