package io.github.keishispl.skologram.features.decentholograms.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import org.jetbrains.annotations.Nullable;

@Name("DecentHolograms - Delete")
@Description("Deletes a decenthologram.")
@Examples({
        "delete decenthologram named \"unused_holo\""
})
@Since("1.0")
@RequiredPlugins("DecentHolograms")

public class EffectHoloDelete extends Effect {
    static{
        if (Bukkit.getServer().getPluginManager().isPluginEnabled("DecentHolograms")){
            Skript.registerEffect(EffectHoloDelete.class,
                    "(delete|remove) [decenthologram] %decentholograms%"
            );
        }
    }

    private Expression<Hologram> hologram;

    @Override
    protected void execute(@NotNull Event event) {
        Hologram[] holo = hologram.getArray(event);
        for (Hologram h : holo) {
            h.delete();
        }
    }

    @Override
    public @NotNull String toString(@Nullable Event event, boolean debug) {
        return "delete decenthologram";
    }

    @Override
    public boolean init(Expression<?> @NotNull [] exprs, int matchedPattern, @NotNull Kleenean isDelayed, SkriptParser.@NotNull ParseResult parseResult) {
        hologram = (Expression<Hologram>) exprs[0];
        return true;
    }
}
