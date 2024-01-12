const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
    outputDir: '../gateway/src/main/resources/static',
    devServer: {
      proxy: 'http://152.67.205.195:8000'
    }
})
