// pages/collect/collect.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    // 这里是一些组件内部数据
    someData: {},
    show: true,
    showTrue: false,
    inputValue: "",
    // 全部的数据值
    img_name_time: [
      {name: "收藏名1",time: "2023-10-1"},
      {name: "收藏名1",time: "2023-10-1"},
      {name: "收藏名1",time: "2023-10-1"},
      {name: "收藏名9",time: "2023-10-1"},
      {name: "收藏名8",time: "2023-10-1"},
      {name: "收藏名7",time: "2023-10-1"},
      {name: "收藏名6",time: "2023-10-1"},
      {name: "收藏名1",time: "2023-10-1"},
      {name: "收藏名1",time: "2023-10-1"},
      {name: "收藏名51",time: "2023-10-1"},
      {name: "收藏名1",time: "2023-10-1"},
      {name: "收藏41",time: "2023-10-1"},
      {name: "收藏名13",time: "2023-10-1"},
      {name: "收藏名2",time: "2023-10-1"}
    ],
    // 查找过后的数据值
    search_img_name_time: []
  },

  // 输入框结果实施呈现
  bindKeyInput: function (e) {
    this.setData({
      inputValue: e.detail.value
    })
  },

  // 跳转到收藏呈现结果页面，并且传值
  collect_outcome() {
    wx.navigateTo({
      url: '/pages/collect_outcome/collect_outcome',
      // events: events,
      success: (result) => {
        // 通过eventChannel向被打开页面传送数据
        result.eventChannel.emit('acceptDataFromOpenerPage', {
          name: this.data.img_name_time[0].name,
          time: this.data.img_name_time[0].time
        })
      }
    })
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