package com.car.sys.service;

import com.car.sys.domain.News;
import com.car.sys.utils.DataGridView;
import com.car.sys.vo.NewsVo;

public interface NewsService {
    /**
     * 查询所有公告
     * @param newsVo
     * @return
     */
    public DataGridView queryAllNews(NewsVo newsVo);

    void addNews(NewsVo newsVo);

    void updateNews(NewsVo newsVo);

    void deleteNews(Integer id);

    void deleteBatchNews(Integer[] ids);

    News queryNewsById(Integer id);
}
