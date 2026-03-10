package cn.imhtb.live.modules.infra.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.imhtb.live.common.PageData;
import cn.imhtb.live.modules.infra.model.PageQuery;
import cn.imhtb.live.modules.infra.model.SortQuery;
import cn.imhtb.live.modules.infra.service.IBaseService;
import cn.imhtb.live.modules.infra.utils.QueryWrapperHelper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

/**
 * @author pinteh
 * @date 2025/4/13
 */
@Slf4j
public abstract class BaseServiceImpl<M extends BaseMapper<T>, T, L, Q, D, U> extends ServiceImpl<M, T> implements IBaseService<L, Q, D, U> {

    private Class<L> listClass;
    private Class<Q> queryClass;
    private Class<D> detailClass;
    private Class<U> updateClass;
    private List<Field> entityFields;

    @Override
    public D detail(Integer id) {
        T entity = getById(id);
        return BeanUtil.toBean(entity, getDetailClass());
    }


    @Override
    public List<L> list(Q query, SortQuery sortQuery) {
        QueryWrapper<T> queryWrapper = buildQueryWrapper(query);
        this.sort(queryWrapper, sortQuery);
        List<T> list = list(queryWrapper);
        return BeanUtil.copyToList(list, getListClass());
    }

    @Override
    public PageData<L> page(Q query, PageQuery pageQuery) {
        QueryWrapper<T> queryWrapper = buildQueryWrapper(query);
        Page<T> page = page(new Page<>(pageQuery.getPageNo(), pageQuery.getPageSize()), queryWrapper);
        PageData<L> pageData = new PageData<>();
        pageData.setTotal(page.getTotal());
        pageData.setList(BeanUtil.copyToList(page.getRecords(), getListClass()));
        return pageData;
    }

    @Override
    public Boolean addOrUpdate(U update) {
        T bean = BeanUtil.copyProperties(update, super.getEntityClass());
        return super.saveOrUpdate(bean);
    }

    @Override
    public Boolean delete(List<Integer> ids) {
        return super.removeByIds(ids);
    }

    private void fill(Object object){
        if (object == null){
            return;
        }
        // TODO 对象填充
    }

    private void sort(QueryWrapper<T> queryWrapper, SortQuery sortQuery) {
        if (sortQuery == null || sortQuery.getSort().isUnsorted()){
            return;
        }
        Sort sort = sortQuery.getSort();
        for (Sort.Order order : sort) {
            String property = order.getProperty();
            // 校验property是否合法，即存在表字段
            List<Field> fields = getEntityFields();
            if (fields.stream().noneMatch(field -> field.getName().equals(property))){
                log.warn("无效的排序字段{}, entity = {}", order.getProperty(), super.getEntityClass().getName());
            }else{
                queryWrapper.orderBy(true, order.isAscending(), CharSequenceUtil.toUnderlineCase(property));
            }
        }
    }

    private QueryWrapper<T> buildQueryWrapper(Q query) {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        Field[] fields = ReflectUtil.getFields(query.getClass());
        return QueryWrapperHelper.getQueryWrapper(query, Arrays.asList(fields),queryWrapper);
    }

    @SuppressWarnings("all")
    private Class<L> getListClass(){
        if (this.listClass == null){
            Class<?> typeArgument = ClassUtil.getTypeArgument(getClass(), 2);
            this.listClass = (Class<L>) typeArgument;
        }
        return this.listClass;
    }

    @SuppressWarnings("all")
    private Class<Q> getQueryClass(){
        if (this.queryClass == null){
            Class<?> typeArgument = ClassUtil.getTypeArgument(getClass(), 3);
            this.queryClass = (Class<Q>) typeArgument;
        }
        return this.queryClass;
    }

    @SuppressWarnings("all")
    private Class<D> getDetailClass(){
        if (this.detailClass == null){
            Class<?> typeArgument = ClassUtil.getTypeArgument(getClass(), 4);
            this.detailClass = (Class<D>) typeArgument;
        }
        return this.detailClass;
    }

    @SuppressWarnings("all")
    private Class<U> getUpdateClass(){
        if (this.updateClass == null){
            Class<?> typeArgument = ClassUtil.getTypeArgument(getClass(), 5);
            this.updateClass = (Class<U>) typeArgument;
        }
        return this.updateClass;
    }

    private List<Field> getEntityFields(){
        if (this.entityFields == null){
            Field[] fields = ReflectUtil.getFields(super.getEntityClass());
            this.entityFields = Arrays.asList(fields);
        }
        return this.entityFields;
    }

    private List<Field> getDetailFields(){
        Field[] fields = ReflectUtil.getFields(getDetailClass());
        return Arrays.asList(fields);
    }

}
