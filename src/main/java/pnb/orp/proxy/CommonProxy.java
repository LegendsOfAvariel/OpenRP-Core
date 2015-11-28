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
package pnb.orp.proxy;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import com.google.inject.Injector;

import pnb.orp.characters.Character;
import pnb.orp.characters.Character.CharacterBuilder;

public class CommonProxy {
	
	private Connection dbConnection;
	private String configDirectory;
	private Injector injector;
	
	public CommonProxy() {
		dbConnection = null;
		configDirectory = "";
	}
	
	public void init(String configDirectory, Injector injector) {
		this.configDirectory = configDirectory;
		this.injector = injector;
	}
	
	/**
     * Loads a requested character from the database, given the character name and player uuid.
     * @param uuid The player UUID
     * @param name Character's Name (should be card name)
     * @return The character object.
     */
	public synchronized Character loadCharacter(UUID uuid, String cardName) {
		return loadCharacterBuilder(uuid, cardName).build();
	}
	
	/**
	 * Loads a character from the database and makes them active.
	 * @param uuid UUID of the player
	 * @param cardName the name of the character card
	 * @return the character card
	 */
	public synchronized Character loadCharacterAndMakeActive(UUID uuid, String cardName) {
		//Initialize our Character
		Character character = null;
		
		//Try to load the character and make them active
    	try {
			//this.dbConnection.setAutoCommit(true);
			this.dbConnection.createStatement().executeQuery("UPDATE Characters SET active=false WHERE UUID='" + uuid.toString() + "' AND active=true");
			
			ResultSet result = this.dbConnection.createStatement().executeQuery("SELECT * FROM Characters WHERE UUID='" + uuid.toString() + "' AND cardName='" + cardName + "'");
			
			result.first();
			
			character = loadCharacterBuilder((UUID)result.getObject("uuid"), result.getString("cardName"))
				.name(result.getString("name"))
				.age(result.getInt("age"))
				.race(result.getString("race"))
				.subrace(result.getString("subrace"))
				.bio(result.getString("bio"))
				.active(true)
				.buildAndUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return character;
	}
	
	/**
	 * Load a CharacterBuilder for editing purposes.
	 * @param uuid UUID of the player who owns the character
	 * @param The name of the character card being loaded
	 * @return The builder of the character requested.
	 */
	public synchronized CharacterBuilder loadCharacterBuilder(UUID uuid, String cardName) {
		//Initialize our CharacterBuilder and SQL statement
		CharacterBuilder character = null;
		String sql = "";
		
		//If null card name, load the currently active character
		if (cardName==null)
			sql = "SELECT * FROM Characters WHERE UUID='" + uuid.toString() + "' AND active=true";
		//Else load the requested character for editing
		else
			sql = "SELECT * FROM Characters WHERE UUID='" + uuid.toString() + "' AND cardName='" + cardName + "'";
		
		//Try to load the character
    	try {
    		//Run the query
			ResultSet result = this.dbConnection.createStatement().executeQuery(sql);
			
			//Set the pointer to the first result
			result.first();
			
			//Load the character
			character = Character.builder(this.dbConnection, (UUID)result.getObject("uuid"), result.getString("cardName"))
				.name(result.getString("name"))
				.age(result.getInt("age"))
				.race(result.getString("race"))
				.subrace(result.getString("subrace"))
				.bio(result.getString("bio"))
				.active(result.getBoolean("active"));
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return character;
	}

	public void shutdownDB() {
		try {
			this.dbConnection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setRenders() {	
		//This should remain empty
	}
}
