package com.artur114.ultradiarrheanote.client.init;


import com.artur114.ultradiarrheanote.client.fx.particle.util.ParticleAtlasSprite;
import com.artur114.ultradiarrheanote.client.fx.particle.util.ParticleSprite;
import com.artur114.ultradiarrheanote.client.util.EnumTextureLocation;

import java.util.ArrayList;
import java.util.List;

public class InitParticleSprite {

    public static final List<ParticleSprite> PARTICLES_SPRITES = new ArrayList<>();

    public static final ParticleAtlasSprite PARTICLE_DIARRHEA_CIRCLE = new ParticleAtlasSprite(EnumTextureLocation.PARTICLE_PATH, ParticleAtlasSprite.genNumberedNames("particle_diarrhea_circle", 1, 10));
}
