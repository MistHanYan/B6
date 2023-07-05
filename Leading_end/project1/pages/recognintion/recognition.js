// pages/recognintion/recognition.js

const appInstance  = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    // 用来承载图片路径
    imgPath: "",
    name:"植物病名称",
    xiangshidu:"90%",
    show:true
  },

  /**
   * 获取图片然后显示在页面上
   */
  open() {
    var that = this;
    wx.chooseMedia({
      count: 1,
      mediaType: ['image', 'video'],
      sourceType: ['album', 'camera'],
      maxDuration: 30,
      camera: 'back',
      success(res) {
        that.setData({
          imgPath: res.tempFiles[0].tempFilePath
        })
      },
    })
  },

  // 结果显示
  outcome() {
    var imgPath = this.data.imgPath
    var show = this.data.show
    console.log(imgPath)
    console.log(appInstance.globalData)
    this.setData({
      show : !show
    })
    // 跳转到识别页面，并传递image图片
    // wx.navigateTo({
    //   // url: '/pages/user/user',
    //   url: '/pages/outcome/outcome',
    //   success: function (res) {
    //     // 通过eventChannel向被打开页面传送数据
    //     res.eventChannel.emit('acceptDataFromOpenerPage', {
    //       imgPath: imgPath
    //     })
    //   }
    // })
  },

  // 弹出收藏对话框
  addsouchang(){
    wx.showModal({
      title: '请输入收藏名',
      content: '',
      editable:true,
      success (res) {
        if (res.confirm) {
          console.log('用户点击确定')
          console.log(res.content)
        } else if (res.cancel) {
          console.log('用户点击取消')
        }
      }
    })
  },

  // 切换显示按钮
  backHome(){
    var show = this.data.show
    this.setData({
      show : !show
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