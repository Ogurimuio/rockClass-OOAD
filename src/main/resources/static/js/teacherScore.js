$(document).ready(function() {
    var token = getCookie("token");

    var courseId = getUrlParam("courseId");
    var courseName = decodeURI(getUrlParam("courseName"));
    document.getElementById("main-header").innerText = courseName + "——查看成绩";

    var searchRows = [];

    var aUrl = "/course/" + courseId + "/allscore";
    $.ajax({
        type: 'get',
        url: aUrl,
        headers: {'Authorization': 'Bearer ' + token},
        data: {},
        dataType: 'json',
        async: false,
        success: function(scoreInfo) {

            // 输出收到的JSON数据以便测试
            console.log(scoreInfo);

            var roundCnt = scoreInfo.length;
            if (roundCnt > 0) {
                document.getElementById("tip").style.display = "none";

                for (var i = 0; i < roundCnt; i++) {
                    $("#roundSelector").append("<option value=\"" + i + "\">第" + (i+1) + "轮</option>");
                }

                var scoreList = document.getElementById("score-list");
                var length1 = scoreInfo[0].allTeamRoundScore.length;
                if (length1 > 0) {
                    for (var j = 0; j < length1; j++) {
                        var length2 = scoreInfo[0].allTeamRoundScore[j].seminarScores.length;
                        for (var k = 0; k < length2; k++) {
                            var newRow1 = scoreList.insertRow(scoreList.rows.length);
                            newRow1.insertCell(0).innerHTML = scoreInfo[0].allTeamRoundScore[j].teamName;
                            newRow1.insertCell(1).innerHTML = scoreInfo[0].allTeamRoundScore[j].seminarScores[k].seminarName;
                            newRow1.insertCell(2).innerHTML = scoreInfo[0].allTeamRoundScore[j].seminarScores[k].preScore;
                            newRow1.insertCell(3).innerHTML = scoreInfo[0].allTeamRoundScore[j].seminarScores[k].questionScore;
                            newRow1.insertCell(4).innerHTML = scoreInfo[0].allTeamRoundScore[j].seminarScores[k].reportScore;
                            newRow1.insertCell(5).innerHTML = scoreInfo[0].allTeamRoundScore[j].seminarScores[k].totalScore;
                            newRow1.insertCell(6).innerHTML = scoreInfo[0].allTeamRoundScore[j].roundScore.totalScore;
                            searchRows[searchRows.length] = newRow1;
                        }
                    }
                } else {
                    document.getElementById("tip").style.display = "";
                }
            } else {
                document.getElementById("tip").style.display = "";
            }

            $("#roundSelector").change(function() {
                var checkValue = $("#roundSelector").val();

                var searchRowsLength = searchRows.length;
                for (var m = 0; m < searchRowsLength; m++) {
                    searchRows[0].parentNode.removeChild(searchRows[0]);
                    searchRows.splice(0, 1);
                }

                var length1 = scoreInfo[checkValue].allTeamRoundScore.length;
                if (length1 > 0) {
                    for (var j = 0; j < length1; j++) {
                        var length2 = scoreInfo[checkValue].allTeamRoundScore[j].seminarScores.length;
                        for (var k = 0; k < length2; k++) {
                            var newRow1 = scoreList.insertRow(scoreList.rows.length);
                            newRow1.insertCell(0).innerHTML = scoreInfo[checkValue].allTeamRoundScore[j].teamName;
                            newRow1.insertCell(1).innerHTML = scoreInfo[checkValue].allTeamRoundScore[j].seminarScores[k].seminarName;
                            newRow1.insertCell(2).innerHTML = scoreInfo[checkValue].allTeamRoundScore[j].seminarScores[k].preScore;
                            newRow1.insertCell(3).innerHTML = scoreInfo[checkValue].allTeamRoundScore[j].seminarScores[k].questionScore;
                            newRow1.insertCell(4).innerHTML = scoreInfo[checkValue].allTeamRoundScore[j].seminarScores[k].reportScore;
                            newRow1.insertCell(5).innerHTML = scoreInfo[checkValue].allTeamRoundScore[j].seminarScores[k].totalScore;
                            newRow1.insertCell(6).innerHTML = scoreInfo[checkValue].allTeamRoundScore[j].roundScore.totalScore;
                            searchRows[searchRows.length] = newRow1;
                        }
                    }
                } else {
                    document.getElementById("tip").style.display = "";
                }
            });
        },
        error: function(XMLHttpRequest) {
            alert("AJAX error: " + XMLHttpRequest.status);
        }
    });

    $("#exportScoreBtn").click(function() {
        // var bUrl = "/course/" + courseId + "/score";
        // var xhr = $.ajax({
        //     type: 'get',
        //     url: bUrl,
        //     headers: {'Authorization': 'Bearer ' + token},
        //     responseType: 'blob',
        //     data: {},
        //     // dataType: 'text/html',
        //     success: function() {
        //         console.log(xhr);
        //         var contentType = xhr.getResponseHeader("Content-Type");
        //         var contentDisposition = xhr.getResponseHeader("Content-disposition");
        //         var fileName = decodeURI(getFileName(contentDisposition, "filename*=utf-8''"));
        //
        //         // 创建隐藏的可下载链接
        //         var eleLink = document.createElement("a");
        //         eleLink.style.display = "none";
        //         eleLink.download = fileName;
        //         // 字符内容转变成blob地址
        //         var blob = new Blob([xhr.responseText]);
        //         eleLink.href = URL.createObjectURL(blob);
        //         // 触发点击
        //         document.body.appendChild(eleLink);
        //         eleLink.click();
        //         // 移除
        //         document.body.removeChild(eleLink);
        //     },
        //     error: function(XMLHttpRequest) {
        //         alert("AJAX error: " + XMLHttpRequest.status);
        //     }
        // });

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

                // document.body.appendChild(aLink);
                // aLink.click();
                // document.body.removeChild(aLink);
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

    // function getFileName(str) {
    //     var fileName = str.split(";")[1].split("filename=")[1];
    //     var fileNameUnicode = str.split("filename*=")[1];
    //     if (fileNameUnicode) {
    //         fileName = decodeURIComponent(fileNameUnicode.split("''")[1]);
    //     }
    //     return fileName;
    // }

    $("#logOutBtn").click(function() {
        window.location.href='../page/login_pc.html';
    });
    $("#backBtn").click(function() {
        window.location.href = "home.html?role=teacher";
    });
    $("#importListBtn").click(function() {
        window.location.href =
            'importStudent.html?courseId=' + courseId +
            '&courseName=' + courseName;
    });
    $("#seminarBtn").click(function() {
        window.location.href =
            "teacherSeminarList.html?courseId=" + courseId +
            "&courseName=" + courseName +
            "&role=teacher";
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
        if (arr = document.cookie.match(reg))
            return unescape(arr[2]);
        else
            return null;
    }
});
