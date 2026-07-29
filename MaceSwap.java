package example.addon.modules;

import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.modules.Category;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.player.FindItemResult;
import meteordevelopment.meteorclient.utils.player.InvUtils;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.item.Items;
import net.minecraft.item.ArmorItem;
import net.minecraft.entity.Entity;
import net.minecraft.util.hit.EntityHitResult;

public class MaceSwap extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Double> swapHeight = sgGeneral.add(new DoubleSetting.Builder()
        .name("swap-distance")
        .description("Расстояние до врага (в блоках), при котором элитра сменится на нагрудник.")
        .defaultValue(3.5)
        .min(1.5)
        .sliderMax(8.0)
        .build()
    );

    public MaceSwap(Category category) {
        super(category, "mace-swap", "Автоматически меняет элитру на нагрудник при подлете к врагу для удара булавой.");
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        if (mc.player == null || mc.world == null) return;
        if (!mc.player.isFallFlying()) return;

        if (mc.crosshairTarget instanceof EntityHitResult entityHit) {
            Entity target = entityHit.getEntity();

            if (target != null && mc.player.getY() - target.getY() <= swapHeight.get()) {
                // Ищем любой нагрудник в инвентаре
                FindItemResult chestplate = InvUtils.find(itemStack -> 
                    itemStack.getItem() instanceof ArmorItem && 
                    ((ArmorItem) itemStack.getItem()).getType() == ArmorItem.Type.CHESTPLATE
                );

                if (chestplate.found()) {
                    // Перемещаем нагрудник в слот брони (2)
                    InvUtils.move().from(chestplate.slot()).toArmor(2);
                    info("Элитра успешно заменена на нагрудник! Бей!");
                    toggle(); 
                }
            }
        }
    }
}
