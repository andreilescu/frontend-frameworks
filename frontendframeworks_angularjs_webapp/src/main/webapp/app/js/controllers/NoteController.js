angular.module('frontendframeworks').controller('NoteController', ['$scope', '$location',
    function($scope, $location) {
        'use strict';

        $scope.note = {};

        $scope.initialize = function() {
            redirectBack();
            var noteId = getNoteIdFromPath();
            $scope.note = getNoteById(noteId);
        };

        $scope.editNote = function() {
            var notePosition = getNoteByIdPosition();
            $scope.notes[notePosition] = $scope.note;
        };

        $scope.navigateBack = function() {
            $location.path("/");
        };

        function redirectBack() {
            if($scope.notes.length === 0)
                $scope.navigateBack();
        }

        function getNoteIdFromPath() {
            return $location.path().split("/")[2];
        }

        function getNoteById(noteId) {
            for(var i = 0; i < $scope.notes.length; i++) {
                if(noteId == $scope.notes[i].id) {
                    return $scope.notes[i];
                }
            }
        }

        function getNoteByIdPosition() {
            for(var i = 0; i < $scope.notes.length; i++) {
                if($scope.note.id == $scope.notes[i].id) {
                    return i;
                }
            }
        }

        $scope.initialize();
    }
]);