package org.jeecg.modules.demo.testComment.service.impl;

import org.jeecg.modules.demo.testComment.entity.TestComment;
import org.jeecg.modules.demo.testComment.mapper.TestCommentMapper;
import org.jeecg.modules.demo.testComment.service.ITestCommentService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 测试评论
 * @Author: jeecg-boot
 * @Date:   2024-04-15
 * @Version: V1.0
 */
@Service
public class TestCommentServiceImpl extends ServiceImpl<TestCommentMapper, TestComment> implements ITestCommentService {

}
