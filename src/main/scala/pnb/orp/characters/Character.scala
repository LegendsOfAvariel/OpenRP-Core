package pnb.orp.characters

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

class Character(protected val conn: Connection,
    protected val uuid: UUID,
    protected val cardName: String,
    protected val name: String = "Jane Doe",
    protected val age: Int = -1,
    protected val race: String = "Human",
    protected val subrace: String = "N/A",
    protected val bio: String = "",
    protected val active: Boolean = false) {
  
  //TODO Move this to the proxy
  //Proxy decides whether it sends saves to the DB or sends to the server for saving.
  def save = {
    
    val sql = "MERGE INTO Characters (uuid, cardName, name, age, race, subrace, bio, active)" +
      " KEY (uuid, cardName) VALUES ('" + uuid + "', '" + cardName + "', '" + name + "', " + age +
      ", '" + race + "', '" + subrace + "', '" + bio + "', " + active + " )"
    
    try {
      conn.createStatement.execute(sql)
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
  
  /**
	 * Checks if this character is active.
	 * @return the active state.
	 */
  def isActive:Boolean = {
    active
  }
  
  def printCard(page: Int) = {
		// TODO Auto-generated method stub
	}
  
}