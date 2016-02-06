package pnb.orp.handlers

import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.AxisAlignedBB
import net.minecraft.util.IChatComponent
import net.minecraft.util.ChatComponentText
import net.minecraftforge.event.ServerChatEvent
import net.minecraftforge.fml.common.Mod.EventHandler
import pnb.orp.proxy.CommonProxy
import net.minecraft.entity.player.EntityPlayerMP
import pnb.orp.util.ChatStyle
import net.minecraft.util.EnumChatFormatting

class ChatHandler(protected val proxy: CommonProxy) {
  
  @EventHandler
  def onServerChatEvent(e: ServerChatEvent) = {
    //Check if the event is a message being sent
    if ( e.message != null ) {
      //Stop the event
      e.setCanceled(true)
  		//Get the player who sent the message.
      //Get the active character. 
      //Look up current channel the active character is in
      val channel = proxy.getChatChannel(proxy.loadCharacter(e.player.getUniqueID))
      //Send the message on that channel.
      channel match {
        case "t" => sendInTalk(e)//send message on talk channel
        case "s" => sendInShout(e)//send message on shout channel
        case "w" => sendInWhisper(e)//send message on whisper channel
        case "o" => //send message on ooc channel
        case "l" => //send message on looc channel
        case "h" => //send message on help channel
        case "g" => //send message on gm channel
        case "e" => //send message on event channel
      }
    }
	}
  
  def sendInTalk(e: ServerChatEvent) = {
    sendRangedMessageFromPlayer(e, 24, 
        new ChatStyle(List(EnumChatFormatting.GREEN, EnumChatFormatting.ITALIC), 
        separator = " ",
        quote = List(EnumChatFormatting.WHITE)))
  }
  
  def sendInShout(e: ServerChatEvent) = {
    sendRangedMessageFromPlayer(e, 48,
        new ChatStyle(List(EnumChatFormatting.RED, EnumChatFormatting.ITALIC), 
        separator = " shouts ",
        quote = List(EnumChatFormatting.WHITE)))
  }
  
  def sendInWhisper(e: ServerChatEvent) = { 
    sendRangedMessageFromPlayer(e, 6,
        new ChatStyle(List(EnumChatFormatting.BLUE, EnumChatFormatting.ITALIC), 
        separator = " whispers ",
        quote = List(EnumChatFormatting.WHITE)))
  }
  
  def sendRangedMessageFromPlayer(e: ServerChatEvent, range:Int, style: ChatStyle) = {
    //Get player's position and load their active character
    val playerPos = e.player.getPosition
    val character = proxy.loadCharacter(e.player.getUniqueID)
    //Style message
    val message = style.apply(character.name, e.message)
    //Find all players within talk range
    val playersInRange = e.player.getServerForPlayer.getEntitiesWithinAABB(classOf[EntityPlayerMP], 
      AxisAlignedBB.fromBounds(
          playerPos.getX-range, 
          playerPos.getY-range, 
          playerPos.getZ-range,
          playerPos.getX+range, 
          playerPos.getY+range, 
          playerPos.getZ+range))
    //Send message as character name to all players within talk range
    val iterator = playersInRange.iterator
    while (iterator.hasNext) {
      iterator.next.asInstanceOf[EntityPlayerMP].addChatMessage(new ChatComponentText(message))
    }
  }
}