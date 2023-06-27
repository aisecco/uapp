<template>
  <div class="app-container">
    <el-button @click='handleOpenDialog'>打开Dialog弹框</el-button>
    <div>{{ returnJson }}</div>
    <el-dialog title='外部表格弹窗' center :visible.sync='outVisible' :before-close='outClose'>
      <el-table :data='gridData'>
        <el-table-column prop='date' label='日期'></el-table-column>
        <el-table-column prop='name' label='姓名'></el-table-column>
        <el-table-column prop='address' label='地址'></el-table-column>
        <el-table-column label='操作'>
          <template slot-scope='scope'>
            <el-button type='warning' size='mini' @click='handleEdit(scope.row)'>修改</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-dialog title='内部表单弹窗' center :visible.sync='innerVisible' :before-close='outClose' append-to-body>
        <el-form :model='formData' label-width='80px'>
          <el-form-item label='日期'>
            <el-input v-model='formData.date'></el-input>
          </el-form-item>
          <el-form-item label='姓名'>
            <el-input v-model='formData.name'></el-input>
          </el-form-item>
          <el-form-item label='地址'>
            <el-input v-model='formData.address'></el-input>
          </el-form-item>
        </el-form>
        <span slot='footer'>
          <el-button @click='innerVisible = false'>返回</el-button>
        </span>
      </el-dialog>
      <span slot='footer'>
        <el-button @click='outVisible = false'>返回</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "uploadFile",
  props: {
    uploadParams: Object,
    paramsData: Object
  },
  data() {
    return {
      returnJson: 'text',
      outVisible: false,
      innerVisible: false,
      formData: { date: '', name: '', address: '' },
      gridData: [{
        date: '2016-05-02',
        name: '张三',
        address: '浙江省杭州西湖区'
      }, {
        date: '2016-05-04',
        name: '李四',
        address: '浙江省嵊州市谷来镇'
      }, {
        date: '2016-05-01',
        name: '王五',
        address: '上海市外滩路129号'
      }, {
        date: '2016-05-03',
        name: '赵六',
        address: '北京海淀区西二旗中关村软件园'
      }],
    }
  },
  created() { },
  methods: {
    handleOpenDialog() {
      this.outVisible = true
    },
    outClose(done) {
      this.$confirm('确认关闭', '提示框').then(() => {
        done()

        this.returnJson = '确认按钮'
        console.log('确认按钮')
      }).catch(() => {

        this.returnJson = '取消按钮'
        console.log('取消按钮')
      })
    },
    handleEdit(row) {
      this.formData = row
      this.innerVisible = true
    },
  }
}
</script>