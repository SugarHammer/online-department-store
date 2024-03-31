<template>
  <div>
    <div class="commodityBox">
      <div class="inlineBlock" >
        <img :src="commodity.pthumbnail">
      </div>
      <div class="inlineBlock" style="width: 480px;">

        <div>
          <p>{{commodity.gname}}</p>
        </div>
        <div style="margin-top: 40px;">
          <span class="oldPrice"><span class="inlineBlock font" style="width: 80px;">原价</span>{{commodity.gprice}}</span>
          <br>
          <span class="newPrice"><span class="inlineBlock" style="width: 80px; margin-top: 10px;">优惠价</span>{{commodity.gprice}}</span>
        </div>
        <div style="margin-top: 80px;">
          <span class="inlineBlock font" style="width: 80px;">数量</span>
          <el-input-number  v-model="count"  size="small"  :min="1"  ></el-input-number>
        </div>
        <div class="addButton">
          <a href="javascript:void(0);" @click="addToShopcart">
            <span><i class="el-icon-shopping-cart-2"></i> 加入购物车</span>
          </a>
        </div>
      </div>
    </div>

    


    <el-dialog
        title="评论"
        :visible.sync="dialogVisible"
        width="50%"
        @close="created"
    >
      <el-form ref="addFormRef" :model="addForm" :rules="addFormRules" label-width="100px">
        <!-- <el-form-item label="用户名" prop="name">
          <el-input v-model="addForm.name"></el-input>
        </el-form-item> -->
        <el-form-item label="评论" prop="content">
          <el-input v-model="addForm.content"></el-input>
        </el-form-item>

        <el-form-item label="评论时间" prop="time">
          <el-date-picker v-model="addForm.time" value-format="yyyy-MM-dd HH:mm:ss"  @change="getNowTime" type="datetime"  placeholder="选择日期和时间" disabled></el-date-picker>
        </el-form-item>
      </el-form>

      <span slot="footer" class="dialog-footer">
            <el-button @click="dialogVisible = false">取消</el-button>
            <el-button type="primary" @click="addComments">确定</el-button>
          </span>
    </el-dialog>

    <div class="commentBox">
      <div class="commentCount font">
            <a :href="'' + commodity.storeUrl"><b>商家: {{commodity.storeName}}</b></a>     
      </div>
    </div>


    <div class="commentBox">
      <div class="commentCount font">
        累计评论 <b>{{comments.length}}</b>
      </div>
      <span style="margin-left: 700px;">
        <el-button icon="el-icon-circle-plus" type="primary" @click="dialogVisible=true">评论</el-button>
      </span>
      <el-divider></el-divider>
      <div v-for="(item, index) in comments" :key="index">

        <div>
          <img :src="'https://images.weserv.nl/?url='+item.picUrl" style="height: 40px;border-radius: 50%"/>

          <span class="font" style="font-size: 18px; color: #3F3F3F;">{{item.nickName}}：</span>
        </div>
        <div>
          <p class="font" style="text-indent: 2em; font-size: 16px; color: #3F3F3F;">{{item.content}}</p>
        </div>
        <div>
          <p class="font" style="text-indent: 2em; font-size: 16px; color: #3F3F3F;">{{item.time}}</p>
        </div>
        <el-divider></el-divider>
      </div>
    </div>
  </div>
</template>

<script>

export default {

  data() {
    return {
      commodity: {

      },
      comments: [

      ],
      count:0,
      dialogVisible:false,
      addForm: {
        // name: '',
        content: '',
        time:'',

      },
      pic:'www.baidu.com',
      //添加表单的验证规则对象
      addFormRules: {

        // content: [
        // { required: true,message: '请输入评论内容',trigger: 'blur'},
        // ]
      }
    }
  },
  methods: {

    getNowTime () {
      var date = new Date();
      var year = date.getFullYear();
      var month = (date.getMonth() + 1) < 10?'0'+(date.getMonth() + 1):(date.getMonth() + 1);
      var day = date.getDate()<10?'0'+date.getDate():date.getDate();
      var hour = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
      var minute = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
      var second = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();
      this.addForm.time = year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second;
      },

    //添加评论
    addComments(){
      let user = JSON.parse(localStorage.getItem('user'));
      console.log(user);
      //let pic = JSON.parse(localStorage.getItem('user')).picUrl;
      let pic = user.picUrl;
      let name =user.username;
      let id = user.id;
      //alert("当前登录用户为"+user);
      // alert("当前登录用户pic为"+pic);
      // alert("当前登录用户name为"+name);
      this.dialogVisible=false;
      //  let pic = JSON.parse(localStorage.getItem('user')).picUrl.data;
      // let name = JSON.parse(localStorage.getItem('user')).username.data;
      // console.log("当前登录用户pic为"+pic);
      // console.log("当前登录用户name为"+name);
      this.$axios.get('/comments/addComments', {
        params: {
          id:this.$route.query.commodityId,
          userId:id,
          name:name,
          pic:pic,
          content:this.addForm.content,
          time:this.addForm.time
        }
      })
          .then(function (response) {
            console.log(response);
            alert('评论成功！');
            location. reload();
          })
          .catch(function (error) {
            console.log(error);
          });
    },

    // 加入购物车
    addToShopcart() {
      console.log("当前登录用户id为"+JSON.parse(localStorage.getItem('user')).id);
      console.log("当前商品id为"+this.$route.query.commodityId);
      console.log("加入的商品数量为: "+this.count);
      console.log("当前商品总价格为："+this.commodity.gprice*this.count);
      this.$axios.get('/cart/toShoppingCart', {
        params: {
          goodsId: this.$route.query.commodityId,
          num: this.count,
          userId: JSON.parse(localStorage.getItem('user')).id,
          sumprice:this.commodity.gprice*this.count
        }
      })
          .then(function (response) {
            console.log(response);
            alert('加入购物车成功！');
          })
          .catch(function (error) {
            console.log(error);
          });
    }
  },
  created() {
    var that = this;
    this.getNowTime();
    console.log("当前商品ID为："+this.$route.query.commodityId)
    // 获取商品信息
    this.$axios.get('/goods/selectGoodsById', {
      params: {
        id: this.$route.query.commodityId
      }
    }).then(function (response) {

          console.log(response.data.data);
          let data=response.data.data;
          that.commodity.pthumbnail = data.pthumbnail;
          that.commodity.gname=data.gname;
          that.commodity.gprice=data.gprice;
          that.count=0;
        })
        .catch(function (error) {
          console.log(error);
        });

    // 获取商家信息
    this.$axios.get('/goods/selectGoodsByIdWithStore', {
      params: {
        id: this.$route.query.commodityId
      }
    }).then(function (response) {
          console.log("商家信息为:");
          console.log(response.data.data);
          let data=response.data.data;
          that.commodity.storeName = data.name;
          that.commodity.storeUrl = data.url;
          that.count=0;
        })
        .catch(function (error) {
          console.log(error);
        });

    // 获取商品评论
    this.$axios.get('/comments/selectCommentsbyGoodsId', {
      params: {
        id: this.$route.query.commodityId
      }
    })
        .then(function (response) {
          console.log(response.data.data);
          let data = response.data.data;
          // that.comments.nickName=data.nikeName;
          // that.comments.content=data.content;
          // console.log(that.comments.nickName);
          // console.log(that.comments.content);
          that.comments = data;
          console.log("评论列表为: ");
          console.log(that.comments);
          //that.count;

        })
        .catch(function (error) {
          console.log(error);
        });
  },
}
</script>

<style scoped>
.inlineBlock {
  display: inline-block;
}

.commodityBox {
  width: 800px;
  background-color: white;
  padding: 20px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);
  margin: 0 auto;
  margin-bottom: 20px;
}

.commodityBox img {
  width: 300px;
  height: 300px;
  object-fit: contain;
  vertical-align: text-bottom;
  margin-right: 20px;
}

.commodityBox p {
  font: 12px/1.5 tahoma,arial,'Hiragino Sans GB','\5b8b\4f53',sans-serif;
  font-size: 17px;
  font-weight: 700;
  line-height: 21px;
  color: #3C3C3C;
  margin-top: 0;
}

.newPrice {
  font-size: 22px;
  color: #f40;
}

.oldPrice {
  text-decoration: line-through;
}

.addButton {
  width: 180px;
  height: 40px;
  line-height: 40px;
  font-size: 16px;
  text-align: center;
  margin-left: 250px;
  margin-top: 30px;
}

.addButton a {
  display: inline-block;
  width: 180px;
  height: 40px;
  text-decoration: none;
  background-color: #f40;
  color: white;
  cursor: pointer;
}

.font {
  color: #6C6C6C;
  font: 14px/1.5 tahoma,arial,'Hiragino Sans GB','\5b8b\4f53',sans-serif;
}

.commentBox {
  width: 800px;
  background-color: white;
  padding: 20px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);
  margin: 0 auto;
  padding-top: 10px;
}

.commentCount {
  height: 40px;
  line-height: 40px;
  color: #f40;
  font-size: 16px;
  margin-bottom: -20px;
}
</style>
