const {resolve} = require("path");

const HtmlWebpackPlugin = require("html-webpack-plugin");

module.exports = {
    entry: "./src/index.js",
    output: {
        filename: "built.js",
        path: resolve(__dirname, "build")
    },

    plugins:[
        new HtmlWebpackPlugin({
            template: "./src/index.html"
        })
    ],

    mode: "development",

    // webpack5 自动刷新浏览器
    target: "web",
    // webpack dev server配置
    devServer: {
        port: 3000,
        // 编译时使用gzip压缩
        compress: true,
        // 编译后自动打开浏览器
        open: true
    }
}