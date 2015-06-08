//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package io.advantageous.qbit.example.recommendationengine;

public class Terminal {
    public Terminal() {
    }

    public static enum Escape {
        RESET("\u001b[0m"),
        BOLD_ON("\u001b[1m"),
        ITALICS_ON("\u001b[3m"),
        UNDERLINE_ON("\u001b[4m"),
        INVERSE_ON("\u001b[7m"),
        STRIKETHROUGH_ON("\u001b[9m"),
        BOLD_OFF("\u001b[22m"),
        ITALICS_OFF("\u001b[23m"),
        UNDERLINE_OFF("\u001b[24m"),
        INVERSE_OFF("\u001b[27m"),
        STRIKETHROUGH_OFF("\u001b[29m"),
        FG_BLACK("\u001b[30m"),
        FG_RED("\u001b[31m"),
        FG_GREEN("\u001b[32m"),
        FG_YELLOW("\u001b[33m"),
        FG_BLUE("\u001b[34m"),
        FG_MAGENTA("\u001b[35m"),
        FG_CYAN("\u001b[36m"),
        FG_WHITE("\u001b[37m"),
        FG_DEFAULT("\u001b[39m"),
        BG_BLACK("\u001b[40m"),
        BG_RED("\u001b[41m"),
        BG_GREEN("\u001b[42m"),
        BG_YELLOW("\u001b[43m"),
        BG_BLUE("\u001b[44m"),
        BG_MAGENTA("\u001b[45m"),
        BG_CYAN("\u001b[46m"),
        BG_WHITE("\u001b[47m"),
        BG_DEFAULT("\u001b[49m");

        private final String value;

        private Escape(String s) {
            this.value = s;
        }

        public String toString() {
            return this.value;
        }
    }
}
