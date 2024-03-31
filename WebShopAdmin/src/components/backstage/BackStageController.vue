<script src="../../../babel.config.js"></script>
   <template>

       <div id="back-stage-controller" class="clearfix">

           <div id="back-stage-controller-header">
             <div id="back-stage-header-text">
               <div id="back-stage-header-title">
                 在线百货购物商城
<!--                 {{$store.state.status}}-->
               </div>
               <div id="back-stage-header-title-eng">
                WEB SHOPPING SYSTEM
               </div>
             </div>

         <el-popover
             placement="top-start"
             width="200"
             trigger="hover"
         >
           <div id="logout-link">
             <el-link @click="logout">注销登录</el-link>
           </div>
           <div id="back-stage-controller-header-avatar"  slot="reference"></div>
         </el-popover>
       </div>


         <div id="back-stage-controller-menu">
           <el-menu
               :router="true"
               :default-active="$route.path.substr(11) || 'situation'"
               class="el-menu-vertical-demo"
               @open="handleOpen"
               @close="handleClose"
               background-color="#fff"
               text-color="#999"
               active-text-color="#8DC4F9">

              <el-menu-item index="home">
               <i class="el-icon-s-home"></i>
               <span slot="title">系统首页</span>
             </el-menu-item>

              <el-submenu index="1" >
               <template slot="title">
                 <i class="el-icon-user"></i>
                 <span >用户管理</span>
               </template>
               <el-menu-item index="userInfo">用户列表</el-menu-item>
             </el-submenu>


             <el-submenu index="2" >
               <template slot="title">
                 <i class="el-icon-goods"></i>
                 <span >商品管理</span>
               </template>
               <el-menu-item index="goodsInfo">商品列表</el-menu-item>
             </el-submenu>

             <el-submenu index="3" >
               <template slot="title">
                 <i class="el-icon-shopping-cart-full"></i>
                 <span >购物车管理</span>
               </template>
               <el-menu-item index="cartInfo">购物车列表</el-menu-item>
             </el-submenu>

             <el-submenu index="4" >
               <template slot="title">
                 <i class="el-icon-shopping-bag-1"></i>
                 <span >订单管理</span>
               </template>
               <el-menu-item index="orderInfo">订单列表</el-menu-item>
             </el-submenu>

             <el-submenu index="5" >
               <template slot="title">
                 <i class="el-icon-news"></i>
                 <span >广告管理</span>
               </template>
               <el-menu-item index="advertInfo">广告列表</el-menu-item>
             </el-submenu>

             <el-submenu index="6" >
               <template slot="title">
                 <i class="el-icon-chat-line-square"></i>
                 <span >评论管理</span>
               </template>
               <el-menu-item index="commentInfo">评论列表</el-menu-item>
             </el-submenu>

             <el-submenu index="7" >
               <template slot="title">
                 <i class="el-icon-s-shop"></i>
                 <span >商家管理</span>
               </template>
               <el-menu-item index="storeInfo">商家列表</el-menu-item>
             </el-submenu>

             <el-submenu index="8">
               <template slot="title">
                 <i class="el-icon-s-data"></i>
                 <span>数据分析</span>
               </template>
               <!-- <el-menu-item index="userAnalyze">用户分析</el-menu-item>
               <el-menu-item index="goodsAnalyze">商品分析</el-menu-item>
               <el-menu-item index="orderAnalyze">订单分析</el-menu-item> -->
             </el-submenu>
           </el-menu>
         </div>


       <div id="back-stage-controller-main">
         <router-view/>
       </div>
     </div>


</template>

<script>
    import {request} from "../../network/request";
    import {fileRequest} from "../../network/fileRequest";

    export default {
        name: "BackStageController",
        data(){
            return{
              state: 0,
              statePermissionTable: {
                //state 0 -> system initial
                //state 1 -> have major info but no stu
                //state 2 -> have major info and stu info -> 可以开始录取
                //state 3 -> finish admit （完成录取）
                //state 4 -> finish adjust can download
                uploadMajorInfo: [0],
                uploadStuInfo:[1],
                enrollable: [2],
                adjustable: [3],
                downloadable:[4]
              },
            }
        },
        methods: {
            handleOpen(key, keyPath) {
                console.log(key, keyPath);
            },
            handleClose(key, keyPath) {
                console.log(key, keyPath);
            },
            //for nav bar
            handleSelect(key, keyPath) {
             console.log(key, keyPath);
            },
            logout(){
                  request({
                      url: 'login/logout'
                  }).then( res => {
                      if (res.code === '000'){
                          this.$message.success('注销成功');
                      } else {
                          this.$message.error(res.message);
                      }
                      this.$router.push('/login');
                  })
              },
            doEnroll(){
              this.setLoading()
              request({
                url: 'student/doEnroll'
              }) .then( res => {
                if (res.code === '000'){
                  this.$message.success('录取成功');
                  this.getStatus();
                  this.getLogList();
                } else {
                  this.$message.error(res.message);
                }
              }).catch(err => {
                this.$message.error('系统错误')
              }).finally( () => {this.setUnloading()})
            },
            downloadResult(){
              this.downloadingResult = true;
              request({
                url: 'login/checkLogin'
              }).then( res => {
                if (res.code === '010'){
                  this.$router.push('/login');
                } else if (res.code === '000') {
                  request({
                    url: 'file/exportResult',
                    responseType: 'blob'
                  }).then((res) => {
                    if(res.code !== '000')
                      this.$message.error(res.message)
                    this.downloading = false;
                  });
                }
              })
            },
            downloadExitQueue(){
              this.downloadingExitQueue = true;
              request({
                url: 'login/checkLogin'
              }).then( res => {
                if (res.code === '010'){
                  this.$router.push('/login');
                } else if (res.code === '000') {
                  request({
                    url: 'file/exportExit',
                    responseType: 'blob'
                  }).then((res) => {
                    if(res.code !== '000')
                      this.$message.error(res.message)
                    this.downloading = false;
                  });
                }
              })
            },
            setUnloading(){
              this.loading.close();
            } ,
            setLoading(){
              this.loading = this.$loading({
                lock: true,
                text: 'Loading',
                spinner: 'el-icon-loading',
                background: 'rgba(0, 0, 0, 0.7)'
              });
            }

        }
    }
</script>

<style scoped lang="less">
    .el-menu.el-menu--horizontal{
      border-bottom: 0;

    }
    #back-stage-controller{
        min-height: 100%;

        background-color: rgba(9, 132, 217, .15);

        #back-stage-controller-header{
            height: 70px;
            background-color: rgb(9, 132, 217);
            position: fixed;
            z-index: 2000;
            width: 100%;
           #option-menu {
             float: left;

             //background-color: ;
           }
            #back-stage-header-text{
                text-align: center;
                width: 400px;
                padding-top: 10px;
                float: left;
                #back-stage-header-title{
                    color: #eee;
                    font-weight: bold;
                    font-size: 24px;
                    margin-bottom: 10px;
                }
                #back-stage-header-title-eng{
                    color: #eee;
                    font-weight: bold;
                }
            }



            #back-stage-controller-header-avatar{
                background: url("../../assets/img/avatar.png") center;
                -webkit-background-size: cover;
                background-size: cover;
                cursor: pointer;
                width: 50px;
                height: 50px;
                border-radius: 50%;
                position: absolute;
                right: 30px;
                top: 10px;
            }
        }
    }

        #back-stage-controller-main{
            width: 83%;
            padding: 20px;
            /*position: fixed;*/
            top: 80px;
            margin-top: 70px;
            float: right;
            right: 0;
        }
    #back-stage-controller-menu{
        width: 17%;
        min-height: 100%;
        float: left;
        background-color: #fff;
        border-right: 1px solid #c3e7ff;
        position: fixed;
        top: 70px;
    }

    #logout-link{
        text-align: center;
    }
</style>
