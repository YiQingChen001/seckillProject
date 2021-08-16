<template>
    <div class="listitem">
      <h3 >商品信息浏览</h3>
        <el-table
      :data="tableData"
      style="width: 100%"
      ref="items"
      @row-click="handler">
      <el-table-column
        prop="id"
        label="商品号"
        width="100">
      </el-table-column>
      <el-table-column
        prop="title"
        label="商品名"
        width="150">
      </el-table-column>

      <el-table-column
        prop="imgUrl"
        label="图片"
        width="200">
        <!-- 在表格中显示图片加入一下代码即可 -->
        <template slot-scope="scope">
               <img :src="scope.row.imgUrl" min-width="100" height="100"/>
       </template>
      </el-table-column>

      <el-table-column
        prop="price"
        label="价格"
        width="100">
      </el-table-column>

      <el-table-column
        prop="stock"
        label="库存"
        width="100">
      </el-table-column>
      <el-table-column
        prop="description"
        label="描述"
        >
      </el-table-column>
    </el-table>
    </div>
</template>

<script>
import qs from 'qs'
export default {
  name: 'listitem',
  data () {
    return {
        tableData:[],
        id:0
    }
    
  },
  created () {
        this.getlist()
        
    },
    // updated () {
    //   this.getlist()
    // },
    methods: {
        
        
        getlist(){
          
            this.$http.get('http://localhost:8090/item/list')
            .then(res=>{
                let itemdata=res.data.data
                // let itemdataparse=qs.parse(res)

                if(res.data.status=="success"){
                    // console.log(itemdataparse.body)
                    for(let n in itemdata){
                        this.tableData.push(itemdata[n])
                    }
                }
                else{
                    alert("error")
                }
               
            })

            
        },
        handler(tableData){
              
             this.$router.push({name:'getitem',params:{id:tableData.id}})
        }


    }
}
</script>
<style scoped>
.listitem{
    width: 1000px;
    margin: auto;
}

</style>
