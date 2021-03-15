package cn.wzq.studymapstruct.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// 被映射类VO5: 数据类型不一样，实体类中有枚举类型
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserVO5 {
    private Integer id;
    private String name;
    private String type;

}
