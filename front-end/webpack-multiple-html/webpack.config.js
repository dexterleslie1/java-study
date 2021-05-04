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
    mode: "development"
}