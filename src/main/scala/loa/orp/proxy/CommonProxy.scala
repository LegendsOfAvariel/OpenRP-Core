/**
 * OpenRP Core
 * Character Card and Chat mod
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
package loa.orp.proxy

import java.sql.Connection
import java.sql.ResultSet
import java.sql.SQLException
import java.util.UUID

import net.minecraft.entity.player.EntityPlayer
import net.minecraftforge.fml.common.event.
  {FMLInitializationEvent, FMLPostInitializationEvent, 
  FMLPreInitializationEvent, FMLServerStoppingEvent}

import loa.orp.characters.Character;

/**
 * Things common to both the client and server side get done here.
 */
class CommonProxy {
  
  //We want the config directory for both client and server side.
  protected var configDirectory: String = null
  
  def preInit(e: FMLPreInitializationEvent) = {
    configDirectory = e.getSuggestedConfigurationFile.getAbsolutePath
  }
  
  def init(e: FMLInitializationEvent) = {}
  
  def postInit(e: FMLPostInitializationEvent) = {}
  
  def serverStopping(e: FMLServerStoppingEvent) = {}
  
  //A bunch of these differ between client and server,
  //where the server has to load the character, while 
  //the client requests it from the server.
  
  def loadCharacter(uuid: UUID, cardName: String = null ):Option[Character] = {None}
  
  def loadCharacterAndMakeActive(uuid: UUID, cardName: String):Option[Character] = {None}
  
  def saveCharacter(c: Character) = {}
  
  def getChatChannel(character: Character):String = {"t"}
}