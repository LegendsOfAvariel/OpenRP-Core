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
    var output = new StringBuilder(name.length + separator.length + message.length + 20)
    
    //Style the name and separator
    if ( nameStyle != null ) {
      //Style the name and separator with the name style
      output.append(nameStyle + name + separator + EnumChatFormatting.RESET.toString + messageStyle)
    }
    else {
      //Style the name and separator with the message style
      output.append(messageStyle + name + separator)
    }
    
    //Style the message proper
    if (quoteStyle != null ) {
      var inQuote = false
      message.foreach { x => 
        if (x == '"' || x == '\'' ) {
          if (inQuote)
            output.append(x.toString + EnumChatFormatting.RESET.toString + messageStyle)
          else 
            output.append(EnumChatFormatting.RESET.toString + quoteStyle + x.toString)
        }
        else
          output.append(x.toString)
      } 
    }
    else
      output.append(message)
    
    output.toString
  }
}