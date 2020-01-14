<template>
  <body id="poster">
    <el-form class="login-container" label-position="left" label-width="0px" :rules="loginFormRules"  :model="loginForm" ref="loginForm">
      <h3 class="login_title">系统登陆</h3>
      <el-form-item prop="username">
        <el-input type="text" v-model="loginForm.username" auto-complete="off" placeholder="账号" autofocus @input="change($event)">
          <i slot="prefix" class="el-icon-s-custom" style="padding-right: 5px;color: black;border-right: 1px solid lightgrey"></i>
          <i slot="suffix" class="el-icon-success" style="margin-right: 5px;color: green" v-show="userShow"></i>
        </el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input type="password" v-model="loginForm.password" auto-complete="off" placeholder="密码" @input="change($event)">
          <i slot="prefix" class="el-icon-s-goods" style="padding-right: 5px;color: black;border-right: 1px solid lightgrey"></i>
          <i slot="suffix" class="el-icon-success" style="margin-right: 5px;color: green" v-show="passShow"></i>
        </el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" style="width: 100%;background-color: #505458;border: none;" @click="login('loginForm')">登录</el-button>
        <router-link to="register"><el-button type="primary" style="width: 100%;background-color: #505458;border: none;">注册</el-button></router-link>
      </el-form-item>
    </el-form>
  </body>
</template>

<script>
    export default {
      name: "Login",
      data(){
        return {
          passShow: false,
          userShow: false,
          loginForm: {
            username: '',
            password: ''
          },
          responseResult: [],
          // 表单验证，需要在 el-form-item 元素中增加 prop 属性
          loginFormRules: {
            username: [
              {required: true, message: '账号不可为空', trigger: 'blur'}
            ],
            password: [
              {required: true, message: '密码不可为空', trigger: 'blur'}
            ]
          }
        }
      },
      mounted() {
      },
      methods:{
        login(formName){
          var _this = this
          this.$refs[formName].validate(valid => {
            if (valid){
              this.$axios.post('/login',{
                username: this.loginForm.username,
                password: this.loginForm.password
              }).then(successResponse => {
                console.log(successResponse)
                if(successResponse.data.code === 200) {
                  _this.$store.commit('login', _this.loginForm)
                  // console.log(this.$store.state.user)
                  var path = this.$route.query.redirect
                  // console.log(path)
                  this.$router.push({path: path === '/' || path === undefined ? '/index' : path})
                  //手动清理store缓存
                  // _this.$store.commit('login', '')
                }else{
                  this.$alert(successResponse.data.message,'提示',{
                    confirmButtonText: '确定'
                  })
                }
              }).catch(failResponse => {
                alert("账号或密码错误！")
              })
            }else{
              return false
            }
          })
        },
        change(e) {
          if (this.loginForm.password) {
            this.passShow = true
          }else{
            this.passShow = false
          }
          if (this.loginForm.username){
            this.userShow = true
          } else {
            this.userShow = false
          }
        }
      }
    }
</script>

<style scoped>
  body{
    margin: 0px;
  }
  .login-container{
    border-radius: 15px;
    background-clip: padding-box;
    margin: 90px auto;
    width: 350px;
    padding: 35px 35px 15px 35px;
    background: #fff;
    border: 1px solid #eaeaea;
    box-shadow: 0 0 25px #cac6c6;
  }
  .login_title {
     margin: 0px auto 40px auto;
     text-align: center;
     color: #505458;
   }
  #poster{
    background:url("../assets/backgroud/eva.jpg") no-repeat;
    background-position: center;
    height: 100%;
    width: 100%;
    background-size: cover;
    position: fixed;
  }
</style>
