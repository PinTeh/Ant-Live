package cn.imhtb.antlive.service.impl;

import cn.imhtb.antlive.entity.database.BanRecord;
import cn.imhtb.antlive.mappers.BanRecordMapper;
import cn.imhtb.antlive.service.IBanRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author PinTeh
 * @date 2020/5/23
 */
@Service
public class BanRecordServiceImpl extends ServiceImpl<BanRecordMapper, BanRecord> implements IBanRecordService {
}
