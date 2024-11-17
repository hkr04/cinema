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
          <div class="movie-info-btn">
            <el-button class="buy-btn" type="primary" @click="toChooseSession" style="font-size: 22px;"><i class="iconfont icon-r-yes" style="font-size: 26px;"></i> 特惠购票</el-button>
          </div>
          <div class="movie-info-score">
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
                        <span class="comment-user">{{ comment.username }}</span>
                        <span class="comment-time">{{ comment.time }}</span>
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
export default {
  name: "MovieInfo",
  components:{
    movieItem
  },
  data() {
    return {
      movieInfo: {
        moviePictures: []
      },
      movieId: this.$route.params.movieId,
      activeName: 'introduction',
      colors: ['#99A9BF', '#F7BA2A', '#FF9900'],
      httpURL: this.global.base,
      // 添加评论相关数据
      newComment: '',
      comments: [
        {
          id: 1,
          username: '用户1',
          content: '这部电影很好看！',
          time: '2024-01-15 12:00'
        },
        {
          id: 2,
          username: '用户2',
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
  methods: {
    async getMovieInfo(){
      const _this = this
      const {data : res} = await axios.get('sysMovie/find/' + this.movieId)
      if(res.code !== 200) return this.$message.error('数据查询失败')
      this.movieInfo = res.data
      this.movieInfo.moviePoster = this.httpURL + JSON.parse(res.data.moviePoster)[0]
      this.movieInfo.moviePictures = JSON.parse(this.movieInfo.moviePictures).map((obj, index) => {
        return this.httpURL + obj
      })
      this.movieInfo.movieCategoryList = this.movieInfo.movieCategoryList.map((obj,index) => {
        return obj.movieCategoryName;
      }).join(" ")
    },
    showPictures(){
      this.activeName = 'pictures'
    },
    // 加入一个提取评论的方法
    async getComments() {
      try {
        const { data: res } = await axios.get('sysComment/find/' + this.movieId);
        console.log('res:', res);

        if (res.code !== 200) {
          return this.$message.error('数据查询失败');
        }

        const resData = res.data;
        console.log('resData:', resData);

        let commentsArray = [];

        if (Array.isArray(resData)) {
          // If resData is an array, map over it
          commentsArray = resData.map(comment => ({
            id: comment.commentId,
            username: comment.author || '匿名用户',
            content: comment.commentContent,
            time: new Date(comment.createdAt).toLocaleString('zh-CN', {
              year: 'numeric',
              month: '2-digit',
              day: '2-digit',
              hour: '2-digit',
              minute: '2-digit',
            }),
          }));
        } else if (typeof resData === 'object' && resData !== null) {
          // If resData is a single object, wrap it in an array
          commentsArray = [{
            id: resData.commentId,
            username: resData.author || '匿名用户',
            content: resData.commentContent,
            time: new Date(resData.createdAt).toLocaleString('zh-CN', {
              year: 'numeric',
              month: '2-digit',
              day: '2-digit',
              hour: '2-digit',
              minute: '2-digit',
            }),
          }];
        } else {
          // If resData is neither an array nor an object, set comments to an empty array
          commentsArray = [];
        }

        // Assign the commentsArray to this.comments
        this.comments = commentsArray;
        console.log(this.comments);
      } catch (error) {
        console.error('Error fetching comments:', error);
        this.$message.error('获取评论失败');
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

    // Prepare the data object for the backend
    const commentData = {
      commentContent: this.newComment,
      author: currentUser.username || '匿名用户',
      contentId: this.movieId,
      userId: currentUser.userId,
      status: '1',
      // You can omit createdAt and updatedAt if the backend sets them automatically
      // createdAt: new Date().toISOString(),
      // updatedAt: new Date().toISOString(),
    };

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
      //   username: commentData.author,
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
    // This method should return an object with userId and username
    // For example, if you're using Vuex:
    // return this.$store.state.user;
    
    // If you're storing user info in localStorage:
    // return JSON.parse(localStorage.getItem('user'));

    // Placeholder implementation:
    return {
      userId: 2, // Replace with actual user ID
      username: '当前用户', // Replace with actual username
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
  /* background: url('../../assets/movie-info-background.jpg') */

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
}

.buy-btn{
  margin-top: 10px;
  width: 250px;
  font-size: 16px;
  text-align: center;
}

.movie-info-score{
  position: absolute;
  top: 145px;
  left: 342px;
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
  height: 300px;
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

</style>
