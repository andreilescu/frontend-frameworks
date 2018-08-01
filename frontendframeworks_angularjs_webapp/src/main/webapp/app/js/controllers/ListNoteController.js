angular.module('frontendframeworks').controller('ListNoteController', ['$scope',

    function($scope) {
        'use strict';

        $scope.notes = ['note1', 'note2'];
        $scope.noteText = '';

        $scope.addNote = function() {

            $scope.notes.push($scope.noteText);
            $scope.noteText = '';
        };

}]);