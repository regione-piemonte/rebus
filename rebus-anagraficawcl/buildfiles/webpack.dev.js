const path = require('path');
var webpack = require('webpack');
var webpackMerge = require('webpack-merge');

// caricamento della configurazione comune, che verra' integrata in
// questo file
var commonConfig = require('./webpack.common.js');

/// caricamento dei parametri di ambiente
const ENV = process.env.NODE_ENV = process.env.ENV = 'dev';
const ENV_PARAMS = require('./dev.properties.json');

const UglifyJsPlugin = require('uglifyjs-webpack-plugin');

/// merge della configurazione
module.exports = webpackMerge(commonConfig, {
  output: {
    jsonpFunction: 'webpackJsonp',
    "path": path.join(process.cwd(), "dist"),
    "publicPath": ENV_PARAMS.publicPath,
    "filename": "[name].bundle.js",
    "chunkFilename": "[id].chunk.js"
  },
  plugins: [
    new webpack.EnvironmentPlugin({
      NODE_ENV: 'dev', // use 'development' unless process.env.NODE_ENV is defined
      DEBUG: false
    }),

    new webpack.DefinePlugin({
      'ENV_PROPERTIES': JSON.stringify(ENV_PARAMS)
    }),

    new UglifyJsPlugin()
  ]
});