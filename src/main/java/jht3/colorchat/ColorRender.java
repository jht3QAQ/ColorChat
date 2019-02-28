package jht3.colorchat;

import java.util.ArrayList;

import scala.Char;
import scala.reflect.internal.Trees.This;

public class ColorRender {
	private static String[] colorList=new String[]{
			"&4","&c","&6","&e","&2","&a","&b","&3",
			"&1","&9","&d","&5","&f","&7","&8","&0"
			};
	private static int last=16;
	public enum Mode{ RANDOM, SINGLE, NONE};
	public enum color{mainColor,atColor,mathColor,englishColor};
	public static Mode mainMode=Mode.RANDOM;

    public static boolean isUseColorChat=true;
	
	private static String[] atColorList=new String[]{
			"&4","&c","&6","&e","&2","&a","&b","&3",
			"&1","&9","&d","&5","&f","&7","&8","&0"
			};
	public static boolean at =true;
	//private static Mode atMode=Mode.RANDOM;
	
	private static String[] englishColorList=new String[]{
			"&4","&c","&6","&e","&2","&a","&b","&3",
			"&1","&9","&d","&5","&f","&7","&8","&0"
			};
	public static boolean english=true;
	//private static Mode englishMode=Mode.RANDOM;
	
	private static String[] mathColorList=new String[]{
			"&4","&c","&6","&e","&2","&a","&b","&3",
			"&1","&9","&d","&5","&f","&7","&8","&0"
			};
	public static boolean math=true;
	//private static Mode englishMode=Mode.RANDOM;
	
	public static String toColor(String s){
		Mode temp=mainMode;
		String rawString=s;
		if(!isUseColorChat) return s;
		if(s.length()>=100) return s;
		StringBuilder sb=new StringBuilder();
		int i=0;
		
		if(mainMode==Mode.RANDOM) s=ColorRender.randomColor(rawString, sb, i);
		else if(mainMode==Mode.SINGLE) s=singleColor(rawString,sb,i);
		else if(mainMode==Mode.NONE) s=noneColor(rawString, sb, i);
		
		if(s.length()>100&&mainMode==Mode.RANDOM){
			mainMode=Mode.SINGLE;
			sb=new StringBuilder();
			i=0;
			s=singleColor(rawString,sb,i);
		}
		if(s.length()>100&&mainMode==Mode.SINGLE){
			mainMode=Mode.NONE;
			sb=new StringBuilder();
			i=0;
			s=noneColor(rawString, sb, i);
		}
		if(s.length()>100&&mainMode==Mode.NONE)
			return rawString;
		mainMode=temp;
		return s;
	}
	
	private static String randomColor(String s,StringBuilder sb,int i){
		if(i<s.length()){
			if(s.charAt(i)!=' '){
				if(english&&((s.charAt(i)>='!'&&s.charAt(i)<='~')&&(!(s.charAt(i)>='0'&&s.charAt(i)<='9')))){
					return ColorRender.addEnglishColor(s, sb, i);
				}else if(math&&(s.charAt(i)>='0'&&s.charAt(i)<='9')){
					return ColorRender.addMathColor(s, sb, i);
				}else{
					if(s.charAt(i)=='\\'&&(s.charAt(i+1)=='&'&&(((s.charAt(i+2)>='1'&&s.charAt(i+2)<='9')||(s.charAt(i+2)>='a'&&s.charAt(i+2)<='f')))||(s.charAt(i+1)=='\\'))){
						i++;sb.append(s.charAt(i));
					i++;sb.append(s.charAt(i));
					i++;
					if(i<s.length()){
						sb.append(s.charAt(i));
						i++;
					}
					}else{
					sb.append(getColor());
					sb.append(s.charAt(i));
					i++;
					}
				}
				return ColorRender.randomColor(s, sb, i);
			}
			else{
				if(at&&(i+1)<s.length()&&s.charAt(i+1)=='@'){
					return ColorRender.addAtColor(s, sb, i);
				}else{
					sb.append(s.charAt(i));
					i++;
					return ColorRender.randomColor(s, sb, i);
				}
			}
		}else
		return sb.toString();
	}
	
	private static String temp=null;
	private static String singleColor(String s,StringBuilder sb,int i){
		if(temp==null){
			temp=getColor();
			sb.append(temp);
		}
		return singleColor(s, sb, i, temp);
	}
	
	private static String singleColor(String s,StringBuilder sb,int i,String temp){
		if(i<s.length()){
			if(s.charAt(i)!=' '){
				if(english&&((s.charAt(i)>='!'&&s.charAt(i)<='~')&&(!(s.charAt(i)>='0'&&s.charAt(i)<='9')))){
					if(i+1<s.length()) return ColorRender.addEnglishColor(s, sb, i);
					else return ColorRender.addEnglishColor(s, sb, i)+temp;
				}else if(math&&(s.charAt(i)>='0'&&s.charAt(i)<='9')){
					if(i+1<s.length()) return ColorRender.addMathColor(s, sb, i);
					else return ColorRender.addMathColor(s, sb, i)+temp;
				}else{
					if(s.charAt(i)=='\\'){
						if(i+1<s.length()&&s.charAt(i+1)=='\\'){
							i++;sb.append(s.charAt(i));
							i++;
						}
						if(i+2<s.length()&&s.charAt(i+1)=='&'&&((s.charAt(i+2)>='1'&&s.charAt(i+2)<='9')||(s.charAt(i+2)>='a'&&s.charAt(i+2)<='f'))){
							i++;sb.append(s.charAt(i));
							i++;sb.append(s.charAt(i));
							i++;
						}
					}
					sb.append(s.charAt(i));
					i++;
					return ColorRender.singleColor(s, sb, i);
				}
			}
			else{
				if(at&&(i+1)<s.length()&&s.charAt(i+1)=='@'){
					if(i+1<s.length()) return ColorRender.addAtColor(s, sb, i);
					return ColorRender.addAtColor(s, sb, i)+temp;
				}else{
					sb.append(s.charAt(i));
					i++;
					return ColorRender.singleColor(s, sb, i);
				}
			}
		}else
			temp=null;
		return sb.toString();
	}
	
	private static String noneColor(String s,StringBuilder sb,int i){
		if(i<s.length()){
			if(s.charAt(i)!=' '){
				if(english&&((s.charAt(i)>='!'&&s.charAt(i)<='~')&&(!(s.charAt(i)>='0'&&s.charAt(i)<='9')))){
					return ColorRender.addEnglishColor(s, sb, i);
				}else if(math&&(s.charAt(i)>='0'&&s.charAt(i)<='9')){
					return ColorRender.addMathColor(s, sb, i);
				}else{
					sb.append(s.charAt(i));
					i++;
					return ColorRender.noneColor(s, sb, i);
				}
			}
			else{
				if(at&&(i+1)<s.length()&&s.charAt(i+1)=='@'){
					return ColorRender.addAtColor(s, sb, i);
				}else{
					sb.append(s.charAt(i));
					i++;
					return ColorRender.noneColor(s, sb, i);
				}
			}
		}else
		return sb.toString();
	}
	
	private static String addAtColor(String s,StringBuilder sb,int i){
		sb.append(s.charAt(i));
		i++;
		sb.append(getColor(atColorList));
			
		while(i<=s.length()-1&&s.charAt(i)!=' '){
			if(i>=s.length()) break;
			sb.append(s.charAt(i));
			i++;
		}
		return returnToMain(s, sb, i);
	}
	
	private static String addEnglishColor(String s,StringBuilder sb,int i){
		sb.append(getColor(englishColorList));
		sb.append(s.charAt(i));
		i++;
		while(i<=s.length()-1&&s.charAt(i)>='!'&&s.charAt(i)<='~'){
			sb.append(s.charAt(i));
			i++;
		}
		
		return returnToMain(s, sb, i);
	}
	
	private static String addMathColor(String s,StringBuilder sb,int i){
		sb.append(getColor(mathColorList));
		sb.append(s.charAt(i));
		i++;
		while(i<=s.length()-1&&s.charAt(i)>='0'&&s.charAt(i)<='9'){
			sb.append(s.charAt(i));
			i++;
		}
		
		return returnToMain(s, sb, i);
	}
	
	private static String getColor(){
		if(colorList.length==0) return "";
		int colorid;
		do{
			colorid=(int)(Math.random()*colorList.length);
		}while(colorid==last);
		last=colorid;
		return colorList[colorid];
	}
	
	private static String getColor(String[] s){
		if(s.length==0)return "";
		int colorid;
		
		boolean test=false;
		for(String s2:s){
			if(colorList.length<=last||s2!=colorList[last]) test=true;
			break;
		}
		
		if(test)
			do{
				colorid=(int)(Math.random()*s.length);
			}while(colorid==last);
		else colorid=(int)(Math.random()*s.length);
		
		last=colorid;
		return s[colorid];
	}
	
	private static String getColorExclude(String... s){
		int colorid;
		do{
			colorid=(int)(Math.random()*colorList.length);
		}while(colorid==last&&checkDifferent(colorid,s));
		last=colorid;
		return colorList[colorid];
	}
	
	private static boolean checkDifferent(int id,String... s){
		for(String s2:s){
			if(colorList[id]==s2)
				return false;
		}
		return true;
	}
	
	private static String returnToMain(String s,StringBuilder sb,int i){
		if(mainMode==Mode.RANDOM) return randomColor(s, sb, i);
		else if(mainMode==Mode.SINGLE) return singleColor(s,sb,i);
		else if(mainMode==Mode.NONE)return noneColor(s,sb,i);
		return null;
	}
	
	public static boolean setColorList(boolean[] b,color c){
		ArrayList<String> list=new ArrayList();
		if(b.length<16) return false;
		if(b[0]==true) list.add("&1"); 
		if(b[1]==true) list.add("&2"); 
		if(b[2]==true) list.add("&3"); 
		if(b[3]==true) list.add("&4"); 
		if(b[4]==true) list.add("&5"); 
		if(b[5]==true) list.add("&6"); 
		if(b[6]==true) list.add("&7"); 
		if(b[7]==true) list.add("&0"); 
		if(b[8]==true) list.add("&9"); 
		if(b[9]==true) list.add("&a"); 
		if(b[10]==true) list.add("&b"); 
		if(b[11]==true) list.add("&c"); 
		if(b[12]==true) list.add("&d"); 
		if(b[13]==true) list.add("&e"); 
		if(b[14]==true) list.add("&f");  
		if(b[15]==true) list.add("&8"); 
		String[] s = new String[list.size()];
		s=list.toArray(s);
		if(c==color.mainColor) ColorRender.mathColorList=s;
		else if(c==color.atColor) ColorRender.atColorList=s;
		else if(c==color.englishColor) ColorRender.englishColorList=s;
		else if(c==color.mathColor) ColorRender.englishColorList=s;
		else return false;
		return true;
	}
}
