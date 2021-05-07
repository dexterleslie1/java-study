<template>
  <div>
      <table>
        <caption><h1>购物车</h1></caption>
        <tr>
          <th></th>
          <th>商品编号</th>
          <th>商品名称</th>
          <th>商品价格</th>
          <th>购买数量</th>
          <th>操作</th>
        </tr>
        <tr v-for="item in cartlist" v-bind:key="item.id" v-if="this.cartlist.length>0">
          <td><input type="checkbox" v-model="item.selected"></td>
          <td>{{item.id}}</td>
          <td>{{item.name}}</td>
          <td>{{item.price.toFixed(2)}}</td>
          <td>
            <button v-on:click="item.count--" v-bind:disabled="item.count<=1">-</button>
            {{item.count}}
            <button v-on:click="item.count++">+</button>
          </td>
          <td><a href="#" v-on:click.prevent="del(item)">删除</a></td>
        </tr>
        <tr v-if="this.cartlist.length>0">
          <td colspan="5" align="right">总计：</td>
          <td>￥{{totalPrice.toFixed(2)}}</td>
        </tr>
        <tr v-else>
          <td colspan="6" align="center">你没有购买商品</td>
        </tr>
      </table>
  </div>
</template>

<script>
export default {
  name: 'App',
  data() {
    return {
      cartlist:[
        {id:1, name:"《细说PHP》", price:12, count:1, selected:true},
        {id:2, name:"《细说Java》", price:12, count:1, selected:true},
        {id:3, name:"《细说Javascript》", price:12, count:1, selected:true},
        {id:4, name:"《细说Python》", price:12, count:1, selected:true}
      ]
    }
  },
  computed: {
    totalPrice: function() {
      let sum = 0;
      for(let cartObject of this.cartlist) {
        if(cartObject.selected) {
          sum += cartObject.price * cartObject.count;
        }
      }
      return sum;
    }
  },
  methods: {
    del(cartObject) {
      let id = cartObject.id;
      this.cartlist = this.cartlist.filter((item) => item.id !== id);
    }
  }
}
</script>

<style>
table {
  border-width: 1px;
  border-style: solid;
  border-color: #888888;
  border-collapse: collapse;
}

th {
  background-color: #cccccc;
}

th, td {
  border-width: 1px;
  border-style: solid;
  border-color: #888888;
  padding:10px;
}
</style>
