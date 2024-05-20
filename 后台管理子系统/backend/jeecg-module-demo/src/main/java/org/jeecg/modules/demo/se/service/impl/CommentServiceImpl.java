package org.jeecg.modules.demo.se.service.impl;

import org.jeecg.modules.demo.se.entity.Comment;
import org.jeecg.modules.demo.se.mapper.CommentMapper;
import org.jeecg.modules.demo.se.service.ICommentService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: comment
 * @Author: jeecg-boot
 * @Date:   2024-05-07
 * @Version: V1.0
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

}
