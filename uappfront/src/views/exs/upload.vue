<template>
  <div id='app'>
    <el-button @click='handleDialog(true)'>打开Dialog弹框</el-button>
    <div>{{ returnJson }}</div>
    <el-dialog title="上传" :visible.sync="dialogOfUpload" width="35%" style="text-align: center;">
      <el-upload class="upload-demo" action="#" drag :multiple="false" :showUploadList="false" :headers="headers"
        :auto-upload="false" :file-list="fileList" :on-change="handleChange">
        <i class="el-icon-upload"></i>
        <div class="el-upload_text">将文件拖到此处，或<em>点击上传2</em></div>
        <div class="el-upload_tip" slot="tip">上传Excel格式文件</div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogOfUpload = false">取 消</el-button>
        <el-button type="primary" @click="confirmUpload()">上 传</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>

import axios from 'axios'
let fileupUrl = 'http://localhost:8000/api/admin/upload/tomatofile?user=10000&basket=NCE2001'
export default {
  name: 'uploadFile',
  props: {
    uploadParams: Object,
    paramsData: Object
  },
  data() {
    return {
      outVisible: false,
      innerVisible: false,
      dialogOfUpload: false,
      fileList: [],
      headers: {
        'Content-Type': 'multipart/form-data'
      },
      returnJson: '返回值'
    }
  },
  created() { },
  methods: {
    handleDialog(open) {
      this.dialogOfUpload = open
    },
    handleChange(file, fileList) {
      this.fileList = fileList
    },
    confirmUpload() {
      var param = new FormData()
      this.fileList.forEach(
        (val, index) => {
          param.append('file', val.raw)
        }
      )
      axios.post(fileupUrl, param).then(resp => {
        let d = resp.data
        // console.log(d)
        // this.returnJson = JSON.stringify(d.response)
        this.returnJson = d.response
        this.handleDialog()
      })
      this.$message({
        message: '上传成功！',
        duration: 1000
      })
    }
  }
}
</script>
