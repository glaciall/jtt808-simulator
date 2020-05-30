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
        <h2>线路管理<a href="${context}/route/create" class="btn btn-sm btn-blue pull-right">创建新线路</a></h2>
        <hr />
        <div id="route-table"></div>
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
                        var html = '';
                        html += '<a href="${context}/route/edit?id=' + r.id + '" target="_blank" class="btn btn-sm btn-blue">编辑</a>';
                        return html;
                    }
                }
            ]
        });
    });
</script>
</html>