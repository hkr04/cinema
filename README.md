## 影响力
### 本地部署
依赖：
- node.js 16.20.2
- MySql 5.7.24

前端：
- 在终端中打开`CinemaManagerUserVue/CinemaManagerAdminVue`，执行`npm install`
- 下载完依赖项后，执行`npm run serve`，即可看到前端的入口：
```
  App running at:
  - Local:   http://localhost:9232/ 
  - Network: http://192.168.1.107:9232/
```
> 如果此前由较高版本的 node.js 降级到 16，可能会出现有关 openssl 的问题。
> 对于单次运行的解决方法，可在对应命令行中执行：
```
MacOS/Linux
export NODE_OPTIONS=--openssl-legacy-provider

Windows
set NODE_OPTIONS=--openssl-legacy-provider
```

后端：
- 新建数据库`cinema_manager`，导入`CinemaManagerApi/cinema_manager.sql`
- 在`CinemaManagerApi/src/main/resources/application.yml`中修改数据库的用户名与密码
- 确保数据库已连接，运行`CinemaManagerApi/src/main/java/com/influence/cm/CinemaManagerApplication.java`
- 此时终端若无报错，前端页面应正常显示电影封面等数据


chatbot：
- `npm install openai markdown-it axios`
- 添加`CinemaManagerUserVue/src/components/AiChatBox.vue`
- 修改 `CinemaManagerUserVue/src/views/home/Home.vue`