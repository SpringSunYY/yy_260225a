<template>
  <div class="app-container home">
    <p class="title">{{ title }}</p>
    <div class="chart-wrapper" v-if="permi">
      <BarLineZoomCharts :chart-data="appointmentStatisticsData" :chart-title="appointmentStatisticsName"/>
    </div>
  </div>
</template>

<script>
import RaddarChart from "@/views/dashboard/RaddarChart.vue";
import BarLineZoomCharts from "@/components/Echarts/BarLineZoomCharts.vue";
import {appointmentStatistics} from "@/api/manage/statistics";
import {checkPermi} from "@/utils/permission";

export default {
  name: "Index",
  components: {BarLineZoomCharts, RaddarChart},
  data() {
    return {
      permi:false,
      title: process.env.VUE_APP_TITLE,
      appointmentStatisticsData: [],
      appointmentStatisticsName: '预约分析'
    };
  },
  mounted() {
    if (checkPermi(['manage:statistics'])) {
      this.permi = true;
      this.getAppointmentStatistics();
    }
  },
  methods: {
    getAppointmentStatistics() {
      appointmentStatistics({}).then(response => {
        this.appointmentStatisticsData = response.data;
      })
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

  .chart-wrapper {
    background: #fff;
    padding: 16px 16px 0;
    margin-bottom: 32px;
    height: 50vh;
  }

}
</style>

