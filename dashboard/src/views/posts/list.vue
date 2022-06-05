<template>
  <div class="app-container">
    <el-table v-loading="listLoading" :data="list" border fit highlight-current-row style="width: 100%">
      <el-table-column align="center" label="序号" width="80">
        <template slot-scope="scope">
          <span v-if="scope">{{ scope.row.passageId }}</span>
        </template>
      </el-table-column>

      <el-table-column width="180px" align="center" label="发布时间">
        <template slot-scope="scope">
          <span v-if="scope">{{ scope.row.time | parseTime('{y}-{m}-{d} {h}:{i}') }}</span>
        </template>
      </el-table-column>

      <el-table-column width="120px" align="center" label="署名">
        <template slot-scope="scope">
          <span v-if="scope">{{ scope.row.userId }}</span>
        </template>
      </el-table-column>

      <el-table-column class-name="status-col" label="关键词" width="110">
        <template slot-scope="scope">
          <span v-if="scope">{{ scope.row.keyword }}</span>
        </template>
      </el-table-column>

      <el-table-column min-width="300px" label="标题">
        <template slot-scope="{row}">
          <router-link :to="'/dashboard/posts/edit/'+row.id" class="link-type">
            <span v-if="row">{{ row.title }}</span>
          </router-link>
        </template>
      </el-table-column>

      <el-table-column align="center" label="操作" width="380">
        <template slot-scope="scope">
          <div v-if="scope">
            <el-button type="danger" size="small" style="margin: 0px 5px" icon="el-icon-delete" @click="deletePost(scope.row.passageId)">
              删除
            </el-button>
            <router-link :to="'/dashboard/posts/commments-list/'+scope.row.passageId">
              <el-button type="secondary" size="small" style="margin: 0px 5px" icon="el-icon-news">
                评论
              </el-button>
            </router-link>
            <router-link :to="'/dashboard/posts/edit/'+scope.row.passageId">
              <el-button type="primary" size="small" style="margin: 0px 5px" icon="el-icon-edit">
                编辑
              </el-button>
            </router-link>
          </div>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="getList" />
  </div>
</template>

<script>
import Pagination from '@/components/Pagination' // Secondary package based on el-pagination

export default {
  name: 'ArticleList',
  components: { Pagination },
  filters: {
    statusFilter(status) {
      const statusMap = {
        published: 'success',
        draft: 'info',
        deleted: 'danger'
      }
      return statusMap[status]
    }
  },
  data() {
    return {
      list: null,
      total: 0,
      listLoading: true,
      listQuery: {
        page: 1,
        limit: 20
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.listLoading = true
      this.$axios.get('http://localhost:8080/ad/showPassagesAd').then(response => {
        this.list = response.data
        this.listLoading = false
        console.log(this.list)
      })
    },
    deletePost(id) {
      this.$axios.get('http://localhost:8080/ad/deletePassageAd', {
        params: {
          'passageId': id
        }
      }).then(response => {
        this.getList()
        this.$notify({
          title: '删除成功',
          message: '已删除文章',
          type: 'success',
          duration: 2000
        })
      })
    }
  }
}
</script>

<style scoped>
.edit-input {
  padding-right: 100px;
}
.cancel-btn {
  position: absolute;
  right: 15px;
  top: 10px;
}
</style>
