package com.avd.springsecurity6backend.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/admin-demo")
public class DemoAdminController {

    @GetMapping
    public ResponseEntity<String> sayHello(){
        return ResponseEntity.ok("Hello from ADMIN secured endpoint");
    }
}
