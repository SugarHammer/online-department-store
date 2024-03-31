<template>
  <div style="padding: 10px">
    <!--    功能区域-->
    <!--    <div style="margin: 10px 0">-->
    <!--      <el-button type="primary" @click="add" v-if="user.role === 1">新增</el-button>-->
    <!--    </div>-->
    <!--    搜索区域-->
    <!-- <div style="margin: 10px 0">
      <el-input v-model="search" placeholder="请输入订单编号" style="width: 20%" clearable></el-input>
      <el-button type="primary" style="margin-left: 5px" @click="load">查询</el-button>
    </div> -->
    <el-table
        v-loading="loading"
        :data="order"
        border
        stripe
        style="width: 100%"
    >

      <el-table-column
          width="160"
          prop="order_Id"
          label="订单编号">
      </el-table-column>
      <el-table-column
          width="165"
          prop="goods_name"
          label="订单名称">
      </el-table-column>

      <el-table-column
          prop="goods_OldPrice"
          label="总价">
      </el-table-column>
      <el-table-column
          prop="goods_OldPrice"
          label="实付款">
      </el-table-column>
      <el-table-column
          prop="discount"
          label="优惠">
      </el-table-column>
      <el-table-column
          prop="transportPrice"
          label="运费">
      </el-table-column>
      <el-table-column
          width="200"
          prop="order_Time"
          label="创建时间">
      </el-table-column>
      <el-table-column
          width="200"
          prop="order_Time"
          label="付款时间">
      </el-table-column>
      <el-table-column
          prop="username"
          label="购买人">
      </el-table-column>
      <el-table-column label="支付状态">
        <template #default="scope">
          <span v-if="scope.row.state === 0" style="color: orange">未支付</span>
          <span v-if="scope.row.state === 1" style="color: green">已支付</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="240">
        <template #default="scope">
          <el-popconfirm title="确定删除吗？">
            <template >
              <el-button size="mini" type="danger" @click="handleDelete(scope.row.order_Id)">删除</el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <div style="margin: 10px 0">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="currentPage"
          :page-sizes="[5, 10, 20]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>
  </div>
</template>

<script>

export default {
  name: 'Order',
  components: {

  },
  data() {
    return {
      user: {},
      loading: true,
      form: {},
      dialogVisible: false,
      search: '',
      currentPage: 1,
      pageSize: 10,
      total: 0,
      order: [

      ],
    }
  },
  created() {

    this.load()

  },
  methods: {
    load() {

      this.loading = true
      let  username;
      this.loading = true
      var user_id = JSON.parse(localStorage.getItem('user')).id;
      var that = this;
      this.$axios.get('/user/findByid?userId='+user_id, {
      })
          .then(function (response) {
            username = response.data.data.username;
          })
          .catch(function (error) {
            console.log(error);
          });

      let order_Src = "/order/selectOrderInfo?userId="+user_id;
      let src="/order/selectOrderByuserId?id="+user_id;
      this.$axios.get(src).then(function(response) {

        console.log("所有商品列表为: ");
        let data = response.data.data;
        console.log(data);
        console.log(data.length);
        for(let i=0;i<data.length;i++){
            let orderInfo = {};
            orderInfo.order_Id=data[i].orderId;
            orderInfo.order_Time=data[i].orderTime;
            orderInfo.goods_OldPrice = data[i].amount;
            orderInfo.goods_NewPrice = data[i].amount;
            orderInfo.goods_name = data[i].orderName;
            orderInfo.discount=0;
            orderInfo.transportPrice=0;
            orderInfo.state=data[i].status;
            orderInfo.username=username;
            that.order.push(orderInfo);
          // console.log("odList长度为: ");
          // console.log(data[i].odList.length);
          //
          // for(let j=0;j<data[i].odList.length;j++){
          //   orderInfo = {}
          //   console.log("j为: ");
          //   console.log(j);
          //   orderInfo.order_Id=data[i].orderId;
          //   orderInfo.order_Time=data[i].orderTime;
          //   orderInfo.goods_OldPrice = data[i].odList[j].goodsPrice;
          //   orderInfo.goods_NewPrice = data[i].odList[j].goodsPrice;
          //   orderInfo.goods_Id =data[i].odList[j].goodsId;
          //   orderInfo.goods_name = data[i].odList[j].goodsName;
          //   orderInfo.cartId=data[i].odList[j].cartId;
          //   orderInfo.num=data[i].odList[j].nums;
          //   //data[j].totalPrice=data[i].odList[j].dealPrice * data[i].odList[j].nums;
          //   orderInfo.checked = false;
          //   orderInfo.pthumbnail=data[i].odList[j].imgSrc;
          //   orderInfo.discount=0;
          //   orderInfo.transportPrice=0;
          //   orderInfo.state=0;
          //   orderInfo.name=username;
          //   that.order.push(orderInfo);
          //   console.log("订单详情为: ");
          //   console.log(orderInfo);
          // }

        }
        console.log(that.order);

      })
          .catch(function(error) {
            console.log(error);
          });
      this.loading = false
    },
    add() {
      this.dialogVisible = true
      this.form = {}
      if (this.$refs['upload']) {
        this.$refs['upload'].clearFiles()  // 清除历史文件列表
      }
    },
    save() {
      if (this.form.id) {  // 更新
        request.put("/order", this.form).then(res => {
          console.log(res)
          if (res.code === '0') {
            this.$message({
              type: "success",
              message: "更新成功"
            })
          } else {
            this.$message({
              type: "error",
              message: res.msg
            })
          }
          this.load() // 刷新表格的数据
          this.dialogVisible = false  // 关闭弹窗
        })
      }  else {  // 新增
        request.post("/order", this.form).then(res => {
          console.log(res)
          if (res.code === '0') {
            this.$message({
              type: "success",
              message: "新增成功"
            })
          } else {
            this.$message({
              type: "error",
              message: res.msg
            })
          }

          this.load() // 刷新表格的数据
          this.dialogVisible = false  // 关闭弹窗
        })
      }
    },
    handleEdit(row) {
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogVisible = true
      this.$nextTick(() => {
        if (this.$refs['upload']) {
          this.$refs['upload'].clearFiles()  // 清除历史文件列表
        }
      })

    },
    handleDelete(order_Id) {

      this.loading=true;
      let Delete_Src = "/order/deleteOrderById?id="+order_Id;
      this.$axios.get(Delete_Src, {
      })
          .then(function(response) {
            location.reload();
          })
          .catch(function(error) {
            console.log(error);
          });
      this.loading=false;

    },
    handleSizeChange(pageSize) {   // 改变当前每页的个数触发
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum) {  // 改变当前页码触发
      this.currentPage = pageNum
      this.load()
    }
  }
}
</script>