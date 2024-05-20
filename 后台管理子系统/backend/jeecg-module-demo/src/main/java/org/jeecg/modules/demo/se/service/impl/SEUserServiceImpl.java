package org.jeecg.modules.demo.se.service.impl;

import org.jeecg.modules.demo.se.entity.SEUser;
import org.jeecg.modules.demo.se.mapper.SEUserMapper;
import org.jeecg.modules.demo.se.service.ISEUserService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.io.Serializable;

/**
 * @Description: user
 * @Author: jeecg-boot
 * @Date:   2024-05-07
 * @Version: V1.0
 */
@Service
public class SEUserServiceImpl extends ServiceImpl<SEUserMapper, SEUser> implements ISEUserService {

    @Override
    public boolean banById(SEUser SEUser) {
        SEUser.setIsbanned(1);
        return updateById(SEUser);
    }

    @Override
    public boolean unbanById(SEUser SEUser) {
        SEUser.setIsbanned(0);
        return updateById(SEUser);
    }
}
