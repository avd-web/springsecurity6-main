package com.avd.springsecurity6backend.api;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpringSecurity6Tests {

    //TODO: Add exception handling to all tests?
    //TODO: Try to remove @AUTOWIRED?
    //TODO: Check dependency versions and H2 Database version (why is it not the latest?)
    //TODO: What is/was the github dependency vulnerability (automatic) error, did it have anything to do with the H2 db version?
    //TODO: PROBLEM: users can be registered without any check, so only a password is enough to register?

}


