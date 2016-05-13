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
package loa.orp.gui

import net.minecraft.client.gui.GuiScreen
import net.minecraft.util.ResourceLocation

class CharacterCreationScreen extends GuiScreen {

	private val guiWidth = 175
	private val guiHeight = 165

	private val guiBackground = new ResourceLocation("orpcore", "textures/gui/charcreate.png")

	override def drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) = {
		val guiX = (width - guiWidth) / 2
		val guiY = (height - guiHeight) / 2

		drawDefaultBackground
		mc.renderEngine.bindTexture(guiBackground)
		this.drawTexturedModalRect(guiX, guiY, 0, 0, guiWidth, guiHeight)

		//fontRendererObj.drawString("Screen Width: " + width + ", Screen Height: " + height, 256, 256, 0xFF0000)

		super.drawScreen(mouseX, mouseY, partialTicks)

	}

}