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
import com.lz.manage.model.domain.PartitionInfo;
import com.lz.manage.model.vo.partitionInfo.PartitionInfoVo;
import com.lz.manage.model.dto.partitionInfo.PartitionInfoQuery;
import com.lz.manage.model.dto.partitionInfo.PartitionInfoInsert;
import com.lz.manage.model.dto.partitionInfo.PartitionInfoEdit;
import com.lz.manage.service.IPartitionInfoService;
import com.lz.common.utils.poi.ExcelUtil;
import com.lz.common.core.page.TableDataInfo;

/**
 * 分区信息Controller
 *
 * @author YY
 * @date 2026-05-14
 */
@RestController
@RequestMapping("/manage/partitionInfo")
public class PartitionInfoController extends BaseController
{
    @Resource
    private IPartitionInfoService partitionInfoService;

    /**
     * 查询分区信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:partitionInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(PartitionInfoQuery partitionInfoQuery)
    {
        PartitionInfo partitionInfo = PartitionInfoQuery.queryToObj(partitionInfoQuery);
        startPage();
        List<PartitionInfo> list = partitionInfoService.selectPartitionInfoList(partitionInfo);
        List<PartitionInfoVo> listVo= list.stream().map(PartitionInfoVo::objToVo).collect(Collectors.toList());
        TableDataInfo table = getDataTable(list);
        table.setRows(listVo);
        return table;
    }

    /**
     * 导出分区信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:partitionInfo:export')")
    @Log(title = "分区信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PartitionInfoQuery partitionInfoQuery)
    {
        PartitionInfo partitionInfo = PartitionInfoQuery.queryToObj(partitionInfoQuery);
        List<PartitionInfo> list = partitionInfoService.selectPartitionInfoList(partitionInfo);
        ExcelUtil<PartitionInfo> util = new ExcelUtil<PartitionInfo>(PartitionInfo.class);
        util.exportExcel(response, list, "分区信息数据");
    }

    /**
     * 获取分区信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:partitionInfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        PartitionInfo partitionInfo = partitionInfoService.selectPartitionInfoById(id);
        return success(PartitionInfoVo.objToVo(partitionInfo));
    }

    /**
     * 新增分区信息
     */
    @PreAuthorize("@ss.hasPermi('manage:partitionInfo:add')")
    @Log(title = "分区信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PartitionInfoInsert partitionInfoInsert)
    {
        PartitionInfo partitionInfo = PartitionInfoInsert.insertToObj(partitionInfoInsert);
        return toAjax(partitionInfoService.insertPartitionInfo(partitionInfo));
    }

    /**
     * 修改分区信息
     */
    @PreAuthorize("@ss.hasPermi('manage:partitionInfo:edit')")
    @Log(title = "分区信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PartitionInfoEdit partitionInfoEdit)
    {
        PartitionInfo partitionInfo = PartitionInfoEdit.editToObj(partitionInfoEdit);
        return toAjax(partitionInfoService.updatePartitionInfo(partitionInfo));
    }

    /**
     * 删除分区信息
     */
    @PreAuthorize("@ss.hasPermi('manage:partitionInfo:remove')")
    @Log(title = "分区信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(partitionInfoService.deletePartitionInfoByIds(ids));
    }
}
