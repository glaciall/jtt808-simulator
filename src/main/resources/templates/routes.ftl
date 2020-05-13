<!DOCTYPE html>
<html lang="en">
<head>
<#include "inc/resource.ftl">
    <title>线路管理</title>
</head>
<body>
<div class="container">
<#include "inc/sidebar.ftl">
    <div class="content datasheet">
        <h2>线路管理<button class="btn btn-sm btn-blue pull-right">创建新线路</button></h2>
        <hr />
        <div id="route-table">
            <table>
                <thead>
                <th>#</th>
                <th>名称</th>
                <th>最高时速</th>
                <th>最低时速</th>
                <th>总距离</th>
                <th>--</th>
                </thead>
                <tbody>
                <tr>
                    <td>1</td>
                    <td>北京 - 广州</td>
                    <td>30 km/h</td>
                    <td>80 km/h</td>
                    <td>172 km</td>
                    <td>
                        <button class="btn btn-sm btn-blue">修改</button>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <ul class="pagination"></ul>
    </div>
</div>
</body>
<#include "inc/footer.ftl">
<script type="text/javascript">
    $(document).ready(function()
    {
        $('#route-table').paginate({
            url: '${context}/route/list',
            fields: [
                {
                    name: 'id',
                    title: '线路ID',
                    align: 'center',
                    width: '160px',
                    formatter: function (i, v, r)
                    {
                        return v;
                    }
                },
                {
                    name: 'name',
                    title: '线路名称',
                    align: 'left',
                    formatter: function (i, v, r)
                    {

                        return v;
                    }
                },
                {
                    name: 'minSpeed',
                    title: '最低速度',
                    align: 'right',
                    width : '160px',
                    formatter: function (i, v, r)
                    {
                        return v + ' km/h';
                    }
                },
                {
                    name: 'maxSpeed',
                    title: '最高速度',
                    align: 'right',
                    width : '160px',
                    formatter: function (i, v, r)
                    {
                        return v + ' km/h';
                    }
                },
                {
                    name: 'mileages',
                    title: '里程',
                    align: 'right',
                    width : '160px',
                    formatter: function (i, v, r)
                    {
                        return v ? (v / 1000).toFixed(1) + ' km' : '--';
                    }
                },
                {
                    name: '-',
                    title: '操作',
                    align: 'center',
                    width : '160px',
                    formatter: function (i, v, r)
                    {
                        var html = '<a href="${context}/route/edit?id=' + r.id + '" target="_blank" class="btn btn-sm btn-blue">编辑</a>';
                        return html;
                    }
                }
            ]
        });
    });
</script>
</html>