import { createStore } from 'vuex'

const store=createStore({
  //放参数
  state: {
    user:{}
  },
  //操控变量(同步)
  mutations: {
    //state：内部变量，user外部变量
    setUser(state,user){
      state.user=user;

    }
  },
  //操控变量(异步)
  actions: {
  },
  modules: {
  }
})
export default store;
