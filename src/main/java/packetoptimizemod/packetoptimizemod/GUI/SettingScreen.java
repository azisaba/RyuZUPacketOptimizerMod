package packetoptimizemod.packetoptimizemod.GUI;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.list.OptionsRowList;
import net.minecraft.client.settings.SliderPercentageOption;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.core.config.yaml.YamlConfiguration;
import org.yaml.snakeyaml.Yaml;
import packetoptimizemod.packetoptimizemod.PacketOptimizeMod;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = PacketOptimizeMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class SettingScreen extends Screen {
    private OptionsRowList optionsRowList;
    public static double drawingRate = 100;

    protected SettingScreen() {
        super(new StringTextComponent("gui." + PacketOptimizeMod.MOD_ID + ".settings"));
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        renderBackground(matrixStack);
        drawCenteredString(matrixStack, font, title.getString(), width / 2, 20, 0xFFFFFF);
        optionsRowList.render(matrixStack, mouseX, mouseY, partialTicks);

        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    @Override
    protected void init() {
        optionsRowList = new OptionsRowList(minecraft, width, height, 24, height - 32, 25);
        children.add(optionsRowList);
        optionsRowList.addOption(
                new SliderPercentageOption("gui." + PacketOptimizeMod.MOD_ID + ".drawing_rate",
                        0, 100, 1,
                        setting -> drawingRate,
                        (settings, value) -> drawingRate = value,
                        (settings, option) -> new StringTextComponent(I18n.format("gui." + PacketOptimizeMod.MOD_ID + ".drawing_rate") + drawingRate + "%"))
        );
        addButton(
                new Button(
                        (width - 200) / 2, this.height - 26, 200, 25,
                        new TranslationTextComponent("gui." + PacketOptimizeMod.MOD_ID + ".gone"),
                        button -> this.closeScreen())
        );
    }

    @Override
    public void onClose() {
        write();
    }

    public static void initialize() {
        ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.CONFIGGUIFACTORY,
                () -> (mc, screen) -> new SettingScreen());
    }

    public static void read() {
        File file = new File(Minecraft.getInstance().gameDir, "config/RyuZUPacketOptimizer.yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Yaml yaml = new Yaml();
        Map<String, Object> yamlMap;
        try {
            yamlMap = yaml.loadAs(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8), Map.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        if (yamlMap == null) yamlMap = new HashMap<>();
        drawingRate = (double) yamlMap.getOrDefault("ParticleDrawingRate", 100.0);
    }

    public static void write() {
        File file = new File(Minecraft.getInstance().gameDir, "config/RyuZUPacketOptimizer.yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Yaml yaml = new Yaml();
        Map<String, Object> yamlMap;
        try {
            yamlMap = yaml.loadAs(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8), Map.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        if (yamlMap == null) yamlMap = new HashMap<>();
        yamlMap.put("ParticleDrawingRate", drawingRate);
        try {
            yaml.dump(yamlMap , new OutputStreamWriter(new FileOutputStream(file) , StandardCharsets.UTF_8));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
