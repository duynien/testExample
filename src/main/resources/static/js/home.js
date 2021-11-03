var urlPost = "http://localhost:8080/post";
var urlLesson = "http://localhost:8080/lesson";
var urlTopic = "http://localhost:8080/topic";
var urlAsk = 'http://localhost:8080/ask'
var urlCmt = 'http://localhost:8080/comment'
var urlResult = 'http://localhost:8080/result'
var stompClient = null;
var username = $('.username-name').text();
const home = $(".main-home");
const qa = $(".q-a");
const askContent = $('.ask-content');
const payload = window.location.search.slice(1);

// HANDLE MODAL FILTER
function alert(icon, title) {
    Swal.fire({
        position: "top-end",
        icon: icon,
        title: title,
        showConfirmButton: false,
        timer: 1500,
    });
}

var level = ["easy", "normal", "difficult"];
var arr = $('input[type="range"]');
$(document).on("click", ".btn-filter", function () {
    $(".modal-filter-code").val($(this).val());
});
for (let i = 0; i < arr.length; i++) {
    console.log();
    $(".val-" + level[i]).text($(arr[i]).val());
}
$(document).on("input", 'input[type="range"]', function () {
    var value = $(this).val();
    $(".val-" + $(this).attr("name")).text(value);
});
//REMDER LOAD TRANG HOME
$(function () {
    if (payload === 'qa=tab') {
        home.slideToggle();
        qa.slideToggle();
        handleGetAsk(0);
    }
    if (payload === 'user=tab') {
        handleGetResult();
        $('#modal-user').modal('show');
    }
    if (payload === '') {
        handleGetWelcome();
        lastPost();
        getLessonIsHot();
        getTopicRandom(5);
    }
    $('.username-avatar').text($('.username-name').text().charAt(0).toUpperCase());
    connect();
});

function handleGetWelcome() {
    $.ajax({
        url: urlPost,
        method: "GET",
        dataType: "json",
        success: function (rs) {
            var htmlPost = `<h1 class="mb-4 text-dark elegantshadow" 
                                style="font-weight: bold;text-indent:40px;">${rs.title}</h1>
                                <span class="text-welcome text-dark"
                                style="text-indent: 40px; text-align: justify; 
                                font-size: 22px;font-style: italic;">${rs.description}</span>
                             </div>`;
            var htmlImg = `<img src="${rs.image}" class="h-100 w-100" alt="">`;
            $(".welcome-title").html(htmlPost);
            $(".welcome-img").html(htmlImg);
        },
    });
}

function handleGetResult() {
    $.ajax({
        url:
            "http://localhost:8080/result/result-by-username?username=" + username,
        dataType: "json",
        method: "GET",
        statusCode: {
            404: function (res) {
                $(".table-result-user").html(`<p class="text-light px-3">${res.responseJSON.message}</p>`);
                return false;
            },
        },
        success: function (res, textStatus, XHR) {
            var htmlRs = "";
            $.each(res, function (index, rs) {
                htmlRs += `<tr class="text-white">
                           <td>${rs.code}</td>
                           <td>${rs.name}</td>
                           <td>${rs.number_question}</td>
                           <td>${rs.number_point}</td>
                           <td><a class="nav-link" href="/filter?topic=${rs.name}">
                                Làm lại<i class="fa fa-pencil-square-o mx-1" aria-hidden="true"></i></a>
                           </td>
                           </tr>`;
            });
            $(".table-result-user").html(htmlRs);
        },
    });
}

function handleGetResultByAscOrDesc(rule) {
    $.ajax({
        url: urlResult + '/result-username/' + rule + '?username=username=="' + username + '"',
        dataType: "json",
        method: "GET",
        statusCode: {
            404: function (res) {
                $(".table-result-user").html(`<p>${res.responseJSON.message}</p>`);
            },
        },
        success: function (res, textStatus, XHR) {
            var htmlRs = "";
            $.each(res, function (index, rs) {
                htmlRs += `<tr class="text-white">
                           <td>${rs.code}</td>
                           <td>${rs.name}</td>
                           <td>${rs.number_question}</td>
                           <td>${rs.number_point}</td>
                           <td><a class="nav-link" href="/filter?topic=${rs.name}">
                                Làm lại<i class="fa fa-pencil-square-o mx-1" aria-hidden="true"></i></a>
                           </td>
                           </tr>`;
            });
            $(".table-result-user").html(htmlRs);
        },
    });
}

function lastPost() {
    $.ajax({
        url: "http://localhost:8080/code-question?page=0&size=3&sort=createdAt,desc",
        dataType: "json",
        method: "GET",
        success: function (res) {
            var htmlRs = "";
            $.each(res.content, function (index, rs) {
                htmlRs += `<div class="p-0 mb-2 rounded">
                           <div class="notice d-flex justify-content-between">
                                <span class="d-flex align-items-center span-new">New</span>
                                <p class="flex-grow-1 p-0 mx-2 my-0">Đề thi ${rs.topic} số ${rs.code}</p>
                           </div>`;
                htmlRs += `<div class="d-flex justify-content-between">
                            <button class="btn-filter custom-btn btn-12 mt-2" value="${rs.code}" data-bs-toggle="modal"
                                                data-bs-target="#modal-filter"><span>Click!</span><span>Làm ngay</span>
                            </button>
                            <small class="d-flex align-items-center">
                                ` + moment(rs.createdAt).fromNow() + `
                            </small>
                           </div>
                           </div>`;
            });
            $(".last-post-content").append(htmlRs);
        },
    });
}

function getLessonIsHot() {
    $.ajax({
        url: urlLesson + "/get-all",
        method: "GET",
        dataType: "json",
        success: function (res) {
            var htmlRs = "";
            $.each(res, function (index, rs) {
                htmlRs += `<div class="item-owl">
                            <div class="ishot g-0 border overflow-auto rounded flex-column mb-4 shadow-sm position-relative">
                                <div class="img-ishot">
                                  <img src="${rs.image}" alt="" class="w-100 h-100">
                                </div>
                                <div class="p-4 d-flex flex-column bg-white">
                                  <h3 class="mb-0">${rs.title}</h3>
                                  <div class="card-text mb-auto">${rs.description}</div>
                                </div>
                            </div>
                            </div>`;
            });
            $(".lesson-hot-content").html(htmlRs);
            $('.owl-carousel').owlCarousel({
                loop: true,
                margin: 15,
                autoplay: true,
                autoplayTimeout: 7000,
                responsive: {
                    0: {
                        items: 1
                    },
                    600: {
                        items: 2
                    },
                    1000: {
                        items: 2
                    }
                }
            })
        },
    });
}

function getTopicRandom(limit) {
    $.ajax({
        url: urlTopic + "/get-limit?limit=" + limit,
        method: "GET",
        dataType: "json",
        success: function (res) {
            var htmlRs = "";
            $.each(res, function (index, rs) {
                htmlRs += `<div class="avatar-flash" name='${rs.name}'>
                    <a href="/filter?topic=${rs.name}"><img
                    src="${rs.image}"
                    style="width: 100%; height: 100%;" alt="" class="image-cover avatar-image"></a>
                  </div>`;
            });
            htmlRs += `<a class="custom-btn btn-10 nav-link" style="font-size: 13px" href="/filter">Xem thêm</a>`;
            $(".avatar-course").html(htmlRs);
        },
    });
}

function handleGetAsk(page) {
    $.ajax({
        url: urlAsk + '?page=' + page + '&sort=createAt,desc',
        method: "GET",
        dataType: "json",
        success: function (res) {
            var htmlRs = ""
            $.each(res.content, function (index, rs) {
                htmlRs += `<div class="card p-0 col-5">
                            <div class="card-header d-flex justify-content-between align-items-center">
                                <div>Đăng bởi <span>${rs.username}</span></div>
                                <h5>
                                    <span class="badge rounded-pill bg-info text-dark">
                                      ${rs.commentListSize} câu trả lời
                                    </span>
                                </h5>
                            </div>
                            <div class="card-body">
                                <h4 class="card-title fw-bold">${rs.purpose}</h4>
                                <div class="card-text description">${rs.content}</div>
                                <div class="d-flex justify-content-between align-items-center mt-3">
                                    <h5>
                                      <span class="badge rounded-pill bg-white text-dark">` + moment(rs.createAt).fromNow() + `
                                      </span>
                                    </h5>
                                    <button class="custom-btn btn-12 btn-detail-ask" 
                                            value="${rs.id}" data-bs-toggle="modal"
                                            data-bs-target="#staticBackdrop"><span>Click</span><span>Chi tiết</span></button>
                                </div>
                            </div>
                        </div>`
            })
            if (res.currentPage < (res.totalPage - 1)) {
                var htmlBtn = `<button class="btn-extend-ask custom-btn btn-12" value="${res.currentPage + 1}">
                                    <span>Click</span><span>Xem thêm</span></button>`;
                $('.btn-ask-content').html(htmlBtn);
            } else {
                $('.btn-ask-content').html("");
            }
            askContent.append(htmlRs);
        }
    })
}

function handleGetAskByUsername() {
    $.ajax({
        url: urlAsk + '?size=20&filter=username=="' + username + '"&sort=createAt,desc',
        method: "GET",
        dataType: "json",
        success: function (res) {
            var htmlRs = ""
            $.each(res.content, function (index, rs) {
                htmlRs += `<div class="card p-0 col-5">
                            <div class="card-header d-flex justify-content-between align-items-center">
                                <div>Đăng bởi <span>${rs.username}</span></div>
                                <h5>
                                    <span class="badge rounded-pill bg-info text-dark">
                                      ${rs.commentListSize} câu trả lời
                                    </span>
                                </h5>
                            </div>
                            <div class="card-body">
                                <h4 class="card-title fw-bold">${rs.purpose}</h4>
                                <div class="card-text description">${rs.content}</div>
                                <div class="d-flex justify-content-between align-items-center mt-3">
                                    <h5>
                                      <span class="badge rounded-pill bg-white text-dark">` + moment(rs.createAt).fromNow() + `
                                      </span>
                                    </h5>
                                    <button class="custom-btn btn-12 btn-detail-ask" 
                                            value="${rs.id}" data-bs-toggle="modal"
                                            data-bs-target="#staticBackdrop"><span>Click</span><span>Chi tiết</span></button>
                                </div>
                            </div>
                        </div>`
            })
            askContent.html(htmlRs);
            $('.btn-ask-content').html("");
        }
    })
}

$(document).on("click", '.nav-user', function () {
    if ($('.table-result-user').text().trim() == "") {
        handleGetResult();
    }
    window.history.pushState("info", "Cá nhân", "?user=tab");
});
$(document).on('click', '.filter-desc', function () {
    handleGetResultByAscOrDesc('desc')
})
$(document).on('click', '.filter-asc', function () {
    handleGetResultByAscOrDesc('asc')
})
$(document).on('click', '.nav-qa', function (e) {
    e.preventDefault();
    // $(this).addClass('active')
    if (askContent.text().trim() === "") {
        handleGetAsk(0);
    }
    home.hide();
    qa.show();
    window.history.pushState("q&a", "Câu hỏi", "?qa=tab");
});
$(document).on('click', '.dropdown__item', function (e) {
    qa.hide();
    home.show();
    window.history.pushState("q&a", "Home", "/");
});
$(document).on('submit', '#modal-comment', function (e) {
    e.preventDefault();
    var content = tinymce.get("commentAns").getContent();
    if (content === "") {
        alert('error', 'Chưa nhập bình luận');
    } else {
        var formData = new FormData(this);
        formData.append('username', username.toUpperCase());
        $.ajax({
            type: "POST",
            url: urlCmt,
            data: formData,
            enctype: "multipart/form-data",
            processData: false,
            contentType: false,
            cache: false,
            success: function (rs) {
                var htmlCmt = `<div class="comment-item">
                                  <h6 class="comment-username">${rs.username}</h6>
                                  <p>${rs.content}</p>
                                  <small>` + moment(rs.create_at).fromNow() + `</small>
                                </div>`;
                if ($('.modal-comment-content').text() === 'Chưa có câu trả lời') {
                    $('.modal-comment-content').html(htmlCmt);
                } else {
                    $('.modal-comment-content').prepend(htmlCmt);
                }
                alert('success', 'Bình luận thành công');
            }
        })
    }
});
$(document).on('submit', '#post-question', function (e) {
    e.preventDefault();
    var content = tinymce.get("post-question__answer").getContent();
    if (content === "") {
        alert('error', 'Chưa nhập dữ liệu');
    } else {
        var formData = new FormData(this);
        formData.append('username', username.toUpperCase());
        $.ajax({
            type: "POST",
            url: urlAsk,
            data: formData,
            enctype: "multipart/form-data",
            processData: false,
            contentType: false,
            cache: false,
            success: function (rs) {
                var htmlRs = `<div class="card p-0 col-5">
                            <div class="card-header d-flex justify-content-between align-items-center">
                                <div>Đăng bởi <span>${rs.username}</span></div>
                                <h5>
                                    <span class="badge rounded-pill bg-info text-dark">
                                      0 câu trả lời
                                    </span>
                                </h5>
                            </div>
                            <div class="card-body">
                                <h4 class="card-title fw-bold">${rs.purpose}</h4>
                                <div class="card-text description">${rs.content}</div>
                                <div class="d-flex justify-content-between align-items-center mt-3">
                                    <h5>
                                      <span class="badge rounded-pill bg-white text-dark">` + moment(rs.createAt).fromNow() + `
                                      </span>
                                    </h5>
                                    <button class="custom-btn btn-12 btn-detail-ask" 
                                            value="${rs.id}" data-bs-toggle="modal"
                                            data-bs-target="#staticBackdrop"><span>Click</span><span>Chi tiết</span></button>
                                </div>
                            </div>
                        </div>`;
                askContent.prepend(htmlRs);
            },
        });
        alert('success', 'Đăng thành công');
        $("#post-question").slideToggle();
    }
});
$(document).on('click', '.btn-extend-ask', function (e) {
    const page = $(this).val()
    handleGetAsk(page)
})
$(document).on('click', '.btn-open-my_ask', function () {
    handleGetAskByUsername();
})
$(document).on('click', '.btn-detail-ask', function () {
    $.ajax({
        url: urlAsk + '/find/id?id=' + $(this).val(),
        method: "GET",
        dataType: "json",
        success: function (res) {
            var htmlRs = `<h3 class="ques-title">
                            <i class="fa fa-question-circle" aria-hidden="true"></i>
                            ${res.purpose}
                           </h3>
                           <div class="ques-description">${res.content}</div>`;
            var htmlCmt = "";
            if (res.commentList != "") {
                $.each(res.commentList, function (index, rs) {
                    htmlCmt += `<div class="comment-item">
                                  <h6 class="comment-username">${rs.username}</h6>
                                  <p>${rs.content}</p>
                                  <small>` + moment(rs.create_at).fromNow() + `</small>
                                </div>`;
                })
            }
            if (res.commentList == "") {
                htmlCmt = `<p>Chưa có câu trả lời</p>`;
            }
            $('.modal-comment-askid').val(res.id);
            $('.modal-comment-content').html(htmlCmt);
            $('.modal-ask').html(htmlRs);
        }
    })
})
$(document).on('click', '.btn-open-post__question', function () {
    $("#post-question").slideToggle();
});


//SOCKET
function connect(event) {
    if (username) {
        var socket = new SockJS("/websocket");
        stompClient = Stomp.over(socket);
        stompClient.connect({}, onConnected);
        stompClient.debug = () => {};
        // stompClient.connect({}, onConnected, onError);
    }
}

function onConnected() {
    var chatMessage = {
        sender: username.toUpperCase(),
        content: 'đã truy cập website',
        type: "JOIN",
    };
    stompClient.send(
        "/app/chat.register",
        {},
        JSON.stringify(chatMessage)
    );
}

function logout(event) {
    if (stompClient) {
        var chatMessage = {
            sender: username.toUpperCase(),
            content: "đã thoát ứng dụng",
            type: "LEAVE",
        };
        stompClient.send("/app/chat.send", {}, JSON.stringify(chatMessage));
    }
}

$(document).on('click', '#logout', function () {
    logout();
})

function reload(event) {
    if (stompClient) {
        var chatMessage = {
            sender: username.toUpperCase(),
            content: "đã tải lại trang home",
            type: "RELOAD",
        };
        stompClient.send("/app/chat.send", {}, JSON.stringify(chatMessage));
    }
}

$(window).on('unload', function (e) {
    reload()
    localStorage.setItem('load', 'unload')
})