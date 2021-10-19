package com.java.demo_ttcscn.controller;

import com.java.demo_ttcscn.services.base.BaseService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import springfox.documentation.annotations.ApiIgnore;

public abstract class BaseControllerImpl<T, D> implements BaseController<T, D> {

  protected abstract BaseService<T, D> getService();

  @ApiImplicitParams({
    @ApiImplicitParam(
        name = "page",
        dataType = "string",
        paramType = "query",
        value = "Results page you want to retrieve (0..N)"),
    @ApiImplicitParam(
        name = "size",
        dataType = "string",
        paramType = "query",
        value = "Number of records per page."),
    @ApiImplicitParam(
        name = "sort",
        allowMultiple = true,
        dataType = "string",
        paramType = "query",
        value =
            "Sorting criteria in the format: property(,asc|desc). "
                + "Default sort order is ascending. "
                + "Multiple sort criteria are supported.")
  })
  public ResponseEntity<?> getFilter(
      @ApiIgnore @PageableDefault(size = 10) Pageable pageable,
      @RequestParam(value = "filter", required = false) String filter) {
    return ResponseEntity.ok(getService().getByFilter(pageable, filter));
  }

  public ResponseEntity<?> getById(@RequestParam(name = "id") String id) {
    return ResponseEntity.ok(getService().getById(Integer.parseInt(id)));
  }

  public ResponseEntity<?> update(@ModelAttribute D updated) {
    return ResponseEntity.ok(getService().update(updated));
  }

  public ResponseEntity<?> create(@ModelAttribute D created) {
    return ResponseEntity.status(HttpStatus.CREATED).body(getService().create(created));
  }

  public ResponseEntity<String> delete(@PathVariable int id) {
    return ResponseEntity.status(202).body(getService().delete(id));
  }
}
