package com.github.chen0040.ws.services;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.chen0040.ws.models.AuditEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;


/**
 * Created by xschen on 18/9/2017.
 */
@Service
public class AuditEventServiceImpl implements AuditEventService {

   private ConcurrentMap<String, Long> histogram = new ConcurrentHashMap<>();
   private ConcurrentLinkedQueue<AuditEvent> eventQueue = new ConcurrentLinkedQueue<>();

   private AuditEvent lastEvent = new AuditEvent();

   @Autowired
   private SimpMessagingTemplate brokerMessagingTemplate;

   private long counter = 1;

   @Override
   public void sendMessage(String category, String name, String description) {
      AuditEvent auditEvent = new AuditEvent();
      auditEvent.setCategory(category);
      auditEvent.setDescription(description);
      auditEvent.setName(name);
      auditEvent.setTime(new Date());
      auditEvent.setCount(counter++);

      enqueue(auditEvent);

      histogram.put(category, histogram.getOrDefault(category, 0L) + 1);

      lastEvent = auditEvent;

      brokerMessagingTemplate.convertAndSend("/topics/event", JSON.toJSONString(auditEvent, SerializerFeature.BrowserCompatible));
   }

   private void enqueue(AuditEvent auditEvent) {
      eventQueue.add(auditEvent);

      if(eventQueue.size() > 300) {
         eventQueue.remove();
      }
   }

   @Override
   public void sendError(String category, String name, String description) {
      AuditEvent auditEvent = new AuditEvent();
      auditEvent.setCategory(category);
      auditEvent.setDescription(description);
      auditEvent.setName(name);
      auditEvent.setTime(new Date());
      auditEvent.setCount(counter++);
      auditEvent.setLevel("Error");

      enqueue(auditEvent);

      histogram.put(category, histogram.getOrDefault(category, 0L) + 1);

      lastEvent = auditEvent;

      brokerMessagingTemplate.convertAndSend("/topics/event", JSON.toJSONString(auditEvent, SerializerFeature.BrowserCompatible));
   }

   @Override
   public void sendWarning(String category, String name, String description) {
      AuditEvent auditEvent = new AuditEvent();
      auditEvent.setCategory(category);
      auditEvent.setDescription(description);
      auditEvent.setName(name);
      auditEvent.setTime(new Date());
      auditEvent.setCount(counter++);
      auditEvent.setLevel("Warning");

      enqueue(auditEvent);

      histogram.put(category, histogram.getOrDefault(category, 0L) + 1);

      lastEvent = auditEvent;

      brokerMessagingTemplate.convertAndSend("/topics/event", JSON.toJSONString(auditEvent, SerializerFeature.BrowserCompatible));
   }

   @Override public AuditEvent getLastEvent() {
      return lastEvent;
   }


   @Override public Map<String, Long> getEventCountHistogram() {
      return histogram;
   }

   @Override public long getCounter() {
      return counter;
   }


   @Override public List<AuditEvent> getLastEvents() {
      return new ArrayList<>(eventQueue);
   }
}
