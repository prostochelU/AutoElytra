package example.addon;

import meteordevelopment.meteorclient.addons.MeteorAddon;
import meteordevelopment.meteorclient.systems.modules.Modules;
import meteordevelopment.meteorclient.systems.modules.Categories;
import example.addon.modules.MaceSwap;

public class Addon extends MeteorAddon {

    @Override
    public void onInitialize() {
        // Добавляем наш модуль в категорию COMBAT
        Modules.get().add(new MaceSwap(Categories.Combat));
    }

    @Override
    public void onRegisterCategories() {
    }

    @Override
    public String getPackage() {
        return "example.addon";
    }
}
