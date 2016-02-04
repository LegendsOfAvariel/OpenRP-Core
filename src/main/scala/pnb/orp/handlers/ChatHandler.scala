package pnb.orp.handlers

import net.minecraftforge.event.ServerChatEvent

import pnb.orp.proxy.CommonProxy

class ChatHandler(protected val proxy: CommonProxy) {
  
  def onServerChatEvent(e: ServerChatEvent) = {
    //Check if the event is a message being sent
    if ( e.message != null ) {
      //Stop the event
      e.setCanceled(true)
  		//Get the player who sent the message.
      val player = e.player.getUniqueID
      //Get the active character.
      val character = proxy.loadCharacter(player)
      //Look up current channel with UUID and active character
      
      //Send the message on that channel.
      
    }
	}
}