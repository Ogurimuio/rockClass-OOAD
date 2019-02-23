$(document).ready(function() {
    var token = getCookie("token");

    var courseId = getUrlParam("courseId");
    var courseName = decodeURI(getUrlParam("courseName"));
    var roundOrder = getUrlParam("roundOrder");
    var seminarId = getUrlParam("seminarId");
    var role = getUrlParam("role");
    document.getElementById("main-header").innerText = courseName + "——第" + roundOrder + "轮讨论课";

    var aUrl = "/seminar/" + seminarId;
    $.ajax({
        type: 'get',
        url: aUrl,
        headers: {'Authorization': 'Bearer ' + token},
        data: {},
        dataType: 'json',
        async: false,
        success: function(seminarInfo) {
            // console.log(seminarInfo);
            document.getElementById("seminarTopic").innerText = "主题：" + seminarInfo.seminarName;
            document.getElementById("seminarIntro").innerText = "内容：" + seminarInfo.introduction;
            document.getElementById("seminarSignUp").innerText = "报名起止时间：" + seminarInfo.enrollStartTime + "--" + seminarInfo.enrollEndTime;
            var maxTeamCnt = seminarInfo.maxTeam;

            var bUrl = "/course/" + courseId + "/class";
            $.ajax({
                type: 'get',
                url: bUrl,
                headers: {'Authorization': 'Bearer ' + token},
                data: {},
                dataType: 'json',
                async: false,
                success: function(classList) {
                    if (role == "teacher") {
                        for (var i = 0; i < classList.length; i++) {
                            var cUrl = "/seminar/" + seminarId + "/class/" + classList[i].id + "/attendance";
                            $.ajax({
                                type: 'get',
                                url: cUrl,
                                headers: {'Authorization': 'Bearer ' + token},
                                data: {},
                                dataType: 'json',
                                async: false,
                                success: function(msg) {
                                    // console.log(msg);
                                    var seminarInfoList = document.getElementById("status-list");
                                    var newRow1 = seminarInfoList.insertRow(seminarInfoList.rows.length);
                                    newRow1.insertCell(0).innerHTML = classList[i].name;
                                    newRow1.insertCell(1).innerHTML = "";
                                    newRow1.insertCell(2).innerHTML = "";
                                    newRow1.insertCell(3).innerHTML = "";
                                    for (var j = 0; j < maxTeamCnt; j++) {
                                        newRow1 = seminarInfoList.insertRow(seminarInfoList.rows.length);
                                        newRow1.id = classList[i].id + "-" + (j+1);
                                        newRow1.insertCell(0).innerHTML = j+1;
                                        newRow1.insertCell(1);
                                        newRow1.insertCell(2);
                                        newRow1.insertCell(3);
                                    }
                                    for (var k = 0; k < maxTeamCnt; k++) {
                                        if (msg["teamAttendance"+k] != null) {
                                            var teamInfo = msg["teamAttendance"+k];
                                            var order = teamInfo.teamOrder;
                                            var aRow = document.getElementById(classList[i].id + "-" + order);
                                            if (teamInfo != null) {
                                                aRow.cells[1].innerHTML = teamInfo.serial;
                                                if (teamInfo.leader == undefined) {
                                                    aRow.cells[2].innerHTML = "";
                                                } else {
                                                    aRow.cells[2].innerHTML = teamInfo.leader;
                                                }

                                                if (msg.seminarStatus == 0) {
                                                    if (teamInfo.pptUrl == null) {
                                                        aRow.cells[3].innerHTML = "未上传";
                                                    } else {
                                                        aRow.cells[3].innerHTML = "已上传";
                                                    }
                                                } else if (msg.seminarStatus == 1) {
                                                    if (teamInfo.pptUrl == null) {
                                                        aRow.cells[3].innerHTML = "未上传";
                                                    } else {
                                                        var downloadPptBtn = document.createElement("button");
                                                        downloadPptBtn.className = "downloadPptBtn";
                                                        downloadPptBtn.name = teamInfo.attendanceId;
                                                        downloadPptBtn.id = teamInfo.pptName;
                                                        downloadPptBtn.innerText = "下载";
                                                        aRow.cells[3].appendChild(downloadPptBtn);
                                                    }
                                                } else {
                                                    document.getElementById("changeArea").innerText = "展示报告";
                                                    if (teamInfo.reportUrl == null) {
                                                        aRow.cells[3].innerHTML = "未上传";
                                                    } else {
                                                        var downloadReportBtn = document.createElement("button");
                                                        downloadReportBtn.className = "downloadPptBtn";
                                                        downloadReportBtn.name = teamInfo.attendanceId;
                                                        downloadReportBtn.id = teamInfo.reportName
                                                        downloadReportBtn.innerText = "下载";
                                                        aRow.cells[3].appendChild(downloadReportBtn);
                                                    }
                                                }
                                            } else {
                                                aRow.cells[1].innerHTML = "未报名";
                                            }
                                        }
                                    }
                                },
                                error: function(XMLHttpRequest) {
                                    alert("AJAX error: " + XMLHttpRequest.status);
                                }
                            });
                        }
                    } else {
                        var dUrl = "/seminar/" + seminarId + "/class/" + classList[0].id + "/attendance";
                        $.ajax({
                            type: 'get',
                            url: dUrl,
                            headers: {'Authorization': 'Bearer ' + token},
                            data: {},
                            dataType: 'json',
                            async: false,
                            success: function(msg) {
                                console.log(msg);

                                // 获取报名情况表
                                var seminarInfoList = document.getElementById("status-list");

                                // 插入最大组数的行数
                                var newRow1;
                                for (var j = 0; j < maxTeamCnt; j++) {
                                    newRow1 = seminarInfoList.insertRow(seminarInfoList.rows.length);
                                    // 定义插入行的id为"班级-次序"便于获取
                                    newRow1.id = classList[0].id + "-" + (j+1);
                                    newRow1.insertCell(0).innerHTML = j+1;
                                    newRow1.insertCell(1);
                                    newRow1.insertCell(2);
                                    newRow1.insertCell(3);
                                }

                                // 用来存储所有次序的报名情况，0为该次序未报名，1为该次序已报名
                                var hasAttendance = [];
                                for (var m = 0; m < maxTeamCnt; m++) {
                                    hasAttendance[m] = 0;
                                }
                                for (var n = 0; n < maxTeamCnt; n++) {
                                    if (msg["teamAttendance"+n] != null) {
                                        var index = msg["teamAttendance"+n].teamOrder;
                                        hasAttendance[index-1] = 1;
                                    }
                                }

                                // 动态填充表格
                                // 填充未报名的次序
                                for (var k = 0; k < maxTeamCnt; k++) {
                                    // 若所在小组未报名，且讨论课未开始（即状态为0），则显示报名按钮，否则不显示
                                    if (hasAttendance[k] == 0) {
                                        if (msg.seminarStatus == 0 && msg.teamStatus == 0) {
                                            var aRow = document.getElementById(classList[0].id + "-" + (k+1));
                                            var enrollBtn = document.createElement("button");
                                            enrollBtn.className = "enrollBtn";
                                            enrollBtn.id = classList[0].id + "-" + msg.myTeamId + "-" + (k+1);
                                            enrollBtn.innerHTML = "报名";
                                            aRow.cells[3].appendChild(enrollBtn);
                                        }
                                    }
                                }
                                // 填充已报名的次序
                                for (var k = 0; k < maxTeamCnt; k++) {
                                    // 判断收到的JSON数据是否为空，若不为空，则为一条报名记录
                                    if (msg["teamAttendance"+k] != null) {
                                        var teamInfo = msg["teamAttendance"+k];
                                        var order = teamInfo.teamOrder;
                                        var aRow = document.getElementById(classList[0].id + "-" + order);

                                        aRow.cells[1].innerHTML = teamInfo.serial;
                                        if (teamInfo.leader == undefined) {
                                            aRow.cells[2].innerHTML = "";
                                        } else {
                                            aRow.cells[2].innerHTML = teamInfo.leader;
                                        }

                                        if (msg.seminarStatus == 0) {
                                            if (msg.myTeamId != teamInfo.teamId) {
                                                if (teamInfo.pptUrl == null) {
                                                    aRow.cells[3].innerHTML = "未上传";
                                                } else {
                                                    aRow.cells[3].innerHTML = "已上传";
                                                }
                                            } else {
                                                var chooseFileBtn = document.createElement("input");
                                                chooseFileBtn.id = teamInfo.attendanceId;
                                                chooseFileBtn.type = "file";
                                                chooseFileBtn.className = "chooseFile";
                                                aRow.cells[3].appendChild(chooseFileBtn);

                                                var uploadPptBtn = document.createElement("button");
                                                uploadPptBtn.className = "uploadPptBtn";
                                                uploadPptBtn.name = teamInfo.attendanceId;
                                                uploadPptBtn.innerText = "上传";
                                                aRow.cells[3].appendChild(uploadPptBtn);

                                                var cancelSeminarBtn = document.createElement("button");
                                                cancelSeminarBtn.className = "cancelSeminarBtn";
                                                cancelSeminarBtn.name = teamInfo.attendanceId;
                                                cancelSeminarBtn.innerText = "取消报名";
                                                aRow.cells[3].appendChild(cancelSeminarBtn);
                                            }
                                        } else if (msg.seminarStatus == 1) {
                                            if (msg.myTeamId != teamInfo.teamId) {
                                                if (teamInfo.pptUrl == null) {
                                                    aRow.cells[3].innerHTML = "未上传";
                                                } else {
                                                    var downloadPptBtn = document.createElement("button");
                                                    downloadPptBtn.className = "downloadPptBtn";
                                                    downloadPptBtn.name = teamInfo.attendanceId;
                                                    downloadPptBtn.id = teamInfo.pptName
                                                    downloadPptBtn.innerText = "下载";
                                                    aRow.cells[3].appendChild(downloadPptBtn);
                                                }
                                            } else {
                                                if (teamInfo.pptUrl == null) {
                                                    var chooseFileBtn = document.createElement("input");
                                                    chooseFileBtn.id = teamInfo.attendanceId;
                                                    chooseFileBtn.type = "file";
                                                    chooseFileBtn.className = "chooseFile";
                                                    aRow.cells[3].appendChild(chooseFileBtn);

                                                    var uploadPptBtn = document.createElement("button");
                                                    uploadPptBtn.className = "uploadPptBtn";
                                                    uploadPptBtn.name = teamInfo.attendanceId;
                                                    uploadPptBtn.innerText = "上传";
                                                    aRow.cells[3].appendChild(uploadPptBtn);
                                                } else {
                                                    var downloadPptBtn = document.createElement("button");
                                                    downloadPptBtn.className = "downloadPptBtn";
                                                    downloadPptBtn.name = teamInfo.attendanceId;
                                                    downloadPptBtn.id = teamInfo.pptName;
                                                    downloadPptBtn.innerText = "下载";
                                                    aRow.cells[3].appendChild(downloadPptBtn);

                                                    var chooseFileBtn = document.createElement("input");
                                                    chooseFileBtn.id = teamInfo.attendanceId;
                                                    chooseFileBtn.type = "file";
                                                    chooseFileBtn.className = "chooseFile";
                                                    aRow.cells[3].appendChild(chooseFileBtn);

                                                    var reuploadPptBtn = document.createElement("button");
                                                    reuploadPptBtn.className = "reuploadPptBtn";
                                                    reuploadPptBtn.name = teamInfo.attendanceId;
                                                    reuploadPptBtn.innerText = "重新上传";
                                                    aRow.cells[3].appendChild(reuploadPptBtn);
                                                }
                                            }
                                        } else {
                                            document.getElementById("changeArea").innerText = "展示报告";
                                            if (msg.myTeamId == teamInfo.teamId) {
                                                var chooseFileBtn = document.createElement("input");
                                                chooseFileBtn.id = teamInfo.attendanceId;
                                                chooseFileBtn.type = "file";
                                                chooseFileBtn.className = "chooseFile";
                                                aRow.cells[3].appendChild(chooseFileBtn);

                                                if (teamInfo.reportUrl == null) {
                                                    var uploadReportBtn = document.createElement("button");
                                                    uploadReportBtn.className = "uploadReportBtn";
                                                    uploadReportBtn.name = teamInfo.attendanceId;
                                                    uploadReportBtn.innerText = "上传";
                                                    aRow.cells[3].appendChild(uploadReportBtn);
                                                } else {
                                                    var reuploadReportBtn = document.createElement("button");
                                                    reuploadReportBtn.className = "reuploadReportBtn";
                                                    reuploadReportBtn.name = teamInfo.attendanceId;
                                                    reuploadReportBtn.innerText = "重新上传";
                                                    aRow.cells[3].appendChild(reuploadReportBtn);
                                                }
                                            } else {
                                                aRow.cells[3].innerHTML = "";
                                            }
                                        }
                                    }
                                }
                            },
                            error: function(XMLHttpRequest) {
                                alert("AJAX error: " + XMLHttpRequest.status);
                            }
                        });
                    }
                },
                error: function(XMLHttpRequest) {
                    alert("AJAX error: " + XMLHttpRequest.status);
                }
            });
        },
        error: function(XMLHttpRequest) {
            alert("AJAX error: " + XMLHttpRequest.status);
        }
    });

    $("#status-list").on('click', '.enrollBtn', function() {
        var btnInfo = this.id.split("-");
        var classId = parseInt(btnInfo[0]);
        var teamId = parseInt(btnInfo[1]);
        var teamOrder = parseInt(btnInfo[2]);

        var teamInfo = JSON.stringify({
            "teamId": teamId,
            "teamOrder": teamOrder
        });

        var enrollUrl = "/seminar/" + seminarId + "/class/" + classId + "/attendance";
        $.ajax({
            type: 'post',
            url: enrollUrl,
            headers: {'Authorization': 'Bearer ' + token},
            data: teamInfo,
            contentType: "application/json;charset=UFT-8",
            success: function() {
                alert("报名成功！");
                window.location.reload();
            },
            error: function(XMLHttpRequest) {
                if (XMLHttpRequest.status == 400) {
                    alert("当前不是报名时间段！");
                } else if (XMLHttpRequest.status == 403) {
                    alert("报名失败，该轮次已报名！");
                } else {
                    alert("AJAX error: " + XMLHttpRequest.status);
                }
            }
        });
    });

    $("#status-list").on('click', '.cancelSeminarBtn', function() {
        var attendanceId = this.name;
        var cancelSeminarUrl = "/attendance/" + attendanceId;
        $.ajax({
            type: 'delete',
            url: cancelSeminarUrl,
            headers: {'Authorization': 'Bearer ' + token},
            success: function() {
                alert("取消成功！");
                window.location.reload();
            },
            error: function(XMLHttpRequest) {
                alert("AJAX error: " + XMLHttpRequest.status);
            }
        });
    });

    $("#status-list").on('click', '.downloadPptBtn', function() {
        var attendanceId = this.name;
        var fileName = this.id;
        var downloadPptUrl = "/attendance/" + attendanceId + "/powerpoint";
        axios.get(downloadPptUrl, {
            headers: {'Authorization': 'Bearer ' + token},
            responseType: 'blob'
        }).then((res) => {
            var b = new Blob([res.data]);
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
            alert("下载成功！");
        });
    });

    $("#status-list").on('click', '.downloadReportBtn', function() {
        var attendanceId = this.name;
        var fileName = this.id;
        var downloadReportUrl = "/attendance/" + attendanceId + "/report";
        axios.get(downloadReportUrl, {
            headers: {'Authorization': 'Bearer ' + token},
            responseType: 'blob'
        }).then((res) => {
            var b = new Blob([res.data]);
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
            alert("下载成功！");
        });
    });

    $("#status-list").on('click', '.uploadPptBtn', function() {
        var attendanceId = this.name;
        var files = document.getElementById(attendanceId).files;
        let formData = new FormData();
        formData.append("file", files[0]);
        var uploadPptUrl = "/attendance/" + attendanceId + "/powerpoint";
        $.ajax({
            type: 'post',
            url: uploadPptUrl,
            headers: {'Authorization': 'Bearer ' + token},
            data: formData,
            contentType: false,
            processData: false,
            success: function() {
                alert("上传成功！");
            },
            error: function(XMLHttpRequest) {
                alert("AJAX error: " + XMLHttpRequest.status);
            }
        });
    });

    $("#status-list").on('click', '.uploadReportBtn', function() {
        var attendanceId = this.name;
        var files = document.getElementById(attendanceId).files;
        let formData = new FormData();
        formData.append("file", files[0]);
        var uploadReportUrl = "/attendance/" + attendanceId + "/report";
        $.ajax({
            type: 'post',
            url: uploadReportUrl,
            headers: {'Authorization': 'Bearer ' + token},
            data: formData,
            contentType: false,
            processData: false,
            success: function() {
                alert("上传成功！");
            },
            error: function(XMLHttpRequest) {
                if (XMLHttpRequest.status == 400) {
                    alert("报告提交时间已截止！");
                } else {
                    alert("AJAX error: " + XMLHttpRequest.status);
                }
            }
        });
    });

    $("#status-list").on('click', '.reuploadPptBtn', function() {
        var attendanceId = this.name;
        var files = document.getElementById(attendanceId).files;
        let formData = new FormData();
        formData.append("file", files[0]);
        var reuploadPptUrl = "/attendance/" + attendanceId + "/powerpoint";
        $.ajax({
            type: 'put',
            url: reuploadPptUrl,
            headers: {'Authorization': 'Bearer ' + token},
            data: formData,
            contentType: false,
            processData: false,
            success: function() {
                alert("重传成功！");
            },
            error: function(XMLHttpRequest) {
                alert("AJAX error: " + XMLHttpRequest.status);
            }
        });
    });

    $("#status-list").on('click', '.reuploadReportBtn', function() {
        var attendanceId = this.name;
        var files = document.getElementById(attendanceId).files;
        let formData = new FormData();
        formData.append("file", files[0]);
        var reuploadReportUrl = "/attendance/" + attendanceId + "/report";
        $.ajax({
            type: 'put',
            url: reuploadReportUrl,
            headers: {'Authorization': 'Bearer ' + token},
            data: formData,
            contentType: false,
            processData: false,
            success: function() {
                alert("重传成功！");
            },
            error: function(XMLHttpRequest) {
                if (XMLHttpRequest.status == 400) {
                    alert("报告提交时间已截止！");
                } else {
                    alert("AJAX error: " + XMLHttpRequest.status);
                }
            }
        });
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
    $("#seminarBtn").click(function() {
        if (role == "teacher") {
            window.location.href =
                "teacherSeminarList.html?courseId=" + courseId +
                "&courseName=" + courseName +
                "&role=teacher";
        } else {
            window.location.href =
                "studentSeminarList.html?courseId=" + courseId +
                "&courseName=" + courseName +
                "&role=student";
        }
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
