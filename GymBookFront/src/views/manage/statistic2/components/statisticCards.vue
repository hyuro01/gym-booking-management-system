<template>
  <div class="statistic-cards">
    <div class="card" v-for="(item, index) in statistics" :key="index">
      <div class="card-content">
        <div class="icon-wrapper">
          <el-icon :size="24" :color="item.color">
            <component :is="item.icon" />
          </el-icon>
        </div>
        <div class="info">
          <div class="value">{{ item.value }}</div>
          <div class="label">{{ item.label }}</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { Calendar, ChatDotRound, Check, Clock } from '@element-plus/icons-vue'

const statistics = [
  {
    label: '预约总数',
    value: '128',
    icon: 'Calendar',
    color: '#409EFF'
  },
  {
    label: '发布评价',
    value: '45',
    icon: 'ChatDotRound',
    color: '#67C23A'
  },
  {
    label: '预约通过',
    value: '98',
    icon: 'Check',
    color: '#E6A23C'
  },
  {
    label: '待审批',
    value: '12',
    icon: 'Clock',
    color: '#F56C6C'
  }
]
</script>

<style lang="scss" scoped>
.statistic-cards {
  display: flex;
  gap: 20px;
  margin-bottom: 20px;
  padding: 20px;
  
  .card {
    flex: 1;
    background: rgba(255, 255, 255, 0.1);
    backdrop-filter: blur(10px);
    border-radius: 16px;
    padding: 20px;
    box-shadow: 0 4px 30px rgba(0, 0, 0, 0.1);
    border: 1px solid rgba(255, 255, 255, 0.3);
    transition: transform 0.3s ease;
    
    &:hover {
      transform: translateY(-5px);
    }
    
    .card-content {
      display: flex;
      align-items: center;
      gap: 16px;
      
      .icon-wrapper {
        width: 48px;
        height: 48px;
        border-radius: 12px;
        background: rgba(255, 255, 255, 0.2);
        display: flex;
        align-items: center;
        justify-content: center;
      }
      
      .info {
        .value {
          font-size: 24px;
          font-weight: bold;
          color: #303133;
          margin-bottom: 4px;
        }
        
        .label {
          font-size: 14px;
          color: #909399;
        }
      }
    }
  }
}
</style> 