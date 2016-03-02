package loa.util

import net.minecraft.client.gui.FontRenderer
import net.minecraft.client.settings.GameSettings
import net.minecraft.util.ResourceLocation
import net.minecraft.client.renderer.texture.TextureManager
import net.minecraftforge.fml.relauncher.{Side, SideOnly}

object LoAFontRenderer {
  val ttfEnabled = true
}

@SideOnly(Side.CLIENT)
class LoAFontRenderer (gs: GameSettings, rl: ResourceLocation, tm: TextureManager, unicode: Boolean) extends FontRenderer (gs, rl, tm, unicode) {
  //val stringCache: StringCache
  var dropShadowEnabled: Boolean = true
  var enabled: Boolean = true
  

}