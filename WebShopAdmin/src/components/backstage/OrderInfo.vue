<template>
    <div id="back-stage-order-info">
        <empty-data v-if="orderInfo == null || orderInfo.length === 0"/>
            <div id="order-plan-info" v-else>
            <table-row-count :count="total"></table-row-count>
            <!-- 搜索与添加区域 -->
            <el-row :gutter="20">
                <el-col :span="8">
                    <el-input placeholder="请输入内容" v-model="queryInfo.keyword" clearable @clear="loadorderInfo()">
                        <el-button slot="append" icon="el-icon-search" @click="getorderByKeyword()"></el-button>
                    </el-input>
                </el-col>
                <el-col :span="4">
                    <el-button type="primary" @click="dialogVisible=true">添加订单</el-button>
                </el-col>
            </el-row>

            <el-table
                    :data="orderInfo"
                    stripe
                    style="width: 100%">
                <el-table-column
                        prop="orderId"
                        label="订单id"
                        width="110"
                        align="center"
                >
                </el-table-column>
                <el-table-column
                        prop="userId"
                        label="用户id"
                >
                </el-table-column>
                <el-table-column
                        prop="orderTime"
                        label="订单时间"
                >
                </el-table-column>
                <el-table-column
                        prop="status"
                        label="订单状态">
                </el-table-column>
                <el-table-column
                        prop="amount"
                        label="订单总价">
                </el-table-column>  

                <el-table-column
                        prop="orderName"
                        label="商品详情">
                </el-table-column>  

                <el-table-column
                        prop="cartList"
                        label="购物车列表">
                </el-table-column>  

                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <!-- 修改按钮 -->
                        <el-button type="primary" icon="el-icon-edit" @click="showEditDialog(scope.row.orderId)"></el-button>
                        <!-- 删除按钮 -->
                        <el-button type="danger" icon="el-icon-delete" @click="deleteorder(scope.row.orderId)"></el-button>
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

        <!-- 添加订单的对话框 -->
        <el-dialog
            title="添加订单"
            :visible.sync="dialogVisible"
            width="50%"
            @close="loadorderInfo">
            <!-- 内容的主体区域 -->
            <el-form ref="addFormRef" :model="addForm" :rules="addFormRules" label-width="100px">
                <el-form-item label="用户id" prop="userId">
                    <el-input v-model="addForm.userId"></el-input>
                </el-form-item>
                <el-form-item label="商品详情" prop="orderName">
                    <el-input v-model="addForm.orderName"></el-input>
                </el-form-item>
                <el-form-item label="订单时间" prop="orderTime">
                    <el-date-picker v-model="addForm.orderTime" value-format="yyyy-MM-dd HH:mm:ss" type="datetime" placeholder="选择日期和时间"></el-date-picker>
                </el-form-item>
                <el-form-item label="订单状态" prop="status">
                    <el-input v-model="addForm.status"></el-input>
                </el-form-item>
                <el-form-item label="订单总价" prop="amount">
                    <el-input v-model="addForm.amount"></el-input>
                </el-form-item>
                <el-form-item label="购物车列表" prop="cartList">
                    <el-input v-model="addForm.cartList"></el-input>
                </el-form-item>
            </el-form>
            <!-- 底部区域 -->
            <span slot="footer" class="dialog-footer">
                <el-button @click="dialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="addOrder">确 定</el-button>
            </span>
        </el-dialog>

        <!-- 修改订单的对话框 -->
        <el-dialog
          title="修改订单"
          :visible.sync="editDialogVisible"
          width="50%"
          @close="editDialogClosed">
          <el-form :model="editForm" :rules="editFormRules" ref="editFormRef" label-width="100px">
            <el-form-item label="订单id" prop="orderId">
              <el-input v-model="editForm.orderId" disabled></el-input>
            </el-form-item>
            <el-form-item label="用户id" prop="userId">
              <el-input v-model="editForm.userId"></el-input>
            </el-form-item>
            <el-form-item label="订单时间" prop="orderTime">
              <el-date-picker v-model="editForm.orderTime" value-format="yyyy-MM-dd HH:mm:ss" type="datetime" placeholder="选择日期和时间"></el-date-picker>
            </el-form-item>
            <el-form-item label="商品详情" prop="orderName">
              <el-input v-model="editForm.orderName"></el-input>
            </el-form-item>
            <el-form-item label="订单状态" prop="status">
              <el-input v-model="editForm.status"></el-input>
            </el-form-item>
            <el-form-item label="订单总价" prop="amount">
              <el-input v-model="editForm.amount"></el-input>
            </el-form-item>
            <el-form-item label="购物车列表" prop="cartList">
              <el-input v-model="editForm.cartList"></el-input>
            </el-form-item>
          </el-form>
          <span slot="footer" class="dialog-footer">
            <el-button @click="editDialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="editorderInfo">确 定</el-button>
          </span>
        </el-dialog>


    </div>
</template>

<script>
    import {request} from "../../network/request";
    import EmptyData from "./EmptyData"
    import TableRowCount from './TableRowCount'
    export default {
        name: "orderInfo",
        data() {
            return {
                // 获取订单列表的参数对象
                queryInfo: {
                    // 查询参数
                    query: '',
                    //搜索订单关键词
                    keyword: '',
                    // 当前的页码数
                    pagenum: 1,
                    // 每页显示多少条数据
                    pagesize: 2
                },
                // 获取的订单列表
                orderInfo: null,
                
                // 总数
                total: 0,
                
                currentPage: 1,
                loading: null,
                dialogVisible: false,
                // 添加订单的表单数据
                addForm: {
                    userId: '',
                    orderTime: '',
                    orderName: '',
                    status: '',
                    amount: '',
                    cartList:''
                },
                // 添加表单的验证规则对象
                addFormRules: {
                    userId: [
                    { required: true, message: '请输入用户Id', trigger: 'blur' },
                    ],
                    orderTime: [
                    { required: true, message: '请输入订单时间', trigger: 'blur' },
                    ],
                    status: [
                    { required: true, message: '请输入订单状态', trigger: 'blur' },
                    ],
                    orderName: [
                    { required: true, message: '请输入商品', trigger: 'blur' },
                    ],
                    amount: [
                    { required: true, message: '请输入订单总价', trigger: 'blur' },
                    ],
                    cartList: [
                    { required: true, message: '请输入购物车列表', trigger: 'blur' },
                    ]
                },    

                // 控制修改订单对话框的显示与隐藏，默认为隐藏
                editDialogVisible: false,
                // 查询到的订单信息对象
                editForm: {
                    orderId:'',
                    userId: '',
                    orderTime: '',
                    status: '',
                    orderName: '',
                    amount: '',
                    cartList: ''
                },
                // 修改表单的验证规则对象
                editFormRules: {
                    orderId: [
                    { required: false,  trigger: 'blur'},
                    ],
                    userId: [
                    { required: true, message: '请输入用户id', trigger: 'blur' },
                    { min: 1, max: 10, message: '长度在 1 到 10 个字符', trigger: 'blur' }
                    ],
                    orderTime: [
                    { required: true, message: '请输入订单时间', trigger: 'blur' },
                    ],
                    status: [
                    { required: true, message: '请输入订单状态', trigger: 'blur' },
                    ],
                    orderName: [
                    { required: true, message: '请输入商品', trigger: 'blur' },
                    ],
                    amount: [
                    { required: true, message: '请输入订单总价', trigger: 'blur' },
                    ],
                    cartList: [
                    { required: true, message: '请输入购物车列表', trigger: 'blur' },
                    ],
                }

            }
        },

        
        methods: {
           changePage(){
               this.loadorderInfo();
           },
           //新增订单
           addOrder(){
              console.log(this.orderTime);
              console.log(this.status);
    
              request({
                    url: 'order/addOrder',
                    params: {
                        userId:this.addForm.userId,
                        orderTime:this.addForm.orderTime,
                        status:this.addForm.status,
                        orderName:this.addForm.orderName,
                        amount:this.addForm.amount,
                        cartList:this.addForm.cartList
                    }
                }) .then( res => {
                    if (res.code === '000'){
                        console.log('添加成功!');
                        this.$message.success('添加成功!');
                        this.loadorderInfo();
                        this.dialogVisible = false;
                    } else {
                        this.$message.error(res.message)
                    }
                }).catch( err => {
                    this.$message.error('系统错误')
                })    
                 
           },

            // 展示编辑订单的对话框
            showEditDialog(id) {
                console.log("id:")
                console.log(id)
                request({
                    url: 'order/selectOrderByorderId',
                    params: {
                      id:id
                    }
                }) .then( res => {
                    if (res.code === '000'){
                        console.log('查询货物的id为: ');
                        console.log(id);
                        console.log('查询货物成功!');
                        this.editForm.orderId = res.data.orderId
                        this.editForm.userId = res.data.userId
                        this.editForm.orderTime = res.data.orderTime
                        this.editForm.status = res.data.status
                        this.editForm.orderName = res.data.status
                        this.editForm.amount = res.data.amount
                        this.editForm.cartList = res.data.cartList
                        this.editDialogVisible = true
                    } else {
                        this.$message.error(res.message)
                    }
                }).catch( err => {
                    this.$message.error('系统错误')
                })  
            },
            
            // 监听修改订单对话框的关闭
            editDialogClosed () {
                this.$refs.editFormRef.resetFields()
            },
            // 修改订单信息并且提交
            editorderInfo () {
                this.$refs.editFormRef.validate(async valid => {
                    console.log(valid)
                    if (!valid) return
                    // 发起修改订单信息的数据请求
                    request({
                        url: 'order/updateOrder',
                        params: {
                            orderId:this.editForm.orderId,
                            userId:this.editForm.userId,
                            orderTime:this.editForm.orderTime,
                            status:this.editForm.status,
                            orderName:this.editForm.orderName,
                            amount:this.editForm.amount,
                            cartList:this.editForm.cartList
                        }
                    }) .then( res => {
                        if (res.code === '000'){
                            console.log('修改成功!');

                            // 提示修改成功
                            this.$message.success('更新订单信息成功！');
                            // 更新数据列表
                            this.loadorderInfo();
                            // 关闭对话框
                            this.editDialogVisible = false;

                        } else {
                            this.$message.error(res.message)
                        }
                    }).catch( err => {
                        this.$message.error('系统错误')
                    }) 
                    
                })
            },

            // 删除目标订单
            deleteorder(id) {
                console.log('删除的货物的id为: ');
                console.log(id);
                request({
                    url: 'order/deleteOrderById',
                    params: {
                      id:id
                    }
                }) .then( res => {
                    if (res.code === '000'){   
                        console.log('删除货物成功!');
                        this.$message.success('删除目标订单成功！')
                        this.loadorderInfo();
                    } else {
                        this.$message.error(res.message)
                    }
                }).catch( err => {
                    this.$message.error('系统错误')
                })  
                
            },

            //搜索订单信息
            getorderByKeyword(){
               this.setLoading();
                request({
                    url: 'order/searchOrder',
                    params: {
                        keyword:this.queryInfo.keyword
                    }
                }) .then( res => {
                    if (res.code === '000'){
                        this.orderInfo = res.data;
                        console.log("关键字为: ");
                        console.log(this.queryInfo.oid);
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
            //获取所有订单信息
            loadorderInfo(){
               this.setLoading();
                request({
                    url: 'order/selectAllOrder',
                    params: {
                        currentPage: this.currentPage
                    }
                }) .then( res => {
                    if (res.code === '000'){
                        this.orderInfo = res.data;
                        //转换price为float类型
                        this.orderInfo.forEach(element => {
                            element.amount = parseFloat(element.amount).toFixed(2)
                        });

                        console.log("获取所有订单数据为: ");
                        console.log(this.orderInfo);
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
            this.loadorderInfo();
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