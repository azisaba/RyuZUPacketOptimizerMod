package packetoptimizemod.packetoptimizemod.Particles.ColorEndRodParticle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.SimpleAnimatedParticle;
import net.minecraft.client.particle.SpriteSet;

import java.awt.*;

public class ColorEndRodParticle extends SimpleAnimatedParticle {
    public ColorEndRodParticle(
            ClientLevel world, double x, double y, double z,
            double motionX, double motionY, double motionZ,
            ColorEndRodParticleData particleData,
            SpriteSet spriteWithAge
    ) {
        super(world, x, y, z, spriteWithAge, 0.0125F);
        float f = (float) Math.random() * 0.4F + 0.6F;
        this.setColor(
                ((float) (Math.random() * (double) 0.2F) + 0.8F) * particleData.getRed() * f,
                ((float) (Math.random() * (double) 0.2F) + 0.8F) * particleData.getGreen() * f,
                ((float) (Math.random() * (double) 0.2F) + 0.8F) * particleData.getBlue() * f
        );
        this.xd = motionX;
        this.yd = motionY;
        this.zd = motionZ;
        this.quadSize *= 0.75F;
        this.lifetime = 60 + this.random.nextInt(12);
        this.setFadeColor(15916745);
        Color color = new Color(Math.round(((float) (Math.random() * (double) 0.2F) + 0.8F) * particleData.getRed() * f * 255) / 2,
                                Math.round(((float) (Math.random() * (double) 0.2F) + 0.8F) * particleData.getGreen() * f * 255) / 2,
                                Math.round(((float) (Math.random() * (double) 0.2F) + 0.8F) * particleData.getBlue() * f * 255) / 2);
        String hex = Integer.toHexString(color.getRGB() & 0xffffff);
        if (hex.length() < 6) {
            hex = "0" + hex;
        }
        this.setFadeColor(Integer.parseInt(hex, 16));
        this.pickSprite(spriteWithAge);
    }

    @Override
    public void move(double x, double y, double z) {
        this.setBoundingBox(this.getBoundingBox().move(x, y, z));
        this.setLocationFromBoundingbox();
    }
}
