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
import com.lz.manage.model.domain.LibraryInfo;
import com.lz.manage.model.vo.libraryInfo.LibraryInfoVo;
import com.lz.manage.model.dto.libraryInfo.LibraryInfoQuery;
import com.lz.manage.model.dto.libraryInfo.LibraryInfoInsert;
import com.lz.manage.model.dto.libraryInfo.LibraryInfoEdit;
import com.lz.manage.service.ILibraryInfoService;
import com.lz.common.utils.poi.ExcelUtil;
import com.lz.common.core.page.TableDataInfo;

/**
 * 图书馆信息Controller
 *
 * @author YY
 * @date 2026-02-27
 */
@RestController
@RequestMapping("/manage/libraryInfo")
public class LibraryInfoController extends BaseController
{
    @Resource
    private ILibraryInfoService libraryInfoService;

    /**
     * 查询图书馆信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:libraryInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(LibraryInfoQuery libraryInfoQuery)
    {
        LibraryInfo libraryInfo = LibraryInfoQuery.queryToObj(libraryInfoQuery);
        startPage();
        List<LibraryInfo> list = libraryInfoService.selectLibraryInfoList(libraryInfo);
        List<LibraryInfoVo> listVo= list.stream().map(LibraryInfoVo::objToVo).collect(Collectors.toList());
        TableDataInfo table = getDataTable(list);
        table.setRows(listVo);
        return table;
    }

    /**
     * 导出图书馆信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:libraryInfo:export')")
    @Log(title = "图书馆信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, LibraryInfoQuery libraryInfoQuery)
    {
        LibraryInfo libraryInfo = LibraryInfoQuery.queryToObj(libraryInfoQuery);
        List<LibraryInfo> list = libraryInfoService.selectLibraryInfoList(libraryInfo);
        ExcelUtil<LibraryInfo> util = new ExcelUtil<LibraryInfo>(LibraryInfo.class);
        util.exportExcel(response, list, "图书馆信息数据");
    }

    /**
     * 获取图书馆信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:libraryInfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        LibraryInfo libraryInfo = libraryInfoService.selectLibraryInfoById(id);
        return success(LibraryInfoVo.objToVo(libraryInfo));
    }

    /**
     * 新增图书馆信息
     */
    @PreAuthorize("@ss.hasPermi('manage:libraryInfo:add')")
    @Log(title = "图书馆信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody LibraryInfoInsert libraryInfoInsert)
    {
        LibraryInfo libraryInfo = LibraryInfoInsert.insertToObj(libraryInfoInsert);
        return toAjax(libraryInfoService.insertLibraryInfo(libraryInfo));
    }

    /**
     * 修改图书馆信息
     */
    @PreAuthorize("@ss.hasPermi('manage:libraryInfo:edit')")
    @Log(title = "图书馆信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody LibraryInfoEdit libraryInfoEdit)
    {
        LibraryInfo libraryInfo = LibraryInfoEdit.editToObj(libraryInfoEdit);
        return toAjax(libraryInfoService.updateLibraryInfo(libraryInfo));
    }

    /**
     * 删除图书馆信息
     */
    @PreAuthorize("@ss.hasPermi('manage:libraryInfo:remove')")
    @Log(title = "图书馆信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(libraryInfoService.deleteLibraryInfoByIds(ids));
    }
}
