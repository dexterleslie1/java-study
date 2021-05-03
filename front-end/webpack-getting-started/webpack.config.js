const {resolve} = require("path");
const HtmlWebpackPlugin = require("html-webpack-plugin");

module.exports = {
    // webpack 打包入口文件配置
    // 多入口配置
    entry: ["./src/index.js", "./src/entry1.js"],

    // webpack打包后输出配置
    output: {
        // 输出文件名
        filename: "built.js",
        // 输出目录
        path: resolve(__dirname, "build")
    },

    // loader处理非javascript、json资源，例如：翻译sass为css、img

    plugins: [
        // 使用html-webpack-plugin插件自动引入js、css资源文件index.html
        new HtmlWebpackPlugin({
            template: "./src/index.html",
            minify: {
                // 删除空格
                collapseWhitespace: true,
                // 删除注释
                removeComments: true
            }
        })
    ],

    // development、开发模式 production、生产模式
    mode: "development"
}