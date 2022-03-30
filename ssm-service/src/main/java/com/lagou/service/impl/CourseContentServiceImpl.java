package com.lagou.service.impl;

import com.lagou.dao.CourseContentMapper;
import com.lagou.domain.Course;
import com.lagou.domain.CourseLesson;
import com.lagou.domain.CourseSection;
import com.lagou.service.CourseContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CourseContentServiceImpl implements CourseContentService {

    @Autowired
    private CourseContentMapper contentMapper;

    @Override
    public List<CourseSection> findSectionAndLessonByCourseId(int courseId) {

        List<CourseSection> courseSections = contentMapper.findSectionAndLessonByCourseId(courseId);
        return courseSections;
    }

    @Override
    public Course findCourseByCourseId(int courseId) {
        Course course = contentMapper.findCourseByCourseId(courseId);

        return course;
    }

    @Override
    public void saveSection(CourseSection courseSection) {
        //补全信息
        Date date = new Date();
        courseSection.setCreateTime(date);
        courseSection.setUpdateTime(date);

        contentMapper.saveSection(courseSection);
    }

    @Override
    public void updateSection(CourseSection courseSection) {
        //补全信息
        Date date = new Date();
        courseSection.setUpdateTime(date);

        contentMapper.updateSection(courseSection);
    }

    @Override
    public void updateSectionStatus(int id, int status) {
        //封装数据
        CourseSection courseSection = new CourseSection();
        courseSection.setId(id);
        courseSection.setStatus(status);
        Date date = new Date();
        courseSection.setUpdateTime(date);

        contentMapper.updateSectionStatus(courseSection);
    }

    @Override
    public void saveLesson(CourseLesson courseLesson) {
        //补全信息
        Date date = new Date();
        courseLesson.setCreateTime(date);
        courseLesson.setUpdateTime(date);

        contentMapper.saveLesson(courseLesson);
    }

    @Override
    public void updateLesson(CourseLesson courseLesson) {
        //补全信息
        Date date = new Date();
        courseLesson.setUpdateTime(date);
        contentMapper.updateLesson(courseLesson);
    }
}
