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

    export default {
        name: 'Note',
        methods: {

            getNote() {


                const noteId = this.$route.query.uid;


                // TODO extract to service / util
                const HOST = "http://localhost";
                const PORT = "8081";
                const PROXY = HOST + ":" + PORT;
                const NOTES = "/notes";
                const url = PROXY + NOTES + '/' + noteId;

                // get all notes from db
                this.$http.get(url).then((response) => {
                    this.note = response.data;
                });
            },

            editNote() {

                // TODO extract to service / util
                const HOST = "http://localhost";
                const PORT = "8081";
                const PROXY = HOST + ":" + PORT;
                const NOTES = "/notes";
                const url = PROXY + NOTES + '/' + this.note.id;

                // update note information
                this.$http.put(url, this.note);
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


