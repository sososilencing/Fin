package com.backend.roxi.controller;

import com.backend.roxi.config.SocketConfig;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.websocket.server.ServerEndpoint;

/**
 * @author Roxi酱
 */

@ServerEndpoint(value = "/room/{num}",configurator = SocketConfig.class)
@Controller
public class RoomSocket {
}
