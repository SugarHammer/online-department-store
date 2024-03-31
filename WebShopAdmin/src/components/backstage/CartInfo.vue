<template>
  <div id="back-stage-goods-info">
    <empty-data v-if="cartInfo == null || cartInfo.length === 0"/>
    <div id="goods-plan-info" v-else>
      <table-row-count :count="total"></table-row-count>
      <!-- 搜索与添加区域 -->
      <el-row :gutter="20">
        <el-col :span="8">
          <el-input placeholder="请输入内容" v-model="queryInfo.keyword" clearable @clear="loadGoodsInfo()">
            <el-button slot="append" icon="el-icon-search" @click="getGoodsByKeyword()"></el-button>
          </el-input>
        </el-col>
        <el-col :span="4">
          <el-button type="primary" @click="dialogVisible=true">添加购物车</el-button>
        </el-col>
      </el-row>

      <el-table
          :data="cartInfo"
          stripe
          style="width: 100%">
        <el-table-column
            prop="cartId"
            label="购物车ID"
        >
        </el-table-column>
        <el-table-column
            prop="userId"
            label="所属用户ID"
        >
        </el-table-column>
        <el-table-column
            prop="goodsId"
            label="对应商品ID"
        >
        </el-table-column>
        <el-table-column
            prop="num"
            label="对应商品数量">
        </el-table-column>
        <el-table-column
            prop="sumprice"
            label="总价">
        </el-table-column>

        <el-table-column label="操作">
          <template slot-scope="scope">
            <!-- 修改按钮 -->
            <el-button type="primary" icon="el-icon-edit" @click="showEditDialog(scope.row.cartId)"></el-button>
            <!-- 删除按钮 -->
            <el-button type="danger" icon="el-icon-delete" @click="deleteCart(scope.row.cartId)"></el-button>
          </template>
        </el-table-column>

      </el-table>


      <div class="page-bar" >
        <el-pagination
            layout="prev, pager, next, jumper"
            @current-change="changePage"
            :page-size="50"
            :current-page.sync="currentPage"
            hide-on-single-page
            :total="total">
        </el-pagination>
      </div>
    </div>

    <!-- 添加商品的对话框 -->
    <el-dialog
        title="添加购物车"
        :visible.sync="dialogVisible"
        width="50%"
        @close="loadGoodsInfo">
      <!-- 内容的主体区域 -->
      <el-form ref="addFormRef" :model="addForm" :rules="addFormRules" label-width="100px">
        <el-form-item label="用户ID" prop="userId">
          <el-input v-model="addForm.userId"></el-input>
        </el-form-item>
        <el-form-item label="商品ID" prop="goodsId">
          <el-input v-model="addForm.goodsId"></el-input>
        </el-form-item>
        <el-form-item label="商品数量" prop="num">
          <el-input v-model="addForm.num"></el-input>
        </el-form-item>
      </el-form>
      <!-- 底部区域 -->
      <span slot="footer" class="dialog-footer">
                <el-button @click="dialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="addCart">确 定</el-button>
            </span>
    </el-dialog>

    <!-- 修改商品的对话框 -->
    <el-dialog
        title="修改商品"
        :visible.sync="editDialogVisible"
        width="50%"
        @close="editDialogClosed">
      <el-form :model="editForm" :rules="editFormRules" ref="editFormRef" label-width="100px">
        <el-form-item label="购物车ID" prop="cartId">
          <el-input v-model="editForm.cartId" disabled></el-input>
        </el-form-item>
        <el-form-item label="用户ID" prop="userId">
          <el-input v-model="editForm.userId"></el-input>
        </el-form-item>
        <el-form-item label="商品ID" prop="goodsId">
          <el-input v-model="editForm.goodsId"></el-input>
        </el-form-item>
        <el-form-item label="商品数量" prop="num">
          <el-input v-model="editForm.num"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
            <el-button @click="editDialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="editCartInfo">确 定</el-button>
          </span>
    </el-dialog>


  </div>
</template>

<script>
import {request} from "../../network/request";
import EmptyData from "./EmptyData"
import TableRowCount from './TableRowCount'
export default {
  name: "CartInfo",
  data() {
    return {
      // 获取商品列表的参数对象
      queryInfo: {
        // 查询参数
        keyword: '',
        // 当前的页码数
        pagenum: 1,
        // 每页显示多少条数据
        pagesize: 2
      },
      // 获取的商品列表
      cartInfo: null,

      // 总数
      total: 0,

      currentPage: 1,
      loading: null,
      dialogVisible: false,
      // 添加商品的表单数据
      addForm: {
        userId: '',
        goodsId: '',
        num: ''
      },
      // 添加表单的验证规则对象
      addFormRules: {
        userId: [
          { required: true, message: '请输入用户ID', trigger: 'blur' },
          { min: 1, max: 100, message: '长度在 1 到 100 个字符', trigger: 'blur' }
        ],
        goodsId: [
          { required: true, message: '请输入商品ID', trigger: 'blur' },
        ],
        num: [
          { required: true, message: '请输入商品数量', trigger: 'blur' },
          { min: 1, max: 100, message: '长度在 1 到 100 个字符', trigger: 'blur' }
        ]
      },

      // 控制修改商品对话框的显示与隐藏，默认为隐藏
      editDialogVisible: false,
      // 查询到的商品信息对象
      editForm: {
        cartId:'',
        userId: '',
        goodsId: '',
        num: ''
      },
      editFormRules: {
        userId: [
          {required: true, message: '请输入用户ID', trigger: 'blur'},
        ],
        goodsId: [
          {required: true, message: '请输入商品ID', trigger: 'blur'},
        ],
        num: [
          {required: true, message: '请输入商品数量', trigger: 'blur'},
        ]
      }
    }
  },


  methods: {

    getCartByKeyword(){
      this.setLoading();
      request({
        url: 'cart/queryCartById',
        params: {
          cartId:this.queryInfo.keyword
        }
      }) .then( res => {
        if (res.code === '000'){
        console.log("res.data==="+res.data);
          this.cartInfo = res.data;
          console.log("关键字为: ");
          console.log(this.queryInfo.keyword);
          console.log("查询结果为: ");
          console.log(res.data);

          this.total = res.data.length;
        } else {
          this.$message.error(res.message)
        }
      }).catch( err => {
        this.$message.error('系统错误')
      }).finally( () => {this.setUnloading();})
    },

    changePage(){
      this.loadgoodsInfo();
    },
    //新增商品
    addCart(){
      /**
       *
       * 添加商品————总价由num*price算出
       * 此时price需要通过商品ID查询得知
       * 因此首先selectGoodsById 随后addCart
       *
       */
      let price;
      request({
        url: 'goods/selectGoodsById',
        params: {
          id:this.addForm.goodsId
        }
      }) .then( res => {
        if (res.code === '000'){
          price=res.data.gprice*this.addForm.num;
          console.log('查询成功!商品总价格为：'+res.data.gprice*this.addForm.num);
          this.loadGoodsInfo();
          this.dialogVisible = false;
          /**
           * 嵌套在selectGoodsById中，防止sumprice为空
           */
          request({
            url: 'cart/addCart',
            params: {
              userId:this.addForm.userId,
              goodsId:this.addForm.goodsId,
              num:this.addForm.num,
              sumprice:price
            }
          }) .then( res => {
            if (res.code === '000'){
              console.log('添加成功!');
              this.$message.success('添加成功!');
              this.loadGoodsInfo();
              this.dialogVisible = false;
            } else {
              this.$message.error(res.message)
            }
          }).catch( err => {
            this.$message.error('系统错误')
          })

        } else {
          this.$message.error(res.message)
        }
      }).catch( err => {
        this.$message.error('系统错误')
      })
    },

    // 展示编辑商品的对话框
    showEditDialog(cartId) {

      request({
        url: 'cart/selectCartById',
        params: {
          cartId:cartId
        }
      }) .then( res => {
        if (res.code === '000'){
          console.log('查询购物车的id为: ');
          console.log(cartId);
          console.log('查询购物车成功!');
          this.editForm.cartId = res.data.cartId
          this.editForm.userId = res.data.userId
          this.editForm.goodsId = res.data.goodsId
          this.editForm.num = res.data.num
          this.editDialogVisible = true
        } else {
          this.$message.error(res.message)
        }
      }).catch( err => {
        this.$message.error('系统错误')
      })
    },
    // 监听修改商品对话框的关闭
    editDialogClosed () {
      this.$refs.editFormRef.resetFields()
    },
    // 修改商品信息并且提交
    editCartInfo () {
      this.$refs.editFormRef.validate(async valid => {

        console.log(valid)
        if (!valid) return
        let price;

        request({
          url: 'goods/selectGoodsById',
          params: {
            id:this.editForm.goodsId
          }
        }) .then( res => {
          if (res.code === '000'){
            price=res.data.gprice*this.editForm.num;
            console.log('查询成功!商品总价格为：'+price);
            // 发起修改商品信息的数据请求
            request({
              url: 'cart/updateCart',
              params: {
                cartId:this.editForm.cartId,
                userId:this.editForm.userId,
                goodsId:this.editForm.goodsId,
                num:this.editForm.num,
                sumprice:price
              }
            }) .then( res => {
              if (res.code === '000'){
                console.log('修改成功!');
                // 提示修改成功
                this.$message.success('更新购物车信息成功！');
                // 更新数据列表
                this.loadGoodsInfo();
                // 关闭对话框
                this.editDialogVisible = false;

              } else {
                this.$message.error(res.message)
              }
            }).catch( err => {
              this.$message.error('系统错误')
            })

            this.dialogVisible = false;
          } else {
            this.$message.error(res.message)
          }
        }).catch( err => {
          this.$message.error('系统错误')
        })

      })
    },

    // 删除目标商品
    deleteCart(cartId) {
      console.log('删除的货物的id为: ');
      console.log(cartId);
      request({
        url: 'cart/deleteCart',
        params: {
          cartId:cartId
        }
      }) .then( res => {
        if (res.code === '000'){
          console.log('删除购物车成功!');
          this.$message.success('删除目标购物车成功！')
          this.loadGoodsInfo();
        } else {
          this.$message.error(res.message)
        }
      }).catch( err => {
        this.$message.error('系统错误')
      })

    },

    //搜索商品信息
    getGoodsByKeyword(){
      this.setLoading();
      request({
        url: 'cart/queryCartById',
        params: {
          cartId:this.queryInfo.keyword
        }
      }) .then( res => {
        if (res.code === '000'){
          this.cartInfo = res.data;
          console.log("关键字为: ");
          console.log(this.queryInfo.keyword);
          console.log("查询结果为: ");
          console.log(res.data);

          //this.cartInfo.sumprice = parseFloat(this.cartInfo.sumprice).toFixed(2)
          this.total = res.data.length;
          console.log("查询长度为: ");
          console.log(this.total);
        } else {
          this.$message.error(res.message)
        }
      }).catch( err => {
        this.$message.error('系统错误')
      }).finally( () => {this.setUnloading();})
    },
    //获取所有商品信息
    loadGoodsInfo(){
      this.setLoading();
      request({
        url: 'cart/selectAllCart',
        params: {
          currentPage: this.currentPage
        }
      }) .then( res => {
        if (res.code === '000'){
          this.cartInfo = res.data;
          //转换price为float类型
          this.cartInfo.forEach(element => {
            element.gprice = parseFloat(element.gprice).toFixed(2)
          });



          console.log("获取所有购物车数据为: ");
          console.log(this.cartInfo);
          this.total = res.data.length;
        } else {
          this.$message.error(res.message)
        }
      }).catch( err => {
        this.$message.error('系统错误')
      }).finally( () => {this.setUnloading();})
    },
    setLoading(){
      this.loading = this.$loading({
        lock: true,
        text: 'Loading',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      });
    },
    setUnloading(){
      this.loading.close();
    }
  },
  created(){
    this.loadGoodsInfo();
  },
  components: {
    EmptyData,
    TableRowCount
  }
}
</script>

<style scoped lang="less">

.page-bar{
  width: 500px;
  margin: 30px auto;
}
.invite{
  float: right;
  padding: auto;
  margin: auto;
  border: auto;
}
.text{
  float: left;
  margin: 20px;
  margin-top: 10px;
  font-size: 20px;
}

</style>