<template>
  <body id="paper">
  <el-form class="login-container" label-position="left"
           label-width="0px" v-loading="loading" :model="loginForm" :rules="rules" ref="loginForm">
    <h3 class="login_title">用户注册</h3>
    <el-form-item prop="username">
      <el-input type="text" v-model="loginForm.username"
                auto-complete="off" placeholder="账号" autofocus @input="change($event)">
        <i slot="prefix" class="el-icon-s-custom" style="padding-right: 5px;color: black;border-right: 1px solid lightgrey"></i>
        <i slot="suffix" class="el-icon-success" style="margin-right: 5px;color: green" v-show="userShow"></i>
      </el-input>
    </el-form-item>
    <el-form-item prop="password">
      <el-input type="password" v-model="loginForm.password"
                auto-complete="off" placeholder="密码" :readonly="readonly">
        <i slot="prefix" class="el-icon-s-goods" style="padding-right: 5px;color: black;border-right: 1px solid lightgrey"></i>
        <i slot="suffix" class="el-icon-success" style="margin-right: 5px;color: green" v-show="passShow"></i>
      </el-input>
    </el-form-item>
    <el-form-item prop="copy">
      <el-input type="password" v-model="loginForm.copy"
                auto-complete="off" placeholder="确认密码" :readonly="readonly">
        <i slot="prefix" class="el-icon-s-goods" style="padding-right: 5px;color: black;border-right: 1px solid lightgrey"></i>
        <i slot="suffix" class="el-icon-success" style="margin-right: 5px;color: green" v-show="copyShow"></i>
      </el-input>
    </el-form-item>
    <el-form-item style="width: 100%">
      <el-button type="primary" style="width: 100%;background: #505458;border: none" v-on:click="register('loginForm')">注册</el-button>
    </el-form-item>
  </el-form>
  </body>
</template>

<script>
    export default {
        name: "Register",
      data () {
        // <!--验证手机号是否合法-->
        let checkTel = (rule, value, callback) => {
          if (value === '') {
            callback(new Error('请输入手机号码'))
          } else if (!this.checkMobile(value)) {
            callback(new Error('手机号码不合法'))
          } else {
            callback()
          }
        }
        //  <!--验证码是否为空-->
        let checkSmscode = (rule, value, callback) => {
          if (value === '') {
            callback(new Error('请输入手机验证码'))
          } else {
            callback()
          }
        }
        // <!--验证密码-->
        let validatePass = (rule, value, callback) => {
          if (value === ""){
            this.passShow = false
            callback(new Error("密码不能为空!"))
          }else{
            if (this.loginForm.password){
              this.passShow = true
              this.$refs.loginForm.validateField("checkPass")
            }
            callback()
          }
        }
        // <!--二次验证密码-->
        let validateCopy = (rule, value, callback) =>{
          if (value === ""){
            this.copyShow = false
            callback(new Error("请确认密码!"))
          }else if(value !== this.loginForm.password){
            this.copyShow = false
            callback(new Error("两次输入密码不一致!"))
          }else{
            this.copyShow = true
            callback()
          }
        }

        return {
          userShow: false,
          passShow: false,
          copyShow: false,
          loading: false,
          readonly: true,
          loginForm: {
            username: '',
            password: '',
            copy:''
          },
          rules: {
            username: [{ required: true, message: '请输入用户名!', trigger: 'blur' }],
            password: [{ required: true, validator: validatePass, trigger: 'change'}],
            copy: [{ required: true, validator: validateCopy, trigger: 'change' }]
          }
        }
      },
      mounted(){
      },
      methods:{
          register(formName) {
            var _this = this
            this.$refs[formName].validate(valid => {
              if (valid){
                this.loading = true
                this.$axios.post('/register', {
                  username: this.loginForm.username,
                  password: this.loginForm.password
                }).then(res => {
                  if (res.data.code === 200){
                    this.loading = false
                    this.$alert('注册成功','提示',{
                      confirmButtonText: '确定'
                    })
                    this.$router.replace('/login')
                  }else{
                    this.$alert(res.data.message,'提示',{
                      confirmButtonText: '确定'
                    })
                  }
                }).catch(failResponse => {})
              }
            })
          },
        // <!--发送验证码-->
        sendCode () {
          let tel = this.ruleForm2.tel
          if (this.checkMobile(tel)) {
            console.log(tel)
            let time = 60
            this.buttonText = '已发送'
            this.isDisabled = true
            if (this.flag) {
              this.flag = false;
              let timer = setInterval(() => {
                time--;
                this.buttonText = time + ' 秒'
                if (time === 0) {
                  clearInterval(timer);
                  this.buttonText = '重新获取'
                  this.isDisabled = false
                  this.flag = true;
                }
              }, 1000)
            }
          }
        },
        // 验证手机号
        checkMobile(str) {
          let re = /^1\d{10}$/
          if (re.test(str)) {
            return true;
          } else {
            return false;
          }
        },
        change(e){
          if(this.loginForm.username) {
            this.readonly = false
            this.userShow = true
          }else{
            this.readonly = true
            this.userShow = false
          }
        }
      }
    }
</script>

<style scoped>
  #paper {
    background:url("../assets/backgroud/eva.jpg") no-repeat;
    background-position: center;
    height: 100%;
    width: 100%;
    background-size: cover;
    position: fixed;
  }
  body{
    margin: -5px 0px;
  }
  .login-container {
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
</style>
