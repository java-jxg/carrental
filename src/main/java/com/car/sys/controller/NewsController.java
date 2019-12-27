package com.car.sys.controller;

import com.car.sys.domain.News;
import com.car.sys.domain.User;
import com.car.sys.service.NewsService;
import com.car.sys.utils.DataGridView;
import com.car.sys.utils.ResultObj;
import com.car.sys.utils.WebUtils;
import com.car.sys.vo.NewsVo;
import com.car.sys.vo.NewsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Date;

@RestController
@RequestMapping("news")
public class NewsController {
    @Autowired
    private NewsService newsService;

    @RequestMapping("loadAllNews")
    public DataGridView loadAllNews(NewsVo newsVo){
        return newsService.queryAllNews(newsVo);
    }

    @RequestMapping("addNews")
    public ResultObj addNews(NewsVo newsVo){
        try {
            if(newsVo.getId()!=null){
                newsVo.setId(null);
            }
            newsVo.setCreatetime(new Date());
            User user = (User) WebUtils.getHttpSession().getAttribute("user");
            newsVo.setOpername(user.getRealname());
            this.newsService.addNews(newsVo);
            return ResultObj.ADD_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }
    }
    @RequestMapping("updateNews")
    public ResultObj updateNews(NewsVo newsVo){
        try {
            newsService.updateNews(newsVo);
            return ResultObj.UPDATE_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }
    
    @RequestMapping("deleteNews")
    public ResultObj deleteNews(NewsVo newsVo){
        try {
            newsService.deleteNews(newsVo.getId());
            return ResultObj.DELETE_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }
    /**
     * 批量删除公告
     * @param newsVo
     * @return
     */
    @RequestMapping("deleteBatchNews")
    public ResultObj deleteBatchNews(NewsVo newsVo){
        try {
            newsService.deleteBatchNews(newsVo.getIds());
            return ResultObj.DELETE_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }


    /**
     * 根据id查询公告
     */
    @RequestMapping("loadNewsById")
    public News loadNewsById(Integer id) {
        return newsService.queryNewsById(id);
    }
}
