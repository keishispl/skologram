package io.github.keishispl.skologram.features.decentholograms.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import org.jetbrains.annotations.Nullable;

@Name("Decent Holograms - Name")
@Description("Gets a decenthologram by its name.")
@Examples({
        "set {_holo} to decenthologram named \"cat\""
})
@Since("1.0")
@RequiredPlugins("DecentHolograms")

@SuppressWarnings("NullableProblems")
public class ExpressionHoloFromName extends SimpleExpression<Hologram> {

    static{
        if (Bukkit.getServer().getPluginManager().isPluginEnabled("DecentHolograms")){
            Skript.registerExpression(ExpressionHoloFromName.class, Hologram.class, ExpressionType.SIMPLE,
                    "decenthologram (from name|named) %string%"
            );
        }
    }

    private Expression<String> name;

    @Override
    protected @Nullable Hologram[] get(@NotNull Event event) {
        String name = this.name.getSingle(event);
        if (name != null) {
            try {
                Hologram hologram = DHAPI.getHologram(name);
                return new Hologram[]{hologram};
            } catch (IllegalArgumentException ae){
                return new Hologram[]{null};
            }
        }
        return new Hologram[]{null};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public @NotNull Class<? extends Hologram> getReturnType() {
        return Hologram.class;
    }

    @Override
    public @NotNull String toString(@Nullable Event event, boolean debug) {
        return "decenthologram from name";
    }

    @Override
    public boolean init(Expression<?> @NotNull [] exprs, int matchedPattern, @NotNull Kleenean isDelayed, SkriptParser.@NotNull ParseResult parseResult) {
        name = (Expression<String>) exprs[0];
        return true;
    }
}
