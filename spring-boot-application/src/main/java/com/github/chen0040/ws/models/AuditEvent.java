package com.github.chen0040.ws.models;


import lombok.Getter;
import lombok.Setter;

import java.util.Date;


/**
 * Created by xschen on 18/9/2017.
 */
@Getter
@Setter
public class AuditEvent {
   private Date time = new Date();
   private String name;
   private String description;
   private String category;
   private long count;
   private String level = "info";

   public AuditEvent(){

   }

   public AuditEvent(String category, String description) {
      this.category = category;
      this.description = description;
   }
}
