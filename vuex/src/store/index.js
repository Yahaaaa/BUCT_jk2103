import { createStore } from 'vuex'
import axios from "axios"
export default createStore({
  state: {
    counter:10,
    user: null,
  },
  getters:{
    getCounter(state){
      return state.counter/*>0?state.counter:"counter小于0，不符合要求"*/
    }
  },
  mutations:{
    addCounter(state,num){
      state.counter+=num
    },
    setUser(state, user) {
      state.user = user;
      if (user) {
        localStorage.setItem('user', JSON.stringify(user))
      } else {
        localStorage.removeItem('user')
      }
    },
  },
  //为异步操作做的准备
  actions:{
    asyncAddCounter({commit}){
      axios.get("http://iwenwiki.com/api/generator/list.php")
          .then(res=>{
            console.log("11111111");
            console.log(res.data[0]);
            commit("addCounter",res.data[0])
          })
    },
    getUser({ commit }) {
      const userString = localStorage.getItem('user')
      if (userString) {
        const user = JSON.parse(userString)
        commit('setUser', user)
      }
    }
  }

})
