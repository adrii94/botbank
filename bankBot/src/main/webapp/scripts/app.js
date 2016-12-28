'use strict';

// declare modules
angular.module('Authentication', []);
angular.module('Home', []);
angular.module('AuthenticationBot', []);
angular.module('SendMessages',[]);

angular.module('EverisBot', [
    'Authentication',
    'Home',
    'AuthenticationBot',
    'SendMessages',
    'ngRoute',
    'ngCookies'
])
 
.config(['$routeProvider', function ($routeProvider) {
    $routeProvider
        .when('/login', {
            controller: 'LoginController',
            templateUrl: 'modules/authentication/views/login.html',
            hideMenus: true
        })
 
        .when('/', {
            controller: 'HomeController',
            templateUrl: 'modules/home/views/home.html'
        })
        
        .when('/loginBot', {
            controller: 'LoginBotController',
            templateUrl: 'modules/authenticationBot/views/login.html'
        })
        
        .when('/messages',{
        	controller: 'SendMessageController',
            templateUrl: 'modules/message/views/start.html'
        })
 
        .otherwise({ redirectTo: '/login' });
}])
 
.run(['$rootScope', '$location', '$cookieStore', '$http',
    function ($rootScope, $location, $cookieStore, $http) {
        // keep user logged in after page refresh
        $rootScope.globals = $cookieStore.get('globals') || {};
        if ($rootScope.globals.currentUser) {
            $http.defaults.headers.common['Authorization'] = 'Basic ' + $rootScope.globals.currentUser.authdata; // jshint ignore:line
        }
 
        $rootScope.$on('$locationChangeStart', function (event, next, current) {
            // redirect to login page if not logged in
        	
        	if ($location.path() == '/loginBot' ) {
                $location.path('/loginBot');
            }else  if ($location.path() !== '/login' && !$rootScope.globals.currentUser) {
                $location.path('/login');
            }
        		
        });
    }]);