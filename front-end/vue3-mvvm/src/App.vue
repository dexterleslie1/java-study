<template>
  num: {{num}}
  <br>
  <input type="button" value="-" @click="sub()"/>
  <input type="text" size="5" v-model="num"/>
  <input type="button" value="+" @click="add()"/>
</template>

<script>
export default {
  name: 'App',
  data() {
    return {
      num: 0,
      min: 1,
      max: 10
    }
  },
  methods: {
    sub() {
      if(this.num <= this.min) {
        this.num = this.min;
      } else {
        this.num--;
      }
    },
    add() {
      if(this.num >= this.max) {
        this.num = this.max;
      } else {
        this.num++;
      }
    }
  }
}
</script>

<style>
</style>
