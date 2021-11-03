var urlTopic = 'http://localhost:8080/topic';
var urlResult = 'http://localhost:8080/result';
var urlLesson = 'http://localhost:8080/lesson';
var urlCodeQuestion = 'http://localhost:8080/code-question';
var urlGetAllCodeQuestionByTopic = 'http://localhost:8080/code-question/topic?topic=';
var urlQuestion = 'http://localhost:8080/question';
var urlPost = 'http://localhost:8080/post';
moment.locale("vi");

function alert(icon, title) {
    Swal.fire({
        position: "top-end",
        icon: icon,
        title: title,
        showConfirmButton: false,
        timer: 1500,
    });
}

var stompClient = null;
var username = $('#username_cont').text();
const handleAvt = () => {
    var colors = [
        "#2196F3",
        "#32c787",
        "#00BCD4",
        "#ff5652",
        "#ffc107",
        "#ff85af",
        "#FF9800",
        "#39bbb0",
    ];
    var color = Math.ceil(Math.random() * (colors.length - 1));
    var avt_txt = username.charAt(0).toUpperCase();
    $('#username_avt').text(avt_txt);
    $('#username_avt').css("background-color", colors[color]);
}
$(function () {
    handleAvt();
    loadResult();
    connect();
});

function connect(event) {
    var socket = new SockJS("/websocket");
    stompClient = Stomp.over(socket);
    stompClient.connect({}, onConnected);
    stompClient.debug = () => {};
}
function onConnected() {
    stompClient.subscribe("/topic/public", onMessageReceived);
}
// function onError(error) {
//     connectingElement.textContent =
//         "Không thể kết nối với WebSocket!";
//     connectingElement.style.color = "red";
// }
function handleReceive(message, text) {
    var sender = message.sender
    var avt_sender = sender.charAt(0).toUpperCase()
    var htmlRs = `<li class="notice_content-nav ${text} h5">
                      <span class="avt-sender">${avt_sender}</span>
                      <small class="h6">` + moment(message.time).calendar() + `</small>
                      <p class="m-0 mt-2 d-flex align-items-center">${sender} ${message.content}</p>
                  </li>`;
    $('.notice_content_name').prepend(htmlRs);
}

function onMessageReceived(payload) {
    var message = JSON.parse(payload.body);
    if (message.type === "JOIN") {
        handleReceive(message, 'text-success');
    } else if (message.type === "LEAVE") {
        handleReceive(message, 'text-danger');
    } else if (message.type === "DOEXAM") {
        handleReceive(message, 'text-success');
    }
}

function hidePage() {
    $('.tab-add-question').hide();
    $('.tab-list-question').hide();
    $('.tab-table-point').hide();
    $('.tab-services').hide();
    $('.services').removeClass('active');
    $('.add-question').removeClass('active');
    $('.see-question').removeClass('active');
    $('.see-point').removeClass('active');
}

//3.HANDLE SUBMIT
$(document).on('click', '.btn_edit', function (e) {
    e.preventDefault();
    var id = $(this).attr("id");
    $.ajax({
        url: urlQuestion + "/get-by-id?id=" + id,
        type: "GET",
        dataType: "json",
        success: function (res) {
            $(".modal-body_id").html(`<div class="row">
                                            <div class="form-group col-3 d-none">
                                                <label for="exampleFormControlInput1">Code ques số</label>
                                                <input type="text" name="codeQuestionId" class="form-control"
                                                     value="${res.codequestion_id}" readonly>
                                            </div>
                                            <div class="form-group col-3 d-none">
                                                <label for="exampleFormControlInput1">Câu hỏi số</label>
                                                <input type="text" name="id" class="form-control"
                                                     value="${res.id}" readonly>
                                            </div>
                                            <div class="form-group col-3 d-none">
                                                <label for="exampleFormControlInput1">Id Đáp số</label>
                                                <input type="text" name="answerId" class="form-control"
                                                     value="${res.ans_id}" readonly>
                                            </div>
                                            <div class="form-group col-3">
                                                <label for="exampleFormControlSelect1">Độ khó</label>
                                                <select name="level" id="edit_level" class="form-select">
                                                    <option value="difficult">Khó</option>
                                                    <option value="normal">Bình thường</option>
                                                    <option value="easy">Dễ</option>
                                                </select>
                                            </div>
                                        </div>
                            `)
            $(".modal-body_ans").html(`<div class="row">
                                            <div class="col-6 d-flex mb-3">
                                                <label class="m-0 d-flex align-items-center"
                                                    style="width: calc(100% / 3);" for="">Đáp án
                                                    A</label>
                                                <input type="text" name="answerAnsw_A" class="form-control" value="${res.answ_A}">
                                            </div>
                                            <div class="col-6 d-flex mb-3">
                                                <label class="m-0 d-flex align-items-center"
                                                    style="width: calc(100% / 3);" for="">Đáp án
                                                    B</label>
                                                <input type="text" name="answerAnsw_B" class="form-control" value="${res.answ_B}">
                                            </div>
                                            <div class="col-6 d-flex mb-3">
                                                <label class="m-0 d-flex align-items-center"
                                                    style="width: calc(100% / 3);" for="">Đáp án
                                                    C</label>
                                                <input type="text" name="answerAnsw_C" class="form-control" value="${res.answ_C}">
                                            </div>
                                            <div class="col-6 d-flex mb-3">
                                                <label class="m-0 d-flex align-items-center"
                                                    style="width: calc(100% / 3);" for="">Đáp án
                                                    D</label>
                                                <input type="text" name="answerAnsw_D" class="form-control" value="${res.answ_D}">
                                            </div>
                                        </div>
                                        <div>
                                            <label for="">Đáp án đúng</label>
                                            <select name="answerCorrect_ans" id="answerCorrect_ans" class="form-select mb-3"
                                                style="width: 15%;" id="correct_ans">
                                                <option value="A">A</option>
                                                <option value="B">B</option>
                                                <option value="C">C</option>
                                                <option value="D">D</option>
                                            </select>
                                        </div>
                                        <div class="d-flex justify-content-center">
                                            <button class="btn btn-secondary mx-3" data-dismiss="modal">Close</button>
                                            <button class="btn btn-primary mx-3" type="submit">Submit</button>
                                        </div>`);
            $('#edit_level').val(res.level);
            $('#answerCorrect_ans').val(res.correct_ans);
            tinyMCE.get('name_question_edit').setContent(res.name_question);
        }
    })
});
$(document).on('submit', '#form_add', function (e) {
    e.preventDefault();
    var formdata = new FormData(this);
    if (formdata.get('name_question') == "") {
        alert('error', 'Nhập thiếu thông tin');
    } else {
        $.ajax({
            url: urlQuestion,
            type: "POST",
            data: formdata,
            enctype: "multipart/form-data",
            processData: false,
            contentType: false,
            cache: false,
            success: function (response) {
                $('#form_add').trigger('reset');
                alert('success', 'Thêm thành công');
            },
            error: function (e) {
                console.log(e);
                alert('error', 'Lỗi')
            }
        });
    }
});
$(document).on('submit', '#form_edit', function (e) {
    e.preventDefault();
    var formdata = new FormData(this);
    $(this).trigger('reset');
    $.ajax({
        url: urlQuestion,
        type: "PUT",
        data: formdata,
        enctype: "multipart/form-data",
        processData: false,
        contentType: false,
        cache: false,
        success: function (response) {
            alert('success', 'Sửa thành công');
            var html = `<h6 class="fw-bold">Câu hỏi ${response.id} :<span>(${response.level})</span></h6>
                                    <h6 class='name_question'>${response.name_question}</h6>
                                    <label for="A"><span>A.</span>${response.answ_A}</label><br>
                                    <label for="B"><span>B.</span>${response.answ_B}</label><br>
                                    <label for="D"><span>C.</span>${response.answ_C}</label><br>
                                    <label for="D"><span>D. </span>${response.answ_D}</label><br>
                                    <p class="m-0 p-0">Đáp án đúng <span class="badge bg-success">${response.correct_ans}</span></p>
                                    <button class="btn btn-sm btn_edit btn-warning" id="${response.id}" data-toggle="modal"
                                    data-target="#modal-edit-question">Sửa</button>`;
            $('.question-' + response.id).html(html);
            $('#modal-edit-question').modal('hide');
        },
        error: function (e) {
            console.log(e);
            console.log(formdata);
        }
    });
});
$(document).on('submit', '#form_add_lesson', function (e) {
    e.preventDefault();
    var formdata = new FormData(this);
    formdata.set('createBy', username.toUpperCase());
    $.ajax({
        url: urlLesson,
        type: "POST",
        data: formdata,
        enctype: "multipart/form-data",
        processData: false,
        contentType: false,
        cache: false,
        success: function (res) {
            $('#form_add_lesson')[0].reset();
            $('#modal-lesson').modal('hide');
            handleAllAddQuestion();
            alert('success', 'Thành công');
        },
        error: function (e) {
            console.log(e);
            alert('error', 'Có lỗi');
        }
    });
});
$(document).on('submit', '#form_add_topic', function (e) {
    e.preventDefault();
    var formdata = new FormData(this);
    formdata.set('createBy', username.toUpperCase());
    $.ajax({
        url: urlTopic,
        type: "POST",
        data: formdata,
        enctype: "multipart/form-data",
        processData: false,
        contentType: false,
        cache: false,
        success: function (res) {
            $("#form_add_topic").trigger('reset');
            $('#modal-topic').modal('hide');
            handleAllAddQuestion();
            alert('success', 'Thành công');
        },
        error: function (e) {
            console.log(e);
            alert('error', 'Có lỗi');
        }
    });
});
$(document).on('submit', '#form_add_code', function (e) {
    e.preventDefault();
    var formdata = new FormData(this);
    formdata.set('create_by', username.toUpperCase());
    $.ajax({
        url: urlCodeQuestion,
        type: "POST",
        data: formdata,
        enctype: "multipart/form-data",
        processData: false,
        contentType: false,
        cache: false,
        success: function (res) {
            $('#form_add_code').trigger('reset');
            $('#modal-code').modal('hide');
            handleAllAddQuestion();
            alert('success', 'Thành công');
        },
        error: function (e) {
            console.log(e);
            alert('error', 'Có lỗi');
        }
    });
});
// 4.Khi click vào vào các button
$(document).on('click', '.add-question', function (e) {
    e.preventDefault();
    hidePage();
    $('.add-question').addClass('active');
    $('.tab-add-question').show();
    handleAllAddQuestion();
});
$(document).on('click', '.see-point', function (e) {
    e.preventDefault();
    loadResult();
    hidePage();
    $('.tab-table-point').show();
    $('.see-point').addClass('active');

});


$(document).on('click', '.nav-topic-add', function (e) {
    $('.nav-topic-add').removeClass('active');
    $(this).addClass('active');
    $.ajax({
        // url: urlCodeQuestion + '?filter=topic=="' + $(this).text() + '";create_by=="' + username + '"',
        url: urlCodeQuestion + '?filter=topic=="' + $(this).text() + '"',
        type: "GET",
        dataType: "json",
        success: function (res) {
            if (res.content == "") {
                $('#codeQuestionId').html(`<option>Chưa có mã đề</option>`);
            } else {
                var htmlRs = "";
                $.each(res.content, function (index, rs) {
                    htmlRs += `<option value="${rs.id}">${rs.code}</option>`;
                });
                $('#codeQuestionId').html(htmlRs);
            }
        },
    });
    e.preventDefault();
});
$(document).on('change', '.lesson', function () {
    $.ajax({
        url: urlTopic + "/topic-by-id?id=" + $(this).val(),
        type: "GET",
        dataType: "json",
        success: function (res) {
            if (res == "") {
                $('.nav-topic').html(`<li class="nav - item">
                                           <button class= "btn nav-topic-add" disabled>Chưa có chủ đề</button >
                                       </li >`);
                $('#codeQuestionId').html("<option>Chưa có</option>");
            } else {
                var htmlRs = "";
                $.each(res, function (index, rs) {
                    htmlRs += `<li class="nav-item">
                                        <button class="btn nav-topic-add" value="${rs.id}">${rs.name}</button>
                                        </li>`;
                });
                $('.nav-topic').html(htmlRs);
                $('.nav-topic-add:first').addClass('active');
                $.ajax({
                    // url: urlCodeQuestion + '?filter=topic=="' + $('.nav-topic-add:first').text() + '";create_by=="' + username + '"',
                    url: urlCodeQuestion + '?filter=topic=="' + $('.nav-topic-add:first').text() + '"',
                    type: "GET",
                    dataType: "json",
                    success: function (res) {
                        if (res.content == "") {
                            $('#codeQuestionId').html(`<option>Chưa có mã đề</option>`);
                        } else {
                            var htmlRs = "";
                            $.each(res.content, function (index, rs) {
                                htmlRs += `<option value="${rs.id}">${rs.code}</option>`;
                            });
                            $('#codeQuestionId').html(htmlRs);
                        }
                    },
                });
            }
        },
    });
});
$(document).on('change', '.form_add_code-lesson', function () {
    $.ajax({
        url: urlTopic + "/topic-by-id?id=" + $(this).val(),
        type: "GET",
        dataType: "json",
        success: function (res) {
            if (res == "") {
                $('.form_add_code-topic').html(`<option>Chưa có</option>`);
            } else {
                var htmlRs = "";
                $.each(res, function (index, rs) {
                    htmlRs += `<option value="${rs.name}">${rs.name}</option>`;
                });
                $('.form_add_code-topic').html(htmlRs);
            }
        }
    });
});
$(document).on('click', '.btn-topic-of-add', function () {
    $.ajax({
        url: urlLesson + "/get-all",
        type: "GET",
        dataType: "json",
        success: function (res) {
            var htmlRs = "";
            $.each(res, function (index, rs) {
                htmlRs += `<option value="${rs.id}" id="op-lesson">${rs.title}</option>`;
            });
            $('select[name="lessonId"]').html(htmlRs);
        }
    });
});
$(document).on('click', '.btn-code-of-add', function () {
    $.ajax({
        url: urlLesson + "/get-all",
        type: "GET",
        dataType: "json",
        success: function (res) {
            var htmlRs = "";
            $.each(res, function (index, rs) {
                htmlRs += `<option value="${rs.id}" id="op-lesson">${rs.title}</option>`;
            });
            $('.form_add_code-lesson').html(htmlRs);
            $.ajax({
                url: urlTopic + "/topic-by-id?id=" + $(".form_add_code-lesson").val(),
                type: "GET",
                dataType: "json",
                success: function (res) {
                    if (res == "") {
                        $('.form_add_code-topic').html(`<option>Chưa có</option>`);
                    } else {
                        var htmlRs = "";
                        $.each(res, function (index, rs) {
                            htmlRs += `<option value="${rs.name}">${rs.name}</option>`;
                        });
                        $('.form_add_code-topic').html(htmlRs);
                    }
                }
            });
        }
    });
});
function handleAllAddQuestion() {
    $.ajax({
        url: urlLesson + "/get-all",
        type: "GET",
        dataType: "json",
        success: function (res) {
            var htmlRs = "";
            $.each(res, function (index, rs) {
                htmlRs += `<option value="${rs.id}" id="op-lesson">${rs.title}</option>`;
            });
            $('.lesson').html(htmlRs);
            $.ajax({
                url: urlTopic + "/topic-by-id?id=" + $(".lesson").val(),
                type: "GET",
                dataType: "json",
                success: function (res) {
                    if (res == "") {
                        $('.nav-topic').html(`<li class="nav - item">
                                                <button class= "btn nav-topic-add" disabled>Chưa có chủ đề</button >
                                                </li >`);
                        $('#codeQuestionId').html("<option>Chưa có</option>");
                    } else {
                        var htmlRs = "";
                        $.each(res, function (index, rs) {
                            htmlRs += `<li class="nav-item">
                                        <button class="btn nav-topic-add" value="${rs.id}">${rs.name}</button>
                                        </li>`;
                        });
                        $('.nav-topic').html(htmlRs);
                    }
                    var nav_topic_add_first = $('.nav-topic-add:first');
                    nav_topic_add_first.addClass('active');
                    $.ajax({
                        url: urlGetAllCodeQuestionByTopic + nav_topic_add_first.text(),
                        type: "GET",
                        dataType: "json",
                        success: function (res) {
                            if (res == "") {
                                $('#codeQuestionId').html(`<option>Chưa có</option>`);
                            } else {
                                var htmlRs = "";
                                $.each(res, function (index, rs) {
                                    htmlRs += `<option value="${rs.id}">${rs.code}</option>`;
                                });
                                $('#codeQuestionId').html(htmlRs);
                            }
                        },
                    });
                },
            });
        },
    });
}
$(document).on('keyup', 'input[name="code"]', function () {
    $('input[name="code"]').removeClass('error');
    $.ajax({
        url: urlCodeQuestion + "/all-code",
        type: "GET",
        dataType: "json",
        success: function (res) {
            var er = false;
            $.each(res, function (index, rs) {
                if (rs == $('input[name="code"]').val()) {
                    er = true;
                }
            });
            if (er == true) {
                $('input[name="code"]').addClass('error');
            }
        }, error: function (e) {
            console.log(e);
        }
    });
})

// 8.HANDLE TRANG SEE-QUESTION
function handleGetCodeQuestionOnPageSee(res) {
    if (res.content == "") {
        $(".list-question-code").html(`<option value="null">Bạn chưa có mã đề nào</option>`);
    } else {
        var htmlRs = "";
        $.each(res.content, function (index, rs) {
            htmlRs += `<option value="${rs.code}">${rs.code}</option>`;
        });
        $(".list-question-code").html(htmlRs);
    }
}
function handleGetTopicOnPageSee(res) {
    if (res == "") {
        $(".list-question-topic").html(`<option value="null">Bạn chưa có</option>`);
    } else {
        var htmlRs = "";
        $.each(res, function (index, rs) {
            htmlRs += `<option value="${rs.name}">${rs.name}</option>`;
        });
        $(".list-question-topic").html(htmlRs);
    }
}
$(document).on("click", ".see-question", function (e) {
    e.preventDefault()
    hidePage();
    $(".see-question").addClass("active");
    $(".tab-list-question").show();
    $('.list-question-content').empty();
    $('.pagination-filter-question').empty();
    $.ajax({
        url: urlLesson + "/get-all",
        type: "GET",
        dataType: "json",
        success: function (res) {
            if (res == "") {
                $(".list-question-lesson").html(`<option value="0">Chưa có</option>`);
            } else {
                var htmlRs = "";
                $.each(res, function (index, rs) {
                    htmlRs += `<option value="${rs.id}">${rs.title}</option>`;
                });
                $(".list-question-lesson").html(htmlRs);
                $.ajax({
                    url: urlTopic + "/topic-by-id?id=" + $(".list-question-lesson").val(),
                    type: "GET",
                    dataType: "json",
                    success: function (res) {
                        handleGetTopicOnPageSee(res);
                        $.ajax({
                            // url: urlCodeQuestion + '?filter=topic=="' + $(".list-question-topic").val() + '";create_by=="' + username + '"',
                            url: urlCodeQuestion + '?filter=topic=="' + $(".list-question-topic").val() + '"',
                            type: "GET",
                            dataType: "json",
                            success: function (res) {
                                handleGetCodeQuestionOnPageSee(res);
                            },
                        });
                    },
                    error: function (e) {
                        console.log("lỗi");
                    }
                });
            }
        },
    });
});
$(document).on("change", ".list-question-lesson", function () {
    $.ajax({
        url: urlTopic + "/topic-by-id?id=" + $(this).val(),
        type: "GET",
        dataType: "json",
        success: function (res) {
            handleGetTopicOnPageSee(res);
            $.ajax({
                // url: urlCodeQuestion + '?filter=topic=="' + $(".list-question-topic").val() + '";create_by=="' + username + '"',
                url: urlCodeQuestion + '?filter=topic=="' + $(".list-question-topic").val() + '"',
                type: "GET",
                dataType: "json",
                success: function (res) {
                    handleGetCodeQuestionOnPageSee(res);
                },
            });
        },
    });
});
$(document).on("change", ".list-question-topic", function () {
    $.ajax({
        // url: urlCodeQuestion + '?filter=topic=="' + $(this).val() + '";create_by=="' + username + '"',
        url: urlCodeQuestion + '?filter=topic=="' + $(this).val() + '"',
        type: "GET",
        dataType: "json",
        success: function (res) {
            handleGetCodeQuestionOnPageSee(res);
        },
    });
});
$(document).on('click', '.btn-filter', function (e) {
    e.preventDefault();
    $.ajax({
        url: urlQuestion + '/get/filter?code=' + $('.list-question-code').val() + '&page=0',
        type: "GET",
        dataType: "json",
        error: function (e) {
            alert('error', 'Chọn mã đề');
        },
        success: function (res) {
            if (res.content == "") {
                alert('error', 'Hãy chọn đầy đủ hoặc chưa có câu hỏi');
            }
            if (res.totalPage != 0) {
                var htmlPagi = "";
                for (let i = 1; i <= res.totalPage; i++) {
                    htmlPagi += `<li class="page-item"><button class="page-link pagi-question" value="${i - 1}">${i}</button></li>`;
                }
                $('.pagination-filter-question').html(htmlPagi);
            }
            $('.pagi-question:first').addClass('bg-success text-white');
            if (res.content != "") {
                var htmlRs = "";
                $.each(res.content, function (index, rs) {
                    htmlRs += `<div class="col-6 mb-2 question-${rs.id}">
                                    <h6 class="fw-bold m-0">
                                        Câu hỏi ${rs.id} :<span class="badge bg-secondary">${rs.level}</span>
                                    </h6>
                                    <small>Được tạo vào ` + moment(rs.create_at).fromNow() + `</small>
                                    <h6 class="m-0 name_question">${rs.name_question}</h6>
                                    <label for="A"><span>A.</span>${rs.answ_A}</label><br>
                                    <label for="B"><span>B.</span>${rs.answ_B}</label><br>
                                    <label for="D"><span>C.</span>${rs.answ_C}</label><br>
                                    <label for="D"><span>D. </span>${rs.answ_D}</label><br>
                                    <p class="m-0 p-0">Đáp án đúng <span class="badge bg-success">${rs.correct_ans}</span></p>
                                    <button class="btn btn-sm mt-2 btn_edit btn-warning" id="${rs.id}" data-toggle="modal"
                                    data-target="#modal-edit-question">Sửa</button>
                                </div>`;
                });
                $('.list-question-content').html(htmlRs);
            }
        }
    });
});
$(document).on('click', '.pagi-question', function (e) {
    e.preventDefault();
    $('.pagi-question').removeClass('bg-success text-white');
    $(this).addClass('bg-success text-white');
    $.ajax({
        url: urlQuestion + '/get/filter?code=' + $('.list-question-code').val() + '&page=' + $(this).val(),
        type: "GET",
        dataType: "json",
        error: function (e) {
            alert('error', 'Chọn mã đề');
        },
        success: function (res) {
            var htmlRs = "";
            $.each(res.content, function (index, rs) {
                htmlRs += `<div class="col-6 mb-2">
                                    <h6 class="fw-bold">Câu hỏi ${rs.id} :<span>(${rs.level})</span></h6>
                                    <h6 class='name_question'>${rs.name_question}</h6>
                                    <label for="A"><span>A.</span>${rs.answ_A}</label><br>
                                    <label for="B"><span>B.</span>${rs.answ_B}</label><br>
                                    <label for="D"><span>C.</span>${rs.answ_C}</label><br>
                                    <label for="D"><span>D. </span>${rs.answ_D}</label><br>
                                    <button class="btn btn-sm btn_edit btn-warning" id="${rs.id}" data-toggle="modal"
                                    data-target="#modal-edit-question">Sửa</button>
                                    </div>`;
            });
            $('.list-question-content').html(htmlRs);
        }
    });
})

// 9.HANDLE TRANG RESULT
function loadResult() {
    $.ajax({
        url: urlResult + "?page=0",
        type: "GET",
        dataType: "json",
        success: function (res) {
            if (res.content == "") {
                $('.table-content-result').html(`<h3 class="fw-bold">Không có kết quả</h3>`)
                $(".pagination-result").empty();
            } else {
                var tableRs = "";
                var pagiRs = "";
                for (let i = 1; i <= res.totalPage; i++) {
                    pagiRs += `<li class="page-item"><button class="page-link pagi-result" value="${i - 1}">${i}</button></li>`;
                }
                handleTableResultIfHave(res.content)
                $(".pagination-result").html(pagiRs);
                $('.pagi-result:first').addClass('bg-success text-white');
            }
        },
    });
    $.ajax({
        url: urlTopic + "/get-hot",
        type: "GET",
        dataType: "json",
        success: function (res) {
            var htmlRs = "";
            htmlRs += `<li class="nav-item">
                                    <button class="btn nav-course-topic nav-course-all active">All</button>
                                    </li>`;
            $.each(res, function (index, rs) {
                htmlRs += `<li class="nav-item">
                                    <button class="btn nav-course-topic nav-course-item">${rs.name}</button>
                                    </li>`;
            });
            htmlRs += `<li class="nav-item">
                                    <button class="btn nav-course-topic see-full">Xem thêm</button>
                                    </li>`;
            $('.nav-course').html(htmlRs);
        }
    })
}
function handleTableResultIfHave(res) {
    var tableRs = "";
    $.each(res, function (index, rs) {
        tableRs += `<tr class="text-white">
                        <td class="text-uppercase">${rs.username}</td>
                        <td>${rs.code}</td> 
                        <td>${rs.name}</td>
                        <td>${rs.number_question}</td>
                        <td>${rs.number_point}</td>
                    </tr>`;
    });
    $(".table-content-result").html(tableRs);
}
$(document).on('click', '.nav-course-all', function () {
    $('.nav-course-topic').removeClass('active');
    $(this).addClass('active');
    $.ajax({
        url: urlResult + "?page=0",
        type: "GET",
        dataType: "json",
        success: function (res) {
            if (res.content == "") {
                $('.table-content-result').html(`<h3 class="fw-bold">Không có kết quả</h3>`)
                $(".pagination-result").empty();
            } else {
                var pagiRs = "";
                for (let i = 1; i <= res.totalPage; i++) {
                    pagiRs += `<li class="page-item"><button class="page-link pagi-result" value="${i - 1}">${i}</button></li>`;
                }
                handleTableResultIfHave(res.content)
                $(".pagination-result").html(pagiRs);
                $('.pagi-result:first').addClass('bg-success text-white');
            }
        },
    });
})
$(document).on('click', '.nav-course-item', function (e) {
    $('.nav-course-topic').removeClass('active');
    $(this).addClass('active');
    $.ajax({
        url: urlResult + "?filter=name=='" + $(this).text() + "'",
        type: "GET",
        dataType: "json",
        success: function (res) {
            if (res.content == "") {
                $('.table-content-result').html(`<h3 class="fw-bold">Không có điểm thi</h3>`);
                $(".pagination-result").empty();
            } else {
                var pagiRs = "";
                for (let i = 1; i <= res.totalPage; i++) {
                    pagiRs += `<li class="page-item"><button class="page-link pagi-result-course" value="${i - 1}">${i}</button></li>`;
                }
                handleTableResultIfHave(res.content)
                $(".pagination-result").html(pagiRs);
                $('.pagi-result-course:first').addClass('bg-success text-white');
            }
        }
    })
});
$(document).on('click', '.pagi-result', function (e) {
    $.ajax({
        url: urlResult + "?page=" + $(this).val(),
        type: "GET",
        dataType: "json",
        success: function (res) {
            if (res.content == "") {
                $('.table-content-result').html(`<h3 class="fw-bold">Không có kết quả</h3>`)
                $(".pagination-result").empty();
            } else {
                handleTableResultIfHave(res.content)
            }
        },
    });
    $('.pagi-result').removeClass('bg-success text-white');
    $(this).addClass('bg-success text-white');
});
$(document).on('click', '.pagi-result-course', function (e) {
    $.ajax({
        url: urlResult + "?page=" + $(this).val() + "&filter=name=='" + $('.nav-course-topic.active').text() + "'",
        type: "GET",
        dataType: "json",
        success: function (res) {
            if (res.content == "") {
                $('.table-content-result').html(`<h3 class="fw-bold">Không có kết quả</h3>`)
                $(".pagination-result").empty();
            } else {
                handleTableResultIfHave(res.content)
            }
        },
    });
    $('.pagi-result-course').removeClass('bg-success text-white');
    $(this).addClass('bg-success text-white');
})
$(document).on('click', '.see-full', function (e) {
    $.ajax({
        url: urlTopic + "/get-all",
        type: "GET",
        dataType: "json",
        success: function (res) {
            var htmlRs = "";
            htmlRs += `<li class="nav-item col-2">
                                    <button class="btn nav-course-topic nav-course-all">All</button>
                                    </li>`;
            $.each(res, function (index, rs) {
                htmlRs += `<li class="nav-item col-2">
                                    <button class="btn nav-course-topic nav-course-item">${rs.name}</button>
                                    </li>`;
            });
            htmlRs += `<li class="nav-item col-2">
                                    <button class="btn nav-course-topic see-full">Thu gọn</button>
                                    </li>`;
            $('.nav-course-full').html(htmlRs);
        }
    })
    $('.nav_summary').toggle();
    $('.nav_full').toggle();
});
$(document).on('click', '.btn-search-rs', function () {
    const inpSearch = $('.inp-search-rs').val();
    $.ajax({
        url: urlResult + "/result-by-username?username=" + inpSearch,
        method: "GET",
        dataType: "json",
        success: function (res) {
            handleTableResultIfHave(res);
            $(".pagination-result").empty();
            $('.nav-course-topic').removeClass('active');
        },
        statusCode: {
            404: function (res) {
                alert("error", res.responseJSON.message);
            }
        }
    })
})


// 10.HANDLE TRANG SERVICE
$(document).on('click', '.services', function (e) {
    hidePage();
    $('.nav-table-lesson').show();
    $('.nav-table-topic').hide();
    $('.tab-services').show();
    $('.services').addClass('active');
    $('.nav-service-item').removeClass('active');
    $('.nav-service-item:first').addClass('active');
    getAllLessonOnService();
});

// HANDLE TRANG SERVICE Lesson
function getAllLessonOnService() {
    $.ajax({
        url: urlLesson + "/get-all",
        method: "GET",
        dataType: "json",
        success: function (res) {
            var htmlRs = "";
            $.each(res, function (index, rs) {
                htmlRs += `<tr class="text-center content-${rs.id}">
                                        <td class="fw-bold">${rs.title}</td>
                                        <td>${rs.description}</td>
                                        <td class="fw-bold">${rs.ishot}</td>
                                        <td style="width:25%"><img class="w-100"
                                                src="${rs.image}"
                                                alt=""></td>
                                        <td style="width: 8%;"><button class="btn btn-primary btn-edit-lesson" value="${rs.id}" data-toggle="modal"
                                                data-target="#modal-edit-lesson"><i class="fa fa-pencil"
                                                    aria-hidden="true"></i></button>
                                        </td>       
                                        </tr>`;
            })
            $('.table-service-content').html(htmlRs);
        }
    });
}

$(document).on('submit', '#form-edit-lesson', function (e) {
    e.preventDefault();
    var formdata = new FormData(this);
    $.ajax({
        url: urlLesson,
        type: "PUT",
        data: formdata,
        enctype: "multipart/form-data",
        processData: false,
        contentType: false,
        cache: false,
        success: function (response) {
            $('#modal-edit-lesson').modal('hide');
            handleAfterUpdateLesson(response);
            alert('success', 'Sửa thành công');
        },
        error: function (e) {
            console.log(e);
            alert('error', 'Xảy ra lỗi');
        }
    });
})

function handleAfterUpdateLesson(data) {
    htmlRs = `<td class="fw-bold">${data.title}</td>
              <td>${data.description}</td>
              <td class="fw-bold">${data.ishot}</td>
              <td style="width:25%"><img class="w-100"
                                    src="${data.image}"
                                    alt=""></td>
              <td style="width: 8%;"><button class="btn btn-primary btn-edit-lesson" value="${data.id}" data-toggle="modal"
                  data-target="#modal-edit-lesson"><i class="fa fa-pencil"
                  aria-hidden="true"></i></button>
              </td>`;

    $('.content-' + data.id).html(htmlRs);
}

$(document).on('click', '.btn-edit-lesson', function () {
    $.ajax({
        url: urlLesson + "/get-by-id?id=" + $(this).val(),
        method: "GET",
        dataType: "json",
        success: function (rs) {
            var htmlRsPart1 = "";
            var htmlRsPart2 = "";
            htmlRsPart1 = `<input type="text" name="id" class="form-control d-none" readonly value="${rs.id}">
                                        <div class="form-group">
                                            <label class="fw-bold form-label">Tiêu đề</label>
                                            <input type="text" name="title" class="form-control" value="${rs.title}">
                                        </div>`;
            htmlRsPart2 = `<div class="form-group">
                                            <label class="fw-bold form-label">Phổ biến</label>
                                            <select name="ishot" class="form-select">
                                                <option value="1">Hot</option>
                                                <option value="0">No</option>
                                            </select>
                                        </div>
                                        <div class="form-group">
                                            <label class="fw-bold form-label">Ảnh chủ đề</label>
                                            <div class="form-control">
                                                <img src="${rs.image}"
                                                    class="w-100 img-edit-lesson mb-2" alt="">
                                                <input type="hidden" role="uploadcare-uploader" name="image"
                                                    data-public-key="72791b85ba57a284b745" /><span
                                                        class="text-warning fw-bold">Nếu thay
                                                        đổi</span>
                                            </div>
                                        </div>
                                        <div class="mt-3 text-center"><button type="submit"
                                                class="post-form-submit">Xác nhận</button>
                                        </div>`;

            $('.form-edit-lesson-part-1').html(htmlRsPart1);
            $('.form-edit-lesson-part-2').html(htmlRsPart2);
            tinyMCE.get('description-lesson').setContent(rs.description);
            if (rs.ishot == true) {
                $('select[name="ishot"]').val("1");
            }
            if (rs.ishot == false) {
                $('select[name="ishot"]').val("0");
            }
        }
    })
})

// HANDLE TRANG SERVICE TOPIC
function handleAfterUpdateTopic(data) {
    htmlRs = `<td class="fw-bold">${data.name}</td>
                                <td class="fw-bold">${data.ishot}</td>
                                <td style="width:25%"><img class="w-100"
                                    src="${data.image}"
                                    alt=""></td>
                                <td style="width: 8%;"><button class="btn btn-primary btn-edit-topic" data-toggle="modal"
                                        data-target="#modal-edit-topic" value="${data.id}"><i class="fa fa-pencil"
                                            aria-hidden="true"></i></button>
                                </td>
                                <td style="width: 8%;"><button class="btn btn-danger btn-delete-topic" value="${data.id}"><i class="fa fa-trash"
                                            aria-hidden="true"></i></button>
                                </td>`;
    $('.content-' + data.id).html(htmlRs);
}
$(document).on('click', '.btn-edit-topic', function () {
    $.ajax({
        url: urlTopic + "/get-by-id?id=" + $(this).val(),
        method: "GET",
        dataType: "json",
        success: function (rs) {
            var htmlRs = `<input type="text" name="lessonId" class="form-control d-none" readonly value="${rs.lessonId}">
                          <input type="text" name="id" class="form-control d-none" readonly value="${rs.id}">
                          <div class="form-group">
                          <label class="fw-bold form-label">Tên chủ đề</label>
                          <input type="text" name="name" class="form-control" value="${rs.name}" readonly>
                          </div>
                          <div class="form-group">
                              <label class="fw-bold form-label">Phổ biến</label>
                              <select name="ishot" class="form-select">
                                    <option value="1">Hot</option>
                                    <option value="0">No</option>
                              </select>
                          </div>
                          <div class="form-group">
                              <label class="fw-bold form-label">Ảnh chủ đề</label>
                              <div class="form-control">
                                  <img src="${rs.image}" class="w-100 img-edit-lesson mb-2" alt="">
                                      <input type="hidden" role="uploadcare-uploader" name="image"
                                          data-public-key="72791b85ba57a284b745" />
                                      <span class="text-warning fw-bold">Nếu thay đổi</span>
                              </div>
                          </div>
                          <div class="mt-3 text-center"><button type="submit" class="post-form-submit">Xác nhận</button>
                          </div>`;
            $('#form-edit-topic').html(htmlRs);
            if (rs.ishot == true) {
                $('select[name="ishot"]').val("1");
            }
            if (rs.ishot == false) {
                $('select[name="ishot"]').val("0");
            }
        }
    })
})
$(document).on('submit', '#form-edit-topic', function (e) {
    e.preventDefault();
    var formdata = new FormData(this);
    $.ajax({
        url: urlTopic,
        type: "PUT",
        data: formdata,
        enctype: "multipart/form-data",
        processData: false,
        contentType: false,
        cache: false,
        success: function (response) {
            $('#modal-edit-topic').modal('hide');
            handleAfterUpdateTopic(response);
            alert('success', 'Sửa thành công');
        },
        error: function (e) {
            console.log(e);
            alert('error', 'Xảy ra lỗi');
        }
    });
})
// HANDLE TRANG SERVICE NAV
$(document).on('click', '.nav-service-item', function (e) {
    $('.nav-service-item').removeClass('active');
    $('.nav-table').hide();
    $('.post').hide();
    // $('.content-post-form').empty();
    $(this).addClass('active');
    var name = $(this).text();
    if (name == "Lesson") {
        $('.nav-table-lesson').show();
        getAllLessonOnService();
    }
    if (name == "Topic") {
        $('.nav-table-topic').show();
        $.ajax({
            url: urlTopic + "/get-all",
            method: "GET",
            dataType: "json",
            success: function (res) {
                var htmlRs = "";
                $.each(res, function (index, rs) {
                    htmlRs += `<tr class="content-${rs.id}">
                                <td class="fw-bold">${rs.name}</td>
                                <td class="fw-bold">${rs.ishot}</td>
                                <td style="width:25%"><img class="w-100"
                                    src="${rs.image}"
                                    alt=""></td>
                                <td style="width: 8%;"><button class="btn btn-primary btn-edit-topic" data-toggle="modal"
                                        data-target="#modal-edit-topic" value="${rs.id}"><i class="fa fa-pencil"
                                            aria-hidden="true"></i></button>
                                </td>
                                <td style="width: 8%;"><button class="btn btn-danger btn-delete-topic" value="${rs.id}"><i class="fa fa-trash"
                                            aria-hidden="true"></i></button>
                                </td>
                            </tr>`;
                })
                $('.table-service-content').html(htmlRs);
            }
        });
    }
    if (name == "Bài đăng") {
        $('.post').show();
        $('.table-service-content').empty();
        $.ajax({
            url: urlPost,
            method: "GET",
            dataType: "json",
            success: function (res) {
                handlePost(res);
            }
        });
    }
});
// HANDLE TRANG POST
function handlePost(data) {
    var htmlRs = `<input type="text" name="id" value="${data.id}" readonly class="d-none">
                            <div class="post-form-group">
                                        <label for="title" class="post-form-label">Tiêu đề</label>
                                        <input name="title" type="text" class="post-form-input" value="${data.title}"/>
                                        </div>
                                        <div class="post-form-group">
                                            <label for="description" class="post-form-label">Miêu tả</label>
                                            <textarea name="description" class="post-form-area">${data.description}</textarea>
                                        </div>
                                        <div class="post-form-group">
                                        <label class="post-form-label w-100">Ảnh</label>
                                        <img class="mb-2 img-edit-post" style="height:50vh;"
                                            src="${data.image}">
                                        <input type="hidden" role="uploadcare-uploader" name="image"
                                                data-public-key="72791b85ba57a284b745" /><span class="text-warning fw-bold">Nếu thay
                                                đổi</span>
                                            </div>`;
    $('.content-post-form').html(htmlRs);
}
$(document).on('submit', '#post-form', function (e) {
    e.preventDefault();
    var formdata = new FormData(this);
    $.ajax({
        url: urlPost,
        type: "PUT",
        data: formdata,
        processData: false,
        contentType: false,
        cache: false,
        success: function (res) {
            handlePost(res);
            alert('success', 'Sửa thành công');
        },
        error: function (e) {
            console.log(e);
            alert('error', 'Xảy ra lỗi');
        }
    });
})
// DELETE
$(document).on('click', '.btn-delete-topic', function (e) {
    var psel = $(this).val();
    $.ajax({
        url: urlTopic + "/" + psel,
        method: 'DELETE',
        dataType: 'text',
        success: function (res) {
            $('.content-' + psel).remove();
            alert('success', res);
        }, error: function (e) {
            alert('error', 'Xảy ra lỗi');
        }
    })
})