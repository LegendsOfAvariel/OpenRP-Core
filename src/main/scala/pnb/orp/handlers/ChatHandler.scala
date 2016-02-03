package pnb.orp.handlers

import net.minecraftforge.event.ServerChatEvent

import pnb.orp.proxy.CommonProxy

class ChatHandler(protected val proxy: CommonProxy) {
  
  def onServerChatEvent(e: ServerChatEvent) = {
    //Stop the event
		//Get the player who sent the message.
    //Find out what chat channel they are on.
    //Send the message on that channel.
	}
}