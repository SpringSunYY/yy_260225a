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
import com.lz.manage.model.domain.SeatInfo;
import com.lz.manage.model.vo.seatInfo.SeatInfoVo;
import com.lz.manage.model.dto.seatInfo.SeatInfoQuery;
import com.lz.manage.model.dto.seatInfo.SeatInfoInsert;
import com.lz.manage.model.dto.seatInfo.SeatInfoEdit;
import com.lz.manage.service.ISeatInfoService;
import com.lz.common.utils.poi.ExcelUtil;
import com.lz.common.core.page.TableDataInfo;

/**
 * 座位信息Controller
 *
 * @author YY
 * @date 2026-02-27
 */
@RestController
@RequestMapping("/manage/seatInfo")
public class SeatInfoController extends BaseController
{
    @Resource
    private ISeatInfoService seatInfoService;

    /**
     * 查询座位信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:seatInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(SeatInfoQuery seatInfoQuery)
    {
        SeatInfo seatInfo = SeatInfoQuery.queryToObj(seatInfoQuery);
        startPage();
        List<SeatInfo> list = seatInfoService.selectSeatInfoList(seatInfo);
        List<SeatInfoVo> listVo= list.stream().map(SeatInfoVo::objToVo).collect(Collectors.toList());
        TableDataInfo table = getDataTable(list);
        table.setRows(listVo);
        return table;
    }

    /**
     * 导出座位信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:seatInfo:export')")
    @Log(title = "座位信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SeatInfoQuery seatInfoQuery)
    {
        SeatInfo seatInfo = SeatInfoQuery.queryToObj(seatInfoQuery);
        List<SeatInfo> list = seatInfoService.selectSeatInfoList(seatInfo);
        ExcelUtil<SeatInfo> util = new ExcelUtil<SeatInfo>(SeatInfo.class);
        util.exportExcel(response, list, "座位信息数据");
    }

    /**
     * 获取座位信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:seatInfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        SeatInfo seatInfo = seatInfoService.selectSeatInfoById(id);
        return success(SeatInfoVo.objToVo(seatInfo));
    }

    /**
     * 新增座位信息
     */
    @PreAuthorize("@ss.hasPermi('manage:seatInfo:add')")
    @Log(title = "座位信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SeatInfoInsert seatInfoInsert)
    {
        SeatInfo seatInfo = SeatInfoInsert.insertToObj(seatInfoInsert);
        return toAjax(seatInfoService.insertSeatInfo(seatInfo));
    }

    /**
     * 修改座位信息
     */
    @PreAuthorize("@ss.hasPermi('manage:seatInfo:edit')")
    @Log(title = "座位信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SeatInfoEdit seatInfoEdit)
    {
        SeatInfo seatInfo = SeatInfoEdit.editToObj(seatInfoEdit);
        return toAjax(seatInfoService.updateSeatInfo(seatInfo));
    }

    /**
     * 删除座位信息
     */
    @PreAuthorize("@ss.hasPermi('manage:seatInfo:remove')")
    @Log(title = "座位信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(seatInfoService.deleteSeatInfoByIds(ids));
    }
}
