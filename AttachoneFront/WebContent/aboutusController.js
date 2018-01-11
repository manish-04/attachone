app.controller("aboutusController",['$scope','$location','$window','$http',function(   $scope , $location, $window , $http){
	
	$scope.Username = 
	{ 
			value: '',
			error : true,
			touched : false,
			validate: function(){
				this.touched = true;
				var reg = /^.{2,20}$/;
				$scope.Username.error = !reg.test( $scope.Username.value );
			}
	}
	
	$scope.Email = 
	{ 
			value: '',
			error : true,
			touched : false,
			validate: function(){
				this.touched = true;
				var reg = /\S+@\S+\.\S+/;
				this.error = !reg.test( this.value );
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
	
	$scope.SendMessage = function()
	{
		var json = 
				{
				'Username': $scope.Username.value,
				'Email': $scope.Email.value,
				'Username': $scope.Username.value
				};
		
		console.log(json);
		//alert(json);
		
		$http({method:'post',url:BASE_URL + '/sendMessage', data: json, headers: {'Content-Type': 'application/json'}}).then(function(data){
			console.log( data )
			
			switch( data.data.msg )
			{
			case 'Success':
				
				swal("Message Sent", "Login to continue", "success")
				
				break;
				
			case 'Failure':
				swal("Message Sending Failure", "Something went wrong!", "error")
				break;
			}
			
		},function(data){
			console.log( data )
			
			switch( data.data.msg )
			{
			case 'Success':
				
				swal("Message Sent", "Login to continue", "success")
				
				break;
				
			case 'Failure':
				swal("Message Sending Failure", "Something went wrong!", "error")
				break;
			}
		});
	}
	
}]);