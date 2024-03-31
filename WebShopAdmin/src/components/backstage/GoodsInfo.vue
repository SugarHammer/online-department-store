<template>
    <div id="back-stage-goods-info">
        <empty-data v-if="goodsInfo == null || goodsInfo.length === 0"/>
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
                    <el-button type="primary" @click="dialogVisible=true">添加商品</el-button>
                </el-col>

                
                    <el-upload
                        style="display: inline"
                        :show-file-list="false"
                        :on-success="onSuccess"
                        :on-error="onError"
                        :before-upload="beforeUpload"
                        action="http://localhost:8081/excel/import">
                        <el-button  type="success" :disabled="!enabledUploadBtn" :icon="uploadBtnIcon">{{btnText}}</el-button>
                    </el-upload>
                
                
                    <el-button @click="exportData" type="success" icon="el-icon-download" style="margin-left:10px">导出数据</el-button>
               
                

            </el-row>

            <!-- <div>
                <el-upload drag
                    :limit=limitNum
                    :auto-upload="false"
                    accept=".xlsx"
                    :action="UploadUrl()"
                    :before-upload="beforeUploadFile"
                    :on-change="fileChange"
                    :on-exceed="exceedFile"
                    :on-success="handleSuccess"
                    :on-error="handleError"
                    :file-list="fileList">
                    <i class="el-icon-upload"></i>
                    <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
                    <div class="el-upload__tip" slot="tip">只能上传xlsx文件，且不超过10M</div>
                </el-upload>
                <br/>
                <el-button size="small" type="primary" @click="uploadFile">立即上传</el-button>
                <el-button size="small">取消</el-button>
            </div> -->


            <el-table
                    :data="goodsInfo"
                    stripe
                    style="width: 100%">
                <el-table-column
                        prop="gid"
                        label="商品id"
                        width="110"
                        align="center"
                >
                </el-table-column>
                <el-table-column
                        prop="gname"
                        label="商品名称"
                >
                </el-table-column>
                <el-table-column
                        prop="gprice"
                        label="商品价格"
                >
                </el-table-column>
                <el-table-column
                        prop="gdesc"
                        label="商品描述">
                </el-table-column>
                <el-table-column
                        prop="pthumbnail"
                        label="商品图片url">
                </el-table-column>  

                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <!-- 修改按钮 -->
                        <el-button type="primary" icon="el-icon-edit" @click="showEditDialog(scope.row.gid)"></el-button>
                        <!-- 删除按钮 -->
                        <el-button type="danger" icon="el-icon-delete" @click="deleteGoods(scope.row.gid)"></el-button>
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
            title="添加商品"
            :visible.sync="dialogVisible"
            width="50%"
            @close="loadGoodsInfo">
            <!-- 内容的主体区域 -->
            <el-form ref="addFormRef" :model="addForm" :rules="addFormRules" label-width="100px">
                <el-form-item label="商品名称" prop="name">
                    <el-input v-model="addForm.name"></el-input>
                </el-form-item>
                <el-form-item label="商品价格" prop="price">
                    <el-input v-model="addForm.price"></el-input>
                </el-form-item>
                <el-form-item label="商品描述" prop="desc">
                    <el-input v-model="addForm.desc"></el-input>
                </el-form-item>
                <el-form-item label="商品图片url" prop="img">
                    <el-input v-model="addForm.img"></el-input>
                </el-form-item>
            </el-form>
            <!-- 底部区域 -->
            <span slot="footer" class="dialog-footer">
                <el-button @click="dialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="addGoods">确 定</el-button>
            </span>
        </el-dialog>

        <!-- 修改商品的对话框 -->
        <el-dialog
          title="修改商品"
          :visible.sync="editDialogVisible"
          width="50%"
          @close="editDialogClosed">
          <el-form :model="editForm" :rules="editFormRules" ref="editFormRef" label-width="100px">
            <el-form-item label="商品id" prop="id">
              <el-input v-model="editForm.id" disabled></el-input>
            </el-form-item>
            <el-form-item label="商品名称" prop="name">
              <el-input v-model="editForm.name"></el-input>
            </el-form-item>
            <el-form-item label="商品价格" prop="price">
              <el-input v-model="editForm.price"></el-input>
            </el-form-item>
            <el-form-item label="商品描述" prop="desc">
              <el-input v-model="editForm.desc"></el-input>
            </el-form-item>
            <el-form-item label="商品图片" prop="img">
              <el-input v-model="editForm.img"></el-input>
            </el-form-item>
          </el-form>
          <span slot="footer" class="dialog-footer">
            <el-button @click="editDialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="editGoodsInfo">确 定</el-button>
          </span>
        </el-dialog>


    </div>
</template>

<script>
    import { fileRequest } from '../../network/fileRequest';
    import {request} from "../../network/request";
    import EmptyData from "./EmptyData"
    import TableRowCount from './TableRowCount'
    export default {
        name: "GoodsInfo",
        data() {
            return {
                //excel
                uploadBtnIcon:'el-icon-upload2',
                enabledUploadBtn:true,
                dialogVisible:false,
                btnText:'数据导入',
                value: '',

                // limitNum: 1,  // 上传excell时，同时允许上传的最大数
                // fileList: [],   // excel文件列表

                // 获取商品列表的参数对象
                queryInfo: {
                    // 查询参数
                    query: '',
                    //搜索商品关键词
                    keyword: '',
                    // 当前的页码数
                    pagenum: 1,
                    // 每页显示多少条数据
                    pagesize: 2
                },
                // 获取的商品列表
                goodsInfo: null,
                
                // 总数
                total: 0,
                
                currentPage: 1,
                loading: null,
                dialogVisible: false,
                // 添加商品的表单数据
                addForm: {
                    name: '',
                    price: '',
                    desc: '',
                    img: ''
                },
                // 添加表单的验证规则对象
                addFormRules: {
                    name: [
                    { required: true, message: '请输入商品名称', trigger: 'blur' },
                    { min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur' }
                    ],
                    price: [
                    { required: true, message: '请输入商品价格', trigger: 'blur' },
                    ],
                    desc: [
                    { required: true, message: '请输入商品描述', trigger: 'blur' },
                    ],
                    img: [
                    { required: true, message: '请输入商品图片', trigger: 'blur' },
                    ]
                },    

                // 控制修改商品对话框的显示与隐藏，默认为隐藏
                editDialogVisible: false,
                // 查询到的商品信息对象
                editForm: {
                    id: '',
                    name: '',
                    price: '',
                    desc: '',
                    img: ''
                },
                // 修改表单的验证规则对象
                editFormRules: {
                    name: [
                    { required: true, message: '请输入商品名称', trigger: 'blur' },
                    { min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur' }
                    ],
                    price: [
                    { required: true, message: '请输入商品价格', trigger: 'blur' },
                    ],
                    desc: [
                    { required: true, message: '请输入商品描述', trigger: 'blur' },
                    ],
                    img: [
                    { required: true, message: '请输入商品图片', trigger: 'blur' },
                    ]
                }

            }
        },

    
        methods: {
            onSuccess(response,file,fileList){
                this.enabledUploadBtn=true;
                this.uploadBtnIcon="el-icon-upload2";
                this.btnText='数据导入';
                //alert("数据导入成功！");
                this.$message.success('数据导入成功！');
                this.loadGoodsInfo();
                //this.initJls();
            },

            onError(err,file,fileList){
                this.enabledUploadBtn=true;
                this.uploadBtnIcon="el-icon-upload2";
                this.btnText='数据导入';
                alert("数据导入失败！请检查是否有重复数据,和网络连接状况！");
            },

            beforeUpload(file){
                this.enabledUploadBtn=false;
                this.uploadBtnIcon="el-icon-loading";
                this.btnText='正在导入';
            },

            importData(){
                //1.查找到存放文件的元素
                let myfile=this.$refs.myfile;
                //2.钙元素内部有一个file数组，里面存放了所有选择的file。
                // 由于上传文件时，文件可以多选，因此这里拿到的files对象是一个数组
                let files=myfile.files;
                //3.从files对象中，获取自己要上传的文件（即数组中的第一项）
                let file=files[0];
                //4.构造一个FormData，用来存放上传的数据，注意FormData不可以使用链式配置
                var formData = new FormData();
                formData.append("file",file);
                formData.append("username","zhaoke");
                //5.构造好FormData后，就可以直接上传数据了，FormData就是要上传的数据
                //6.文件上传注意两点，①请求方法为POST，②设置Content-Type为multipart/form-data

                // this.uploadFileRequest("/excel/import",formData).then(resp=>{
                //     if (resp) {
                //     alert(resp);
                //     }
                // })

                fileRequest({
                    url: 'excel/import',
                    data:formData,
                    methods:'post'
                }) .then( res => {
                    if (res.code === '000'){
                        
                        this.$message.success('上传成功!');
                        
                    } else {
                        this.$message.error(res.message)
                    }
                }).catch( err => {
                    this.$message.error('系统错误')
                })
            },

           exportData(){
                /*表示在当前页面打开新地址*/
                window.open("http://localhost:8081/excel/export");
            },
        
           changePage(){
               this.loadgoodsInfo();
           },
           //新增商品
           addGoods(){
              console.log(this.gname);
              console.log(this.gdesc);
    
              request({
                    url: 'goods/addGoods',
                    params: {
                      name:this.addForm.name,
                      price:this.addForm.price,
                      desc:this.addForm.desc,
                      img:this.addForm.img
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
                 
           },

            // 展示编辑商品的对话框
            showEditDialog(id) {
                // console.log(id)
                request({
                    url: 'goods/selectGoodsById',
                    params: {
                      id:id
                    }
                }) .then( res => {
                    if (res.code === '000'){
                        console.log('查询货物的id为: ');
                        console.log(id);
                        console.log('查询货物成功!');
                        this.editForm.id = res.data.gid
                        this.editForm.name = res.data.gname
                        this.editForm.price = res.data.gprice
                        this.editForm.desc = res.data.gdesc
                        this.editForm.img = res.data.pthumbnail
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
            editGoodsInfo () {
                this.$refs.editFormRef.validate(async valid => {
                    console.log(valid)
                    if (!valid) return
                    // 发起修改商品信息的数据请求
                    request({
                        url: 'goods/updateGoods',
                        params: {
                            id:this.editForm.id,
                            name:this.editForm.name,
                            price:this.editForm.price,
                            desc:this.editForm.desc,
                            img:this.editForm.img
                        }
                    }) .then( res => {
                        if (res.code === '000'){
                            console.log('修改成功!');

                            // 提示修改成功
                            this.$message.success('更新商品信息成功！');
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
                    
                })
            },

            // 删除目标商品
            deleteGoods(id) {
                console.log('删除的货物的id为: ');
                console.log(id);
                request({
                    url: 'goods/deleteGoodsById',
                    params: {
                      id:id
                    }
                }) .then( res => {
                    if (res.code === '000'){   
                        console.log('删除货物成功!');
                        this.$message.success('删除目标商品成功！')
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
                    url: 'goods/searchGoods',
                    params: {
                        keyword:this.queryInfo.keyword
                    }
                }) .then( res => {
                    if (res.code === '000'){
                        this.goodsInfo = res.data;
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
            //获取所有商品信息
            loadGoodsInfo(){
               this.setLoading();
                request({
                    url: 'goods/selectAllGoods',
                    params: {
                        currentPage: this.currentPage
                    }
                }) .then( res => {
                    if (res.code === '000'){
                        this.goodsInfo = res.data;
                        //转换price为float类型
                        this.goodsInfo.forEach(element => {
                            element.gprice = parseFloat(element.gprice).toFixed(2)
                        });

                        console.log("获取所有商品数据为: ");
                        console.log(this.goodsInfo);
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