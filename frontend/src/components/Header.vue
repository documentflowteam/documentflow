<template>
    <div>
        <b-navbar toggleable type="dark" variant="info">

            <b-navbar-brand href="#">
                <div class="row">
                <b-icon icon="inboxes-fill"
                        font-scale="3"
                        class="text-light col-md-4 col-md-offset-4">

                </b-icon>
                <h1 class="col-md-4 col-md-offset-2">Документооборот</h1>
                </div>

            </b-navbar-brand>

            <b-navbar vtarget="nabar">
                <b-icon icon="box-arrow-right"
                        font-scale="2"
                        class="text-light"
                        v-on:click="logout"
                ></b-icon>
            </b-navbar>
        </b-navbar>
    </div>
</template>

<script>
  import api from './backend-api'
  import store from './../store'

export default {
  name: 'Header',

  data () {
    return {
      errors: null
    }
  },
  methods: {
      logout(){
        window.location.href = '/logout'
      }
  }
}

</script>

<style scoped>

</style>