package com.backend.roxi.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Roxi酱
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Chess {

    int who;
    String name;
    int x;
    int y;
    String type;
    public Chess(int who,String name,int x,int y){
        this.who=who;
        this.name=name;
        this.x=x;
        this.y=y;
    }
    public String description(){
        if(who==1){
            System.out.println("黑方:"+name+"落子在于坐标:"+"["+x+","+y+"]");
            return "黑方:"+name+"落子在于坐标:"+"["+x+","+y+"]";
        }else {
            System.out.println("白方:"+name+"落子在于坐标:"+"["+x+","+y+"]");
            return "白方:"+name+"落子在于坐标:"+"["+x+","+y+"]";
        }
    }

}
