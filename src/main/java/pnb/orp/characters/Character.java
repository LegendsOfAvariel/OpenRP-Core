/**
 * Character Cards
 * @author Emily Marriott
 * 
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
package pnb.orp.characters;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

public final class Character {
	
	private final UUID uuid;
	private final String name;
	private final String cardName;
	private final int age;
	private final String race;
	private final String subrace;
	private final String bio;
	private final boolean active;
	
	//Expansion Fields
    
    private Character(CharacterBuilder builder, int save, Connection conn) {
    	
    	//Build the class
    	this.uuid = builder.uuid;
    	this.name = builder.name;
    	this.cardName = builder.cardName;
    	this.age = builder.age;
    	this.race = builder.race;
    	this.subrace = builder.subrace;
    	this.bio = builder.bio;
    	this.active = (save==1)?true:builder.active;
    	
    	
    	//If told to save, then save the character as a new entry.
    	if (save>0) {
    		//Save new character to DB.
        	try {
    			String sql = (save==1)?"INSERT INTO Characters VALUES ('" + this.uuid.toString() + "', '" + this.name + 
    					"', '" + this.cardName + "', " + this.age + ", '" + this.race + "', '" + this.subrace + "', " +
    					this.active + ", '" + this.bio + "' )":
    						"UPDATE Characters SET name='" + this.name + "', age=" + this.age + ", race='" + this.race + 
    						"', subrace='" + this.subrace + "', bio='" + this.bio + "', active=" + this.active + 
    						"WHERE uuid='" + this.uuid.toString() + "' AND cardName='" + this.cardName + "'";
    			
    			conn.createStatement().execute(sql);
    		} catch (SQLException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    	}
    }
    
    /**
	 * Checks if this character is active.
	 * @return the active state.
	 */
    public boolean isActive() {
    	return this.active;
    }

	public void printCard(int page) {
		// TODO Auto-generated method stub
		
	}
    
    public static CharacterBuilder builder(Connection conn, UUID player, String cardName) {
    	return new CharacterBuilder(conn, player, cardName);
    }
    
    public static class CharacterBuilder {
    	private UUID uuid;
    	private String name;
    	private String cardName;
    	private int age;
    	private String race;
    	private String subrace;
    	private String bio;
    	private boolean active;
    	private Connection conn;
    	
    	public CharacterBuilder(Connection conn, UUID uuid, String cardName) {
    		this.uuid = uuid;
    		this.cardName = cardName;
    		this.name = null;
    		this.age = -1;
    		this.race = null;
    		this.subrace = null;
    		this.bio = null;
    		this.active = false;
    		
    	}
    	
    	public CharacterBuilder name(String name) {
    		this.name = name;
    		return this;
    	}
    	
    	public CharacterBuilder age(int age) {
    		this.age = age;
    		return this;
    	}
    	
    	public CharacterBuilder race(String race) {
    		this.race = race;
    		return this;
    	}
    	
    	public CharacterBuilder subrace(String subrace) {
    		this.subrace = subrace;
    		return this;
    	}
    	
    	public CharacterBuilder bio(String bio) {
    		this.bio = bio;
    		return this;
    	}
    	
    	public CharacterBuilder active(boolean active) {
    		this.active=active;
    		return this;
    	}
    	
    	public Character buildAndSave() {
    		return new Character(this, 1, this.conn);
    	}
    	
    	public Character build() {
    		return new Character(this, 0, null);
    	}
    	
    	public Character buildAndUpdate() {
    		return new Character(this, 2, this.conn);
    	}
    }

}
