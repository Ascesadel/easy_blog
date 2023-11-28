module.exports = {
    devServer: {
        proxy: {
            '/api': {
                target: 'http://localhost:8115/', //接口域名
                changeOrigin: true,             //是否跨域
                pathRewrite: {                  //路径重置
                    '^/api': ''
                }
            }
        }
    }
};

