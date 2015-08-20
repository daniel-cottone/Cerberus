package com.brahalla.Cerberus.controller.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${cerberus.route.protected}")
public class ProtectedController {

  @RequestMapping(method = RequestMethod.GET)
  @PreAuthorize("hasRole('ADMIN')")
  public String getDaHoney() {
    return ":O";
  }

}
