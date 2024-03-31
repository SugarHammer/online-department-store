<template>
    <div style="margin-bottom: 520px;">
        <div
        v-for="(item, index) in shopCart"
        :key="index" class="shopCartItem"
        :class="{shopCartItemHover: item.checked}"
        >

            <div class="inlineBlock" style="margin-right: 10px;">
                <input class="input1" type="checkbox" name="shopCart" :value="index" @click="checkedHandle(index)" >
            </div>
            <router-link :to="{name: 'introduce', query:{commodityId: item.goods_Id}}">
                <div class="inlineBlock">
                    <img :src="item.pthumbnail">
                </div>
                <div class="inlineBlock itemName">
                    <span>{{item.name}}</span>
                </div>
            </router-link>

            <div class="inlineBlock" style="vertical-align: top; margin-right: 50px;">
                <span class="oldPrice">￥{{item.goods_OldPrice}}</span>
                <br>
                <span class="newPrice">￥{{item.goods_NewPrice}}</span>
            </div>
            <div class="inlineBlock" style="margin-right: 50px;">
                <el-input-number v-model="item.num" size="small" :min="1"  @change="update(index)"></el-input-number>
            </div>
            <div class="inlineBlock" style="margin-right: 50px;">
                <span style="color: #F56C6C; font-size: 24px;">￥{{(item.goods_NewPrice * item.num).toFixed(2)}}</span>
            </div>
            <div class="inlineBlock" style="vertical-align: top;">
                <el-button type="text" @click="moveToCollect(index)">移入收藏夹</el-button>
                <br>
                <el-button type="text" style="color: #F56C6C;" @click="remove(index)">删除</el-button>
            </div>
        </div>
        <div class="bottomPay">
            <div class="inlineBlock" style="height: 60px; margin-right: 800px; font-size:14px; line-height: 60px;">
                <input class='input2' type="checkbox" name="checkAll" @change="checkAll"> 全选
            </div>
            <div class="inlineBlock" style="height: 60px; line-height: 60px;">
                合计：<span style="color: #f40; font-size: 24px;">￥{{totalPrice}}</span>
            </div>
            <div class="inlineBlock payButton">
                <a href="javascript:void(0);" :class="{activeButton: totalPrice > 0 ? true : false}" @click="pay">
                    <span>结 算</span>
                </a>
            </div>
        </div>

        <el-dialog
        title="支付"
        :visible.sync="dialogVisible"
        width="30%"
        >
            <div>
                <div v-for="(item, index) in checkedShopcart" :key="index" class="payItem">
                    <span>商品：{{item.name}}</span>
                    <br>
                    <span>数量：{{item.num}}</span>
                    <br>
                    <span>价格：<span style="color: #f40;">￥{{(item.goods_NewPrice * item.num).toFixed(2)}}</span></span>
                </div>
                <div style="float: right;">
                    <span style="font-size: 24px;">合计：<span style="color: #f40;">￥{{totalPrice}}</span></span>
                </div>
            </div>
            <div slot="footer">
                <el-button type="primary" @click="payConfirm" size="small">支 付</el-button>
                <el-button @click="dialogVisible = false" size="small">取 消</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>

export default {
    data() {
        return {
            goods : [],
            shopCart: [

            ],
            checkedAll: false ,
            dialogVisible: false
        }
    },
    methods: {
      //修改商品数量时同步修改数据库数据
      update(index){
        var user_id = JSON.parse(localStorage.getItem('user')).id;
        let card_id = this.shopCart[index].cartId;
        let num=this.shopCart[index].num;
        let sumprice=this.shopCart[index].goods_NewPrice*num;
        console.log("更改后总金额为："+sumprice);
        let src="/cart/updateCartprice?cartId="+card_id+"&num="+num+"&sumprice="+sumprice;
        console.log("请求路径为："+src);
        this.$axios.get(src, {
        })
             .then(function(response) {
            })
            .catch(function(error) {
              console.log(error);
            });
      },
        // 是否选中该商品
        checkedHandle(index) {
            this.shopCart[index].checked = !this.shopCart[index].checked;
            document.getElementsByName('shopCart')[index].checked = this.shopCart[index].checked;
        },
        // 移入收藏夹
        moveToCollect(index) {
            var that = this ;
            var id = JSON.parse(localStorage.getItem('user')).id;
            let ShopCart_Src = "/cart/listByUser?userId="+id;
            let collect_id = this.shopCart[index].goods_Id;
            this.$axios.get(ShopCart_Src, {
                params: {
                    ct_UserID: id,
                    goods_ID: this.shopCart[index].goods_ID
                }
            })
            .then(function(response) {
                //console.log(response.data);
                console.log("已经收藏货物为：");
                console.log(that.goods);
                for (let i = 0; i < response.data.length; i++) {
                    if(collect_id == response.data[i].goodsId){
                        let temp = {
                            goods_ID : collect_id,
                            goods_Name : response.data[i].name,
                            goods_Picture :"http://202.193.52.12:8080/"+response.data[i].thumbnail,
                            price :response.data[i].price
                        };
                        temp.goods_OldPrice = temp.price;
                        temp.goods_NewPrice = temp.price;
                        that.goods.push(temp);
                    }
                }
                console.log("收藏货物为：");
                console.log(that.goods);
                localStorage.setItem('collect_goods', JSON.stringify(that.goods));
                //console.log(response);
                alert('商品已移入收藏夹！');
            })
            .catch(function(error) {
                alert(" 商品已移入收藏夹！")
                console.log(error);
            });
        },
        // 从购物车中删除该商品
        remove(index) {
            var user_id = JSON.parse(localStorage.getItem('user')).id;
            console.log(user_id);
            let card_id = this.shopCart[index].cartId;
            console.log("该商品CartId为："+card_id);
            console.log("商品项："+card_id);
            let Delete_Src = "/cart/deleteGoods?userId="+user_id+"&cartId="+card_id;
            this.$axios.get(Delete_Src, {
                params: {
                    ct_UserID: user_id,
                    cartId: this.shopCart[index].cartId
                }
            })
            .then(function(response) {
                alert(" 删除成功! ");
                console.log(Delete_Src);
                console.log(response.data);
            })
            .catch(function(error) {
                console.log(error);
            });

            this.shopCart.splice(index, 1);
        },
        // 全选
        checkAll() {
            this.checkedAll = !this.checkedAll;
            var temp = document.getElementsByName('shopCart');
            if (this.checkedAll) {
                // 商品是否选中 激活边框
                for (let i = 0; i < this.shopCart.length; i++) {
                    this.shopCart[i].checked = true;
                }
                // checkbox是否选中
                for (let i = 0; i < temp.length; i++) {
                    temp[i].checked = true;
                }
            } else {
                for (let i = 0; i < this.shopCart.length; i++) {
                    this.shopCart[i].checked = false;
                }
                for (let i = 0; i < temp.length; i++) {
                    temp[i].checked = false;
                }
            }
        },
        // 点击结算按钮，弹出对话框
        pay() {
            this.dialogVisible = true;
        },
        // 确认支付提示框
        payConfirm() {

            var that = this;
            this.dialogVisible = false;
            let goodsIdList;

            var user_id = JSON.parse(localStorage.getItem('user')).id;
                console.log("用户id为："+user_id);
            let cart_str = "";
            console.log("开始！");
            for (var i = 0; i < this.shopCart.length; i++) {
                if (this.shopCart[i].checked) {
                  cart_str+=this.shopCart[i].cartId+",";
                }
            }
            let cart_list=cart_str.substring(0,cart_str.length-1);
            console.log("商品项列表："+cart_list);
          console.log(this.totalPrice);

          let paysrc = "/cart/order?goodsIdList="+cart_list+"&totalprice="+this.totalPrice+"&userId="+user_id;
          console.log("订单地址为:"+paysrc);
          this.$axios.get(paysrc, {
          })
              // .then(res=> {
              //   window.open(res.data)
              //   this.load();
              //
              // })

            // let CreateOrder_Src = "/cart/addCartOrder?userId="+user_id+"&cartIdList="+cart_list;
            // console.log("订单地址为:"+CreateOrder_Src);
            // this.$axios.get(CreateOrder_Src, {
            //     params: {
            //         ct_UserID: user_id,
            //     }
            // })
            .then(function(response) {
                console.log("订单数据为:"+response.data.data);
                alert(' 生成订单成功！');
                that.$router.push('/order');
            })
            .catch(function(error) {
                alert(' 生成订单失败！');
                console.log("错误:"+error);
                // 刷新页面
                //window.location.reload(true);
            });
        }
    },
    computed: {
        // 计算勾选的商品总价格
        totalPrice() {
            var result = 0;
            for (var i = 0; i < this.shopCart.length; i++) {
                if (this.shopCart[i].checked) {
                    result += this.shopCart[i].goods_NewPrice * this.shopCart[i].num;
                }
            }
            return result.toFixed(2);
        },
        // 返回选中的商品数组
        checkedShopcart() {
            var result = [];
            for (let i = 0; i < this.shopCart.length; i++) {
                if (this.shopCart[i].checked) {
                    result.push(this.shopCart[i]);
                }
            }
            return result;
        },

      load(){
        /**
         *
         * 根据用户ID获得购物车信息
         *
         * */
        var user_id = JSON.parse(localStorage.getItem('user')).id;
        var that = this;
        let ShopCart_Src = "/cart/queryGoodsByUserId?userId="+user_id;
        this.$axios.get(ShopCart_Src, {
          params: {
            ct_UserID: user_id
          }
        }).then(function(response) {

          console.log("所有商品列表为: ");
          let data = response.data.data;
          console.log(data);
          for(let i=0;i<data.length;i++){

            data[i].goods_OldPrice = data[i].gprice;
            data[i].goods_NewPrice = data[i].gprice;
            data[i].goods_Id =data[i].gid;
            data[i].checked = false;
            data[i].name=data[i].gname;
            data[i].num=data[i].num;
            data[i].cartId=data[i].cartId;
            console.log("购物车ID:  "+data[i].cartId);
            console.log(data[i].name);

          }
          that.shopCart = data;
          console.log(that.shopCart);
        })
            .catch(function(error) {
              console.log(error);
            });
      }
    },

created() {

  /**
   *
   * 根据用户ID获得购物车信息
   *
   * */
  var user_id = JSON.parse(localStorage.getItem('user')).id;
  var that = this;
  let ShopCart_Src = "/cart/queryGoodsByUserId?userId="+user_id;
  this.$axios.get(ShopCart_Src, {
    params: {
      ct_UserID: user_id
    }
  }).then(function(response) {

    console.log("所有商品列表为: ");
    let data = response.data.data;
    console.log(data);
    for(let i=0;i<data.length;i++){

      data[i].goods_OldPrice = data[i].gprice;
      data[i].goods_NewPrice = data[i].gprice;
      data[i].goods_Id =data[i].gid;
      data[i].checked = false;
      data[i].name=data[i].gname;
      data[i].num=data[i].num;
      data[i].cartId=data[i].cartId;
      console.log("购物车ID:  "+data[i].cartId);
      console.log(data[i].name);

    }
    that.shopCart = data;
    console.log(that.shopCart);
  })
      .catch(function(error) {
        console.log(error);
      });

    },
}

</script>

<style scoped>
.input1 {
    width:30px ;
    height: 30px;
    transform: translateY(100%);
}

.input2 {
    width: 20px ;
    height: 20px;
    transform: translateY(20%);
}

.payItem {
    width: 360px;
    margin: 0 auto;
    border: 1px solid #909399;
    border-radius: 4px;
    margin-bottom: 10px;
    padding: 20px;
}
.payItem:hover {
    border: solid 2px #F56C6C;
}

.shopCartItem {
    width: 1140px;
    background-color: white;
    padding: 20px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);
    margin: 0 auto;
    margin-bottom: 30px;
}

.shopCartItemHover{
    border: solid 2px #F56C6C;
    border-radius: 4px;
}

.shopCartItem img {
    width: 100px;
    height: 100px;
    object-fit: contain;
    vertical-align: top;
}

.inlineBlock {
    display: inline-block;
}

.itemName {
    width: 200px;
    font-size: 13px;
    margin-left: 20px;
    margin-right: 200px;
    vertical-align: text-top;
}

.newPrice {
    font-size: 22px;
}

.oldPrice {
    color: #909399;
    text-decoration: line-through;
}

.bottomPay {
    position: fixed;
    bottom: 0;
    left: 0;
    right: 0;
    padding-left: 20px;
    width: 1160px;
    height: 60px;
    margin: 0 auto;
    background-color: #e5e5e5;
    z-index: 10000;
}

.payButton {
    float: right;
    width: 120px;
    height: 60px;
    line-height: 60px;
    font-size: 20px;
    text-align: center;
}

.payButton a {
    display: inline-block;
    width: 120px;
    height: 60px;
    text-decoration: none;
    background: #B0B0B0;
    color: #fff;
    cursor: not-allowed;
}

.activeButton {
    background-color: #f40 !important;
    color: white !important;
    cursor: pointer !important;
}

</style>
