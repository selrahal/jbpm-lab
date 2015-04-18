'use strict';

angular.module('firstClassUpgradeApp', ['ngRoute', 'ui.bootstrap']).
  config(['$routeProvider','$httpProvider', function($routeProvider, $httpProvider) {
    $routeProvider.when('/home', {templateUrl: 'app/home.html'});
    $routeProvider.otherwise({redirectTo: '/home'});
  }]).constant('API_ENDPOINT','/first-class-upgrade-app/rest');
