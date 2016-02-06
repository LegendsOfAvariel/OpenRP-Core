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
    //If there is a quote style to apply
    if (quoteStyle != null ) {
      var inQuote = false
      //Loop through the message
      message.foreach { x => 
        //If we've found a quote mark
        if (x == '"' ) {
          //If we're within a quote, end the quote formatting
          if (inQuote)
            output.append(x.toString + EnumChatFormatting.RESET.toString + messageStyle)
          //Else apply the quote formatting
          else 
            output.append(EnumChatFormatting.RESET.toString + quoteStyle + x.toString)
        }
        //Otherwise just append the message
        else
          output.append(x.toString)
      } 
    }
    else
      output.append(message)
    
    output.toString
  }
}