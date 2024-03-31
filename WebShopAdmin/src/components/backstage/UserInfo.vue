<template>
    <div id="back-stage-User-info">
        <empty-data v-if="UserInfo == null || UserInfo.length === 0"/>
            <div id="User-plan-info" v-else>
            <table-row-count :count="total"></table-row-count>
            <!-- 搜索与添加区域 -->
            <el-row :gutter="20">
                <el-col :span="8">
                    <el-input placeholder="请输入内容" v-model="queryInfo.keyword" clearable @clear="loadUserInfo()">
                        <el-button slot="append" icon="el-icon-search" @click="getUserByKeyword()"></el-button>
                    </el-input>
                </el-col>
                <el-col :span="4">
                    <el-button type="primary" @click="dialogVisible=true">添加用户</el-button>
                </el-col>
            </el-row>

            <el-table
                    :data="UserInfo"
                    stripe
                    style="width: 100%">
                <el-table-column
                        prop="userId"
                        label="用户id"
                        width="110"
                        align="center"
                >
                </el-table-column>
                <el-table-column
                        prop="username"
                        label="用户名称"
                >
                </el-table-column>
                <el-table-column
                        prop="password"
                        label="用户密码"
                >
                </el-table-column>
                <el-table-column
                        prop="picUrl"
                        label="用户头像">
                </el-table-column>
                <el-table-column
                        prop="address"
                        label="收货地址">
                </el-table-column>  
                <el-table-column
                        prop="phone"
                        label="用户电话">
                </el-table-column>
                <el-table-column
                        prop="email"
                        label="用户邮箱">
                </el-table-column>

                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <!-- 修改按钮 -->
                        <el-button type="primary" icon="el-icon-edit" @click="showEditDialog(scope.row.userId)"></el-button>
                        <!-- 删除按钮 -->
                        <el-button type="danger" icon="el-icon-delete" @click="deleteUser(scope.row.userId)"></el-button>
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

        <!-- 添加用户的对话框 -->
        <el-dialog
            title="添加用户"
            :visible.sync="dialogVisible"
            width="50%"
            @close="loadUserInfo">
            <!-- 内容的主体区域 -->
            <el-form ref="addFormRef" :model="addForm" :rules="addFormRules" label-width="100px">
                <el-form-item label="用户名称" prop="username">
                    <el-input v-model="addForm.username"></el-input>
                </el-form-item>
                <el-form-item label="用户密码" prop="password">
                    <el-input v-model="addForm.password" type="password"></el-input>
                </el-form-item>
                <el-form-item label="确认密码" prop="surePassword">
                    <el-input v-model="addForm.surePassword" type="password"></el-input>
                </el-form-item>
                <el-form-item label="用户头像" prop="picUrl">
                    <el-input v-model="addForm.picUrl"></el-input>
                </el-form-item>
                <el-form-item label="收货地址" prop="address">
                    <el-input v-model="addForm.address"></el-input>
                </el-form-item>
                <el-form-item label="联系方式" prop="phone">
                    <el-input v-model="addForm.phone"></el-input>
                </el-form-item>
                <el-form-item label="用户邮箱" prop="email">
                    <el-input v-model="addForm.email"></el-input>
                </el-form-item>
            </el-form>
            <!-- 底部区域 -->
            <span slot="footer" class="dialog-footer">
                <el-button @click="dialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="addUser">确 定</el-button>
            </span>
        </el-dialog>

        <!-- 修改用户的对话框 -->
        <el-dialog
          title="修改用户"
          :visible.sync="editDialogVisible"
          width="50%"
          @close="editDialogClosed">
          <el-form :model="editForm" :rules="editFormRules" ref="editFormRef" label-width="100px">
            <el-form-item label="用户id" prop="userId">
              <el-input v-model="editForm.userId" disabled></el-input>
            </el-form-item>
            <el-form-item label="用户名称" prop="username">
              <el-input v-model="editForm.username"></el-input>
            </el-form-item>
            <el-form-item label="用户密码" prop="password">
              <el-input v-model="editForm.password" type="password"></el-input>
            </el-form-item>
            <el-form-item label="用户头像" prop="picUrl">
              <el-input v-model="editForm.picUrl"></el-input>
            </el-form-item>
            <el-form-item label="收货地址" prop="address">
              <el-input v-model="editForm.address"></el-input>
            </el-form-item>
            <el-form-item label="联系方式" prop="phone">
              <el-input v-model="editForm.phone"></el-input>
            </el-form-item>
            <el-form-item label="用户邮箱" prop="email">
              <el-input v-model="editForm.email"></el-input>
            </el-form-item>
          </el-form>
          <span slot="footer" class="dialog-footer">
            <el-button @click="editDialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="editUserInfo">确 定</el-button>
          </span>
        </el-dialog>


    </div>
</template>

<script>
    import {request} from "../../network/request";
    import EmptyData from "./EmptyData"
    import TableRowCount from './TableRowCount'
    export default {
        name: "UserInfo",
        data() {
            return {
                // 获取用户列表的参数对象
                queryInfo: {
                    // 查询参数
                    query: '',
                    //搜索用户关键词
                    keyword: '',
                    // 当前的页码数
                    pagenum: 1,
                    // 每页显示多少条数据
                    pagesize: 2
                },
                // 获取的用户列表
                UserInfo: null,
                
                // 总数
                total: 0,
                
                currentPage: 1,
                loading: null,
                dialogVisible: false,
                // 添加用户的表单数据
                addForm: {
                    username: '',
                    password: '',
                    surePassword: '',
                    picUrl: '',
                    address: '',
                    phone: '',
                    email: ''
                },
                // 添加表单的验证规则对象
                addFormRules: {
                    username: [
                    { required: true, message: '请输入用户名称', trigger: 'blur' },
                    { min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur' }
                    ],
                    password: [
                    { required: true, message: '请输入用户密码', trigger: 'blur' },
                    { min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur' }
                    ],
                    surePassword: [
                    { required: true, message: '请重新输入用户密码', trigger: 'blur' },
                    { min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur' }
                    ],
                    picUrle: [
                    { required: true, message: '请输入用户头像', trigger: 'blur' },
                    ],
                    address: [
                    { required: true, message: '请输入收货地址', trigger: 'blur' },
                    ],
                    phone: [
                    { required: true, message: '请输入联系方式', trigger: 'blur' },
                    ],
                    email: [
                    { required: true, message: '请输入用户邮箱', trigger: 'blur' },
                    ]
                },    

                // 控制修改用户对话框的显示与隐藏，默认为隐藏
                editDialogVisible: false,
                // 查询到的用户信息对象
                editForm: {
                    userId: '',
                    username: '',
                    password: '',
                    picUrl: '',
                    address: '',
                    phone: '',
                    email: ''
                },
                // 修改表单的验证规则对象
                editFormRules: {
                    username: [
                    { required: true, message: '请输入用户名称', trigger: 'blur' },
                    { min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur' }
                    ],
                    password: [
                    { required: true, message: '请输入用户密码', trigger: 'blur' },
                    { min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur' }
                    ],
                    picUrle: [
                    { required: true, message: '请输入用户头像', trigger: 'blur' },
                    ],
                    address: [
                    { required: true, message: '请输入收货地址', trigger: 'blur' },
                    ],
                    phone: [
                    { required: true, message: '请输入联系方式', trigger: 'blur' },
                    ],
                    email: [
                    { required: true, message: '请输入用户邮箱', trigger: 'blur' },
                    ]
                }

            }
        },

        
        methods: {
           changePage(){
               this.loadUserInfo();
           },
           //新增用户
           addUser(){
              console.log(this.gname);
              console.log(this.gdesc);
              if(this.addForm.password != this.addForm.surePassword){
                 this.$message.error('两次输入的密码不同,请重新输入!')
              }
              else{
                    request({
                    url: 'user/register',
                    params: {
                      username:this.addForm.username,
                      password:this.addForm.password,
                      picUrl:this.addForm.picUrl,
                      address:this.addForm.address,
                      phone:this.addForm.phone,
                      email:this.addForm.email
                    }
                }) .then( res => {
                    if (res.code === '000'){
                        console.log('添加成功!');
                        this.$message.success('添加成功!');
                        this.loadUserInfo();
                        this.dialogVisible = false;
                    } else {
                        this.$message.error(res.message)
                    }
                }).catch( err => {
                    this.$message.error('系统错误')
                }) 

              }
                 
           },

            // 展示编辑用户的对话框
            showEditDialog(id) {
                // console.log(id)
                request({
                    url: 'user/findByid',
                    params: {
                      userId:id
                    }
                }) .then( res => {
                    if (res.code === '000'){
                        console.log('查询用户的id为: ');
                        console.log(id);
                        console.log('查询用户成功!');
                        // this.editForm.id = res.data.gid
                        // this.editForm.name = res.data.gname
                        // this.editForm.price = res.data.gprice
                        // this.editForm.desc = res.data.gdesc
                        // this.editForm.img = res.data.pthumbnail
                        this.editForm = res.data
                        this.editDialogVisible = true
                    } else {
                        this.$message.error(res.message)
                    }
                }).catch( err => {
                    this.$message.error('系统错误')
                })  
            },
            
            // 监听修改用户对话框的关闭
            editDialogClosed () {
                this.$refs.editFormRef.resetFields()
            },
            // 修改用户信息并且提交
            editUserInfo () {
                this.$refs.editFormRef.validate(async valid => {
                    console.log(valid)
                    if (!valid) return
                    // 发起修改用户信息的数据请求
                    request({
                        url: 'user/update',
                        params: {
                            userId:this.editForm.userId,
                            username:this.editForm.username,
                            password:this.editForm.password,
                            picUrl:this.editForm.picUrl,
                            address:this.editForm.address,
                            phone:this.editForm.phone,
                            email:this.editForm.email
                        }
                    }) .then( res => {
                        if (res.code === '000'){
                            console.log('修改成功!');

                            // 提示修改成功
                            this.$message.success('更新用户信息成功！');
                            // 更新数据列表
                            this.loadUserInfo();
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

            // 删除目标用户
            deleteUser(id) {
                console.log('删除的货物的id为: ');
                console.log(id);
                request({
                    url: 'user/delete',
                    params: {
                      userId:id
                    }
                }) .then( res => {
                    if (res.code === '000'){   
                        console.log('删除用户成功!');
                        this.$message.success('删除目标用户成功！')
                        this.loadUserInfo();
                    } else {
                        this.$message.error(res.message)
                    }
                }).catch( err => {
                    this.$message.error('系统错误')
                })  
                
            },

            //搜索用户信息
            getUserByKeyword(){
               this.setLoading();
                request({
                    url: 'user/searchUser',
                    params: {
                        keyword:this.queryInfo.keyword
                    }
                }) .then( res => {
                    if (res.code === '000'){
                        this.UserInfo = res.data;
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
            //获取所有用户信息
            loadUserInfo(){
               this.setLoading();
                request({
                    url: 'user/findall',
                    params: {
                        currentPage: this.currentPage
                    }
                }) .then( res => {
                    if (res.code === '000'){
                        this.UserInfo = res.data;
                        
                        console.log("获取所有用户数据为: ");
                        console.log(this.UserInfo);
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
            this.loadUserInfo();
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