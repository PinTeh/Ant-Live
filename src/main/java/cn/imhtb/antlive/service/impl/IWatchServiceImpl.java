package cn.imhtb.antlive.service.impl;

import cn.imhtb.antlive.entity.database.Watch;
import cn.imhtb.antlive.mappers.WatchMapper;
import cn.imhtb.antlive.service.IWatchService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author PinTeh
 * @date 2020/3/18
 */
@Service
public class IWatchServiceImpl extends ServiceImpl<WatchMapper, Watch> implements IWatchService {
}
