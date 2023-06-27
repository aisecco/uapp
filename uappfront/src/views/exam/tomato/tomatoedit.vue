<template>
  <div class="app-container">
    <el-form :model="form" ref="form" label-width="100px" v-loading="formLoading" :rules="rules">
      <el-form-item label="水平：" prop="level" required>
        <el-select v-model="form.level" placeholder="水平" @change="levelChange">
          <el-option v-for="item in levelEnum" :key="item.key" :value="item.key" :label="item.value"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="Subject：" prop="subjectId" required>
        <el-select v-model="form.subjectId" placeholder="Subject">
          <el-option v-for="item in subjectFilter" :key="item.id" :value="item.id"
            :label="item.name + ' ( ' + item.levelName + ' )'"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="方案类型：" prop="paperType" required>
        <el-select v-model="form.paperType" placeholder="方案类型">
          <el-option v-for="item in paperTypeEnum" :key="item.key" :value="item.key" :label="item.value"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="时间限制：" required v-show="form.paperType === 4">
        <el-date-picker v-model="form.limitDateTime" value-format="yyyy-MM-dd HH:mm:ss" type="datetimerange"
          range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="方案名称：" prop="name" required>
        <el-input v-model="form.name" />
      </el-form-item>
      <el-form-item label="text：" prop="text">
        <el-input type="textarea" v-model="form.text" />
      </el-form-item>
      <el-form-item label="textcn：" prop="textcn">
        <el-input type="textarea" v-model="form.textcn" />
      </el-form-item>
      <el-form-item label="player">
        <tomPlayer msg="ABH"></tomPlayer>
        <TomEditor ref="tomEditor" msg="editor"></TomEditor>
        <el-button type="text" class="link-left" style="margin-left: 20px" size="mini" @click="putvalue()">
          传入json
        </el-button>
        <el-button type="text" class="link-left" style="margin-left: 20px" size="mini" @click="getvalue()">
          获取json
        </el-button>
        <el-button type="text" class="link-left" style="margin-left: 20px" size="mini" @click="resetvalue()">
          resetjson
        </el-button>
      </el-form-item>
      <el-form-item label="subtitle">
        <el-input type="textarea" v-model="form.subtitle" />
      </el-form-item>
      <el-form-item label="src" prop="src" required>
        <el-input v-model="form.src" :value="form.src" />
        <el-button type="text" size="mini" @click='handleDialog(true)'>上传文件</el-button>
        <el-button type="text" size="mini" @click='handleTestPlay()'>校验文件</el-button>
        <el-button type="text" size="mini" @click='handleTestUseCurrent()'>使用当前文件</el-button>
      </el-form-item>
      <el-form-item :key="index" :label="'标题' + (index + 1) + '：'" required v-for="(titleItem, index) in form.titleItems">
        <el-input v-model="titleItem.name" style="width: 80%" />
        <el-button type="text" class="link-left" style="margin-left: 20px" size="mini" @click="addQuestion(titleItem)">
          新建库元
        </el-button>
        <el-button type="text" class="link-left" size="mini" @click="form.titleItems.splice(index, 1)">删除</el-button>
        <el-card class="exampaper-item-box" v-if="titleItem.questionItems.length !== 0">
          <el-form-item :key="questionIndex" :label="'库元' + (questionIndex + 1) + '：'"
            v-for="(questionItem, questionIndex) in titleItem.questionItems" style="margin-bottom: 15px">
            <el-row>
              <el-col :span="23">
                <QuestionShow :qType="questionItem.questionType" :question="questionItem" />
              </el-col>
              <el-col :span="1">
                <el-button type="text" size="mini" @click="titleItem.questionItems.splice(questionIndex, 1)">删除
                </el-button>
              </el-col>
            </el-row>
          </el-form-item>
        </el-card>
      </el-form-item>
      <el-form-item label="建议时长：" prop="suggestTime" required>
        <el-input v-model="form.suggestTime" value="15" placeholder="分钟" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm">提交</el-button>
        <el-button @click="resetForm">重置</el-button>
        <el-button type="success" @click="addTitle">新建标题</el-button>
      </el-form-item>
    </el-form>
    <el-dialog :visible.sync="questionPage.showDialog" width="70%">
      <el-form :model="questionPage.queryParam" ref="queryForm" :inline="true">
        <el-form-item label="ID：">
          <el-input v-model="questionPage.queryParam.id" clearable></el-input>
        </el-form-item>
        <el-form-item label="题型：">
          <el-select v-model="questionPage.queryParam.questionType" clearable>
            <el-option v-for="item in questionTypeEnum" :key="item.key" :value="item.key" :label="item.value"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="queryForm">查询</el-button>
        </el-form-item>
      </el-form>
      <el-table v-loading="questionPage.listLoading" :data="questionPage.tableData"
        @selection-change="handleSelectionChange" border fit highlight-current-row style="width: 100%">
        <el-table-column type="selection" width="35"></el-table-column>
        <el-table-column prop="id" label="Id" width="60px" />
        <el-table-column prop="questionType" label="题型" :formatter="questionTypeFormatter" width="70px" />
        <el-table-column prop="shortTitle" label="题干" show-overflow-tooltip />
      </el-table>
      <pagination v-show="questionPage.total > 0" :total="questionPage.total"
        :page.sync="questionPage.queryParam.pageIndex" :limit.sync="questionPage.queryParam.pageSize"
        @pagination="search" />
      <span slot="footer" class="dialog-footer">
        <el-button @click="questionPage.showDialog = false">取消</el-button>
        <el-button type="primary" @click="confirmQuestionSelect">确定</el-button>
      </span>
    </el-dialog>
    <el-dialog title="上传" :visible.sync="dialogOfUpload" :multiple="false" :showUploadList="false" width="35%"
      style="text-align: center;">
      <el-upload class="uploadfile" action="#" drag multiple :headers="headers" :auto-upload="false" :file-list="fileList"
        :on-change="handleChange">
        <i class="el-icon-upload"></i>
        <div class="el-upload_text">将文件拖到此处，或<em>点击选择文件</em></div>
        <!-- <div class="el-upload_tip" slot="tip">上传文件</div> -->
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogOfUpload = false">取消</el-button>
        <el-button type="primary" @click="confirmUpload()">上传</el-button>
      </div>
    </el-dialog>
    <el-dialog title="测试文件" :visible.sync="dialogOfTest" width="35%" style="text-align: center;">
      <audio :src="form.src" controls ref="audioOfTest" style='width: 100%; display: '></audio>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogOfTest = false">取消</el-button>
        <el-button type="primary" @click="confirmDownload()">download</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>

import { mapGetters, mapState, mapActions } from 'vuex'
import Pagination from '@/components/Pagination'
import TomPlayer from '@/components/TomPlayer'
import TomEditor from './components/TomEditor'
import QuestionShow from '../question/components/Show'
import tomatoApi from '@/api/tomato'

import questionApi from '@/api/question'

import axios from "axios";
let fileupUrl = 'http://localhost:8000/api/admin/upload/tomatofile?user=10000&basket=NCE2001'

export default {
  components: { Pagination, QuestionShow, TomPlayer, TomEditor },
  data() {
    return {
      allstop: false,
      currentChunk: 0,
      form: {
        id: null,
        level: null,
        subjectId: null,
        paperType: 1,
        limitDateTime: [],
        suggestTime: null,
        src: '',
        poster: '',
        name: '',
        author: '',
        title: '',
        subtitle: '',
        chunks: []
      },
      subjectFilter: null,
      formLoading: false,
      rules: {
        level: [
          { required: true, message: '请选择水平', trigger: 'change' }
        ],
        subjectId: [
          { required: true, message: '请选择Subject', trigger: 'change' }
        ],
        paperType: [
          { required: true, message: '请选择方案类型', trigger: 'change' }
        ],
        name: [
          { required: true, message: '请输入方案名称', trigger: 'blur' }
        ],
        suggestTime: [
          { required: true, message: '请输入建议时长', trigger: 'blur' }
        ]
      },
      questionPage: {
        multipleSelection: [],
        showDialog: false,
        queryParam: {
          id: null,
          questionType: null,
          subjectId: 1,
          pageIndex: 1,
          pageSize: 5
        },
        listLoading: true,
        tableData: [],
        total: 0
      },
      currentTitleItem: null,
      dialogOfUpload: false,
      fileList: [],
      headers: {
        'Content-Type': 'multipart/form-data'
      },
      dialogOfTest: false,
    }
  },
  created() {
    console.log(TomPlayer)
    let id = this.$route.query.id
    let _this = this
    this.initSubject(function () {
      _this.subjectFilter = _this.subjects
    })

    if (id && parseInt(id) !== 0) {
      _this.formLoading = true
      tomatoApi.select(id).then(re => {
        console.log("page created", re.response);
        _this.form = re.response

        _this.putvalue();

        _this.formLoading = false
      })
    }
  },
  methods: {
    putvalue() {
      console.log("put values to editor", this.form);
      this.$refs.tomEditor.tomato = this.form
      this.$refs.tomEditor.chunkInit();
    },
    getvalue() {

      this.form.chunks = this.$refs.tomEditor.tomato.chunks
      console.log("get values form editor", this.$refs.tomEditor.tomato);
    },
    resetvalue() {
      console.log("reset values form editor", this.$refs.tomEditor.tomato);
    },
    setSrc(value) {
      this.form.src = value
      console.log("set value to src", value);
    },
    handleDialogOfTest(open) {
      this.dialogOfTest = open
    },
    handleTestPlay() {
      this.form.src = this.form.src
      this.handleDialogOfTest(true);
    },
    confirmDownload() {
      let url = this.form.src
      console.log('test download url:', url)

      axios.post(url, { params: {}, responseType: 'blob' })
        .then((res) => {
          console.log('download ok')
          this.$message({
            message: "文件下载测试成功！",
            duration: 1000
          });
        })
        .catch((err) => {
          console.log('文件下载测试失败，请检查url')
          this.$message.error('文件下载测试失败，请检查url', url)
        })


    },
    handleTestUseCurrent() {
    },
    handleDialog(open) {
      this.dialogOfUpload = open
    },
    handleChange(file, fileList) {
      this.fileList = fileList; 54
    },
    confirmUpload() {
      var param = new FormData();
      this.fileList.forEach(
        (val, index) => {
          param.append("file", val.raw);
        }
      );
      axios.post(fileupUrl, param).then(resp => {
        this.returnJson = resp.data.response
        this.setSrc(this.returnJson)
        this.handleDialog()
      });

      this.$message({
        message: "上传成功！",
        duration: 1000
      });
    },
    submitForm() {
      let _this = this

      _this.getvalue();
      console.log(this.form)

      let listUrl = '/tomato/tomatolist'

      this.$refs.form.validate((valid) => {
        if (valid) {
          this.formLoading = true

          tomatoApi.edit(this.form).then(re => {
            if (re.code === 1) {
              _this.$message.success(re.message)
              _this.delCurrentView(_this).then(() => {
                _this.$router.push(listUrl)
              })
            } else {
              _this.$message.error(re.message)
              this.formLoading = false
            }
          }).catch(e => {
            this.formLoading = false
          })
        } else {
          return false
        }
      })
    },
    addTitle() {
      this.form.titleItems.push({
        name: '',
        questionItems: []
      })
    },
    addQuestion(titleItem) {
      this.currentTitleItem = titleItem
      this.questionPage.showDialog = true
      this.search()
    },
    removeTitleItem(titleItem) {
      this.form.titleItems.remove(titleItem)
    },
    removeQuestion(titleItem, questionItem) {
      titleItem.questionItems.remove(questionItem)
    },
    queryForm() {
      this.questionPage.queryParam.pageIndex = 1
      this.search()
    },
    confirmQuestionSelect() {
      let _this = this
      this.questionPage.multipleSelection.forEach(q => {
        questionApi.select(q.id).then(re => {
          _this.currentTitleItem.questionItems.push(re.response)
        })
      })
      this.questionPage.showDialog = false
    },
    levelChange() {
      this.form.subjectId = null
      this.subjectFilter = this.subjects.filter(data => data.level === this.form.level)
    },
    search() {
      this.questionPage.queryParam.subjectId = this.form.subjectId
      this.questionPage.listLoading = true
      questionApi.pageList(this.questionPage.queryParam).then(data => {
        const re = data.response
        this.questionPage.tableData = re.list
        this.questionPage.total = re.total
        this.questionPage.queryParam.pageIndex = re.pageNum
        this.questionPage.listLoading = false
      })
    },
    handleSelectionChange(val) {
      this.questionPage.multipleSelection = val
    },
    questionTypeFormatter(row, column, cellValue, index) {
      return this.enumFormat(this.questionTypeEnum, cellValue)
    },
    subjectFormatter(row, column, cellValue, index) {
      return this.subjectEnumFormat(cellValue)
    },
    resetForm() {
      let lastId = this.form.id
      this.$refs['form'].resetFields()
      this.form = {
        id: null,
        level: null,
        subjectId: null,
        paperType: 1,
        limitDateTime: [],
        name: '',
        text: '',
        textcn: '',
        suggestTime: null,
        titleItems: []
      }
      this.form.id = lastId
    },
    ...mapActions('exam', { initSubject: 'initSubject' }),
    ...mapActions('tagsView', { delCurrentView: 'delCurrentView' })
  },
  computed: {
    ...mapGetters('enumItem', ['enumFormat']),
    ...mapState('enumItem', {
      questionTypeEnum: state => state.exam.question.typeEnum,
      paperTypeEnum: state => state.exam.examPaper.paperTypeEnum,
      levelEnum: state => state.user.levelEnum
    }),
    ...mapState('exam', { subjects: state => state.subjects })
  }
}
</script>

<style lang="scss">
.exampaper-item-box {
  .q-title {
    margin: 0px 5px 0px 5px;
  }

  .q-item-content {}
}
</style>
