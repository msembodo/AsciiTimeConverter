package net.msembodo.ascii;

import uk.co.flamingpenguin.jewel.cli.ArgumentValidationException;

import static uk.co.flamingpenguin.jewel.cli.CliFactory.parseArguments;

/**
 * Main class.
 */
public class Main {
    public static void main(String[] args) {
        try {
            Format format = parseArguments(Format.class, args);

            AsciiFile ascii = new AsciiFile(format.getFile());

            ascii.removeHeader();
            ascii.convertDateTime(format.getFormat());
        }
        catch (ArgumentValidationException e) {
            System.err.println(e.getMessage());
        }
    }
}
