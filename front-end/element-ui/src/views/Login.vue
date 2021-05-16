<template>
  <div class="login-main">
    <el-form label-width="80px" class="login-form" v-bind:model="loginModel">
      <el-form-item label="账号">
        <el-input placeholder="输入账号" v-model="loginModel.loginname"></el-input>
      </el-form-item>
      <el-form-item label="密码">
        <el-input placeholder="输入密码" show-password v-model="loginModel.password"></el-input>
      </el-form-item>
      <el-form-item label="验证码">
        <el-row :span="24">
          <el-col :span="16">
            <el-input
                placeholder="输入验证码"
                v-model="loginModel.verificationCode"
                v-bind:maxlength="4"></el-input>
          </el-col>
          <el-col :span="8">
            <img src="" alt="" class="login-verification-code"/>
          </el-col>
        </el-row>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click.prevent="submitLogin" class="login-button-submit">登录</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  name: "Login",
  data() {
    return {
      loginModel: {
        loginname: "",
        password: "",
        verificationCode: ""
      }
    }
  },
  methods: {
    submitLogin() {
      console.log(this.loginModel);
    }
  }
}
</script>

<style scoped>
  .login-main {
    margin: 0 auto;
    width: 60%;
  }

  .login-verification-code {
    width: 100%;
    height: 100%;
  }

  .login-button-submit {
    width: 100%;
    background: none;
    border-style: solid;
    border-width: 1px;
    border-color: #409eff;
    color: #409eff;
  }
</style>