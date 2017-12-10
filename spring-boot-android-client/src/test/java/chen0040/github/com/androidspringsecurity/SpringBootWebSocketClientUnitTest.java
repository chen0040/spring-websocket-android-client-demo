package chen0040.github.com.androidspringsecurity;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.chen0040.sbclient.SpringBootWebSocketClient;
import com.github.chen0040.sbclient.StompMessage;
import com.github.chen0040.sbclient.StompMessageListener;
import com.github.chen0040.sbclient.TopicHandler;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;


/**
 * Created by xschen on 12/6/2017.
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest= Config.NONE, sdk = 23)
public class SpringBootWebSocketClientUnitTest extends AndroidLogContext {



   @Test
   public void testWebSocket() throws InterruptedException {
      SpringBootWebSocketClient client = new SpringBootWebSocketClient();
      TopicHandler handler = client.subscribe("/topics/event");
      handler.addListener(new StompMessageListener() {
         @Override
         public void onMessage(StompMessage message) {
            System.out.println(message.getHeader("destination") + ": " + message.getContent());
         }
      });
      client.connect("ws://localhost:8080/my-ws/websocket");
      Thread.sleep(60000L);
      client.disconnect();
   }


}
