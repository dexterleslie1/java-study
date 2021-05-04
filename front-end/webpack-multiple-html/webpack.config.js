const {resolve} = require("path");

const HtmlWebpackPlugin = require("html-webpack-plugin");
const MiniCssExtractPlugin = require("mini-css-extract-plugin");

module.exports = {
    // index、cart html页面js分开
    entry: {
        // index html页面js
        index: ["./src/js/index.js"],
        // cart html页面js
        cart: ["./src/js/cart.js"]
    },
    output: {
        filename: "[name].js",
        path: resolve(__dirname, "build")
    },

    // 配置css loader
    module: {
        rules: [
            {test:/\.css$/, use: [MiniCssExtractPlugin.loader, "css-loader"]}
        ]
    },

    plugins:[
        new HtmlWebpackPlugin({
            template: "./src/index.html",
            filename: "index.html",
            chunks: ["index"],
            minify: {
                collapseWhitespace: true,
                removeComments: true
            }
        }),
        new HtmlWebpackPlugin({
            template: "./src/cart.html",
            filename: "cart.html",
            chunks: ["cart"]
        }),
        // 提取单独css文件插件
        new MiniCssExtractPlugin({
            filename: "all-in-on.css"
        })
    ],

    mode: "production",

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