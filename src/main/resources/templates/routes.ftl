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
        setCurrentMenu('route');
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
                    name: 'id',
                    title: '操作',
                    align: 'center',
                    width : '160px',
                    formatter: function (i, v, r)
                    {
                        var html = '';
                        html += '<a href="javascript:;" class="btn btn-sm btn-blue">编辑</a>';
                        html += '<a href="javascript:;" onclick="remove(\'' + v + '\')" class="btn btn-sm btn-orange">删除</a>';
                        return html;
                    }
                }
            ]
        });
    });

    function remove(id)
    {
        if (!confirm('真的要删除吗？')) return;
        $.post('${context}/route/remove', { id : id }, function(result)
        {
            if (result.error && result.error.code) return alert(result.error.reason);
            alert('删除成功');
            $('#route-table').paginate('reload');
        });
    }

</script>
</html>