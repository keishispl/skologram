package io.github.keishispl.skologram.features.decentholograms.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import org.jetbrains.annotations.Nullable;

@Name("DecentHolograms - Move")
@Description("Moves a decenthologram.")
@Examples({
        "move decenthologram \"move\" to player"
})
@Since("1.0")
@RequiredPlugins("DecentHolograms")

public class EffectHoloMove extends Effect {

    static{
        if (Bukkit.getServer().getPluginManager().isPluginEnabled("DecentHolograms")){
            Skript.registerEffect(EffectHoloMove.class,
                    "move [decenthologram] %decenthologram% to %location%"
            );
        }
    }

    private Expression<Hologram> hologram;
    private Expression<Location> location;

    @Override
    protected void execute(@NotNull Event event) {
        Location loc = location.getSingle(event);
        if (loc != null){
            for (Hologram holo : hologram.getArray(event)) {
                DHAPI.moveHologram(holo, loc);
            }
        }
    }

    @Override
    public @NotNull String toString(@Nullable Event event, boolean debug) {
        return "move decenthologram";
    }

    @Override
    public boolean init(Expression<?> @NotNull [] exprs, int matchedPattern, @NotNull Kleenean isDelayed, SkriptParser.@NotNull ParseResult parseResult) {
        hologram = (Expression<Hologram>) exprs[0];
        location = (Expression<Location>) exprs[1];
        return true;
    }
}
