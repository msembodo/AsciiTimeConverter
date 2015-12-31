package net.msembodo.ascii;

import com.opencsv.CSVReader;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * ASCII file object.
 */
public class AsciiFile {
    private List<String[]> ascii;
    private String[] nameHeader;
    private String[] unitHeader;

    public AsciiFile(String filename) {
        try {
            CSVReader reader = new CSVReader(new FileReader(filename), '\t');

            ascii = reader.readAll();
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeHeader() {
        nameHeader = ascii.get(0);
        unitHeader = ascii.get(1);
        ascii.remove(0);
        ascii.remove(0);
    }

    public void printContent() {
        for (String[] row : ascii) {
            for (int i = 0; i < row.length; i++)
                if (row[i] == null)
                    System.out.print("" + '\t');
                else
                    System.out.print(row[i] + '\t');
            System.out.println();
        }
    }

    public void convertDateTime(String dateTimeFormat) {
        SimpleDateFormat df = new SimpleDateFormat(dateTimeFormat);

        String[] time = new String[ascii.size()];
        long[] epoch = new long[ascii.size()];
        int i = 0;

        for (String[] row : ascii) {
            row[1] = row[0].concat(" ").concat(row[1]);
            time[i] = row[1];
            i++;
        }

        try {
            for (int t = 0; t < time.length; t++) {
                Date date = df.parse(time[t]);
                epoch[t] = (long) Math.floor(date.getTime() / 1000);
            }
        }
        catch (ParseException e) {
            e.printStackTrace();
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

        File outFile = new File("out.asc");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outFile))) {
            writer.append(sb);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }
}
