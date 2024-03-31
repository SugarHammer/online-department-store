module.exports = {
  //devServer.proxy适用于本地开发使用，
  //生成环境请用第三方代理软件，如nginx。
  devServer: {
      port: 8082, //本机端口号
      host: "localhost", //本机主机名
      https: false, //协议
      hotOnly:true,
      disableHostCheck:true,
      open: true, //启动服务器时自动打开浏览器访问
      proxy: {
          '/api': {
              //目标服务器,代理访问到
              target: 'http://localhost:8081',
              changOrigin: true, //开启代理
              ws:true,
              pathRewrite: {
                  '^/api': ''
              }
          }
      }
  },
  lintOnSave: false
}