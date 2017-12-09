(function(){
    var service = function($log, $http, $location, $interval, $timeout) {

        var stompClient = null;
        var socket = null;
        var connected = false;
        var thisCallback = null;
        var thisUrl = "/my-ws";
        var thisIntervalHandler = null;
        var thisCounter = 0;

        function stompConnect() {
            $log.debug('STOMP: Attempting connection');
            stompClient = null;
            connect(thisCallback);
        }

        var setUrl = function(newUrl) {
            thisUrl = newUrl;
        };

        var connectToHost = function() {
            var url = thisUrl;

            $log.debug('connecting to ' + url);

            socket = new SockJS(url);
            stompClient = Stomp.over(socket);
            stompClient.connect({}, function(frame) {
                connected = true;

                if(thisCallback) thisCallback('connect', connected);
                stompClient.subscribe('/topics/event', function(message) {
                    if(thisCallback) thisCallback('event', message.body);
                });


            }, function (error) {
               $log.debug('STOMP: ' + error);
               setTimeout(stompConnect, 5000);
               $log.debug('STOMP: Reconecting in 5 seconds');
            });
        };

        var connect = function(callback) {
            thisCallback = callback;

            if(stompClient == null) {
                connectToHost();
            }
        };

        var disconnect = function() {
            if (stompClient != null) {
                stompClient.disconnect();
            }
            if (thisIntervalHandler != null) {
                thisIntervalHandler.cancel();
                thisIntervalHandler = null;
            }
            connected = false;
            $log.debug("Disconnected");
        };

        var send = function(topic, obj) {
            if(stompClient != null){
                stompClient.send(topic, {}, JSON.stringify(obj));
            }
        };

        var afterConnected = function(callback) {
            if(connected) {
                if(callback) callback();
            } else {
                $timeout(function(){
                    $log.debug('waiting to connect ...');
                    afterConnected(callback);
                }, 1000);
            }
        };

        return {
            connect : connect,
            disconnect : disconnect,
            send : send,
            setUrl : setUrl,
            afterConnected : afterConnected
        };
    };

	var module = angular.module('client');
	module.factory('socketService', ['$log', '$http', '$location', '$interval', '$timeout', service]);
})();
