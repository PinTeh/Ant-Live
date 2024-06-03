package cn.imhtb.live.service.impl;

import cn.imhtb.live.pojo.database.Video;
import cn.imhtb.live.mappers.VideoMapper;
import cn.imhtb.live.service.IVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author PinTeh
 * @date 2020/5/25
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements IVideoService {
}
