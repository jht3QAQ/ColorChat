package jht3.colorchat;

import java.io.IOException;

import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiScreen;

public class ExtendsGuiChat extends GuiChat{
	@Override
    public void sendChatMessage(String msg)
    {
		this.mc.ingameGUI.getChatGUI().addToSentMessages(msg);
		msg=ColorRender.toColor(msg);
        this.sendChatMessage(msg, false);
    }
}
