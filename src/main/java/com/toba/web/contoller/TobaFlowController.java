package com.toba.web.contoller;

import com.toba.tobaflow.TobaFlowManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TobaFlowController {

    @Autowired
    private TobaFlowManager tobaFlowManager;

    @RequestMapping(value = "/stop", method = RequestMethod.GET)
    public ModelAndView boardListGet(ModelAndView mv) {
        mv.setViewName("index");
        //기능 코드는 생략
        return mv;
    }


    @RequestMapping("/start")
    public String dashboard() {
        return "index";
    }

    @RequestMapping("/sample-page2")
    public String sample2() {
        return "sample-page2";
    }
}

