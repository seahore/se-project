<template>
  <div>
    <el-menu :default-active="activeIndex" class="d-flex" mode="horizontal" :router="true" type="flex">
      <div id="top-menu">
        <el-menu-item index="/" class="mr-auto">新闻</el-menu-item>
        <el-menu-item style="padding: 0"><el-input v-model="searchWord" placeholder="搜索内容" /></el-menu-item>
        <el-menu-item style="padding: 0"><router-link :to="'/search/'+searchWord"><el-button>搜索</el-button></router-link></el-menu-item>
        <el-menu-item index="/home">最新</el-menu-item>
        <el-menu-item index="/trend">热搜</el-menu-item>
        <el-menu-item index="/following">关注</el-menu-item>
        <el-menu-item index="/dashboard">后台</el-menu-item>
      </div>
    </el-menu>
  </div>
</template>

<script>
export default {
  props: {
    activeIndex: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      searchWord: ''
    }
  }
}
</script>

<style scoped>
#top-menu {
  display: flex;
  max-width: 1024px;
  width: 70%;
  margin-left: 15%;
}
</style>
