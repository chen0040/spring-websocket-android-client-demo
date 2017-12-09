(function(){

    var controller = function($timeout, $log, $scope, messageService) {
        var vm = this;
        $scope.events = [];
        vm.activate = function() {
            messageService.subscribe('event', 'eventsController', function(channel, message){
                var eventMessage = JSON.parse(message);
                console.log(eventMessage);

                $timeout((function(_event){
                    return function(){
                        if($scope.events.length < 30) {
                            $scope.events.push({});
                        }

                        for(var i=$scope.events.length-1; i > 0; --i) {
                            $scope.events[i] = $scope.events[i-1];
                        }
                        $scope.events[0] = _event;
                     };
                })(eventMessage), 100);
            });
        };

        vm.activate();
    };

    var app = angular.module('client');
    app.controller("eventsController", ['$timeout', '$log', '$scope', 'messageService', controller]);


})();
