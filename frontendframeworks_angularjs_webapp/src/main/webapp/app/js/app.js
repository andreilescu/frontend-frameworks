(function(angular) {
    var frontendframeworks = angular.module('frontendframeworks', ['ngRoute']).config(
        ['$routeProvider', function($routeProvider) {
            'use strict';

            $routeProvider
                .when('/', {
                    templateUrl: 'partials/pages/list-note.html'
                })
                .otherwise({
                    templateUrl: 'partials/pages/list-note.html'
                });
        }]
    );
}(angular));