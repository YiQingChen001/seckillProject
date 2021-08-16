<template>
    <div class="create">
        <el-form :model="item" :rules="rules"  style="width:450px;margin:auto">
            <h3>创建商品</h3>
            <el-form-item label="商品名" prop="title">
                <el-input type="text" v-model="item.title" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="商品描述" prop="description">
                <el-input type="text" v-model="item.description" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="价格" prop="price">
                <el-input v-model.number="item.price" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="图片" prop="imgUrl">
                <el-input v-model="item.imgUrl" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="库存" prop="stock">
                <el-input v-model.number="item.stock" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item >
                <el-button type="primary" @click="onClick">提交创建</el-button>
            </el-form-item>

           
        </el-form>
    </div>
</template>

<script>
import qs from 'qs'
export default {
  name: 'create',
  data () {
    return{
        item:{
            title:'',
            description:'',
            price:'',
            imgUrl:'',
            stock:''
        },
        rules:{
            title:[{ required: true, message: '商品名不能为空',trigger: 'blur'}],
            description:[{ required: true, message: '描述不能为空',trigger: 'blur'}],
            imgUrl:[{ required: true, message: '图片地址不能为空',trigger: 'blur'}],
            price:[{ required: true, message: '价格不能为空',trigger: 'blur'}],
            stock:[{ required: true, message: '库存不能为空',trigger: 'blur'}],
        }
    }
  },

  methods:{
      onClick(){
          let params={
            title:this.item.title,
            description:this.item.description,
            price:this.item.price,
            imgUrl:this.item.imgUrl,
            stock:this.item.stock
          }
          
          this.$axios.post('http://localhost:8090/item/create',qs.stringify(params))
          .then(res=>{

              if(res.data.status=="success"){
                  alert("创建成功")
              }
              else{
                  alert("创建失败："+res.data.data.errorMessage)
              }
          })
          .catch(error=>{
              alert("创建失败")
          })
      }
  }
}
</script>

<style scoped>
  .create{
   border:solid;border-radius: 30px;
   width: 600px;   
   margin: auto;
}
</style>
