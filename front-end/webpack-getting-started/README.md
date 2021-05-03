webpack基本用法演示

初始化webpack项目
npm init

本项目安装webpack、webpack-cli
npm install webpack webpack-cli --save-dev

本项目安装html-webpack-plugin
npm install html-webpack-plugin --save-dev

development环境编译运行demo，结果输出120
webpack --mode development
node dist/main.js

production环境编译运行demo，结果输出120
webpack --mode production
node dist/main.js
