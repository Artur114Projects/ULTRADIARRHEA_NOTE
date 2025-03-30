package com.artur114.ultradiarrheanote.register;

import com.artur114.ultradiarrheanote.client.fx.particle.util.ParticleSprite;
import com.artur114.ultradiarrheanote.client.init.InitParticleSprite;
import com.artur114.ultradiarrheanote.common.init.InitItems;
import com.artur114.ultradiarrheanote.common.item.BaseItem;
import com.artur114.ultradiarrheanote.common.network.ClientPacketSyncDiarrheaNote;
import com.artur114.ultradiarrheanote.common.network.ClientPacketSyncTemporallyEffects;
import com.artur114.ultradiarrheanote.common.network.ServerPacketSyncDiarrheaNote;
import com.artur114.ultradiarrheanote.common.util.data.UDNConfigs;
import com.artur114.ultradiarrheanote.main.MainUDN;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IRecipeFactory;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber
public class RegisterHandler {

    @SubscribeEvent
    public static void onItemRegister(RegistryEvent.Register<Item> event) {
        for (BaseItem item : InitItems.ITEMS) {
            boolean register = true;

            if (item instanceof IIsNeedRegister) {
                register = ((IIsNeedRegister) item).isNeedRegister();
            }

            if (register) {
                event.getRegistry().register(item);
            }
        }
    }

    @SubscribeEvent
    public static void onModelRegister(ModelRegistryEvent event) {
        for(Item item : InitItems.ITEMS) {
            if(item instanceof IHasModel) {
                boolean register = true;

                if (item instanceof IIsNeedRegister) {
                    register = ((IIsNeedRegister) item).isNeedRegister();
                }

                if (register) {
                    ((IHasModel) item).registerModels();
                }
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
        MainUDN.NETWORK.registerMessage(new ClientPacketSyncTemporallyEffects.HandlerSTE(), ClientPacketSyncTemporallyEffects.class, id++, Side.CLIENT);
        MainUDN.NETWORK.registerMessage(new ServerPacketSyncDiarrheaNote.HandlerSDN(), ServerPacketSyncDiarrheaNote.class, id++, Side.SERVER);
        MainUDN.NETWORK.registerMessage(new ClientPacketSyncDiarrheaNote.HandlerCDN(), ClientPacketSyncDiarrheaNote.class, id++, Side.CLIENT);
    }

    public static void registerRecipes() {
        if (!UDNConfigs.immutableRemoveDiarrhea()) {
            registerRecipe("ultra_diarrhea_note_recipe");
        }
    }

    private static void registerRecipe(String name) {
        CraftingHelper.register(new ResourceLocation(MainUDN.MODID, name), (IRecipeFactory) (context, json) -> CraftingHelper.getRecipe(json, context));
    }
}
