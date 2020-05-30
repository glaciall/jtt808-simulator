<!DOCTYPE html>
<html lang="en">
<head>
    <#include "inc/resource.ftl">
    <title>创建临时行程</title>
    <style type="text/css">
        .container .content .x-row
        {
            height: 40px;
            line-height: 40px;
        }
        .container .content
        {
            padding: 20px 20px 20px 20px;
        }
    </style>
</head>
<body>
<div class="container">
    <#include "inc/sidebar.ftl">
    <div class="content">
        <h2>创建临时行程任务</h2>
        <hr />
        <div class="x-row">
            <div class="x-col-2 text-right">行驶线路：</div>
            <div class="x-col-6">
                <select name="routeId" id="routeId" class="">
                    <option value="0">- 请选择行驶线路 -</option>
                    <#list routes as item>
                        <option value="${item.id}">${item.name} - ${item.kilometers!'NaN'}公里</option>
                    </#list>
                </select>
            </div>
            <div class="x-clearfix"></div>
        </div>
        <div class="x-row">
            <div class="x-col-2 text-right">车牌号：</div>
            <div class="x-col-6"><input type="text" name="vehicleNumber" id="vehicleNumber" /></div>
            <div class="x-clearfix"></div>
        </div>
        <div class="x-row">
            <div class="x-col-2 text-right">终端ID：</div>
            <div class="x-col-6"><input type="text" name="deviceSn" id="deviceSn" /></div>
            <div class="x-clearfix"></div>
        </div>
        <div class="x-row">
            <div class="x-col-2 text-right">终端SIM卡号：</div>
            <div class="x-col-6"><input type="text" name="simNumber" id="simNumber" /></div>
            <div class="x-clearfix"></div>
        </div>
        <div class="x-row">
            <div class="x-col-2 text-right">808网关服务器：</div>
            <div class="x-col-6"><input type="text" name="serverAddress" id="serverAddress" /></div>
            <div class="x-clearfix"></div>
        </div>
        <div class="x-row">
            <div class="x-col-2 text-right">808网关服务器端口：</div>
            <div class="x-col-6"><input type="text" name="serverPort" id="serverPort" /></div>
            <div class="x-clearfix"></div>
        </div>
        <div class="x-row">
            <div class="x-col-2 text-right">&nbsp;</div>
            <div class="x-col-6">
                <button class="btn btn-blue">启动</button>
            </div>
            <div class="x-clearfix"></div>
        </div>
    </div>
</div>
</body>
<#include "inc/footer.ftl">
</html>