// pages/recognintion/recognition.js

const appInstance = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    // 用来承载图片路径
    imgPath: "",
    name: "植物病名称",
    xiangshidu: "90%",
    show: true
  },

  /**
   * 获取图片然后显示在页面上
   */
  open() {
    var that = this;
    wx.chooseMedia({
      count: 1,
      mediaType: ['image'],
      sourceType: ['album', 'camera'],
      camera: 'back',
      success(res) {
        console.log(res.tempFiles[0].tempFilePath)
        wx.saveFile({
          tempFilePath: res.tempFiles[0].tempFilePath, // 临时文件路径
          success(res) {
            var savedFilePath = res.savedFilePath; // 保存后的文件路径
            // 在这里进行后续操作，比如显示图片、上传文件等
            console.log(savedFilePath)
            that.setData({
              imgPath: savedFilePath
            })
          },
          fail(e){
            console.log(e)
          }
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
    if(appInstance.globalData.userInfo != "" && appInstance.globalData.image != "" && appInstance.globalData.name != ""){
      // 开始识别
      console.log("开始识别")
      // 显示其他按钮
      this.setData({
        show: !show
      })
    }
    else{
      wx.navigateTo({
        url: '/pages/user-perfect/user-perfect'
      })
    }
  },

  // 弹出收藏对话框
  addsouchang() {
    wx.showModal({
      title: '请输入收藏名',
      content: '',
      editable: true,
      success(res) {
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
  backHome() {
    var show = this.data.show
    this.setData({
      show: !show
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