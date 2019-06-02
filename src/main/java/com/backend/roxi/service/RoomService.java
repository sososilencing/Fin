package com.backend.roxi.service;

import com.backend.roxi.bean.Chess;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author Roxi酱
 */
@Service
public class RoomService {


    /**
     * 1.加入房间
     * @param rooms
     * @param room
     * @param id
     * @return
     * @throws JSONException
     */
    public Map<String, List<Integer>> getRoom(Map<String, List<Integer>> rooms, String room, int id) {
        List<Integer> users = null;
        users=rooms.get(room);
        if(users==null){
            users=new ArrayList<>();
            users.add(id);
            rooms.put(room,users);
        }else {
            if(users.size()<2){
                users.add(id);
                rooms.put(room,users);
            }else {
               return null;
            }
        }
        return rooms;
    }

    /**
     * 改变是否准备的状态
     * @param id
     * @param states
     * @return
     */
    public Map<Integer,String> getState(int id, Map<Integer,String> states){
        String state;
           state=states.get(id);
       if("Y".equals(state)){
           states.put(id,"N");
       }else {
           states.put(id,"Y");
       }
       return states;
    }

    /**
     * 下一步棋
     * @param map
     * @param chess
     * @param room
     * @return
     */
    public int[][] changeMap(int[][] map,Chess chess,String room,int who){
        if(map==null){
            map=new int[16][16];
        }
        map[chess.getX()][chess.getY()] = who;
        return map;
    }
}
