<template>
  <div class="app-container">
    <el-form :model="queryParam" ref="queryForm" :inline="true">
      <el-form-item label="发送者用户名：">
        <el-input v-model="queryParam.sendUserName"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm">查询</el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="listLoading" :data="tableData" border fit highlight-current-row style="width: 100%">
      <el-table-column prop="id" label="Id" width="100" />
      <el-table-column prop="title" label="标题" show-overflow-tooltip />
      <el-table-column prop="content" label="内容" show-overflow-tooltip />
      <el-table-column prop="sendUserName" label="发送人" width="100" />
      <el-table-column prop="receives" label="接收人" show-overflow-tooltip />
      <el-table-column prop="readCount" label="已读数" width="70" />
      <el-table-column prop="receiveUserCount" label="接收人数" width="100" />
      <el-table-column prop="createTime" label="创建时间" width="160px" />
    </el-table>
  </div>
</template>

<script>
import sfc2 from '@/views/uapp2/sfc2'

export default {
  data() {
    return {
      queryParam: {
        sendUserName: null,
        pageIndex: 1,
        pageSize: 10
      },
      listLoading: true,
      tableData: [{ 'id': '1000', 'title': 'test' }],
      total: 0
    }
  },
  created() {
    this.search()

    console.log('router!', this.$router, this.$router)

    let u1 = {
      path: '/uapp2/sfc2',
      component: () => sfc2,
      // component: () => import('@/views/uapp2/sfc'),
      name: 'sfc2',
      meta: { title: 'sfc2', noCache: true }
    }
    this.$router.options.routes.push(u1)
    // this.$router.options.routes.push({ path: '/abc' })
  },
  methods: {
    search() {
      this.listLoading = false

    },
    submitForm() {
      this.queryParam.pageIndex = 1
      this.search()
    }
  }
}
</script>
