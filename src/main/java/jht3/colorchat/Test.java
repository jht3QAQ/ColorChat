package jht3.colorchat;

public class Test {
	public static void main(String[] s2){
		String[] colorList=new String[]{
				"&4","&c","&6","&e","&2","&a","&b","&3",
				"&1","&8","&d","&5","&f","&7","&8","&0"
				};
		int i=0;

		String s="2";
		//if((s.charAt(i)>='a'&&s.charAt(i)<='z')||(s.charAt(i)>='A'&&s.charAt(i)<='Z'||s.charAt(i)>='')){
		//	System.out.println("Char!");
		//}
		String s3="233333 Test @混合测试2 \\&a23333";
		//System.out.println(ColorRender.toColor("23333"));
		//System.out.println(ColorRender.toColor("@23333"));
		//System.out.println(ColorRender.toColor("@���Ĳ���"));
		//System.out.println(ColorRender.toColor("�����Ĳ���"));
		//System.out.println(ColorRender.toColor("2333@��ϲ���"));
		//System.out.println(s3+" ==> "+ColorRender.toColor(s3));
		String s4="0123456789"
				+ "0123456789"
				+ "0123456789"
				+ "0123456789"
				+ "0123456789"
				+ "0123456789"
				+ "0123456789"
				+ "0123456789"
				+ "0123456789"
				+ "0123456";

		System.out.println(s4+" ==> "+ColorRender.toColor(s4));
		System.out.println(colorList.length);
		String s5="11111111111111";
		System.out.println(s5+" ==> "+ColorRender.toColor(s5));
		//System.out.println(ColorRender.toColor("23333"));
		//System.out.println(ColorRender.toColor("23333"));
		
	}
}
