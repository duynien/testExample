package com.java.demo_ttcscn.services.model.impl;

import com.java.demo_ttcscn.enitities.dto.AnswerDto;
import com.java.demo_ttcscn.enitities.model.Answer;
import com.java.demo_ttcscn.enitities.model.Question;
import com.java.demo_ttcscn.enitities.result.AnswerResponse;
import com.java.demo_ttcscn.exception.NotFoundException;
import com.java.demo_ttcscn.repositories.AnswerRepository;
import com.java.demo_ttcscn.repositories.QuestionRepository;
import com.java.demo_ttcscn.repositories.base.BaseRepository;
import com.java.demo_ttcscn.services.base.BaseServiceImpl;
import com.java.demo_ttcscn.services.model.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class AnswerServiceImpl extends BaseServiceImpl<Answer, AnswerDto> implements AnswerService {
  @Autowired DecimalFormat df;
  @Autowired private AnswerRepository answerRepository;
  @Autowired private QuestionRepository questionRepository;

  @Override
  protected Class<Answer> classEntity() {
    return Answer.class;
  }

  @Override
  protected Class<AnswerDto> classDto() {
    return AnswerDto.class;
  }

  @Override
  protected BaseRepository<Answer> baseRepository() {
    return answerRepository;
  }

  @Override
  public AnswerResponse submitAnswer(String answerRequest) {
    if (answerRequest.length() > 2) {
      try {
        Map<Integer, String> mapAnswer = new HashMap<>();
        AtomicReference<Double> point = new AtomicReference<>((double) 0);
        AtomicInteger numberQuestionCorrect = new AtomicInteger();
        int numberQuestion = 0;
        List<String> content = new ArrayList<>();
        StringTokenizer tokenizer = new StringTokenizer(answerRequest, "$");
        //      numberQuestion = Integer.parseInt(answerRequest.trim().split("\\$")[0]);
        numberQuestion = Integer.parseInt(tokenizer.nextToken());
        tokenizer = new StringTokenizer(tokenizer.nextToken(),"&");
        //      String verifyAnswers[] = answerRequest.trim().split("\\$")[1].trim().split("\\&");
        double pointEveryQuestion = (double) 10 / numberQuestion;
        while (tokenizer.hasMoreTokens()) {
          String rs = tokenizer.nextToken();
          mapAnswer.put(Integer.parseInt(rs.split("-")[0]), rs.split("-")[1]);
        }
        mapAnswer.forEach(
            (k, v) -> {
              Question question = questionRepository.findById(k).get();
              String correctAnswer = question.getAnswer().getCorrect_ans();
              if (v.equals("No") || !v.equals(correctAnswer)) {
                String nameQuestion = question.getName_question();
                if (correctAnswer.equals("A")) {
                  content.add("Câu hỏi." + nameQuestion + "\nA." + question.getAnswer().getAnsw_A());
                } else if (correctAnswer.equals("B")) {
                  content.add("Câu hỏi." + nameQuestion + "\nB." + question.getAnswer().getAnsw_B());
                } else if (correctAnswer.equals("C")) {
                  content.add("Câu hỏi." + nameQuestion + "\nC." + question.getAnswer().getAnsw_C());
                } else if (correctAnswer.equals("D")) {
                  content.add("Câu hỏi." + nameQuestion + "\nD." + question.getAnswer().getAnsw_D());
                }
              }
              if (v.equals(correctAnswer)) {
                numberQuestionCorrect.getAndIncrement();
                point.updateAndGet(v1 -> new Double((double) (v1 + pointEveryQuestion)));
              }
            });
        //      for (int i = 0; i < verifyAnswers.length; i++) {
        //        String answer[] = verifyAnswers[i].split("-");
        //        Question question =
        // questionRepository.findById(Integer.parseInt(answer[0])).get();
        //        String correctAnswer = question.getAnswer().getCorrect_ans();
        //        if (answer[1].equals("No") || !answer[1].equals(correctAnswer)) {
        //          String nameQuestion = question.getName_question();
        //          if (correctAnswer.equals("A")) {
        //            content.add((i + 1) + "." + nameQuestion + "\nA." +
        // question.getAnswer().getAnsw_A());
        //          } else if (correctAnswer.equals("B")) {
        //            content.add((i + 1) + "." + nameQuestion + "\nB." +
        // question.getAnswer().getAnsw_B());
        //          } else if (correctAnswer.equals("C")) {
        //            content.add((i + 1) + "." + nameQuestion + "\nC." +
        // question.getAnswer().getAnsw_C());
        //          } else if (correctAnswer.equals("D")) {
        //            content.add((i + 1) + "." + nameQuestion + "\nD." +
        // question.getAnswer().getAnsw_D());
        //          }
        //        }
        //        if (answer[1].equals(correctAnswer)) {
        //          numberQuestionCorrect++;
        //          point += pointEveryQuestion;
        //        }
        //      }
        AnswerResponse answerResponse = new AnswerResponse();
        answerResponse.setNumberQuestionCorrect(numberQuestionCorrect.get());
        answerResponse.setPoint(df.format(point.get()));
        answerResponse.setNumberQuestion(numberQuestion);
        answerResponse.setContent(content);
        return answerResponse;
      } catch (Exception e) {
        e.printStackTrace();
        throw new NotFoundException("Chưa chọn đáp án");
      }
    } else {
      throw new NotFoundException("Chưa chọn đáp án");
    }
  }
}
