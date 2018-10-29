var menuChoosedClass = 'layui-nav-itemed';
var menuChild = 'child-menu';

$(function () {
    $('div.layui-header ul.layui-layout-left li').first().find('a').click();
});

function queryMenu(systemId) {
    loadContent('/static/html/manage/index.html');
    ajaxGet('/manage/menu/listTree', {systemId: systemId}, function (response) {
        if (response && response.length > 0) {
            var menuTmpl = $('#menuTmpl').html();
            var menuHtml = doT.template(menuTmpl)(response);
            $('ul.layui-nav-tree').html(menuHtml);
        }
    });
}

function changeMunu(obj) {
    $(obj).addClass(menuChoosedClass);
    $(obj).find('dd').addClass(menuChild);
    $(obj).siblings().removeClass(menuChoosedClass)
    $(obj).siblings().find('dd').removeClass(menuChild)
}

function loadContent(linkPath) {
    $('#manageContent').attr('src', linkPath);
}

function loginOut() {
    ajaxGet('/loginOut', {}, function (response) {
        if (response == 1) {
            window.location.href = '/login';
        } else {
            alert('退出失败！请刷新页面！');
        }
    });
}