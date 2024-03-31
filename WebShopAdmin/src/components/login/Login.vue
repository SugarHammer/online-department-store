<template>
    <div id="login-wrap" class="clearfix">
        <div id="login-bg">
            </div>
            <div id="login-bg-block-bottom" :class="{'block-active': blockActive}">

            </div>

            <div id="login-welcome" >
                <div id="welcome-system-name" v-text="$t('m.pinxixi')">

                </div>

            </div>

            <div id="login-form" class="container-fluid">
                <el-col :span="24">
                    <div id="login-form-avatar">
                    </div>
                    <div id="login-form-title" v-text="$t('m.Admin')">

                    </div>
                    <div id="login-form-name">
                        <el-input placeholder="用户名" v-model="username" ></el-input>
                    </div>
                    <div id="login-form-pass">
                        <el-input placeholder="密码" type="password" v-model="password" show-password
                                  @keyup.enter.native="doLogin"
                        ></el-input>
                    </div>


                    <div id="login-form-submit" class="clearfix">
                        <el-button type="primary" :loading="loading" @click="doLogin"  v-text="$t('m.signIn')"> </el-button>
                      <a class="btn btn-sm" @click="changeLangCN">中文</a>
                      <a class="btn btn-sm" @click="changeLangUS">English</a>
                    </div>

                </el-col>

            </div>

        </div>

    <!-- </div> -->
</template>

<script>
    import {request} from "../../network/request";

    export default {
        name: "Login",
        data(){
            return {
                blockActive: false,
                username: '',
                password: '',
                loading: false
            }
        },
        methods: {

          //国际化
          changeLangCN(){

                this.$i18n.locale = 'zh-CN';//切换中文

          },
          changeLangUS(){

               this.$i18n.locale = 'en-US';//切换英文

          },

            doLogin(){
                this.loading = true;
                request({
                    url: 'login/doLogin',
                    method: 'POST',
                    data: {
                        username: this.username,
                        password: this.password
                    }
                }).then ( res => {
                    if(res.code === '000'){
                        this.$router.push('/backStage/home')
                        this.$message.success('登陆成功');
                    } else {
                        this.$message.error(res.message);
                    }
                    this.loading = false;
                }).catch( err => {
                    this.$message.error('系统错误');
                    this.loading = false;
                })
            }
        },
        mounted() {
            setInterval(() => {
                this.blockActive = !this.blockActive;
            },5000);
            setTimeout( () => {
                this.blockActive = !this.blockActive;
            },200);
        }
    }
</script>

<style scoped lang="less">
    #login-wrap{
        background: url("../../assets/img/loginbg.jpg") no-repeat;
        background-size: cover;
        height: 100%;

        #login-bg{
            height: 100%;
            background-color: rgba(82,101,249,0.3);
            overflow: hidden;
            #login-bg-block-top{
                width: 2000px;
                height: 600px;
                background-color: rgba(204, 229, 249, .9);
                transform: rotate(15deg) translate(-300px, -375px);
                transition: 5s;
                &.block-active{
                    transform: rotate(15deg) translate(0, -425px);
                }
            }
            #login-bg-block-bottom{
                width: 2000px;
                height: 600px;
                background-color: rgba(204, 229, 249, .9);
                transform: rotate(15deg) translate(-600px, 225px);
                transition: 5s;
                &.block-active{
                    transform: rotate(15deg) translate(-600px, 175px);
                }
            }
        }
    }

    #login-welcome{
        position: absolute;
        top: 20%;
        left: 39%;
        text-align: center;
        font-weight: bold;
        text-shadow: 5px 5px 5px #777;
        #welcome-system-name{
            font-size: 56px;
            color: #eee;
            margin-bottom: 20px;
        }
        #welcome-system-name-eng{
            font-size: 24px;
            color: #eee;
        }
    }

    #login-form{
        height: 290px;
        width: 300px;
        padding: 40px 15px;
        border-radius: 5px;
        background-color: rgba(255,255,255,.8);
        box-shadow: 0 0 15px #666;

        position: absolute;
        top: 40%;
        left: 43%;

        #login-form-avatar{
            background: url("../../assets/img/avatar.png") center ;
            background-size: cover;
            border-radius: 50%;
            width: 100px;
            height: 100px;
            position: absolute;
            top: -50px;
            left: 100px;
        }
        #login-form-title{
            font-size: 24px;
            text-align: center;
            color: dodgerblue;
            margin: 25px;
            font-weight: bold;
        }

        #login-form-name, #login-form-pass, #login-form-submit{
            margin: 15px;
        }

        #login-form-submit .el-button{
            width: 240px;
        }
    }
</style>
