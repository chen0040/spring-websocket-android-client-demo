package com.github.chen0040.ws.services;



import com.github.chen0040.ws.models.AuditEvent;

import java.util.List;
import java.util.Map;


/**
 * Created by xschen on 18/9/2017.
 */
public interface AuditEventService {
   void sendMessage(String category, String name, String summary);
   void sendError(String category, String name, String summary);
   void sendWarning(String category, String name, String summary);

   AuditEvent getLastEvent();
   Map<String, Long> getEventCountHistogram();
   long getCounter();

   List<AuditEvent> getLastEvents();
}
