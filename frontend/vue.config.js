module.exports = {
  devServer: {
    proxy: {
      '/api': {
        target: 'http://localhost:8090', // this configuration needs to correspond to the Spring Boot backends' application.properties server.port
        ws: true,
        changeOrigin: true
      },
      '/old': {
        target: 'http://localhost:8090', // this configuration needs to correspond to the Spring Boot backends' application.properties server.port
        ws: true,
        changeOrigin: true
      }
    }
  },

  outputDir: '../src/main/resources/public',
  assetsDir: 'static'
};
