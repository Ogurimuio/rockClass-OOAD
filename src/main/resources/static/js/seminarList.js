$(document).ready(function() {
    var token = getCookie("token");

    var courseId = getUrlParam("courseId");
    var courseName = decodeURI(getUrlParam("courseName"));
    var role = getUrlParam("role");
    document.getElementById("main-header").innerText = courseName + "——讨论课";

    var aUrl = "/course/" + courseId + "/round";
    var roundIDs = [];
    var roundOrders = [];

    $.ajax({
        type: 'get',
        url: aUrl,
        headers: {'Authorization': 'Bearer ' + token},
        data: {},
        dataType: 'json',
        async: false,
        success: function(roundList) {
            var length = roundList.length;
            if (length > 0) {
                document.getElementById("tip").style.display = "none";
                for (var i = 0; i < length; i++) {
                    roundIDs[i] = roundList[i].id;
                    roundOrders[i] = roundList[i].order;
                }

                for (var j = 0; j < length; j++) {
                    var newTable = document.createElement("table");
                    newTable.id = roundOrders[j];
                    var tableAttr1 = document.createAttribute("class");
                    tableAttr1.value = "seminarListTable";
                    newTable.setAttributeNode(tableAttr1);

                    var newRow1 = newTable.insertRow(newTable.rows.length);
                    var cell1;
                    cell1 = newRow1.insertCell(0);
                    cell1.innerHTML = "第" + roundOrders[j] + "轮讨论课";
                    cell1 = newRow1.insertCell(1);
                    cell1.innerHTML = "";

                    var bUrl = "/round/" + roundIDs[j] + "/seminar";
                    var seminarIDs = [];
                    var seminarTopics = [];
                    var seminarOrder = [];

                    $.ajax({
                        type: 'get',
                        url: bUrl,
                        headers: {'Authorization': 'Bearer ' + token},
                        data: {},
                        dataType: 'json',
                        async: false,
                        success: function(seminarList) {
                            var length1 = seminarList.length;
                            for (var k = 0; k < length1; k++) {
                                seminarIDs[k] = seminarList[k].id;
                                seminarTopics[k] = seminarList[k].topic;
                                seminarOrder[k] = seminarList[k].order;

                                var newRow2 = newTable.insertRow(newTable.rows.length);
                                var cell2;
                                cell2 = newRow2.insertCell(0);
                                cell2.innerHTML = seminarTopics[k];
                                cell2 = newRow2.insertCell(1);

                                var btn1 = document.createElement("button");
                                btn1.id = seminarIDs[k];
                                var btnAttr1 = document.createAttribute("class");
                                btnAttr1.value = "enterSeminarBtn";
                                btn1.setAttributeNode(btnAttr1);
                                btn1.innerText = "进入";
                                cell2.appendChild(btn1);
                            }
                        },
                        error: function(XMLHttpRequest) {
                            alert("AJAX error: " + XMLHttpRequest.status);
                        }
                    });
                    document.getElementById("list-table").appendChild(newTable);
                }
            } else {
                document.getElementById("tip").style.display = "";
            }
        },
        error: function(XMLHttpRequest) {
            if (XMLHttpRequest.status == 404) {
                alert("当前暂无讨论课！");
            } else {
                alert("AJAX error: " + XMLHttpRequest.status);
            }
        }
    });

    $("#list-table").on('click', '.enterSeminarBtn', function() {
        var roundOrder = this.parentNode.parentNode.parentNode.parentNode.id;
        var seminarId = this.id;
        if (role == "teacher") {
            window.location.href =
                "teacherSeminar.html?courseId=" + courseId +
                "&courseName=" + courseName +
                "&roundOrder=" + roundOrder +
                "&seminarId=" + seminarId +
                "&role=teacher";
        } else {
            window.location.href =
                "studentSeminar.html?courseId=" + courseId +
                "&courseName=" + courseName +
                "&roundOrder=" + roundOrder +
                "&seminarId=" + seminarId +
                "&role=student";
        }
    });

    $("#logOutBtn").click(function() {
        window.location.href='../page/login_pc.html';
    });
    $("#backBtn").click(function() {
        window.location.href = "home.html?role=" + role;
    });
    $("#importListBtn").click(function() {
        window.location.href =
            'importStudent.html?courseId=' + courseId +
            '&courseName=' + courseName;
    });
    $("#exportListBtn").click(function() {
        if (role == "teacher") {
            window.location.href =
                "teacherScore.html?courseId=" + courseId +
                "&courseName=" + courseName;
        } else {
            window.location.href =
                "studentScore.html?courseId=" + courseId +
                "&courseName=" + courseName;
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
});
