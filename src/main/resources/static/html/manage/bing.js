$(function () {
    window.g_idx = 0;
    query()
});

function query() {
    ajaxGet('/file/image/getBingImgs', {idx: g_idx}, function (response) {
        if (response && response.length > 0) {
            var jsonObj = $.parseJSON(response);
            var imgTmpl = $('#imgTmpl').html();
            $('#img').html(doT.template(imgTmpl)(jsonObj.images));
        }
    });
}

function loadNext() {
    g_idx = g_idx + 6;
    var pageIndex = $('#pageIndex').text();
    pageIndex++;
    $('#pageIndex').text(pageIndex);
    query();
}

function loadBefore() {
    g_idx = g_idx - 6;
    var pageIndex = $('#pageIndex').text();
    pageIndex--;
    if (g_idx >= 0 && pageIndex >= 0) {
        query();
    } else {
        g_idx = 0;
        pageIndex = 0;
    }
    $('#pageIndex').text(pageIndex);
}

function downloadImg(url) {
    alertConfirm("是否保存图片？", function () {
        ajaxGet('/file/image/saveBingImgs', {url: url}, function () {
            alertSuccess("保存成功！");
        });
    });
}