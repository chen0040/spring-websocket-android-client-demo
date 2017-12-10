package chen0040.github.com.androidspringsecurity;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.chen0040.sbclient.SpringBootWebSocketClient;

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
      client.connect("ws://localhost:8080/my-ws");
      Thread.sleep(60000L);
      client.disconnect();
   }


}
