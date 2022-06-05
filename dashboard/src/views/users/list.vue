<template>
  <div class="app-container">
    <el-table v-loading="listLoading" :data="list" border fit highlight-current-row style="width: 100%">
      <el-table-column align="center" label="用户名" width="80">
        <template slot-scope="scope">
          <span v-if="scope">{{ scope.row.userId }}</span>
        </template>
      </el-table-column>

      <el-table-column width="180px" align="center" label="电话">
        <template slot-scope="scope">
          <span v-if="scope">{{ scope.row.phone }}</span>
        </template>
      </el-table-column>

      <el-table-column align="center" label="操作" width="120">
        <template slot-scope="scope">
          <div v-if="scope">
            <router-link :to="'/profile/'+scope.row.userId">
              <el-button style="margin: 0px 1em" type="secondary" size="small" icon="el-icon-edit">
                编辑
              </el-button>
            </router-link>
            <el-button style="margin: 0px 1em" type="primary" size="small" icon="el-icon-edit" @click="admit(scope.row.userId)">
              转正
            </el-button>
          </div>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
export default {
  name: 'UserList',
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
      listLoading: true
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.listLoading = true
      this.$axios.get('http://localhost:8080/ad/showUsersAd').then(response => {
        this.list = response.data
        this.listLoading = false
      })
    },
    admit(id) {
      this.$axios.get('http://localhost:8080/ad/admitUser', {
        params: {
          userId: id
        }
      }).then(response => {
        this.$message({
          message: '转正成功',
          type: 'success',
          showClose: true,
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
