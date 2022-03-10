package com.mp.showandtell.business.reader;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mp.showandtell.business.FileRegister;
import com.mp.showandtell.business.ReaderInterface;
import com.mp.showandtell.domain.CreditInput;
import com.mp.showandtell.domain.CreditOutput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component
public class CSVFileReader implements ReaderInterface {

    private static final String DILIM_CSV = ",";
    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        FileRegister.register("csv", new CSVFileReader());
    }

    private Function<String, CreditInput> mapToItem = (line) -> {

        String[] p = line.split(DILIM_CSV);// a CSV has comma separated lines

        CreditInput input = new CreditInput();

        input.setName(p[0] + "," + p[1]);//<-- this is the first column in the csv file
        input.setAddress(p[2]);
        input.setPostCode(p[3]);
        if (p[4] != null && p[4].trim().length() > 0) {
            input.setPhoneNumber(p[4]);
        }
        if (p[5] != null && p[5].trim().length() > 0) {
            input.setCreditLimit(p[5]);
        }
        if (p[6] != null && p[6].trim().length() > 0) {
            input.setBirthDate(p[6]);
        }
        //more initialization goes here

        return input;
    };

    @Override
    public String readFile(Path tempFile) throws JsonProcessingException {

        return processCSVInputFile(tempFile.toString());
    }

    @Override
    public void writeFile() {
    }

    private String processCSVInputFile(String inputFilePath) throws JsonProcessingException {

        List<CreditInput> inputList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(inputFilePath.toString(), Charset.forName("UTF-8")))){
           /* File inputF = new File(inputFilePath);
            InputStream inputFS = new FileInputStream(inputF);
            BufferedReader br = new BufferedReader(new InputStreamReader(inputFS));*/

            // skip the header of the csv
            inputList = br.lines().skip(1).map(mapToItem).collect(Collectors.toList());
//            br.close();
        } catch (IOException e) {
            log.error("File handling exception{}", e);
        }

        CreditOutput out = new CreditOutput();
        out.setOutput(inputList);
//        System.out.println(MAPPER.writeValueAsString(inputList));
        return MAPPER.writeValueAsString(out);
    }
}
