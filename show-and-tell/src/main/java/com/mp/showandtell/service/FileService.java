package com.mp.showandtell.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mp.showandtell.business.FileRegister;
import com.mp.showandtell.business.ReaderInterface;
import com.mp.showandtell.domain.CreditInput;
import com.mp.showandtell.exception.JsonParseOrProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.lang.reflect.Constructor;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FileService {

    public String loadFile(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        String fileExtn = fileName.split("\\.")[1];
        Path tempFile = Files.createTempFile(null, null);
        // write a line
        Files.write(tempFile, file.getBytes());
//        System.out.println(tempFile.toString());
//        BufferedReader reader = Files.newBufferedReader(tempFile, Charset.forName("UTF-8"));

//        prnParse(tempFile.toString());
//        System.out.println(reader.readLine());
        try {
                ReaderInterface fileReader = FileRegister.getClass(fileExtn);
                return fileReader.readFile(tempFile);
        } catch (IOException ioe) {
            throw new JsonParseOrProcessingException("File could not be parsed or mapped to object" + ioe);
        } finally {
            Files.delete(tempFile);
        }
    }

 /*   private void prnParse(String filename){
        System.out.println("FileName"+ filename);
        try (
                BufferedReader br = new BufferedReader(new FileReader(filename))) {
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
                System.out.println(sb.toString());
            }
        }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
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
    }*/


}
