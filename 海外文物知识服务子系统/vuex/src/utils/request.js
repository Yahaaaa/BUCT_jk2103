import axios from "axios"
import * as querystring from "querystring";

const errorHandle=(status,info)=>{
    switch (status){
        case 400:
            console.log("语义有误");
            break;
        case 401:
            console.log("服务器有误");
            break;
        case 403:
            console.log("服务器拒绝访问");
            break;
        case 404:
            console.log("地址错误");
            break;
        case 500:
            console.log("服务器遇到意外");
            break;
        case 502:
            console.log("服务器无响应");
            break;
        default:
            console.log(info);
            break;
    }
}
const instance=axios.create({
    timeout:5000
})

//拦截器
//发送数据之前
instance.interceptors.request.use(
    config=>{
        if(config.methods==="post"){
            config.data=querystring.stringify(config.data)
        }
        // 包含网络请求的所有信息
        return config;
    },
    error=>{
        return Promise.reject(error)
    }
)

//获取数据之前
instance.interceptors.response.use(
    response=>{
        return response.status===200?Promise.resolve(response):Promise.reject(response)
    },
    error=>{
        const {response}=error;
        errorHandle(response.status,response.info)
    }
)


export default instance;
