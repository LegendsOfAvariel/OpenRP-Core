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
    val message = Option(e.message) //Wrap the possible java null in an Option
    if ( message != None ) {
      //Stop the event
      e.setCanceled(true)
  		//Get the player who sent the message.
      //Get the active character. 
      //Look up current channel the active character is in
      val channel = proxy.getChatChannel(proxy.loadCharacter(e.player.getUniqueID))
      val character = proxy.loadCharacter(e.player.getUniqueID)
      //Send the message on that channel.
      channel match {
        case "t" => sendRangedMessageFromPlayer(e, 24, 
                      new ChatStyle(List(EnumChatFormatting.GREEN, EnumChatFormatting.ITALIC), 
                      character.name,
                      separator = " ",
                      quote = Some(List(EnumChatFormatting.WHITE))))//send message on talk channel
        case "s" => sendRangedMessageFromPlayer(e, 48,
                      new ChatStyle(List(EnumChatFormatting.RED, EnumChatFormatting.ITALIC), 
                      character.name,
                      separator = " shouts ",
                      quote = Some(List(EnumChatFormatting.WHITE))))//send message on shout channel
        case "w" => sendRangedMessageFromPlayer(e, 6,
                      new ChatStyle(List(EnumChatFormatting.BLUE, EnumChatFormatting.ITALIC), 
                      character.name,
                      separator = " whispers ",
                      quote = Some(List(EnumChatFormatting.WHITE))))//send message on whisper channel
        case "o" => //send message on ooc channel
        case "l" => sendRangedMessageFromPlayer(e, 6,
                      new ChatStyle(List(EnumChatFormatting.GRAY), 
                      e.player.getName,
                      separator = ": "))//send message on looc channel
        case "h" => //send message on help channel
        case "g" => //send message on gm channel
        case "e" => //send message on event channel
      }
    }
	}
  
  private def sendRangedMessageFromPlayer(e: ServerChatEvent, range:Int, style: ChatStyle) = {
    //Get player's position and load their active character
    val playerPos = e.player.getPosition
    //Style message
    val message = style.apply(e.message)
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