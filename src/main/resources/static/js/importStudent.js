$(document).ready(function() {
    var token = getCookie("token");

    var courseId = getUrlParam("courseId");
    var courseName = decodeURI(getUrlParam("courseName"));
    document.getElementById("main-header").innerText = courseName + "——导入学生名单";

    // var totalSize = 0;
    // var fileName;
    // var fileSize;
    // var fileType;
    // var bUrl;

    var aUrl = "/course/" + courseId + "/class";
    $.ajax({
        type: 'get',
        url: aUrl,
        headers: {'Authorization': 'Bearer ' + token},
        data: {},
        dataType: 'json',
        success: function (classList) {
            var length = classList.length;
            if (length > 0) {
                document.getElementById("tip").style.display = "none";
                var classListTable = document.getElementById("class-list");

                for (var i = 0; i < length; i++) {
                    var newRow = classListTable.insertRow(classListTable.rows.length);
                    var cell1;

                    var form1 = document.createElement("form");
                    // form1.action = "/class/"+classList[i].id;
                    form1.className = classList[i].id;
                    form1.enctype = "multipart/form-data";

                    var btn1 = document.createElement("input");
                    btn1.id = classList[i].id;
                    btn1.type = "file";
                    btn1.className = "chooseFile";
                    // btn1.innerText = "选择文件";

                    form1.appendChild(btn1);

                    var btn2 = document.createElement("button");
                    btn2.className = "submitFile";
                    btn2.name = classList[i].id;
                    btn2.innerText = "提交";
                    // btn2.type = "submit";
                    // form1.appendChild(btn2);

                    var btn3 = document.createElement("button");
                    btn3.className = "resetFile";
                    btn3.innerText = "重置";

                    cell1 = newRow.insertCell(0);
                    cell1.innerHTML = classList[i].name;
                    cell1 = newRow.insertCell(1);
                    cell1.appendChild(form1);
                    cell1 = newRow.insertCell(2);
                    cell1.appendChild(btn2);
                    cell1.appendChild(btn3);
                }
            } else {
                document.getElementById("tip").style.display = "";
            }
        },
        error: function(XMLHttpRequest) {
            alert("AJAX error: " + XMLHttpRequest.status);
        }
    });

    // //绑定所有type=file的元素的onchange事件的处理函数
    // $(":file").change(function() {
    //     //假设file标签没打开multiple属性，那么只取第一个文件就行了
    //     var file = this.files[0];
    //     fileName = file.name;
    //     fileSize = file.size;
    //     fileType = file.type;
    //     bUrl = window.URL.createObjectURL(file);//获取本地文件的url，如果是图片文件，可用于预览图片
    //     totalSize += fileSize;
    // });

    $("#class-list").on('click', '.submitFile', function() {
        var classId = this.name;
        var files = document.getElementById(classId).files;
        // console.log(files);
        let formData = new FormData();
        formData.append("file", files[0]);
        // console.log(formData.get("file"));

        var cUrl = "/class/" + classId;
        $.ajax({
            type: 'put',
            url: cUrl,
            headers: {'Authorization': 'Bearer ' + token},
            data: formData,

            // xhr: function() {
            //     //获取ajaxSettings中的xhr对象，为它的upload属性绑定progress事件的处理函数
            //     var myXhr = $.ajaxSettings.xhr();
            //     if(myXhr.upload){
            //         //检查upload属性是否存在
            //         //绑定progress事件的回调函数
            //         myXhr.upload.addEventListener('progress', progressHandlingFunction, false);
            //     }
            //     return myXhr;//xhr对象返回给jQuery使用
            // },

            //必须false才会自动加上正确的Content-Type
            contentType: false,
            //必须false才会避开jQuery对formdata的默认处理
            processData: false,

            success: function() {
                alert("导入成功！");
            },
            error: function(XMLHttpRequest) {
                alert("AJAX error: " + XMLHttpRequest.status);
            }
        });
    });

    // $("#class-list").on('click', '.submitFile', function() {
    //     var classId = this.id;
    //     $("."+classId).submit();
    // });

    // function progressHandlingFunction(e) {
    //     if (e.lengthComputable) {
    //         $('#progress').attr({value : e.loaded, max : e.total}); //更新数据到进度条
    //         var percent = e.loaded/e.total*100;
    //         $('#progress').html(e.loaded + "/" + e.total+" bytes. " + percent.toFixed(2) + "%");
    //         $('#progress').css('width', percent.toFixed(2) + "%");
    //     }
    // }

    $("#logOutBtn").click(function() {
        window.location.href='../page/login_pc.html';
    });
    $("#backBtn").click(function() {
        window.location.href = "home.html?role=teacher";
    });
    $("#seminarBtn").click(function() {
        window.location.href =
            "teacherSeminarList.html?courseId=" + courseId +
            "&courseName=" + courseName +
            "&role=teacher";
    });
    $("#exportListBtn").click(function() {
        window.location.href =
            "teacherScore.html?courseId=" + courseId +
            "&courseName=" + courseName;
    });

    function getUrlParam(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        var r = encodeURI(window.location.search).substr(1).match(reg);
        if (r != null) {
            return unescape(r[2]);
        } else {
            return null;
        }
    }

    function getCookie(name) {
        var arr,reg = new RegExp("(^| )"+name+"=([^;]*)(;|$)");
        if(arr = document.cookie.match(reg))
            return unescape(arr[2]);
        else
            return null;
    }
});
