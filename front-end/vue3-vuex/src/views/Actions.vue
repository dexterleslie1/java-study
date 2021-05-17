<template>
  <div>
    <div>演示vuex actions用法</div>
    {{$store.state.loginStatus}}
    <br/>
    <button v-on:click="onStart()">开始模拟</button>
    <hr/>
  </div>
</template>

<script>
export default {
  name: "Actions",
  methods: {
    onStart() {
      let loginInfo = {loginname: "zhangsan", password: "123456", verificationCode: "1234"};
      let promise = this.$store.dispatch("login", loginInfo);
      promise.then((result)=>{
        this.$store.commit("setLoginStatus", "登录成功");
      }).catch((error)=>{
        this.$store.commit("setLoginStatus", "E#100001业务调用错误");
      });
    }
  }
}
</script>

<style scoped>

</style>