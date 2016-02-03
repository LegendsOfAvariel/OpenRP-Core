/**
 * OpenRP Core
 * Character Card and Chat mod
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
package pnb.orp

import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.Mod.EventHandler
import net.minecraftforge.fml.common.SidedProxy
import net.minecraftforge.fml.common.event.
  {FMLInitializationEvent, FMLPostInitializationEvent, 
  FMLPreInitializationEvent, FMLServerStoppingEvent}

import pnb.orp.proxy.CommonProxy

@Mod(
	modid = "orpcore",
	name = "OpenRP Core",
	version = "0.1.0a",
	modLanguage = "scala"
)
object Core {
  
  @SidedProxy (
		clientSide = "pnb.orp.proxy.ClientProxy",
		serverSide = "pnb.orp.proxy.ServerProxy"
	)
	var proxy: CommonProxy = null
	
	/**
	 * Pre-initialization Event
	 * Before we initialize the rest of the plugin, make sure the dependency
	 * injector is set up.
	 * @param event
	 */
	@EventHandler
  def preInit(e: FMLPreInitializationEvent) = {
		proxy.preInit(e)
	}

  /**
	 * Initialization Event
	 * Set up our Event Handlers
	 * @param event
	 */
	@EventHandler
  def init(e: FMLInitializationEvent) = {
		proxy.init(e)
  }

  @EventHandler
  def postInit(e: FMLPostInitializationEvent) = {
  }
  
  @EventHandler
	def serverStopping(e: FMLServerStoppingEvent) = {
		proxy.serverStopping(e)
	}
}