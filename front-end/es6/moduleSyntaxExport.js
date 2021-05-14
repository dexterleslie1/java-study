// 导出箭头函数
export const functionTest = (params = {a: 100})=>{
    let { a } = params;
    console.log("functionTest输入：" + a);
}

export default {
    key1: 1001,
    key2: "value2"
}