<template>
  <div class="tag">
    <el-card class="box-card">
      <div slot="header" class="d-flex align-items-center">
        <img class="card-icon" src="../assets/biaoqian.png">
        <span>关键词</span>
      </div>
      <div class="text item">
        <el-tag size="mini" class="tag-item" type="success" @click="tag('意义不明')">意义不明[2]</el-tag>
      </div>
    </el-card>
  </div>
</template>

<script>
export default {
  name: 'Tag',
  data() {
    return {
      profileInfo: {
        id: 1
      },
      cateList: [],
      typeList: ['warning', 'success', 'info', 'danger']
    }
  },
  created() {
    this.getProfileInfo()
    this.GetCateList()
  },
  methods: {
    tag(name) {
      this.$router.push({
        name: 'tag',
        params: {
          'name': name
        }
      })
    },
    // 获取个人设置
    async getProfileInfo() {
      const { data: res } = await this.$http.get(
        `profile/${this.profileInfo.id}`
      )
      this.profileInfo = res.data
      this.$root.$emit('msg', res.data.icp_record)
    },
    gotoCate(cid) {
      this.$router.push(`/category/${cid}`).catch((err) => err)
    },
    // 获取分类
    async GetCateList() {
      const { data: res } = await this.$http.get('category')
      this.cateList = res.data
    }
  }
}
</script>

<style scoped>
.box-card .item:hover {
  color: #409EFF;
  cursor: pointer;
}

.box-card span {
  font-weight: bold;
}

.card-icon {
  width: 20px;
  height: 20px;
  margin-right: 10px;
}

.tag-item {
  margin: 4px 2px;
}
</style>
