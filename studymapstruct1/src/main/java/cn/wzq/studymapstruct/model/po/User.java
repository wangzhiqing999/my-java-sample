package cn.wzq.studymapstruct.model.po;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


// 这个类是用于模拟一个与数据库表字段关联的类。
// 这个类预期是仅仅后端处理
// 一般情况下不将这个类直接返回给前端
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private Integer id;
    private String name;
    private String createTime;
    private LocalDateTime updateTime;
}
