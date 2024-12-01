<template>
  <div class="ai-chat-box">
    <div class="chat-header">
      <h3>影响力智能影院助手</h3>
    </div>
    <div class="chat-content" ref="chatContent">
      <div v-for="(message, index) in messages" :key="index" :class="['message', message.role]">
        <span v-html="renderMarkdown(message.content)"></span>
      </div>
    </div>
    <div class="chat-input">
      <input v-model="userInput" @keyup.enter="sendMessage" placeholder="输入你的消息..." />
      <button @click="sendMessage">发送</button>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import MarkdownIt from 'markdown-it';

export default {
  name: "AiChatBox",
  data() {
    return {
      messages: [
        { role: "system", content: "你好，我是影响力智能影院助手，为你提供电影问答与推荐服务~！" },
      ],
      userInput: "",
      md: new MarkdownIt()
    };
  },
  methods: {
    renderMarkdown(content) {
      return this.md.render(content);
    },
    async sendMessage() {
      if (!this.userInput.trim()) return;

      this.messages.push({ role: "user", content: this.userInput });
      const input = this.userInput;
      this.userInput = "";

      this.scrollToBottom(); // 发送消息后滚动到底部

      try {
        const response = await axios.post(
            // 'https://api.deepseek.com/chat/completions',
            'http://localhost:8080/v1/chat/completions',

            {
              messages: this.messages,
              model: "deepseek-chat",
            },
            {
              headers: {
                'Authorization': `Bearer sk-31cb341fe1ac49a196e2aa4984b8745f`,
                'Content-Type': 'application/json',
              }
            }
        );

        const aiResponse = response.data.choices[0].message.content;
        this.streamOutput(aiResponse);

      } catch (error) {
        console.error("错误:", error);
        this.messages.push({ role: "assistant", content: "抱歉，出现了错误。\n" + error });
      }
    },
    streamOutput(content) {
      let currentContent = '';
      let index = 0;
      const interval = setInterval(() => {
        if (index < content.length) {
          currentContent += content[index];
          if (this.messages[this.messages.length - 1].role !== "assistant") {
            this.messages.push({ role: "assistant", content: currentContent });
          } else {
            this.messages[this.messages.length - 1].content = currentContent;
          }
          index++;
          this.scrollToBottom(); // 每次更新内容时滚动到底部
        } else {
          clearInterval(interval);
        }
      }, 50);
    },
    scrollToBottom() {
      // 滚动到底部
      this.$nextTick(() => {
        const chatContent = this.$refs.chatContent;
        if (chatContent) {
          chatContent.scrollTop = chatContent.scrollHeight;
        }
      });
    }
  },
  watch: {
    messages() {
      this.scrollToBottom(); // 监听 messages 更新，并自动滚动到底部
    }
  }
};
</script>

<style scoped>
.ai-chat-box {
  border: 1px solid #ddd;
  border-radius: 10px;
  padding: 16px;
  width: 100%;
  max-width: 600px;
  height: 500px;
  overflow: hidden;
  background-color: #f7f7f8; /* 白色背景替换为浅灰 */
  display: flex;
  flex-direction: column;
  box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1); /* 添加微弱的阴影 */
}

.chat-header h3 {
  margin: 5px;
  font-size: 18px;
  color: #ef4238; /* 红色标题，与网站的标题配色一致 */
  text-align: center;
  font-weight: bold;
}

.chat-content {
  flex: 1;
  max-height: 400px;
  overflow-y: auto;
  padding: 10px;
  background-color: #ffffff;
  border-radius: 8px;
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.message {
  padding: 2px 15px;
  border-radius: 8px;
  max-width: 70%;
  word-wrap: break-word;
  font-size: 14px;
  line-height: 1.6;
}

.message.user {
  background-color: #ffe6e1; /* 浅红色，柔和但显眼，适合与网站整体配色搭配 */
  color: #333; /* 字体颜色 */
  align-self: flex-end;
  text-align: left;
  border-radius: 10px 10px 0px 10px; /* 右上角圆角效果 */
  padding: 2px 15px;
  font-size: 14px;
  line-height: 1.6;
}

.message.assistant {
  background-color: #f1f1f1; /* 浅灰色用于AI消息 */
  color: #333;
  align-self: flex-start;
  text-align: left;
  border-radius: 10px 10px 10px 0px;
}

.chat-input {
  display: flex;
  padding: 10px 0;
}

.chat-input input {
  flex: 1;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 4px;
  margin-right: 8px;
  font-size: 14px;
}

.chat-input button {
  padding: 10px 16px;
  border: none;
  background-color: #ef4238; /* 红色按钮，符合主题 */
  color: white;
  border-radius: 4px;
  cursor: pointer;
  font-size: 14px;
  font-weight: bold;
}

.chat-input button:hover {
  background-color: #d83531; /* 深红色，用于按钮悬停效果 */
}

</style>
