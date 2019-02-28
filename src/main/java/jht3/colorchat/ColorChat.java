package jht3.colorchat;

import java.util.Map;

import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.GuiScreenEvent.InitGuiEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkCheckHandler;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = ColorChat.modid,name=ColorChat.name,version=ColorChat.version,clientSideOnly=true)
public class ColorChat {
	public static final String modid="colorchat";
	public static final String name="ColorChat";
	public static final String version="1.0";
	public static boolean isUseASM;
	@NetworkCheckHandler
	public boolean checker(Map<String,String> map,Side side)
	{
		return true;
	}
	@EventHandler
	public void preLoad(FMLPreInitializationEvent event)
	{
		if(!Loader.isModLoaded("colorchartcore")){
			new ConfigLoader(event);
			ConfigLoader.setIsASM(false);
			ConfigLoader.saveConfig();
		}
	}
	 
	@EventHandler
	public void load(FMLInitializationEvent event)
	{
		MinecraftForge.EVENT_BUS.register(this);
	}
	 
	@EventHandler
	public void postLoad(FMLPostInitializationEvent event)
	{
	}
	
	@SubscribeEvent
	public void guiOpenEvent(GuiOpenEvent event){
		if(event.getGui() instanceof GuiChat &&!(event.getGui() instanceof ExtendsGuiChat)){
			event.setGui(new ExtendsGuiChat());
		}
	}
	
	@SubscribeEvent 
	public void guiScreenShow(InitGuiEvent.Post event)
	{
		if(event.getGui() instanceof GuiChat)//||event.getGui() instanceof GuiMainMenu) 
	    { 
			MyGuiChat.addButton(event);//(MyGuiChat.addButton(event.getButtonList()));
	    }
		//System.out.println(event.getGui().getClass());
	}
}
