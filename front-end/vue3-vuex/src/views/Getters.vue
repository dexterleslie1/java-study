<template>
  <div>
    <div>演示不带参数getters用法</div>
    <div>商品总计：{{$store.getters.totalGoodsPrice}}</div>
    <hr/>

    <div>演示带参数getters用法</div>
    <div>商品价格大于40总计：{{$store.getters.totalGoodsPriceWithParam(40)}}</div>
    <hr/>
  </div>
</template>

<script>
export default {
  name: "Gettters"
}
</script>

<style scoped>

</style>