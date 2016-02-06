package pnb.orp.util

import net.minecraft.util.EnumChatFormatting

class ChatStyle(message: List[EnumChatFormatting], 
    quote: List[EnumChatFormatting] = null, 
    protected val separator: String = ": " , 
    name: List[EnumChatFormatting] = null) {
  
  protected val messageStyle: String = if (message != null) message.mkString else null
  protected val quoteStyle: String = if (quote != null) quote.mkString else null
  protected val nameStyle: String = if (name != null) name.mkString else null
  
  def apply(name: String, message: String):String = {
    var output = ""
    
    //Style the name and separator
    if ( nameStyle != null ) {
      //Style the name and separator with the name style
      output += nameStyle + name + separator + EnumChatFormatting.RESET.toString + messageStyle
    }
    else {
      //Style the name and separator with the message style
      output += messageStyle + name + separator
    }
    
    //Style the message proper
    if (quoteStyle != null ) {
      var inQuote = false
      message.foreach { x => 
        if (x == '"' || x == '\'' ) {
          if (inQuote)
            output += x.toString + EnumChatFormatting.RESET.toString + messageStyle
          else 
            output += EnumChatFormatting.RESET.toString + quoteStyle + x.toString
        }
        else
          output += x.toString
      } 
    }
    else
      output += message
    
    output
  }
}