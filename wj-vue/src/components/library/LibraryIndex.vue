<template>
  <el-container>
    <el-aside style="width: 200px;margin-top: 20px">
      <switch></switch>
      <SideMenu @indexSelect="listByCategory" ref="sideMenu"></SideMenu>
    </el-aside>
    <el-main>
      <books class="books-area" ref="booksArea" :cids="cid"></books>
    </el-main>
  </el-container>
</template>

<script>
  import SideMenu from './SideMenu'
  import Books from './Books'
  export default {
    name: 'AppLibrary',
    data(){
      return{
        cid: ''
      }
    },
    components: {SideMenu, Books},
    methods: {
      listByCategory(){
        var _this = this
        this.cid = this.$refs.sideMenu.cid
        console.log("cid:    "+this.cid)
        var url = 'categories/' + this.cid + '/books'
        this.$axios.get(url).then(resp => {
          if (resp && resp.status === 200) {
            _this.$refs.booksArea.books = resp.data
          }
        })
      }
    }
  }
</script>

<style scoped>
  .books-area {
    width: 990px;
    margin-left: auto;
    margin-right: auto;
  }
</style>

