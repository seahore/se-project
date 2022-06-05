<template>
  <div class="friend">
    <el-card class="box-card">
      <div slot="header" class="d-flex align-items-center">
        <img class="card-icon" src="@/assets/lianjie.png">
        <span>发布人</span>
      </div>
      <div style="margin: 1em 0em" class="text item"><a href="#">{{ userId }}</a></div>
      <el-button v-if="!following" type="secondary" size="large" @click="follow()">关注</el-button>
      <el-button v-else type="primary" size="large" @click="follow()">取消关注</el-button>
    </el-card>
  </div>
</template>

<script>
export default {
  name: 'Introduction',
  props: {
    userId: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      following: false
    }
  },
  created() {
    this.following = this.$axios.get('http://localhost:8080/selectFollowUser').includes(this.userId)
  },
  methods: {
    follow() {
      if (!this.following) {
        this.following = true
        this.$axios.get('http://localhost:8080/followUser', {
          params: {
            user_id_followed: this.userId
          }
        })
      } else {
        this.following = false
        this.$axios.get('http://localhost:8080/deleteFollowUser', {
          params: {
            user_id_followed: this.userId
          }
        })
      }
    }
  }
}
</script>

<style scoped>
.box-card .item a{
	text-decoration: none;
	/*color: #F56C6C;*/
}
.box-card span{
	font-weight: bold;
}
.card-icon{
	width: 20px;
	height: 20px;
	margin-right: 10px;
}
</style>
