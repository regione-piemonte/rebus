const path = require('path');
var webpack = require('webpack');
var webpackMerge = require('webpack-merge');

// caricamento della configurazione comune, che verra' integrata in
// questo file
var commonConfig = require('./webpack.common.js');

/// caricamento parametri di ambiente
const ENV = process.env.NODE_ENV = process.env.ENV = 'development';
const ENV_PARAMS = require('./local.properties.json');

/// merge della configurazione
module.exports = webpackMerge(commonConfig, {
  output: {
    jsonpFunction:'webpackJsonp',
    "path": path.join(process.cwd(), "dist"),
    "publicPath": ENV_PARAMS.publicPath,
    "filename": "[name].bundle.js",
    "chunkFilename": "[id].chunk.js",
  },
  plugins: [
    new webpack.EnvironmentPlugin({
      NODE_ENV: 'development', // use 'development' unless process.env.NODE_ENV is defined
      DEBUG: false
    }),
    
    new webpack.DefinePlugin({
      'ENV_PROPERTIES': JSON.stringify(ENV_PARAMS)
    })
  ],
  devServer: {
    historyApiFallback: true,
    stats: 'minimal',
    publicPath: ENV_PARAMS.publicPath,
    //contentBase: [path.join(process.cwd(), "dist")],
    proxy: {
      '/anagrafichetplweb/restfacade': {
        target: ENV_PARAMS.devBackendServer,
        secure: false
      }
    }
  }
});
