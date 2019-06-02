package com.backend.roxi.controller;

import com.backend.roxi.bean.Chess;
import com.backend.roxi.bean.User;
import com.backend.roxi.config.SocketConfig;
import com.backend.roxi.service.CoreService;
import com.backend.roxi.service.RoomService;
import com.backend.roxi.utils.ToCast;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Roxi酱
 */
@ServerEndpoint(value = "/game",configurator = SocketConfig.class)
@Controller
public class GameRoomSocket {

    private Session session;
    private HttpSession httpSession;
    private User user;
    private Gson gson = new Gson();


    private static RoomService roomService;
    private static CoreService coreService;


    private static Map<String,Integer> counts=RoomSocket.counts;
    /**
     * 每个房间的游戏盘
     */
    protected static Map<String,int[][]> maps=new HashMap<>();

    /**
     * 人对应在的哪个房间
     */
    private Map<String,String> users=RoomSocket.users;

    /**
     * 这个socket 每个 id 对应的session 通信
     */
    private static Map<Integer,Session> userSession=new HashMap<>();

    /**
     * 房间号 以及房间里面的人
     */
    protected static Map<String, List<Integer>> rooms=RoomSocket.rooms;

    @Autowired
    public void setRoomService(RoomService roomService,CoreService coreService) {
        GameRoomSocket.roomService = roomService;
        GameRoomSocket.coreService = coreService;
    }

    @OnOpen
    public void onOpen(Session session,EndpointConfig config){
        System.out.println("打开了游戏房间");
        httpSession= (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        user= (User) httpSession.getAttribute("user");

        userSession.put(user.getId(),session);

    }

    @OnMessage
    public void onMessage(String message,Session session1) throws IOException {
        String room = users.get(ToCast.stringCast(user.getId()));
        System.out.println(room);
        if (room == null) {
            session1.getBasicRemote().sendText("请先加入房间");
        } else {
            List<Integer> userList=rooms.get(room);
            Chess chess = gson.fromJson(message, Chess.class);
            chess.setName(user.getName());
            if(user.getId()==userList.get(0)){
                chess.setWho(1);
            }else {
                chess.setWho(2);
            }
            System.out.println(chess);
            int count=counts.get(room);
            if(count%2==0 && chess.getWho()!=2){
                for(int id:userList) {
                    userSession.get(id).getBasicRemote().sendText("该白方落子");
                }
            }else if(count%2==1 && chess.getWho()!=1){
                for(int id:userList) {
                    userSession.get(id).getBasicRemote().sendText("该黑方落子");
                }
            }else {
                int[][] map = maps.get(room);
                if (map != null && map[chess.getX()][chess.getY()] != 0) {
                    for (int id : userList) {
                        if (chess.getWho() == 1) {
                            userSession.get(id).getBasicRemote().sendText("黑方落子于：x，y -->[" + chess.getX() + "," + chess.getY() + "] 是错误的，请重下");
                        } else {
                            userSession.get(id).getBasicRemote().sendText("白方落子于：x，y -->[" + chess.getX() + "," + chess.getY() + "] 是错误的，请重下");
                        }
                    }
                } else {
                    counts.put(room,counts.get(room)+1);
                    map = roomService.changeMap(map, chess, room, chess.getWho());
                    maps.put(room, map);
                    System.out.println(room);
                    System.out.println("who:" + chess.getWho());
                    if (coreService.victory(chess.getWho(), maps.get(room), chess.getX(), chess.getY())) {
                        map = new int[16][16];
                        counts.put(room,1);
                        for (int id : userList) {
                            if (chess.getWho() == 1) {
                                userSession.get(id).getBasicRemote().sendText("黑方win");
                            } else {
                                userSession.get(id).getBasicRemote().sendText("白方win");
                            }
                        }
                    } else {
                        for (int id : userList) {
                            if (chess.getWho() == 1) {
                                userSession.get(id).getBasicRemote().sendText("黑方落子于：x，y -->[" + chess.getX() + "," + chess.getY() + "]");
                            } else {
                                userSession.get(id).getBasicRemote().sendText("白方落子于：x，y -->[" + chess.getX() + "," + chess.getY() + "]");
                            }
                        }
                    }
                }
            }
        }
    }


    @OnClose
    public void onClose(){

    }

}
