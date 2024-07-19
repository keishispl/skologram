package io.github.keishispl.skologram.features.decentholograms.expressions;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.*;
import ch.njol.skript.expressions.base.PropertyExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import org.bukkit.Location;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@Name("DecentHolograms - Location")
@Description("Gets/Sets a decenthologram location.")
@Examples({
        "send decenthologram location of {_holo}"
})
@Since("1.0")
@RequiredPlugins("DecentHolograms")

public class ExpressionHoloLocation extends PropertyExpression<Hologram, Location> {

    static {
        register(ExpressionHoloLocation.class, Location.class, "decenthologram location", "decentholograms");
    }

    @Override
    protected Location @NotNull [] get(@NotNull Event event, Hologram[] source) {
        List<Location> names = new ArrayList<>();
        for (Hologram holo : source){
            names.add(holo.getLocation());
        }
        return names.toArray(Location[]::new);
    }

    @Override
    public @NotNull Class<? extends Location> getReturnType() {
        return Location.class;
    }

    @Override
    public @NotNull String toString(@Nullable Event event, boolean debug) {
        return "location of decenthologram";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, @NotNull Kleenean isDelayed, SkriptParser.@NotNull ParseResult parseResult) {
        setExpr((Expression<? extends Hologram>) exprs[0]);
        return true;
    }

    @Override
    public @Nullable Class<?> @NotNull [] acceptChange(Changer.@NotNull ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET){
            return CollectionUtils.array(Location.class);
        }
        return null;
    }

    @Override
    public void change(@NotNull Event event, @Nullable Object @NotNull [] delta, Changer.@NotNull ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET){
            for (Hologram holo : getExpr().getArray(event)){
                holo.setLocation((Location) delta[0]);
            }
        }
    }
}
