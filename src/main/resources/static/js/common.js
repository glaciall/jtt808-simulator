Date.prototype.format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

$.fn.paginate = function(param)
{
    var paginate = this;
    if (typeof(param) == 'string' && param == 'reload')
    {
        this.paginate(this.get(0).parameters);
        return;
    }
    var concatParam = function(param1, param2)
    {
        if (param1 == '') return param2;
        if (param1.charAt(param1.length - 1) == '&') return param1 + param2;
        else return param1 + '&' + param2;
    };

    param = $.extend({
        // 绑定此form表单的submit事件
        form : null,
        // 初始页码
        pageIndex : 1,
        // 是否不生成thead标题行
        nothead : false,
        // 表格样式
        tableStyle : 'table-bordered table-striped table-condensed table-hover',
        // 页码参数名称
        pageIndexName : 'pageIndex',
        // 分页元素容器
        pagination : $('.pagination'),
        // 加载中的提示
        loading : '正在载入，请稍候...',
        // 在加载前执行
        onBefore : null
    }, param);

    if (param.form && typeof(this.get(0).parameters) == 'undefined') param.form.submit(function()
    {
        paginate.paginate(paginate.get(0).parameters);
        return false;
    });

    this.parameters = param;
    var container = $(this);
    container.get(0).parameters = param;
    var urlParameters = param.form;
    if (param.form && param.form.serialize) urlParameters = param.form.serialize();
    else urlParameters = '';
    urlParameters = concatParam(urlParameters, param.pageIndexName + '=' + param.pageIndex);

    if (param.onBefore && typeof(param.onBefore) == 'function') param.onBefore();

    if (param.loading) container.html(param.loading);
    if (param.pagination && param.pagination.html) param.pagination.html('');
    $.post(param.url, urlParameters, function(result)
    {
        if (result.error.code)
        {
            if (param.error) param.error(result, param.url, urlParameters);
            else console.log(result, param.url, urlParameters);
            container.html('<font style="color: red;font-size: 20px">'+result.error.reason+'</font>');
            return;
        }

        var shtml = '';
        if (param.render && typeof(param.render) == 'function')
        {
            shtml = param.render(result.data.result);
        }
        else
        {
            shtml = '<table class="table ' + param.tableStyle + '">';
            if (param.nothead == false) shtml += '<thead><tr>';
            for (var i = 0; param.nothead == false && i < param.fields.length; i++)
            {
                var field = param.fields[i];
                field.align = field.align == null ? 'left' : field.align;
                // shtml += '<th ' + (field.width == null ? '' : 'width="' + field.width + '"') + ' class="text-' + field.align + '">' + field.title + '</th>';

                shtml += '<td ' + (field.width == null ? '' : 'style="width:' + field.width + '"') + ' class="text-' + field.align + '">' + field.title + '</td>';
            }
            if (param.nothead == false) shtml += '</tr></thead>';

            shtml += '<tbody>';


            for (var i = 0; result.data.list && i < result.data.list.length; i++)
            {
                var row = result.data.list[i];

                shtml += '<tr>';
                for (var k = 0; k < param.fields.length; k++)
                {
                    var field = param.fields[k];
                    field.align = field.align == null ? 'left' : field.align;
                    var content = row[field.name];
                    if (typeof(field.formatter) == 'function') content = field.formatter(i, content, row);
                    shtml += '<td ' + (field.width == null ? '' : 'style="width:' + field.width + '"') + ' valign="middle" align="' + field.align + '">' + content + '</td>';
                }
                shtml += '</tr>';
            }
            shtml += '</tbody></table>';
        }

        container.html(shtml);
        if (typeof(param.onComplete) == 'function')
        {
            param.onComplete();
        }

        // 生成分页
        shtml = '<li><a href="javascript:;" x-page="1">首页</a></li>';
        shtml += '<li><a href="javascript:;" x-page="' + Math.max(1, result.data.pageIndex - 1) + '">&laquo;</a></li>';
        for (var i = Math.max(1, result.data.pageIndex - 5), k = 0; i <= Math.min(result.data.pageIndex + 5, result.data.pageCount); i++)
        {
            shtml += '<li class="' + (i == result.data.pageIndex ? 'active' : '') + '"><a x-page="' + i + '" href="javascript:;">' + i + '</a></li>';
        }
        var totalPage = (result.data.pageCount == undefined ? 1 :result.data.pageCount);

        shtml += '<li><a href="javascript:;" x-page="' + Math.min(result.data.pageIndex + 1, totalPage) + '">&raquo;</a></li>';
        shtml += '<li><a href="javascript:;" x-page="' + totalPage + '">末页</a></li>';

        param.pagination.html(shtml);
        if (param.load) param.load();
    });
}