'use strict';

angular.module('firstClassUpgradeApp').factory('UpgradeFactory', ['$http', 'API_ENDPOINT',function($http, API_ENDPOINT) {
	return {
		create : function(upgradeRequest) {
			var api = API_ENDPOINT + '/firstclass';
			var headers = {'Content-Type' : 'application/json'};
			return $http.post(api, upgradeRequest, headers);
		}
	}
}]);