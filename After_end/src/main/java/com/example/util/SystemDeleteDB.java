package com.example.util;

import com.example.util.db.MySQLUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

import static com.example.util.time.GetSystemTime.getTime;

@Slf4j
public class SystemDeleteDB{
    // 构建 SQL 查询语句，删除 datetime 超过3天的行
    private static final String timedDeleteSql = "DELETE FROM tb_history WHERE time <= DATE_SUB(NOW(), INTERVAL 3 DAY)";

    // 每3天执行一次删除操作
    @Scheduled(fixedRate = 3 * 24 * 60 * 60 * 1000) // 毫秒为单位
    public void deleteTimedDB(){
        if(MySQLUtil.timedDelete(timedDeleteSql) > 0){
            log.info("系统在{}时，成功删除，历史表中超时数据",getTime());
        }else {
            log.info("系统在{}时，删除失败，历史表中超时数据",getTime());
        }
    }
}
