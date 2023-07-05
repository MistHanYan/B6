// app.js
App({
  onLaunch() {
    // 展示本地存储能力
    const logs = wx.getStorageSync('logs') || []
    logs.unshift(Date.now())
    wx.setStorageSync('logs', logs)

    // 登录
    wx.login({
      success: res => {
        if(res.code){
          let APPID = "wx6ee0d4d6edcfbc7c"
          let SECRET = "90fd7a9771d72240b6bb3d72f774248a"
          let JSCODE = res.code
          let URL = "https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code"
          // 发送网络请求，本来这个请求应该在后台进行请求
          wx.request({
            url:URL,
            success: res => {
              console.log(res.data)
            }
          })
        }
        // 发送 res.code 到后台换取 openId, sessionKey, unionId
        // 然后后面的所有需要用到用户的页面，都需要在后端根据openID是否在数据库里面存在，若存在，这跳过，若不存在，则，自行加入头像、昵称和openID，加入到数据库，然后在判断
      }
    })
  },
  globalData: {
    userInfo: "用户id"
  }
})
