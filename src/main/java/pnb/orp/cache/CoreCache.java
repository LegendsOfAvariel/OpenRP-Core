package pnb.orp.cache;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import pnb.orp.characters.Character;
import pnb.orp.characters.Character.CharacterBuilder;

@Singleton
public class CoreCache implements ORPCache {
	
	private final Connection dbConnection;
	private final String configDirectory;
	
	@Inject
	private CoreCache(String configDirectory) {
		this.configDirectory = configDirectory;
		this.dbConnection = null; 
	}
	
	/**
     * Loads a requested character from the database, given the character name and player uuid.
     * @param uuid The player UUID
     * @param name Character's Name (should be card name)
     * @return The character object.
     */
	@Override
	public synchronized Character loadCharacter(UUID uuid, String cardName) {
		return loadCharacterBuilder(uuid, cardName).build();
	}
	
	/**
	 * Loads a character from the database and makes them active.
	 * @param uuid UUID of the player
	 * @param cardName the name of the character card
	 * @return the character card
	 */
	@Override
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
	@Override
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

	@Override
	public void shutdownCache() {
		try {
			this.dbConnection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
