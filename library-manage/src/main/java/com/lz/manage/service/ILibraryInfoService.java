package com.lz.manage.service;

import java.util.List;
import com.lz.manage.model.domain.LibraryInfo;
import com.lz.manage.model.vo.libraryInfo.LibraryInfoVo;
import com.lz.manage.model.dto.libraryInfo.LibraryInfoQuery;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
/**
 * 图书馆信息Service接口
 * 
 * @author YY
 * @date 2026-02-27
 */
public interface ILibraryInfoService extends IService<LibraryInfo>
{
    //region mybatis代码
    /**
     * 查询图书馆信息
     * 
     * @param id 图书馆信息主键
     * @return 图书馆信息
     */
    public LibraryInfo selectLibraryInfoById(Long id);

    /**
     * 查询图书馆信息列表
     * 
     * @param libraryInfo 图书馆信息
     * @return 图书馆信息集合
     */
    public List<LibraryInfo> selectLibraryInfoList(LibraryInfo libraryInfo);

    /**
     * 新增图书馆信息
     * 
     * @param libraryInfo 图书馆信息
     * @return 结果
     */
    public int insertLibraryInfo(LibraryInfo libraryInfo);

    /**
     * 修改图书馆信息
     * 
     * @param libraryInfo 图书馆信息
     * @return 结果
     */
    public int updateLibraryInfo(LibraryInfo libraryInfo);

    /**
     * 批量删除图书馆信息
     * 
     * @param ids 需要删除的图书馆信息主键集合
     * @return 结果
     */
    public int deleteLibraryInfoByIds(Long[] ids);

    /**
     * 删除图书馆信息信息
     * 
     * @param id 图书馆信息主键
     * @return 结果
     */
    public int deleteLibraryInfoById(Long id);
    //endregion
    /**
     * 获取查询条件
     *
     * @param libraryInfoQuery 查询条件对象
     * @return 查询条件
     */
    QueryWrapper<LibraryInfo> getQueryWrapper(LibraryInfoQuery libraryInfoQuery);

    /**
     * 转换vo
     *
     * @param libraryInfoList LibraryInfo集合
     * @return LibraryInfoVO集合
     */
    List<LibraryInfoVo> convertVoList(List<LibraryInfo> libraryInfoList);
}
