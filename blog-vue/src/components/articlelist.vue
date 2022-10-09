<!-- 文章列表 -->
<template>
    <el-row class="sharelistBox">
      <el-col  :span="24" class="s-item tcommonBox" v-for="(item,index) in articleList" :key="'article'+index">
        <el-skeleton :rows="6" animated :loading="loading" >
          <template slot="template" style="position: relative;">
            <el-skeleton-item variant="text" style="  position: absolute; top:30px; left:-10px;width: 70px; height: 35px; " />
            <el-skeleton-item variant="text" style="  position: absolute; top:110px; width: 250px; height: 16px; " />
            <div style="display: flex; flex-direction:column; align-items: center; justify-content: center;">
                <el-skeleton-item variant="text" style="margin-bottom: 15px; width: 140px; height: 30px;" />
                <el-skeleton-item variant="text" style="margin-bottom: 65px; width: 280px; height: 16px;" />
            </div>
            <el-skeleton-item variant="image" style="width: 100%; height: 300px;" />
            <div style="margin-top: 18px;">
              <div style="display: flex; align-items: center; justify-content: center;">
                <el-skeleton-item variant="text" style=" width: 120px; height: 34px;" />
              </div>
            </div>
          </template>
        
          <header style="height: 75px;">
              <h1>
                  <a :href="'#/DetailArticle?aid='+item.id" target="_blank">
                      {{item.title}}
                  </a>
              </h1>
              <h2>
                  <i class="fa fa-fw fa-user"></i>发表于
                  <i class="fa fa-fw fa-clock-o"></i><span v-html="showInitDate(item.createTime,'all')">{{showInitDate(item.createTime,'all')}}</span>
                  <i class="fa fa-fw fa-eye"></i>{{item.viewCount}} 次围观

              </h2>
              <div class="ui label" style="top: -80px;">
                  {{item.categoryName}}
              </div>
          </header>
          <div class="article-content">
              <p style="text-indent:2em;">
                  {{item.summary}}
              </p>
              <p style=" display: flex; justify-content: center;">
                  <img :src="item.thumbnail" alt="" class="maxW">
              </p>
          </div>
          <div class="viewdetail">
              <a class="tcolors-bg" :href="'#/DetailArticle?aid='+item.id" target="_blank">
                  阅读全文
              </a>
          </div>
        </el-skeleton>
      </el-col>

      <el-skeleton :rows="1" animated :loading="loading">
          <template slot="template" >
              <el-skeleton-item variant="text" style="width: 100%; height: 35px; " />              
            </template>
         <el-col class="viewmore" v-show="!loading">
            <a v-show="hasMore" class="tcolors-bg" href="javascript:void(0);" @click="addMoreFun">点击加载更多</a>
            <a v-show="!hasMore" class="tcolors-bg" href="javascript:void(0);">暂无更多数据</a>
          </el-col>
      </el-skeleton>
    </el-row>
</template>

<script>
import { initDate } from "../utils/server.js";
import { articleList } from "../api/article";
export default {
  name: "Share",
  data() {
    //选项 / 数据
    return {
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        categoryId: 0
      },
      loading: true,
      articleList: [],
      hasMore: true
    };
  },

  methods: {
    //事件处理器
    showInitDate: function(oldDate, full) {
      return initDate(oldDate, full);
    },
    getList() {
      articleList(this.queryParams).then(response => {
        this.articleList = this.articleList.concat(response.rows);

        if (response.total <= this.articleList.length) {
          this.hasMore = false;
        } else {
          this.hasMore = true;
          this.queryParams.pageNum++;
        }
        this.timeout = setTimeout(() => {
          this.loading = false;
        }, 500);
      });
    },
    showSearchShowList: function(initData) {
      //展示数据
      if (initData) {
        this.articleList = [];
      }
      this.getList();
    },
    addMoreFun: function() {
      //查看更多
      this.showSearchShowList(false);
    },
    routeChange: function() {
      var that = this;
      this.queryParams.categoryId =
        that.$route.query.classId == undefined
          ? 0
          : parseInt(that.$route.query.classId); //获取传参的classId
      this.showSearchShowList(true);
    }
  },
  components: {
    //定义组件
  },
  watch: {
    // 如果路由有变化，会再次执行该方法
    $route: "routeChange",
    "$store.state.keywords": "routeChange"
  },
  created() {
    var that = this;
    that.routeChange();
  }
};
</script>

<style>
/*分享标题*/
.shareTitle {
  margin-bottom: 40px;
  position: relative;
  border-radius: 5px;
  background: #fff;
  padding: 15px;
}
.shareclassTwo {
  width: 100%;
}
.shareclassTwo li {
  display: inline-block;
}
.shareclassTwo li a {
  display: inline-block;
  padding: 3px 7px;
  margin: 5px 10px;
  color: #fff;
  border-radius: 4px;
  background: #64609e;
  border: 1px solid #64609e;
  transition: transform 0.2s linear;
  -webkit-transition: transform 0.2s linear;
}
.shareclassTwo li a:hover {
  transform: translate(0, -3px);
  -webkit-transform: translate(0, -3px);
}
.shareclassTwo li a.active {
  background: #fff;
  color: #64609e;
}
/*文章列表*/
.sharelistBox {
  transition: all 0.5s ease-out;
  font-size: 15px;
}
</style>
