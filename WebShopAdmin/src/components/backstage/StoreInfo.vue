<template>
    <div id="back-stage-store-info">
        <empty-data v-if="storeInfo == null || storeInfo.length === 0"/>
            <div id="store-plan-info" v-else>
            <table-row-count :count="total"></table-row-count>
            <!-- 搜索与添加区域 -->
            <el-row :gutter="20">
                <el-col :span="8">
                    <el-input placeholder="请输入内容" v-model="queryInfo.keyword" clearable @clear="loadStoreInfo()">
                        <el-button slot="append" icon="el-icon-search" @click="getStoreByKeyword()"></el-button>
                    </el-input>
                </el-col>
                <el-col :span="4">
                    <el-button type="primary" @click="dialogVisible=true">添加商家</el-button>
                </el-col>
            </el-row>

            <el-table
                    :data="storeInfo"
                    stripe
                    style="width: 100%">
                <el-table-column
                        prop="id"
                        label="商家id"
                        width="110"
                        align="center"
                >
                </el-table-column>
                <el-table-column
                        prop="name"
                        label="商家名称"
                >
                </el-table-column>
                 <el-table-column
                        prop="descp"
                        label="商家描述">
                </el-table-column>
                <el-table-column
                        prop="url"
                        label="商家地址url">
                </el-table-column>  

                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <!-- 修改按钮 -->
                        <el-button type="primary" icon="el-icon-edit" @click="showEditDialog(scope.row.id)"></el-button>
                        <!-- 删除按钮 -->
                        <el-button type="danger" icon="el-icon-delete" @click="deleteStore(scope.row.id)"></el-button>
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

        <!-- 添加商家的对话框 -->
        <el-dialog
            title="添加商家"
            :visible.sync="dialogVisible"
            width="50%"
            @close="loadStoreInfo">
            <!-- 内容的主体区域 -->
            <el-form ref="addFormRules" :model="addForm" :rules="addFormRules" label-width="100px">
               <el-form-item label="商家名称" prop="name">
                    <el-input v-model="addForm.name"></el-input>
                </el-form-item>
                <el-form-item label="商家描述" prop="descp">
                    <el-input v-model="addForm.descp"></el-input>
                </el-form-item>
                <el-form-item label="商家地址url" prop="url">
                    <el-input v-model="addForm.url"></el-input>
                </el-form-item>
            </el-form>
            <!-- 底部区域 -->
            <span slot="footer" class="dialog-footer">
                <el-button @click="dialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="addStore">确 定</el-button>
            </span>
        </el-dialog>

        <!-- 修改商家的对话框 -->
        <el-dialog
          title="修改商家"
          :visible.sync="editDialogVisible"
          width="50%"
          @close="editDialogClosed">
          <el-form :model="editForm" :rules="editFormRules" ref="editFormRef" label-width="100px">
            <el-form-item label="商家名称" prop="name">
                <el-input v-model="editForm.name"></el-input>
            </el-form-item>
            <el-form-item label="商家描述" prop="descp">
                <el-input v-model="editForm.descp"></el-input>
            </el-form-item>
            <el-form-item label="商家地址url" prop="url">
                <el-input v-model="editForm.url"></el-input>
            </el-form-item>
          </el-form>
          <span slot="footer" class="dialog-footer">
            <el-button @click="editDialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="editStoreInfo">确 定</el-button>
          </span>
        </el-dialog>


    </div>
</template>

<script>
    import {request} from "../../network/request";
    import EmptyData from "./EmptyData"
    import TableRowCount from './TableRowCount'
    export default {
        name: "StoreInfo",
        data() {
            return {
                // 获取商家列表的参数对象
                queryInfo: {
                    // 查询参数
                    query: '',
                    //搜索商家关键词
                    keyword: '',
                    // 当前的页码数
                    pagenum: 1,
                    // 每页显示多少条数据
                    pagesize: 2
                },
                // 获取的商家列表
                storeInfo: null,
                
                // 总数
                total: 0,
                
                currentPage: 1,
                loading: null,
                dialogVisible: false,
                // 添加商家的表单数据
                addForm: {
                    name:'',
                    descp: '',
                    url: ''
                },
                // 添加表单的验证规则对象
                addFormRules: {
                    name: [
                    { required: true, message: '请输入商家名称', trigger: 'blur' },
                    { min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur' }
                    ],
                    url: [
                    { required: true, message: '请输入商家地址', trigger: 'blur' },
                    ]
                },    

                // 控制修改商家对话框的显示与隐藏，默认为隐藏
                editDialogVisible: false,
                // 查询到的商家信息对象
                editForm: {
                    name: '',
                    descp: '',
                    url: ''
                },
                // 修改表单的验证规则对象
                editFormRules: {
                    name: [
                 
                    ],
                    descp: [
                 
                    ],
                    url: [
                   
                    ]
                }

            }
        },

        
        methods: {
           changePage(){
               this.loadstoreInfo();
           },
           //新增商家
           addStore(){
              console.log(this.name);
              console.log(this.descp);
            //   console.log(this.url);
    
              request({
                    url: 'store/addStore',
                    params: {
                      name:this.addForm.name,
                      descp:this.addForm.descp,
                      url:this.addForm.url,
                     
                    }
                }) .then( res => {
                    if (res.code === '000'){
                        console.log('添加成功!');
                        this.$message.success('添加成功!');
                        this.loadStoreInfo();
                        this.dialogVisible = false;
                    } else {
                        this.$message.error(res.message)
                    }
                }). catch( err => {
                        this.$message.success('添加商家信息成功！')
                          // 更新数据列表
                            this.loadStoreInfo();
                            // 关闭对话框
                            this.editDialogVisible = false;
                })    
                 
           },

            // 展示编辑商家的对话框
            showEditDialog(id) {
                // console.log(id)
                request({
                    url: 'store/selectStoreById',
                    params: {
                      id:id
                    }
                }) .then( res => {
                    if (res.code === '000'){
                        console.log('查询商家的id为: ');
                        console.log(id);
                        console.log('查询商家成功!');
            
                        this.editForm.id = res.data.id
                        console.log(res.data.id);
                        this.editForm.name = res.data.name
                        console.log("=========="+res.data.name);
                        this.editForm.descp = res.data.descp
                        this.editForm.url = res.data.url
                        console.log("=========="+res.data.url);
                        this.editDialogVisible = true
                    } else {
                        this.$message.error(res.message)
                    }
                }).catch( err => {
                    this.$message.error('系统错误')
                })  
            },
            
            // 监听修改商家对话框的关闭
            editDialogClosed () {
                this.$refs.editFormRef.resetFields()
            },
            // 修改商家信息并且提交
            editStoreInfo () {
                this.$refs.editFormRef.validate(async valid => {
                    console.log(valid)
                    if (!valid) return
                    // 发起修改商家信息的数据请求
                    request({
                        url: 'store/updateStore',
                        params: {
                            id:this.editForm.id,
                            name:this.editForm.name,
                            descp:this.editForm.descp,
                            url:this.editForm.url
                        }
                    }) .then( res => {
                        if (res.code === '000'){
                            console.log('修改成功!');

                            // 提示修改成功
                            this.$message.success('更新商家信息成功！');
                            // 更新数据列表
                            this.loadStoreInfo();
                            // 关闭对话框
                            this.editDialogVisible = false;

                        } else {
                            this.$message.error(res.message)
                        }
                    }).catch( err => {
                        this.$message.success('更新商家信息成功！')
                          // 更新数据列表
                            this.loadStoreInfo();
                            // 关闭对话框
                            this.editDialogVisible = false;
                    }) 
                    
                })
            },

            // 删除目标商家
            deleteStore(id) {
                console.log('删除的商家的id为: ');
                console.log(id);
                request({
                    url: 'store/deleteStoreById',
                    params: {
                      id:id
                    }
                }) .then( res => {
                    if (res.code === '000'){   
                        console.log('删除商家成功!');
                        this.$message.success('删除目标商家成功！')
                        this.loadStoreInfo();
                    } else {
                        this.$message.error(res.message)
                    }
                }).catch( err => {
                   this.$message.success('删除目标商家成功！')
                        this.loadStoreInfo();
                })  
                
            },

            //搜索商家信息
            getStoreByKeyword(){
               this.setLoading();
                request({
                    url: 'store/searchStore',
                    params: {
                        keyword:this.queryInfo.keyword
                    }
                }) .then( res => {
                    if (res.code === '000'){
                        this.storeInfo = res.data;
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
            
            //获取所有商家信息
            loadStoreInfo(){
               this.setLoading();
                request({
                    url: 'store/selectAllStore',
                    params: {
                        currentPage: this.currentPage
                    }
                }) .then( res => {
                    if (res.code === '000'){
                        this.storeInfo = res.data;

                        console.log("获取所有商家数据为: ");
                        console.log(this.storeInfo);
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
            this.loadStoreInfo();
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