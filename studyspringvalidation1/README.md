Spring Validation 学习的例子1 - 基本的 web api 参数校验，与通用返回结果



创建 Spring Starter 项目
名称：studyspringvalidation1



在选择包的时候。
选择 Web 下面的 Spring Web


修改 pom.xml 添加依赖.



已有类的功能说明：


constants
	基本的错误码常量定义.

core
	提供核心封装.
core.exception
	服务异常.
core.validator
	自定义校验的处理.
core.vo
	通用返回结果.
	也就是一个 code/message/data 的对象.
core.web
	Web 异常处理与结果转换.
	根据需要，控制器代码， 可以简单返回 void.
	那么，前端调用时
	有异常，则是异常报错：{ "code": 错误码, "message":"错误信息", "data":null }
	无异常，则是：{ "code":0, "message":"", "data":null }

dto
	用于测试的参数.
service
	用于测试的服务.
controller	
	用于测试的控制器.



在 studyspringvalidation 目录下，运行
mvnw spring-boot:run


验证结果
http://localhost:8080/swagger-ui.html



测试的控制器方法，所有返回值，都是void.
测试检查了哪些参数， 通过查看 控制器方法的参数，是 dto 下面的哪一个类， 这个类的属性上，都加了哪些注解。







## 需要了解的注解

### Bean Validation 定义的约束注解
javax.validation.constraints 包下，定义了一系列的约束( constraint )注解。如下：

空和非空检查
@NotBlank ：只能用于字符串不为 null ，并且字符串 #trim() 以后 length 要大于 0 。
@NotEmpty ：集合对象的元素不为 0 ，即集合不为空，也可以用于字符串不为 null 。
@NotNull ：不能为 null 。
@Null ：必须为 null 。

数值检查
@DecimalMax(value) ：被注释的元素必须是一个数字，其值必须小于等于指定的最大值。
@DecimalMin(value) ：被注释的元素必须是一个数字，其值必须大于等于指定的最小值。
@Digits(integer, fraction) ：被注释的元素必须是一个数字，其值必须在可接受的范围内。
@Positive ：判断正数。
@PositiveOrZero ：判断正数或 0 。
@Max(value) ：该字段的值只能小于或等于该值。
@Min(value) ：该字段的值只能大于或等于该值。
@Negative ：判断负数。
@NegativeOrZero ：判断负数或 0 。

Boolean 值检查
@AssertFalse ：被注释的元素必须为 true 。
@AssertTrue ：被注释的元素必须为 false 。

长度检查
@Size(max, min) ：检查该字段的 size 是否在 min 和 max 之间，可以是字符串、数组、集合、Map 等。

日期检查
@Future ：被注释的元素必须是一个将来的日期。
@FutureOrPresent ：判断日期是否是将来或现在日期。
@Past ：检查该字段的日期是在过去。
@PastOrPresent ：判断日期是否是过去或现在日期。

其它检查
@Email ：被注释的元素必须是电子邮箱地址。
@Pattern(value) ：被注释的元素必须符合指定的正则表达式。


### Hibernate Validator 附加的约束注解
org.hibernate.validator.constraints 包下，定义了一系列的约束( constraint )注解。如下：

@Range(min=, max=) ：被注释的元素必须在合适的范围内。
@Length(min=, max=) ：被注释的字符串的大小必须在指定的范围内。
@URL(protocol=,host=,port=,regexp=,flags=) ：被注释的字符串必须是一个有效的 URL 。
@SafeHtml ：判断提交的 HTML 是否安全。例如说，不能包含 javascript 脚本等等。
......


### @Valid 和 @Validated
@Valid 注解，是 Bean Validation 所定义，可以添加在普通方法、构造方法、方法参数、方法返回、成员变量上，表示它们需要进行约束校验。
@Validated 注解，是 Spring Validation 锁定义，可以添加在类、方法参数、普通方法上，表示它们需要进行约束校验。
同时，@Validated 有 value 属性，支持分组校验。

① 声明式校验
Spring Validation 仅对 @Validated 注解，实现声明式校验。
② 分组校验
Bean Validation 提供的 @Valid 注解，因为没有分组校验的属性，所以无法提供分组校验。此时，我们只能使用 ``@Validated` 注解。
③ 嵌套校验
相比来说，@Valid 注解的地方，多了【成员变量】。这就导致，如果有嵌套对象的时候，只能使用 @Valid 注解。

总的来说，绝大多数场景下，我们使用 @Validated 注解即可。
而在有嵌套校验的场景，我们使用 @Valid 注解添加到成员属性上。



