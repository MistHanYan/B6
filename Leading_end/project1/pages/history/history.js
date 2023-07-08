// pages/collect/collect.js

const appInstance = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
     // 这里是一些组件内部数据
     someData: {},
     show:true,
     showTrue:false,
     inputValue:"",
     // 全部的数据值
     img_name_time:[
       {name:"历史记录1",time:"2023-10-1"},
       {name:"历史记录2",time:"2023-10-1"},
       {name:"历史记录3",time:"2023-10-1"},
       {name:"历史记录4",time:"2023-10-1"},
       {name:"历史记录5",time:"2023-10-1"},
       {name:"历史记录1",time:"2023-10-1"},
       {name:"历史记录5",time:"2023-10-1"},
       {name:"历史记录5",time:"2023-10-1"},
       {name:"历史记录5",time:"2023-10-1"},
       {name:"历史记录5",time:"2023-10-1"},
       {name:"历史记录5",time:"2023-10-1"},
       {name:"历史记录5",time:"2023-10-1"},
       {name:"历史记录5",time:"2023-10-1"},
       {name:"历史记录5",time:"2023-10-1"}
   ],
   // 查找过后的数据值
   search_img_name_time:[],
    id:"",
    image:"",
    name:""
  },

  // 输入框结果实施呈现
  bindKeyInput: function (e) {
    this.setData({
      inputValue: e.detail.value
    })
  },

  history_outcome(event){
    var index = event.currentTarget.dataset.index;
    var clickedElement = this.data.img_name_time[index];
    console.log('用户点击了元素：', clickedElement.name,clickedElement.time);
    wx.navigateTo({
      url: '/pages/history_outcome/history_outcome',
      // events: events,
      success: (result) => {
        // 通过eventChannel向被打开页面传送数据
        result.eventChannel.emit('acceptDataFromOpenerPage', {
          name: clickedElement.name,
          time: clickedElement.time
        })
      }
    })
  },

    // 跳转到用户页面
    user(){
      wx.navigateTo({
        url: '/pages/user/user',
      })
    },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    if(appInstance.globalData.userInfo != "" && appInstance.globalData.image != "" && appInstance.globalData.name != ""){
      console.log("用户id，头像，图片都有")
    }
    else{
      wx.navigateTo({
        url: '/pages/user-perfect/user-perfect'
      })
    }
    this.setData({
      id:111,
      image: appInstance.globalData.image,
      name: appInstance.globalData.name
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