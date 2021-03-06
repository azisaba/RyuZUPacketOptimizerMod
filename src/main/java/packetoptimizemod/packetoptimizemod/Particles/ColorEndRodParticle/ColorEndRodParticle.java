package packetoptimizemod.packetoptimizemod.Particles.ColorEndRodParticle;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.SimpleAnimatedParticle;
import net.minecraft.client.particle.SpriteTexturedParticle;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.MathHelper;

import java.awt.*;

public class ColorEndRodParticle extends SimpleAnimatedParticle {
    private final IAnimatedSprite spriteWithAge;

    public ColorEndRodParticle(
            ClientWorld world, double x, double y, double z,
            double motionX, double motionY, double motionZ,
            ColorEndRodParticleData particleData,
            IAnimatedSprite spriteWithAge
    ) {
        super(world, x, y, z, spriteWithAge, -5.0E-4F);
        this.spriteWithAge = spriteWithAge;
        this.motionX = motionX;
        this.motionY = motionY;
        this.motionZ = motionZ;
        float f = (float) Math.random() * 0.4F + 0.6F;
        this.particleRed = ((float) (Math.random() * (double) 0.2F) + 0.8F) * particleData.getRed() * f;
        this.particleGreen = ((float) (Math.random() * (double) 0.2F) + 0.8F) * particleData.getGreen() * f;
        this.particleBlue = ((float) (Math.random() * (double) 0.2F) + 0.8F) * particleData.getBlue() * f;
        this.maxAge = 60 + this.rand.nextInt(12);
        this.particleScale *= 0.75F;

        Color color = new Color(Math.round(particleRed * 255) / 2,Math.round(particleGreen * 255) / 2,Math.round(particleBlue * 255) / 2);
        String hex = Integer.toHexString(color.getRGB() & 0xffffff);
        if (hex.length() < 6) {
            hex = "0" + hex;
        }

        this.setColorFade(Integer.parseInt(hex, 16));
        this.selectSpriteWithAge(spriteWithAge);
    }

    @Override
    public void move(double x, double y, double z) {
        this.setBoundingBox(this.getBoundingBox().offset(x, y, z));
        this.resetPositionToBB();
    }
}
