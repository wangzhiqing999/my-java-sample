package cn.wzq.studymapstruct.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//被映射类VO4: 字段名称不同， 数据类型也存在一些差异
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserVO4 {
    // 实体类该属性名是id
    private String userId;
    // 实体类该属性名是name
    private String userName;
    private String createTime;
    private String updateTime;
}
