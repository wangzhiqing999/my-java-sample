package cn.wzq.studymapstruct.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


//被映射类VO3: 字段名称相同，但是数据类型不一样
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserVO3 {
    private String id;
    private String name;
    // 实体类该属性是String
    private LocalDateTime createTime;
    // 实体类该属性是LocalDateTime
    private String updateTime;
}
