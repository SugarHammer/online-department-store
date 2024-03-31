<template>
    <div id="back-stage-comment-info">
       <empty-data v-if="commentInfo == null || commentInfo.length === 0"/>
          <div id="comment-plan-info" v-else>
          <table-row-count :count="total"></table-row-count>
          <!--搜索与添加区域-->
          <el-row :gutter="20">
              <el-col :span="8">
                  <el-input placeholder="请输入内容" v-model="queryInfo.keyword" clearable @click="loadCommentsinfo()">
                      <el-button slot="append" icon="el-icon-search" @click="getCommentsByKeyword()"></el-button>
                  </el-input>
              </el-col>
              
             </el-row>

             <el-table
                  :data="commentInfo"
                  stripe
                  style="width: 100%">
                <el-table-column
                   prop="cId"
                   label="评论id"
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
                    prop="byGoodsId"
                    label="商品id"
                >
                </el-table-column>
                <el-table-column
                    prop="nickName" 
                    label="用户昵称"
                >
                </el-table-column>
                <el-table-column
                     prop="picUrl"
                     label="用户头像url"
                >
                </el-table-column>
                <el-table-column
                      prop="content"
                      label="评论内容"
                >
                </el-table-column>
                <el-table-column
                      prop="time"
                      label="评论时间"
                >
                </el-table-column>

                <el-table-column label="操作">
                  <template slot-scope="scope">
                      <!--修改按钮-->
                      <el-button type="primary" icon="el-icon-edit" @click="showEditDialog(scope.row.cId)"></el-button>
                      <!--删除按钮-->
                      <el-button type="danger" icon="el-icon-delete" @click="deleteComments(scope.row.cId)"></el-button>
                    </template>
                </el-table-column>
             </el-table>

             <div class="page-bar">
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

          <!-- 修改评论的对话框 -->
          <el-dialog
             title="修改评论"
             :visible.sync="editDialogVisible"
             width="50%"
             @close="editDialogClosed">
            <el-form :model="editForm" :rules="editFormRules" ref="editFormRef" label-width="100px">
            <el-form-item label="评论id" prop="id">
                <el-input v-model="editForm.id" disabled></el-input>
            </el-form-item>
            <el-form-item label="用户id" prop="userid">
                <el-input v-model="editForm.userid" disabled></el-input>
            </el-form-item>
            <el-form-item label="商品id" prop="goodsid">
                <el-input v-model="editForm.goodsid" disabled></el-input>
            </el-form-item>
            <el-form-item label="用户昵称" prop="username">
                <el-input v-model="editForm.username" disabled></el-input>
            </el-form-item>
            <el-form-item label="用户头像" prop="img">
                <el-input v-model="editForm.img" disabled></el-input>
            </el-form-item>
            <el-form-item label="评论内容" prop="content">
                <el-input v-model="editForm.content"></el-input>
            </el-form-item>
            <el-form-item label="评论时间" prop="time">
                <el-date-picker v-model="editForm.time" value-format="yyyy-MM-dd HH:mm:ss" type="datetime" placeholder="选择日期和时间"></el-date-picker>
            </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editDialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="editCommentInfo">确 定</el-button>
            </span> 
          </el-dialog>
    </div>              
</template>

<script>
    import {request} from "../../network/request";
    import EmptyData from "./EmptyData"
    import TableRowCount from './TableRowCount'
    export default {
        name: "CommentInfo",
        data() {
           return {
               //获取评论列表的参数对象
               queryInfo: {
                   // 查询参数
                   query: '',
                   //搜索商品的关键词
                   keyword: '',
                   // 当前的页码数
                   pagenum: 1,
                   // 每页显示多少条数据
                   pagesize: 2
               },
               // 获取的评论列表
               commentInfo: null,

               //总数
               total: 0,

               currentPage: 1,
               loading: null,
               dialogVisible: false,
             //控制修改评论对话框框的显示与隐藏，默认为隐藏
             editDialogVisible: false,
             // 查询到的评论信息对象
             editForm: {
                 id: '',
                 userid: '',
                 goodsid: '',
                 username: '',
                 img: '',
                 content: '',
                 time: '',
             },
             // 修改表单验证规则对象
             editFormRules: {
                 content: [
                 { required: true, message: '请输入修改后的评论', trigger: 'blur' },    
                 ],
             }
           } 
        },

    methods: {
        changePage(){
            this.loadCommentsinfo()
        },
        // 展示编辑商品的对话框
        showEditDialog(id) {
            //console.log(id)
            request({
                url: 'comments/selectCommentsBycId',
                params: {
                    id:id
                }
            }) .then( res => {
                if(res.code === '000'){
                 console.log('查询评论的id为：');
                 console.log(id);
                 console.log('查询评论成功！');
                 this.editForm.id = res.data.cId
                 this.editForm.userid = res.data.userId
                 this.editForm.goodsid = res.data.byGoodsId
                 this.editForm.username = res.data.nickName
                 this.editForm.img = res.data.picUrl
                 this.editForm.content = res.data.content
                 this.editForm.time = res.data.time
                 this.editDialogVisible = true
                } else {
                    this.$message.error(res.message);
                }
            }).catch( err =>{
                this.$message.error('系统错误')
            })
        },
        
        // 监听修改评论对话框的关闭
        editDialogClosed() {
            this.$refs.editFormRef.resetFields()
        },

        //修改评论信息并且提交
        editCommentInfo() {
            this.$refs.editFormRef.validate(async valid => {
                console.log(valid)
                if(!valid) return
                //发起修改评论信息的数据请求
                request({
                    url: 'comments/updateComments',
                    params: {
                        id:this.editForm.id,
                        userid:this.editForm.userid,
                        goodsid:this.editForm.goodsid,
                        username:this.editForm.username,
                        img:this.editForm.img,
                        content:this.editForm.content,
                        time:this.editForm.time
                    }
                }).then( res => {
                    if(res.code === '000'){
                       console.log('修改成功！');
                       //提示修改成功
                       this.$message.success('更新评论信息成功！');
                       //更新数据列表
                       this.loadCommentsinfo();
                       //关闭对话框
                       this.editDialogVisible =false;
                    }else{
                        this.$message.error(res.message)
                    }
                }).catch( err =>{
                    this.$message.error('系统错误')
                })
            })
        },
        
        //删除目标商品
        deleteComments(id) {
            console.log('删除的评论的id为: ');
            console.log(id);
            request({
                url: 'comments/deleteCommentsBycId',
                params: {
                    id:id
                }
            }).then( res =>{
                if(res.code === '000'){
                    console.log('删除评论成功!');
                    this.$message.success('删除目标评论成功!')
                    this.loadCommentsinfo();
                } else {
                    this.$message.error(res.message)
                }
            }).catch( err => {
                this.$message.error('系统错误')
            })
        },

        //搜索评论信息
        getCommentsByKeyword(){
            this.setLoading();
            request({
                url: 'comments/searchComments',
                params: {
                    keyword:this.queryInfo.keyword
                }
            }) .then( res =>{
                if(res.code === '000'){
                    this.commentInfo = res.data;
                    console.log("关键字为: ");
                    console.log(this.queryInfo.keyword);
                    console.log("查询结果为: ");
                    console.log(res.data);
                    this.total = res.data.length;
                }else{
                    this.$message.error(res.message)
                }
            }).catch( err => {
                this.$message.error('系统错误')
            }).finally( () => {this.setUnloading();})
        },
        //获取所有评论信息
        loadCommentsinfo(){
            this.setLoading();
            request({
                url: 'comments/selectAllComments',
                params: {
                    currentPage:this.currentPage
                }
            }).then( res => {
                if(res.code === '000'){
                    this.commentInfo = res.data;
                    console.log("获取所有商品数据为：");
                    console.log(this.commentInfo);
                    this.total = res.data.length;
                }else {
                    this.$message.error(res.message)
                }
            }).catch( err =>{
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
        this.loadCommentsinfo();
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