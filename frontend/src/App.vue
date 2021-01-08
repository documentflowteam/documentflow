<template>
  <div id="app" >
    <Header></Header>

    <div class="row">
      <b-list-group class="col-4">

<!--        <b-list-group-item>-->
<!--          <router-link to="old/docs/in">Входящие документы</router-link>-->
<!--        </b-list-group-item>-->
<!--        <b-list-group-item>-->
<!--          <router-link to="old/docs/out">Исходящие документы</router-link>-->
<!--        </b-list-group-item>-->
<!--        <b-list-group-item>-->
<!--          <router-link to="old/tasks/in">Задачи</router-link>-->
<!--        </b-list-group-item>-->
<!--        <b-list-group-item>-->
<!--          <router-link to="old/sys/departments">Отделы</router-link>-->
<!--        </b-list-group-item>-->

        <b-list-group-item>
          <a href="old/docs/in">Входящие документы</a>
        </b-list-group-item>
        <b-list-group-item>
          <a href="old/docs/out">Исходящие документы</a>
        </b-list-group-item>
        <b-list-group-item>
          <a href="old/tasks/in">Задачи</a>
        </b-list-group-item>
        <b-list-group-item>
          <a href="old/sys/departments">Отделы</a>
        </b-list-group-item>

      </b-list-group>
      <router-view ></router-view>
    </div>

  </div>

</template>

<script>

  export default {
    name: 'app',
    data () {
      return {
        isAuthenticated: false,
      }
    },
    components: {
      Header: () => import("@/components/Header")
    }

  }
</script>

<style lang="scss" scoped>
  .col-4{
    margin-top: 10px;
    text-align: center;
  }
</style>
