package com.github.chen0040.ws.components;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.chen0040.ws.services.AuditEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by xschen on 16/9/2017.
 *
 * A simple scheduler that tries to ping the web client every 10 seconds via web-socket
 */
@Component
public class WebSocketPingScheduler {

   @Autowired
   private AuditEventService service;

   private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

   @Scheduled(fixedDelay = 10000L)
   public void webSocketPing() {
      service.sendMessage("ping", "web-socket-ping", "ping at time " + dateFormat.format(new Date()));
   }
}
