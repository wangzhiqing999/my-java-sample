package cn.wzq.studymapstruct.model.po;


import cn.wzq.studymapstruct.enums.UserTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


// 这里专门用于测试 枚举类型.
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEnum {
    private Integer id;
    private String name;
    private UserTypeEnum userTypeEnum;
}
