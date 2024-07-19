package io.github.keishispl.skologram.features.decentholograms.conditions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@Name("DecentHolograms - Exists")
@Description("Returns a boolean of its existence")
@Examples({
        "if decenthologram named \"holo\" exists:"
})
@Since("1.0")
@RequiredPlugins("DecentHolograms")

public class ConditionHoloExist extends Condition {

    static{
        if (Bukkit.getServer().getPluginManager().isPluginEnabled("DecentHolograms")){
            Skript.registerCondition(ConditionHoloExist.class,
                    "[decenthologram] [named|with name] %string% [(does)] exist[s]",
                    "[decenthologram] %string% [named|with name] does(n't| not) exist"
            );
        }
    }

    private Expression<String> name;
    private boolean is;

    @Override
    public boolean check(@NotNull Event event) {
        String name = this.name.getSingle(event);
        if (name != null) {
            List<String> names = new ArrayList<>();
            Hologram.getCachedHolograms().forEach(hologram -> names.add(hologram.getName()));
            if (is){
                return names.contains(name);
            }
            return !names.contains(name);
        }
        return false;
    }

    @Override
    public @NotNull String toString(@Nullable Event event, boolean debug) {
        return "decenthologram exists";
    }

    @Override
    public boolean init(Expression<?> @NotNull [] exprs, int matchedPattern, @NotNull Kleenean isDelayed, SkriptParser.@NotNull ParseResult parseResult) {
        name = (Expression<String>) exprs[0];
        is = matchedPattern == 0;
        return true;
    }
}
