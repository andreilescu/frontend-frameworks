angular.module('frontendframeworks').controller('ListNoteController', ['$scope',

    function($scope) {
        'use strict';

        $scope.note = {};

        $scope.notes = [
            {
                "title": 'Note Title 1',
                "description": 'Note Description 1'
            },
            {
                "title": 'Note Title 2',
                "description": 'Note Title 2'
            }
        ];

        $scope.addNote = function() {
            $scope.notes.push(angular.copy($scope.note));
            $scope.resetNote();
        };

        $scope.resetNote = function() {
            $scope.note = {};
        };

    }]);