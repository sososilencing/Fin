package com.backend.roxi.controller;

import com.backend.roxi.bean.Chess;
import com.backend.roxi.bean.User;
import com.backend.roxi.config.SocketConfig;
import com.backend.roxi.service.CoreService;
import com.backend.roxi.service.RoomService;
import com.backend.roxi.utils.ToCast;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpSession;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author Roxi酱
 */

@ServerEndpoint(value = "/room",configurator = SocketConfig.class)
@Controller
public class RoomSocket {


    private static RoomService roomService;

    @Autowired
    public void setRoomService(RoomService roomService,CoreService coreService) {
        RoomSocket.roomService = roomService;
    }

    private HttpSession httpSession;
    private Session session;
    private static CopyOnWriteArraySet<RoomSocket> roomSockets=new CopyOnWriteArraySet<>();
    private User user;

    /**
     * 人对应在的哪个房间
     */
    protected static Map<String,String> users=new HashMap<>();

    /**
     * 房间号 以及房间里面的人
     */
    protected static Map<String, List<Integer>> rooms=new HashMap<>();

    /**
     * 每个用户的状态
     */
    private static Map<Integer,String> states=new HashMap<>();

    /**
     *每个id都对应一个session
     */
    protected static Map<Integer,Session> userSession=new HashMap<>();

    @OnOpen
    public void onOpen(Session session, EndpointConfig ec) throws IOException {
        this.session=session;
        System.out.println(session.getId()+"打开了websocket");

        roomSockets.add(this);

        session.getBasicRemote().sendText("欢迎进入游戏大厅");

       this.httpSession= (HttpSession) ec.getUserProperties().get(HttpSession.class.getName());
       this.user= (User) httpSession.getAttribute("user");
        userSession.put(user.getId(),this.session);
    }

    /**
     *  1是加入房间 2是准备模式 3是进入游戏了
     * @param message
     * @throws IOException
     * @throws JSONException
     */
    @OnMessage
    public void onMessage(String message) throws IOException {
        try {
            JSONObject jsonObject = new JSONObject(message);
            System.out.println("----------------");
            System.out.println(jsonObject.toString());
            String type = jsonObject.getString("type");
            if ("1".equals(type)) {
                String room = jsonObject.getString("room");
                Map<String, List<Integer>> ddrooms=roomService.getRoom(rooms, room, user.getId());
                if(ddrooms==null){
                    this.session.getBasicRemote().sendText("房间:" + room + "加入失败");
                }else {
                    rooms=ddrooms;
                    users.put(ToCast.stringCast(user.getId()),room);
                    this.session.getBasicRemote().sendText("你目前在房间:" + room);
                }
            }

            else if ("2".equals(type)) {
                states = roomService.getState(user.getId(), states);
                String action = isStart(user.getId());
                System.out.println(action);
                if ("No".equals(action)) {
                    this.session.getBasicRemote().sendText("未加入房间，请先加入房间");
                } else if ("Yes".equals(action)) {
                    String room = users.get(String.valueOf(user.getId()));
                   List<Integer> ids=rooms.get(room);
                   for(int id:ids){
                       userSession.get(id).getBasicRemote().sendText("对战即将开始");
                   }
                } else {
                    String room = users.get(String.valueOf(user.getId()));
                    List<Integer> ids=rooms.get(room);
                    for(int id:ids){
                        userSession.get(id).getBasicRemote().sendText("还有玩家没有准备");
                    }
                }
                System.out.println(states);
            }

            else {
                String context = jsonObject.getString("context");
                context = context.replaceAll("<", "&lt;");
                context = context.replaceAll(">", "&gt;");
                for (RoomSocket item : roomSockets) {
                    item.session.getBasicRemote().sendText(user.getName() + ":" + context);
                }
            }
        }catch (JSONException e) {

        }
    }


    @OnClose
    public void onClose(){
        System.out.println(session.getId()+"弹出了socket");
        roomSockets.remove(this);
    }


    public String isStart(int uid){
        String room=users.get(ToCast.stringCast(uid));
        if(room==null){
           return "No";
        }else {
            int count=0;
            List<Integer> list=rooms.get(room);
            for(Integer integer:list){
                if("Y".equals(states.get(integer))){
                    count++;
                }
            }
            System.out.println(count);
            if(count==2){
                return "Yes";
            }
        }
        return "Ready";
    }
}
