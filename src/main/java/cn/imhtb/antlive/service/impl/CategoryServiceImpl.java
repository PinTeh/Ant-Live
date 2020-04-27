package cn.imhtb.antlive.service.impl;

import cn.imhtb.antlive.entity.database.Category;
import cn.imhtb.antlive.mappers.CategoryMapper;
import cn.imhtb.antlive.service.ICategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author PinTeh
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {
}
