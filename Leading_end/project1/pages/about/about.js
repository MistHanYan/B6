// pages/about/about.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    // 设备型号
    model: "",
    // 微信设置的语言
    language: "",
    // 微信版本号
    version: "",
    // 操作系统及版本号
    system: "",
    // 客户端版本号
    platform: ""
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {

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
    var array = []
    wx.getSystemInfo({
      success(res) {
        array[0] = res.model
        array[1] = res.language
        array[2] = res.version
        array[3] = res.system
        array[4] = res.platform
      }
    })
    this.setData({
      model : array[0],
      language : array[1],
      version : array[2],
      system : array[3],
      platform : array[4]
    })
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