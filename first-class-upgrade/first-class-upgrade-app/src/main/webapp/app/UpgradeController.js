'use strict';

angular.module('firstClassUpgradeApp').controller('UpgradeController', [
		'$log',
		'UpgradeFactory',
		function($log, upgradeFactory) {
			var vm = this;
			
			vm.person = {};
			vm.person.firstName = "Sal";
			vm.person.lastName = "Elrahal";
			vm.person.age = 87;
			vm.feedback = '';
			
			vm.submit = function() {
				$log.info("Submitting request");
				$log.info(vm.person);
				upgradeFactory.create(vm.person).then(function(response) {
					vm.feedback = response.data;
				});
			}
		} ]);