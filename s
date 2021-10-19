warning: LF will be replaced by CRLF in src/main/java/com/java/demo_ttcscn/DemoTtcscnApplication.java.
The file will have its original line endings in your working directory
[1mdiff --git a/src/main/java/com/java/demo_ttcscn/DemoTtcscnApplication.java b/src/main/java/com/java/demo_ttcscn/DemoTtcscnApplication.java[m
[1mindex 4ca147f..0904e44 100644[m
[1m--- a/src/main/java/com/java/demo_ttcscn/DemoTtcscnApplication.java[m
[1m+++ b/src/main/java/com/java/demo_ttcscn/DemoTtcscnApplication.java[m
[36m@@ -5,22 +5,28 @@[m [mimport org.springframework.boot.SpringApplication;[m
 import org.springframework.boot.autoconfigure.SpringBootApplication;[m
 import org.springframework.context.annotation.Bean;[m
 import org.springframework.data.jpa.repository.config.EnableJpaAuditing;[m
[31m-import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;[m
[31m-import org.springframework.security.crypto.password.PasswordEncoder;[m
[32m+[m
[32m+[m[32mimport java.text.DecimalFormat;[m
 [m
 @SpringBootApplication[m
 @EnableJpaAuditing[m
 public class DemoTtcscnApplication {[m
 [m
[31m-    public static void main(String[] args) {[m
[31m-        SpringApplication.run(DemoTtcscnApplication.class, args);[m
[31m-    }[m
[31m-    @Bean[m
[31m-    public PasswordEncoder encoder() {[m
[31m-        return new BCryptPasswordEncoder();[m
[31m-    }[m
[31m-    @Bean[m
[31m-    public ModelMapper modelMapper() {[m
[31m-        return new ModelMapper();[m
[31m-    }[m
[32m+[m[32m  public static void main(String[] args) {[m
[32m+[m[32m    SpringApplication.run(DemoTtcscnApplication.class, args);[m
[32m+[m[32m  }[m
[32m+[m
[32m+[m[32m  //  @Bean[m
[32m+[m[32m  //  public PasswordEncoder encoder() {[m
[32m+[m[32m  //    return new BCryptPasswordEncoder();[m
[32m+[m[32m  //  }[m
[32m+[m[32m  @Bean[m
[32m+[m[32m  public DecimalFormat df() {[m
[32m+[m[32m    return new DecimalFormat("0.00");[m
[32m+[m[32m  }[m
[32m+[m
[32m+[m[32m  @Bean[m
[32m+[m[32m  public ModelMapper modelMapper() {[m
[32m+[m[32m    return new ModelMapper();[m
[32m+[m[32m  }[m
 }[m
[1mdiff --git a/src/main/java/com/java/demo_ttcscn/config/security/WebSecurityConfig.java b/src/main/java/com/java/demo_ttcscn/config/security/WebSecurityConfig.java[m
[1mindex fc3277f..ee4b0ef 100644[m
[1m--- a/src/main/java/com/java/demo_ttcscn/config/security/WebSecurityConfig.java[m
[1m+++ b/src/main/java/com/java/demo_ttcscn/config/security/WebSecurityConfig.java[m
[36m@@ -96,7 +96,6 @@[m [mpublic class WebSecurityConfig extends WebSecurityConfigurerAdapter {[m
         .sessionRegistry(sessionRegistry());[m
   }[m
 [m
[31m-  // Work around https://jira.spring.io/browse/SEC-2855[m
   @Bean[m
   public SessionRegistry sessionRegistry() {[m
     SessionRegistry sessionRegistry = new SessionRegistryImpl();[m
[1mdiff --git a/src/main/java/com/java/demo_ttcscn/services/model/impl/AnswerServiceImpl.java b/src/main/java/com/java/demo_ttcscn/services/model/impl/AnswerServiceImpl.java[m
[1mindex 6bd03a7..ac2f33b 100644[m
[1m--- a/src/main/java/com/java/demo_ttcscn/services/model/impl/AnswerServiceImpl.java[m
[1m+++ b/src/main/java/com/java/demo_ttcscn/services/model/impl/AnswerServiceImpl.java[m
[36m@@ -2,9 +2,11 @@[m [mpackage com.java.demo_ttcscn.services.model.impl;[m
 [m
 import com.java.demo_ttcscn.enitities.dto.AnswerDto;[m
 import com.java.demo_ttcscn.enitities.model.Answer;[m
[32m+[m[32mimport com.java.demo_ttcscn.enitities.model.Question;[m
 import com.java.demo_ttcscn.enitities.result.AnswerResponse;[m
 import com.java.demo_ttcscn.exception.NotFoundException;[m
 import com.java.demo_ttcscn.repositories.AnswerRepository;[m
[32m+[m[32mimport com.java.demo_ttcscn.repositories.QuestionRepository;[m
 import com.java.demo_ttcscn.repositories.base.BaseRepository;[m
 import com.java.demo_ttcscn.services.base.BaseServiceImpl;[m
 import com.java.demo_ttcscn.services.model.AnswerService;[m
[36m@@ -17,7 +19,9 @@[m [mimport java.util.List;[m
 [m
 @Service[m
 public class AnswerServiceImpl extends BaseServiceImpl<Answer, AnswerDto> implements AnswerService {[m
[32m+[m[32m  @Autowired DecimalFormat df;[m
   @Autowired private AnswerRepository answerRepository;[m
[32m+[m[32m  @Autowired private QuestionRepository questionRepository;[m
 [m
   @Override[m
   protected Class<Answer> classEntity() {[m
[36m@@ -36,7 +40,6 @@[m [mpublic class AnswerServiceImpl extends BaseServiceImpl<Answer, AnswerDto> implem[m
 [m
   @Override[m
   public AnswerResponse submitAnswer(String answerRequest) {[m
[31m-    DecimalFormat df = new DecimalFormat("0.00");[m
     List<String> content = new ArrayList<>();[m
     float point = 0;[m
     int numberQuestionCorrect = 0;[m
[36m@@ -49,23 +52,26 @@[m [mpublic class AnswerServiceImpl extends BaseServiceImpl<Answer, AnswerDto> implem[m
       pointEveryQuestion = (double) 10 / numberQuestion;[m
       for (int i = 0; i < verifyAnswers.length; i++) {[m
         String answer[] = verifyAnswers[i].split("-");[m
[31m-        Answer answerResult = answerRepository.getAnswerByQuestion(Integer.parseInt(answer[0]));[m
[31m-        if (answer[1].equals(answerResult.getCorrect_ans())) {[m
[31m-          numberQuestionCorrect++;[m
[31m-          point += pointEveryQuestion;[m
[31m-        } else {[m
[31m-          String nameQuestion = answerRepository.getNameByQuestion(Integer.parseInt(answer[0]));[m
[31m-          String correctAnswer = answerResult.getCorrect_ans();[m
[32m+[m[32m        Question question = questionRepository.findById(Integer.parseInt(answer[0])).get();[m
[32m+[m[32m        //        Answer answerResult =[m
[32m+[m[32m        // answerRepository.getAnswerByQuestion(Integer.parseInt(answer[0]));[m
[32m+[m[32m        String correctAnswer = question.getAnswer().getCorrect_ans();[m
[32m+[m[32m        if (answer[1].equals("No") || !answer[1].equals(correctAnswer)) {[m
[32m+[m[32m          String nameQuestion = question.getName_question();[m
           if (correctAnswer.equals("A")) {[m
[31m-            content.add((i + 1) +"."+nameQuestion+ "\nA." + answerResult.getAnsw_A());[m
[32m+[m[32m            content.add((i + 1) + "." + nameQuestion + "\nA." + question.getAnswer().getAnsw_A());[m
           } else if (correctAnswer.equals("B")) {[m
[31m-            content.add((i + 1) +"."+nameQuestion+ "\nB." + answerResult.getAnsw_B());[m
[32m+[m[32m            content.add((i + 1) + "." + nameQuestion + "\nB." + question.getAnswer().getAnsw_B());[m
           } else if (correctAnswer.equals("C")) {[m
[31m-            content.add((i + 1) +"."+nameQuestion+ "\nC." + answerResult.getAnsw_C());[m
[32m+[m[32m            content.add((i + 1) + "." + nameQuestion + "\nC." + question.getAnswer().getAnsw_C());[m
           } else if (correctAnswer.equals("D")) {[m
[31m-            content.add((i + 1) +"."+nameQuestion+ "\nD." + answerResult.getAnsw_D());[m
[32m+[m[32m            content.add((i + 1) + "." + nameQuestion + "\nD." + question.getAnswer().getAnsw_D());[m
           }[m
         }[m
[32m+[m[32m        if (answer[1].equals(correctAnswer)) {[m
[32m+[m[32m          numberQuestionCorrect++;[m
[32m+[m[32m          point += pointEveryQuestion;[m
[32m+[m[32m        }[m
       }[m
       answerResponse.setNumberQuestionCorrect(numberQuestionCorrect);[m
       answerResponse.setPoint(df.format(point));[m
[1mdiff --git a/src/main/java/com/java/demo_ttcscn/services/model/impl/PostService.java b/src/main/java/com/java/demo_ttcscn/services/model/impl/PostService.java[m
[1mindex 2b83062..a90d744 100644[m
[1m--- a/src/main/java/com/java/demo_ttcscn/services/model/impl/PostService.java[m
[1m+++ b/src/main/java/com/java/demo_ttcscn/services/model/impl/PostService.java[m
[36m@@ -7,6 +7,8 @@[m [mimport org.springframework.beans.factory.annotation.Autowired;[m
 import org.springframework.stereotype.Service;[m
 import org.springframework.transaction.annotation.Transactional;[m
 [m
[32m+[m[32mimport java.util.List;[m
[32m+[m
 @Service[m
 public class PostService {[m
   @Autowired private PostRepository postRepository;[m
[1mdiff --git a/src/main/resources/static/css/filter.css b/src/main/resources/static/css/filter.css[m
[1mindex 25d0e6d..2ff5c31 100644[m
[1m--- a/src/main/resources/static/css/filter.css[m
[1m+++ b/src/main/resources/static/css/filter.css[m
[36m@@ -112,11 +112,11 @@[m [mi {[m
 }[m
 [m
 .username-avatar {[m
[31m-    background: linear-gradient([m
[31m-            0deg, rgba(255, 27, 0, 1) 0%, rgba(251, 75, 2, 1) 100%);;[m
[32m+[m[32m    background: #38d39f;[m
     border-radius: 50%;[m
[31m-    padding: 10px 15px;[m
[32m+[m[32m    padding: 5px 10px;[m
     font-style: normal;[m
[32m+[m[32m    font-family: none;[m
 }[m
 [m
 .menu-user.active {[m
[1mdiff --git a/src/main/resources/static/css/home.css b/src/main/resources/static/css/home.css[m
[1mindex 3059c21..e9e5356 100644[m
[1m--- a/src/main/resources/static/css/home.css[m
[1m+++ b/src/main/resources/static/css/home.css[m
[36m@@ -3,7 +3,9 @@[m
 ::-webkit-scrollbar {[m
     display: none;[m
 }[m
[31m-[m
[32m+[m[32m.fw-bold{[m
[32m+[m[32m    font-weight: 400 !important;[m
[32m+[m[32m}[m
 body {[m
     background-color: whitesmoke;[m
 }[m
[36m@@ -194,6 +196,7 @@[m [mp {[m
     border-radius: 50%;[m
     padding: 5px 10px;[m
     font-style: normal;[m
[32m+[m[32m    font-family: none;[m
 }[m
 [m
 .menu-user.active {[m
[36m@@ -565,7 +568,6 @@[m [mh1.elegantshadow {[m
 [m
 .dropdown {[m
     margin: 0 auto;[m
[31m-    width: 20vh;[m
     position: relative;[m
     color: white;[m
 }[m
[1mdiff --git a/src/main/resources/static/js/exam.js b/src/main/resources/static/js/exam.js[m
[1mindex 6779b83..4f4cd2a 100644[m
[1m--- a/src/main/resources/static/js/exam.js[m
[1m+++ b/src/main/resources/static/js/exam.js[m
[36m@@ -8,29 +8,25 @@[m [mfunction alert(icon, title) {[m
     });[m
 }[m
 [m
[31m-var number_question = $('.size').text();[m
 var stompClient = null;[m
 var username = null;[m
 $('canvas').css('height', $(document).height() + 50);[m
[31m-$('.loading').css('height', $(document).height())[m
[31m-var formDataAnswer = new Map();[m
[31m-var answers = "";[m
[31m-[m
[31m-function loading(callback) {[m
[31m-    var time = 0;[m
[31m-    var running = setInterval(frame, 1000);[m
[31m-[m
[31m-    function frame() {[m
[31m-        if (time == 2) {[m
[31m-            $('.loading').hide();[m
[31m-            callback();[m
[31m-            clearInterval(running);[m
[31m-        } else {[m
[31m-            $('.loading').show();[m
[31m-            time++;[m
[31m-        }[m
[31m-    }[m
[31m-}[m
[32m+[m[32mvar size = $('.answer-verify').length;[m
[32m+[m[32m// function loading(callback) {[m
[32m+[m[32m//     var time = 0;[m
[32m+[m[32m//     var running = setInterval(frame, 1000);[m
[32m+[m[32m//[m
[32m+[m[32m//     function frame() {[m
[32m+[m[32m//         if (time == 2) {[m
[32m+[m[32m//             $('.loading').hide();[m
[32m+[m[32m//             callback();[m
[32m+[m[32m//             clearInterval(running);[m
[32m+[m[32m//         } else {[m
[32m+[m[32m//             $('.loading').show();[m
[32m+[m[32m//             time++;[m
[32m+[m[32m//         }[m
[32m+[m[32m//     }[m
[32m+[m[32m// }[m
 [m
 $(function () {[m
     var timer = ($('.time').attr('value') * 60), minute = $('.minutes'), second = $('.seconds');[m
[36m@@ -48,26 +44,14 @@[m [m$(function () {[m
             $("#form_submit").submit();[m
             $('.content').remove();[m
             clearInterval((dem));[m
[31m-[m
         }[m
     }, 1000);[m
 });[m
 $(document).on('click', 'input[type="radio"]', function () {[m
[31m-    var text = "";[m
[31m-    var question = $(this).attr("name").split("-")[1];[m
[31m-    var location = $(this).parents().attr('id').split("-")[1];[m
[31m-    formDataAnswer.set(question, $(this).val());[m
[31m-    formDataAnswer.forEach((value, key) => {[m
[31m-        answers = key + "-" + value + "&";[m
[31m-        text += answers;[m
[31m-    });[m
[31m-    $('#nav-ans-' + location).text($(this).val());[m
[31m-    $('#nav-ans-' + location).parents().eq(0).removeClass('text-danger');[m
[31m-    $('#nav-ans-' + location).parents().eq(0).addClass('text-success');[m
[31m-    $('#nav-ans-' + location).parents().eq(0).addClass('fw-bold');[m
[31m-    answers = number_question + "$" + text;[m
[31m-    answers = answers.substring(0, answers.length - 1)[m
[31m-    console.log(answers)[m
[32m+[m[32m    var location = this.name.split("-")[1];[m
[32m+[m[32m    $('#ans-' + location).text($(this).val());[m
[32m+[m[32m    $('#ans-' + location).parents().eq(0).removeClass('text-danger');[m
[32m+[m[32m    $('#ans-' + location).parents().eq(0).addClass('text-success');[m
 });[m
 [m
 function insertResult(formDataResult) {[m
[36m@@ -88,13 +72,21 @@[m [mfunction insertResult(formDataResult) {[m
 }[m
 [m
 $(document).on("submit", "#form_submit", function (e) {[m
[31m-    var formdata = new FormData();[m
[31m-    formdata.append("answer", answers);[m
     e.preventDefault();[m
[32m+[m[32m    var formDataAnswer = new FormData();[m
[32m+[m[32m    var answers = size + "$";[m
[32m+[m[32m    for (let i = 0; i < size; i++) {[m
[32m+[m[32m        let location = $('.answer-verify')[i].id.split('-')[1];[m
[32m+[m[32m        answers += location + "-";[m
[32m+[m[32m        answers += $(`#ans-${location}`).text() == "" ? "No" : $(`#ans-${location}`).text();[m
[32m+[m[32m        answers += "&";[m
[32m+[m[32m    }[m
[32m+[m[32m    formDataAnswer.append("answer", answers.substr(0, answers.length - 1));[m
[32m+[m[32m    console.log(formDataAnswer.get('answer'));[m
     $.ajax({[m
         url: "http://localhost:8080/answer/verify-answer",[m
         type: "POST",[m
[31m-        data: formdata,[m
[32m+[m[32m        data: formDataAnswer,[m
         enctype: "multipart/form-data",[m
         processData: false,[m
         contentType: false,[m
[36m@@ -108,33 +100,29 @@[m [m$(document).on("submit", "#form_submit", function (e) {[m
             }[m
         },[m
         success: function (res) {[m
[31m-            function showResultAnswer() {[m
[31m-                var rs = res.content;[m
[31m-                var html = "";[m
[31m-                html += `<h5><i class="far fa-thumbs-up"></i>Số câu đúng : ${res.number_success}</h5>[m
[31m-                             <h5><i class="far fa-gem"></i>Số điểm : ${res.point}</h5>[m
[31m-                             <div class="d-flex align-items-center mt-2">[m
[31m-                                 <a class="btn btn-outline-primary" href="/">Trang chủ</a>[m
[31m-                                 <button class="custom-btn btn-12" onclick="history.back();">[m
[31m-                                 <span>Click!</span><span>Làm tiếp</span>[m
[31m-                                 </button>[m
[31m-                             </div>`;[m
[31m-                html += `<h3 class="text-white my-2">Một số câu sai trong bài thi và đáp án :</h3>`;[m
[31m-                $.each(rs, function (index, cnt) {[m
[31m-                    html += `<h5 class="bg-white text-dark rounded px-3 py-2">${cnt}</h5>`;[m
[31m-                })[m
[31m-                var formDataResult = new FormData();[m
[31m-                formDataResult.append('codeQues', $('#code_ques').text());[m
[31m-                formDataResult.append('name', $('#name_course').text());[m
[31m-                formDataResult.append('numberPoint', res.point);[m
[31m-                formDataResult.append('numberQuestion', res.number_question);[m
[31m-                insertResult(formDataResult);[m
[31m-                $(window).scrollTop(0);[m
[31m-                $('#point').html(html);[m
[31m-                $('.content').remove();[m
[31m-            }[m
[31m-[m
[31m-            loading(showResultAnswer);[m
[32m+[m[32m            var rs = res.content;[m
[32m+[m[32m            var html = "";[m
[32m+[m[32m            html += `<h5><i class="far fa-thumbs-up"></i>Số câu đúng : ${res.number_success}</h5>[m
[32m+[m[32m                     <h5><i class="far fa-gem"></i>Số điểm : ${res.point}</h5>[m
[32m+[m[32m                     <div class="d-flex align-items-center mt-2">[m
[32m+[m[32m                          <a class="btn btn-outline-primary" href="/">Trang chủ</a>[m
[32m+[m[32m                          <button class="custom-btn btn-12" onclick="history.back();">[m
[32m+[m[32m                               <span>Click!</span><span>Làm tiếp</span>[m
[32m+[m[32m                          </button>[m
[32m+[m[32m                     </div>`;[m
[32m+[m[32m            html += `<h3 class="text-white my-2">Một số câu sai trong bài thi và đáp án :</h3>`;[m
[32m+[m[32m            $.each(rs, function (index, cnt) {[m
[32m+[m[32m                html += `<h5 class="bg-white text-dark rounded px-3 py-2">${cnt}</h5>`;[m
[32m+[m[32m            })[m
[32m+[m[32m            var formDataResult = new FormData();[m
[32m+[m[32m            formDataResult.append('codeQues', $('#code_ques').text());[m
[32m+[m[32m            formDataResult.append('name', $('#name_course').text());[m
[32m+[m[32m            formDataResult.append('numberPoint', res.point);[m
[32m+[m[32m            formDataResult.append('numberQuestion', res.number_question);[m
[32m+[m[32m            insertResult(formDataResult);[m
[32m+[m[32m            $(window).scrollTop(0);[m
[32m+[m[32m            $('#point').html(html);[m
[32m+[m[32m            $('.content').remove();[m
             connect();[m
         }, error: function (e) {[m
             console.log(e);[m
[1mdiff --git a/src/main/resources/static/js/home.js b/src/main/resources/static/js/home.js[m
[1mindex 869b0cc..4d15eb2 100644[m
[1m--- a/src/main/resources/static/js/home.js[m
[1m+++ b/src/main/resources/static/js/home.js[m
[36m@@ -50,19 +50,7 @@[m [m$(function () {[m
         $('#modal-user').modal('show');[m
     }[m
     if (payload === '') {[m
[31m-        $.ajax({[m
[31m-            url: urlPost,[m
[31m-            method: "GET",[m
[31m-            dataType: "json",[m
[31m-            success: function (res) {[m
[31m-                var htmlPost = `<h1 class="mb-4 text-dark elegantshadow" style="font-weight: bold;text-indent:40px;">${res.title}</h1>[m
[31m-            <span class="text-welcome text-dark"[m
[31m-                style="text-indent: 40px; text-align: justify; font-size: 22px;font-style: italic;">${res.description}</span>`;[m
[31m-                var htmlImg = `<img src="${res.image}" class="h-100 w-100" alt="">`;[m
[31m-                $(".welcome-title").html(htmlPost);[m
[31m-                $(".welcome-img").html(htmlImg);[m
[31m-            },[m
[31m-        });[m
[32m+[m[32m        handleGetWelcome();[m
         lastPost();[m
         getLessonIsHot();[m
         getTopicRandom(5);[m
[36m@@ -71,6 +59,25 @@[m [m$(function () {[m
     connect();[m
 });[m
 [m
[32m+[m[32mfunction handleGetWelcome() {[m
[32m+[m[32m    $.ajax({[m
[32m+[m[32m        url: urlPost,[m
[32m+[m[32m        method: "GET",[m
[32m+[m[32m        dataType: "json",[m
[32m+[m[32m        success: function (rs) {[m
[32m+[m[32m            var htmlPost = `<h1 class="mb-4 text-dark elegantshadow"[m[41m [m
[32m+[m[32m                                style="font-weight: bold;text-indent:40px;">${rs.title}</h1>[m
[32m+[m[32m                                <span class="text-welcome text-dark"[m
[32m+[m[32m                                style="text-indent: 40px; text-align: justify;[m[41m [m
[32m+[m[32m                                font-size: 22px;font-style: italic;">${rs.description}</span>[m
[32m+[m[32m                             </div>`;[m
[32m+[m[32m            var htmlImg = `<img src="${rs.image}" class="h-100 w-100" alt="">`;[m
[32m+[m[32m            $(".welcome-title").html(htmlPost);[m
[32m+[m[32m            $(".welcome-img").html(htmlImg);[m
[32m+[m[32m        },[m
[32m+[m[32m    });[m
[32m+[m[32m}[m
[32m+[m
 function handleGetResult() {[m
     $.ajax({[m
         url:[m
[1mdiff --git a/src/main/resources/templates/exam.html b/src/main/resources/templates/exam.html[m
[1mindex f51e4c2..f727c44 100644[m
[1m--- a/src/main/resources/templates/exam.html[m
[1m+++ b/src/main/resources/templates/exam.html[m
[36m@@ -15,17 +15,17 @@[m
 </head>[m
 [m
 <body>[m
[31m-<div class="loading">[m
[31m-    <div class="btn-load">[m
[31m-        <h3 class="text-light">Loading...</h3>[m
[31m-        <div class="circle-loading2">[m
[31m-            <div></div>[m
[31m-            <div></div>[m
[31m-        </div>[m
[31m-    </div>[m
[31m-</div>[m
[32m+[m[32m<!--<div class="loading">-->[m
[32m+[m[32m<!--    <div class="btn-load">-->[m
[32m+[m[32m<!--        <h3 class="text-light">Loading...</h3>-->[m
[32m+[m[32m<!--        <div class="circle-loading2">-->[m
[32m+[m[32m<!--            <div></div>-->[m
[32m+[m[32m<!--            <div></div>-->[m
[32m+[m[32m<!--        </div>-->[m
[32m+[m[32m<!--    </div>-->[m
[32m+[m[32m<!--</div>-->[m
 <div id="particles-js">[m
[31m-    <div class="container profile text-light">[m
[32m+[m[32m    <div class="container profile text-light" sec:authorize="isAuthenticated()">[m
         <span id="username-cnt" sec:authentication="name" class="d-none"></span>[m
         <div class="infomation d-flex justify-content-between">[m
             <h2 class="mb-3 title">Môn thi :[m
[36m@@ -43,7 +43,7 @@[m
                 <form action="" id="form_submit" class="d-flex justify-content-between w-100 h-100">[m
                     <div class="exam">[m
                         <th:block th:each="item,state:${rs.content}">[m
[31m-                            <div th:id="'q-'+${state.count}" class="mb-3">[m
[32m+[m[32m                            <div th:id="'q-'+${item.id}" class="mb-3">[m
                                 <h5>Câu hỏi <span th:text="${state.count}"></span> :<span[m
                                         th:text="' ('+${item.level}+')'"></span></h5>[m
                                 <h5 class="bg-white p-2 rounded" th:utext="${item.name_question}"></h5>[m
[36m@@ -61,8 +61,8 @@[m
                     <th:block th:if="${rs.content != null}">[m
                         <button type="submit"[m
                                 class="custom-btn btn-12 position-sticky mx-3"[m
[31m-                                style="top: 1.5rem; transform: translate(-40px,-10px)"><span class="mx-2"[m
[31m-                                                                                             style="width: 100px;">Click!</span>[m
[32m+[m[32m                                style="top: 1.5rem; transform: translate(-40px,-10px)">[m
[32m+[m[32m                            <span class="mx-2" style="width: 100px;">Click!</span>[m
                             <span class="mx-2" style="width: 100px">Nộp bài</span>[m
                         </button>[m
                     </th:block>[m
[36m@@ -96,11 +96,12 @@[m
                                 </th:block>[m
                             </h4>[m
                             <th:block th:each="item,state:${rs.content}">[m
[31m-                                <a class="nav-link col-6 p-0 text-danger fw-bold h6" th:href="'#q-'+${state.count}">Câu[m
[31m-                                    <span[m
[31m-                                            th:utext="${state.count}"></span><span[m
[31m-                                            class="mx-1"[m
[31m-                                            th:id="'nav-ans-'+${state.count}"></span></a>[m
[32m+[m[32m                                <a class="nav-link answer-verify col-6 p-0 text-danger fw-bold h6"[m
[32m+[m[32m                                   th:id="'q-'+${item.id}"[m
[32m+[m[32m                                   th:href="'#q-'+${item.id}">Câu[m
[32m+[m[32m                                    <span th:utext="${state.count}"></span>[m
[32m+[m[32m                                    <span class="mx-1" th:id="'ans-'+${item.id}"></span>[m
[32m+[m[32m                                </a>[m
                             </th:block>[m
                         </div>[m
                     </th:block>[m
[1mdiff --git a/src/main/resources/templates/home.html b/src/main/resources/templates/home.html[m
[1mindex a6648f2..0777ccf 100644[m
[1m--- a/src/main/resources/templates/home.html[m
[1m+++ b/src/main/resources/templates/home.html[m
[36m@@ -77,19 +77,20 @@[m
                     </div>[m
                 </ul>[m
                 <!-- background: #38d39f -->[m
[31m-<!--                <div sec:authorize="isAnonymous()">-->[m
[31m-<!--                    <a class="btn-login text-white nav-link d-flex align-items-center" href="#"-->[m
[31m-<!--                       style="padding: 0 0 0 15px; background: #38d39f;font-family: 'Quicksand', sans-serif;">-->[m
[31m-<!--                        Đăng nhập<i class="fa fa-user" aria-hidden="true"></i>-->[m
[31m-<!--                    </a>-->[m
[31m-<!--                </div>-->[m
[32m+[m[32m                <!--                <div sec:authorize="isAnonymous()">-->[m
[32m+[m[32m                <!--                    <a class="btn-login text-white nav-link d-flex align-items-center" href="#"-->[m
[32m+[m[32m                <!--                       style="padding: 0 0 0 15px; background: #38d39f;font-family: 'Quicksand', sans-serif;">-->[m
[32m+[m[32m                <!--                        Đăng nhập<i class="fa fa-user" aria-hidden="true"></i>-->[m
[32m+[m[32m                <!--                    </a>-->[m
[32m+[m[32m                <!--                </div>-->[m
                 <div class="username d-flex align-items-center position-relative"[m
                      sec:authorize="isAuthenticated()">[m
                     <h4 class="username-content m-0">[m
                         <span class="text-light username-avatar"></span>[m
                         <span class="username-name fw-bold text-uppercase" sec:authentication="name"></span>[m
                     </h4>[m
[31m-                    <span class="mx-3"><a class="text-white bg-danger rounded-circle py-1 px-2" href="/logout" id="logout">[m
[32m+[m[32m                    <span class="mx-3"><a class="text-white bg-danger rounded-circle py-1 px-2" href="/logout"[m
[32m+[m[32m                                          id="logout">[m
                         <i class="fa fa-power-off" aria-hidden="true"></i></a>[m
                     </span>[m
                 </div>[m
[36m@@ -196,7 +197,8 @@[m
     <hr>[m
     <div class="welcome mb-3 rounded overflow-hidden">[m
         <div class="row">[m
[31m-            <div class="col-8 welcome p-4 text-white d-flex flex-column justify-content-center">[m
[32m+[m[32m            <div class="col-8 welcome p-4[m
[32m+[m[32m                    text-white d-flex flex-column justify-content-center">[m
                 <div class="welcome-title"></div>[m
                 <div class="row">[m
                     <div class="col-4"><img[m
