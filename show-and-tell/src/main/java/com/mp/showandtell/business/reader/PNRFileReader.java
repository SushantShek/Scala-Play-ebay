package com.mp.showandtell.business.reader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mp.showandtell.business.FileRegister;
import com.mp.showandtell.business.ReaderInterface;
import com.mp.showandtell.domain.CreditInput;
import com.mp.showandtell.domain.CreditOutput;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Component
public class PNRFileReader implements ReaderInterface {
    private static final String DILIM_PRN = " ";
    private static final String STRING_DELIM = ";";
    private static final Pattern PRN_SPLITTER = Pattern.compile(DILIM_PRN);
    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        FileRegister.register("prn", new PNRFileReader());
    }

    private Function<String, CreditInput> mapToCredit = (line) -> {

        String[] p = line.split(STRING_DELIM);// a CSV has comma separated lines
        CreditInput item = new CreditInput();

        item.setName(p[0]);//<-- this is the first column in the csv file
        item.setAddress(p[1]);
        item.setPostCode(p[2]);
        if (p[3] != null && p[3].trim().length() > 0) {
            item.setPhoneNumber(p[3]);
        }
        if (p[4] != null && p[4].trim().length() > 0) {
            item.setCreditLimit(p[4]);
        }
        if (p[5] != null && p[5].trim().length() > 0) {
            item.setBirthDate(p[5]);
        }
        //more initialization goes here

        return item;
    };

    public static String[] splitStringToChunks(String inputString, int... chunkSizes) {
        List<String> list = new ArrayList<>();
        int chunkStart = 0, chunkEnd = 0;
        for (int length : chunkSizes) {
            chunkStart = chunkEnd;
            chunkEnd = chunkStart + length;
            String dataChunk = inputString.substring(chunkStart, chunkEnd);
            list.add(dataChunk.trim());
        }
        return list.toArray(new String[0]);
    }

    @Override
    public String readFile(Path tempFile) throws IOException {
        List<CreditInput> creditInputs = processPNRInputFile(tempFile).stream().skip(1).map(mapToCredit).collect(Collectors.toList());
        CreditOutput out = new CreditOutput();
        out.setOutput(creditInputs);
//        System.out.println(MAPPER.writeValueAsString(out));
        return MAPPER.writeValueAsString(out);
    }

    @Override
    public void writeFile() {

    }

    public List<String> processPNRInputFile(Path path) {
        List<String> prnList = new ArrayList<>();
        String response = "{}";
        try (
                BufferedReader br = new BufferedReader(new FileReader(path.toString(),Charset.forName("UTF-8")))) {
            String line;
            StringBuilder sb;
            while ((line = br.readLine()) != null) {
                if (line.trim().equals("")) { continue; }
                sb = new StringBuilder();
                // Method called with supplied file data line and the widths of
                // each column as outlined within the file.
                String[] parts = splitStringToChunks(line, 16, 22, 9, 14, 13, 8);
                for (String str : parts) {
                    sb.append(sb.toString().equals("") ? str : "; " + str);
                }
                prnList.add(sb.toString());
            }
        }
      catch (IOException ex) {
//            System.out.println("Exception processPNRInputFile : "+ex);
            log.error("processPNRInputFile{}",ex);
        }
        return prnList;
    }

    public List<CreditInput> processPNRInputFile(InputStream inputLines) throws IOException {
//        List<String> inputLines = Files.readAllLines(new File(path).toPath());

        BufferedReader br = new BufferedReader(new InputStreamReader(inputLines));
        List<String[]> inputValuesInLines = br.lines().skip(1)
                .map(l -> PRN_SPLITTER.split(StringUtils.normalizeSpace(l)))
                .collect(Collectors.toList());

        /*List<String> outputList = inputValuesInLines.stream()
                .flatMap()
                .map(s -> s.replaceAll("\\s+",""))
                .collect(Collectors.toList());*/
        List<CreditInput> inputList = new ArrayList<>();

        List<String> strList = new ArrayList<>();
        //Pattern.compile(regex).matcher(this).replaceAll(replacement);
        for (String[] s : inputValuesInLines) {
            StringBuilder builder = new StringBuilder();
            int count = 0;
            for (String str : s) {
                str.strip();
//                str.replaceAll(",\\s+", "" );
                if (str != "") {
                    if (count == s.length - 1) {
                        String[] tmp = str.strip().split(" ");
                        builder.append(tmp[0] + ";");
                        builder.append(tmp[1]);
                    } else {
                        builder.append(str + ";");
                    }
                }
                count++;
            }
            strList.add(builder.toString());
            System.out.println(" builder : " + builder.toString());
            System.out.println(" ");
        }
        List<CreditInput> output = strList.stream().map(mapToCredit).collect(Collectors.toList());
        return output;
    }
}
