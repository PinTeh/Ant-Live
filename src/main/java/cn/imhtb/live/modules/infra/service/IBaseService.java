package cn.imhtb.live.modules.infra.service;

import cn.imhtb.live.common.PageData;
import cn.imhtb.live.modules.infra.model.PageQuery;
import cn.imhtb.live.modules.infra.model.SortQuery;

import java.util.List;

/**
 * @author pinteh
 * @date 2025/4/13
 */
public interface IBaseService<L, Q, D, U> {

    /**
     * 获取列表
     *
     * @return 列表数据
     */
    List<L> list(Q query, SortQuery sortQuery);

    /**
     * 分页查询
     *
     * @param query 查询参数
     * @param pageQuery 分页参数
     * @return 分页列表数据
     */
    PageData<L> page(Q query, PageQuery pageQuery);

    /**
     * 新增或者更新
     *
     * @param update 操作对象
     * @return 操作结果
     */
    Boolean addOrUpdate(U update);

    /**
     * 根据id删除
     *
     * @param ids 标识集合
     * @return 删除结果
     */
    Boolean delete(List<Integer> ids);

    /**
     * 根据id查询详情
     *
     * @param id 标识
     * @return 详情
     */
    D detail(Integer id);

}
