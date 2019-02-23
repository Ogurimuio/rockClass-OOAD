$(document).ready(function() {
    var token = getCookie("token");

    var courseId = getUrlParam("courseId");
    var courseName = decodeURI(getUrlParam("courseName"));
    document.getElementById("main-header").innerText = courseName + "——查看成绩";

    var roundIDs = [];
    var roundOrders = [];
    var teamId;
    var cUrl = "/course/" + courseId + "/myTeam";
    $.ajax({
        type: 'get',
        url: cUrl,
        headers: {'Authorization': 'Bearer ' + token},
        data: {},
        dataType: 'json',
        async: false,
        success: function (msg) {
            // console.log(msg);
            teamId = msg.teamId;
        },
        error: function(XMLHttpRequest) {
            if (XMLHttpRequest.status == 404) {
                alert("当前课程暂未分组！");
            } else {
                alert("AJAX error: " + XMLHttpRequest.status);
            }
        }
    });

    var aUrl = "/course/" + courseId + "/round";
    $.ajax({
        type: 'get',
        url: aUrl,
        headers: {'Authorization': 'Bearer ' + token},
        data: {},
        dataType: 'json',
        async: false,
        success: function(roundList) {
            // console.log(roundList);
            var length = roundList.length;
            if (length > 0) {
                document.getElementById("tip").style.display = "none";
                for (var i = 0; i < length; i++) {
                    roundIDs[i] = roundList[i].id;
                    roundOrders[i] = roundList[i].order;
                }
                for (var j = 0; j < length; j++) {
                    var scoreList = document.getElementById("score-list");
                    var newRow1 = scoreList.insertRow(scoreList.rows.length);
                    var cell1;

                    var bUrl = "/round/" + roundIDs[j] + "/team/" + teamId + "/roundscore";
                    $.ajax({
                        type: 'get',
                        url: bUrl,
                        headers: {'Authorization': 'Bearer ' + token},
                        data: {},
                        dataType: 'json',
                        async: false,
                        success: function(roundScore) {
                            // console.log(roundScore);

                            cell1 = newRow1.insertCell(0);
                            cell1.innerHTML = roundOrders[j];
                            // cell1 = newRow1.insertCell(1);
                            // cell1.innerHTML = "";
                            cell1 = newRow1.insertCell(1);
                            cell1.innerHTML = roundScore.preScore;
                            cell1 = newRow1.insertCell(2);
                            cell1.innerHTML = roundScore.questionScore;
                            cell1 = newRow1.insertCell(3);
                            cell1.innerHTML = roundScore.reportScore;
                            cell1 = newRow1.insertCell(4);
                            cell1.innerHTML = roundScore.totalScore;
                        },
                        error: function(XMLHttpRequest) {
                            if (XMLHttpRequest.status == 404) {
                                cell1 = newRow1.insertCell(0);
                                cell1.innerHTML = roundOrders[j];
                                cell1 = newRow1.insertCell(1);
                                cell1.innerHTML = "";
                                cell1 = newRow1.insertCell(2);
                                cell1.innerHTML = "";
                                cell1 = newRow1.insertCell(3);
                                cell1.innerHTML = "";
                                cell1 = newRow1.insertCell(4);
                                cell1.innerHTML = "";
                                cell1 = newRow1.insertCell(5);
                                cell1.innerHTML = "未评分";
                            } else {
                                alert("AJAX error: " + XMLHttpRequest.status);
                            }
                        }
                    });
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

    $("#exportScoreBtn").click(function() {
        axios.get(`/course/${courseId}/score`, {
            headers: {'Authorization': 'Bearer ' + token},
            responseType: 'blob'
        }).then((res) => {
            // console.log(res);

            var b = new Blob([res.data]);
            var fileName = courseName+"总成绩表.xls";

            if (window.navigator.msSaveBlob) {
                try {
                    window.navigator.msSaveBlob(b, fileName);
                } catch (e) {
                    console.log(e);
                }
            } else {
                var aLink = document.createElement('a');
                aLink.style.display = "none";
                aLink.download = fileName;
                aLink.href = URL.createObjectURL(b);

                var event;
                if (window.MouseEvent) {
                    event = new MouseEvent('click');
                } else {
                    event = document.createEvent('MouseEvents');
                    event.initMouseEvent('click', true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);
                }
                aLink.dispatchEvent(event);
            }
            alert("导出成功！");
        });
    });

    $("#logOutBtn").click(function() {
        window.location.href='../page/login_pc.html';
    });
    $("#backBtn").click(function() {
        window.location.href = "home.html?role=student";
    });
    $("#seminarBtn").click(function() {
        window.location.href =
            "studentSeminarList.html?courseId=" + courseId +
            "&courseName=" + courseName +
            "&role=student";
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
