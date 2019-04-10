package com.igeek.service;

import com.igeek.daoimpl.DuanziDaoImpl;
import com.igeek.pojo.Duanzi;

import java.util.ArrayList;

/**
 * @Author:黄广
 * @Description:段子的业务
 * @Date:Created in 下午3:07 19-1-4
 * @Modified By:
 */
public class DuanziService {


    /**
     * 批量增加段子
     *
     * @param listduanzi
     */
    public void addAllDuanzi(ArrayList<Duanzi> listduanzi) {
        DuanziDaoImpl dao = new DuanziDaoImpl();
        dao.addAllDuanzi(listduanzi);
    }


    /**
     * 根据用户id获取他所有的段子
     *
     * @param id
     */
    public ArrayList<Duanzi> findAllById(String id) {
        DuanziDaoImpl dao = new DuanziDaoImpl();
        return dao.findAllById(id);
    }


}
