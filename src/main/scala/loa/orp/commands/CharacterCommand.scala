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
package loa.orp.commands

import java.util.ArrayList

import net.minecraft.command.ICommand
import net.minecraft.command.ICommandSender
import net.minecraft.util.BlockPos
import net.minecraft.client.Minecraft

import loa.orp.gui.CharacterCreationScreen

class CharacterCommand extends ICommand {
  
  private val aliases: java.util.List[String] = new ArrayList[String]
  aliases.add("character")
  aliases.add("char")
  aliases.add("c")
  
  override def getCommandName:String = {
    "sample"
  }

  override def getCommandUsage(icommandsender: ICommandSender):String = {
    """
    |Character Sheet Commands
    |
    |/character [option]
    |
    |Options:
    |help     This message
    |create   Opens the character creation dialog
    |manage   Opens the character management screen
    |delete   Deletes the named character
    |list     List existing characters
    |view     Views the named character sheet
    """".stripMargin
  }

  override def getCommandAliases:java.util.List[String] = {
    aliases
  }

  override def processCommand(icommandsender: ICommandSender, astring: Array[String]) = astring(0) match {
    case "help" => getCommandUsage(icommandsender)
    case "create" => Minecraft.getMinecraft.displayGuiScreen(new CharacterCreationScreen)
  }

  override def canCommandSenderUseCommand(icommandsender: ICommandSender):Boolean = {
    true
  }

  override def addTabCompletionOptions(icommandsender: ICommandSender, astring: Array[String], pos: BlockPos):java.util.List[String] = {
    null
  }

  override def isUsernameIndex(astring: Array[String], i: Int): Boolean = {
    false
  }

  override def compareTo(o :ICommand):Int = {
    0
  }
}