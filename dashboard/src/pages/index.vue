<template>
  <div>
    <VHeader :active-index="'/home'" :class="{'navBarWrap':navBarFixed}" />
    <el-row id="content" type="flex" justify="center" class="app-content">
      <el-col :xs="20" :md="20" :style="{'minHeight':minHeight+'px'}">
        <div class="home">
          <el-row id="posts" type="flex" justify="space-around">
            <el-col :span="16">
              <PostItem v-for="(p, index) in posts" :key="index" :passage="p" />
            </el-col>
            <el-col id="side" :span="6" class="hidden-sm-and-down">
              <div class="item">
                <tag />
              </div>
            </el-col>
          </el-row>
        </div>
      </el-col>
    </el-row>
    <VFooter />
  </div>
</template>

<script>
import VHeader from '@/components/f-header'
import VFooter from '@/components/f-footer'
import tag from '@/components/tag'
import PostItem from './components/PostItem'

const defaultPassage = {
  id: 0,
  title: '',
  time: 0,
  keywords: [],
  abstract: '',
  viewCount: 0,
  goodCount: 0,
  comments: [],
  topped: false
}
export default {
  name: 'Index',
  components: { tag, PostItem, VHeader, VFooter },
  data() {
    return {
      posts: [],

      minHeight: 0,
      navBarFixed: false
    }
  },
  mounted() {
    const that = this
    that.minHeight = document.documentElement.clientHeight
    window.addEventListener('scroll', that.watchScroll)
    window.onresize = () => {
      that.minHeight = document.documentElement.clientHeight
    }
    this.getArtList()
  },
  methods: {
    // 获取文章列表
    async getArtList() {
      const { data: res } = await this.$axios.get('http://localhost:8080/selectLatestPassage').then(response => {
        response.data.forEach(p => {
          const tempPassage = Object.assign({}, defaultPassage)
          tempPassage.id = p.passageId
          tempPassage.title = p.title
          tempPassage.time = p.time
          tempPassage.keywords = p.keyword.split(',')
          tempPassage.abstract = p.content.substr(0, 100)
          tempPassage.viewCount = 0
          tempPassage.goodCount = p.goodCount
          tempPassage.comments = p.comments
          tempPassage.topped = true
          this.posts.push(tempPassage)
        })
      })
      this.total = res.total
      // eslint-disable-next-line no-console
      console.log(this.posts)
    },
    watchScroll() {
      var scrollTop = window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop
      //  当滚动超过 50 时，实现吸顶效果
      this.navBarFixed = scrollTop > 50
    }
  }
}
</script>

<style scoped>
#side .item {
  margin-bottom: 30px;
}

.art-item {
  margin-bottom: 30px;
  position: relative;
}

.art-item .star {
  width: 60px;
  height: 60px;
  position: absolute;
  top: 0;
  right: 0;
}

img.tag {
  width: 16px;
  height: 16px;
}

.art-title {
  border-left: 3px solid #F56C6C;
  padding-left: 5px;
  cursor: pointer;
}

.art-title:hover {
  padding-left: 10px;
  color: #409EFF;
}

.art-time {
  margin-right: 20px;
}

.art-body {
  display: flex;
  padding: 10px 0;
}

.side-img {
  height: 150px;
  width: 270px;
  overflow: hidden;
  margin-right: 10px;
}

img.art-banner {
  width: 100%;
  height: 100%;
  transition: all 0.6s;
}

img.art-banner:hover {
  transform: scale(1.4);
}

.side-abstract {
  flex: 1;
  display: flex;
  flex-direction: column;
}

.art-abstract {
  flex: 1;
  color: #aaa;
}

.art-more {
  height: 40px;
  display: flex;
  justify-content: space-between;
  align-items: flex-end;
}

.art-more .view {
  color: #aaa;
}
h5{
  font-size: 18px;
}
.pagination {
  background-color: #F9F9F9;
}

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
