package jht3.colorchat;

import java.util.Map;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.*;


@MCVersion("1.12.2")
public class ColorChatLoadingPlugin implements IFMLLoadingPlugin{
	@Override
	public String[] getASMTransformerClass() {
		return new String[]{
			"jht3.colorchat.ClassTransformer"
		};
	}

	@Override
	public String getModContainerClass() {
		return "jht3.colorchat.ColorChatCore";
	}

	@Override
	public String getSetupClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void injectData(Map<String, Object> data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getAccessTransformerClass() {
		// TODO Auto-generated method stub
		return null;
	}

}
