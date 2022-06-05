<template>
  <div class="app">
    <VHeader :class="{'navBarWrap':navBarFixed}" />
    <el-row id="content" type="flex" justify="center" class="app-content">
      <el-col :xs="20" :md="20" :style="{'minHeight':minHeight+'px'}">
        <router-view />
      </el-col>
    </el-row>
    <VFooter />
  </div>
</template>

<script>
import VHeader from '@/components/f-header'
import VFooter from '@/components/f-footer'
export default {
  name: 'Home',
  components: { VHeader, VFooter },
  data() {
    return {
      minHeight: 0,
      navBarFixed: false
    }
  },
  mounted() {
    const that = this
    that.minHeight = document.documentElement.clientHeight
    window.addEventListener('scroll', that.watchScroll)
    window.onresize = function() {
      that.minHeight = document.documentElement.clientHeight
    }
  },
  methods: {
    watchScroll() {
      var scrollTop = window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop
      //  当滚动超过 50 时，实现吸顶效果
      this.navBarFixed = scrollTop > 50
    }
  }
}
</script>

<style scoped>
.app{
  font-family: "microsoft yahei",serif;
}
.app-content{
  background-color: #f9f9f9;
  padding: 30px 0;
}
.navBarWrap {
  position:fixed;
  top:0;
  z-index:999;
  width: 100%;
}
</style>

