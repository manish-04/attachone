app.controller("forumController",['$scope','$location','$window','$http','$rootScope', '$routeParams',function(   $scope , $location, $window , $http, $rootScope, $routeParams){

	console.log("forumController");
	
	$scope.$routeParams = $routeParams;
	//$root.LoginRole = $rootScope.LoginRole;
	
	if( $window.sessionStorage.getItem("currentUser") == null || $window.sessionStorage.getItem("currentUser") == undefined )
	{
		
		$('.modal').modal('hide');
		$('.modal-backdrop').remove();
		$location.path("/");
	}
	
	$scope.filterVal = '';
	
	$scope.setFilterVal = function(arg)
	{
		console.log(arg);
		$scope.filterVal = arg;
	}
	
	$scope.AllForums = [];
	
	$http({method:'get',url:BASE_URL + '/fetchAllForums', headers: {'Content-Type': 'application/json'}})
	.then(function(resp){
		console.log( resp.data )
	
		$scope.AllForums = resp.data;
	},function(resp){
		
		console.log( "fetchAllForums Error" )
	});
	
	$scope.CheckForums = function( arg )
	{
		for( var i = 0 ; i < $scope.AllForums.length ; i++ )
		{
			if( $scope.AllForums[i].ownerId == arg )
				return true;
		}
		
		return false;
	}
	
	$scope.CheckApprovedForums = function( arg )
	{
		console.log(arg);
		
		for( var i = 0 ; i < $scope.AllForums.length ; i++ )
		{
			if( $scope.AllForums[i].ownerId == arg && $scope.AllForums[i].approved)
				return true;
		}
		
		return false;
	}
	
	$scope.Title = 
	{ 
			value: '',
			error : true,
			touched : false,
			validate: function(){
				this.touched = true;
				var reg = /^.{2,20}$/;
				this.error = !reg.test( this.value );
				
				console.log( this.error );
				
			}
	}
	
	$scope.Description = 
	{ 
			value: '',
			error : true,
			touched : false,
			validate: function(){
				this.touched = true;
				var reg = /^.{2,160}$/;
				this.error = !reg.test( this.value );
			}
	}
	
	$scope.ForumComment = function(arg1,arg2)
	{
		var json = 
		{
		'ownerId': $rootScope.LoginEmail,
		'id': arg1,
		'post': arg2,
		
		};

		console.log(json);
		//alert(json);
		
		$http({method:'post',url:BASE_URL + '/addForumComment', data: json, headers: {'Content-Type': 'application/json'}}).then(function(data){
			console.log( data )
			
			switch( data.data.msg )
			{
			case 'Success':
				
				swal("Forum Updated", "Congratulations", "success")
				
				$http({method:'get',url:BASE_URL + '/fetchAllForums', headers: {'Content-Type': 'application/json'}})
				.then(function(resp){
					console.log( resp.data )
				
					$scope.AllForums = resp.data;
				},function(resp){
					
					console.log( "fetchAllForums Error" )
				});
				
				break;
				
			case 'Failure':
				swal("Forum Creation Failure", "Something went wrong!", "error")
				break;
			}
			
		},function(data){
			console.log( data )
			
			switch( data.data.msg )
			{
			case 'Success':
				
				swal("Forum Updated", "Congratulations", "success")
				
				$http({method:'get',url:BASE_URL + '/fetchAllForums', headers: {'Content-Type': 'application/json'}})
				.then(function(resp){
					console.log( resp.data )
				
					$scope.AllForums = resp.data;
				},function(resp){
					
					console.log( "fetchAllForums Error" )
				});
				
				break;
				
			case 'Failure':
				swal("Forum Creation Failure", "Something went wrong!", "error")
				break;
			}
		});
	}
	
	$scope.DeleteComment = function( arg1 , arg2 ){
		
		var json = 
		{
		'id': arg2,
		'date':arg1
		};

		console.log(json);
		//alert(json);
		
		$http({method:'post',url:BASE_URL + '/deleteForumComment', data: json, headers: {'Content-Type': 'application/json'}}).then(function(data){
			console.log( data )
			
			switch( data.data.msg )
			{
			case 'Success':
				
				swal("Forum Updated", "Congratulations", "success")
				
				$http({method:'get',url:BASE_URL + '/fetchAllForums', headers: {'Content-Type': 'application/json'}})
				.then(function(resp){
					console.log( resp.data )
				
					$scope.AllForums = resp.data;
				},function(resp){
					
					console.log( "fetchAllForums Error" )
				});
				
				break;
				
			case 'Failure':
				swal("Forum Update Failure", "Something went wrong!", "error")
				break;
			}
			
		},function(data){
			console.log( data )
			
			switch( data.data.msg )
			{
			case 'Success':
				
				swal("Forum Updated", "Congratulations", "success")
				
				$http({method:'get',url:BASE_URL + '/fetchAllForums', headers: {'Content-Type': 'application/json'}})
				.then(function(resp){
					console.log( resp.data )
				
					$scope.AllForums = resp.data;
				},function(resp){
					
					console.log( "fetchAllForums Error" )
				});
				
				break;
				
			case 'Failure':
				swal("Forum Update Failure", "Something went wrong!", "error")
				break;
			}
		});
		
	}
	
	$scope.AddForum = function()
	{
		var json = 
				{
				'Email': $rootScope.LoginEmail,
				'Title': $scope.Title.value,
				'Description': $scope.Description.value,
				
				};
		
		console.log(json);
		//alert(json);
		
		$http({method:'post',url:BASE_URL + '/addForum', data: json, headers: {'Content-Type': 'application/json'}}).then(function(data){
			console.log( data )
			
			switch( data.data.msg )
			{
			case 'Success':
				
				swal("Forum Created", "Congratulations", "success")
				
				$http({method:'get',url:BASE_URL + '/fetchAllForums', headers: {'Content-Type': 'application/json'}})
				.then(function(resp){
					console.log( resp.data )
				
					$scope.AllForums = resp.data;
				},function(resp){
					
					console.log( "fetchAllForums Error" )
				});
				
				break;
				
			case 'Failure':
				swal("Forum Creation Failure", "Something went wrong!", "error")
				break;
			}
			
		},function(data){
			console.log( data )
			
			switch( data.data.msg )
			{
			case 'Success':
				
				swal("Forum Created", "Congratulations", "success")
				
				$http({method:'get',url:BASE_URL + '/fetchAllForums', headers: {'Content-Type': 'application/json'}})
				.then(function(resp){
					console.log( resp.data )
				
					$scope.AllForums = resp.data;
				},function(resp){
					
					console.log( "fetchAllForums Error" )
				});
				
				break;
				
			case 'Failure':
				swal("Forum Creation Failure", "Something went wrong!", "error")
				break;
			}
		});
	}
	
	$scope.ApproveForum = function(arg)
	{
		var json = 
				{
				'id': arg
				};
		
		console.log(json);
		//alert(json);
		
		$http({method:'post',url:BASE_URL + '/approveForum', data: json, headers: {'Content-Type': 'application/json'}}).then(function(data){
			console.log( data )
			
			switch( data.data.msg )
			{
			case 'Success':
				
				swal("Forum Approved", "Congratulations", "success")
				
				$http({method:'get',url:BASE_URL + '/fetchAllForums', headers: {'Content-Type': 'application/json'}})
				.then(function(resp){
					console.log( resp.data )
				
					$scope.AllForums = resp.data;
				},function(resp){
					
					console.log( "fetchAllForums Error" )
				});
				
				break;
				
			case 'Failure':
				swal("Forum Approval Failure", "Something went wrong!", "error")
				break;
			}
			
		},function(data){
			console.log( data )
			
			switch( data.data.msg )
			{
			case 'Success':
				
				swal("Forum Approved", "Congratulations", "success")
				
				$http({method:'get',url:BASE_URL + '/fetchAllForums', headers: {'Content-Type': 'application/json'}})
				.then(function(resp){
					console.log( resp.data )
				
					$scope.AllForums = resp.data;
				},function(resp){
					
					console.log( "fetchAllForums Error" )
				});
				
				break;
				
			case 'Failure':
				swal("Forum Approval Failure", "Something went wrong!", "error")
				break;
			}
		});
	}
	
	$scope.RejectForum = function(arg)
	{
		var json = 
				{
				'id': arg
				};
		
		console.log(json);
		//alert(json);
		
		$http({method:'post',url:BASE_URL + '/rejectForum', data: json, headers: {'Content-Type': 'application/json'}}).then(function(data){
			console.log( data )
			
			switch( data.data.msg )
			{
			case 'Success':
				
				swal("Forum Rejected", "Congratulations", "success")
				
				$http({method:'get',url:BASE_URL + '/fetchAllForums', headers: {'Content-Type': 'application/json'}})
				.then(function(resp){
					console.log( resp.data )
				
					$scope.AllForums = resp.data;
				},function(resp){
					
					console.log( "fetchAllForums Error" )
				});
				
				break;
				
			case 'Failure':
				swal("Forum Reject Failure", "Something went wrong!", "error")
				break;
			}
			
		},function(data){
			console.log( data )
			
			switch( data.data.msg )
			{
			case 'Success':
				
				swal("Forum Rejected", "Congratulations", "success")
				
				$http({method:'get',url:BASE_URL + '/fetchAllForums', headers: {'Content-Type': 'application/json'}})
				.then(function(resp){
					console.log( resp.data )
				
					$scope.AllForums = resp.data;
				},function(resp){
					
					console.log( "fetchAllForums Error" )
				});
				
				break;
				
			case 'Failure':
				swal("Forum Reject Failure", "Something went wrong!", "error")
				break;
			}
		});
	}
	
	$scope.ETitle = 
	{ 
			value: '',
			error : false,
			touched : true,
			validate: function(){
				this.touched = true;
				var reg = /^.{2,20}$/;
				this.error = !reg.test( this.value );
				
				console.log( this.error );
				
			}
	}
	
	$scope.EDescription = 
	{ 
			value: '',
			error : false,
			touched : true,
			validate: function(){
				this.touched = true;
				var reg = /^.{2,160}$/;
				this.error = !reg.test( this.value );
			}
	}
	
	$scope.Eid = -1;
	
	$scope.EditForum = function( arg ){
		
		
		
		for( var x in $scope.AllForums )
		{
			console.log( $scope.AllForums[x] );
			console.log( arg );
			
			if( $scope.AllForums[x].id == arg )
			{
				console.log( $scope.AllForums[x].id );
				
				$scope.Eid = $scope.AllForums[x].id;
				$scope.ETitle.value = $scope.AllForums[x].title;
				$scope.EDescription.value = $scope.AllForums[x].description;
				break;
			}
			
		}
		
	}
	
	$scope.EditForumToDB = function()
	{
		var json = 
				{
				'id': $scope.Eid,
				'Title': $scope.ETitle.value,
				'Description': $scope.EDescription.value,
				
				};
		
		console.log(json);
		//alert(json);
		
		$http({method:'post',url:BASE_URL + '/editForum', data: json, headers: {'Content-Type': 'application/json'}}).then(function(data){
			console.log( data )
			
			switch( data.data.msg )
			{
			case 'Success':
				
				swal("Forum Updated", "Congratulations", "success")
				
				$http({method:'get',url:BASE_URL + '/fetchAllForums', headers: {'Content-Type': 'application/json'}})
				.then(function(resp){
					console.log( resp.data )
				
					$scope.AllForums = resp.data;
				},function(resp){
					
					console.log( "fetchAllForums Error" )
				});
				
				break;
				
			case 'Failure':
				swal("Forum Update Failure", "Something went wrong!", "error")
				break;
			}
			
		},function(data){
			console.log( data )
			
			switch( data.data.msg )
			{
			case 'Success':
				
				swal("Forum Updated", "Congratulations", "success")
				
				$http({method:'get',url:BASE_URL + '/fetchAllForums', headers: {'Content-Type': 'application/json'}})
				.then(function(resp){
					console.log( resp.data )
				
					$scope.AllForums = resp.data;
				},function(resp){
					
					console.log( "fetchAllForums Error" )
				});
				
				break;
				
			case 'Failure':
				swal("Forum Update Failure", "Something went wrong!", "error")
				break;
			}
		});
	}
	
	$scope.DeleteForum = function( arg )
	{
		
		
		var json = 
				{
				'id': arg
				};
		
		console.log(json);
		//alert(json);
		
		$http({method:'post',url:BASE_URL + '/deleteForum', data: json, headers: {'Content-Type': 'application/json'}}).then(function(data){
			console.log( data )
			
			switch( data.data.msg )
			{
			case 'Success':
				
				swal("Forum Deleted", "Congratulations", "success")
				
				$http({method:'get',url:BASE_URL + '/fetchAllForums', headers: {'Content-Type': 'application/json'}})
				.then(function(resp){
					console.log( resp.data )
				
					$scope.AllForums = resp.data;
				},function(resp){
					
					console.log( "fetchAllForums Error" )
				});
				
				break;
				
			case 'Failure':
				swal("Forum Delete Failure", "Something went wrong!", "error")
				break;
			}
			
		},function(data){
			console.log( data )
			
			switch( data.data.msg )
			{
			case 'Success':
				
				swal("Forum Deleted", "Congratulations", "success")
				
				$http({method:'get',url:BASE_URL + '/fetchAllForums', headers: {'Content-Type': 'application/json'}})
				.then(function(resp){
					console.log( resp.data )
				
					$scope.AllForums = resp.data;
				},function(resp){
					
					console.log( "fetchAllForums Error" )
				});
				
				break;
				
			case 'Failure':
				swal("Forum Delete Failure", "Something went wrong!", "error")
				break;
			}
		});
	}
	
}]);