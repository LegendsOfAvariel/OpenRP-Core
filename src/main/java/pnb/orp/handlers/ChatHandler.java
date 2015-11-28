package pnb.orp.handlers;

import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import pnb.orp.proxy.CommonProxy;


public class ChatHandler {
	
	private final CommonProxy proxy;
	
	public ChatHandler(CommonProxy proxy) {
		this.proxy = proxy;
	}
	
	@SubscribeEvent
	public void onServerChatEvent(ServerChatEvent event) {
		
	}
	
}
