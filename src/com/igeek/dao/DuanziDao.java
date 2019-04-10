package com.igeek.dao;

import com.igeek.pojo.Duanzi;

import java.util.ArrayList;

/**
 * @Author:黄广
 * @Description:有关段子操作的接口类
 * @Date:Created in 下午3:04 19-1-4
 * @Modified By:
 */
public interface DuanziDao {

    /**
     * 批量增加段子
     *
     * @param listduanzi
     */
    void addAllDuanzi(ArrayList<Duanzi> listduanzi);

    /**
     * 根据id寻找段子
     *
     * @param id
     * @return
     */
    ArrayList<Duanzi> findAllById(String id);
}
