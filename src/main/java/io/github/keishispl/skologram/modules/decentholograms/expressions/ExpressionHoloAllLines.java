package io.github.keishispl.skologram.modules.decentholograms.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import eu.decentsoftware.holograms.api.holograms.HologramPage;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@Name("DecentHolograms - All Lines")
@Description("Gets all the lines of a decenthologram.")
@Examples({
        "set {_lines::*} to all lines of {_holo}"
})
@Since("1.0")
@RequiredPlugins("DecentHolograms")

public class ExpressionHoloAllLines extends SimpleExpression<String> {

    static {
        Skript.registerExpression(ExpressionHoloAllLines.class, String.class, ExpressionType.COMBINED,
                "[all [of the]] lines of %hologrampages%"
        );
    }

    private Expression<HologramPage> page;

    @Override
    protected @Nullable String @NotNull [] get(@NotNull Event event) {
        List<String> pages = new ArrayList<>();
        for (HologramPage holo : page.getArray(event)) {
            holo.getLines().forEach(hologramLine -> pages.add(hologramLine.getContent()));
        }
        return pages.toArray(String[]::new);
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public @NotNull Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public @NotNull String toString(@Nullable Event event, boolean debug) {
        return "all hologram lines";
    }

    @Override
    public boolean init(Expression<?> @NotNull [] exprs, int matchedPattern, @NotNull Kleenean isDelayed, SkriptParser.@NotNull ParseResult parseResult) {
        page = (Expression<HologramPage>) exprs[0];
        return true;
    }
}
