// pages/Component/Component_collect/Component_collect.js
Component({
  properties: {
  },
  data: {
    // 这里是一些组件内部数据
    someData: {},
    show:true,
    showTrue:false,
    inputValue:"",
    // 全部的数据值
    img_name_time:[
      {img:"",name:"收藏名1",time:"2023-10-1"},
      {img:"",name:"收藏名1",time:"2023-10-1"},
      {img:"",name:"收藏名1",time:"2023-10-1"},
      {img:"",name:"收藏名9",time:"2023-10-1"},
      {img:"",name:"收藏名8",time:"2023-10-1"},
      {img:"",name:"收藏名7",time:"2023-10-1"},
      {img:"",name:"收藏名6",time:"2023-10-1"},
      {img:"",name:"收藏名1",time:"2023-10-1"},
      {img:"",name:"收藏名1",time:"2023-10-1"},
      {img:"",name:"收藏名51",time:"2023-10-1"},
      {img:"",name:"收藏名1",time:"2023-10-1"},
      {img:"",name:"收藏41",time:"2023-10-1"},
      {img:"",name:"收藏名13",time:"2023-10-1"},
      {img:"",name:"收藏名2",time:"2023-10-1"}
  ],
  // 查找过后的数据值
  search_img_name_time:[]
  },
  methods: {
    // 实现搜索功能
    search: function(){
      // 读取搜索框里面的值，和全部值里面的病历名称
      var img_name_time = this.data.img_name_time
      var inputValue = this.data.inputValue
      var search_img_name_time = this.data.search_img_name_time
      //设置模块是否可以显示
      if(inputValue != ""){
        this.setData({
          show:false
        })
      }
      else{
        this.setData({
          show:true,
          showTrue:false
        })
      }
      // 遍历全部病历名称，与搜索框里面的值进行匹配
      img_name_time.forEach(element => {
        if(inputValue == element.name){
          // 设置数据 番茄病 this.setData()是一个异步
          this.setData({
            search_img_name_time: search_img_name_time.concat(element),
          })
        }
      });
      console.log(search_img_name_time)
      this.setData({
        showTrue:true
      })
    },
    
    bindKeyInput: function (e) {
      this.setData({
        inputValue: e.detail.value
      })
    },
  }
})
