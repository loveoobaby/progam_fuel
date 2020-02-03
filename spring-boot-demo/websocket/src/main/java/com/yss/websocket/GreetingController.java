package com.yss.websocket;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {


    @RequestMapping("/hello")
    public Greeting greeting() throws Exception {
        return new Greeting("Hello, " + "!");
    }

}
