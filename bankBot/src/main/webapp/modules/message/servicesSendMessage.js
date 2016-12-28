'use strict';

angular.module('SendMessages')
.factory('SendMessageService', ['$http','$q', function($http, $q){
	
	 var service = {};
	service.fetchAllFriends = function(){
			return $http.get('https://everisbot-digitallab.rhcloud.com/rest/data/friendsList')
            .then(
                    function(response){
                        return response.data;
                    }, 
                    function(errResponse){
                        console.error('Error while fetching friends');
                        return $q.reject(errResponse);
                    }
            );
		};
	
	service.sendMessages = function(sender, message){
		return $http.get('https://everisbot-digitallab.rhcloud.com/rest/facebook/message',{params:{"sender": sender, "mensaje": message}})
        .then(
                function(response){
                    return response.data;
                }, 
                function(errResponse){
                    console.error('Error while send message');
                    return $q.reject(errResponse);
                }
        );
	};
		
	return service;
} ])