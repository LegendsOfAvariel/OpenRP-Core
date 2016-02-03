package pnb.orp.handlers;

import com.google.inject.Inject;
import com.google.inject.Injector;

import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import pnb.orp.cache.ORPCache;


public class ChatHandler {
	
	private final Injector injector;
	private final ORPCache cache;
	
	@Inject
	private ChatHandler(Injector injector, ORPCache cache) {
		this.injector = injector;
		this.cache = cache;
	}
	
	@SubscribeEvent
	public void onServerChatEvent(ServerChatEvent event) {
		
	}
	
}
