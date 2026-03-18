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
import com.lz.manage.model.domain.SignInfo;
import com.lz.manage.model.vo.signInfo.SignInfoVo;
import com.lz.manage.model.dto.signInfo.SignInfoQuery;
import com.lz.manage.model.dto.signInfo.SignInfoInsert;
import com.lz.manage.model.dto.signInfo.SignInfoEdit;
import com.lz.manage.service.ISignInfoService;
import com.lz.common.utils.poi.ExcelUtil;
import com.lz.common.core.page.TableDataInfo;

/**
 * 签到信息Controller
 *
 * @author YY
 * @date 2026-03-18
 */
@RestController
@RequestMapping("/manage/signInfo")
public class SignInfoController extends BaseController
{
    @Resource
    private ISignInfoService signInfoService;

    /**
     * 查询签到信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:signInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(SignInfoQuery signInfoQuery)
    {
        SignInfo signInfo = SignInfoQuery.queryToObj(signInfoQuery);
        startPage();
        List<SignInfo> list = signInfoService.selectSignInfoList(signInfo);
        List<SignInfoVo> listVo= list.stream().map(SignInfoVo::objToVo).collect(Collectors.toList());
        TableDataInfo table = getDataTable(list);
        table.setRows(listVo);
        return table;
    }

    /**
     * 导出签到信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:signInfo:export')")
    @Log(title = "签到信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SignInfoQuery signInfoQuery)
    {
        SignInfo signInfo = SignInfoQuery.queryToObj(signInfoQuery);
        List<SignInfo> list = signInfoService.selectSignInfoList(signInfo);
        ExcelUtil<SignInfo> util = new ExcelUtil<SignInfo>(SignInfo.class);
        util.exportExcel(response, list, "签到信息数据");
    }

    /**
     * 获取签到信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:signInfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        SignInfo signInfo = signInfoService.selectSignInfoById(id);
        return success(SignInfoVo.objToVo(signInfo));
    }

    /**
     * 新增签到信息
     */
    @PreAuthorize("@ss.hasPermi('manage:signInfo:add')")
    @Log(title = "签到信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SignInfoInsert signInfoInsert)
    {
        SignInfo signInfo = SignInfoInsert.insertToObj(signInfoInsert);
        return toAjax(signInfoService.insertSignInfo(signInfo));
    }

    /**
     * 修改签到信息
     */
    @PreAuthorize("@ss.hasPermi('manage:signInfo:edit')")
    @Log(title = "签到信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SignInfoEdit signInfoEdit)
    {
        SignInfo signInfo = SignInfoEdit.editToObj(signInfoEdit);
        return toAjax(signInfoService.updateSignInfo(signInfo));
    }

    /**
     * 删除签到信息
     */
    @PreAuthorize("@ss.hasPermi('manage:signInfo:remove')")
    @Log(title = "签到信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(signInfoService.deleteSignInfoByIds(ids));
    }
}
