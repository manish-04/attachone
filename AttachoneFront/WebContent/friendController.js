app.controller("friendController",['$scope','$location','$window','$http','$rootScope',function(   $scope , $location, $window , $http,$rootScope){
	
	if( $window.sessionStorage.getItem("currentUser") == null || $window.sessionStorage.getItem("currentUser") == undefined )
	{
		
		$('.modal').modal('hide');
		$('.modal-backdrop').remove();
		$location.path("/");
	}
	
	console.log('friendController');
	
	$scope.AllUsers = [];
	
	$scope.userData = null;
	
	$scope.AddFriend = function(arg1,arg2)
		{
			var json = 
					{
					'SourceEmail': arg1,
					'TargetEmail': arg2
					};
			
			console.log(json);
			//alert(json);
			
			$http({method:'post',url:BASE_URL + '/addFriend', data: json, headers: {'Content-Type': 'application/json'}}).then(function(data){
				console.log( data )
				
				switch( data.data.msg )
				{
				case 'Success':
					
					swal("Friend Request Sent", "", "success")
					
					$scope.fetchAllUsers();
					
					break;
					
				case 'Failure':
					swal("Failed", "Something went wrong!", "error")
					break;
				}
				
			},function(data){
				console.log( data )
				
				switch( data.data.msg )
				{
				case 'Success':
					
					swal("Friend Request Sent", "", "success")
					
					$scope.fetchAllUsers();
					
					break;
					
				case 'Failure':
					swal("Failed", "Something went wrong!", "error")
					break;
				}
			});
		}
	
	$scope.UndoFriend = function(arg1,arg2)
	{
		var json = 
				{
				'SourceEmail': arg1,
				'TargetEmail': arg2
				};
		
		console.log(json);
		//alert(json);
		
		$http({method:'post',url:BASE_URL + '/undoFriend', data: json, headers: {'Content-Type': 'application/json'}}).then(function(data){
			console.log( data )
			
			switch( data.data.msg )
			{
			case 'Success':
				
				swal("Friend Request Undone", "", "success")
				
				$scope.fetchAllUsers();
				
				break;
				
			case 'Failure':
				swal("Failed", "Something went wrong!", "error")
				break;
			}
			
		},function(data){
			console.log( data )
			
			switch( data.data.msg )
			{
			case 'Success':
				
				swal("Friend Request Undone", "", "success")
				
				$scope.fetchAllUsers();
				
				break;
				
			case 'Failure':
				swal("Failed", "Something went wrong!", "error")
				break;
			}
		});
	}
	
	$scope.AcceptFriend = function(arg1,arg2)
	{
		var json = 
				{
				'SourceEmail': arg1,
				'TargetEmail': arg2
				};
		
		console.log(json);
		//alert(json);
		
		$http({method:'post',url:BASE_URL + '/acceptFriend', data: json, headers: {'Content-Type': 'application/json'}}).then(function(data){
			console.log( data )
			
			switch( data.data.msg )
			{
			case 'Success':
				
				swal("Friend Request Accepted", "", "success")
				
				$scope.fetchAllUsers();
				
				break;
				
			case 'Failure':
				swal("Failed", "Something went wrong!", "error")
				break;
			}
			
		},function(data){
			console.log( data )
			
			switch( data.data.msg )
			{
			case 'Success':
				
				swal("Friend Request Accepted", "", "success")
				
				$scope.fetchAllUsers();
				
				break;
				
			case 'Failure':
				swal("Failed", "Something went wrong!", "error")
				break;
			}
		});
	}
	
	$scope.UnFriend = function(arg1,arg2)
	{
		var json = 
				{
				'SourceEmail': arg1,
				'TargetEmail': arg2
				};
		
		console.log(json);
		//alert(json);
		
		$http({method:'post',url:BASE_URL + '/unFriend', data: json, headers: {'Content-Type': 'application/json'}}).then(function(data){
			console.log( data )
			
			switch( data.data.msg )
			{
			case 'Success':
				
				swal("Friend Removed", "", "success")
				
				$scope.fetchAllUsers();
				
				break;
				
			case 'Failure':
				swal("Failed", "Something went wrong!", "error")
				break;
			}
			
		},function(data){
			console.log( data )
			
			switch( data.data.msg )
			{
			case 'Success':
				
				swal("Friend Removed", "", "success")
				
				$scope.fetchAllUsers();
				
				break;
				
			case 'Failure':
				swal("Failed", "Something went wrong!", "error")
				break;
			}
		});
	}
	
	$scope.fetchAllUsers = function(){
	
		$http({method:'get',url:BASE_URL + '/fetchAllUsers', headers: {'Content-Type': 'application/json'}})
		.then(function(resp){
			console.log( resp.data )
			//console.log( resp.data )
		
			$scope.AllUsers = resp.data;
			
			for( var i = 0 ; i < $scope.AllUsers.length ; i++ )
			{
				if( $scope.AllUsers[i].email == $rootScope.LoginEmail )
				{
					$scope.userData = $scope.AllUsers[i];
					break;
				}
			}
			
			console.log( $scope.userData );
			
		},function(resp){
			
			console.log( "fetchAllUsers Error" )
		});
		
	}
	
	$scope.fetchAllUsers();
	
}]);