package org.urbantower.j4s.example.springmvc.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.urbantower.j4s.example.springmvc.Service;

@Controller
public class HelloWorldRestController {

    @Autowired
    private Service service;

    @RequestMapping(name="/helloworld", method= RequestMethod.GET)
    public String helloWorld(Model model) {
        model.addAttribute("message", service.helloWorld());
        return "helloWorld";
    }
}