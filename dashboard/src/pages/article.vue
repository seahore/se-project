<template>
  <div class="app">
    <VHeader :class="{'navBarWrap':navBarFixed}" />
    <el-row id="content" type="flex" justify="center" class="app-content">
      <el-col :xs="20" :md="20" :style="{'minHeight':minHeight+'px'}">
        <div>
          <el-row class="main" type="flex" justify="space-around">
            <el-col :span="16">
              <div id="artcle-info">
                <h2 class="text-center"><strong>{{ passage.title }}</strong></h2>
                <div class="text-center timeAndView">
                  <span class="article-time">
                    <i class="el-icon-time" />
                    发表于：<span>{{ passage.time }}</span>
                  </span>
                  &nbsp;|&nbsp;
                  <span class="article-views">
                    <i class="el-icon-view" />
                    阅读量：<span>1</span>万
                  </span>
                </div>
              </div>
              <hr>
              <div id="artcle-content">
                {{ passage.content }}
              </div>
              <div id="statement">
                <div class="item">作者：{{ passage.userId }}</div>
                <div class="item">转载本站所有新闻内容，除有特别声明者外，敬请注明出处。</div>
                <el-button v-if="!good" type="secondary" size="large" style="margin: 10px" icon="el-icon-arrow-up" @click="switchGood()">
                  点赞
                </el-button>
                <el-button v-else type="primary" size="large" style="margin: 10px" icon="el-icon-arrow-down" @click="switchGood()">
                  取消点赞
                </el-button>
              </div>
              <div id="comment" style="margin: 1em 0em">
                <el-input v-model="comment" type="textarea" :rows="2" placeholder="请输入内容" />
                <el-button type="secondary" size="large" style="margin: 10px" @click="sendComment()">
                  发表评论
                </el-button>
              </div>
              <div id="comment-list">
                <div class="item" style="margin: 1em">{{ passage.comments.length }}条评论：</div>
                <CommentItem v-for="(c, i) in passage.comments" :key="i" :comment="c" />
              </div>
            </el-col>
            <el-col id="side" :span="6" class="hidden-sm-and-down">
              <div class="item">
                <tag />
              </div>
              <div class="item">
                <Introduction :user-id="passage.userId" />
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
import tag from '../components/tag'
import CommentItem from './components/CommentItem'
import Introduction from './components/Introduction'

export default {
  name: 'Article',
  components: { VHeader, VFooter, tag, CommentItem, Introduction },
  data() {
    return {
      passage: Object(),
      good: false,
      comment: '',

      minHeight: 0,
      navBarFixed: false
    }
  },
  created() {
    const id = this.$route.params && this.$route.params.id
    this.fetchData(id)
    this.$axios.get('http://localhost:8080/login', {
      params: {
        userId: 'tan',
        password: '114514'
      }
    })
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
    fetchData(id) {
      this.$axios.get('http://localhost:8080/selectPassage', {
        params: { 'passageId': id }
      }).then(response => {
        this.passage = response.data
        console.log(this.passage)
      }).catch(err => {
        console.log(err)
      })
    },
    switchGood() {
      if (!this.good) {
        this.good = true
        this.$axios.get('http://localhost:8080/good', {
          params: { passageId: this.passage.passageId }
        })
      } else {
        this.good = false
        this.$axios.get('http://localhost:8080/deleteGood', {
          params: { passageId: this.passage.passageId }
        })
      }
    },
    sendComment() {
      this.$axios.get('http://localhost:8080/comment', {
        params: {
          passageId: this.passage.passageId,
          review: this.comment
        }
      })
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
#artcle-info {
  padding: 20px;
  background-image: url(../assets/vue.jpg);
  margin-bottom: 40px;
}

#artcle-info .abstract {
  color: #ffffff;
  border-left: 3px solid #F56C6C;
  padding: 10px;
  background-color: rgba(126, 129, 135, 0.3);
}

#artcle-info .timeAndView {
  padding: 20px;
  line-height: 30px;
  font-size: 16px;
  color: #ffffff;
}

pre.has {
  color: #ffffff;
  background-color: rgba(0, 0, 0, 0.8);
}

img.has {
  width: 100%;
}

#statement {
  border-left: 3px solid #F56C6C;
  padding: 20px;
  background-color: #EBEEF5;
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

#artcle-content {
  margin: 1em;
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, 'Open Sans', 'Helvetica Neue', sans-serif;
  line-height: 20px;
}
</style>
