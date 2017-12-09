# spring-websocket-android-client-demo

Demo of connecting android client to spring web application via websocket

# Usage

### WebSocket Spring Boot Server

git clone this project, run the "./make.ps1" powershell script in the project root directory to build spring-boot-application.jar
into the "bin" folder.

Run the following command to start the spring-boot-application at http://localhost:8080

```bash
java -jar bin/spring-boot-application.jar
```

The spring-boot-application defines an end point at http://localhost:8080/my-ws and sends a ping message to any connected client that subscribe to its topic "/topics/event" every 10 seconds. the angularjs demo can be viewed by navigating to http://localhost:8080 on your web browser.

### Android WebSocket Client


