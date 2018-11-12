function ajaxPost(url, data, callBack) {// POST请求
    ajaxCommon('POST', true, url, data, callBack, false);
}

function ajaxGet(url, data, callBack) {// GET请求
    ajaxCommon('GET', true, url, data, callBack, false);
}

function ajaxSync(url, data, callBack) {// 同步请求
    ajaxCommon('GET', false, url, data, callBack, false);
}

function ajaxCommon(type, isAsync, url, data, callBack, contentType) {// AJAX构造方法
    // 当需要调用其他项目的接口时，定制开发
    url = 'http://' + window.location.host + url;
    // 给每个请求添加随机数，防止缓存
    url += ((url.indexOf('?') > -1) ? '&' : '?') + 'rn=' + new Date().getTime();
    // 根据提交参数类型不同，设置不同的ajax提交方式
    var processDataVal, contentTypeVal;
    if (contentType == 'upload') {
        processDataVal = false;
        contentTypeVal = false;
    } else {
        processDataVal = true;
        contentTypeVal = contentType ? 'application/json' : 'application/x-www-form-urlencoded; charset=UTF-8'
    }
    $.ajax({
        type: type,
        url: url,
        async: isAsync,
        processData: processDataVal,// 默认值为true
        contentType: contentTypeVal,
        data: data || {},
        success: function (response) {
            try {
                if (callBack) callBack(response);
            } catch (e) {
                alert(e);
            }
        },
        error: function (e) {
            //alertServerError(); //不弹出此异常
        }
    });
}

/**
 * 通用的打开layer弹出框
 */
function openSelfDialog(id, title, url, width, height, endCallBack, successCallBack) {
    // 获取该条记录的主键
    // var winW = $(top.window).width();
    // var winH = $(top.window).height() - getTopHeaderHeight();
    // width = (width > winW || !width) ? winW : width;
    // height = (height > winH || !height) ? winH : height;
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

function closeSelfDialog(index) {
    index = index == undefined ? top.layer.getFrameIndex(window.name) : index;
    top.layer.close(index);
}

/**
 * 将json转为url的字符串
 */
function convertJsonToString(json) {
    if (!json) return '';
    var str = '';
    for (var key in json) {
        str += key + '=' + json[key] + '&'
    }
    return str.slice(0, -1);
}

/**
 * 获取url里面的param参数值
 *
 * @param url 地址
 * @param param 参数key
 * @param defaultV 默认值
 */
function searchByParam(param, defaultV) {
    return searchUrlByParam(decodeURI(document.URL), param, defaultV);
}

function searchUrlByParam(url, param, defaultV) {
    defaultV = defaultV == undefined ? '' : defaultV;
    var paramValue = '';
    var urlArray = url.split('?');
    if (urlArray.length == 1) {
        return defaultV;
    } else {
        var paramArray = urlArray[1].split('&');
        for (var i = 0; i < paramArray.length; i++) {
            if (paramArray[i].split('=')[0] == param) {
                paramValue = paramArray[i].split('=')[1];
                return paramValue;
            }
        }
    }
    return defaultV;
}

$.fn.serializeObject = function () {
    var o = {};
    var a = this.serializeArray();
    $.each(a, function () {
        if (o[this.name]) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};

function alertSuccess(message) {
    layer.alert(message, {
        icon: 1,
        skin: 'layer-ext-moon' //该皮肤由layer.seaning.com友情扩展。关于皮肤的扩展规则，去这里查阅
    })
}

function alertError(message) {
    layer.alert(message, {
        icon: 2,
        skin: 'layer-ext-moon' //该皮肤由layer.seaning.com友情扩展。关于皮肤的扩展规则，去这里查阅
    })
}

/**
 * 弹出确认框
 */
function alertConfirm(msg, okCall) {
    layer.open({
        content: msg
        , btn: ['确定', '取消'],
        style: 'width:80%',
        yes: function () {
            if (okCall) {
                okCall();
            }
        },
        cancel: function () { //按右上角“X”按钮
            return false;
        },
    })
}