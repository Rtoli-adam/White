<template>
  <el-menu
    :default-active="this.$route.path"
    router
    mode="horizontal"
    menu-trigger="click"
    background-color="white"
    text-color="#222"
    active-text-color="red"
    style="width: 100%;position: fixed;z-index: 999">
    <el-menu-item v-for="(item,i) in navList" :key="i" :index="item.name">
      {{ item.navItem }}
    </el-menu-item>
    <el-submenu index="2" style="float: right;">
      <template slot="title">更多</template>
      <el-menu-item>
        <i style="font-size: 17px;color: #222;margin-left: 35%">{{ username }}</i>
      </el-menu-item>
      <el-menu-item index="2-1" @click="logout">
        <i class="el-icon-switch-button" style="font-size: 17px;color: #222;margin-left: 35%">退出</i>
      </el-menu-item>
      <!--<el-menu-item index="2-2">选项2</el-menu-item>-->
      <!--<el-menu-item index="2-3">选项3</el-menu-item>-->
    </el-submenu>
    <i class="el-icon-menu" style="float:right;font-size: 45px;color: #222;padding-top: 8px"></i>
    <span style="position: absolute;padding-top: 20px;right: 43%;font-size: 20px;font-weight: bold">White Jotter - Your Mind Palace</span>
  </el-menu>
</template>

<script>
  export default {
    name: 'NavMenu',
    data () {
      return {
        navList: [
          {name: '/index', navItem: '首页'},
          {name: '/jotter', navItem: '笔记本'},
          {name: '/library', navItem: '图书馆'},
          {name: '/admin', navItem: '个人中心'}
        ],
        username: ''
      }
    },
    mounted() {
      this.username = this.$store.state.user.username
    },
    methods: {
      logout(){
        var _this = this
        console.log(11111111111111)
        this.$axios.get('/logout').then(res => {
          console.log(res.data.code)
          if (res.data.code === 200){
            alert(res.data.data)
            _this.$store.commit('logout')
            _this.$router.replace('/login')
          }
        })
      }
    }
  }
</script>

<style scoped>

  span {
    pointer-events: none;
  }
</style>

