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

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.event.
  {FMLInitializationEvent, FMLPostInitializationEvent, 
  FMLPreInitializationEvent, FMLServerStoppingEvent}

import loa.orp.characters.Character
import loa.orp.handlers.ChatHandler

class ServerProxy extends CommonProxy {
  
  private var dbConnection: Connection = null
  
  override def preInit(e: FMLPreInitializationEvent) = {
    super.preInit(e)
    
    //TODO Initialize DB Connection
    this.dbConnection = null
  }
  
  override def init(e: FMLInitializationEvent) = {
    super.init(e)
    
    MinecraftForge.EVENT_BUS.register(new ChatHandler(this))
  }
  
  override def serverStopping(e: FMLServerStoppingEvent) = {
    super.serverStopping(e)
	  
    try {
      this.dbConnection.close
    } catch {
      // TODO Auto-generated catch block
      case e: SQLException => e.printStackTrace
    }
  }
  
  /**
   * Loads a requested character from the database, given the character name and player uuid.
   * @param uuid The player UUID
   * @param name Character's Name (should be card name)
   * @return The character object.
   */
  override def loadCharacter(uuid: UUID, name: String = "" ):Option[Character] = {
	  
    //Get the requested character. Get the active if not given a card name.
    val sql: String = if (name=="") "SELECT * FROM Characters WHERE UUID='" + uuid.toString + "' AND active=true" 
      else "SELECT * FROM Characters WHERE UUID='" + uuid.toString + "' AND name='" + name + "'"
		
    var character: Option[Character] = None
    //Try to load the character
    try {
      //Run the query
      val result = this.dbConnection.createStatement.executeQuery(sql)
			
      //Set the pointer to the first result
      result.first
			
      //Load the character
      character = Some(new Character (this, 
        result.getObject("uuid").asInstanceOf[UUID], 
        result.getString("name"),
        age = result.getInt("age"),
        race = result.getString("race"),
        subrace = result.getString("subrace"),
        bio = result.getString("bio"),
        active = result.getBoolean("active")))
			
    } catch {
      // TODO Auto-generated catch block
      case e: SQLException => e.printStackTrace
    }
    character
  }
	
  /**
	 * Loads a character from the database and makes them active.
	 * @param uuid UUID of the player
	 * @param cardName the name of the character card
	 * @return the character card
	 */
  override def loadCharacterAndMakeActive(uuid: UUID, name: String):Option[Character] = {
	  
    var character: Option[Character] = None
		
    //Try to load the character and make them active
    try {
      this.dbConnection.setAutoCommit(true)
      this.dbConnection.createStatement.executeQuery("UPDATE Characters SET active=false WHERE UUID='" + uuid.toString + "' AND active=true")
			
      val result = this.dbConnection.createStatement.executeQuery("SELECT * FROM Characters WHERE UUID='" + uuid.toString + "' AND name='" + name + "'")
			
      result.first
			
      //Initialize our Character
      character = Some(new Character(this, 
        result.getObject("uuid").asInstanceOf[UUID], 
        result.getString("name"), 
        age = result.getInt("age"), 
        race = result.getString("race"), 
        subrace = result.getString("subrace"), 
        bio = result.getString("bio"), 
        active = true))
			
    } catch {
      // TODO Auto-generated catch block
      case e: SQLException => e.printStackTrace
    }
    character
  }

	override def saveCharacter(c: Character) = {
	  
    val sql = "MERGE INTO Characters (uuid, name, age, race, subrace, bio, active)" +
      " KEY (uuid, name) VALUES ('" + c.uuid + "', '" + c.name + "', " + c.age +
      ", '" + c.race + "', '" + c.subrace + "', '" + c.bio + "', " + c.active + " )"
    
    try {
      this.dbConnection.createStatement.execute(sql)
    } catch {
      case e: SQLException => e.printStackTrace
    }
    //Old SQL Insert/Update replaced by the nice, streamlined merge.
    /*String sql = (save==1)?"INSERT INTO Characters VALUES ('" + this.uuid.toString() + "', '" + this.name + 
    					"', '" + this.cardName + "', " + this.age + ", '" + this.race + "', '" + this.subrace + "', " +
    					this.active + ", '" + this.bio + "' )":
    						"UPDATE Characters SET name='" + this.name + "', age=" + this.age + ", race='" + this.race + 
    						"', subrace='" + this.subrace + "', bio='" + this.bio + "', active=" + this.active + 
    						"WHERE uuid='" + this.uuid.toString() + "' AND cardName='" + this.cardName + "'";*/
	}
	
	override def getChatChannel(character: Character):String = {
	  val sql = "SELECT channel FROM CharacterChat WHERE uuid='" + character.uuid.toString() + "' AND name='" + character.name + "'"
	  
	  var channel = "t"
	  
	  try {
      val result = this.dbConnection.createStatement.executeQuery(sql)
      result.first
      channel = result.getString("channel")
    } catch {
      case e: SQLException => e.printStackTrace
    }
	  
	  channel
	}
}