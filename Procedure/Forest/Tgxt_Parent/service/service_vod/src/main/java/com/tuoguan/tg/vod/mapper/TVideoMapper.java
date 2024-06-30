package com.tuoguan.tg.vod.mapper;

import com.tuoguan.tg.vod.entity.TVideo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 课程视频 Mapper 接口
 * </p>
 *
 * @author tuoguan
 * @since 2024-04-13
 */
public interface TVideoMapper extends BaseMapper<TVideo> {

    @Select("SELECT v.*\n" +
            "FROM t_video v\n" +
            "WHERE v.course_id = #{courseId}")
    TVideo getVideo(Long courseId);

    @Delete("DELETE FROM t_video WHERE video_source_id = #{fileId}")
    void removeVide(String fileId);
}
