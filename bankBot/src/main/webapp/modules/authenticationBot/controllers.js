'use strict';
 
angular.module('AuthenticationBot')
 
.controller('LoginBotController',
    ['$scope', '$rootScope', '$window','$location', '$routeParams', 'AuthenticationBotService',
    function ($scope, $rootScope, $window, $location, $routeParams, AuthenticationBotService) {
        // reset login status
        AuthenticationBotService.ClearCredentials();
        var self = this;
        self.absURL = $location.absUrl();
        self.accountToken  = self.absURL.substring(self.absURL.indexOf('account_linking_token=')+'account_linking_token='.length, self.absURL.indexOf('&redirect_uri='));
        $scope.login = function () {
            $scope.dataLoading = true;
            AuthenticationBotService.Login($scope.username, $scope.password, function(response) {
                if(response.success) {
                	$window.location.href = 'https://www.facebook.com/messenger_platform/account_linking?account_linking_token='+self.accountToken+
    					'&authorization_code=1234';
                } else {
                    $scope.error = response.message;
                    $scope.dataLoading = false;
                    
                    $window.location.href = 'https://www.facebook.com/messenger_platform/account_linking?account_linking_token='+self.accountToken;
                }
            });
        };
    }]);