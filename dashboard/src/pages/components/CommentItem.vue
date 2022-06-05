<template>
  <el-row class="art-item">
    <el-card shadow="hover">
      <el-row style="color: #888" class="art-info d-flex align-items-center justify-content-start">
        <div class="art-time"><i class="el-icon-edit" />：{{ comment.userId }}</div>
        <div class="art-time"><i class="el-icon-time" />：{{ comment.time }}</div>
      </el-row>
      <el-row class="art-body">
        {{ comment.review }}
      </el-row>
    </el-card>
  </el-row>
</template>

<script>
const defaultComment = {
  commentId: 0,
  userId: '',
  passageId: 0,
  time: 0,
  review: ''
}

export default {
  name: 'CommentItem',
  props: {
    comment: {
      type: Object,
      default: defaultComment
    }
  },
  created() {
    console.log('create comment item')
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
</style>
