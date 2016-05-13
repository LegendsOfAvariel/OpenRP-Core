/**
 * OpenRP Core
 * Character Sheet and Chat mod
 * @author Emily Marriott
 * 
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Legends of Avariel
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package loa.orp.handlers

import net.minecraft.util.math.AxisAlignedBB
import net.minecraft.util.text.TextComponentString
import net.minecraftforge.event.ServerChatEvent
import net.minecraftforge.fml.common.Mod.EventHandler
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.util.text.TextFormatting

import loa.orp.proxy.CommonProxy
import loa.orp.util.ChatStyle

class ChatHandler(protected val proxy: CommonProxy) {
  
  @EventHandler
  def onServerChatEvent(e: ServerChatEvent) = {
    //Get the player who sent the message.
    //Get the active character. 
    val character = proxy.loadCharacter(e.getPlayer.getUniqueID)
    if ( character != None ) {
      //Stop the event
      e.setCanceled(true)
      //Look up current channel the active character is in
      val channel = proxy.getChatChannel(character.get)
      //Send the message on that channel.
      channel match {
        case "t" => sendRangedMessageFromPlayer(e, 24, 
                      new ChatStyle(List(TextFormatting.GREEN, TextFormatting.ITALIC),
                      character.get.name,
                      separator = " ",
                      quote = Some(List(TextFormatting.WHITE))))//send message on talk channel
        case "s" => sendRangedMessageFromPlayer(e, 48,
                      new ChatStyle(List(TextFormatting.RED, TextFormatting.ITALIC),
                      character.get.name,
                      separator = " shouts ",
                      quote = Some(List(TextFormatting.WHITE))))//send message on shout channel
        case "w" => sendRangedMessageFromPlayer(e, 6,
                      new ChatStyle(List(TextFormatting.BLUE, TextFormatting.ITALIC),
                      character.get.name,
                      separator = " whispers ",
                      quote = Some(List(TextFormatting.WHITE))))//send message on whisper channel
        case "o" => //send message on ooc channel
        case "l" => sendRangedMessageFromPlayer(e, 6,
                      new ChatStyle(List(TextFormatting.GRAY),
                      e.getPlayer.getName,
                      separator = ": "))//send message on looc channel
        case "h" => //send message on help channel
        case "g" => //send message on gm channel
        case "e" => //send message on event channel
      }
    }
	}
  
  private def sendRangedMessageFromPlayer(e: ServerChatEvent, range:Int, style: ChatStyle) = {
    //Get player's position and load their active character
    val playerPos = e.getPlayer.getPosition
    //Style message
    val message = style.apply(e.getMessage)
    //Find all players within talk range
    val playersInRange = e.getPlayer.getServerForPlayer.getEntitiesWithinAABB(classOf[EntityPlayerMP],
      new AxisAlignedBB (
          playerPos.getX-range, 
          playerPos.getY-range, 
          playerPos.getZ-range,
          playerPos.getX+range, 
          playerPos.getY+range, 
          playerPos.getZ+range))
    //Send message as character name to all players within talk range
    val iterator = playersInRange.iterator
    while (iterator.hasNext) {
      iterator.next.addChatMessage(new TextComponentString(message))
    }
  }
}