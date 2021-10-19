package com.java.demo_ttcscn.controller;

import com.java.demo_ttcscn.enitities.dto.AskDto;
import com.java.demo_ttcscn.enitities.model.Ask;
import com.java.demo_ttcscn.exception.NotFoundException;
import com.java.demo_ttcscn.repositories.AskRepository;
import com.java.demo_ttcscn.services.base.BaseService;
import com.java.demo_ttcscn.services.model.AskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/ask")
public class AskController extends BaseControllerImpl<Ask, AskDto>{
  @Autowired private AskService askService;
  @Autowired private AskRepository askRepository;

  @Override
  protected BaseService<Ask, AskDto> getService() {
    return askService;
  }

  @GetMapping("/find/id")
  public ResponseEntity<?> findByIdToEntity(@RequestParam(name = "id") Integer id) {
    try {
      Ask rs = askRepository.findById(id).get();
      if (rs == null) {
        throw new NotFoundException("Không tìm thấy");
      } else {
        return ResponseEntity.ok(rs);
      }
    } catch (Exception e) {
      throw new NotFoundException("Không tìm thấy");
    }
  }
}
