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
        <el-select
          v-model="queryParams.libraryId"
          filterable
          remote
          reserve-keyword
          placeholder="请输入图书馆名称"
          :remote-method="remoteGetLibraryList"
          :loading="libraryLoading">
          <el-option
            v-for="item in libraryList"
            :key="item.id"
            :label="item.name"
            :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable>
          <el-option
            v-for="dict in dict.type.manage_seat_status"
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
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['manage:seatInfo:add']"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['manage:seatInfo:edit']"
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
          v-hasPermi="['manage:seatInfo:remove']"
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
          v-hasPermi="['manage:seatInfo:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="seatInfoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="编号" align="center" v-if="columns[0].visible" prop="id"/>
      <el-table-column label="图书馆" :show-overflow-tooltip="true" align="center" v-if="columns[1].visible"
                       prop="libraryName"/>
      <el-table-column label="名称" :show-overflow-tooltip="true" align="center" v-if="columns[2].visible" prop="name"/>
      <el-table-column label="状态" align="center" v-if="columns[3].visible" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.manage_seat_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="图片" align="center" v-if="columns[4].visible" prop="image" width="100">
        <template slot-scope="scope">
          <image-preview :src="scope.row.image" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="描述" :show-overflow-tooltip="true" align="center" v-if="columns[5].visible"
                       prop="description"/>
      <el-table-column label="备注" :show-overflow-tooltip="true" align="center" v-if="columns[6].visible"
                       prop="remark"/>
      <el-table-column label="创建人" :show-overflow-tooltip="true" align="center" v-if="columns[7].visible"
                       prop="userName"/>
      <el-table-column label="创建时间" align="center" v-if="columns[8].visible" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="更新人" :show-overflow-tooltip="true" align="center" v-if="columns[9].visible"
                       prop="updateBy"/>
      <el-table-column label="更新时间" align="center" v-if="columns[10].visible" prop="updateTime" width="180">
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
            v-hasPermi="['manage:seatInfo:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['manage:seatInfo:remove']"
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

    <!-- 添加或修改座位信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="图书馆" prop="libraryId">
          <el-select
            v-model="form.libraryId"
            filterable
            remote
            reserve-keyword
            placeholder="请输入图书馆名称"
            :remote-method="remoteGetLibraryList"
            :loading="libraryLoading">
            <el-option
              v-for="item in libraryList"
              :key="item.id"
              :label="item.name"
              :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入名称"/>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio
              v-for="dict in dict.type.manage_seat_status"
              :key="dict.value"
              :label="dict.value"
            >{{ dict.label }}
            </el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="图片" prop="image">
          <image-upload v-model="form.image"/>
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input v-model="form.description" type="textarea" placeholder="请输入内容"/>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {addSeatInfo, delSeatInfo, getSeatInfo, listSeatInfo, updateSeatInfo} from "@/api/manage/seatInfo";
import {listLibraryInfo} from "@/api/manage/libraryInfo";

export default {
  name: "SeatInfo",
  dicts: ['manage_seat_status'],
  data() {
    return {
      //图书馆信息
      libraryList: [],
      libraryLoading: false,
      libraryQuery: {
        pageNum: 1,
        pageSize: 30,
        name: null
      },
      //表格展示列
      columns: [
        {key: 0, label: '编号', visible: true},
        {key: 1, label: '图书馆', visible: true},
        {key: 2, label: '名称', visible: true},
        {key: 3, label: '状态', visible: true},
        {key: 4, label: '图片', visible: true},
        {key: 5, label: '描述', visible: true},
        {key: 6, label: '备注', visible: true},
        {key: 7, label: '创建人', visible: true},
        {key: 8, label: '创建时间', visible: true},
        {key: 9, label: '更新人', visible: false},
        {key: 10, label: '更新时间', visible: false},
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
      // 座位信息表格数据
      seatInfoList: [],
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
        name: null,
        status: null,
        createTime: null,
      },
      // 表单参数
      form: {},
      // 导出地址
      exportUrl: 'manage/seatInfo/export',
      // 表单校验
      rules: {
        libraryId: [
          {required: true, message: "图书馆不能为空", trigger: "blur"}
        ],
        name: [
          {required: true, message: "名称不能为空", trigger: "blur"}
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
    this.getLibraryList();
  },
  methods: {
    /** 获取图书馆信息*/
    getLibraryList() {
      this.libraryLoading = true
      listLibraryInfo(this.libraryQuery).then(response => {
        this.libraryList = response.rows;
        this.libraryLoading = false
      })
    },
    /** 远程获取图书馆信息 */
    remoteGetLibraryList(query) {
      this.libraryQuery.name = query;
      this.getLibraryList();
    },
    /** 查询座位信息列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeCreateTime && '' != this.daterangeCreateTime) {
        this.queryParams.params["beginCreateTime"] = this.daterangeCreateTime[0];
        this.queryParams.params["endCreateTime"] = this.daterangeCreateTime[1];
      }
      listSeatInfo(this.queryParams).then(response => {
        this.seatInfoList = response.rows;
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
        name: null,
        status: null,
        image: null,
        description: null,
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
      this.title = "添加座位信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getSeatInfo(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改座位信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateSeatInfo(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addSeatInfo(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除座位信息编号为"' + ids + '"的数据项？').then(function () {
        return delSeatInfo(ids);
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
      }, `seatInfo_${new Date().getTime()}.xlsx`)
    },
  }
};
</script>
