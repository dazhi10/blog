<template>
  <div class="app-container">
    <el-form
        v-show="showSearch"
        ref="queryForm"
        :model="queryParams"
        :inline="true"
        label-width="68px"
    >
      <el-form-item label="评论内容" prop="status">
        <el-input
            v-model="queryParams.content"
            placeholder="请输入评论内容"
            clearable
            size="small"
            @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button
            type="primary"
            icon="el-icon-search"
            size="mini"
            @click="handleQuery"
        >搜索
        </el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
            type="danger"
            plain
            icon="el-icon-delete"
            size="mini"
            :disabled="multiple"
            @click="handleDelete"
        >删除
        </el-button>
      </el-col>
    </el-row>

    <el-table
        v-loading="loading"
        :data="commentList"
        @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="发布者id" align="center" prop="createBy"/>
      <el-table-column label="内容" align="center" prop="content"/>
      <el-table-column
          label="操作"
          align="center"
          class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <el-button
              size="mini"
              type="text"
              icon="el-icon-delete"
              @click="handleDelete(scope.row)"
          >删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination
        :page-size.sync="queryParams.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
        :page-sizes="[10, 20, 30, 40]"
        :current-page.sync="queryParams.pageNum"
        @current-change="getList"
        @size-change="getList"
    />
  </div>
</template>

<script>

import {delComment, listComment} from "@/api/content/comment";

export default {
  name: "index",
  data() {
    return {
      // 显示搜索条件
      showSearch: true,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        content: null,
      },
      // 遮罩层
      loading: true,
      // 评论表格数据
      commentList: null,
      // 选中数组
      ids: [],
      // 非多个禁用
      multiple: true,
      // 总条数
      total: 0,
    }
  },

  created() {
    this.getList()
  },

  methods: {
    /** 查询友链列表 */
    async getList() {
      this.loading = true
      const res = await listComment(this.queryParams);
      this.commentList = res.rows
      this.total = res.total
      this.loading = false
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm('queryForm')
      this.handleQuery()
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 多选框选中数据*/
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id)
      this.multiple = !selection.length
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$modal
          .confirm('是否确认删除评论编号为"' + ids + '"的数据项？')
          .then(function () {
            return delComment(ids)
          })
          .then(() => {
            this.getList()
            this.$modal.msgSuccess('删除成功')
          })
          .catch(() => {
          })
    }
  }
}
</script>

<style scoped lang="less">

</style>