package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {
    //    @RequestMapping(value = "/", method = RequestMethod.GET)
    @GetMapping("/test")
    public String helloWorld() {
        return "Welcome spring";
    }
}

