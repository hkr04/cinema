package com.influence.cm.controller;

import com.influence.cm.common.response.ResponseResult;
import com.influence.cm.domain.SysMovieCategory;
import com.influence.cm.domain.SysMovieToCategory;
import com.influence.cm.service.impl.SysMovieCategoryServiceImpl;
import com.influence.cm.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class SysMovieCategoryController extends BaseController {

    @Autowired
    private SysMovieCategoryServiceImpl sysMovieCategoryService;

    @GetMapping("/sysMovieCategory/find")
    public ResponseResult findAllCategorys() {
        startPage();
        List<SysMovieCategory> data = sysMovieCategoryService.findAllCategorys();
        return getResult(data);
    }

    @GetMapping("/sysMovieCategory/{id}")
    public ResponseResult findCategoryById(@PathVariable Long id) {
        return getResult(sysMovieCategoryService.findCategoryById(id));
    }

    @PostMapping("/sysMovieCategory")
    public ResponseResult addCategory(@Validated @RequestBody SysMovieCategory sysMovieCategory) {
        return getResult(sysMovieCategoryService.addCategory(sysMovieCategory));
    }


    @PutMapping("/sysMovieCategory")
    public ResponseResult updateCategory(@Validated @RequestBody SysMovieCategory sysMovieCategory) {
        return getResult(sysMovieCategoryService.updateCategory(sysMovieCategory));
    }

    @DeleteMapping("/sysMovieCategory/{ids}")
    public ResponseResult deleteCategory(@PathVariable Long[] ids) {
        return getResult(sysMovieCategoryService.deleteCategory(ids));
    }

    @PostMapping("/sysMovieToCategory/{movieId}/{movieCategoryId}")
    public ResponseResult addMovieToCategory(@PathVariable Long movieId, @PathVariable Long movieCategoryId) {
        return getResult(sysMovieCategoryService.addMovieToCategory(new SysMovieToCategory(movieId, movieCategoryId)));
    }

    @DeleteMapping("/sysMovieToCategory/{movieId}/{movieCategoryId}")
    public ResponseResult deleteMovieToCategory(@PathVariable Long movieId, @PathVariable Long movieCategoryId) {
        return getResult(sysMovieCategoryService.deleteMovieToCategory(new SysMovieToCategory(movieId, movieCategoryId)));
    }

}
