package com.lz.manage.mapper;

import java.util.List;
import com.lz.manage.model.domain.LibraryInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 图书馆信息Mapper接口
 * 
 * @author YY
 * @date 2026-02-27
 */
public interface LibraryInfoMapper extends BaseMapper<LibraryInfo>
{
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
     * 删除图书馆信息
     * 
     * @param id 图书馆信息主键
     * @return 结果
     */
    public int deleteLibraryInfoById(Long id);

    /**
     * 批量删除图书馆信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteLibraryInfoByIds(Long[] ids);
}
