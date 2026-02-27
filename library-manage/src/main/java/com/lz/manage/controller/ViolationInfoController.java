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
import com.lz.manage.model.domain.ViolationInfo;
import com.lz.manage.model.vo.violationInfo.ViolationInfoVo;
import com.lz.manage.model.dto.violationInfo.ViolationInfoQuery;
import com.lz.manage.model.dto.violationInfo.ViolationInfoInsert;
import com.lz.manage.model.dto.violationInfo.ViolationInfoEdit;
import com.lz.manage.service.IViolationInfoService;
import com.lz.common.utils.poi.ExcelUtil;
import com.lz.common.core.page.TableDataInfo;

/**
 * 违规信息Controller
 *
 * @author YY
 * @date 2026-02-27
 */
@RestController
@RequestMapping("/manage/violationInfo")
public class ViolationInfoController extends BaseController
{
    @Resource
    private IViolationInfoService violationInfoService;

    /**
     * 查询违规信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:violationInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(ViolationInfoQuery violationInfoQuery)
    {
        ViolationInfo violationInfo = ViolationInfoQuery.queryToObj(violationInfoQuery);
        startPage();
        List<ViolationInfo> list = violationInfoService.selectViolationInfoList(violationInfo);
        List<ViolationInfoVo> listVo= list.stream().map(ViolationInfoVo::objToVo).collect(Collectors.toList());
        TableDataInfo table = getDataTable(list);
        table.setRows(listVo);
        return table;
    }

    /**
     * 导出违规信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:violationInfo:export')")
    @Log(title = "违规信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ViolationInfoQuery violationInfoQuery)
    {
        ViolationInfo violationInfo = ViolationInfoQuery.queryToObj(violationInfoQuery);
        List<ViolationInfo> list = violationInfoService.selectViolationInfoList(violationInfo);
        ExcelUtil<ViolationInfo> util = new ExcelUtil<ViolationInfo>(ViolationInfo.class);
        util.exportExcel(response, list, "违规信息数据");
    }

    /**
     * 获取违规信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:violationInfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        ViolationInfo violationInfo = violationInfoService.selectViolationInfoById(id);
        return success(ViolationInfoVo.objToVo(violationInfo));
    }

    /**
     * 新增违规信息
     */
    @PreAuthorize("@ss.hasPermi('manage:violationInfo:add')")
    @Log(title = "违规信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ViolationInfoInsert violationInfoInsert)
    {
        ViolationInfo violationInfo = ViolationInfoInsert.insertToObj(violationInfoInsert);
        return toAjax(violationInfoService.insertViolationInfo(violationInfo));
    }

    /**
     * 修改违规信息
     */
    @PreAuthorize("@ss.hasPermi('manage:violationInfo:edit')")
    @Log(title = "违规信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ViolationInfoEdit violationInfoEdit)
    {
        ViolationInfo violationInfo = ViolationInfoEdit.editToObj(violationInfoEdit);
        return toAjax(violationInfoService.updateViolationInfo(violationInfo));
    }

    /**
     * 删除违规信息
     */
    @PreAuthorize("@ss.hasPermi('manage:violationInfo:remove')")
    @Log(title = "违规信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(violationInfoService.deleteViolationInfoByIds(ids));
    }
}
