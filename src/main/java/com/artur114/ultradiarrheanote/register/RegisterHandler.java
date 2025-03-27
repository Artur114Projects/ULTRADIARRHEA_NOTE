package com.artur114.ultradiarrheanote.register;

import com.artur114.ultradiarrheanote.client.fx.particle.util.ParticleSprite;
import com.artur114.ultradiarrheanote.client.init.InitParticleSprite;
import com.artur114.ultradiarrheanote.common.init.InitItems;
import com.artur114.ultradiarrheanote.common.network.ServerPacketSyncDiarrheaNote;
import com.artur114.ultradiarrheanote.main.MainUDN;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber
public class RegisterHandler {

    @SubscribeEvent
    public static void onItemRegister(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(InitItems.ITEMS.toArray(new Item[0]));
    }

    @SubscribeEvent
    public static void onModelRegister(ModelRegistryEvent event) {
        for(Item item : InitItems.ITEMS) {
            if(item instanceof IHasModel) {
                ((IHasModel) item).registerModels();
            }
        }
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void registerParticlesTexture(TextureStitchEvent.Pre e){
        for (ParticleSprite sprite : InitParticleSprite.PARTICLES_SPRITES) {
            sprite.register(e);
        }
    }

    public static void registerPackets() {
        int id = 0;
        MainUDN.NETWORK.registerMessage(new ServerPacketSyncDiarrheaNote.HandlerSDN(), ServerPacketSyncDiarrheaNote.class, id++, Side.SERVER);
    }
}
