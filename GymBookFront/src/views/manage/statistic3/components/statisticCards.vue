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
import { Document, Star, Calendar, Trophy } from '@element-plus/icons-vue'

const statistics = [
  {
    label: '课程申请数',
    value: '4',
    icon: 'Document',
    color: '#409EFF'
  },
  {
    label: '课程通过数',
    value: '3',
    icon: 'Trophy',
    color: '#67C23A'
  },
  {
    label: '被预约总数',
    value: '15',
    icon: 'Calendar',
    color: '#E6A23C'
  },
  {
    label: '平均评分',
    value: '4.2',
    icon: 'Star',
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