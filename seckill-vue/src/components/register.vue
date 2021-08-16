<template>
  <div class="register">
    <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm"
    style="width:450px;margin:auto">
    <el-form-item >
      <h3>注册界面</h3>
    </el-form-item>
    <el-form-item label="姓名" prop="username">
    <el-input type="text" v-model="ruleForm.username" autocomplete="off"></el-input>
  </el-form-item>

  <el-form-item label="性别" prop="gender">
    <el-input type="text" v-model="ruleForm.gender" autocomplete="off"></el-input>
  </el-form-item>

  <el-form-item label="年龄" prop="age">
    <el-input v-model.number="ruleForm.age" autocomplete="off"></el-input>
  </el-form-item>

  <el-form-item label="密码" prop="password">
    <el-input type="password" v-model="ruleForm.password" autocomplete="off"></el-input>
  </el-form-item>

    <el-form-item label="电话号码" prop="telphone">
    <el-input v-model="ruleForm.telphone" autocomplete="off"></el-input>
  </el-form-item>
    <el-form-item label="otp验证码" prop="otpCode">
    <el-input type="text" v-model="ruleForm.otpCode" autocomplete="off"></el-input>
  </el-form-item>

  <el-form-item>
    <el-button type="primary" @click="submitForm">注册</el-button>
    <el-button @click="resetForm('ruleForm')">重置</el-button>
  </el-form-item>
</el-form>
  </div>
</template>

<script>
import qs from 'qs'
export default {
  name: 'register',
  data() {
      return {
        ruleForm: {
          username:'',
          gender:'',
          telphone:'',
          otpCode:'',
          password: '',
          age: '',

        },
        rules: {
          username:[
            { required: true, message: '姓名不能为空',trigger: 'blur'},
          ],
          age:[
             { required: true, message: '年龄不能为空' ,trigger: 'blur'},
              { type: 'number', message: '年龄必须为数字值',trigger: 'blur'},
              // {min:1,max:100,message:'不能超过100',trigger: 'blur'}
          ],
          gender:[
            { required: true, message: '性别不能为空',trigger: 'blur'},
            // {enum:('男','女'),message:'性别只能填男或女',trigger: 'blur'}
          ],
          password:[
            { required: true, message: '密码不能为空',trigger: 'blur'},
          ],
          telphone:[
            { required: true, message: '电话号码不能为空',trigger: 'blur'},
            // { type: 'number', message: '电话号码必须为数字值',trigger: 'blur'},
            // {
            // pattern: /^1[3-9]\d{9}$/,
            // message: "手机号格式不对",
            // trigger: "blur"
            // }
          ],
          otpCode:[
            { required: true, message: 'otpcode不能为空',trigger: 'blur'},
          ],

        }
      };
    },
    methods: {
      submitForm(){

        let dataparams={
          "telphone":this.ruleForm.telphone,
          "otpCode":this.ruleForm.otpCode,
          "password":this.ruleForm.password,
          "age":this.ruleForm.age,
          "gender":this.ruleForm.gender,
          "username":this.ruleForm.username,
        }

        this.$axios.post(
          'http://localhost:8090/user/register',
          qs.stringify(dataparams),
          
          // {
          // telphone:this.telphone,
          // otpCode:this.otpCode,
          // password:this.password,
          // age:this.age,
          // gender:this.gender,
          // username:this.username,
          // }
        )
        .then(res=>{
          alert("注册成功");
        })
        .catch(error=>{
          alert("error:"+error.data.data.errorMessage);
        })
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
      }
    }

}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
 
   .register{
  border:solid;border-radius: 30px;
  width: 500px;
  margin: auto;
} 

</style>
