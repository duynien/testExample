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
var username = null;
$('canvas').css('height', $(document).height() + 50);
var size = $('.answer-verify').length;
// function loading(callback) {
//     var time = 0;
//     var running = setInterval(frame, 1000);
//
//     function frame() {
//         if (time == 2) {
//             $('.loading').hide();
//             callback();
//             clearInterval(running);
//         } else {
//             $('.loading').show();
//             time++;
//         }
//     }
// }

$(function () {
    var timer = ($('.time').attr('value') * 60), minute = $('.minutes'), second = $('.seconds');
    const dem = setInterval(function () {
        minutes = parseInt(timer / 60, 10);
        seconds = parseInt(timer % 60, 10);

        minutes = minutes < 10 ? "0" + minutes : minutes;
        seconds = seconds < 10 ? "0" + seconds : seconds;

        minute.text(minutes);
        second.text(seconds);

        if (timer-- == 0) {
            $("#form_submit").submit();
            $('.content').remove();
            clearInterval((dem));
        }
    }, 1000);
});
$(document).on('click', 'input[type="radio"]', function () {
    var location = this.name.split("-")[1];
    $('#ans-' + location).text($(this).val());
    $('#ans-' + location).parents().eq(0).removeClass('text-danger');
    $('#ans-' + location).parents().eq(0).addClass('text-success');
});

function insertResult(formDataResult) {
    $.ajax({
        url: "http://localhost:8080/result",
        type: "POST",
        data: formDataResult,
        enctype: "multipart/form-data",
        processData: false,
        contentType: false,
        cache: false,
        success: function (res) {
            alert('success', 'Hoàn thành bài thi');
        }, error: function (e) {
            console.log(e);
        }
    });
}

$(document).on("submit", "#form_submit", function (e) {
    e.preventDefault();
    var formDataAnswer = new FormData();
    var answers = size + "$";
    for (let i = 0; i < size; i++) {
        let location = $('.answer-verify')[i].id.split('-')[1];
        answers += location + "-";
        answers += $(`#ans-${location}`).text() == "" ? "No" : $(`#ans-${location}`).text();
        answers += "&";
    }
    formDataAnswer.append("answer", answers.substr(0, answers.length - 1));
    console.log(formDataAnswer.get('answer'));
    $.ajax({
        url: "http://localhost:8080/answer/verify-answer",
        type: "POST",
        data: formDataAnswer,
        enctype: "multipart/form-data",
        processData: false,
        contentType: false,
        cache: false,
        statusCode: {
            404: function (res) {
                alert('error', res.responseJSON.message);
                setInterval(function () {
                    history.back();
                }, 3000);
            }
        },
        success: function (res) {
            var rs = res.content;
            var html = "";
            html += `<h5 class="text-dark"><i class="far fa-thumbs-up"></i>Số câu đúng : ${res.number_success}</h5>
                     <h5 class="text-dark"><i class="far fa-gem"></i>Số điểm : ${res.point}</h5>
                     <div class="d-flex align-items-center mt-2">
                          <a class="btn btn-outline-primary" href="/">Trang chủ</a>
                          <button class="btn btn-primary m-3" onclick="history.back();">Làm tiếp
                          </button>
                     </div>`;
            html += `<h3 class="text-dark my-2">Một số câu sai trong bài thi và đáp án :</h3>`;
            $.each(rs, function (index, cnt) {
                html += `<h5 class="bg-white text-dark rounded px-3 py-2">${cnt}</h5>`;
            })
            var formDataResult = new FormData();
            formDataResult.append('codeQues', $('#code_ques').text());
            formDataResult.append('name', $('#name_course').text());
            formDataResult.append('numberPoint', res.point);
            formDataResult.append('numberQuestion', res.number_question);
            insertResult(formDataResult);
            $(window).scrollTop(0);
            $('#point').html(html);
            $('.content').remove();
            connect();
        }, error: function (e) {
            console.log(e);
        }
    });
});

function connect() {
    username = $('#username-cnt').text();
    var socket = new SockJS("/websocket");
    stompClient = Stomp.over(socket);
    stompClient.debug = () => {}
    stompClient.connect({}, function () {
        var chatMessage = {
            sender: username.toUpperCase(),
            content: 'đã thi đề mã ' + $('#code_ques').text(),
            type: "DOEXAM",
        };
        stompClient.send("/app/chat.send", {}, JSON.stringify(chatMessage));
    });
}