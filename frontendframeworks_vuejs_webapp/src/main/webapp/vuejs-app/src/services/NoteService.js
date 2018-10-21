import Vue from 'vue';
import VueResource from 'vue-resource';

Vue.use(VueResource);

const HOST = "http://localhost";
const PORT = "8081";
const PROXY = HOST + ":" + PORT;
const NOTES = "/notes";
const url = PROXY + NOTES;

export const getNotes = () => {
    return Vue.http.get(url);
};

export const getNote = (noteId) => {
    return Vue.http.get(url + '/' + noteId);
};

export const removeNote = (noteId) => {
    return Vue.http.delete(url + '/' + noteId);
};

export const addNote = (note) => {
    return Vue.http.post(url, note);
};

export const updateNote = (note) => {
    return Vue.http.put(url + '/' + note.id, note);
};

