const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');

module.exports = {
    entry: {
        app: [
            './index.js'
        ],
        vendor: [
            'react',
            'react-dom'
        ]
    },
    output: {
        filename: '[name].js',
        path: path.join(__dirname, '../resources/static/')
    },
    devtool: 'eval-source-map',
    module: {
        rules: [
            {
                test: /\.js|jsx$/,
                loader: 'babel-loader',
                exclude: /node_modules/,
                query: {
                    plugins: ['transform-class-properties']
                }
            },
            {
                test: /\.scss$/,
                loader: ['style-loader', 'css-loader', 'postcss-loader', 'sass-loader']
            },
            {
                test: /\.css$/,
                include: /node_modules/,
                loaders: ['style-loader', 'css-loader']
            },
            {
                test: /\.(png|woff|woff2|eot|ttf|svg)$/,
                loader: 'url-loader?limit=100000'
            }
        ]
    },
    plugins: [
        new HtmlWebpackPlugin({
            template: './index.html',
            filename: 'index.html',
            inject: 'body'
        })
    ]
};
