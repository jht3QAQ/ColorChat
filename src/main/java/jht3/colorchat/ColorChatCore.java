package jht3.colorchat;

import java.util.Arrays;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.DummyModContainer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.LoadController;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ColorChatCore extends DummyModContainer{
	

	public ColorChatCore()
	{
		super(new ModMetadata());
		ModMetadata meta = getMetadata();
		meta.modId = "colorchartcore";
		meta.name = "ColorChatcore";
		meta.version = "1.0.0";
		meta.authorList = Arrays.asList("jht3");
	}
	
	public static String toColor(String s){
		Minecraft.getMinecraft().ingameGUI.getChatGUI().addToSentMessages(s);
		s=ColorRender.toColor(s);
		return s;
	}
	
    @Override
    public boolean registerBus(EventBus bus, LoadController controller)
    {
    	bus.register(this);
        return true;
    }
    
    @Subscribe
    public void preInit(FMLPreInitializationEvent event)
    {
    	new ConfigLoader(event);
    }
    
    @Subscribe
    public void init(FMLInitializationEvent event)
    {
    	FMLCommonHandler.instance().getFMLLogger().info("ColorChat is starting.");
    }
}
