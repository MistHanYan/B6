// pages/collect/collect.js
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
   search_img_name_time:[]    
  },

  // 输入框结果实施呈现
  bindKeyInput: function (e) {
    this.setData({
      inputValue: e.detail.value
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