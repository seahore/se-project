<template>
  <el-form>
    <el-form-item label="用户ID">
      <el-input v-model.trim="user.userId" tabindex="0" />
    </el-form-item>
    <el-form-item label="电话">
      <el-input v-model.trim="user.phone" tabindex="1" />
    </el-form-item>
    <el-form-item label="修改密码">
      <el-input v-model.trim="user.password" type="password" tabindex="2" />
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="submit">Update</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
export default {
  props: {
    user: {
      type: Object,
      default: () => {
        return {
          userId: '',
          password: '',
          phone: ''
        }
      }
    }
  },
  methods: {
    submit() {
      this.$message({
        message: 'User information has been updated successfully',
        type: 'success',
        duration: 5 * 1000
      })
    }
  }
}
</script>
