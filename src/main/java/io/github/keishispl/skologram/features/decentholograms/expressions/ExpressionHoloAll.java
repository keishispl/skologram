package io.github.keishispl.skologram.features.decentholograms.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;

import static eu.decentsoftware.holograms.api.commands.TabCompleteHandler.PLUGIN;

@Name("DecentHolograms - All Holograms")
@Description("Gets all the decentholograms.")
@Examples({
        "send all decentholograms"
})
@Since("1.0")
@RequiredPlugins("DecentHolograms")

public class ExpressionHoloAll extends SimpleExpression<Hologram> {

    static {
        Skript.registerExpression(ExpressionHoloAll.class, Hologram.class, ExpressionType.COMBINED,
                "[all [of the]] decentholograms"
        );
    }

    @Override
    protected @Nullable Hologram @NotNull [] get(@NotNull Event event) {
        return PLUGIN.getHologramManager().getHolograms().toArray(Hologram[]::new);
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public @NotNull Class<? extends Hologram> getReturnType() {
        return Hologram.class;
    }

    @Override
    public @NotNull String toString(@Nullable Event event, boolean debug) {
        return "all decentholograms";
    }

    @Override
    public boolean init(Expression<?> @NotNull [] exprs, int matchedPattern, @NotNull Kleenean isDelayed, SkriptParser.@NotNull ParseResult parseResult) {
        return true;
    }
}
