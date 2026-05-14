<template>
  <div class="app-container home">
    <p class="title">{{ title }}</p>
    <div class="search-wrapper" v-if="permi">
      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" label-width="68px">
        <el-form-item label="时间范围">
          <el-date-picker
            v-model="monthRange"
            type="monthrange"
            range-separator="-"
            start-placeholder="开始月份"
            end-placeholder="结束月份"
            value-format="yyyyMM"
            style="width: 240px"
          ></el-date-picker>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div class="chart-wrapper" v-if="permi">
      <div class="pie-row">
        <el-card class="chart-card" shadow="hover">
          <PieRoseCharts :chart-data="appointmentStatisticsData" :chart-title="appointmentStatisticsRateName"/>
        </el-card>
        <el-card class="chart-card" shadow="hover">
          <PieRoseHollowCharts/>
        </el-card>
      </div>
      <el-card class="chart-card chart-card-full" shadow="hover">
        <BarLineZoomCharts style="height: 380px" :chart-data="appointmentStatisticsData"
                           :chart-title="appointmentStatisticsName"/>
      </el-card>
    </div>
  </div>
</template>

<script>
import BarLineZoomCharts from "@/components/Echarts/BarLineZoomCharts.vue";
import {appointmentStatistics} from "@/api/manage/statistics";
import {checkPermi} from "@/utils/permission";
import PieRoseHollowCharts from "@/components/Echarts/PieRoseHollowCharts.vue";
import PieRoseCharts from "@/components/Echarts/PieRoseCharts.vue";

export default {
  name: "Index",
  components: {PieRoseCharts, PieRoseHollowCharts, BarLineZoomCharts},
  data() {
    return {
      permi: false,
      title: process.env.VUE_APP_TITLE,
      appointmentStatisticsData: [],
      appointmentStatisticsName: '预约分析',
      appointmentStatisticsRateName: '预约占比',
      monthRange: [],
      queryParams: {}
    };
  },
  mounted() {
    if (checkPermi(['manage:statistics'])) {
      this.permi = true;
      this.initDefaultMonthRange();
      this.getAppointmentStatistics();
    }
  },
  methods: {
    initDefaultMonthRange() {
      const now = new Date();
      const currentYear = now.getFullYear();
      const currentMonth = now.getMonth() + 1;
      const startMonth = new Date(currentYear, currentMonth - 3, 1);
      const fmt = (y, m) => `${y}${String(m).padStart(2, '0')}`;
      this.monthRange = [fmt(startMonth.getFullYear(), startMonth.getMonth() + 1), fmt(currentYear, currentMonth)];
    },
    getAppointmentStatistics() {
      if (this.monthRange && this.monthRange.length === 2) {
        this.queryParams = {
          startTime: this.monthRange[0],
          endTime: this.monthRange[1]
        };
      } else {
        this.queryParams = {};
      }
      appointmentStatistics(this.queryParams).then(response => {
        this.appointmentStatisticsData = response.data;
      });
    },
    handleQuery() {
      this.getAppointmentStatistics();
    },
    resetQuery() {
      this.initDefaultMonthRange();
      this.getAppointmentStatistics();
    }
  }
};
</script>

<style scoped lang="scss">
.home {
  .title {
    text-align: center;
    font-size: 48px;
    margin-bottom: 20px;
    font-weight: bold;
  }

  .search-wrapper {
    background: #fff;
    padding: 16px 16px 0;
    margin-bottom: 0;
  }

  .chart-wrapper {
    background: #fff;
    padding: 16px;
    margin-bottom: 32px;
    display: flex;
    flex-direction: column;
    gap: 16px;

    .pie-row {
      display: flex;
      gap: 16px;

      .chart-card {
        flex: 1;
        min-width: 0;
        height: 380px;

        ::v-deep .el-card__body {
          height: 100%;
        }
      }
    }

    .chart-card-full {
      flex: 1;
      min-width: 0;

      ::v-deep .el-card__body {
        height: 100%;
      }
    }
  }

}
</style>

