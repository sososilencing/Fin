package com.backend.roxi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Roxi酱
 */
@RestController
public class ViewController {
    @GetMapping(value = "/view")
    public int[][] view(){
        int [][]map=new int[15][15];
        map[1][1]=1;
       return map;
    }
}
