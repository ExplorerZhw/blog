$(function () {
    bindSelectFid();
});

function bindSelectFid(selectId) {
    ajaxGet('/manage/menu/listData', {}, function (response) {
        var fIdTmpl = $('#fIdTmpl').html();
        $('select[name=fId]').html(doT.template(fIdTmpl)(response));
        if (selectId) {
            $('select[name=fId]').val(selectId);
        }
    });
}

function query() {
    var param = {
        menuName: $('input[name=menuName]').val()
    };
    debugger
    ajaxGet('/manage/menu/listData', param, function (response) {
        debugger
        var tableTmpl = $('#tableTmpl').html();
        $('tbody').html(doT.template(tableTmpl)(response));
    });
}

function editNemu(obj) {
    var menuId = '';
    if (obj) {
        menuId = $(obj).closest('tr').data('menuid');
    }
    openSelfDialog('edit', '编辑标签', '/static/html/menu/input_menu.html?menuId=' + menuId, 600, 400);
}

function deleteMenu() {

}