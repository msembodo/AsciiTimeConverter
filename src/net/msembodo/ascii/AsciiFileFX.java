package net.msembodo.ascii;

import com.opencsv.CSVReader;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * ASCII file object for JavaFX TableView.
 */
public class AsciiFileFX {
    private List<String[]> ascii;
    private String[] nameHeader;
    private String[] unitHeader;

    public AsciiFileFX(String fileName) {
        try {
            String fileExt;
            String[] tokens = fileName.split("[.]");
            fileExt = tokens[tokens.length - 1];

            if (fileExt.equals("asc") || fileExt.equals("ASC")) {
                CSVReader reader = new CSVReader(new FileReader(fileName), '\t');
                ascii = reader.readAll();
            }
            else {
                CSVReader reader = new CSVReader(new FileReader(fileName));
                ascii = reader.readAll();
            }
        }
        catch (FileNotFoundException e) {}
        catch (IOException e) {}
    }

    public void removeHeader() {
        nameHeader = ascii.get(0);
        unitHeader = ascii.get(1);
        ascii.remove(0);
        ascii.remove(0);
    }

    public StringBuilder convertDateTime(String dateTimeFormat) {
        DateTimeFormatter dtf = DateTimeFormat.forPattern(dateTimeFormat);

        String[] time = new String[ascii.size()];
        long[] epoch = new long[ascii.size()];
        int i = 0;

        for (String[] row : ascii) {
            row[1] = row[0].concat(" ").concat(row[1]);
            time[i] = row[1];
            i++;
        }

        double origin = (new DateTime(1970, 1, 1, 0, 0, 0).getMillis()) / 1000;

        for (int t = 0; t < time.length; t++) {
            DateTime date = dtf.parseDateTime(time[t]);
            epoch[t] = (date.getMillis() / 1000) - (long) origin;
        }

        String newNameHeader = "DateTime";
        for (int n = 2; n < nameHeader.length; n++)
            newNameHeader = newNameHeader.concat("\t").concat(nameHeader[n]);

        String newUnitHeader = "s";
        for (int s = 2; s < unitHeader.length; s++)
            newUnitHeader = newUnitHeader.concat("\t").concat(unitHeader[s]);

        StringBuilder sb = new StringBuilder();

        sb.append(newNameHeader);
        sb.append("\n");
        sb.append(newUnitHeader);
        sb.append("\n");

        String line;
        i = 0;
        for (String[] row : ascii) {
            line = Long.toString(epoch[i]);
            for (int r = 2; r < row.length; r++)
                line = line.concat("\t").concat(row[r]);

            i++;

            sb.append(line);
            sb.append("\n");
        }

        return sb;
    }
}
