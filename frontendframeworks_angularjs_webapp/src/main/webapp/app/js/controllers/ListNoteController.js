angular.module('frontendframeworks').controller('ListNoteController', ['$scope', '$location',

    function($scope, $location) {
        'use strict';

        $scope.note = {};

        $scope.addNote = function() {
            $scope.incrementNoteIndex();
            $scope.copyAndUpdateNotes();
            $scope.resetNote();
        };

        $scope.copyAndUpdateNotes = function() {
            $scope.notes.push($scope.note);
        };

        $scope.incrementNoteIndex = function() {
            $scope.note.id = $scope.notes.length + 1;
        };

        $scope.resetNote = function() {
            $scope.note = {};
        };

        $scope.navigateToNote = function(noteId) {
            $location.path("/notes/" + noteId);
        };

    }]);