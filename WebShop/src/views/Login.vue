<template>
  <div class="login">
    <el-form size="medium" :inline="true" :label-position="labelPosition" label-width="60px" :model="form">
      <el-form-item label="账号">
        <el-input v-model="form.user"></el-input>
      </el-form-item>
      <br>
      <el-form-item label="密码">
        <el-input v-model="form.password" type="password"></el-input>
      </el-form-item>
      <br>
      <drag-verify :width="drag.width"
                   :height="drag.height"
                   :text="drag.text"
                   :success-text="drag.successText"
                   :text-size="drag.textSize"
                   class="darg"
                   @passcallback="verified = true">
      </drag-verify>
      <el-form-item class="button">
        <el-button type="warning" @click="onAdminLogin" size="small" :disabled="!verified">商家登陆</el-button>
        <el-button type="primary" @click="dialogVisible=true" size="small" :disabled="!verified">注册</el-button>
        <el-button type="primary" @click="onLogin" size="small" :disabled="!verified">登陆</el-button>
      </el-form-item>
    </el-form>

    <!-- 注册用户的对话框 -->
    <el-dialog
        title="注册用户"
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
                <el-button type="primary" @click="onRegister">确 定</el-button>
            </span>
    </el-dialog>
  </div>
</template>

<script>
import dragVerify from 'vue-drag-verify';

export default {
  data() {
    return {
      drag: {
        width: 300,
        height: 48,
        text: "请拖动到右侧进行验证",
        successText: "验证成功",
        textSize: '16px',
      },

      labelPosition: 'left',
      form: {
        user: '',
        password: ''
      },

      verified: false,
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
      }
    }
  },
  components: {
    dragVerify
  },
  methods: {
    onAdminLogin() {
      //var that = this;
      this.$message.success('跳转成功!');
      var url = 'http://localhost:8000/#/login';
      window.location.href = url;
      // this.$axios.get(url, {
      //     username: this.form.user,
      //     password: this.form.password
      // })
      // .then(function (response) {
      //     console.log(response);
      //     if (response.data.status == 200) {
      //         that.$router.push('/admin');
      //     } else {
      //         alert('账号或密码错误！');
      //         // 刷新页面
      //         window.location.reload(true);
      //     }
      // })
      // .catch(function (error) {
      //     console.log(error);
      // });
    },
    onLogin() {
      var that = this;
      var url = '/user/doLogin?username='+this.form.user+'&password='+this.form.password
      this.$axios.get(url, {
        username: this.form.user,
        password: this.form.password
      })
          .then(function (response) {
            if (response.data.code == '000') {

              that.$message.success('登录成功!');

              let temp = {
                id: response.data.data.userId,
                username: response.data.data.username,
                password: response.data.data.password,
                picUrl: response.data.data.picUrl
              };
              // alert("登录用户为:");
              // alert(temp.id);
              // alert(temp.username);
              // alert(temp.password);
              // alert(temp.picUrl);
              localStorage.setItem('user', JSON.stringify(temp));
              that.$router.push('/');
              // 刷新页面
              window.location.reload(true);
            } else {
              that.$message.error('账号或密码错误！');
              // 刷新页面
              window.location.reload(true);
            }
          })
          .catch(function (error) {
            console.log(error);
          });
    },
    onRegister() {
      var that = this;
      if(this.addForm.password != this.addForm.surePassword){
        this.$message.error('两次输入的密码不同,请重新输入!')
      }
      else{
        var url = '/user/register?username='+this.addForm.username+'&password='+this.addForm.password
            +'&picUrl='+this.addForm.picUrl+'&address='+this.addForm.address+
            '&phone='+this.addForm.phone+'&email='+this.addForm.email
        this.$axios.get(url, {
          username:this.addForm.username,
          password:this.addForm.password,
          picUrl:this.addForm.picUrl,
          address:this.addForm.address,
          phone:this.addForm.phone,
          email:this.addForm.email
        })
            .then(function (response) {
              if (response.data.code == '000') {
                //alert('注册成功，请登陆！');
                that.$message.success('注册成功，请登陆！');
                // 刷新页面
                window.location.reload(true);
              } else {
                //alert('账号已存在，请登陆账号或修改账号重新注册！');
                that.$message.error('账号已存在，请登陆账号或修改账号重新注册!')
                // 刷新页面
                window.location.reload(true);
              }
            })
            .catch(function (error) {
              console.log(error);
            });
      }

    }
  }
}
</script>

<style scoped>
.login {
  width: 400px;
  height: 200px;
  margin: 0 auto;
  margin-top: 80px;
  margin-bottom: 150px;
  text-align: center;
  padding: 40px;

  box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);
  border-radius: 10px;
}

.darg {
  margin: 0 auto;
  margin-bottom: 20px;
}

.button {
  float: right;
  margin-right: 75px !important;
}
</style>
