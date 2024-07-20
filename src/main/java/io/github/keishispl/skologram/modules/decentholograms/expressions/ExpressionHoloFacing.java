package io.github.keishispl.skologram.modules.decentholograms.expressions;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.*;
import ch.njol.skript.expressions.base.PropertyExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@Name("DecentHolograms - Facing")
@Description("Gets/Sets a decenthologram facing.")
@Examples({
        "send hologram facing of {_holo}"
})
@Since("1.0")
@RequiredPlugins("DecentHolograms")

public class ExpressionHoloFacing extends PropertyExpression<Hologram, Number> {

    static {
        register(ExpressionHoloFacing.class, Number.class, "hologram facing", "holograms");
    }

    @Override
    protected Number @NotNull [] get(@NotNull Event event, Hologram[] source) {
        List<Number> names = new ArrayList<>();
        for (Hologram holo : source){
            names.add(holo.getFacing());
        }
        return names.toArray(Number[]::new);
    }

    @Override
    public @NotNull Class<? extends Number> getReturnType() {
        return Number.class;
    }

    @Override
    public @NotNull String toString(@Nullable Event event, boolean debug) {
        return "facing of hologram";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, @NotNull Kleenean isDelayed, SkriptParser.@NotNull ParseResult parseResult) {
        setExpr((Expression<? extends Hologram>) exprs[0]);
        return true;
    }

    @Override
    public @Nullable Class<?> @NotNull [] acceptChange(Changer.@NotNull ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET){
            return CollectionUtils.array(Number.class);
        }
        return null;
    }

    @Override
    public void change(@NotNull Event event, @Nullable Object @NotNull [] delta, Changer.@NotNull ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET){
            for (Hologram holo : getExpr().getArray(event)){
                holo.setFacing((Float) delta[0]);
            }
        }
    }
}
