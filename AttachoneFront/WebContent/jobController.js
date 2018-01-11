app.controller("jobController",['$scope','$location','$window','$http',function(   $scope , $location, $window , $http){
	
	if( $window.sessionStorage.getItem("currentUser") == null || $window.sessionStorage.getItem("currentUser") == undefined )
	{
		
		$('.modal').modal('hide');
		$('.modal-backdrop').remove();
		$location.path("/");
	}
	
	$scope.AllJobs = [];
	
	$http({method:'get',url:BASE_URL + '/fetchAllJobs', headers: {'Content-Type': 'application/json'}})
	.then(function(resp){
		console.log( resp.data )
	
		$scope.AllJobs = resp.data;
	},function(resp){
		
		console.log( "fetchAllJobs Error" )
	});
	
	$scope.ApplyJob = function(arg1,arg2)
	{
		var json = 
				{
				'id': arg2,
				'applicant':arg1
				};
		
		console.log(json);
		//alert(json);
		
		$http({method:'post',url:BASE_URL + '/applyJob', data: json, headers: {'Content-Type': 'application/json'}}).then(function(data){
			console.log( data )
			
			switch( data.data.msg )
			{
			case 'Success':
				
				swal("Applied", "Congratulations", "success")
				
				$http({method:'get',url:BASE_URL + '/fetchAllJobs', headers: {'Content-Type': 'application/json'}})
				.then(function(resp){
					console.log( resp.data )
				
					$scope.AllJobs = resp.data;
				},function(resp){
					
					console.log( "fetchAllJobs Error" )
				});
				
				break;
				
			case 'Failure':
				swal("Application Failed", "Something went wrong!", "error")
				break;
			}
			
		},function(data){
			console.log( data )
			
			switch( data.data.msg )
			{
			case 'Success':
				
				swal("Applied", "Congratulations", "success")
				
				$http({method:'get',url:BASE_URL + '/fetchAllJobs', headers: {'Content-Type': 'application/json'}})
				.then(function(resp){
					console.log( resp.data )
				
					$scope.AllJobs = resp.data;
				},function(resp){
					
					console.log( "fetchAllJobs Error" )
				});
				
				break;
				
			case 'Failure':
				swal("Application Failed", "Something went wrong!", "error")
				break;
			}
		});
	}
	
	$scope.WithdrawApplyJob = function(arg1,arg2)
	{
		var json = 
				{
				'id': arg2,
				'applicant':arg1
				};
		
		console.log(json);
		//alert(json);
		
		$http({method:'post',url:BASE_URL + '/withdrawJob', data: json, headers: {'Content-Type': 'application/json'}}).then(function(data){
			console.log( data )
			
			switch( data.data.msg )
			{
			case 'Success':
				
				swal("Withdrawn", "Congratulations", "success")
				
				$http({method:'get',url:BASE_URL + '/fetchAllJobs', headers: {'Content-Type': 'application/json'}})
				.then(function(resp){
					console.log( resp.data )
				
					$scope.AllJobs = resp.data;
				},function(resp){
					
					console.log( "fetchAllJobs Error" )
				});
				
				break;
				
			case 'Failure':
				swal("Withdraw Application Failed", "Something went wrong!", "error")
				break;
			}
			
		},function(data){
			console.log( data )
			
			switch( data.data.msg )
			{
			case 'Success':
				
				swal("Withdrawn", "Congratulations", "success")
				
				$http({method:'get',url:BASE_URL + '/fetchAllJobs', headers: {'Content-Type': 'application/json'}})
				.then(function(resp){
					console.log( resp.data )
				
					$scope.AllJobs = resp.data;
				},function(resp){
					
					console.log( "fetchAllJobs Error" )
				});
				
				break;
				
			case 'Failure':
				swal("Withdraw Application Failed", "Something went wrong!", "error")
				break;
			}
		});
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
	
	$scope.EditJob = function( arg ){
		
		
		
		for( var x in $scope.AllJobs )
		{
			console.log( $scope.AllJobs[x] );
			console.log( arg );
			
			if( $scope.AllJobs[x].id == arg )
			{
				console.log( $scope.AllJobs[x].id );
				
				$scope.Eid = $scope.AllJobs[x].id;
				$scope.ETitle.value = $scope.AllJobs[x].title;
				$scope.EDescription.value = $scope.AllJobs[x].description;
				break;
			}
			
		}
		
	}
	
	$scope.AddJob = function()
	{
		var json = 
				{
				'Email': 'vasudev89@yahoo.com',
				'Title': $scope.Title.value,
				'Description': $scope.Description.value,
				
				};
		
		console.log(json);
		//alert(json);
		
		$http({method:'post',url:BASE_URL + '/addJob', data: json, headers: {'Content-Type': 'application/json'}}).then(function(data){
			console.log( data )
			
			switch( data.data.msg )
			{
			case 'Success':
				
				swal("Job Created", "Congratulations", "success")
				
				$http({method:'get',url:BASE_URL + '/fetchAllJobs', headers: {'Content-Type': 'application/json'}})
				.then(function(resp){
					console.log( resp.data )
				
					$scope.AllJobs = resp.data;
				},function(resp){
					
					console.log( "fetchAllJobs Error" )
				});
				
				break;
				
			case 'Failure':
				swal("Job Creation Failure", "Something went wrong!", "error")
				break;
			}
			
		},function(data){
			console.log( data )
			
			switch( data.data.msg )
			{
			case 'Success':
				
				swal("Job Created", "Congratulations", "success")
				
				$http({method:'get',url:BASE_URL + '/fetchAllJobs', headers: {'Content-Type': 'application/json'}})
				.then(function(resp){
					console.log( resp.data )
				
					$scope.AllJobs = resp.data;
				},function(resp){
					
					console.log( "fetchAllJobs Error" )
				});
				
				break;
				
			case 'Failure':
				swal("Job Creation Failure", "Something went wrong!", "error")
				break;
			}
		});
	}
	
	$scope.EditJobToDB = function()
	{
		var json = 
				{
				'id': $scope.Eid,
				'Title': $scope.ETitle.value,
				'Description': $scope.EDescription.value,
				
				};
		
		console.log(json);
		//alert(json);
		
		$http({method:'post',url:BASE_URL + '/editJob', data: json, headers: {'Content-Type': 'application/json'}}).then(function(data){
			console.log( data )
			
			switch( data.data.msg )
			{
			case 'Success':
				
				swal("Job Updated", "Congratulations", "success")
				
				$http({method:'get',url:BASE_URL + '/fetchAllJobs', headers: {'Content-Type': 'application/json'}})
				.then(function(resp){
					console.log( resp.data )
				
					$scope.AllJobs = resp.data;
				},function(resp){
					
					console.log( "fetchAllJobs Error" )
				});
				
				break;
				
			case 'Failure':
				swal("Job Update Failure", "Something went wrong!", "error")
				break;
			}
			
		},function(data){
			console.log( data )
			
			switch( data.data.msg )
			{
			case 'Success':
				
				swal("Job Updated", "Congratulations", "success")
				
				$http({method:'get',url:BASE_URL + '/fetchAllJobs', headers: {'Content-Type': 'application/json'}})
				.then(function(resp){
					console.log( resp.data )
				
					$scope.AllJobs = resp.data;
				},function(resp){
					
					console.log( "fetchAllJobs Error" )
				});
				
				break;
				
			case 'Failure':
				swal("Job Update Failure", "Something went wrong!", "error")
				break;
			}
		});
	}
	
	$scope.DeleteJob = function( arg )
	{
		
		
		var json = 
				{
				'id': arg
				};
		
		console.log(json);
		//alert(json);
		
		$http({method:'post',url:BASE_URL + '/deleteJob', data: json, headers: {'Content-Type': 'application/json'}}).then(function(data){
			console.log( data )
			
			switch( data.data.msg )
			{
			case 'Success':
				
				swal("Job Deleted", "Congratulations", "success")
				
				$http({method:'get',url:BASE_URL + '/fetchAllJobs', headers: {'Content-Type': 'application/json'}})
				.then(function(resp){
					console.log( resp.data )
				
					$scope.AllJobs = resp.data;
				},function(resp){
					
					console.log( "fetchAllJobs Error" )
				});
				
				break;
				
			case 'Failure':
				swal("Job Delete Failure", "Something went wrong!", "error")
				break;
			}
			
		},function(data){
			console.log( data )
			
			switch( data.data.msg )
			{
			case 'Success':
				
				swal("Job Deleted", "Congratulations", "success")
				
				$http({method:'get',url:BASE_URL + '/fetchAllJobs', headers: {'Content-Type': 'application/json'}})
				.then(function(resp){
					console.log( resp.data )
				
					$scope.AllJobs = resp.data;
				},function(resp){
					
					console.log( "fetchAllJobs Error" )
				});
				
				break;
				
			case 'Failure':
				swal("Job Delete Failure", "Something went wrong!", "error")
				break;
			}
		});
	}
	
}]);