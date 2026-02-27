package com.lz.manage.controller;

import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.lz.common.annotation.Log;
import com.lz.common.core.controller.BaseController;
import com.lz.common.core.domain.AjaxResult;
import com.lz.common.enums.BusinessType;
import com.lz.manage.model.domain.AppointmentInfo;
import com.lz.manage.model.vo.appointmentInfo.AppointmentInfoVo;
import com.lz.manage.model.dto.appointmentInfo.AppointmentInfoQuery;
import com.lz.manage.model.dto.appointmentInfo.AppointmentInfoInsert;
import com.lz.manage.model.dto.appointmentInfo.AppointmentInfoEdit;
import com.lz.manage.service.IAppointmentInfoService;
import com.lz.common.utils.poi.ExcelUtil;
import com.lz.common.core.page.TableDataInfo;

/**
 * 预约信息Controller
 *
 * @author YY
 * @date 2026-02-27
 */
@RestController
@RequestMapping("/manage/appointmentInfo")
public class AppointmentInfoController extends BaseController
{
    @Resource
    private IAppointmentInfoService appointmentInfoService;

    /**
     * 查询预约信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:appointmentInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(AppointmentInfoQuery appointmentInfoQuery)
    {
        AppointmentInfo appointmentInfo = AppointmentInfoQuery.queryToObj(appointmentInfoQuery);
        startPage();
        List<AppointmentInfo> list = appointmentInfoService.selectAppointmentInfoList(appointmentInfo);
        List<AppointmentInfoVo> listVo= list.stream().map(AppointmentInfoVo::objToVo).collect(Collectors.toList());
        TableDataInfo table = getDataTable(list);
        table.setRows(listVo);
        return table;
    }

    /**
     * 导出预约信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:appointmentInfo:export')")
    @Log(title = "预约信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AppointmentInfoQuery appointmentInfoQuery)
    {
        AppointmentInfo appointmentInfo = AppointmentInfoQuery.queryToObj(appointmentInfoQuery);
        List<AppointmentInfo> list = appointmentInfoService.selectAppointmentInfoList(appointmentInfo);
        ExcelUtil<AppointmentInfo> util = new ExcelUtil<AppointmentInfo>(AppointmentInfo.class);
        util.exportExcel(response, list, "预约信息数据");
    }

    /**
     * 获取预约信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:appointmentInfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        AppointmentInfo appointmentInfo = appointmentInfoService.selectAppointmentInfoById(id);
        return success(AppointmentInfoVo.objToVo(appointmentInfo));
    }

    /**
     * 新增预约信息
     */
    @PreAuthorize("@ss.hasPermi('manage:appointmentInfo:add')")
    @Log(title = "预约信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AppointmentInfoInsert appointmentInfoInsert)
    {
        AppointmentInfo appointmentInfo = AppointmentInfoInsert.insertToObj(appointmentInfoInsert);
        return toAjax(appointmentInfoService.insertAppointmentInfo(appointmentInfo));
    }

    /**
     * 修改预约信息
     */
    @PreAuthorize("@ss.hasPermi('manage:appointmentInfo:edit')")
    @Log(title = "预约信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AppointmentInfoEdit appointmentInfoEdit)
    {
        AppointmentInfo appointmentInfo = AppointmentInfoEdit.editToObj(appointmentInfoEdit);
        return toAjax(appointmentInfoService.updateAppointmentInfo(appointmentInfo));
    }

    /**
     * 删除预约信息
     */
    @PreAuthorize("@ss.hasPermi('manage:appointmentInfo:remove')")
    @Log(title = "预约信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(appointmentInfoService.deleteAppointmentInfoByIds(ids));
    }
}
