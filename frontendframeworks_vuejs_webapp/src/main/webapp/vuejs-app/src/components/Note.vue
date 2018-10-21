<template>

    <div class="container">

        <div class="card">
            <button type="button"
                    class="btn btn-primary">
                <router-link to="/">
                    <span style="color: white">Back</span>
                </router-link>
            </button>

            <div class="card-body">

                <div class="form-group">
                    <label for="titleId">Note Title</label>
                    <input v-model="note.title"
                           name="title"
                           id="titleId"
                           type="text"
                           class="form-control"
                           aria-describedby="basic-addon2">
                </div>

                <div class="form-group">
                    <label for="descriptionId">Note Description</label>
                    <textarea v-model="note.description"
                              name="description"
                              id="descriptionId"
                              class="form-control">
                    </textarea>
                </div>

                <button v-on:click="editNote()"
                        class="btn btn btn-primary"
                        type="button">
                    Edit Note
                </button>

            </div>
        </div>

    </div>

</template>

<script>

    import {getNote, updateNote} from "../services/NoteService";

    export default {
        name: 'Note',
        methods: {

            getNote() {

                const noteId = this.$route.query.uid;
                getNote(noteId)
                    .then(response => {
                        this.note = response.data;
                    })
                    .catch(() => {
                        // error handler
                    });
            },

            editNote() {

                updateNote(this.note)
                    .then(() => {
                        this.getNote();
                    })
                    .catch(() => {
                        // error handler
                    });
            }
        },
        data: function () {
            return {
                note: {
                    title: '',
                    description: ''
                }
            }
        },
        beforeMount() {
            this.getNote();
        }
    }

</script>


