function alert(icon, title) {
    Swal.fire({
        position: "top-end",
        icon: icon,
        title: title,
        showConfirmButton: false,
        timer: 1500,
    });
}

let payload = window.location.search;
const username = $('.username-name').text().toUpperCase().charAt(0);
var level = ['easy', 'normal', 'difficult'];
var arr = $('input[type="range"]');
for (let i = 0; i < arr.length; i++) {
    $('.val-' + level[i]).text($(arr[i]).val());
}
$(document).on('click', '.btn-totop', function () {
    $(window).scrollTop(0);
})
$(function () {
    $('.username-avatar').text($('.username-name').text().charAt(0).toUpperCase());
    $.ajax({
        url: 'http://localhost:8080/lesson/get-all',
        dataType: 'json',
        method: 'GET',
        success: function (res) {
            getAllLesson(res);
            $('.lesson-item:first-child').addClass('active')
            var id = parseInt($('.lesson-item:first-child').attr('id'));
            handleGetTopicByLesson(id);
        }
    });
    lastPost();
    if (payload != "") {
        getCodeByTopic(payload.split("=")[1], payload.split("=")[1])
    }
})

function getAllLesson(res) {
    var htmlRs = "";
    $.each(res, function (index, rs) {
        htmlRs += `<div class="simple-card lesson-item" id="${rs.id}">
                       <div class="simple-card-image">
                           <img src="${rs.image}" alt="" />
                       </div>
                       <div class="simple-card-content">
                          <h4 class="simple-card-title m-0">${rs.title}</h4>
                          <div class="simple-card-desc">${rs.description}</div>
                       </div>
                    </div>`;
    })
    $('.subnav-lesson').html(htmlRs);
}

function handleGetTopicByLesson(id) {
    $.ajax({
        url: 'http://localhost:8080/topic/topic-by-id?id=' + id,
        dataType: 'json',
        method: 'GET',
        success: function (res) {
            var htmlRs = "";
            if (res == "") {
                htmlRs += `<div class="simple-card topic-item" id="null">
                                <div class="simple-card-content">
                                    <h4 class="simple-card-title m-0">Chưa có đề tài</h4>
                                    <p class="simple-card-desc">Chờ cập nhật.</p>
                                </div>
                            </div>`;
            } else {
                $.each(res, function (index, rs) {
                    htmlRs += `<div class="simple-card topic-item" id="${rs.name}">
                                    <div class="simple-card-image">
                                        <img src="${rs.image}"/>
                                    </div>
                                    <div class="simple-card-content">
                                        <h4 class="simple-card-title m-0">${rs.name}</h4>                         
                                    </div>
                                </div>`;
                })
            }
            $('.subnav-topic').html(htmlRs);
        }
    })
}

function getPageByCode(code) {
    var htmlRs = "";
    $.ajax({
        url: 'http://localhost:8080/question/records?code=' + code,
        dataType: 'text',
        method: 'GET',
        async: false,
        success: function (res) {
            if (res != 0) {
                for (let i = 1; i <= res; i++) {
                    htmlRs +=
                        `<form class="d-inline m-0" method="post" action="/random/do-exam">
                             <input name="code" type="text" value="${code}"  class="d-none">
                             <input name="page" type="text" value="${i}"  class="d-none">
                             <button class="custom-btn btn-12 mt-2" 
                                  value="${res}" type="submit"><span>Click!</span><span>Làm phần ${i}</span>
                             </button>
                        </form>`;
                }
            } else {
                htmlRs += `<h4 class="d-inline mx-2">Hiện chưa có câu hỏi nào!</h4>`
            }
        }
    });
    return htmlRs;
}

function getTotalElements(code) {
    var elements = "";
    $.ajax({
        url: 'http://localhost:8080/question/elements?code=' + code,
        dataType: 'text',
        method: 'GET',
        async: false,
        success: function (res) {
            elements = res;
        }
    });
    return elements;
}

function getCodeByTopic(topic, payloadTopic) {
    $.ajax({
        url: 'http://localhost:8080/code-question?filter=topic=="' + topic + '"&page=0&sort=createdAt,desc',
        dataType: 'json',
        method: 'GET',
        success: function (res) {
            window.history.pushState({}, {}, "filter?topic=" + topic);
            var htmlRs = "";
            if (res.content == "") {
                if (typeof payloadTopic !== "undefined") {
                    htmlRs = `<h1 class="bg-white h-100 d-flex justify-content-center py-5">
                                    Không có mã đề nào của ` + payloadTopic.toUpperCase() + `!
                              </h1>`
                } else {
                    htmlRs = `<h1 class="bg-white h-100 d-flex justify-content-center py-5">Không có mã đề nào !</h1>`
                }
                $('.content-item').html(htmlRs);
                alert("error", "Vui lòng chọn lại")
                $(window).scrollTop(0)
            } else {
                $.each(res.content, function (index, rs) {
                    htmlRs += `<div class="card my-2">
                                        <div class="card-header d-flex justify-content-between">
                                            <div><i class="fa fa-hand-o-right" aria-hidden="true"></i>Ngày tạo : <span class="time-create">` + moment(rs.createdAt).fromNow() + `
                                                            </span>by ${rs.create_by}
                                            </div>
                                            <div class="fw-bold">Số câu hỏi : <span>` + getTotalElements(rs.id) + `</span></div>
                                        </div>`;
                    htmlRs += `<div class="card-body position-relative">
                                            <h3 class="card-title"><i class="fa fa-hand-o-right" aria-hidden="true"></i>Chủ đề ${rs.topic}</h3>
                                            <p class="m-0 mb-2 fw-bold"><i class="fa fa-hand-o-right" aria-hidden="true"></i>Mã đề <span class='code'>${rs.code}</span>
                                            </p>
                                            <p class="m-0 description">${rs.description}</p>
                                            <button class="btn-filter custom-btn btn-12 mt-2" value="${rs.code}" 
                                               data-bs-toggle="modal"
                                               data-bs-target="#modal-filter">
                                            <span>Click!</span><span>Lựa chọn</span>
                                            </button>
                                            <button class="position-absolute d-flex align-items-center view-select" 
                                             value="${rs.code}" data-bs-toggle="modal"
                                             data-bs-target="#modal-select">Xem thêm
                                                <span>
                                                    <i class="fa fa-hand-o-left" aria-hidden="true"></i>
                                                </span>
                                            </button>`;
                    htmlRs += getPageByCode(rs.code)
                    htmlRs += `</div></div>`;
                })
                $('.content-item').html(htmlRs);
                $(window).scrollTop(500)
            }
        }
    })
    window.history.pushState(null, null, '/filter?topic=' + topic);
}

function lastPost() {
    $.ajax({
        url: 'http://localhost:8080/code-question?page=0&size=3&sort=createdAt,desc',
        dataType: 'json',
        method: 'GET',
        success: function (res) {
            var htmlRs = "";
            $.each(res.content, function (index, rs) {
                htmlRs += `<div class="p-2 mb-2 rounded">
                           <div class="notice d-flex justify-content-between">
                                <span class="d-flex align-items-center span-new">New</span>
                                <p class="flex-grow-1 p-0 mx-2 my-0">Đề thi ${rs.topic} số ${rs.code}</p>
                           </div>`;
                htmlRs += `<div class="d-flex justify-content-between">
                            <button class="custom-btn btn-12 btn-filter mt-2" alue="${rs.code}" data-bs-toggle="modal"
                                                data-bs-target="#modal-filter"><span>Click!</span><span>Làm ngay</span>            
                            </button>
                            <small class="d-flex align-items-center">
                                ` + moment(rs.createdAt).fromNow() + `
                            </small>
                           </div>
                           </div>`;
            })
            $('.last-post').append(htmlRs);
        }
    })
}

function handleClickModalSelect(code, page) {
    $.ajax({
        url: 'http://localhost:8080/question/user/filter?code=' + code + '&page=' + page + '&sort=create_at,desc',
        dataType: 'json',
        method: 'GET',
        statusCode: {
            404: function (res) {
                console.log(res.responseJSON.message);
            }
        },
        success: function (res) {
            var htmlRs = "";
            const totalPage = res.totalPage;
            const currentPage = res.currentPage;
            if (res.content != "") {
                $.each(res.content, function (index, rs) {
                    htmlRs += ` <div class="form-group">
                                    <input type="checkbox" id="${rs.id}" value="${rs.id}">
                                    <label style="display: -webkit-box" for="${rs.id}">${rs.name_question}</label>
                                </div>`;
                })
                $('.form-select-filter-content').append(htmlRs);
            }
            if (res.content == "") {
                htmlRs += `<h3>Chưa có câu hỏi</h3>`;
                $('.form-select-filter-content').append(htmlRs);
            }
            if (currentPage < totalPage - 1) {
                $('.pagi-select').html(`
                                            <button class="custom-btn btn-2 btn-extend p-0" code="${code}"
                                            value="${currentPage + 1}">Xem thêm</button>
                                            `)
            }
            if (currentPage == totalPage - 1) {
                $('.pagi-select').empty();
            }
        }
    })
}

$(document).on('click', '.view-select', function () {
    const code = $(this).val();
    $('#message').val(code)
    handleClickModalSelect(code, 0)
})
$(document).on('click', '.btn-extend', function (e) {
    e.preventDefault();
    const code = $(this).attr('code')
    const page = $(this).val()
    handleClickModalSelect(code, page)
})
$(document).on('click', '.btn-select-close', function () {
    $('.form-select-filter-content').empty()
    $('.pagi-select').empty()
})
$(document).on('click', '.btn-submit', function (e) {
    // e.preventDefault()
    let code = $('#message').val()
    var selectFilter = "";
    var cont = $(document).find('input[type="checkbox"]:checked')
    for (let i = 0; i < cont.length; i++) {
        selectFilter += $(cont[i]).val() + "&";
    }
    selectFilter = selectFilter.substring(0, selectFilter.length - 1)
    code = code + '$' + selectFilter
    $('#message').val(code)
    $('#form-select-filter').submit();
})
$(document).on('input', 'input[type="range"]', function () {
    var value = $(this).val();
    $('.val-' + $(this).attr('name')).text(value);
})
//SEARCH,CHANGE LESSON-ITEM
$(document).on('click', '.lesson-item', function () {
    $('.simple-card').removeClass('active');
    const id = parseInt($(this).attr('id'));
    $(this).addClass('active');
    handleGetTopicByLesson(id);
});
$(document).on('click', '.topic-item', function () {
    $('.topic-item').removeClass('active')
    $(this).addClass('active')
    getCodeByTopic($(this).attr('id'));
})

// $(document).on('click', '.btn-search-code', function () {
//     let topic = $('.topic-item').hasClass('active');
//     console.log(topic)
//     // getCodeByTopic(topic.attr('id'));
// })
$(document).on('click', '.btn-filter', function (e) {
    e.preventDefault();
    // $(".modal-filter").modal('show');
    $(".modal-filter-code").val($(this).val());
    // window.history.pushState(null, null, '/new');
});
$(document).on('click', '.menu-user', function () {
    $(this).addClass('active');
    $.ajax({
        url: 'http://localhost:8080/result/result-by-username?username=' + $('.username-name').text(),
        dataType: 'json',
        method: 'GET',
        statusCode: {
            404: function (res) {
                console.log(res.responseJSON.status);
            }
        },
        success: function (res, textStatus, XHR) {
            var htmlRs = "";
            $.each(res, function (index, rs) {
                htmlRs += `<tr>
                                    <th class="table-primary" scope="row">${rs.code}</th>
                                    <td>${rs.name}</td>
                                    <td>${rs.number_question}</td>
                                    <td>${rs.number_point}</td>
                                    </tr>`;
            })
            $('.table-result-user').html(htmlRs);
        }
    })
});
$(document).on('click', '.btn-close-user', function () {
    $('.menu-user').removeClass('active')
});
$(document).on('click', '.btn-search', function (e) {
    e.preventDefault()
    var topic = $('.value-search').val()
    getCodeByTopic(topic)
    $('.search-content').hide()
})
$(document).on('keyup', '.value-search', function () {
    var searchCnt = $('.search-content')
    searchCnt.hide();
    var data = $(this).val();
    if (data.length > 0) {
        $.ajax({
            url: 'http://localhost:8080/code-question?filter=topic==' + data + '*',
            dataType: 'json',
            method: 'GET',
            success: function (res) {
                var htmlRs = "";
                if (res.content == "") {
                    htmlRs = `<h4 class='p-3 m-0 text-dark fw-500' style='font-style:normal;'>Không có mã đề cho từ khóa này!</h4>`
                    searchCnt.html(htmlRs);
                } else {
                    $.each(res.content, function (index, rs) {
                        htmlRs += `<div class="search-item dropdown-item rounded my-1">
                                            <button class="btn-filter search-item-value" value="${rs.code}"
                                            data-bs-toggle="modal" data-bs-target="#modal-filter">
                                                <span class="text-dark">Đề thi ${rs.topic} số 
                                                    <span class="badge bg-primary rounded-pill">${rs.code}</span>
                                                </span>
                                            </button>
                                         </div>`;
                    })
                    htmlRs += `<div class="btn-close btn-search-close" style="cursor: pointer; padding: 0 18px 10px 18px;"></div>`;
                    searchCnt.html(htmlRs);
                }
            }
        });
        searchCnt.show();
    }
})
$(document).on('click', '.btn-search-close', function () {
    $('.search-content').hide()
})

