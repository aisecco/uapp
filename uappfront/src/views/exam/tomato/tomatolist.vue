<template>
  <div class="app-container">
    <el-form :model="queryParam" ref="queryForm" :inline="true">
      <el-form-item label="TodoID：">
        <el-input v-model="queryParam.id" clearable></el-input>
      </el-form-item>
      <el-form-item label="水平：">
        <el-select v-model="queryParam.level" placeholder="水平" @change="levelChange" clearable>
          <el-option v-for="item in levelEnum" :key="item.key" :value="item.key" :label="item.value"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="Subject：">
        <el-select v-model="queryParam.subjectId" clearable>
          <el-option v-for="item in subjectFilter" :key="item.id" :value="item.id"
            :label="item.name + ' ( ' + item.levelName + ' )'"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm">查询</el-button>
        <router-link :to="{ path: 'tomatoedit' }" class="link-left">
          <el-button type="primary">新建</el-button>
        </router-link>
      </el-form-item>
    </el-form>
    <el-table v-loading="listLoading" :data="tableData" border fit highlight-current-row style="width: 100%">
      <el-table-column prop="id" label="Id" width="80px" />
      <el-table-column prop="subjectId" label="Subject" :formatter="subjectFormatter" width="120px" />
      <el-table-column prop="name" label="名称" />
      <el-table-column prop="createTime" label="创建时间" width="160px" />
      <el-table-column label="操作" align="center" width="250px">
        <template slot-scope="{row}">
          <el-button size="mini" @click="$router.push({ path: 'tomatoedit', query: { id: row.id } })">编辑</el-button>
          <el-button size="mini" @click="duplicate(row)" class="link-left">拷贝</el-button>
          <el-button size="mini" type="danger" @click="deletePaper(row)" class="link-left">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <pagination v-show="total > 0" :total="total" :page.sync="queryParam.pageIndex" :limit.sync="queryParam.pageSize"
      @pagination="search" />
  </div>
</template>

<script>
import { mapGetters, mapState, mapActions } from 'vuex'
import Pagination from '@/components/Pagination'
import tomatoApi from '@/api/tomato'

export default {
  components: { Pagination },
  data() {
    return {
      queryParam: {
        id: null,
        level: null,
        subjectId: null,
        pageIndex: 1,
        pageSize: 10
      },
      subjectFilter: null,
      listLoading: true,
      tableData: [],
      total: 0
    }
  },
  created() {
    this.initSubject()
    this.search()
  },
  methods: {
    submitForm() {
      this.queryParam.pageIndex = 1
      this.search()
    },
    search() {
      this.listLoading = true
      tomatoApi.pageList(this.queryParam).then(data => {
        const re = data.response
        this.tableData = re.list
        this.total = re.total
        this.queryParam.pageIndex = re.pageNum
        this.listLoading = false
      })
    },
    duplicate(row) {
      console.log('duplicate', row)
      let _this = this

      tomatoApi.duplicate(row.id).then(re => {
        if (re.code === 1) {
          _this.search()
          _this.$message.success(re.message)
        } else {
          _this.$message.error(re.message)
        }
      })
    },
    deletePaper(row) {
      console.log('删除', row)
      let _this = this

      this.$confirm('确认删除？', '提示框').then(() => {
        console.log('确认')

        tomatoApi.delete(row.id).then(re => {
          if (re.code === 1) {
            _this.search()
            _this.$message.success(re.message)
          } else {
            _this.$message.error(re.message)
          }
        })

      }).catch(() => {
        console.log('取消')
      })
    },
    levelChange() {
      this.queryParam.subjectId = null
      this.subjectFilter = this.subjects.filter(data => data.level === this.queryParam.level)
    },
    subjectFormatter(row, column, cellValue, index) {
      return this.subjectEnumFormat(cellValue)
    },
    ...mapActions('exam', { initSubject: 'initSubject' })
  },
  computed: {
    ...mapGetters('enumItem', ['enumFormat']),
    ...mapState('enumItem', {
      levelEnum: state => state.user.levelEnum
    }),
    ...mapGetters('exam', ['subjectEnumFormat']),
    ...mapState('exam', { subjects: state => state.subjects })
  }
}
</script>
