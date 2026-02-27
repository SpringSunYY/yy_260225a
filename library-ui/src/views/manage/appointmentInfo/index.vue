<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="编号" prop="id">
        <el-input
          v-model="queryParams.id"
          placeholder="请输入编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="图书馆" prop="libraryId">
        <el-input
          v-model="queryParams.libraryId"
          placeholder="请输入图书馆"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="座位" prop="seatId">
        <el-input
          v-model="queryParams.seatId"
          placeholder="请输入座位"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="标题" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入标题"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable>
          <el-option
            v-for="dict in dict.type.manage_appointment_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="创建时间">
        <el-date-picker
          v-model="daterangeCreateTime"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <!--      <el-col :span="1.5">-->
      <!--        <el-button-->
      <!--          type="primary"-->
      <!--          plain-->
      <!--          icon="el-icon-plus"-->
      <!--          size="mini"-->
      <!--          @click="handleAdd"-->
      <!--          v-hasPermi="['manage:appointmentInfo:add']"-->
      <!--        >新增</el-button>-->
      <!--      </el-col>-->
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['manage:appointmentInfo:edit']"
        >修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['manage:appointmentInfo:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['manage:appointmentInfo:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="appointmentInfoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="编号" align="center" v-if="columns[0].visible" prop="id"/>
      <el-table-column label="图书馆" :show-overflow-tooltip="true" align="center" v-if="columns[1].visible"
                       prop="libraryName"/>
      <el-table-column label="座位" :show-overflow-tooltip="true" align="center" v-if="columns[2].visible"
                       prop="seatName"/>
      <el-table-column label="标题" :show-overflow-tooltip="true" align="center" v-if="columns[3].visible" prop="name"/>
      <el-table-column label="开始时间" align="center" v-if="columns[4].visible" prop="startTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.startTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="结束时间" align="center" v-if="columns[5].visible" prop="endTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.endTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" v-if="columns[6].visible" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.manage_appointment_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="备注" :show-overflow-tooltip="true" align="center" v-if="columns[7].visible"
                       prop="remark"/>
      <el-table-column label="创建人" :show-overflow-tooltip="true" align="center" v-if="columns[8].visible"
                       prop="userName"/>
      <el-table-column label="创建时间" align="center" v-if="columns[9].visible" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="更新人" :show-overflow-tooltip="true" align="center" v-if="columns[10].visible"
                       prop="updateBy"/>
      <el-table-column label="更新时间" align="center" v-if="columns[11].visible" prop="updateTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updateTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['manage:appointmentInfo:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['manage:appointmentInfo:remove']"
          >删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改预约信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
<!--        <el-form-item label="图书馆" prop="libraryId">-->
<!--          <el-input v-model="form.libraryId" placeholder="请输入图书馆"/>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="座位" prop="seatId">-->
<!--          <el-input v-model="form.seatId" placeholder="请输入座位"/>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="标题" prop="name">-->
<!--          <el-input v-model="form.name" placeholder="请输入标题"/>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="开始时间" prop="startTime">-->
<!--          <el-date-picker clearable-->
<!--                          v-model="form.startTime"-->
<!--                          type="date"-->
<!--                          value-format="yyyy-MM-dd"-->
<!--                          placeholder="请选择开始时间">-->
<!--          </el-date-picker>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="结束时间" prop="endTime">-->
<!--          <el-date-picker clearable-->
<!--                          v-model="form.endTime"-->
<!--                          type="date"-->
<!--                          value-format="yyyy-MM-dd"-->
<!--                          placeholder="请选择结束时间">-->
<!--          </el-date-picker>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="状态" prop="status">-->
<!--          <el-radio-group v-model="form.status">-->
<!--            <el-radio-->
<!--              v-for="dict in dict.type.manage_appointment_status"-->
<!--              :key="dict.value"-->
<!--              :label="dict.value"-->
<!--            >{{ dict.label }}-->
<!--            </el-radio>-->
<!--          </el-radio-group>-->
<!--        </el-form-item>-->
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容"/>
        </el-form-item>
<!--        <el-form-item label="创建人" prop="userId">-->
<!--          <el-input v-model="form.userId" placeholder="请输入创建人"/>-->
<!--        </el-form-item>-->
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  addAppointmentInfo,
  delAppointmentInfo,
  getAppointmentInfo,
  listAppointmentInfo,
  updateAppointmentInfo
} from "@/api/manage/appointmentInfo";

export default {
  name: "AppointmentInfo",
  dicts: ['manage_appointment_status'],
  data() {
    return {
      //表格展示列
      columns: [
        {key: 0, label: '编号', visible: true},
        {key: 1, label: '图书馆', visible: true},
        {key: 2, label: '座位', visible: true},
        {key: 3, label: '标题', visible: true},
        {key: 4, label: '开始时间', visible: true},
        {key: 5, label: '结束时间', visible: true},
        {key: 6, label: '状态', visible: true},
        {key: 7, label: '备注', visible: true},
        {key: 8, label: '创建人', visible: true},
        {key: 9, label: '创建时间', visible: true},
        {key: 10, label: '更新人', visible: true},
        {key: 11, label: '更新时间', visible: true},
      ],
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 预约信息表格数据
      appointmentInfoList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 更新时间时间范围
      daterangeCreateTime: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        id: null,
        libraryId: null,
        seatId: null,
        name: null,
        status: null,
        createTime: null,
      },
      // 表单参数
      form: {},
      // 导出地址
      exportUrl: 'manage/appointmentInfo/export',
      // 表单校验
      rules: {
        libraryId: [
          {required: true, message: "图书馆不能为空", trigger: "blur"}
        ],
        seatId: [
          {required: true, message: "座位不能为空", trigger: "blur"}
        ],
        name: [
          {required: true, message: "标题不能为空", trigger: "blur"}
        ],
        startTime: [
          {required: true, message: "开始时间不能为空", trigger: "blur"}
        ],
        endTime: [
          {required: true, message: "结束时间不能为空", trigger: "blur"}
        ],
        status: [
          {required: true, message: "状态不能为空", trigger: "change"}
        ],
        userId: [
          {required: true, message: "创建人不能为空", trigger: "blur"}
        ],
        createTime: [
          {required: true, message: "创建时间不能为空", trigger: "blur"}
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询预约信息列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeCreateTime && '' != this.daterangeCreateTime) {
        this.queryParams.params["beginCreateTime"] = this.daterangeCreateTime[0];
        this.queryParams.params["endCreateTime"] = this.daterangeCreateTime[1];
      }
      listAppointmentInfo(this.queryParams).then(response => {
        this.appointmentInfoList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        libraryId: null,
        seatId: null,
        name: null,
        startTime: null,
        endTime: null,
        status: null,
        remark: null,
        userId: null,
        createTime: null,
        updateBy: null,
        updateTime: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.daterangeCreateTime = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加预约信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getAppointmentInfo(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改预约信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateAppointmentInfo(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addAppointmentInfo(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除预约信息编号为"' + ids + '"的数据项？').then(function () {
        return delAppointmentInfo(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download(this.exportUrl, {
        ...this.queryParams
      }, `appointmentInfo_${new Date().getTime()}.xlsx`)
    },
  }
};
</script>
