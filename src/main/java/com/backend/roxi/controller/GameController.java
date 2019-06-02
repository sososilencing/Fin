package com.backend.roxi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author Roxi酱
 */
@Controller
@RequestMapping(value = "/game")
public class GameController {

    @RequestMapping(value = "/join",method = RequestMethod.GET)
    public String getJoin(){
        return "join";
    }

    @RequestMapping(value = "/join",method = RequestMethod.POST)
    public String join(@RequestParam("room") String room){
        return "ws";
    }
}
