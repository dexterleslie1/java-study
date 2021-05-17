<template>
  <div>
    <div>演示不带参数vuex mutations使用</div>
    <button v-on:click="add()">+</button>
    {{$store.state.countMutation}}
    <button v-on:click="sub()">-</button>
    <hr/>

    <div>演示带一个参数vuex mutations使用</div>
    <button v-on:click="addWithSingleParam()">+</button>
    {{$store.state.countMutationWithSingleParam}}
    <button v-on:click="subWithSingleParam()">-</button>
    <hr/>

    <div>演示带多个参数vuex mutations使用</div>
    <button v-on:click="addWithMultipleParams()">+</button>
    {{$store.state.countMutationWithMultipleParams}}
    <button v-on:click="subWithMultipleParams()">-</button>
    <hr/>
  </div>
</template>

<script>
export default {
  name: "Mutations",
  methods: {
    add() {
      this.$store.commit("addCountMutation")
    },
    sub() {
      this.$store.commit("subCountMutation")
    },

    addWithSingleParam() {
      this.$store.commit("addCountMutationWithSingleParam", 3);
    },
    subWithSingleParam() {
      this.$store.commit("subCountMutationWithSingleParam", 2)
    },

    addWithMultipleParams() {
      this.$store.commit("addCountMutationWithMultipleParams", {step1: 2, step2: 1})
    },
    subWithMultipleParams() {
      this.$store.commit("subCountMutationWithMultipleParams", {step1: 1, step2: 1})
    }
  }
}
</script>

<style scoped>

</style>