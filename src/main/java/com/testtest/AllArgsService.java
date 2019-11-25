package com.testtest;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author shennan
 * @date 2019/10/29 16:43
 */
@Service
@RequestMapping("/aa")
@RestController
public class AllArgsService {
  @Autowired
  private Map<String, IAllArgs> map01;

  @Autowired
  private List<IAllArgs> list01;

  @GetMapping("/bb")
  public Map<String, IAllArgs> getMap01() {
    System.out.println(list01.toString());
    System.out.println(map01.toString());
    return map01;
  }
}
