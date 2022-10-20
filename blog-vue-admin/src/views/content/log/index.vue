<template>
  <div class="app-container">
    <el-form
        v-show="showSearch"
        ref="queryForm"
        :model="queryParams"
        :inline="true"
        label-width="68px"
    >
      <el-form-item label="操作描述" prop="status">
        <el-input
            v-model="queryParams.operateDescribe"
            placeholder="请输入操作描述"
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
        :data="logList"
        @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="操作描述" align="center" prop="operateDescribe"/>
      <el-table-column label="请求方式" align="center" prop="requestMethod">
        <template slot-scope="scope">
          <el-tag :type="tagType(scope.row.requestMethod)">
            {{ scope.row.requestMethod }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="请求ip" align="center" prop="ip"/>
      <el-table-column label="操作账号" align="center">
        <template slot-scope="scope">
          {{ scope.row.operateBy ? scope.row.operateBy : '-' }}
        </template>
      </el-table-column>
      <el-table-column label="操作时间" align="center" prop="createTime"/>
      <el-table-column
          label="操作"
          align="center"
          class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <el-button
              size="mini"
              type="text"
              icon="el-icon-view"
              @click="queryDelete(scope.row)"
          >查看
          </el-button>
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

    <!-- 查看模态框 -->
    <el-dialog :visible.sync="isCheck" width="40%">
      <div class="dialog-title-container" slot="title">
        <i class="el-icon-more"/>详细信息
      </div>

      <el-form :model="optLog" label-width="100px" size="mini">
        <el-form-item label="操作描述：">
          {{ optLog.operateDescribe }}
        </el-form-item>
        <el-form-item label="请求地址：">
          {{ optLog.url }}
        </el-form-item>
        <el-form-item label="请求方式：">
          <el-tag :type="tagType(optLog.requestMethod)">
            {{ optLog.requestMethod }}
          </el-tag>
        </el-form-item>
        <el-form-item label="操作方法：">
          {{ optLog.operateMethod }}
        </el-form-item>
        <el-form-item label="请求参数：">
          {{ optLog.requestParam }}
        </el-form-item>
        <el-form-item label="返回数据：">
          {{ optLog.responseData }}
        </el-form-item>
        <el-form-item label="操作账号：">
          {{ optLog.operateBy ? optLog.operateBy : '-' }}
        </el-form-item>
      </el-form>
    </el-dialog>

  </div>
</template>

<script>
import {delLog, listLog} from "@/api/content/log";

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
        operateDescribe: null,
      },
      isCheck: false,
      optLog: {},
      // 遮罩层
      loading: true,
      // 评论表格数据
      logList: null,
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
      const res = await listLog(this.queryParams);
      this.logList = res.rows
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
      console.log(1111)
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 多选框选中数据*/
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id)
      this.multiple = !selection.length
    },
    /** 查看详细按钮操作 */
    queryDelete(log) {
      this.optLog = log
      this.isCheck = true
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$modal
          .confirm('是否确认删除日志编号为"' + ids + '"的数据项？')
          .then(function () {
            return delLog(ids)
          })
          .then(() => {
            this.getList()
            this.$modal.msgSuccess('删除成功')
          })
          .catch(() => {
          })
    }
  },
  computed: {
    tagType() {
      return function (type) {
        switch (type) {
          case "GET":
            return "";
          case "POST":
            return "success";
          case "PUT":
            return "warning";
          case "DELETE":
            return "danger";
        }
      };
    }
  }
}
</script>


<style scoped lang="less">

</style>