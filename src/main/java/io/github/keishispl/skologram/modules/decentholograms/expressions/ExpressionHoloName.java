package io.github.keishispl.skologram.modules.decentholograms.expressions;

import ch.njol.skript.doc.*;
import ch.njol.skript.expressions.base.PropertyExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@Name("DecentHolograms - Name")
@Description("Gets a decenthologram name.")
@Examples({
        "send hologram name of {_holo}"
})
@Since("1.0")
@RequiredPlugins("DecentHolograms")

public class ExpressionHoloName extends PropertyExpression<Hologram, String> {

    static {
        register(ExpressionHoloName.class, String.class, "hologram name", "holograms");
    }

    @Override
    protected String @NotNull [] get(@NotNull Event event, Hologram[] source) {
        List<String> names = new ArrayList<>();
        for (Hologram holo : source){
            names.add(holo.getName());
        }
        return names.toArray(String[]::new);
    }

    @Override
    public @NotNull Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public @NotNull String toString(@Nullable Event event, boolean debug) {
        return "name of hologram";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, @NotNull Kleenean isDelayed, SkriptParser.@NotNull ParseResult parseResult) {
        setExpr((Expression<? extends Hologram>) exprs[0]);
        return true;
    }
}
