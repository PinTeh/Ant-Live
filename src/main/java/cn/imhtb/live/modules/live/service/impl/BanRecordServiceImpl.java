package cn.imhtb.live.modules.live.service.impl;

import cn.imhtb.live.pojo.database.BanRecord;
import cn.imhtb.live.mappers.BanRecordMapper;
import cn.imhtb.live.modules.live.service.IBanRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author PinTeh
 * @date 2020/5/23
 */
@Service
public class BanRecordServiceImpl extends ServiceImpl<BanRecordMapper, BanRecord> implements IBanRecordService {
}
