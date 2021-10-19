package com.java.demo_ttcscn.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface BaseController<T, D> {
  @GetMapping("")
  public ResponseEntity<?> getFilter(Pageable pageEntity, String filter);

  @GetMapping("/get-by-id")
  public ResponseEntity<?> getById(@RequestParam(name = "id") String id);

  @PutMapping("")
  public ResponseEntity<?> update(@ModelAttribute D updated);

  @PostMapping("")
  public ResponseEntity<?> create(@ModelAttribute D created);

  @DeleteMapping("/{id}")
  public ResponseEntity<String> delete(@PathVariable int id);
}
