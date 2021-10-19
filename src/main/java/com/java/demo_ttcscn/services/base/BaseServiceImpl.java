package com.java.demo_ttcscn.services.base;

import com.java.demo_ttcscn.config.rsql.CustomRsqlVisitor;
import com.java.demo_ttcscn.enitities.result.PageResponse;
import com.java.demo_ttcscn.exception.NotFoundException;
import com.java.demo_ttcscn.repositories.base.BaseRepository;
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
public abstract class BaseServiceImpl<T, D> implements BaseService<T, D> {
  @Autowired ModelMapper modelMapper;

  protected abstract Class<T> classEntity();

  protected abstract Class<D> classDto();

  protected abstract BaseRepository<T> baseRepository();

  @Override
  public D getById(int id) {
    T rs = baseRepository().findById(id).get();
    D res = modelMapper.map(rs, classDto());
    return res;
  }

  @Override
  public PageResponse getByFilter(Pageable pageable, String filter) {
    try {
      if (filter == null) {
        Page<T> page = baseRepository().findAll(pageable);

        int totalPage = baseRepository().findAll(pageable).getTotalPages();
        List<D> res =
            page.getContent().stream()
                .map(source -> modelMapper.map(source, classDto()))
                .collect(Collectors.toList());
        return new PageResponse(
            pageable.getPageNumber(), totalPage, (int) page.getTotalElements(), res);
      }
      if (filter != null) {
        Node rootNode = new RSQLParser().parse(filter);
        Specification<T> spec = rootNode.accept(new CustomRsqlVisitor<T>());
        Page<T> page = baseRepository().findAll(spec, pageable);
        int totalPage = page.getTotalPages();
        List<D> res =
            page.getContent().stream()
                .map(source -> modelMapper.map(source, classDto()))
                .collect(Collectors.toList());
        return new PageResponse(
            pageable.getPageNumber(), totalPage, (int) page.getTotalElements(), res);
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw new NotFoundException("Không tìm thấy dữ liệu");
    }
    throw new NotFoundException("Không tìm thấy dữ liệu");
  }

  @Override
  public D create(D dto) {
    T rs = modelMapper.map(dto, classEntity());
    T responseDb = baseRepository().save(rs);
    D res = modelMapper.map(responseDb, classDto());
    return res;
  }

  @Override
  public D update(D dto) {
    T rs = modelMapper.map(dto, classEntity());
    T responseDb = baseRepository().save(rs);
    D res = modelMapper.map(responseDb, classDto());
    return res;
  }

  @Override
  public String delete(int id) {
    try {
      T rs = baseRepository().findById(id).get();
      if (rs != null) {
        baseRepository().delete(rs);
        return "Xóa thành công";
      } else {
        return "Có lỗi xảy ra";
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw new NotFoundException("Không tồn tại");
    }
  }
}
