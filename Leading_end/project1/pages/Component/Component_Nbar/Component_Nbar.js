// pages/Component/Component_Nbar/Component_Nbar.js
Page({
  

  /**
   * 页面的初始数据
   */
  data: {

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
Component({
  properties: {
  },
  data: {
    // 这里是一些组件内部数据
    someData: {}
  },
  methods: {
    // 跳转到识别页面
    bindViewTap() {
      wx.navigateTo({
        url: '/pages/recognintion/recognition'
      })
    },

    // 跳转到用户页面
    bindViewTapuser(){
      wx.navigateTo({
        url: '/pages/user/user',
      })
    },

    // 跳转到收藏页面
    bindViewTapcollect(){
      wx.navigateTo({
        url: '/pages/collect/collect',
      })
    }
  }
})