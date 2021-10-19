package com.java.demo_ttcscn.services.model.impl;

import com.java.demo_ttcscn.config.rsql.CustomRsqlVisitor;
import com.java.demo_ttcscn.enitities.dto.AskDto;
import com.java.demo_ttcscn.enitities.model.Ask;
import com.java.demo_ttcscn.enitities.result.AskReponse;
import com.java.demo_ttcscn.enitities.result.PageResponse;
import com.java.demo_ttcscn.exception.NotFoundException;
import com.java.demo_ttcscn.repositories.AskRepository;
import com.java.demo_ttcscn.repositories.base.BaseRepository;
import com.java.demo_ttcscn.services.base.BaseServiceImpl;
import com.java.demo_ttcscn.services.model.AskService;
import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AskServiceImpl extends BaseServiceImpl<Ask, AskDto> implements AskService {
  @Autowired ModelMapper modelMapper;
  @Autowired private AskRepository askRepository;

  @Override
  protected Class<Ask> classEntity() {
    return Ask.class;
  }

  @Override
  protected Class<AskDto> classDto() {
    return AskDto.class;
  }

  @Override
  protected BaseRepository<Ask> baseRepository() {
    return askRepository;
  }

  @Override
  public PageResponse getByFilter(Pageable pageable, String filter) {
    try {
      if (filter == null) {
        Page<Ask> page = baseRepository().findAll(pageable);

        int totalPage = baseRepository().findAll(pageable).getTotalPages();
        List<AskReponse> res =
            page.getContent().stream()
                .map(source -> modelMapper.map(source, AskReponse.class))
                .collect(Collectors.toList());
        for (int i = 0; i < res.size(); i++) {
          res.get(i).setCommentListSize(page.getContent().get(i).getCommentList().size());
        }
        return new PageResponse(
            pageable.getPageNumber(), totalPage, (int) page.getTotalElements(), res);
      }
      if (filter != null) {
        Node rootNode = new RSQLParser().parse(filter);
        Specification<Ask> spec = rootNode.accept(new CustomRsqlVisitor<Ask>());
        Page<Ask> page = baseRepository().findAll(spec, pageable);
        int totalPage = page.getTotalPages();
        List<AskReponse> res =
            page.getContent().stream()
                .map(source -> modelMapper.map(source, AskReponse.class))
                .collect(Collectors.toList());
        for (int i = 0; i < res.size(); i++) {
          res.get(i).setCommentListSize(page.getContent().get(i).getCommentList().size());
        }
        return new PageResponse(
            pageable.getPageNumber(), totalPage, (int) page.getTotalElements(), res);
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw new NotFoundException("Không tìm thấy dữ liệu");
    }
    throw new NotFoundException("Không tìm thấy dữ liệu");
  }
}
