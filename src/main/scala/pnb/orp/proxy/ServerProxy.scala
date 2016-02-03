package pnb.orp.proxy

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.event.
  {FMLInitializationEvent, FMLPostInitializationEvent, 
  FMLPreInitializationEvent, FMLServerStoppingEvent}

import pnb.orp.characters.Character
import pnb.orp.handlers.ChatHandler

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
	override def loadCharacter(uuid: UUID, cardName: String ):Character = {
	  
		//Get the requested character. Get the active if not given a card name.
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
		  character = new Character (this, 
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
	override def loadCharacterAndMakeActive(uuid: UUID, cardName: String):Character = {
	  
		var character: Character = null
		
		//Try to load the character and make them active
    try {
			//this.dbConnection.setAutoCommit(true);
			this.dbConnection.createStatement.executeQuery("UPDATE Characters SET active=false WHERE UUID='" + uuid.toString + "' AND active=true")
			
			val result = this.dbConnection.createStatement.executeQuery("SELECT * FROM Characters WHERE UUID='" + uuid.toString + "' AND cardName='" + cardName + "'")
			
			result.first
			
			//Initialize our Character
			val character = new Character(this, 
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

	override def saveCharacter(c: Character) = {
	  
	  val sql = "MERGE INTO Characters (uuid, cardName, name, age, race, subrace, bio, active)" +
      " KEY (uuid, cardName) VALUES ('" + c.uuid + "', '" + c.cardName + "', '" + c.name + "', " + c.age +
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
}