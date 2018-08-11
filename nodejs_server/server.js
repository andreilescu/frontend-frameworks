// create a server
var express = require('express');
var app = express();
var bodyParser = require('body-parser');
var cors = require('cors');
var fs = require('fs');
var uuid = require('uuid');

app.use(bodyParser.json());
app.use(cors());

var FILE_NAME = 'notes.json';
var FILE_ENCODING = 'UTF-8';

app.get('/notes', function(request, response) {
    response.send(getNotesFromFile());
});

app.get('/notes/:noteId', function(request, response) {
    response.send(getNoteById(request.params.noteId));
});

app.post('/notes', function(request, response) {
    response.send(appendNoteToFile(request.body));
});

app.put('/notes/:noteId', function(request, response) {
    response.send(updateNoteFromFile(request.body));
});

app.delete('/notes/:noteId', function(request, response) {
    response.send(deleteNoteFromFile(request.params.noteId));
});

function appendNoteToFile(note) {
    note.id = uuid.v4();
    var notes = getNotesFromFile();
    notes.push(note);
    writeNotesToFile(notes);
    return note;
}

function updateNoteFromFile(note) {
    var notes = getNotesFromFile();
    for(var i = 0; i < notes.length; i++) {
        if(note.id === notes[i].id) {
            notes[i] = note;
        }
    }
    writeNotesToFile(notes);
    return note;
}

function getNoteById(id) {
    var notes = getNotesFromFile();
    for(var i = 0; i < notes.length; i++) {
        if(id === notes[i].id) return notes[i];
    }
}

function deleteNoteFromFile(id) {
    var notes = getNotesFromFile();
    notes = notes.filter(function(item) {
        return item.id !== id
    });
    writeNotesToFile(notes);
}

function getNotesFromFile() {
    return JSON.parse(fs.readFileSync(FILE_NAME, FILE_ENCODING));
}

function writeNotesToFile(notes) {
    fs.writeFileSync(FILE_NAME, JSON.stringify(notes));
}

var server = app.listen(8081, function() {
    var host = server.address().address;
    var port = server.address().port;
    console.log("App listening at http://%s:%s", host, port)
});
