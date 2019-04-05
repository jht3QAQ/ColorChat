package jht3.colorchat;

import java.io.IOException;
import java.util.List;

import com.google.common.collect.Lists;

import jht3.colorchat.ColorRender.color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiButtonImage;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.client.gui.GuiOptionsRowList;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.GuiScreenEvent.InitGuiEvent;

public class MyGuiChat extends GuiScreen{ 
	GuiOptopnsMyGuiChatList list;
	GuiButton guiDone;
	MyGuiChat myGuiChat;
	
	@Override
	public void initGui(){
		guiDone=new GuiButton(200, this.width / 2 - 100, this.height - 27, I18n.format("gui.done"));
		this.buttonList.add(guiDone);
		this.list=new GuiOptopnsMyGuiChatList(this.mc,this.width,this.height,32, this.height - 32, 25);
		myGuiChat=this;
	}
	
	@Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
    	super.mouseClicked(mouseX, mouseY, mouseButton);
    	this.list.mouseClicked(mouseX, mouseY, mouseButton);
    }
	
    @Override
    protected void mouseReleased(int mouseX, int mouseY, int mouseButton)
    {
    	super.mouseReleased(mouseX, mouseY, mouseButton);
    	this.list.mouseReleased(mouseX, mouseY, mouseButton);
    }
    
	@Override
    public void drawScreen(int par1, int par2, float par3) 
    {
    	drawBackground(0);
        this.list.drawScreen(par1, par2, par3);
    	this.drawCenteredString(this.fontRenderer, I18n.format("colorchat.gui.colorchatsettig"), this.width/2, 5 , 16777215);
        super.drawScreen(par1,par2,par3);
    }
	
    @Override
    public void onGuiClosed(){
    	ConfigLoader.saveConfig(); 
    } 
    
    @Override
    protected void actionPerformed(GuiButton button) throws IOException
    {
        if (button.enabled)
        {
            if (button.equals(guiDone))
            {
            	this.onGuiClosed();
            	this.mc.displayGuiScreen(null);
            }
        }
    }

	public static void addButton(InitGuiEvent.Post event){
		List<GuiButton> list=event.getButtonList();
		Minecraft mc=Minecraft.getMinecraft();
		GuiScreen gui=event.getGui(); 
		
		list.add(new GuiButtonImage(list.size(),gui.width-14 ,gui.height-14, 12, 12, 0, 0, 0, new ResourceLocation("colorchat","textures/gui/config.png")){
			ResourceLocation res=new ResourceLocation("colorchat","textures/gui/config.png");
			@Override    
			public boolean mousePressed(Minecraft mc, int mouseX, int mouseY)
		    {
				if(mouseX>=gui.width-14&&mouseX<=gui.width-2&&mouseY>=gui.height-14&&mouseY<=gui.height-2){
					mc.displayGuiScreen(new MyGuiChat());
					return true;
				}
		        return false;    
		    }
			@Override
		    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks)
		    {
	            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
	            this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
	            int i = this.getHoverState(this.hovered);
	            GlStateManager.enableBlend();
	            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
	            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
	            this.mouseDragged(mc, mouseX, mouseY);
	            
	            mc.getTextureManager().bindTexture(this.res);
	            GlStateManager.disableDepth();
	            this.drawTexturedModalRect(this.x, this.y, 0, 0, this.width, this.height);
	            GlStateManager.enableDepth();
		    } 
		});  
		event.setButtonList(list); 
	}  
	
	class GuiOptopnsMyGuiChatList extends GuiListExtended{
		
		GuiButton colorChatOn;
		GuiButton colorChatMode;
		GuiButton colorSet;
		GuiButton atOn;
		GuiButton atSet;
		GuiButton englishOn;
		GuiButton englishSet;
		GuiButton mathOn;
		GuiButton mathSet;
		
		private final List<GuiOptionsRowList.Row> entry = Lists.<GuiOptionsRowList.Row>newArrayList();
		
		public GuiOptopnsMyGuiChatList(Minecraft mcIn, int widthIn, int heightIn, int topIn, int bottomIn,
				int slotHeightIn) {
			super(mcIn, widthIn, heightIn, topIn, bottomIn, slotHeightIn); 
			colorChatOn=new GuiButton(slotHeightIn, widthIn / 2 - 155, 0,150,20, I18n.format("colorchat.gui.colorchatswitch.off")){ 
				@Override
				public boolean mousePressed(Minecraft mc, int mouseX, int mouseY)
			    {
					if(this.isMouseOver()){
						ConfigLoader.setIsUseColorChat(!ColorRender.isUseColorChat);
						if(ColorRender.isUseColorChat)colorChatOn.displayString=I18n.format("colorchat.gui.colorchatswitch.on");
						else colorChatOn.displayString=I18n.format("colorchat.gui.colorchatswitch.off");
						return true;
			    	}
			        return false;    
			    }   
			};
			if(ColorRender.isUseColorChat)colorChatOn.displayString=I18n.format("colorchat.gui.colorchatswitch.on");
			colorChatMode=new GuiButton(slotHeightIn, widthIn / 2 - 155, 0,150,20,I18n.format("colorchat.gui.colorchatmode.off")){
				@Override
				public boolean mousePressed(Minecraft mc, int mouseX, int mouseY)
			    {
					ColorRender.Mode mode = ColorRender.Mode.NONE;
					if(this.isMouseOver()){
						if(ColorRender.mainMode==ColorRender.Mode.NONE) {mode=ColorRender.Mode.RANDOM;this.displayString=I18n.format("colorchat.gui.colorchatmode.randomcolor");}
						if(ColorRender.mainMode==ColorRender.Mode.RANDOM) {mode=ColorRender.Mode.SINGLE;this.displayString=I18n.format("colorchat.gui.colorchatmode.singlecolor");}
						if(ColorRender.mainMode==ColorRender.Mode.SINGLE) {mode=ColorRender.Mode.NONE;this.displayString=I18n.format("colorchat.gui.colorchatmode.off");}
						ConfigLoader.setMode(mode);
						return true;
			    	}
			        return false;    
			    }   
			};
			if(ColorRender.mainMode==ColorRender.Mode.NONE) colorChatMode.displayString=I18n.format("colorchat.gui.colorchatmode.off");
			if(ColorRender.mainMode==ColorRender.Mode.RANDOM) colorChatMode.displayString=I18n.format("colorchat.gui.colorchatmode.randomcolor");
			if(ColorRender.mainMode==ColorRender.Mode.SINGLE) colorChatMode.displayString=I18n.format("colorchat.gui.colorchatmode.singlecolor");
			colorSet=new GuiButton(slotHeightIn,widthIn/2-155+160,0,150,20,I18n.format("colorchat.gui.colorsetting")){
				@Override
				public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) 
			    {
					if(this.isMouseOver()){
						mcIn.displayGuiScreen(new ColorSet(ColorRender.color.mainColor,myGuiChat));
						return true;
			    	}
			        return false;    
			    }   
			}; 
			
			this.entry.add(new GuiOptionsRowList.Row(colorChatOn, null));
			this.entry.add(new GuiOptionsRowList.Row(colorChatMode, colorSet));
			
			atOn=new GuiButton(slotHeightIn, widthIn / 2 - 155, 0,150,20, I18n.format("colorchat.gui.atsetting.on")){ 
				@Override
				public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) 
			    {
					if(this.isMouseOver()){
						ConfigLoader.setIsUseAtColor(!ColorRender.at);
						if(ColorRender.at)atOn.displayString=I18n.format("colorchat.gui.atsetting.on");
						else atOn.displayString=I18n.format("colorchat.gui.atsetting.off");
						return true;
			    	}
			        return false;    
			    }   
			}; 
			if(!ColorRender.at) atOn.displayString=I18n.format("colorchat.gui.atsetting.off");
			atSet=new GuiButton(slotHeightIn,widthIn/2 -155 +160 ,0,150,20,I18n.format("colorchat.gui.atsetting")){
				@Override
				public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) 
			    {
					if(this.isMouseOver()){
						mcIn.displayGuiScreen(new ColorSet(ColorRender.color.atColor,myGuiChat));
						return true;
			    	}
			        return false;    
			    }   
			}; 
			this.entry.add(new GuiOptionsRowList.Row(atOn, atSet));
			englishOn=new GuiButton(slotHeightIn, widthIn / 2 - 155, 0,150,20, I18n.format("colorchat.gui.englishsetting.on")){ 
				@Override
				public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) 
			    {
					if(this.isMouseOver()){
						ConfigLoader.setIsUseEnglishColor(!ColorRender.english);
						if(ColorRender.english)englishOn.displayString=I18n.format("colorchat.gui.englishsetting.on");
						else englishOn.displayString=I18n.format("colorchat.gui.englishsetting.off");
						return true;
			    	}
			        return false;    
			    }   
			}; 
			if(!ColorRender.english) englishOn.displayString=I18n.format("colorchat.gui.englishsetting.off");
			englishSet=new GuiButton(slotHeightIn,widthIn/2 -155 +160 ,0,150,20,I18n.format("colorchat.gui.englishsetting")){
				@Override
				public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) 
			    {
					if(this.isMouseOver()){
						mcIn.displayGuiScreen(new ColorSet(ColorRender.color.englishColor,myGuiChat));
						return true;
			    	}
			        return false;    
			    }   
			}; 
			this.entry.add(new GuiOptionsRowList.Row(englishOn, englishSet));
			mathOn=new GuiButton(slotHeightIn, widthIn / 2 - 155, 0,150,20, I18n.format("colorchat.gui.mathsetting.on")){ 
				@Override
				public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) 
			    {
					if(this.isMouseOver()){
						ConfigLoader.setIsUseMathColor(!ColorRender.math);
						if(ColorRender.math)mathOn.displayString=I18n.format("colorchat.gui.mathsetting.on");
						else mathOn.displayString=I18n.format("colorchat.gui.mathsetting.off");
						return true;
			    	}
			        return false;    
			    }    
			}; 
			if(!ColorRender.math) mathOn.displayString=I18n.format("colorchat.gui.mathsetting.off");
			mathSet=new GuiButton(slotHeightIn,widthIn/2 -155 +160 ,0,150,20,I18n.format("colorchat.gui.mathsetting")){
				@Override
				public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) 
			    {
					if(this.isMouseOver()){
						mcIn.displayGuiScreen(new ColorSet(ColorRender.color.mathColor,myGuiChat));
						return true;
			    	}
			        return false;    
			    }   
			}; 
			this.entry.add(new GuiOptionsRowList.Row(mathOn,mathSet));
		} 

		@Override 
		public IGuiListEntry getListEntry(int index) { 
			return entry.get(index);
		}

		@Override
		protected int getSize() {
			return this.entry.size();
		}
		
		@Override
	    public boolean mouseClicked(int mouseX, int mouseY, int mouseEvent)
	    {
	        if (this.isMouseYWithinSlotBounds(mouseY))
	        {
	            int i = this.getSlotIndexFromScreenCoords(mouseX, mouseY);

	            if (i >= 0)
	            {
	                int j = this.left + this.width / 2 - this.getListWidth() / 2 + 2;
	                int k = this.top + 4 - this.getAmountScrolled() + i * this.slotHeight + this.headerPadding;
	                int l = mouseX - j;
	                int i1 = mouseY - k;

	                if (this.getListEntry(i).mousePressed(i, mouseX, mouseY, mouseEvent, l, i1))
	                {
	                    this.setEnabled(false);
	                    return true;
	                }
	            }
	        }

	        return false;
	    }
		
		@Override
	    public boolean mouseReleased(int x, int y, int mouseEvent)
	    {
	        for (int i = 0; i < this.getSize(); ++i)
	        {
	            int j = this.left + this.width / 2 - this.getListWidth() / 2 + 2;
	            int k = this.top + 4 - this.getAmountScrolled() + i * this.slotHeight + this.headerPadding;
	            int l = x - j;
	            int i1 = y - k;
	            this.getListEntry(i).mouseReleased(i, x, y, mouseEvent, l, i1);
	        }

	        this.setEnabled(true);
	        return false;
	    }
	}
	
	class ColorSet extends GuiScreen{
		ColorRender.color color;
		GuiScreen parentGuiScreen;
		boolean[] b;
		String[] colorName=new String[]{I18n.format("colorchat.gui.color.darkblue")+"&1",I18n.format("colorchat.gui.color.darkgreen")+"&2",
				I18n.format("colorchat.gui.color.cyan")+"&3",I18n.format("colorchat.gui.color.crimson")+"&4",
				I18n.format("colorchat.gui.color.violet")+"&5",I18n.format("colorchat.gui.color.deepyellow")+"&6",
				I18n.format("colorchat.gui.color.gray")+"&7",I18n.format("colorchat.gui.color.black")+"&0",
				I18n.format("colorchat.gui.color.lightblue")+"&9",I18n.format("colorchat.gui.color.lightgreen")+"&a",
				I18n.format("colorchat.gui.color.skyblue")+"&b",I18n.format("colorchat.gui.color.palered")+"&c",
				I18n.format("colorchat.gui.color.pink")+"&d",I18n.format("colorchat.gui.color.lightyellow")+"&e",
				I18n.format("colorchat.gui.color.white")+"&f",I18n.format("colorchat.gui.color.darkgray")+"&8"};
		int[] colorRGB=new int[]{0x0000BE,0x00BE00,0x00BEBE,0xBE0000,0xBE00BE,0xD8A232,0xBEBEBE,0x000000,0x3F3FFF,0x3EFE3E,0x40FDFD,0xFF3F3F,0xFF3FFF,0xFFFF3F,0xFFFFFF,0x3F3F3F};
		GuiButton exit;
		GuiButton[] colorList;
		ColorSet(ColorRender.color c,GuiScreen parentGuiScreen){
			this.color=c;
			this.parentGuiScreen=parentGuiScreen;
			b=ConfigLoader.getColorList(c);
		} 
		@Override
		public void initGui(){
			exit=new GuiButton(200, this.width / 2 - 100, this.height - 27, I18n.format("gui.done"));
			this.buttonList.add(exit);
			colorList=new GuiButton[16];
			for(int i=0;i<8;i++){
				colorList[i]=new ColorSetButton(this.buttonList.size(),this.width/2 -160+40*i,32,40,20,colorName[i],colorRGB[i]);
				this.addButton(colorList[i]); 
				colorList[i].enabled=b[i];
			}
			for(int i=0;i<8;i++){
				colorList[i+8]=new ColorSetButton(this.buttonList.size(),this.width/2 -160+40*i,32+20,40,20,colorName[i+8],colorRGB[i+8]);
				this.addButton(colorList[i+8]);  
				colorList[i+8].enabled=b[i+8]; 
			}
		}
		@Override
	    public void onGuiClosed(){
			for(int i=0;i<16;i++){
				b[i]=colorList[i].enabled;
			}
			ConfigLoader.setColorList(color, b);
	    	ConfigLoader.saveConfig();
	    }
		@Override
	    protected void actionPerformed(GuiButton button) throws IOException
	    {
	        if (button.enabled)
	        {
	            if (button.equals(exit))
	            {
	                this.mc.displayGuiScreen(this.parentGuiScreen); 
	            }
	        }
	    }
		@Override
	    public void drawScreen(int par1, int par2, float par3) 
	    {
	    	drawBackground(0);
	    	if(color==ColorRender.color.mainColor)this.drawCenteredString(this.fontRenderer, I18n.format("colorchat.gui.colorsetting"), this.width/2, 5 , 16777215);
	    	if(color==ColorRender.color.atColor)this.drawCenteredString(this.fontRenderer, I18n.format("colorchat.gui.atsetting"), this.width/2, 5 , 16777215);
	    	if(color==ColorRender.color.mathColor)this.drawCenteredString(this.fontRenderer, I18n.format("colorchat.gui.mathsetting"), this.width/2, 5 , 16777215);
	    	if(color==ColorRender.color.englishColor)this.drawCenteredString(this.fontRenderer, I18n.format("colorchat.gui.englishsetting"), this.width/2, 5 , 16777215);
	        super.drawScreen(par1,par2,par3);
	        
	    }
		class ColorSetButton extends GuiButton{
			int color;
			//MyGuiChat screen;
			ColorSetButton(int buttonId, int x, int y, int widthIn, int heightIn,String buttonText,int color){
				super(buttonId, x, y, widthIn, heightIn, buttonText);
				this.color=color;
				//this.screen=screen;
			}
			
			@Override
			public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks)
		    {
		        if (this.visible)
		        {
		            FontRenderer fontrenderer = mc.fontRenderer;
		            mc.getTextureManager().bindTexture(BUTTON_TEXTURES);
		            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		            this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
		            int i = this.getHoverState(this.hovered);
		            GlStateManager.enableBlend();
		            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
		            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
		            this.drawTexturedModalRect(this.x, this.y, 0, 46 + i * 20, this.width / 2, this.height);
		            this.drawTexturedModalRect(this.x + this.width / 2, this.y, 200 - this.width / 2, 46 + i * 20, this.width / 2, this.height);
		            this.mouseDragged(mc, mouseX, mouseY);
		            
		            this.drawCenteredString(fontrenderer, this.displayString, this.x + this.width / 2, this.y + (this.height - 8) / 2, color);
		        }
		    }
			
			@Override
		    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY)
		    {
		        return this.visible && mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
		    }
			
			@Override
		    public void mouseReleased(int mouseX, int mouseY)
		    {
				this.enabled=!this.enabled;
		    }
		}
	}
}
 