const webpack = require('webpack');
const writeFilePlugin = require('write-file-webpack-plugin');
const webpackMerge = require('webpack-merge');
const BrowserSyncPlugin = require('browser-sync-webpack-plugin');
const WebpackNotifierPlugin = require('webpack-notifier');
const path = require('path');

const utils = require('./utils.js');
const commonConfig = require('./webpack.common.js');

const ENV = 'development';

module.exports = webpackMerge(commonConfig({ env: ENV }), {
    devtool: 'eval-source-map',
    devServer: {
        contentBase: './build/www',
        proxy: [{
            context: [
                '/api',
                '/management',
                '/auth'
            ],
            target: 'http://127.0.0.1:8080',
            secure: false
        }],
        watchOptions: {
            ignored: /node_modules/
        }
    },
    entry: {
        polyfills: './src/main/webapp/app/polyfills',
        global: './src/main/webapp/content/scss/global.scss',
        main: './src/main/webapp/app/app.main'
    },
    output: {
        path: utils.root('build/www'),
        filename: 'app/[name].bundle.js',
        chunkFilename: 'app/[id].chunk.js'
    },
    module: {
        rules: [{
            test: /\.ts$/,
            enforce: 'pre',
            loaders: 'tslint-loader',
            exclude: ['node_modules', new RegExp('reflect-metadata\\' + path.sep + 'Reflect\\.ts')]
        },
        {
            test: /\.ts$/,
            loaders: [
                'angular2-template-loader',
                'awesome-typescript-loader'
            ],
            exclude: ['node_modules/generator-jhipster']
        },
        {
            test: /\.scss$/,
            loaders: ['to-string-loader', 'css-loader', 'sass-loader'],
            exclude: /(vendor\.scss|global\.scss)/
        },
        {
            test: /(vendor\.scss|global\.scss)/,
            loaders: ['style-loader', 'css-loader', 'postcss-loader', 'sass-loader']
        },
        {
            test: /\.css$/,
            loaders: ['to-string-loader', 'css-loader'],
            exclude: /(vendor\.css|global\.css)/
        },
        {
            test: /(vendor\.css|global\.css)/,
            loaders: ['style-loader', 'css-loader']
        }]
    },
    plugins: [
        new BrowserSyncPlugin({
            host: 'localhost',
            port: 9000,
            proxy: {
                target: 'http://localhost:9060'
            }
        }, {
            reload: false
        }),
        new webpack.NoEmitOnErrorsPlugin(),
        new webpack.NamedModulesPlugin(),
        new writeFilePlugin(),
        new webpack.WatchIgnorePlugin([
            utils.root('src/test'),
        ]),
        new WebpackNotifierPlugin({
            title: 'AutoPOS',
            contentImage: path.join(__dirname, 'AutoPOS-logo.jpg')
        })
    ]
});
