package com.lagou.controller;

import com.lagou.domain.Course;
import com.lagou.domain.CourseLesson;
import com.lagou.domain.CourseSection;
import com.lagou.domain.ResponseResult;
import com.lagou.service.CourseContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/courseContent")
public class CourseContentController {

    @Autowired
    private CourseContentService courseContentService;

    /*
        查询课程内容
     */
    @RequestMapping("/findSectionAndLesson")
    public ResponseResult findSectionAndLessonByCourseId(@RequestParam int courseId) {
        //调用service方法
        List<CourseSection> sections = courseContentService.findSectionAndLessonByCourseId(courseId);

        //封装数据并返回
        ResponseResult responseResult = new ResponseResult(true,200,"响应数据成功",sections);

        return responseResult;
    }

    /*
        回显章节对应的课程信息
     */
    @RequestMapping("/findCourseByCourseId")
    public ResponseResult findCourseByCourseId(@RequestParam int courseId) {
        //调用service方法
        Course course = courseContentService.findCourseByCourseId(courseId);
        ResponseResult responseResult = new ResponseResult(true,200,"响应成功",course);
        return responseResult;
    }

    /*
        新增章节信息
     */
    @RequestMapping("/saveOrUpdateSection")
    public ResponseResult saveOrUpdateSection(@RequestBody CourseSection courseSection) {
        //判断携带id是修改操作还是新增操作
        if (courseSection.getId() == null) {
            //新增操作
            courseContentService.saveSection(courseSection);
            ResponseResult responseResult = new ResponseResult(true,200,"新增章节成功",null);
            return responseResult;
        } else {
            //修改操作
            courseContentService.updateSection(courseSection);
            ResponseResult responseResult = new ResponseResult(true,200,"更新章节成功",null);
            return responseResult;
        }
    }

    /*
        修改章节状态信息
        状态：0：隐藏； 1：待更新； 2：已发布
     */
    @RequestMapping("/updateSectionStatus")
    public ResponseResult updateSectionStatus(@RequestParam int id , @RequestParam int status) {
        try {
            courseContentService.updateSectionStatus(id,status);
            //封装最新数据
            Map<String, Object> map = new HashMap<>();
            map.put("status",status);
            ResponseResult responseResult = new ResponseResult(true,200,"响应成功",map);

            return responseResult;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /*
        保存&修改课时
     */
    @RequestMapping("/saveOrUpdateLesson")
    public ResponseResult saveOrUpdateLesson(@RequestBody CourseLesson courseLesson) {
        try {
            //保存课时
            if (courseLesson.getId() == null) {
                courseContentService.saveLesson(courseLesson);
                ResponseResult responseResult = new ResponseResult(true,200,"新增课时成功",null);
                return responseResult;
            } else {
                //修改课信息
                courseContentService.updateLesson(courseLesson);
                ResponseResult responseResult = new ResponseResult(true,200,"修改课时信息成功",null);
                return responseResult;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
