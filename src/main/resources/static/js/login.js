const inputs = document.querySelectorAll(".input");
$(function () {
    $('#form_signup').hide();
    $(document).on('click', '#go_signup', function (e) {
        $('#form_signup').slideToggle(1000);
        $('#form_login').slideToggle(1000);
        $('.alert-mess').hide();
    });
    $(document).on('click', '#have_account', function (e) {
        $('#form_login').slideToggle(1000);
        $('#form_signup').slideToggle(1000);
    });
});
function addcl() {
    let parent = this.parentNode.parentNode;
    parent.classList.add("focus");
}
function remcl() {
    let parent = this.parentNode.parentNode;
    if (this.value == "") {
        parent.classList.remove("focus");
    }
}
inputs.forEach((input) => {
    input.addEventListener("focus", addcl);
    input.addEventListener("blur", remcl);
});

if ($(".input").first().val() != "") {
    var parent = $(".input").first().parent().parent();
    parent.addClass("focus");
}
$(document).on('blur', '.email', function () {
    var parent = $(this).parents().eq(1);
    var filter = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
    if (!filter.test($(this).val())) {
        parent.addClass('errorvalue');
        $('.error').html("Nhập đúng định dạng email");
        $('.error').show();
    }
    else {
        $.ajax({
            url: "http://localhost:8080/auth/email?email=" + $(this).val(),
            type: 'GET',
            dataType: 'json',
            success: function (res) {
                if (res.status == false) {
                    parent.addClass('errorvalue');
                    $('.error').html(res.content);
                    $('.error').show();
                }
                else {
                    parent.removeClass('errorvalue');
                    $('.error').hide();
                }
            }, error: function (e) {
                console.log(e);
            }
        });
    }
})
$(document).on('blur', '.username', function () {
    var parent = $(this).parents().eq(1);
    $.ajax({
        url: "http://localhost:8080/auth/username?username=" + $(this).val(),
        type: 'GET',
        dataType: 'json',
        success: function (res) {
            if (res.status == false) {
                parent.addClass('errorvalue');
                $('.error').html(res.content);
                $('.error').show();
            }
            else {
                parent.removeClass('errorvalue');
                $('.error').hide();
            }
        }, error: function (e) {
            console.log(e);
        }
    });
})
