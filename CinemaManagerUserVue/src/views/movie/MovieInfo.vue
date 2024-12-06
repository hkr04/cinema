<template>
  <div class="movie-container">
    <div class="header">
      <div class="header-inner clearfix">
        <div class="movie-info-left">
          <div class="avatar-shadow">
            <img class="avatar" :src="movieInfo.moviePoster">
          </div>
        </div>
        <div class="movie-info-right">
          <div class="movie-info-msg">
            <h1 class="movie-name">{{movieInfo.movieName}}</h1>
            <ul>
              <li>{{movieInfo.movieCategoryList}}</li>
              <li>{{movieInfo.movieArea}} / {{movieInfo.movieLength}}分钟</li>
              <li>{{movieInfo.releaseDate}} {{movieInfo.movieArea}} 上映</li>
            </ul>
          </div>
          <!-- 电影评分 -->
          <div class="movie-rating" style="display: flex; align-items: center; gap: 10px; z-index: 999;">
            <el-rate v-model="movieRating" :max="5" allow-half
                     void-color="white"
                     disabled-void-color="#d3d3d3"
                     class="custom-rate">
            </el-rate>
            <!-- 显示 10 分制评分 -->
            <span>{{ this.movieRating * 2 }} 分</span>
            <!-- 提交按钮 -->
            <el-button
              type="danger"
              @click="submitRating"
            >
              {{ this.movieRating === this.movieInfo.previousRating ? '已提交' : '提交' }}
            </el-button>
          </div>
          <div class="movie-info-btn">
            <el-button class="buy-btn" type="primary" @click="toChooseSession" style="font-size: 22px;">
              <i class="iconfont icon-r-yes" style="font-size: 26px;"></i> 特惠购票</el-button>

            <el-button class="vote-btn" type="success" @click="openDatePicker" style="  font-size: 22px; margin-left: 10px;">
              <i class="el-icon-date" style="font-size: 26px;"></i> 票选放映时段
            </el-button>

            <el-dialog :title="dialogTitle" :visible.sync="dialogVisible" width="30%">
              <div v-if="!voteSubmitted">
                <el-form :model="formdata" style="margin-top: 10px;">
                  <el-form-item class="edit-form-wrap">
                    <el-time-picker
                      v-model="formdata.starttime"
                      placeholder="起始时间"
                      :value-format="pickerFormatText"
                      :format="pickerFormatText"
                      :picker-options="{
                          selectableRange: '08:00:00 - 23:59:00',
                          format: pickerFormatText
                        }"
                      @change="changeTime"
                    ></el-time-picker>
                    <span>-</span>
                    <el-time-picker
                      v-model="formdata.endtime"
                      placeholder="结束时间"
                      :value-format="pickerFormatText"
                      :format="pickerFormatText"
                      :picker-options="{
                          selectableRange: `${minPickerTime} - 23:59:00`,
                          format: pickerFormatText
                        }"
                    ></el-time-picker>
                  </el-form-item>
                </el-form>
                <span slot="footer" class="dialog-footer">
                    <el-button @click="dialogVisible = false">取消</el-button>
                    <el-button type="primary" @click="voteSuccess">提交</el-button>
                  </span>
              </div>
              <div v-else>
                <p>5秒后返回首页...</p>
              </div>
            </el-dialog>
          </div>
          <div class="movie-info-score">
            <!-- 评分分布图 -->
            <div class="rating-distribution" ref="ratingChart">
            </div>
            <div class="movie-index box-office-container">
              <span class="movie-index-title">累计票房</span>
              <div style="display: flex;align-items: flex-end;">
                <span class="box-office">{{movieInfo.movieBoxOffice}}</span>
                <span class="unit">元</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="movie-info-detail-container">
      <div class="movie-info-detail clearfix">
        <div class="main-content">
          <div class="crumbs-nav-container">
            <a href="/welcome">影响力</a> &gt; <a href="/movie">电影</a> &gt; {{movieInfo.movieName}}
          </div>
          <el-tabs v-model="activeName">
            <el-tab-pane label="介绍" name="introduction">
              <div class="tab-body">
                <!-- 剧情简介 -->
                <div class="module">
                  <div class="mod-title">
                    <h2>剧情简介</h2>
                  </div>
                  <div class="mod-content">
                    <span class="dra">
                      {{movieInfo.movieIntroduction}}
                    </span>
                  </div>
                </div>
                <!-- 图集 -->
                <div class="module">
                  <div class="mod-title">
                    <h2>图集</h2>
                    <a class="more" @click="showPictures">全部</a>
                  </div>
                  <div class="mod-content">
                    <div class="pictures-list">
                      <div v-if="movieInfo.moviePictures.length === 0">
                        暂无图片资源
                      </div>
                      <el-image
                        class="default-img"
                        :src="movieInfo.moviePictures[0]"
                        :preview-src-list="movieInfo.moviePictures"
                        v-if="movieInfo.moviePictures.length > 0">
                      </el-image>
                      <div class="little-pictures">
                        <el-image
                          class="default-img"
                          v-for="(item,index) in movieInfo.moviePictures.slice(1)"
                          :key="index"
                          :src="item"
                          :preview-src-list="movieInfo.moviePictures">
                        </el-image>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </el-tab-pane>
            <el-tab-pane label="图集" name="pictures">
              <div class="tab-body">
                <div class="pictures-list">
                  <div class="little-pictures">
                    <div v-if="movieInfo.moviePictures.length == 0">
                      暂无图片资源
                    </div>
                    <el-image
                      fit="cover"
                      class="default-img"
                      v-for="(item,index) in movieInfo.moviePictures"
                      :key="index"
                      :src="item"
                      :preview-src-list="movieInfo.moviePictures">
                    </el-image>
                  </div>
                </div>
              </div>
            </el-tab-pane>
            <!-- 添加评论标签页 -->
            <el-tab-pane label="评论" name="comments">
              <div class="tab-body">
                <!-- 评论输入框 -->
                <div class="comment-box">
                  <el-input
                    v-model="newComment"
                    type="textarea"
                    :rows="3"
                    placeholder="写下你的评论..."
                  />
                  <el-button
                    type="primary"
                    @click="addComment"
                    style="margin-top: 10px;"
                  >
                    发表评论
                  </el-button>
                </div>

                <!-- 评论列表 -->
                <div class="comment-list">
                  <el-card v-for="comment in comments" :key="comment.id" class="comment-item">
                    <div class="comment-info">
                      <span class="comment-user">{{ comment.userName }}</span>
                      <span class="comment-time">{{ comment.time }}</span>
                      <!-- 删除按钮 -->
                      <el-button
                        v-if="comment.userId === currentUserId"
                        type="text"
                        size="small"
                        icon="el-icon-delete"
                        @click="deleteComment(comment.id)"
                        style="color: red;"
                      >
                        删除
                      </el-button>
                    </div>
                    <div class="comment-content">{{ comment.content }}</div>
                  </el-card>
                </div>
              </div>
            </el-tab-pane>

          </el-tabs>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import movieItem from './../../components/movie/movie-item';
import moment from 'moment'
import { Rate } from 'element-ui';
export default {
  name: "MovieInfo",
  components:{
    movieItem,
    'el-rate': Rate
  },
  data() {
    const loginUser = window.sessionStorage.getItem('loginUser');

    return {
      currentUserId: loginUser ? JSON.parse(loginUser).userId : "",
      currentUserName: loginUser ? JSON.parse(loginUser).userName: "",
      movieInfo: {},
      movieRating: 4.5,
      movieId: this.$route.params.movieId,
      activeName: 'introduction',
      colors: ['#99A9BF', '#F7BA2A', '#FF9900'],
      httpURL: this.global.base,
      dialogVisible: false,
      dialogTitle: "您希望电影在哪个时间段内开场？",

      formdata: {
        starttime: '',
        endtime: ''
      },
      minPickerTime: '08:00:00',
      pickerFormatText: 'HH:mm',
      voteSubmitted: false,
      // 添加评论相关数据
      newComment: '',
      comments: [
        {
          id: 1,
          userName: '用户1',
          content: '这部电影很好看！',
          time: '2024-01-15 12:00'
        },
        {
          id: 2,
          userName: '用户2',
          content: '演员演技很棒。',
          time: '2024-01-15 13:30'
        }
      ]
    }
  },
  created() {
    this.getMovieInfo()
    this.getComments()
  },
  mounted() {
    this.initRatingDistributionChart();
  },
  methods: {
    // 自定义 sleep 函数，返回一个 Promise 用于暂停异步操作
    sleep(ms) {
      return new Promise(resolve => setTimeout(resolve, ms));
    },
    async initRatingDistributionChart() {
      // 使用 while 循环，但不阻塞线程
      while (!this.movieInfo.ratingDistribution) {
        await this.sleep(1000);  // 每秒检查一次
      }

      console.log(this.movieInfo.ratingDistribution)

      if (this.movieInfo.ratingDistribution) {
        const chartDom = this.$refs.ratingChart;
        const myChart = echarts.init(chartDom);

        const option = {
          // 隐藏坐标轴
          xAxis: {
            type: 'value',  // 横向柱状图
            show: false,    // 隐藏 x 轴
            axisLine: {
              show: false,    // 隐藏 y 轴的坐标轴线
            },
          },
          yAxis: {
            type: 'category', // 评分区间
            data: ['1星', '2星', '3星', '4星', '5星'], // 评分区间
            axisLine: {
              show: false,    // 隐藏 y 轴的坐标轴线
            },
            axisLabel: {
              rotate: 45,
              show: true,     // 显示 y 轴的标签（评分区间）
              color: '#000',  // 标签颜色
              fontSize: 12,   // 标签字体大小
            },
            axisTick:{
              show:false // 不显示坐标轴刻度线
            },
          },
          series: [
            {
              data: this.movieInfo.ratingDistribution,
              type: 'bar',
              barWidth: '60%',
              itemStyle: {
                color: '#42b983', // 设置柱状图颜色
              },
              label: {
                show: true,  // 显示标签
                position: 'right',  // 标签在条形的右侧
                formatter: '{c}%',  // 显示百分比，`{c}` 是数据值
                color: '#000',  // 标签颜色
                fontSize: 14,   // 字体大小
                margin: 20,
              }
            },
          ],
        };

        myChart.setOption(option);
      } else {
        console.error('评分分布数据无效');
      }
    },
    async submitRating() {
      // Get current user info
      const currentUser = this.getCurrentUser(); // Implement this method based on your authentication system
      if (!currentUser) {
        this.$message.error('用户未登录');
        return;
      }
      console.log('Current user:', currentUser);
      console.log('Current userName:', currentUser.userName);

      if (this.movieRating <= 0) {
        this.$message.warning('评分不能为零哦');
        return;
      }

      const ratingData = {
        movieId: this.movieInfo.movieId,
        userId: currentUser.userId,
        rating: this.movieRating * 2
      };

      try {
        let response;
        if (this.movieInfo.previousRating > 0) {
          response = await axios.put('sysRating/', ratingData);
        } else {
          response = await axios.post('sysRating/', ratingData);
          this.movieInfo.previousRating = this.movieRating
        }

        if (response.data.code !== 200) {
          this.$message.error('评分失败');
          return;
        }
        this.$message.success('评分成功');
      } catch (error) {
        this.$message.error('评分失败')
      }
    },
    //票选
    openDatePicker() {
      this.dialogVisible = true;
      this.voteSubmitted = false;
    },
    voteSuccess() {
      // 显示“已投票”文本
      this.voteSubmitted = true;
      this.dialogTitle = "投票成功";

      // 在2秒后自动关闭对话框
      setTimeout(() => {
        this.dialogVisible = false;
      }, 5000);
    },

    async submitVote() {
      if (this.selectedDateRange.length === 2) {
        const voteData = {
          user_id: this.user_id,            // 用户 ID
          movie_id: this.movieId,           // 电影 ID
          vote_time_start: this.selectedDateRange[0], // 用户选择的开始时间
          vote_time_end: this.selectedDateRange[1],   // 用户选择的结束时间
        };

        try {
          // 提交投票数据的代码...
        } catch (error) {
          this.$message.error('网络错误，无法提交投票');
        }
      } else {
        this.$message.error('请选择一个时间段');
      }
    },
    async fetchData() {
      // 确保在获取电影信息后再初始化评分分布图
      await this.getMovieInfo();
      this.getComments();  // 获取评论的操作可以并行执行，等待 movieInfo 加载完后继续执行

      // 获取电影信息后，再初始化评分分布图
      this.initRatingDistributionChart();
    },
    async getMovieInfo() {
      const _this = this;
      const { data: res } = await axios.get('sysMovie/find/' + this.movieId);
      const { data: ratingRes } = await axios.get('sysRating/findByMovieId/' + this.movieId);

      if (res.code !== 200) return this.$message.error('数据查询失败');
      if (ratingRes.code !== 200) return this.$message.error('评分数据查询失败');

      // 设置电影基本信息
      this.movieInfo = res.data;
      this.movieInfo.moviePoster = this.httpURL + JSON.parse(res.data.moviePoster)[0];
      this.movieInfo.moviePictures = JSON.parse(this.movieInfo.moviePictures).map((obj, index) => {
        return this.httpURL + obj;
      });
      this.movieInfo.movieCategoryList = this.movieInfo.movieCategoryList.map((obj, index) => {
        return obj.movieCategoryName;
      }).join(" ");

      // 计算评分数据
      const ratings = ratingRes.data;  // 假设 ratingRes.data 包含所有用户评分数据
      let totalRating = 0;
      let ratingCount = ratings.length;
      let ratingDistribution = [0, 0, 0, 0, 0]; // [1星, 2星, 3星, 4星, 5星]
      let userRating = -1;  // 默认没有评分

      // 统计分布和计算平均分
      ratings.forEach(rating => {
        let ratingValue = Math.ceil(rating.rating / 2);  // 假设评分是以半颗星计算的，转换为 1-5 分

        if (ratingValue >= 1 && ratingValue <= 5) {
          ratingDistribution[ratingValue - 1] += 1;  // 更新相应的评分分布
        }

        if (rating.userId === this.currentUserId) {  // 如果当前用户的 ID 存在于 rating 中
          userRating = ratingValue;
        }
      });

      // 计算平均评分
      this.movieInfo.averageRating = totalRating / (ratingCount * 2);  // 除以 2 转换为 1-5 评分
      this.movieInfo.ratingDistribution = ratingDistribution.map(count => count / ratingCount); // 转换为比例
      this.movieInfo.previousRating = userRating;

      console.log(this.movieInfo.ratingDistribution);

      // 设置当前评分（若没有评分则为 -1）
      this.movieRating = this.movieInfo.previousRating > 0 ? this.movieInfo.previousRating : this.movieInfo.averageRating;
    },
    showPictures(){
      this.activeName = 'pictures'
    },
    // 加入一个提取评论的方法
    async getComments() {
      try {
        // 使用新的接口路径
        const { data: res } = await axios.get(`http://127.0.0.1:9231/sysComment/findByContentId/${this.movieId}`);
        console.log('评论接口响应:', res);

        if (res.code !== 200) {
          return this.$message.error('获取评论失败');
        }

        // 解析评论数据
        const resData = res.data;
        let commentsArray = [];

        if (Array.isArray(resData)) {
          commentsArray = resData.map((comment) => ({
            id: comment.commentId,
            userId: comment.userId,
            userName: comment.author,
            content: comment.commentContent,
            time: new Date(comment.createdAt).toLocaleString('zh-CN', {
              year: 'numeric',
              month: '2-digit',
              day: '2-digit',
              hour: '2-digit',
              minute: '2-digit',
            }),
          }));
        } else {
          commentsArray = [];
        }

        // 更新评论列表
        this.comments = commentsArray;
      } catch (error) {
        console.error('获取评论时出错:', error);
        this.$message.error('获取评论失败，请重试');
      }
    },
    // 删除评论方法
    async deleteComment(commentId) {
      try {
        // 弹窗确认是否删除
        const confirm = await this.$confirm(
          '确定要删除这条评论吗？',
          '提示',
          {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
          }
        );

        if (confirm) {
          // 调用后端删除评论接口
          const { data: res } = await axios.delete(`http://127.0.0.1:9231/sysComment/${commentId}`);
          if (res.code !== 200) {
            return this.$message.error('删除评论失败');
          }

          this.$message.success('评论已删除');
          // 重新获取评论列表
          await this.getComments();
        }
      } catch (error) {
        console.error('删除评论时出错:', error);
        this.$message.error('删除评论失败，请重试');
      }
    },



    //转到购票页面
    toChooseSession(){
      let cinemaId = 1
      this.$router.push('/chooseSession/' + cinemaId)
    },
    // 添加评论方
    async addComment() {
      if (!this.newComment.trim()) {
        this.$message.warning('请输入评论内容');
        return;
      }

      // Get current user info
      const currentUser = this.getCurrentUser(); // Implement this method based on your authentication system
      if (!currentUser) {
        this.$message.error('用户未登录');
        return;
      }
      console.log('Current user:', currentUser);
      console.log('Current userName:', currentUser.userName);

      // Prepare the data object for the backend
      const commentData = {
        commentContent: this.newComment,
        author: currentUser.userName,
        contentId: this.movieId,
        userId: currentUser.userId,
        status: '1',
        // You can omit createdAt and updatedAt if the backend sets them automatically
        // createdAt: new Date().toISOString(),
        // updatedAt: new Date().toISOString(),
      };
      console.log('Comment Data:', commentData);

      try {
        const response = await axios.post('http://localhost:9231/sysComment/', commentData);
        console.log('Add comment response:', response.data);

        if (response.data.code !== 200) {
          this.$message.error('评论失败');
          return;
        }

        this.$message.success('评论成功');

        // Option 1: Refresh comments from the backend
        await this.getComments();

        // Option 2: Add the new comment to this.comments manually
        // this.comments.unshift({
        //   id: response.data.data.commentId, // Use the ID returned by the backend
        //   userName: commentData.author,
        //   content: commentData.commentContent,
        //   time: new Date().toLocaleString('zh-CN', {
        //     year: 'numeric',
        //     month: '2-digit',
        //     day: '2-digit',
        //     hour: '2-digit',
        //     minute: '2-digit',
        //   }),
        // });

        // Clear the input field
        this.newComment = '';
      } catch (error) {
        console.error('Error adding comment:', error);
        this.$message.error('评论失败，请重试');
      }
    },

    // Implement this method to retrieve the current user information
    getCurrentUser() {
      if (!this.currentUserId)
        return null;
      // This method should return an object with userId and userName
      // For example, if you're using Vuex:
      // return this.$store.state.user;

      // If you're storing user info in localStorage:
      // return JSON.parse(localStorage.getItem('user'));

      // Placeholder implementation:
      return {
        userId: this.currentUserId, // Replace with actual user ID
        userName: this.currentUserName, // Replace with actual userName
      };
    },
  }
};
</script>

<style scoped>
.movie-container{

}

a{
  text-decoration: none;
  cursor:pointer;
}

.header{
  padding: 0;
  width: 100%;
  min-width: 1200px;
  height: 500px;
  background: radial-gradient( pink, lightblue);
}

.header-inner{
  width: 1200px;
  margin: 0 auto;
  position: relative;
}

.clearfix::before, .clearfix::after{
  content: " ";
  display: table;
}

.clearfix::after{
  clear: both;
}

.movie-info-left{
  width: 300px;
  height: 370px;
  float: left;
  position: relative;
  top: 70px;
  overflow: hidden;
  z-index: 9;
}

.avatar-shadow{
  position: relative;
  margin: 0 30px;
  width: 240px;
  height: 330px;
  padding-bottom: 40px;
  background: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAPAAAAAyAgMAAAAfG76+AAAADFBMVEUAAAAAAAAAAAAAAAA16TeWAAAABHRSTlMOAgoGQ0SIeAAAADpJREFUSMdjGAWjYBRgAasoAAwdFACKbB7VPEI076YAUGbzfwrAqOYRormcAjCANodSAEY1j2oexJoBlx1+yE7RXIIAAAAASUVORK5CYII=) no-repeat bottom;
}

.avatar{
  border: 4px solid #fff;
  height: 322px;
  width: 232px;
}

.movie-info-msg{
  position: absolute;
  color: black;
  font-size: 14px;
  z-index: 1;
}

.movie-name{
  width: 900px;
  margin-top: 0;
  font-size: 26px;
  line-height: 32px;
  font-weight: 700;
  margin-bottom: 0;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  max-height: 64px;
}

.movie-ename{
  width: 340px;
  font-size: 18px;
  line-height: 1.3;
  margin-bottom: 14px;
}

ul{
  width: 250px;
  list-style: none;
  padding-left: 0;
  margin-bottom: 20px;
}

ul li{
  margin: 12px 0;
  line-height: 100%;
}

.movie-info-btn{
  position: absolute;
  bottom: 20px;
  z-index: 999;
}

.movie-rating {
  position: absolute;
  bottom: 80px;
}

.custom-rate .el-rate__item {
  font-size: 100px; /* 调整星星大小 ？？？*/
}

.vote-btn {
  font-size: 16px;
  text-align: center;
}

.buy-btn{
  margin-top: 10px;
  width: 250px;
  font-size: 16px;
  text-align: center;
}

.movie-info-score{
  display: flex;
  position: absolute;
  align-items: center;             /* 垂直居中对齐 */
  top: 80px;
  left: 0;
  z-index: 1;
}

.movie-index {
  margin-bottom: 16px;
  color: black;
}

.box-office-container{
  display: flex;
  flex-direction: column;
}

.movie-index-title{
  font-size: 12px;
  margin-bottom: 8px;
}

.movie-score-num{
  font-size: 30px;
  color: #ffc600;
  height: 30px;
  line-height: 30px;
  margin-right: 10px;
}

.score-container{
  display: flex;
  flex-direction: column;
  align-items: center;
  font-size: 12px;
}

.box-office{
  font-size: 30px;
}

.unit{
  font-size: 12px;
  padding-left: 1px;
  line-height: 24px;
}

.movie-info-right{
  height: 400px;
  position: relative;
  margin-right: 30px;
  margin-left: 300px;
  margin-top: 70px;
}

.movie-info-detail-container{
  width:1200px;
  margin: 0 auto;
}

.movie-info-detail{
  margin-top: 80px;
}

.main-content {
  width: 730px;
  float: left;
  margin-bottom: 20px;
}

.crumbs-nav-container {
  margin-bottom: 25px;
  color: #333;
}

.crumbs-nav-container a{
  color: inherit;
}

.el-tabs >>> .el-tabs__item{
  font-size: 20px;
}

.tab-body{
  margin-top: 40px;
}

.module{
  position: relative;
  margin-bottom: 60px;
}

.mod-title h2{
  display: inline-block;
  margin: 0;
  padding: 0;
  font-weight: 400;
  font-size: 18px;
  color: #333;
  line-height: 18px;
}

.mod-title h2:before {
  float: left;
  content: "";
  display: inline-block;
  width: 4px;
  height: 18px;
  margin-right: 6px;
  background-color: #409EFF;
}

.mod-content{
  margin-top: 20px;
  color: #333;
}

.mod-content .dra{
  font-size: 14px;
  line-height: 26px;
}

.more{
  float: right;
  cursor: pointer;
  font-size: 14px;
  color: #999;
  padding-right: 14px;
  background: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAgAAAAOCAYAAAASVl2WAAAABmJLR0QA/wD/AP+gvaeTAAAAv0lEQVQY013RTUpDQRAE4G8eghcR8ScgKCIugpJFjuIjqAvBc7jxj0muEnCjiIQQJOImB3GnbnpkfL1qpqqrunpSzvkDPxjhGdq2VarBF3q4wRHknP8RzvCEQzzguCalaHZwiwHecY6XogCf8TjFHh7Rh9Tx3AylIZa4TgWpSBuY4BSrYlFXKsr4bjrTW5HkJJa9SBW4jbtukmKxG5MDLOKqfzEPcB9LzQN8LSdfwxj7eMMlZvV/NFiPzFddEH4Bt5Y1mf3fnDwAAAAASUVORK5CYII=) no-repeat 100%;
}

.portrait{
  margin-bottom: 6px;
  width: 128px;
  height: 170px;
  overflow: hidden;
}

.portrait .default-img{
  width: 128px;
  height: 170px;
}

.pictures-list{
  display: flex;
}

.pictures-list>.el-image:first-child{
  width: 465px;
  height: 258px;
}

.pictures-list .default-img{
  border-style: none;
  cursor: pointer;
  width: 126px;
  height: 126px;
}

.little-pictures{
  width: 262px;
  height: 262px;
  display: flex;
  flex-wrap: wrap;
  margin-left: 10px;
  justify-content: space-between;
}

#pane-pictures .little-pictures{
  width: 100%;
  justify-content: flex-start;
  margin-left: 0;
}

#pane-pictures .default-img{
  margin-top: 10px;
  margin-left: 10px;
}

.user-avatar{
  width: 50px;
  height: 50px;
  margin-right: 20px;
}


.main-header{
  font-size: 16px;
}

.user-name{
  margin-top: 2px;
}

.comment-user {
  margin-right: 5px; /* 控制右侧间距为5px */
}

.comment-time {
  margin-right: 5px; /* 控制右侧间距为5px */
}

.rating-distribution {
  width: 230px;
  height: 250px;
  margin-left: 5px;           /* 左边距 */
  margin-bottom: 50px;        /* 下边距 */
  margin-right: 30px;
}


</style>
