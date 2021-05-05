import axios from "axios";

// 全局配置
axios.defaults.baseURL = "http://c.com:8080";
axios.defaults.timeout = 5000;
let api1 = axios.create({
    baseURL: "http://a.com:8080",
    timeout: 5000
});
// request、response 拦截器
api1.interceptors.request.use((config)=>{
    let json = JSON.stringify(config);
    let message = `请求前拦截config=${json}`;
    console.log(message);
    return config;
}, (error)=>{
    console.log(error);
});
api1.interceptors.response.use((config)=>{
    let json = JSON.stringify(config);
    let message = `请求响应拦截config=${json}`;
    console.log(message);
    return config;
}, (error)=>{
    console.log(error);
});
let api2 = axios.create({
    baseURL: "http://b.com:8080",
    timeout: 5000
});

// 测试全局配置
axios.get("api/v1/get", {params: {name: "Dexterleslie0"}}).then((result)=>{
    console.log(result);
});

// 测试api1、api2 axios实例
api1.get("api/v1/get", {params: {name: "Dexterleslie"}}).then((result)=>{
    console.log(result);
});
api2.get("api/v1/get", {params: {name: "Dexterlesllie2"}}).then((result)=>{
    console.log(result);
});

// 测试request、response拦截
api1.get("api/v1/get", {params: {name: "Dexterleslie"}}).then((result)=>{
    console.log(result);
});

// 测试并发请求
axios.all([
    api1.get("api/v1/get", {params: {name: "Dexterleslie0"}}),
    api2.get("api/v1/get", {params: {name: "Dexterleslie1"}})
]).then((result)=>{
    console.log(result);
}).catch((error)=>{
    console.log(error);
}).finally(()=>{
    console.log("finally回调")
});