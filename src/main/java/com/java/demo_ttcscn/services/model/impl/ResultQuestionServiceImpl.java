package com.java.demo_ttcscn.services.model.impl;

import com.java.demo_ttcscn.config.rsql.CustomRsqlVisitor;
import com.java.demo_ttcscn.enitities.dto.ResultQuestionDto;
import com.java.demo_ttcscn.enitities.model.ResultQuestion;
import com.java.demo_ttcscn.exception.NotFoundException;
import com.java.demo_ttcscn.repositories.ResultQuestionRepository;
import com.java.demo_ttcscn.repositories.base.BaseRepository;
import com.java.demo_ttcscn.services.base.BaseServiceImpl;
import com.java.demo_ttcscn.services.model.ResultQuestionService;
import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResultQuestionServiceImpl extends BaseServiceImpl<ResultQuestion, ResultQuestionDto>
    implements ResultQuestionService {
  @Autowired ModelMapper modelMapper;
  @Autowired private ResultQuestionRepository resultQuestionRepository;

  @Override
  protected Class<ResultQuestion> classEntity() {
    return ResultQuestion.class;
  }

  @Override
  protected Class<ResultQuestionDto> classDto() {
    return ResultQuestionDto.class;
  }

  @Override
  protected BaseRepository<ResultQuestion> baseRepository() {
    return resultQuestionRepository;
  }

  @Override
  public List<ResultQuestionDto> getResultByCode(String code) {
    try {
      List<ResultQuestionDto> rs =
          resultQuestionRepository.getResultByCodeQues(code).stream()
              .map(source -> modelMapper.map(source, classDto()))
              .collect(Collectors.toList());
      return rs;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public List<ResultQuestionDto> getResultByUsername(String username) {
    try {
      List<ResultQuestionDto> rs =
          resultQuestionRepository.getResultByUsername(username).stream()
              .map(source -> modelMapper.map(source, classDto()))
              .collect(Collectors.toList());
      if (rs.size() > 0) {
        return rs;
      } else {
        throw new NotFoundException("Không tìm thấy kết quả");
      }
    } catch (Exception e) {
      throw new NotFoundException("Không tìm thấy kết quả");
    }
  }

  @Override
  public List<ResultQuestionDto> getResultByUsernameAndDesc(String username) {
    try {
      Node rootNode = new RSQLParser().parse(username);
      Specification<ResultQuestion> spec = rootNode.accept(new CustomRsqlVisitor<ResultQuestion>());
      Sort sortOrder = Sort.by("numberPoint").descending();
      List<ResultQuestion> page = baseRepository().findAll(spec, sortOrder);
      List<ResultQuestionDto> rs =
              page.stream()
                      .map(source -> modelMapper.map(source, classDto()))
                      .collect(Collectors.toList());
      if (rs.size() > 0) {
        return rs;
      } else {
        throw new NotFoundException("Không tìm thấy kết quả");
      }
    } catch (Exception e) {
      throw new NotFoundException("Không tìm thấy kết quả");
    }
  }

  @Override
  public List<ResultQuestionDto> getResultByUsernameAndAsc(String username) {
    try {
      Node rootNode = new RSQLParser().parse(username);
      Specification<ResultQuestion> spec = rootNode.accept(new CustomRsqlVisitor<ResultQuestion>());
      Sort sortOrder = Sort.by("numberPoint");
      List<ResultQuestion> page = baseRepository().findAll(spec, sortOrder);
      List<ResultQuestionDto> rs =
          page.stream()
              .map(source -> modelMapper.map(source, classDto()))
              .collect(Collectors.toList());
      if (rs.size() > 0) {
        return rs;
      } else {
        throw new NotFoundException("Không tìm thấy kết quả");
      }
    } catch (Exception e) {
      throw new NotFoundException("Không tìm thấy kết quả");
    }
  }
}
