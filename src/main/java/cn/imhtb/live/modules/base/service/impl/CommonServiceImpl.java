package cn.imhtb.live.modules.base.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.imhtb.live.common.holder.UserHolder;
import cn.imhtb.live.common.utils.CommonUtil;
import cn.imhtb.live.mappers.CategoryMapper;
import cn.imhtb.live.modules.base.model.CategoryResp;
import cn.imhtb.live.modules.base.model.VerifyCodeReq;
import cn.imhtb.live.modules.base.service.ICommonService;
import cn.imhtb.live.modules.infra.config.RedisKey;
import cn.imhtb.live.modules.infra.utils.RedisUtils;
import cn.imhtb.live.pojo.database.Category;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

/**
 * @author pinteh
 * @date 2025/4/12
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class CommonServiceImpl implements ICommonService {

    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryResp> getCategories() {
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<Category>().orderByDesc(Category::getSort);
        List<Category> categories = categoryMapper.selectList(wrapper);
        return BeanUtil.copyToList(categories, CategoryResp.class);
    }

    @Override
    public Boolean sendVerifyCode(VerifyCodeReq req) {
        Integer userId = UserHolder.getUserId();
        int verifyCode = CommonUtil.getRandomCode();
        // 发送验证码
        String verifyCodeKey = RedisKey.getKey(RedisKey.VERIFY_CODE, req.getVerifyType(), userId);
        Boolean hasKey = RedisUtils.contains(verifyCodeKey);
        if (hasKey){
            return false;
        }
        RedisUtils.set(verifyCodeKey, String.valueOf(verifyCode), Duration.ofMinutes(5));
        log.info("{}, 发送验证码:{}", userId, verifyCode);
        // TODO 发送验证码
        switch (req.getVerifyType()){
            case "MAIL":

                break;
            case "TEL":
                break;
        }
        return true;
    }

}
