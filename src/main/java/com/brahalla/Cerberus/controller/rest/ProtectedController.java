package com.brahalla.Cerberus.controller.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("protected")
public class ProtectedController {

  @RequestMapping(method = RequestMethod.GET)
  public String getDaHoney() {
    return ":O";
  }

}
