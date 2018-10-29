$(function () {
    setPageSize();
    setDate();
    setBackground();
    setInterval('setDate()', 60000);
    query();
});

function changeTags(obj) {
    $('div.l-labels ul li').removeClass('layui-this');
    $(obj).addClass('layui-this');
    var lVal = $(obj).attr('labelVal');
    $('div.l-labels div div').removeClass('layui-show');
    $.each($('div.l-labels div div'), function (i, e) {
        if ($(e).attr('labelVal') == lVal) {
            $(e).addClass('layui-show');
            return false;
        }
    });
}

function setPageSize() {
    var width = window.innerWidth;
    var height = window.innerHeight;
    var leftW = 400;
    var rightW = width - leftW - 1;
    $('div.page-container').attr('style', 'width:' + width + 'px;height:' + height + 'px');
    $('div.left').attr('style', 'width:' + leftW + 'px;height:' + height + 'px;float:left');
    $('div.right').attr('style', 'width:' + rightW + 'px;height:' + height + 'px;float:left');
}

function setDate() {
    var today = new Date();
    var yyyy = today.getFullYear();
    var mm = fomartDate(today.getMonth() + 1);
    var dd = fomartDate(today.getDate());
    var hh = fomartDate(today.getHours());
    var MM = fomartDate(today.getMinutes());
    // var ss = fomartDate(today.getSeconds());
    var date = yyyy + '-' + mm + '-' + dd;
    var time = hh + ':' + MM;
    $('div.date').text(date);
    $('div.time').text(time);
}

function fomartDate(v) {
    return v < 10 ? '0' + v : v;
}

function setBackground() {
    // var max = 0;
    // var min = 10;
    // var rand = parseInt(Math.random() * (max - min + 1) + min);
    var rand = 1;
    $('body').attr('background', '/static/image/bg/' + rand + '.jpg');
}

function changeSearchProvider(obj) {
    var urls = ['https://www.baidu.com/baidu', 'https://cn.bing.com/search', 'https://www.google.com/search'];
    var opt = $(obj).val();
    if (opt == 0) {
        $('#keyWord').attr('name', 'word');
    } else if (opt == 1) {
        $('#keyWord').attr('name', 'q');
    } else if (opt == 2) {
        $('#keyWord').attr('name', 'q');
    }
    $('form').attr('action', urls[opt]);
}

function subCheck() {
    if (!$('input[name=word]').val()) {
        return false;
    }
}

function query() {
    ajaxGet('/label/all', {}, function (response1) {
        ajaxGet('/website/classifyData', {}, function (response2) {
            var labels = [];
            $.each(response1, function (i, e) {
                var myLab = e;
                $.each(response2, function (ii, ee) {
                    if (e.labelId == ii) {
                        myLab.websites = ee;
                        return;
                    }
                });
                labels.push(myLab);
            });
            var labelTmpl = $('#labelTmpl').html();
            var labelHtml = doT.template(labelTmpl)(labels);
            $('div.l-context').html(labelHtml);
        });
    });
}

function openWebsite(obj) {
    var url = $(obj).data('url');
    window.open(url);
}

function editPage() {
    $editDom = $('div.layui-tab div.edit');
    var state = $editDom.data('state');
    if (state == 'view') {
        $editDom.data('state', 'edit');
        $editDom.show();
    } else {
        $editDom.data('state', 'view');
        $editDom.hide();
        quitEditWebSite();
        quitEditLable();
        query();
    }
}

function intoEditWebSite() {
    $('div.layui-tab-content').find('div.layui-show').find('div').removeClass('d-website');
    $('div.layui-tab-content').find('div.layui-show').find('div').addClass('d-website-edit');
    $('div.layui-tab-content').find('div.layui-show').find('div').attr('onclick', 'editWebsite(this)');
}

function quitEditWebSite() {
    $('div.layui-tab-content').find('div.layui-show').find('div').removeClass('d-website-edit');
    $('div.layui-tab-content').find('div.layui-show').find('div').addClass('d-website');
    $('div.layui-tab-content').find('div.layui-show').find('div').attr('onclick', 'openWebsite(this)');
}

function intoEditLable() {
    $('div.layui-tab').find('.is-label').addClass('label-edit');
    $('div.layui-tab').find('.is-label').attr('onclick', 'editLabel(this)');
}

function quitEditLable() {
    $('div.layui-tab-title').find('.is-label').removeClass('label-edit');
    $('div.layui-tab-title').find('.is-label').attr('onclick', 'editLabel(this)');
}

function editWebsite(obj) {
    var wsid = $(obj).data('wsid');
    if (wsid) {
        openSelfDialog('edit', '编辑站点', '/static/html/blogModel/input_website.html?wsid=' + wsid, 500, 300);
    }
}

function editLabel(obj) {
    var labelId = $(obj).attr('labelVal');
    if (labelId) {
        openSelfDialog('edit', '编辑标签', '/static/html/blogModel/input_label.html?labelId=' + labelId, 500, 300);
    }
}

function toLogin() {
    ajaxGet('/toLogin', {}, function (data) {
        window.location.href = '/' + data;
    });
}