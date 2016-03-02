/**
 * OpenRP Core
 * Character Sheet and Chat mod
 * @author Emily Marriott
 * 
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Legends of Avariel
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
package loa.orp.util

import net.minecraft.util.EnumChatFormatting

class ChatStyle(protected val message: List[EnumChatFormatting],
    protected val senderName: String,
    protected val quote: Option[List[EnumChatFormatting]] = None, 
    protected val separator: String = ": " , 
    protected val name: Option[List[EnumChatFormatting]] = None,
    protected val prefix: String = "",
    protected val suffix: String = "",
    protected val channelTag: String = ""
    ) {
  
  protected val messageStyle: String = message.mkString
  protected val quoteStyle: String = quote.getOrElse(List("")).mkString
  protected val nameStyle: String = name.getOrElse(List("")).mkString
  
  def apply(message: String):String = {
    var output = new StringBuilder(senderName.length + separator.length + message.length + channelTag.length + prefix.length + suffix.length + 20)
    
    //Remove prefix and suffix from the message if they are in there
    
    //Style the name and separator
    if ( nameStyle != "" ) {
      //Style the name and separator with the name style
      output.append(nameStyle + channelTag +  senderName + separator + EnumChatFormatting.RESET.toString + messageStyle + prefix)
    }
    else {
      //Style the name and separator with the message style
      output.append(messageStyle + channelTag + senderName + separator + prefix)
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