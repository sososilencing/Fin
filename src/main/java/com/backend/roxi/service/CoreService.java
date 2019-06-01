package com.backend.roxi.service;

/**
 * @author Roxi酱
 */
public class CoreService {

    final int FOUR=4;
    /**
     * 五子棋 判断是否逻辑胜利
     */
    public boolean victory(int who,int[][] map,int x,int y) {
        boolean flag=heng(who, map,x,y);
        if(flag){
            return true;
        }
        flag=shu(who, map,x,y);

        if(flag){
            return true;
        }
        return false;
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

        if(count>=5){
            return true;
        }
        return false;
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

        if(count>=5){
            return true;
        }
        
        return false;
    }

}
