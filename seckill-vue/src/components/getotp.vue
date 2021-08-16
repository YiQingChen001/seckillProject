<template>
  <div class="getotp" >
    <div style="display: flex;justify-content: center;margin-top: 150px">
      <el-card style="width: 400px" >
        <div slot="header" class="clearfix">
          <span>获取otp信息</span>
        </div>
        <table>
          <tr>
            <td>电话号</td>
            <td>
              <el-input 
              v-model="telphone" 
              placeholder="请输入电话号码"
               ></el-input>
            </td>
          </tr>
          <tr>
            <!-- 占两行-->
            <td colspan="2">
              <!-- 点击事件的两种不同的写法v-on:click和 @click-->
              <!--<el-button style="width: 300px" type="primary" v-on:click="doLogin">登录</el-button>-->
              <el-button style="width: 120px" type="primary" @click="doGetOtp">获取otp信息</el-button>
            </td>
          </tr>
        </table>
      </el-card>
    </div>
  </div>
</template>

<script>
import qs from 'qs'
export default {
  name: 'getotp',
   data() {
      return{
        telphone:''
      }
    },
    methods:{
      doGetOtp(){//一点击登录按钮，这个方法就会执行
      
        var tel=this.telphone;

        if(tel==null||tel=="")alert("电话号码不能为空")
        else{
            // axios({
            //     url:"localhost:8090/user/getotp",
            //     methods:'post',
            //     data:{
            //         telphone:this.telphone
            //     }
            // }).then(res=>{
            //     alert("success");
            // }).catch(err=>{
            //     alert("error");
            // })
            let params={
              "telphone":this.telphone
            }
            this.$axios.post('http://localhost:8090/user/getotp',qs.stringify(params)
            ).then(res=>{

              alert("验证码已经发送到手机，请注意查看！")
               this.$router.push({path:'/register'});
               
            })
            .catch(error=>{
              alert("error")
            })
            
        }
      }
    }

}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
