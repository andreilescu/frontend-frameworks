angular.module('frontendframeworks').controller('NoteController', ['$scope', '$location', 'NoteService',
    function($scope, $location, noteService) {
        'use strict';

        $scope.note = {};

        $scope.editNote = function() {
            noteService.updateNote($scope.note).then(function(response) {
                console.log("Note updated", response.data.id);
            }, function(error) {
                console.error("Error during note update" + error);
            });
        };

        $scope.navigateBack = function() {
            $location.path("/");
        };

        function getNoteIdFromPath() {
            return $location.path().split("/")[2];
        }

        function getNote() {
            var noteId = getNoteIdFromPath();
            noteService.getNote(noteId).then(function(response) {
                $scope.note = response.data;
            });
        }

        function initialize() {
            getNote();
        }

        initialize();
    }
]);