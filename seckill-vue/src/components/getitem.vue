<template>
    <div class="getitem">

    <el-descriptions title="商品详情" :column="1" :size="medium" border class="des">
        <template slot="extra">
      <el-button  size="small" type="primary" @click="tolistitem">返回</el-button>
        </template>
    <el-descriptions-item label="商品名" >{{item.title}}</el-descriptions-item>
    <el-descriptions-item  label="秒杀活动" label-class-name="my-label" content-class-name="my-content">
      <!-- 开始时间：{{item.promostartdate}}<br>结束时间：{{item.promoenddate}}<br>{{item.countdowntime}}</el-descriptions-item> -->
      <pre>{{item.promo}}</pre></el-descriptions-item>
    <el-descriptions-item label="图片">
        <template><img :src=item.imgUrl min-width="100" height="100"></template>
    </el-descriptions-item>
    <el-descriptions-item label="价格">{{item.price}}</el-descriptions-item>
    <el-descriptions-item label="秒杀价格" label-class-name="my-label" content-class-name="my-content">{{item.promoprice}}</el-descriptions-item>
    <el-descriptions-item label="库存">{{item.stock}}</el-descriptions-item>
    <el-descriptions-item label="销量">{{item.sales}}</el-descriptions-item>
    <el-descriptions-item label="描述">{{item.description}}</el-descriptions-item>

</el-descriptions>
<br>
<br>
<template >
      <el-button type="primary" size="medium" @click="purchase" :disabled="sendDisabled">下单</el-button>
        </template>
    </div>
</template>

<script>
import qs from 'qs'
import moment from "moment";
export default {
  name: 'getitem',
  data () {
    var promoid;
    return {

        item:{
            title:'',
            imgUrl:'',
            price:'',
            stock:'',
            sales:'',
            description:'',
            promoprice:'',
            promo:''
        }

    }

  },
  created () {
      this.getitem()
  },
  mounted(){
    this.getitem()
  },
  methods:{
      tolistitem(){
           this.$router.push({name:'listitem'})
      },
      getitem(){

          let dataparams={
          "id":this.$route.params.id,
        }

          this.$axios.post('http://localhost:8090/item/get',qs.stringify(dataparams))
          .then(res=>{

            let itemdata=res.data.data
            this.item.title=itemdata.title;
            this.item.imgUrl=itemdata.imgUrl;
            this.item.price=itemdata.price;
            this.item.stock=itemdata.stock;
            this.item.sales=itemdata.sales;
            this.item.description=itemdata.description;
            this.promoid=itemdata.promoId;

            if(itemdata.promoStatus!=0){
              this.item.promoprice=itemdata.promoPrice
             let timer = setInterval(() => {
                 let countdowntime = this.gettime(itemdata.startDate,itemdata.endDate);
                this.item.promo="开始时间："+itemdata.startDate+"\n"+"结束时间："+itemdata.endDate+"\n"+countdowntime
            }, 100);
            }else{
              this.sendDisabled=false;//设置按钮可用
              this.item.promo="该商品暂无促销活动"
            }
          })
          .catch(error=>{
            alert("error")
          })
      },
      gettime(starttime,endtime){//倒计时结束时间
        let seconds = moment(starttime).diff(new Date, 'seconds');
        let endseconds = moment(endtime).diff(new Date, 'seconds');
        if(seconds<=0){
          if(endseconds<=0)return "秒杀活动已结束"
          else {
            this.sendDisabled=false;//设置按钮可用
            return "秒杀活动已开始";
          }
        }else{
          this.sendDisabled=true;//设置按钮不可用
          let second = seconds%60;
            let minutes = (seconds-second) / 60;
            let minute = minutes%60;
            let hours = (minutes-minute) / 60;
            let hour = hours%24;
            let day = (hours-hour) / 24;
            return "倒计时：" + day + '天 '+ hour + '小时 '+ minute + '分钟 '+ second + '秒 ';
        }

      },
      purchase(){

           let params={
              itemId:this.$route.params.id,
              amount:'1',
              promoId:this.promoid
          }
          this.$axios.post('http://localhost:8090/order/create',qs.stringify(params))
          .then(res=>{
              if(res.data.status=="success"){
                alert("下单成功")
                this.getitem()
              }
              else{
                  alert("下单失败，"+res.data.data.errorMessage)
              }
          })
          .catch(error=>{
              alert("error")
          })

        }



  }
}
</script>


<style scoped>
.getitem{
    width: 500px;
    margin: auto;
}

.getitem >>> .my-label {
    color: red ;

  }

.getitem >>> .my-content {
    color: red ;
  }


</style>


