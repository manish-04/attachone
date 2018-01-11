app.directive('myEnter', function () {
    return function (scope, element, attrs) {
        element.bind("keydown keypress", function (event) {
            if(event.which === 13) {
                scope.$apply(function (){
                    scope.$eval(attrs.myEnter);
                });

                event.preventDefault();
            }
        });
    };
});

app.filter('reverse', function() {
	  return function(items) {
	    return items.slice().reverse();
	  };
	});

app.controller("chatController",['$scope','$location','$window','$http','$rootScope',function(   $scope , $location, $window , $http, $rootScope){
	
	console.log('stompController');

	if( $window.sessionStorage.getItem("currentUser") == null || $window.sessionStorage.getItem("currentUser") == undefined )
	{
		
		$('.modal').modal('hide');
		$('.modal-backdrop').remove();
		$location.path("/");
	}
	
	$scope.currUser = $rootScope.LoginEmail;
	
	var json = 
	{
	'Email': $rootScope.LoginEmail
	};

	console.log(json);
	//alert(json);
	
	$scope.user = null;
	
	$scope.Friends = [];
	
	$scope.AllChats = [{Name:'All',Msg:"",messages:[]}];
	
	$scope.addChat = function(arg) {
		
		found = false;
		
		for( var i = 0 ; i < $scope.AllChats.length ; i++ )
		{
			if( $scope.AllChats[i].Name == arg )
				{
				found = true;
				break;
				}
				
		}
		
		if( !found )
			$scope.AllChats.push( {Name:arg,Msg:"",messages:[]} );
	}
	
	$scope.removeChat = function(arg) {
		
		found = false;
		index = -1;
		
		for( var i = 0 ; i < $scope.AllChats.length ; i++ )
		{
			if( $scope.AllChats[i].Name == arg )
				{
				found = true;
				index = i;
				break;
				}
				
		}
		
		if( found )
			$scope.AllChats.splice( index , 1 );
	}
	
	$scope.addMessage = function(arg) {
		
		found = false;
		index = -1;
		
		for( var i = 0 ; i < $scope.AllChats.length ; i++ )
		{
			if( $scope.AllChats[i].Name == arg.Name )
				{
				found = true;
				index = i;
				break;
				}
			
			if( $scope.AllChats[i].Name == arg.by )
			{
			found = true;
			index = i;
			break;
			}
				
			
		}
		
		if( found )
			$scope.AllChats[index].messages.push( arg );
		else
		{
			$scope.AllChats.push( {Name:arg.Name,Msg:"",messages:[arg]} );
		}
		
		window.setTimeout( function(){
		
			var i, tabcontent, tablinks;
		    tabcontent = document.getElementsByClassName("tabcontent");
		    for (i = 0; i < tabcontent.length; i++) {
		        tabcontent[i].style.display = "none";
		    }
		    tablinks = document.getElementsByClassName("tablinks");
		    for (i = 0; i < tablinks.length; i++) {
		        tablinks[i].className = tablinks[i].className.replace(" active", "");
		    }
		    document.getElementById(arg.Name).style.display = "block";
		    
		    //document.getElementById("tablinks_"+arg.Name).className += " active";
		    
			
		},1000 );
		
	}
	
	$scope.openCity = function($evt, arg)
	{
		var i, tabcontent, tablinks;
	    tabcontent = document.getElementsByClassName("tabcontent");
	    for (i = 0; i < tabcontent.length; i++) {
	        tabcontent[i].style.display = "none";
	    }
	    tablinks = document.getElementsByClassName("tablinks");
	    for (i = 0; i < tablinks.length; i++) {
	        tablinks[i].className = tablinks[i].className.replace(" active", "");
	    }
	    document.getElementById(arg).style.display = "block";
	    
	    $evt.currentTarget.className += " active";
	}
	
	$http({method:'post',url:BASE_URL + '/fetchUserData', data: json, headers: {'Content-Type': 'application/json'}}).then(function(data){
		console.log( data )
		
		$scope.Friends = data.data.friends;
	
	},function(data){
		console.log( data )
	
	});
	
	$scope.currMsg = '';
	
	$scope.sendMessage = function(by,index)
	{
		console.log('Enter Pressed');
		
		if( $scope.AllChats[index].Msg != '' )
		{
			//$scope.messages.push({Name:'vasudev89@yahoo.com',Message: $scope.currMsg});
		
			stompClient.send( "/app/chat", {}, JSON.stringify({by: $rootScope.LoginEmail, Name: by,Message: $scope.AllChats[index].Msg,type:"To"}));
			
			$scope.AllChats[index].Msg = '';
		}
	}
	
	$scope.messages = [];
	
	var stompClient = null;

	var socket = new SockJS(BASE_URL + '/farzichatSocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/msgs', function (greeting) {
            console.log( greeting );
            
            $scope.$apply( $scope.addMessage( JSON.parse(greeting.body) ) ) ;
        });
        
        /*window.setTimeout(function(){
        	stompClient.send( "/app/chat", {}, JSON.stringify({'msg': 'vasudev'}));
        },2000);*/
        
    });
}]);