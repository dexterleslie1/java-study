<template>
  <div>
    <div class="navigator">
      <ul>
        <li><router-link to="/help/helpArticle?article=download">下载帮助</router-link></li>
        <li><router-link to="/help/helpArticle?article=login">登录帮助</router-link></li>
      </ul>
    </div>
    <div class="content">
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
export default {
  name: "Help"
}
</script>

<style scoped>
.navigator {
  width: 30%;
  height: 400px;
  background-color: #42b983;
  float: left;
}
.content {
  width: 70%;
  height: 400px;
  background-color: #cccccc;
  float: right;
}
ul {
  display: inline-block;
  margin: 0px;
  padding: 0px;
}
</style>