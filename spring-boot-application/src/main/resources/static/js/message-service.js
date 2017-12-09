(function(){
    var service = function($log) {
        var routees = {};
        var route = function(channel, message) {
            if(channel in routees) {
                var subscribers = routees[channel];
                for(var subscriberId in subscribers) {
                    var subscriber = subscribers[subscriberId];
                    subscriber(channel, message);
                }
            }
        };

        var subscribe = function(channel, subscriberId, subscriber) {
            if(!(channel in routees)) {
                routees[channel] = {};
            }

            routees[channel][subscriberId] = subscriber;
        };

        return {
            route : route,
            subscribe : subscribe
        };
    };

    var module = angular.module('client');
    module.factory('messageService', ['$log', service]);
})();
