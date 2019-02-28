package jht3.colorchat;

import java.io.File;

import org.apache.logging.log4j.Logger;

import com.typesafe.config.Config;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ConfigLoader {
	private static final String sisUseAtColor="是否启用at识别功能";
	private static final String sisUseASM="是否使用ASM加载mod";
	private static final String sisUseColorChat="是否启用ColorChat";
	private static final String smainMode="ColorChat模式 (0为不启用,1为混彩模式,2为单色模式)";
	private static final String sisUseMathColor="是否启用数字识别功能";
	private static final String sisUseEnglishColor="是否启用英语识别功能";
	private static final String smainColor="设置主要颜色\n依次为:\n深蓝&1   深绿&2  青色&3  深红&4  紫色&5  深黄&6  灰色&7  黑色&0\n浅蓝&9  浅绿&a  天蓝&b  浅红&c  粉色&d  浅黄&e  白色&f  灰黑&8";
	private static final String satColor="设置at颜色";
	private static final String smathColor="设置数字颜色";
	private static final String senglishColor="设置英文颜色";

    private static Configuration config;
    private static boolean isUseASM;
    private static boolean isUseColorChat;
    private static ColorRender.Mode mode;
    private static boolean isUseAtColor;
    private static boolean isUseMathColor;
    private static boolean isUseEnglishColor;
    private static boolean[] mainColor;
    private static boolean[] atColor;
    private static boolean[] englishColor;
    private static boolean[] mathColor;

    private static Logger logger;
    
    public ConfigLoader(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
        config = new Configuration(new File(event.getModConfigurationDirectory(), "colorChat.cfg"));

        config.load();
        loadConfig();
    }
    
	public static void saveConfig(){
		config.get(Configuration.CATEGORY_GENERAL,"isUseColorChat",true,sisUseColorChat).set(isUseColorChat);
		
		int i=0;
		if(mode==ColorRender.Mode.NONE) i=0;
		if(mode==ColorRender.Mode.RANDOM) i=1;
		if(mode==ColorRender.Mode.SINGLE) i=2;
		config.get(Configuration.CATEGORY_GENERAL, "mainMode",1 ,smainMode).set(i);

		config.get(Configuration.CATEGORY_GENERAL, "isUseAtColor", true,sisUseAtColor).set(isUseAtColor);
		config.get(Configuration.CATEGORY_GENERAL, "isUseMathColor", true,sisUseMathColor).set(isUseMathColor);
		config.get(Configuration.CATEGORY_GENERAL, "isUseEnglishColor", true,sisUseEnglishColor).set(isUseEnglishColor);
		
		config.get(Configuration.CATEGORY_GENERAL, "mainColor",new boolean[]{false,false,false,false,false,false,false,false,true,true,true,true,true,true,true,true} ,smainColor).set(mainColor);
		config.get(Configuration.CATEGORY_GENERAL, "atColor",new boolean[]{false,false,false,false,false,false,false,false,true,true,true,true,true,true,true,true} ,satColor).set(atColor);
		config.get(Configuration.CATEGORY_GENERAL, "mathColor",new boolean[]{false,false,false,false,false,false,false,false,true,true,true,true,true,true,true,true} ,smathColor).set(mathColor);
		config.get(Configuration.CATEGORY_GENERAL, "englishColor",new boolean[]{false,false,false,false,false,false,false,false,true,true,true,true,true,true,true,true} ,senglishColor).set(englishColor);
		
		config.get(Configuration.CATEGORY_GENERAL,"isUseASM",true,sisUseASM).set(isUseASM);
		
		config.save();
	}
	public static void loadConfig(){
		logger.info("Started loading config.");
		
		isUseASM=config.get(Configuration.CATEGORY_GENERAL,"isUseASM",true,sisUseASM).getBoolean();
		
		isUseColorChat=config.get(Configuration.CATEGORY_GENERAL,"isUseColorChat",true,sisUseColorChat).getBoolean();
		
		int i=config.get(Configuration.CATEGORY_GENERAL, "mainMode",1 ,smainMode).getInt();
		if(i==0) mode=ColorRender.Mode.NONE;
		else if(i==1) mode=ColorRender.Mode.RANDOM;
		else if(i==2) mode=ColorRender.Mode.SINGLE;
		else{
			config.get(Configuration.CATEGORY_GENERAL, "mainMode",1 ,smainMode).set(1);
			mode=ColorRender.Mode.RANDOM;
		}
		
		
		isUseAtColor=config.get(Configuration.CATEGORY_GENERAL, "isUseAtColor", true,sisUseAtColor).getBoolean();
		
		isUseMathColor=config.get(Configuration.CATEGORY_GENERAL, "isUseMathColor", true,sisUseMathColor).getBoolean();
		
		isUseEnglishColor=config.get(Configuration.CATEGORY_GENERAL, "isUseEnglishColor", true,sisUseEnglishColor).getBoolean();
		
		mainColor=config.get(Configuration.CATEGORY_GENERAL, "mainColor",new boolean[]{false,false,false,false,false,false,false,false,true,true,true,true,true,true,true,true} ,smainColor).getBooleanList();
		
		atColor=config.get(Configuration.CATEGORY_GENERAL, "atColor",new boolean[]{false,false,false,false,false,false,false,false,true,true,true,true,true,true,true,true} ,satColor).getBooleanList();
		
		mathColor=config.get(Configuration.CATEGORY_GENERAL, "mathColor",new boolean[]{false,false,false,false,false,false,false,false,true,true,true,true,true,true,true,true} ,smathColor).getBooleanList();

		englishColor=config.get(Configuration.CATEGORY_GENERAL, "englishColor",new boolean[]{false,false,false,false,false,false,false,false,true,true,true,true,true,true,true,true} ,senglishColor).getBooleanList();
		
		config.save();
		ConfigLoader.setConfig();
		logger.info("Finished loading config.");
	}
	
	
	public static void setConfig(){
		ColorRender.isUseColorChat=ConfigLoader.isUseColorChat;
		ColorRender.mainMode=mode;
		ColorRender.at=isUseAtColor;
		ColorRender.english=isUseEnglishColor;
		ColorRender.math=isUseMathColor;
		
		ColorRender.setColorList(mainColor, ColorRender.color.mainColor);
		ColorRender.setColorList(atColor, ColorRender.color.atColor);
		ColorRender.setColorList(mathColor, ColorRender.color.mathColor);
		ColorRender.setColorList(englishColor, ColorRender.color.englishColor);
		
		ColorChat.isUseASM=isUseASM;
	}
	
	public static void setIsUseColorChat(Boolean b){
		ConfigLoader.isUseColorChat=b;
		ColorRender.isUseColorChat=ConfigLoader.isUseColorChat;
		}
	public static void setMode(ColorRender.Mode mode){
		ColorRender.mainMode=mode;
		ConfigLoader.mode=mode;
	}
	public static void setIsUseEnglishColor(Boolean b){
		ConfigLoader.isUseEnglishColor=b;
		ColorRender.english=b;
	}
	public static void setIsUseMathColor(Boolean b){
		ConfigLoader.isUseMathColor=b;
		ColorRender.math=b;
	}
	public static void setIsUseAtColor(Boolean b){
		ConfigLoader.isUseAtColor=b;
		ColorRender.at=b;
	}
	public static void setIsASM(Boolean b){
		ConfigLoader.isUseASM=b;
		ColorChat.isUseASM=isUseASM;
	}
	public static void setColorList(ColorRender.color color,boolean[] b){
		if(color==ColorRender.color.atColor) ConfigLoader.atColor=b;
		if(color==ColorRender.color.englishColor) ConfigLoader.englishColor=b;
		if(color==ColorRender.color.mainColor) ConfigLoader.mainColor=b;
		if(color==ColorRender.color.mathColor) ConfigLoader.mathColor=b;
		ColorRender.setColorList(b, color);
	}
	
	public static boolean[] getColorList(ColorRender.color color){
		if(color==ColorRender.color.atColor) return ConfigLoader.atColor;
		if(color==ColorRender.color.englishColor) return ConfigLoader.englishColor;
		if(color==ColorRender.color.mainColor) return ConfigLoader.mainColor;
		if(color==ColorRender.color.mathColor) return ConfigLoader.mathColor;
		return null;
	}
}
