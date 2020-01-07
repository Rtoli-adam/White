<template>
    <div>
      <el-upload
        class="img-upload"
        ref="upload"
        action="http://localhost:8443/api/covers"
        :on-preview="handlePreview"
        :on-remove="handleRemove"
        :before-remove="beforeRemove"
        :on-success="handleSuccess"
        :on-error="clear"
        multiple
        :limit="1"
        :on-exceed="handleExceed"
        :file-list="fileList"
        list-type="picture"
        @close="clear">
        <el-button size="small" type="primary">点击上传</el-button>
        <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
      </el-upload>
    </div>
</template>

<script>
    export default {
      name: "ImgUpload",
      data(){
        return{
          fileList:[],
          url: ''
        }
      },
      methods: {
        handleRemove(file, fileList) {
          console.log(file, fileList);
        },
        handlePreview(file) {
          console.log(file);
        },
        beforeRemove(file, fileList){
          return this.$confirm(`确定移除 ${file.name}？`)
        },
        handleExceed (files, fileList) {
          this.$message.warning(`当前限制选择 1 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`)
        },
        handleSuccess (response) {
          this.url = response
          this.$emit('upLoad')
          this.$message.warning('上传成功')
        },
        clear () {
          this.$message.warning('文件过大，只能上传jpg/png文件，且不超过500kb')
          this.$refs.upload.clearFiles()
        }
      }
    }
</script>

<style scoped>

</style>
