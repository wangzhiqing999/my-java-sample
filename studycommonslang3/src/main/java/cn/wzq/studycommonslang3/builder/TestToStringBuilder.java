package cn.wzq.studycommonslang3.builder;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class TestToStringBuilder {

	public static void main(String[] args) {

		

		TestToStringBuilder data = new TestToStringBuilder();
		
		data.setName("张三");
		data.setAge(18);
		
		System.out.println(data);
	}
	
	
	
	
	
	private String name;
	
	private int age;
	
	


	
	
   public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}






	public String toString() {
	     return new ToStringBuilder(this).
	       append("name", name).
	       append("age", age).
	       toString();
   }

}
