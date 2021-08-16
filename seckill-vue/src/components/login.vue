<template>
    <div class="login" >
        <el-form :model="ruleForm" :rules="rules" class="loginForm" 
        label-width="100px" style="width:400px;margin:auto" >
          <h3>登录界面</h3>
          <el-form-item label="电话号码" prop="telphone">
              <el-input v-model="ruleForm.telphone" autocomplete="off"></el-input>
          </el-form-item>

          <el-form-item label="密码" prop="password">
            <el-input type="password" v-model="ruleForm.password" autocomplete="off"></el-input>
          </el-form-item>

          <el-form-item>
            <el-button type="primary" @click="login">登录</el-button>
            <el-button @click="toRegister">注册</el-button>
          </el-form-item>
        </el-form>
    </div>
</template>

<script>
import qs from 'qs'

export default {
  name: 'login',
  data () {
    return {
      ruleForm:{
        telphone:'',
        password:''
      },
          rules:{
            telphone:[{ required: true, message: '电话号码不能为空',trigger: 'blur'}],
            password:[{ required: true, message: '密码不能为空',trigger: 'blur'}]

    }
    }
  },

  methods: {
    login(){
      let params={
        "telphone":this.ruleForm.telphone,
        "password":this.ruleForm.password,
      }
      this.$axios.post('http://localhost:8090/user/login',qs.stringify(params))
      .then(res=>{
        if(res.data.status=="success"){
          alert("登录成功")
          this.$router.push({name:'listitem'})
        }
        else{
          alert("登录失败，"+res.data.data.errorMessage)
        }
      })
      .catch(error=>{
        alert("登录失败")
      })
    },
    toRegister(){
      this.$router.push('/getotp');
    }
  }
}
</script>


<style scoped>
.login{
  border:solid;border-radius: 30px;
  margin: auto;
  width: 500px;
  /*
  background: url(../assets/main.jpg);
  background-size:100% 100%; */
}

</style>
