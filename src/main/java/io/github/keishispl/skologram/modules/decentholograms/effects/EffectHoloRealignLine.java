package io.github.keishispl.skologram.modules.decentholograms.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;

@Name("DecentHolograms - Realign Lines")
@Description("Realigns lines of a decenthologram.")
@Examples({
        "realign lines of hologram {_holo}"
})
@Since("1.0")
@RequiredPlugins("DecentHolograms")

public class EffectHoloRealignLine extends Effect {

    static{
        if (Bukkit.getServer().getPluginManager().isPluginEnabled("DecentHolograms")){
            Skript.registerEffect(EffectHoloRealignLine.class,
                    "[re]align lines of hologram %holograms%"
            );
        }
    }

    private Expression<Hologram> holo;

    @Override
    protected void execute(@NotNull Event event) {
        for (Hologram holo : this.holo.getArray(event)){
            holo.realignLines();
        }
    }

    @Override
    public @NotNull String toString(@Nullable Event event, boolean debug) {
        return "realign lines of hologram";
    }

    @Override
    public boolean init(Expression<?> @NotNull [] exprs, int matchedPattern, @NotNull Kleenean isDelayed, SkriptParser.@NotNull ParseResult parseResult) {
        holo = (Expression<Hologram>) exprs[0];
        return true;
    }
}
