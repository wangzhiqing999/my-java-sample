学习使用 mapstruct 的例子


创建 Spring Starter 项目
名称：studymapstruct1



在选择包的时候。
选择 Web 下面的 Spring Web


修改 pom.xml 添加依赖



先创建 po 类，用于模拟一个与数据库表字段关联的类

然后创建 dto 类，用于模拟画面上的类

分别有下列几种情况

被映射类VO1:和实体类一模一样
被映射类VO2:比实体类少一个字段
被映射类VO3:字段名称相同，但是数据类型不一样
被映射类VO4:字段名称不同， 数据类型也存在一些差异
被映射类VO5:数据类型不一样，实体类中有枚举类型



然后，创建 UserCovertBasic 接口
定义 如何做转换的操作。



创建一个 HelloController
用于测试网站是否成功启动。


创建一个 TestController
编写 测试转换的代码






在 studymapstruct1 目录下，运行
mvnw spring-boot:run



测试

http://localhost:8080/hello
标准 Hello World.


http://localhost:8080/source
查看原始数据.


http://localhost:8080/convert01
测试 "被映射类VO1:和实体类一模一样"

http://localhost:8080/convert01b
测试 "被映射类VO1:和实体类一模一样"  (使用 BeanUtils)


http://localhost:8080/convert02
测试 "被映射类VO2:比实体类少一个字段"

http://localhost:8080/convert02b
测试 "被映射类VO2:比实体类少一个字段"  (使用 BeanUtils)



http://localhost:8080/convert03
测试 "被映射类VO3: 字段名称相同，但是数据类型不一样"

http://localhost:8080/convert03b
测试 "被映射类VO3: 字段名称相同，但是数据类型不一样"  (使用 BeanUtils)
这里数据类型不一致的列，最终转换失败。



http://localhost:8080/convert04
测试 "被映射类VO4: 字段名称不同， 数据类型也存在一些差异"




http://localhost:8080/sourceenum
查看原始数据.(包含枚举的数据类型)

http://localhost:8080/convert05
测试 "被映射类VO5: 数据类型不一样，实体类中有枚举类型"
