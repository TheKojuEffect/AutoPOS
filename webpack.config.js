module.exports = {
    devtool: 'inline-source-map',
    context: __dirname + '/src/main/webapp/app',
    entry: './app',
    output: {
        path: __dirname + '/src/main/webapp/app/dist',
        filename: 'bundle.js'
    },
    module: {
        loaders: [{
            test: /\.js$/,
            exclude: /node_modules/,
            loader: 'babel-loader'
        }]
    },
    devServer: {
        contentBase: __dirname + '/src/main/webapp/app',
        inline: true,
        port: 9090,
        proxy: {
            '/': {
                target: 'http://localhost:8080',
                secure: false,
                // node-http-proxy option - don't add /localhost:8080/ to proxied request paths
                prependPath: false
            },
        },
        publicPath: '/app/dist/'
        // hot: true
    }
};