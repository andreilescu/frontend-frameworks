<template>

    <div class="container">

        <div class="card">

            <div class="card-header">
                Notes
            </div>

            <div class="card-body">

                <div class="card">

                    <div class="accordion" id="accordionNote">
                        <div class="card">
                            <div class="card-header" id="headingTitle">
                                <h5 class="mb-0">
                                    <button class="btn btn-link"
                                            type="button"
                                            data-toggle="collapse"
                                            data-target="#collapseOne"
                                            aria-expanded="true"
                                            aria-controls="collapseOne">
                                        Add note
                                    </button>
                                </h5>
                            </div>

                            <div id="collapseOne"
                                 class="collapse show"
                                 aria-labelledby="headingTitle"
                                 data-parent="#accordionNote">

                                <div class="card-body">
                                    <div class="form-group">
                                        <label for="titleId">Note Title</label>
                                        <input v-model="note.title"
                                               type="text"
                                               id="titleId"
                                               class="form-control"
                                               aria-describedby="basic-addon2"/>
                                    </div>

                                    <div class="form-group">
                                        <label for="descriptionId">Note Description</label>
                                        <textarea v-model="note.description"
                                                  id="descriptionId"
                                                  class="form-control">
                                        </textarea>
                                    </div>

                                    <button v-on:click="addNote()"
                                            type="button"
                                            class="btn btn btn-primary">
                                        Add Note
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="card mt-5" v-if="notes && notes.length !== 0">

                    <div class="card-header">
                        List of Notes
                    </div>

                    <div class="card-body">

                        <table class="table table-striped">
                            <thead>
                            <th scope="col">Title</th>
                            <th scope="col">Description</th>
                            <th scope="col"></th>
                            </thead>

                            <tbody>
                            <tr v-for="note in notes">
                                <td>
                                    <a href="">
                                        <router-link :to="{path: 'note', query: {uid: note.id }}">{{note.title}}
                                        </router-link>
                                    </a>
                                </td>
                                <td>
                                    <a href="">
                                        <router-link :to="{path: 'note', query: {uid: note.id }}">{{note.description}}
                                        </router-link>
                                    </a>
                                </td>
                                <td class="float-right">
                                    <button v-on:click="removeNote(note.id)"
                                            type="button"
                                            class="btn btn-danger">
                                        Remove
                                    </button>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>

            </div>
        </div>

    </div>

</template>

<script>

    import {addNote, getNotes, removeNote} from '../services/NoteService'

    export default {
        name: 'ListNote',
        methods: {

            getNotes() {
                getNotes()
                    .then(response => {
                        this.notes = response.data;
                    })
                    .catch(() => {
                        // error handler
                    });
            },

            addNote() {

                addNote(this.note)
                    .then(() => {
                        this.getNotes();
                        this.note = {};
                    })
                    .catch(() => {
                        // error handler
                    });
            },
            removeNote(noteId) {

                removeNote(noteId)
                    .then(() => {
                        this.getNotes();
                    })
                    .catch(() => {
                        // error handler
                    });
            }
        },
        data: function () {
            return {
                notes: [],
                note: {
                    title: '',
                    description: ''
                }
            }
        },
        beforeMount() {
            this.getNotes();
        }
    }

</script>


