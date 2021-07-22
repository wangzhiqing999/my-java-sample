package cn.wzq.studylombok;

import lombok.*;


// @Data：作用于类上，是以下注解的集合：@ToString @EqualsAndHashCode @Getter @Setter @RequiredArgsConstructor
@Data

// @NoArgsConstructor：生成无参构造器；
@NoArgsConstructor

// @AllArgsConstructor：生成全参构造器
@AllArgsConstructor

// @Builder：作用于类上，将类转变为建造者模式
@Builder

public class User {

	private Long id;


	// @NonNull：主要作用于成员变量和参数中，标识不能为空，否则抛出空指针异常。
	@NonNull
	private String userName;


	@Builder.Default
	private String password = "123456";

	private String name; 
	private Integer age;

	private String email = "";
}