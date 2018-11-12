$(function () {
    Simditor.locale = 'zh-CN';//设置中文
    window.editor = new Simditor({
        textarea: $('#editor'),  //textarea的id
        placeholder: '这里输入文字...',
        toolbar: ['title', 'bold', 'italic', 'underline', 'strikethrough', 'fontScale', 'color', '|', 'ol', 'ul', 'blockquote', 'code', 'table', '|', 'link', 'image', 'hr', '|', 'indent', 'outdent', 'alignment'], //工具条都包含哪些内容
        pasteImage: true,//允许粘贴图片
        // defaultImage: '/res/simditor/images/image.png',//编辑器插入的默认图片，此处可以删除
        upload: {
            url: '/file/upload', //文件上传的接口地址
            params: null, //键值对,指定文件上传接口的额外参数,上传的时候随文件一起提交
            fileKey: 'articleImg', //服务器端获取文件数据的参数名
            connectionCount: 3,
            leaveConfirm: '正在上传文件'
        }
    });
});

function queryList() {
    var param = {
        title: $('#queryTitle').val()
    };
    ajaxGet('/manage/Article/listData', param, function (response) {
        var tableTmpl = $('#tableTmpl').html();
        $('tbody').html(doT.template(tableTmpl)(response));
    });
}

function save() {
    var param = {
        articleId: $('#editArticleId').val() || '',
        title: $('#editTitle').val(),
        content: $('textarea').val()
    };
    ajaxGet('/manage/Article/save', param, function () {
        goList();
    });
}

function editArticle(obj) {
    $('#list').addClass('layui-hide');
    $('#edit').removeClass('layui-hide');
    if (obj) {
        var param = {
            articleId: $(obj).closest('tr').data('articleid')
        };
        ajaxGet('/manage/Article/getOne', param, function (response) {
            $('#editArticleId').val(response.articleId);
            $('#editTitle').val(response.title);
            editor.setValue(response.content);
        });
    }
}

function deleteArticle() {

}

function viewArticle(obj) {
    $('#list').addClass('layui-hide');
    $('#edit').removeClass('layui-hide');
    if (obj) {
        var param = {
            articleId: $(obj).closest('tr').data('articleid')
        };
        ajaxGet('/manage/Article/getOne', param, function (response) {
            $('#editArticleId').val(response.articleId);
            $('#editTitle').val(response.title);
            $('#viewContent').val(response.content);
        });
    }
}

function goList() {
    $('#list').removeClass('layui-hide');
    $('#edit').addClass('layui-hide');
    queryList();
}