package net.msembodo.ascii;

import uk.co.flamingpenguin.jewel.cli.ArgumentValidationException;

import static uk.co.flamingpenguin.jewel.cli.CliFactory.parseArguments;

/**
 * Main class.
 */
public class Main {
    public static void main(String[] args) {
        String fmt = "dd-MM-yyyy HH:mm:ss";

        try {
            Format format = parseArguments(Format.class, args);

            AsciiFile ascii = new AsciiFile(format.getFile());

            if (format.isFormat())
                fmt = format.getFormat();

            ascii.removeHeader();
            ascii.convertDateTime(fmt);
        }
        catch (ArgumentValidationException e) {
            System.err.println(e.getMessage());
        }
    }
}
