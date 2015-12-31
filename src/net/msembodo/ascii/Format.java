package net.msembodo.ascii;

import uk.co.flamingpenguin.jewel.cli.Option;

/**
 * Format of date-time.
 */
public interface Format {
    @Option
    String getFile();

    @Option
    String getFormat();
    boolean isFormat();

    @Option(helpRequest = true)
    boolean getHelp();
}
