<template>
  <div class="item">
    <h3 style="width: 500px; text-align: center; margin-bottom: 30px;">个人信息</h3>
    <el-form label-width="100px" label-position="left">
      <el-form-item label="昵称：">
        <el-input v-model="form.name"></el-input>
      </el-form-item>
      <el-form-item label="联系方式：">
        <el-input v-model="form.phone"></el-input>
      </el-form-item>
      <el-form-item label="电子邮箱：">
        <el-input v-model="form.email"></el-input>
      </el-form-item>
      <el-form-item label="收获地址：">
        <el-input v-model="form.address"></el-input>
      </el-form-item>
      <el-form-item label="修改密码：">
        <el-input v-model="form.password" type="password"></el-input>
      </el-form-item>
      <el-form-item style="text-align: right;">
        <el-button type="primary" @click="onSave">保存</el-button>
        <el-button type="danger" @click="onExit">退出登陆</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      form: {
        name: '',
        phone: '',
        address: '',
        password: '',
        email:''
      }
    }
  },
  methods: {
    // 保存用户信息
    onSave() {

      let temp = {
        name: this.form.name,
        phone: this.form.phone,
        address: this.form.address,
        New_Password : this.form.password,
        email:this.form.email
      };
      localStorage.setItem('relation', JSON.stringify(temp));

      let user = JSON.parse(localStorage.getItem('user'));
      console.log(user);
      let pic = user.picUrl;
      let id = user.id;

      console.log('图片地址为'+pic)
      this.$axios.get('/user/update', {
          params: {
              userId:id,
              username:temp.name,
              password:temp.New_Password,
              picUrl:pic,
              address:temp.address,
              phone:temp.phone,
              email:temp.email
          }
      })
      .then(function (response) {
          console.log(response);
      })
      .catch(function (error) {
          console.log(error);
      });

      // if (this.form.password != '') {
      //     this.$axios.post('/modct_Password', {
      //         ct_UserID: user_id,
      //         ct_Password: this.form.password
      //     })
      //     .then(function (response) {
      //         console.log(response);
      //     })
      //     .catch(function (error) {
      //         console.log(error);
      //     });
      // }

      alert('保存成功！');
      this.form.password = '';
    },
    // 退出登录
    onExit() {
      localStorage.removeItem('user');
      // 跳转到首页
      this.$router.push('/');
      // 刷新页面
      window.location.reload(true);
    }
  },
  created() {
    // 获取用户信息
    var that = this;
    var user_id = JSON.parse(localStorage.getItem('user')).id;
    var user_password = JSON.parse(localStorage.getItem('user')).password;
    var User_src = "/user/login?userId="+user_id+"&password="+user_password;
    this.$axios.get(User_src, {
    })
        .then(function (response) {
          console.log(response.data);
          console.log(response.data.data.phone);
          that.form.address=response.data.data.address;
          that.form.name=response.data.data.username;
          that.form.password=response.data.data.password;
          that.form.phone=response.data.data.phone;
          that.form.email=response.data.data.email;
        })
        .catch(function (error) {
          console.log(error);
        });
    //that.form = JSON.parse(localStorage.getItem('user'));
  },
}
</script>

<style scoped>
.item {
  width: 500px;
  background-color: white;
  padding: 10px 200px 20px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);
  margin: 0 auto;
  margin-bottom: 50px;
  margin-top: 50px;
}
</style>