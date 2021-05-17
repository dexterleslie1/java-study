import { createStore } from 'vuex'

export default createStore({
  state: {
    count:0,
    countMutation: 0,
    countMutationWithSingleParam: 0,
    countMutationWithMultipleParams: 0,

    // 所有商品列表
    goods: [
      {name: "《书本1》", price: 10},
      {name: "《书本2》", price: 20},
      {name: "《书本3》", price: 30},
      {name: "《书本4》", price: 40},
      {name: "《书本5》", price: 50},
      {name: "《书本6》", price: 60}
    ],

    // 登录状态显示
    loginStatus: ""
  },
  mutations: {
    // mutation修改变量
    addCountMutation(state) {
      state.countMutation++;
    },
    subCountMutation(state) {
      if(state.countMutation>0) {
        state.countMutation--;
      }
    },

    // mutation带一个参数修改变量
    addCountMutationWithSingleParam(state, step) {
      state.countMutationWithSingleParam = state.countMutationWithSingleParam+step;
    },
    subCountMutationWithSingleParam(state, step) {
      let countTemporary = state.countMutationWithSingleParam-step
      if(countTemporary>=0) {
        state.countMutationWithSingleParam = countTemporary;
      }
    },

    // mutation多个参数修改变量
    addCountMutationWithMultipleParams(state, payload) {
      state.countMutationWithMultipleParams = state.countMutationWithMultipleParams+payload.step1+payload.step2;
    },
    subCountMutationWithMultipleParams(state, payload) {
      let countTemporary = state.countMutationWithMultipleParams-payload.step1-payload.step2;
      if(countTemporary>=0) {
        state.countMutationWithMultipleParams = countTemporary;
      }
    },

    setLoginStatus(state, statusText) {
      state.loginStatus = statusText;
    }
  },
  getters: {
    totalGoodsPrice(state, getters) {
      let funTemporary = getters.totalGoodsPriceWithParam;
      return funTemporary(0);
    },
    totalGoodsPriceWithParam(state) {
      return function(price) {
        let totalPrice = 0;
        for(let i=0; i<state.goods.length; i++) {
          let priceTemporary = state.goods[i].price;
          if(priceTemporary>=price) {
            totalPrice += priceTemporary;
          }
        }
        return totalPrice;
      }
    }
  },
  actions: {
    login(context, loginInfo) {
      return new Promise((resolve, reject)=>{
        console.log("loginInfo=" + JSON.stringify(loginInfo));

        let min = 0;
        let max = 1;
        // 模拟ajax调用后端api
        context.commit("setLoginStatus", "开始请求后端登录接口，稍后。。。")
        setTimeout(()=>{
          let randomInt = Math.floor(Math.random()*(max - min + 1)) + min;
          if(randomInt==0) {
            // 模拟调用ajax成功
            resolve();
          } else {
            // 模拟调用ajax失败
            reject();
          }
        }, 3000);
      });
    }
  },
  modules: {
  }
})
