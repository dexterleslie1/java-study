const {resolve} = require("path");
const HtmlWebpackPlugin = require("html-webpack-plugin");

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
    ],
    mode: "development"
}