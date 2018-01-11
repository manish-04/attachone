

app.directive('fileModel', ['$parse', function ($parse) {
    return {
        restrict: 'A',
        link: function(scope, element, attrs) {
            var model = $parse(attrs.fileModel);
            var modelSetter = model.assign;
            
            element.bind('change', function(){
                scope.$apply(function(){
                    modelSetter(scope, element[0].files[0]);
                });
            });
        }
    };
}]);
	
	app.service('fileUpload', [ '$http', function($http) {
		this.uploadFileToUrl = function(file, paramuser, uploadUrl) {
			var fd = new FormData();
			fd.append('file', file);
			//fd.append('user','vasudev89');
			
			//fd.setAttribute("enctype","multipart/form-data");
			
			console.log(fd);
			
			return $http.post(uploadUrl, fd, {
				transformRequest : angular.identity,
				
				headers : {
					'Content-Type' : undefined,
					
					user : paramuser
				}
			}).then(function(response) {
				return response.data;
			}, function(errResponse) {
				console.error('Error while updating User');
				return "error";
			});
		}
	} ]);

app.controller("profileController",['$scope','$location','$window','$http','fileUpload','$rootScope','$routeParams',function(   $scope , $location, $window , $http , $fileUpload, $rootScope, $routeParams){
	
	$scope.$routeParams = $routeParams;
	
	if( $window.sessionStorage.getItem("currentUser") == null || $window.sessionStorage.getItem("currentUser") == undefined )
	{
		
		$('.modal').modal('hide');
		$('.modal-backdrop').remove();
		$location.path("/");
	}
	
	console.log('profileController');
	
	$scope.openFileChooser = function() {
		$('#trigger').trigger('click');
	};
	
	// Upload image 
	$scope.setFile = function(element) {
		$scope.currentFile = element.files[0];
		var reader = new FileReader();
		reader.onload = function(event) {
			//$scope.userdetails.Image = event.target.result
			$scope.$apply()
			
			
			
		};
		// when the file is read it triggers the onload event above.
		reader.readAsDataURL($scope.currentFile);
		var file = $scope.currentFile;
		console.log('file is :');
		console.dir(file);
		var uploadUrl =  BASE_URL + "/updateProfilePicture/";
		// calling uploadFileToUrl function of $fileUpload
		var res = $fileUpload
				.uploadFileToUrl(file,
						$scope.Email.value,
						uploadUrl)
				.then(
						function(response) {
							/*$scope.status = response.status;
							$scope.imagesrc = response.imagesrc;
							$scope.picDeleted = false;
							//console.log( $scope.response );
							//console.log( $scope.imagesrc );
							$scope.currentImage = '/'
									+ $scope.imagesrc;
							$scope.stateDisabled = false;*/
							
							console.log( response )
							
							switch( response.msg )
							{
							case 'Success':
								
								swal("User Updated", "Congratulations", "success")
								
								$scope.profilePicUrl = response.imageUrl;
								
								break;
								
							case 'Failure':
								swal("User Update Failure", "Something went wrong!", "error")
								break;
							}
						},
						function(errResponse) {
							console.log( errResponse )
						});
	};
	//
	
	$scope.Username = 
	{ 
			value: '',
			error : false,
			touched : true,
			validate: function(){
				this.touched = true;
				var reg = /^$/;
				$scope.Username.error = reg.test( $scope.Username.value );
			}
	}
	
	$scope.Email = 
	{ 
			value: $rootScope.LoginEmail,
			error : false,
			touched : true,
			validate: function(){
				this.touched = true;
				var reg = /\S+@\S+\.\S+/;
				this.error = !reg.test( this.value );
			}
	}
	
	$scope.previousPassword = '';
	
	$scope.OPassword = 
	{ 
			value: '',
			error : true,
			touched : false,
			validate: function(){
				this.touched = true;
				this.error =  (this.value != $scope.previousPassword);
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
				
				
				
				this.error = !reg.test( this.value ) | (this.value == $scope.previousPassword);
				
				
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
			value: -1,
			error : false,
			touched : true,
			validate: function(){
				this.touched = true;
				var reg = /^[7-9]{1}[0-9]{9}$/;
				this.error = !reg.test( this.value );
			}
	}
	
	var json = 
	{
	'Email': ($routeParams.param != null) ? $routeParams.param : $rootScope.LoginEmail
	};

	console.log($routeParams.param);
	console.log(json);
	//alert(json);
	
	$scope.user = null;
	
	$http({method:'post',url:BASE_URL + '/fetchUserData', data: json, headers: {'Content-Type': 'application/json'}}).then(function(data){
	console.log( data )
	
	$scope.user = data.data;
	
	$scope.Username.value = data.data.username;
	$scope.Email.value = data.data.email;
	$scope.Phone.value = parseInt(data.data.phone);
	$scope.Gender = data.data.gender;
	$scope.profilePicUrl = data.data.profilePicUrl;
	$scope.previousPassword = data.data.password;
	
	
	},function(data){
	console.log( data )
	
	
	});
	
	$scope.UpdateUser = function()
	{
		//alert('UpdateUser');
		
		var json = 
				{
				'_id' : $scope.user.id,
				'Username': $scope.Username.value,
				'Email': $scope.Email.value,
				'Phone': $scope.Phone.value,
				'Gender': $scope.Gender
				};
		
		console.log(json);
		//alert(json);
		
		$http({method:'post',url:BASE_URL + '/updateUser', data: json, headers: {'Content-Type': 'application/json'}}).then(function(data){
			console.log( data )
			
			switch( data.data.msg )
			{
			case 'Success':
				
				swal("User Updated", "Congratulations", "success")
				
				break;
				
			case 'Failure':
				swal("User Update Failure", "Something went wrong!", "error")
				break;
			}
			
		},function(data){
			console.log( data )
			
			switch( data.data.msg )
			{
			case 'Success':
				
				swal("User Updated", "Congratulations", "success")
				
				break;
				
			case 'Failure':
				swal("User Update Failure", "Something went wrong!", "error")
				break;
			}
		});
	}
	
	$scope.UpdatePassword = function()
	{
		//alert('UpdateUser');
		
		var json = 
				{
				'_id' : $scope.user.id,
				'Email': $scope.Email.value,
				'Password': $scope.Password.value
				
				};
		
		console.log(json);
		//alert(json);
		
		$http({method:'post',url:BASE_URL + '/updateUserPassword', data: json, headers: {'Content-Type': 'application/json'}}).then(function(data){
			console.log( data )
			
			switch( data.data.msg )
			{
			case 'Success':
				
				swal("User Updated", "Congratulations", "success")
				
				break;
				
			case 'Failure':
				swal("User Update Failure", "Something went wrong!", "error")
				break;
			}
			
		},function(data){
			console.log( data )
			
			switch( data.data.msg )
			{
			case 'Success':
				
				swal("User Updated", "Congratulations", "success")
				
				break;
				
			case 'Failure':
				swal("User Update Failure", "Something went wrong!", "error")
				break;
			}
		});
	}
	
}]);