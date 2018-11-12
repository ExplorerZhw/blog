function doLogin() {
    var param = {
        userName: $('[name=userName]').val(),
        password: $('[name=password]').val()
    };
    ajaxGet('/doLogin', param, function (response) {
        if (response.status == 1) {
            window.location.href = '/' + response.data;
        } else {
            alertError('账号密码错误');
        }
    });
}

function goIndex() {
    window.location.href = '/index';
}