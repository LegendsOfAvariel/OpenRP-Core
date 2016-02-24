package pnb.orp.util

import net.minecraft.util.EnumChatFormatting

class ChatStyle(protected val message: List[EnumChatFormatting],
    protected val senderName: String,
    protected val quote: Option[List[EnumChatFormatting]] = None, 
    protected val separator: String = ": " , 
    protected val name: Option[List[EnumChatFormatting]] = None,
    protected val prefix: String = "",
    protected val suffix: String = ""
    ) {
  
  protected val messageStyle: String = message.mkString
  protected val quoteStyle: String = quote.getOrElse(List("")).mkString
  protected val nameStyle: String = name.getOrElse(List("")).mkString
  
  def apply(message: String):String = {
    var output = new StringBuilder(senderName.length + separator.length + message.length + 20)
    
    //Remove prefix and suffix from the message if they are in there
    
    //Style the name and separator
    if ( nameStyle != "" ) {
      //Style the name and separator with the name style
      output.append(nameStyle + senderName + separator + EnumChatFormatting.RESET.toString + messageStyle + prefix)
    }
    else {
      //Style the name and separator with the message style
      output.append(messageStyle + senderName + separator + prefix)
    }
    
    //Style the message proper
    //If there is a quote style to apply
    if (quoteStyle != "" ) {
      var inQuote = false
      //Loop through the message
      message.foreach { char => 
        //If we've found a quote mark
        if (char == '"' ) {
          //If we're within a quote, end the quote formatting
          if (inQuote)
            output.append(char.toString + EnumChatFormatting.RESET.toString + messageStyle)
          //Else apply the quote formatting
          else 
            output.append(EnumChatFormatting.RESET.toString + quoteStyle + char.toString)
        }
        //Otherwise just append the message
        else
          output.append(char.toString)
      }
    }
    else
      output.append(message)
    
    output.toString + suffix
  }
}