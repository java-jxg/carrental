package com.car.sys.service.impl;

import com.car.sys.domain.News;
import com.car.sys.domain.NewsExample;
import com.car.sys.mapper.NewsMapper;
import com.car.sys.service.NewsService;
import com.car.sys.utils.DataGridView;
import com.car.sys.vo.NewsVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class NewsServiceImpl implements NewsService {
    @Autowired
    private NewsMapper newsMapper;
    @Override
    public DataGridView queryAllNews(NewsVo newsVo) {
        NewsExample example = new NewsExample();
        NewsExample.Criteria criteria = example.createCriteria();
        if(newsVo.getTitle()!=null && newsVo.getTitle()!=""){
            criteria.andTitleLike("%"+newsVo.getTitle()+"%");
        }
        if(newsVo.getContent()!=null && newsVo.getContent()!=""){
            criteria.andContentLike("%"+newsVo.getContent()+"%");
        }
        if(newsVo.getCreatetime()!=null){
            criteria.andCreatetimeGreaterThanOrEqualTo(newsVo.getCreatetime());
        }
        if(newsVo.getOpername()!=null && newsVo.getOpername()!=""){
            criteria.andOpernameLike("%"+newsVo.getOpername()+"%");
        }
        Page<Object> page = PageHelper.startPage(newsVo.getPage(),newsVo.getLimit());
        List<News> news = newsMapper.selectByExample(example);
        return new DataGridView(page.getTotal(),news);
    }

    @Override
    public void addNews(NewsVo newsVo) {
        newsMapper.insert(newsVo);
    }

    @Override
    public void updateNews(NewsVo newsVo) {
        News news = newsMapper.selectByPrimaryKey(newsVo.getId());
        newsVo.setOpername(news.getOpername());
        newsVo.setCreatetime(news.getCreatetime());
        newsMapper.updateByPrimaryKey(newsVo);
    }

    @Override
    public void deleteNews(Integer id) {
        newsMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void deleteBatchNews(Integer[] ids) {
        for(Integer id : ids){
            deleteNews(id);
        }
    }

    @Override
    public News queryNewsById(Integer id) {
        NewsExample example = new NewsExample();
        example.createCriteria().andIdEqualTo(id);
        return newsMapper.selectByExample(example).get(0);

    }
}
