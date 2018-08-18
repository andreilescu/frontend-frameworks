<template>
  <div id="app">
    <list-note/>
  </div>
</template>

<script>
    import ListNote from './components/ListNote.vue'

    export default {
  name: 'app',
  components: {
      ListNote
  }
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
