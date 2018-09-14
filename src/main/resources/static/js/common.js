function ajaxPost(url, data, callBack) {// POST请求
    ajaxCommon('POST', true, url, data, callBack);
}

function ajaxGet(url, data, callBack) {// GET请求
    ajaxCommon('GET', true, url, data, callBack);
}

function ajaxSync(url, data, callBack) {// 同步请求
    ajaxCommon('GET', false, url, data, callBack);
}

function ajaxCommon(type, isAsync, url, data, callBack) {// AJAX构造方法
    // 当需要调用其他项目的接口时，定制开发
    url = 'http://' + window.location.host + url;
    $.ajax({
        type: type,
        url: url,
        async: isAsync,
        data: data,
        success: function (response) {
            try {
                if (response == 401) {
                    alert(response.message);
                    setTimeout(function () {
                        top.location.href = response.data.loginUrl;
                    }, 2000);
                } else if (response == 500) {
                    alert(response.message);
                } else {
                    callBack(response);
                }
            } catch (e) {
                alert(e);
            }
        },
        error: function (e) {
            alert(e);
        }
    });
}

/**
 * create by fuhuichao 通用的打开layer弹出框
 */
function openSelfDialog(id, title, url, width, height, endCallBack, successCallBack) {
    // 获取该条记录的主键
    var winW = $(top.window).width();
    var winH = $(top.window).height() - getTopHeaderHeight();
    width = (width > winW || !width) ? winW : width;
    height = (height > winH || !height) ? winH : height;

    // 打开layer弹窗
    top.layer.open({
        id: id,
        type: 2,
        shade: 0.3,
        // maxmin:true,
        // closeBtn: 2,
        title: title,
        area: [width + 'px', height + 'px'],
        content: url,
        end: function () {
            if (endCallBack) endCallBack();
        },
        moveEnd: function (layero) {
        },
        success: function (layero, index) {
            if (successCallBack) successCallBack(layero, index);
        }
    });
}