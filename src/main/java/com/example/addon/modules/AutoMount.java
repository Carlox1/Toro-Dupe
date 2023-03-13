package com.example.addon.modules;

import com.example.addon.Addon;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.IntSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.Hand;

public class AutoMount extends Module {
    public AutoMount() {
        super(Addon.CATEGORY, "AutoMount", "Toro Dupe by Colonizadores.");
    }

    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Integer> ticks = sgGeneral.add(new IntSetting.Builder()
        .name("ticks")
        .description("Ticks")
        .defaultValue(6)
        .range(0, 100)
        .sliderMax(100)
        .build()
    );

    private int timer = 0;

    @EventHandler
    private void onTick(TickEvent.Post event) {
        if (mc.player == null) return;
        mc.options.sneakKey.setPressed(false);
        if (timer >= ticks.get()) {
            mc.world.getEntities().forEach(entity -> {
                if (entity instanceof BoatEntity) {
                    if (!entity.hasPassengers()) {
                        mc.interactionManager.interactEntity(mc.player, entity, Hand.MAIN_HAND);
                    } else {
                        mc.options.sneakKey.setPressed(true);
                    }
                }
            });
            timer = 0;
        }
        timer++;
    }
}
