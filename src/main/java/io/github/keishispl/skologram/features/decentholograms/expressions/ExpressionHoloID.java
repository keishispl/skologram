package io.github.keishispl.skologram.features.decentholograms.expressions;

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

@Name("DecentHolograms - ID")
@Description("Gets a decenthologram id.")
@Examples({
        "send decenthologram id of {_holo}"
})
@Since("1.0")
@RequiredPlugins("DecentHolograms")

public class ExpressionHoloID extends PropertyExpression<Hologram, String> {

    static {
        register(ExpressionHoloID.class, String.class, "[decentholo[gram]] id", "decentholograms");
    }

    @Override
    protected String @NotNull [] get(@NotNull Event event, Hologram[] source) {
        List<String> names = new ArrayList<>();
        for (Hologram holo : source){
            names.add(holo.getId());
        }
        return names.toArray(String[]::new);
    }

    @Override
    public @NotNull Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public @NotNull String toString(@Nullable Event event, boolean debug) {
        return "id of decenthologram";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, @NotNull Kleenean isDelayed, SkriptParser.@NotNull ParseResult parseResult) {
        setExpr((Expression<? extends Hologram>) exprs[0]);
        return true;
    }
}
