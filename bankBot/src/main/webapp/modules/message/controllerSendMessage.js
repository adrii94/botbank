'use strict';
 
angular.module('SendMessages')

.controller('SendMessageController', ['$scope', 'SendMessageService', function($scope, SendMessageService){
	var self = this;
	$scope.friend = null;
	$scope.fTemp = {'nombre':'Todos', 'idFaceebook':'-1'};
	$scope.friends = [];
	$scope.friends.push($scope.fTemp);
	$scope.message = 'Mensaje a enviar...'
	
	$scope.fetchAllFriends = function() {
		SendMessageService.fetchAllFriends().then(
				function(d){
					$scope.friends.push.apply($scope.friends,d);
				}, function(errResponse){
					console.error('Error while fetching data');
				}
		);
	};
	
	$scope.sendMessages = function() {
		SendMessageService.sendMessages($scope.friend.idFaceebook, $scope.message).then(
				function(d){
					alert('Mensaje enviado correctamente...');
				}, function(errResponse){
					alert('Ocurrió un error al enviar el mensaje.');
					console.error('Error while fetching data');
				}
		);
	};
	
	
	$scope.fetchAllFriends();
}]);