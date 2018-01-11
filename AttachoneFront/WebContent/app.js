var app = angular.module('myApp',['ngRoute']);

var BASE_URL = 'http://localhost:8080/AttachOneBackend'

app.config(function($routeProvider){
	
	$routeProvider
	
	.when('/',{
		templateUrl : 'home.html',
		controller : 'homeController'
	})
	.when('/home',{
		templateUrl : 'home.html',
		controller : 'homeController'
	})
	.when('/signup',{
		templateUrl: 'signup.html',
	    controller: 'signupController'
	})
	.when('/blog',{
		templateUrl: 'blog.html',
	    controller: 'blogController'
	})
	.when('/blog/:param',{
		templateUrl: 'blog.html',
	    controller: 'blogController'
	})
	.when('/job',{
		templateUrl: 'job.html',
	    controller: 'jobController'
	})
	.when('/forum',{
		templateUrl: 'forum.html',
	    controller: 'forumController'
	})
	.when('/forum/:param',{
		templateUrl: 'forum.html',
	    controller: 'forumController'
	})
	.when('/profile',{
		templateUrl: 'profile.html',
	    controller: 'profileController'
	})
	.when('/profile/:param',{
		templateUrl: 'profile.html',
	    controller: 'profileController'
	})
	.when('/friend',{
		templateUrl: 'friend.html',
	    controller: 'friendController'
	})
	.when('/chat',{
		templateUrl: 'chat.html',
	    controller: 'chatController'
	})
	.when('/aboutus',{
		templateUrl: 'aboutus.html',
	    controller: 'aboutusController'
	})
	
});

app.service('dataService', function() {

  // private variable
  var _dataObj = {};

  // public API
  this.dataObj = _dataObj;
});

app.controller("navController",['$scope','$location','$window','$http','dataService','$rootScope',function(   $scope , $location, $window , $http , dataService, $rootScope){
	
	console.log('navController');
	
	console.log( $window.sessionStorage.getItem("currentUser") );
	
	$rootScope.LoginStatus = false;
	
	if( $window.sessionStorage.getItem("currentUser") != null && $window.sessionStorage.getItem("currentUser") != undefined )
	{
		$rootScope.LoginEmail = JSON.parse( $window.sessionStorage.getItem("currentUser") ).email;
		$rootScope.LoginRole = JSON.parse( $window.sessionStorage.getItem("currentUser") ).role;
		
		//dataService.dataObj.LoginStatus = true;
		
		$rootScope.LoginStatus = true;
		
	}
	else
	{
		window.setTimeout(function(){
			$("#loginModal").modal('show');
		},1000);
		$rootScope.LoginStatus = false;
	}
	
	$scope.logout = function()
	{
		$window.sessionStorage.clear();
		$rootScope.LoginStatus = false;
		
		$location.path('/home');
		
	}
	
}]);