angular.module('frontendframeworks').factory('NoteService', ['$q', '$http', function($q, $http) {
    'use strict';

    var HOST = "http://localhost";
    var PORT = "8081";
    var PROXY = HOST + ":" + PORT;
    var NOTES = "/notes";

    var srv = {};

    srv.getNotesImpl = function() {
        return $http.get(PROXY + NOTES, {});
    };

    srv.getNoteImpl = function(noteId) {
        return $http.get(PROXY + NOTES + '/' + noteId, {});
    };

    srv.addNoteImpl = function(note) {
        return $http.post(PROXY + NOTES, note);
    };

    srv.updateNoteImpl = function(note) {
        return $http.put(PROXY + NOTES + '/' + note.id, note);
    };

    srv.deleteNoteImpl = function(noteId) {
      return $http.delete(PROXY + NOTES + '/' + noteId, {});
    };

    return {
        getNotes: function() {
            return srv.getNotesImpl();
        },

        getNote: function(noteId) {
            return srv.getNoteImpl(noteId);
        },

        addNote: function(note) {
            return srv.addNoteImpl(note);
        },

        updateNote: function(note) {
            return srv.updateNoteImpl(note);
        },

        deleteNote: function(noteId) {
            return srv.deleteNoteImpl(noteId);
        }
    };

}]);