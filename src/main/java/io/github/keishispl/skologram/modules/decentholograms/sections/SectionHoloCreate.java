package io.github.keishispl.skologram.modules.decentholograms.sections;

import ch.njol.skript.Skript;
import ch.njol.skript.config.SectionNode;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.Section;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.TriggerItem;
import ch.njol.util.Kleenean;
import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.DecentHologramsAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.skriptlang.skript.lang.entry.EntryContainer;
import org.skriptlang.skript.lang.entry.EntryValidator;
import org.skriptlang.skript.lang.entry.util.ExpressionEntryData;

import java.util.List;

@Name("DecentHolograms - Create")
@Examples({
        "create a new hologram:",
        "\tname: \"test\"",
        "\tlocation: player's location",
        "\tlines: \"&fthis is cool text\", \"&7idk\""})
@Description("Creates a decenthologram")
@Since("1.0")
@RequiredPlugins("DecentHolograms")

public class SectionHoloCreate extends Section {

    private static final EntryValidator.EntryValidatorBuilder ENTRY_VALIDATOR = EntryValidator.builder();

    static {
        if (Bukkit.getServer().getPluginManager().isPluginEnabled("DecentHolograms")){
            Skript.registerSection(SectionHoloCreate.class,
                    "(create|make) [a] [new] [:persistent] hologram[s]"
            );
            ENTRY_VALIDATOR.addEntryData(new ExpressionEntryData<>("name", null, false, String.class));
            ENTRY_VALIDATOR.addEntryData(new ExpressionEntryData<>("location", null, false, Location.class));
            ENTRY_VALIDATOR.addEntryData(new ExpressionEntryData<>("lines", null, false, String.class));
        }
    }

    private Expression<String> name;
    private Expression<Location> location;
    private Expression<String> lines;
    private boolean persistent;

    @Override
    public boolean init(@NotNull Expression<?> @NotNull [] exprs,
                        int matchedPattern,
                        @NotNull Kleenean isDelayed,
                        SkriptParser.@NotNull ParseResult parseResult,
                        @NotNull SectionNode sectionNode,
                        @NotNull List<TriggerItem> triggerItems) {
        EntryContainer container = ENTRY_VALIDATOR.build().validate(sectionNode);
        if (container == null) return false;
        persistent = parseResult.hasTag("persistent");
        name = (Expression<String>) container.getOptional("name", false);
        location = (Expression<Location>) container.getOptional("location", false);
        lines = (Expression<String>) container.getOptional("lines", false);
        return name != null && location != null && lines != null;
    }

    @Override
    protected @Nullable TriggerItem walk(@NotNull Event event) {
        String name = this.name.getSingle(event);
        List<String> lines = List.of(this.lines.getArray(event));
        Location loc = this.location.getSingle(event);
        if (name != null && loc != null) {
            Hologram hologram = DHAPI.createHologram(name, loc, persistent, lines);
            DecentHologramsAPI.get().getHologramManager().registerHologram(hologram);
        }
        return super.walk(event, false);
    }

    @NotNull
    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "create a new hologram";
    }
}
