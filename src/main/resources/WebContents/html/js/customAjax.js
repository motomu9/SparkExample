/*
inputにファイルが選択された時にアップロードする。
アップロードをクリックした時に動作させるためにはchangeをclickにする。
$(document).onはあとから追加した要素にも対応させるため。
*/
$(function () {
    $(document).on('click', '#bt_Master_File1', function () {
        var fd = new FormData();
        if ($("input[name='Master_File1']").val() !== '') {
            fd.append("Master_File1", $("input[name='Master_File1']").prop("files")[0]);
        }
        var postData = {
            type: "POST",
            dataType: "text",
            data: fd,
            processData: false,
            contentType: false
        };
        $.ajax(
            "/fileUpload", postData
        ).done(function (result) {
            $('#result_Master_File1').text(result);
        });
    });
});

$(function () {
    $(document).on('click', '#bt_Master_File2', function () {
        var fd = new FormData();
        if ($("input[name='Master_File2']").val() !== '') {
            fd.append("Master_File2", $("input[name='Master_File2']").prop("files")[0]);
        }
        var postData = {
            type: "POST",
            dataType: "text",
            data: fd,
            processData: false,
            contentType: false
        };
        $.ajax(
            "/fileUpload", postData
        ).done(function (result) {
            $('#result_Master_File2').text(result);
        });
    });
});

///*
//inputにファイルが選択された時にアップロードする。
//アップロードをクリックした時に動作させるためにはchangeをclickにする。
//$(document).onはあとから追加した要素にも対応させるため。
//*/
//$(function () {
//    $(document).on('click', '#bt_Master_File1', function () {
//        var fd = new FormData();
//        if ($("input[name='Master_File1']").val() !== '') {
//            fd.append("Master_File1", $("input[name='Master_File1']").prop("files")[0]);
//        }
//        var postData = {
//            type: "POST",
//            dataType: "text",
//            data: fd,
//            processData: false,
//            contentType: false
//        };
//        $.ajax(
//            "/fileUpload", postData
//        ).done(function (result) {
//            $('#result_Master_File1').text(result);
//        });
//    });
//});


///*
//inputにファイルが選択された時にアップロードする。
//アップロードをクリックした時に動作させるためにはchangeをclickにする。
//$(document).onはあとから追加した要素にも対応させるため。
//*/
//$(function () {
//    $(document).on('click', '#bt_Master_File1', uploadFile('Master_File1'));
//    $(document).on('click', '#bt_Master_File2', uploadFile('Master_File2'));
//    $(document).on('click', '#bt_Master_File3', uploadFile('Master_File3'));
//    $(document).on('click', '#bt_Tran_File1', uploadFile('Tran_File1'));
//    $(document).on('click', '#bt_Tran_File2', uploadFile('Tran_File2'));
//
//    function uploadFile(fileName) {
//
//        var fd = new FormData();
//        if ($("input[name='" + fileName + "']").val() !== '') {
//            fd.append(fileName, $("input[name='" + fileName + "']").prop("files")[0]);
//        }
//        var postData = {
//            type: "POST",
//            dataType: "text",
//            data: fd,
//            processData: false,
//            contentType: false
//        };
//        $.ajax(
//            "/fileUpload", postData
//        ).done(function (result) {
//            $('#result_' + fileName).text(result);
//        });
//    }
//});
