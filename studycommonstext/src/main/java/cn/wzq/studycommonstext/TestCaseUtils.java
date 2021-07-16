package cn.wzq.studycommonstext;

import org.apache.commons.text.CaseUtils;

public class TestCaseUtils {

	public static void main(String[] args) throws Exception {
		
		
		all();

	}

	
	
	
		
	public static void all() throws Exception {

		
		String db_col_name = "last_update_user";
		
		
		
		System.out.println("----- toCamelCase -----");
		
		
		
		
		
		System.out.println(CaseUtils.toCamelCase(db_col_name, false, new char[]{'_'}));
		
		System.out.println(CaseUtils.toCamelCase(db_col_name, true, new char[]{'_'}));
		
		
		System.out.println();
	}

}
