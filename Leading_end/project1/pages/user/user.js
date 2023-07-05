// pages/index-user/inderx_user.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    id: "1111",
    motto: 'Hello World',
    userInfo: {},
    hasUserInfo: false,
    canIUse: wx.canIUse('button.open-type.getUserInfo'),
    canIUseGetUserProfile: false,
    canIUseOpenData: false,
    image:'/pages/Component/img/微信灰色头像.png',
    name: "微信用户"
  },
  // 跳转到用户界面，函数
  user(){
    wx.navigateTo({
      // url: '/pages/user/user',
      url: '/pages/logs/logs',
    })
  },

  // 跳转到关于界面
  about(){
    wx.navigateTo({
      url: '/pages/about/about',
    })
  },

  // 跳转到反馈界面
  feedback(){
    wx.navigateTo({
      url: '/pages/feedback/feedback',
    })
  },
  
  // 跳转到历史记录页面
  history(){
    wx.navigateTo({
      url: '/pages/history/history',
    })
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    if (wx.getUserProfile) {
      this.setData({
        canIUseGetUserProfile: true
      })
    }
  },

  getUserProfile(e) {
    // 推荐使用wx.getUserProfile获取用户信息，开发者每次通过该接口获取用户个人信息均需用户确认
    // 开发者妥善保管用户快速填写的头像昵称，避免重复弹窗
    wx.getUserProfile({
      desc: '用于完善用户资料', // 声明获取用户个人信息后的用途，后续会展示在弹窗中，请谨慎填写
      success: (res) => {
        console.log(res)
        this.setData({
          userInfo: res.userInfo,
          hasUserInfo: true
        })
        console.log(res.userInfo)
      }
    })
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady() {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow() {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide() {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload() {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh() {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom() {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage() {

  }
})