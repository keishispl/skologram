package io.github.keishispl.skologram.modules.decentholograms.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import eu.decentsoftware.holograms.api.holograms.HologramPage;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@Name("DecentHolograms - All Pages")
@Description("Gets all the pages of a decenthologram.")
@Examples({
        "set {_pages::*} to all pages of {_holo}"
})
@Since("1.0")
@RequiredPlugins("DecentHolograms")

public class ExpressionHoloAllPages extends SimpleExpression<HologramPage> {

    static {
        Skript.registerExpression(ExpressionHoloAllPages.class, HologramPage.class, ExpressionType.COMBINED,
                "[all [of the]] pages of %holograms%"
        );
    }

    private Expression<Hologram> hologram;

    @Override
    protected @Nullable HologramPage @NotNull [] get(@NotNull Event event) {
        List<HologramPage> pages = new ArrayList<>();
        for (Hologram holo : hologram.getArray(event)) {
            pages.addAll(holo.getPages());
        }
        return pages.toArray(HologramPage[]::new);
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public @NotNull Class<? extends HologramPage> getReturnType() {
        return HologramPage.class;
    }

    @Override
    public @NotNull String toString(@Nullable Event event, boolean debug) {
        return "all hologram pages";
    }

    @Override
    public boolean init(Expression<?> @NotNull [] exprs, int matchedPattern, @NotNull Kleenean isDelayed, SkriptParser.@NotNull ParseResult parseResult) {
        hologram = (Expression<Hologram>) exprs[0];
        return true;
    }
}
