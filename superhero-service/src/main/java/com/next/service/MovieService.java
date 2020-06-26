package com.next.service;

import com.next.pojo.Movie;
import com.next.utils.JqGridResult;

import java.util.List;

public interface MovieService {

    public List<Movie> queryHotSuperhero(String type);

    /**
     * 查询电影预告表的记录数
     * @return
     */
    public Integer queryAllTrailerCounts();

    /**
     * 查询所有的电影记录
     * @return
     */
    public List<Movie> queryAllTrailers();

    /**
     * 根据关键字查询分页的电影内容
     * @param keywords
     * @param page
     * @param pageSize
     * @return
     */
    public JqGridResult searchTrailer(String keywords, Integer page, Integer pageSize);

    /**
     * 根据主键查询电影详情
     * @param trailerId
     * @return
     */
    public Movie queryTrailerInfo(String trailerId);
}
