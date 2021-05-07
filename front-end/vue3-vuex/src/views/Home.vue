<template>
  <div class="home">

    <div>Home页面操作并显示vuex count变量</div>
    <button v-on:click="sub()">-</button>
    {{$store.state.count}}
    <button v-on:click="add()">+</button>

    <hr>

    <HelloWorld msg="Welcome to Your Vue.js App"/>
  </div>
</template>

<script>
// @ is an alias to /src
import HelloWorld from '@/components/HelloWorld.vue'

export default {
  name: 'Home',
  components: {
    HelloWorld
  },
  methods: {
    sub() {
      if(this.$store.state.count>1) {
        this.$store.state.count--;
      }
    },
    add() {
      if(this.$store.state.count<10) {
        this.$store.state.count++;
      }
    }
  }
}
</script>
