package com.backend.roxi.service;

import org.springframework.stereotype.Service;

/**
 * @author Roxi酱
 */

@Service
public class CoreService {

    private final int FOUR=4;
    /**
     * 五子棋 判断是否逻辑胜利
     */
    public boolean victory(int who,int[][] map,int x,int y) {
        return shu(who, map,x,y)||xie(who, map,x,y)||heng(who, map,x,y);
    }
    /**
     * 斜着统计
     */
    private boolean xie(int who,int[][] map,int x,int y){
        int count=0;
        boolean heng;
        boolean shu;
        System.out.println("斜着来");
        //右下斜
        int i=x,j=y;
        do {

            if(map[i][j]==who) {
                count++;
                System.out.println("x:"+i+" y:"+j);
                i++;
                j++;
            }else {
                break;
            }
            heng= i<=y+FOUR && i<map.length;
            shu= j < map[x].length && j <= y + FOUR;
        }while ( heng && shu );

        //左上斜
       i=x-1; j=y-1;
        do {
            if(map[i][j]==who) {
                System.out.println("x:"+i+" y:"+j);
                count++;
                i--;
                j--;
            }else {
                break;
            }
            heng = (i >= x - FOUR && i > 0);
            shu =(j > 0 && j >= y - FOUR) ;
        }while ( heng && shu );

        //左下
        i=x-1; j=y+1;
        do {
            if(map[i][j]==who) {
                System.out.println("x:"+i+" y:"+j);
                count++;
                i--;
                j++;
            }else {
                break;
            }
            heng= i>= x - FOUR && i > 0;
            shu= j < map[x].length && j <= y + FOUR;
        }while ( heng && shu );

        //右上
        i=x+1; j=y-1;
        do {
            if(map[i][j]==who) {
                System.out.println("x:"+i+" y:"+j);
                count++;
                i++;
                j--;
            }else {
                break;
            }
            heng= i<=y+FOUR && i<map.length;
            shu= j > 0 && j >= y - FOUR;
        }while ( heng && shu );
        System.out.println(count);
        return count >= 5;
    }

    /**
     * 开始竖着统计
     */
    private boolean shu(int who,int[][] map,int x,int y){
        int count=0;
        int j;
        System.out.println("竖向统计");
       //向上统计
        for ( j=y ; j < map[x].length && j <= y + FOUR; j++) {
            if (map[x][j] == who) {
                System.out.println("x:"+x+" y:"+j);
                count++;
            } else {
                break;
            }
        }
        //向左边统计
        for (j = y-1; j > 0 && j > y - 1 - FOUR; j--) {
            if (map[x][j] == who) {
                System.out.println("x:"+x+" y:"+j);
                count++;
            } else {
                break;
            }
        }

        return count >= 5;
    }

    /**
     * 横向统计
     */
    private boolean heng(int who,int[][] map,int x,int y){
        int count = 0;
        int i ;
        System.out.println("横向统计");
        // 向右边统计
        for ( i=x ; i < map.length && i <= x + FOUR; i++) {
            if (map[i][y] == who) {
                System.out.println("x:"+i+" y:"+y);
                count++;
            } else {
                break;
            }
        }
        //向左边统计
        for (i = x-1; i > 0 && i > x - 1 - FOUR; i--) {
            if (map[i][y] == who) {
                System.out.println("x:"+i+" y:"+y);
                count++;
            } else {
                break;
            }
        }

        return count >= 5;

    }

}
