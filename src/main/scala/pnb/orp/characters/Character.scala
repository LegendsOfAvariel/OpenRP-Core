package pnb.orp.characters

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import pnb.orp.proxy.CommonProxy

class Character(protected val proxy: CommonProxy,
    val uuid: UUID,
    val cardName: String,
    val name: String = "Jane Doe",
    val age: Int = -1,
    val race: String = "Human",
    val subrace: String = "N/A",
    val bio: String = "",
    val active: Boolean = false) {
  
  def save = {
    proxy.saveCharacter(this)
  }
  
  def printCard(page: Int) = {
		// TODO Auto-generated method stub
	}
  
}