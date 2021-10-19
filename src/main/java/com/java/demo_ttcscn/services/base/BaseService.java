package com.java.demo_ttcscn.services.base;

import com.java.demo_ttcscn.enitities.result.PageResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface BaseService<T, D> {
  public D getById(int id);

  public PageResponse getByFilter(Pageable pageable, String filter);

  @Transactional
  public D create(D dto);

  @Transactional
  public D update(D dto);

  @Transactional
  @Modifying
  public String delete(int id);
}
