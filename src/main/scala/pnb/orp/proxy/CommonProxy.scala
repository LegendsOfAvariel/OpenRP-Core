/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Project New Beginning
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
package pnb.orp.proxy

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import pnb.orp.characters.Character;

class CommonProxy {
  
  private var dbConnection: Connection = null
	private var configDirectory: String = null
  
	def init(configDirectory: String) = {
		this.configDirectory = configDirectory
		//TODO Initialize DB Connection
		this.dbConnection = null
  }
  
  /**
   * Loads a requested character from the database, given the character name and player uuid.
   * @param uuid The player UUID
   * @param name Character's Name (should be card name)
   * @return The character object.
   */
	def loadCharacter(uuid: UUID, cardName: String ):Character = {
		//Get th
		val sql: String = if (cardName==null) "SELECT * FROM Characters WHERE UUID='" + uuid.toString + "' AND active=true" 
		  else "SELECT * FROM Characters WHERE UUID='" + uuid.toString + "' AND cardName='" + cardName + "'"
		
		var character: Character = null
		//Try to load the character
  	try {
  		//Run the query
		  val result = this.dbConnection.createStatement.executeQuery(sql)
			
		  //Set the pointer to the first result
		  result.first
			
		  //Load the character
		  character = new Character (this.dbConnection, 
	      result.getObject("uuid").asInstanceOf[UUID], 
	      result.getString("cardName"),
			  name = result.getString("name"),
			  age = result.getInt("age"),
			  race = result.getString("race"),
			  subrace = result.getString("subrace"),
			  bio = result.getString("bio"),
			  active = result.getBoolean("active"))
			
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
	def loadCharacterAndMakeActive(uuid: UUID, cardName: String):Character = {
		
		var character: Character = null
		
		//Try to load the character and make them active
    try {
			//this.dbConnection.setAutoCommit(true);
			this.dbConnection.createStatement.executeQuery("UPDATE Characters SET active=false WHERE UUID='" + uuid.toString + "' AND active=true")
			
			val result = this.dbConnection.createStatement.executeQuery("SELECT * FROM Characters WHERE UUID='" + uuid.toString + "' AND cardName='" + cardName + "'")
			
			result.first
			
			//Initialize our Character
			val character = new Character(dbConnection, 
			    result.getObject("uuid").asInstanceOf[UUID], 
			    result.getString("cardName"), 
			    name = result.getString("name"), 
			    age = result.getInt("age"), 
			    race = result.getString("race"), 
			    subrace = result.getString("subrace"), 
			    bio = result.getString("bio"), 
			    active = true)
			
		} catch {
			// TODO Auto-generated catch block
		  case e: SQLException => e.printStackTrace
		}
    character
	}

	def shutdownDB = {
		try {
			this.dbConnection.close
		} catch {
			// TODO Auto-generated catch block
			case e: SQLException => e.printStackTrace
		}
	}
  
}