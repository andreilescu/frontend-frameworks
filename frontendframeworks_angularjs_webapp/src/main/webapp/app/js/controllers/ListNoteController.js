angular.module('frontendframeworks').controller('ListNoteController', ['$scope', '$location', 'NoteService',

    function($scope, $location, noteService) {
        'use strict';

        var NOTES = "/notes";
        $scope.notes = [];
        $scope.note = {};

        $scope.addNote = function() {
            noteService.addNote($scope.note).then(
                function() {
                    resetNote();
                    getNotes();
                },
                function(error) {
                    console.error(error);
                });
        };

        $scope.deleteNote = function(noteId) {
            noteService.deleteNote(noteId).then(
                function(response) {
                    console.log("Note deleted");
                    getNotes();
                },
                function(error) {
                    console.error(error);
                });
        };

        $scope.navigateToNote = function(noteId) {
            $location.path(NOTES + '/' + noteId);
        };

        function resetNote() {
            $scope.note = {};
        }

        function getNotes() {
            noteService.getNotes().then(function(response) {
                $scope.notes = response.data;
            }, function(error) {
                console.error(error);
            });
        }

        function initialize() {
            getNotes();
        }

        initialize();
    }]);