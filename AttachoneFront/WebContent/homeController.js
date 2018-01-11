app.controller("homeController",['$scope','$location','$window','$http','dataService','$rootScope',function(   $scope , $location, $window , $http , dataService, $rootScope){
	
	console.log('homeController');
	
	$scope.LoginStatus = false;
	
	if( $window.sessionStorage.getItem("currentUser") != null && $window.sessionStorage.getItem("currentUser") != undefined )
	{
		$rootScope.LoginEmail = JSON.parse( $window.sessionStorage.getItem("currentUser") ).email;
		$rootScope.LoginRole = JSON.parse( $window.sessionStorage.getItem("currentUser") ).role;
		
		console.log( $rootScope.LoginRole );
		
		$rootScope.LoginStatus = true;
		$location.path("/blog");
	}
	else
	{
		window.setTimeout(function(){
			$("#loginModal").modal('show');
		},1000);
		$scope.LoginStatus = dataService.dataObj.LoginStatus = false;
	}
	
	$scope.PerformLogin = function()
	{
		var json = 
				{
				'Email': $scope.LoginEmail.value,
				'Password': $scope.LoginPassword.value
				};
		
		console.log(json);
		//alert(json);
		
		$http({method:'post',url:BASE_URL + '/tryLogin', data: json, headers: {'Content-Type': 'application/json'}}).then(function(data){
			console.log( data )
			
			switch( data.data.msg )
			{
			default:
				
				//swal("User Login Successful", "", "success")
				
				var elem = data.data;
			
				delete elem.id;
				delete elem.password;
				
				$window.sessionStorage.setItem("currentUser",JSON.stringify(elem));
				
				$("#loginModal").modal('hide');
				
				$rootScope.LoginEmail = JSON.parse( $window.sessionStorage.getItem("currentUser") ).email;
				
				$rootScope.LoginRole = JSON.parse( $window.sessionStorage.getItem("currentUser") ).role;
				
				window.setTimeout(function(){
					$scope.$apply( $location.path('/blog') );
					
					$scope.$apply( $scope.LoginStatus = dataService.dataObj.LoginStatus = true );
					
					$scope.$apply( $rootScope.LoginStatus = true );
					
				},500);
				
				
				break;
				
			case 'Failure':
				swal("User Login Failure", "Please enter correct credentials!", "error")
				break;
			}
			
		},function(data){
			console.log( data )
			
			switch( data.data.msg )
			{
			default:
				
				//swal("User Login Successful", "", "success")
				$("#loginModal").modal('hide');
				$rootScope.LoginEmail = JSON.parse( $window.sessionStorage.getItem("currentUser") );
				$location.path('/blog');
				$scope.$apply( $scope.LoginStatus = dataService.dataObj.LoginStatus = true );
				break;
				
			case 'Failure':
				swal("User Login Failure", "Please enter correct credentials!", "error")
				break;
			}
		});
	}
	
	$scope.Username = 
	{ 
			value: '',
			error : true,
			touched : false,
			validate: function(){
				this.touched = true;
				var reg = /^$/;
				$scope.Username.error = reg.test( $scope.Username.value );
			}
	}
	
	$scope.LoginEmail = 
	{ 
			value: '',
			error : true,
			touched : false,
			validate: function(){
				this.touched = true;
				var reg = /^$/;
				this.error = reg.test( this.value );
			}
	}
	
	$scope.LoginPassword = 
	{ 
			value: '',
			error : true,
			touched : false,
			validate: function(){
				this.touched = true;
				var reg = /^$/;
				this.error = reg.test( this.value );
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
	
	$scope.Password = 
	{ 
			value: '',
			error : true,
			touched : false,
			validate: function(){
				this.touched = true;
				var reg = /^.{8,20}$/;
				this.error = !reg.test( this.value );
			}
	}
	
	$scope.CPassword = 
	{ 
			value: '',
			error : true,
			touched : false,
			validate: function(){
				this.touched = true;
				this.error =  (this.value != $scope.Password.value);
			}
	}
	
	$scope.Phone = 
	{ 
			value: '',
			error : true,
			touched : false,
			validate: function(){
				this.touched = true;
				var reg = /^[7-9]{1}[0-9]{9}$/;
				this.error = !reg.test( this.value );
			}
	}
	
	//$scope.Gender = 'Male';
	
	$scope.RegisterUser = function()
	{
		var json = 
				{
				'Username': $scope.Username.value,
				'Email': $scope.Email.value,
				'Password': $scope.Password.value,
				'Phone': $scope.Phone.value,
				'Gender': $scope.Gender
				};
		
		console.log(json);
		//alert(json);
		
		$http({method:'post',url:BASE_URL + '/addUser', data: json, headers: {'Content-Type': 'application/json'}}).then(function(data){
			console.log( data )
			
			switch( data.data.msg )
			{
			case 'Success':
				
				swal("User Created", "Login to continue", "success")
				
				break;
				
			case 'Failure':
				swal("User Creation Failure", "Something went wrong!", "error")
				break;
			}
			
		},function(data){
			console.log( data )
			
			switch( data.data.msg )
			{
			case 'Success':
				
				swal("User Created", "Login to continue", "success")
				
				break;
				
			case 'Failure':
				swal("User Creation Failure", "Something went wrong!", "error")
				break;
			}
		});
	}
	
}]);