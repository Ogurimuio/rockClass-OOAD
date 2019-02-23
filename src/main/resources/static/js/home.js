$(document).ready(function() {
    var token = getCookie("token");
    var role = decodeURI(getUrlParam("role"));

    $.ajax({
        type: 'get',
        url: '/course',
        headers: {'Authorization': 'Bearer ' + token},
        data: {},
        dataType: 'json',
        // async: false,
        success: function (courseList) {
            courseList = combineObjectInList(courseList, "id", []);

            var length = courseList.length;
            if (length > 0) {
                document.getElementById("tip").style.display = "none";
                var courseListTable = document.getElementById("course-list");

                for (var i = 0; i < length; i++) {
                    var newRow = courseListTable.insertRow(courseListTable.rows.length);
                    var cell1;

                    cell1 = newRow.insertCell(0);
                    cell1.innerHTML = courseList[i].name;

                    cell1 = newRow.insertCell(1);
                    var btn1 = document.createElement("button");
                    btn1.id = courseList[i].id;
                    var btnName1 = document.createAttribute("name");
                    btnName1.value = courseList[i].name;
                    btn1.setAttributeNode(btnName1);
                    var btnAttr1 = document.createAttribute("class");
                    btnAttr1.value = "enterCourseBtn";
                    btn1.setAttributeNode(btnAttr1);
                    btn1.innerText = "进入";
                    cell1.appendChild(btn1);
                }
            } else {
                document.getElementById("tip").style.display = "";
            }
        },
        error: function(XMLHttpRequest) {
            alert("AJAX error: " + XMLHttpRequest.status);
        }
    });

    $("#logOutBtn").click(function() {
        window.location.href='../page/login_pc.html';
    });
    $("#course-list").on('click', '.enterCourseBtn', function() {
        var courseId = this.id;
        var courseName = this.name;
        if (role == "teacher") {
            window.location.href = "teacherSeminarList.html?courseId=" + courseId + "&courseName=" + courseName + "&role=" + role;
        } else {
            window.location.href = "studentSeminarList.html?courseId=" + courseId + "&courseName=" + courseName + "&role=" + role;
        }
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

    function combineObjectInList(arr, item, list) {
        //数组去除重复，item为重复判定项，list为重复记录需要累加的值的数组

        var obj = {};
        var a = [];
        for(var i in arr) {
            if (!obj[arr[i][item]]) {
                obj[arr[i][item]] = copyObj(arr[i]);//数组克隆
            } else if (!!obj[arr[i][item]]) {
                for(var j in list) {
                    obj[arr[i][item]][list[j]] = obj[arr[i][item]][list[j]] + parseFloat(arr[i][list[j]]);
                }
            }
        }
        for (var k in obj) {
            a.push(obj[k]);
        }
        return a;
    }

    function copyObj(obj) {
        //obj arr 对象的克隆（区分于指针赋值）

        if (obj.constructor == Array) {
            var a = [];
            for (var i in obj) {
                a.push(obj[i]);
            }
            return a;
        } else {
            var o = {};
            for (var i in obj) {
                o[i] = obj[i];
            }
            return o;
        }
    }
});
