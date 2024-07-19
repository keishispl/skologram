package io.github.keishispl.skologram.features.decentholograms;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;
import ch.njol.skript.util.EnumUtils;
import eu.decentsoftware.holograms.api.actions.ClickType;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import eu.decentsoftware.holograms.api.holograms.HologramPage;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;

public class Types {
    static{
        if (Classes.getClassInfoNoError("decenthologram") == null) {
            Classes.registerClass(new ClassInfo<>(Hologram.class, "decenthologram")
                    .user("decentholograms?")
                    .name("decenthologram")
                    .description("Represents a decenthologram.")
                    .since("1.0")
                    .parser(new Parser<>() {

                        @Override
                        public boolean canParse(@NotNull ParseContext context) {
                            return false;
                        }

                        @Override
                        public @NotNull String toVariableNameString(Hologram holo) {
                            return holo.getName().toLowerCase().replaceAll("_", " ");
                        }

                        @Override
                        public @NotNull String toString(Hologram holo, int flags) {
                            return toVariableNameString(holo);
                        }
                    })
            );
        }
        if (Classes.getClassInfoNoError("decenthologramclicktype") == null) {
            EnumUtils<ClickType> clicktypes = new EnumUtils<>(ClickType.class, "decenthologramclicktype");
            Classes.registerClass(new ClassInfo<>(ClickType.class, "decenthologramclicktype")
                    .user("decenthologram ?click ?types?")
                    .name("decenthologramclicktype")
                    .description("Represents a decentholograms click type.")
                    .since("1.0")
                    .parser(new Parser<>() {

                        @Override
                        @Nullable
                        public ClickType parse(@NotNull String input, @NotNull ParseContext context) {
                            return clicktypes.parse(input);
                        }

                        @Override
                        public boolean canParse(@NotNull ParseContext context) {
                            return true;
                        }

                        @Override
                        public @NotNull String toVariableNameString(ClickType holo) {
                            return holo.name().toLowerCase().replaceAll("_", " ");
                        }

                        @Override
                        public @NotNull String toString(ClickType holo, int flags) {
                            return toVariableNameString(holo);
                        }
                    })
            );
        }
        if (Classes.getClassInfoNoError("decenthologrampage") == null) {
            Classes.registerClass(new ClassInfo<>(HologramPage.class, "decenthologrampage")
                    .user("decenthologram ?pages?")
                    .name("DecentHolograms - Page")
                    .description("Represents a decentholograms page.")
                    .since("1.0")
                    .parser(new Parser<>() {

                        @Override
                        public boolean canParse(@NotNull ParseContext context) {
                            return false;
                        }

                        @Override
                        public @NotNull String toVariableNameString(HologramPage holo) {
                            return "page " + holo.getIndex() + " of " + holo.getParent().getName();
                        }

                        @Override
                        public @NotNull String toString(HologramPage holo, int flags) {
                            return toVariableNameString(holo);
                        }
                    })
            );
        }
    }
}
