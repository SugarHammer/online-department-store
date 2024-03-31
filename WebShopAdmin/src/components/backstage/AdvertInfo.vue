<template>
    <div id="back-stage-advert-info">
        <empty-data v-if="advertInfo == null || advertInfo.length === 0"/>
            <div id="advert-plan-info" v-else>
            <table-row-count :count="total"></table-row-count>
            <!-- 搜索与添加区域 -->
            <el-row :gutter="20">
                <el-col :span="8">
                    <el-input placeholder="请输入内容" v-model="queryInfo.keyword" clearable @clear="loadAdvertInfo()">
                        <el-button slot="append" icon="el-icon-search" @click="getAdvertByKeyword()"></el-button>
                    </el-input>
                </el-col>
                <el-col :span="4">
                    <el-button type="primary" @click="dialogVisible=true">添加广告</el-button>
                </el-col>
            </el-row>

            <el-table
                    :data="advertInfo"
                    stripe
                    style="width: 100%">
                <el-table-column
                        prop="aid"
                        label="广告id"
                        width="110"
                        align="center"
                >
                </el-table-column>
                <el-table-column
                        prop="adContent"
                        label="广告名称"
                >
                </el-table-column>
                <el-table-column
                        prop="adPicUrl"
                        label="广告图片url">
                </el-table-column>  

                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <!-- 修改按钮 -->
                        <el-button type="primary" icon="el-icon-edit" @click="showEditDialog(scope.row.aid)"></el-button>
                        <!-- 删除按钮 -->
                        <el-button type="danger" icon="el-icon-delete" @click="deleteAdvert(scope.row.aid)"></el-button>
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

        <!-- 添加广告的对话框 -->
        <el-dialog
            title="添加广告"
            :visible.sync="dialogVisible"
            width="50%"
            @close="loadAdvertInfo">
            <!-- 内容的主体区域 -->
            <el-form ref="addFormRules" :model="addForm" :rules="addFormRules" label-width="100px">
                <el-form-item label="广告id" prop="aid">
              <el-input v-model="addForm.aid" disabled></el-input>
            </el-form-item>
                <el-form-item label="广告名称" prop="adContent">
                    <el-input v-model="addForm.adContent"></el-input>
                </el-form-item>
                <el-form-item label="广告图片url" prop="adPicUrl">
                    <el-input v-model="addForm.adPicUrl"></el-input>
                </el-form-item>
            </el-form>
            <!-- 底部区域 -->
            <span slot="footer" class="dialog-footer">
                <el-button @click="dialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="addAdvert">确 定</el-button>
            </span>
        </el-dialog>

        <!-- 修改广告的对话框 -->
        <el-dialog
          title="修改广告"
          :visible.sync="editDialogVisible"
          width="50%"
          @close="editDialogClosed">
          <el-form :model="editForm" :rules="editFormRules" ref="editFormRef" label-width="100px">
            <el-form-item label="广告id" prop="aid">
              <el-input v-model="editForm.aid" disabled></el-input>
            </el-form-item>
            <el-form-item label="广告名称" prop="adContent">
              <el-input v-model="editForm.adContent"></el-input>
            </el-form-item>
            <el-form-item label="广告图片" prop="adPicUrl">
              <el-input v-model="editForm.adPicUrl"></el-input>
            </el-form-item>
          </el-form>
          <span slot="footer" class="dialog-footer">
            <el-button @click="editDialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="editAdvertInfo">确 定</el-button>
          </span>
        </el-dialog>


    </div>
</template>

<script>
    import {request} from "../../network/request";
    import EmptyData from "./EmptyData"
    import TableRowCount from './TableRowCount'
    export default {
        adContent: "AdvertInfo",
        data() {
            return {
                // 获取广告列表的参数对象
                queryInfo: {
                    // 查询参数
                    query: '',
                    //搜索广告关键词
                    keyword: '',
                    // 当前的页码数
                    pagenum: 1,
                    // 每页显示多少条数据
                    pagesize: 2
                },
                // 获取的广告列表
                advertInfo: null,
                
                // 总数
                total: 0,
                
                currentPage: 1,
                loading: null,
                dialogVisible: false,
                // 添加广告的表单数据
                addForm: {
                    aid:'',
                    adContent: '',
                    adPicUrl: ''
                },
                // 添加表单的验证规则对象
                addFormRules: {
                    adContent: [
                    { required: true, message: '请输入广告名称', trigger: 'blur' },
                    { min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur' }
                    ],
                    adPicUrl: [
                    { required: true, message: '请输入广告图片', trigger: 'blur' },
                    ]
                },    

                // 控制修改广告对话框的显示与隐藏，默认为隐藏
                editDialogVisible: false,
                // 查询到的广告信息对象
                editForm: {
                    aid: '',
                    adContent: '',
                    adPicUrl: ''
                },
                // 修改表单的验证规则对象
                editFormRules: {
                    adContent: [
                 
                    ],
                    adPicUrl: [
                   
                    ]
                }

            }
        },

        
        methods: {
           changePage(){
               this.loadadvertInfo();
           },
           //新增广告
           addAdvert(){
              console.log(this.adContent);
              console.log(this.adPicUrl);
    
              request({
                    url: 'advert/addAdvert',
                    params: {
    
                      adContent:this.addForm.adContent,
                      adPicUrl:this.addForm.adPicUrl,
                     
                    }
                }) .then( res => {
                    if (res.code === '000'){
                        console.log('添加成功!');
                        this.$message.success('添加成功!');
                        this.loadAdvertInfo();
                        this.dialogVisible = false;
                    } else {
                        this.$message.error(res.message)
                    }
                }). catch( err => {
                        this.$message.success('添加广告信息成功！')
                          // 更新数据列表
                            this.loadAdvertInfo();
                            // 关闭对话框
                            this.editDialogVisible = false;
                })    
                 
           },

            // 展示编辑广告的对话框
            showEditDialog(id) {
                // console.log(id)
                request({
                    url: 'advert/selectAdvertById',
                    params: {
                      id:id
                    }
                }) .then( res => {
                    if (res.code === '000'){
                        console.log('查询广告的id为: ');
                        console.log(id);
                        console.log('查询广告成功!');
            
                        this.editForm.aid = res.data.aid
                        console.log(res.data.aid);
                        this.editForm.adContent = res.data.adContent
                        console.log("=========="+res.data.adContent);
                        this.editForm.adPicUrl = res.data.adPicUrl
                        console.log("=========="+res.data.adPicUrl);
                        this.editDialogVisible = true
                    } else {
                        this.$message.error(res.message)
                    }
                }).catch( err => {
                    this.$message.error('系统错误')
                })  
            },
            
            // 监听修改广告对话框的关闭
            editDialogClosed () {
                this.$refs.editFormRef.resetFields()
            },
            // 修改广告信息并且提交
            editAdvertInfo () {
                this.$refs.editFormRef.validate(async valid => {
                    console.log(valid)
                    if (!valid) return
                    // 发起修改广告信息的数据请求
                    request({
                        url: 'advert/updateAdvert',
                        params: {
                            aid:this.editForm.aid,
                            adContent:this.editForm.adContent,
                            adPicUrl:this.editForm.adPicUrl
                        }
                    }) .then( res => {
                        if (res.code === '000'){
                            console.log('修改成功!');

                            // 提示修改成功
                            this.$message.success('更新广告信息成功！');
                            // 更新数据列表
                            this.loadAdvertInfo();
                            // 关闭对话框
                            this.editDialogVisible = false;

                        } else {
                            this.$message.error(res.message)
                        }
                    }).catch( err => {
                        this.$message.success('更新广告信息成功！')
                          // 更新数据列表
                            this.loadAdvertInfo();
                            // 关闭对话框
                            this.editDialogVisible = false;
                    }) 
                    
                })
            },

            // 删除目标广告
            deleteAdvert(id) {
                console.log('删除的广告的id为: ');
                console.log(id);
                request({
                    url: 'advert/deleteAdvertById',
                    params: {
                      aid:id
                    }
                }) .then( res => {
                    if (res.code === '000'){   
                        console.log('删除广告成功!');
                        this.$message.success('删除目标广告成功！')
                        this.loadAdvertInfo();
                    } else {
                        this.$message.error(res.message)
                    }
                }).catch( err => {
                   this.$message.success('删除目标广告成功！')
                        this.loadAdvertInfo();
                })  
                
            },

            //搜索广告信息
            getAdvertByKeyword(){
               this.setLoading();
                request({
                    url: 'advert/searchAdvert',
                    params: {
                        keyword:this.queryInfo.keyword
                    }
                }) .then( res => {
                    if (res.code === '000'){
                        this.advertInfo = res.data;
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
            
            //获取所有广告信息
            loadAdvertInfo(){
               this.setLoading();
                request({
                    url: 'advert/selectAllAdvert',
                    params: {
                        currentPage: this.currentPage
                    }
                }) .then( res => {
                    if (res.code === '000'){
                        this.advertInfo = res.data;

                        console.log("获取所有广告数据为: ");
                        console.log(this.advertInfo);
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
            this.loadAdvertInfo();
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