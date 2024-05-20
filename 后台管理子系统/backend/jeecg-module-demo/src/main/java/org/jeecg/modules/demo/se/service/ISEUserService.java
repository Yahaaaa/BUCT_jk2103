package org.jeecg.modules.demo.se.service;

import org.jeecg.modules.demo.se.entity.SEUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.Serializable;

/**
 * @Description: user
 * @Author: jeecg-boot
 * @Date:   2024-05-07
 * @Version: V1.0
 */
public interface ISEUserService extends IService<SEUser> {
    public boolean banById(SEUser SEUser);

    public boolean unbanById(SEUser SEUser);
}
