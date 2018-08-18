<template>
    <div class="hello">

        <input v-model="note.title"/>
        <input v-model="note.description"/>

        <button v-on:click="bar()">Add note</button>

        <div v-for="note in notes">
            <span>{{note.title}}</span>
            <span>{{note.description}}</span>
        </div>

    </div>

</template>

<script>

    export default {
        name: 'ListNote',
        methods: {
            bar() {
                this.notes.push(this.note);
                this.note = {};
            }
        },
        data: function() {
            return {
                notes: [],
                note: {
                    title: '',
                    description: ''
                }
            }
        }
    }

</script>


