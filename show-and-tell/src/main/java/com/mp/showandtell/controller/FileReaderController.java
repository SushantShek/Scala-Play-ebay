package com.mp.showandtell.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mp.showandtell.domain.CreditInput;
import com.mp.showandtell.domain.User;
import com.mp.showandtell.exception.FileProcessingException;
import com.mp.showandtell.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;

@Slf4j
//@RestController
//@RequestMapping("/api")
@Controller
public class FileReaderController {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    JSONArray jsonArray;
    String resp = "";

    @Autowired
    FileService fileService;

    @Autowired
    CreditInput input;

    @PostMapping(path = "/upload")
    public String loadInventory(Model model, @RequestPart("file") MultipartFile file) throws FileNotFoundException {
        if (null == file.getOriginalFilename()) {
//            return new ResponseEntity<>("File not found", HttpStatus.BAD_REQUEST);
        }
        try {
            String response = fileService.loadFile(file);
//            model.addAttribute("creditObject", response);
            JSONObject jsonObject = new JSONObject(response);
//            jsonObject.put("message", MAPPER.writeValueAsString(response));

//            jsonArray = new JSONArray(response);
            JSONArray arr = (JSONArray) jsonObject.get("output");
            System.out.println(arr.length());
            JSONObject obj = (JSONObject) arr.get(1);
            String payload = jsonObject.toString();

            model.addAttribute("jsonArr", obj);
            resp = jsonObject.toString();
//            return new ResponseEntity(fileService.loadFile(file),HttpStatus.OK);
            return "hello";
        } catch (IOException | JSONException e) {
            log.error("Exception in loadInventory " + e);
            throw new FileProcessingException("Some exception while saving the data");
        }
    }

    @GetMapping({"/", "/hello"})
    public String hello(Model model,
                        @RequestParam(value = "name", required = false, defaultValue = "World") String name) throws JsonProcessingException {
//        model.addAttribute("name", name);
        System.out.println(MAPPER.writeValueAsString(input));
        model.addAttribute("jsonArr", MAPPER.writeValueAsString(input));

        return "hello";
    }

    @GetMapping("/index")
    public String showUserList(Model model) {
        String[] arr = {"1", "2", "3", "4"};
        model.addAttribute("jsonArr", jsonArray);
        return "index";
    }

    @GetMapping("/signup")
    public String showSignUpForm(User user) {
        return "add-user";
    }


//    private String jsonToHtml(Object obj) {
//        StringBuilder html = new StringBuilder();
//
//        try {
//            if (obj instanceof JSONObject) {
//                JSONObject jsonObject = (JSONObject) obj;
//                String[] keys = JSONObject.getNames(jsonObject);
//
//                html.append("<div class=\"json_object\">");
//
//                if (keys.length > 0) {
//                    for (String key : keys) {
//                        // print the key and open a DIV
//                        html.append("<div><span class=\"json_key\">")
//                                .append(key).append("</span> : ");
//
//                        Object val = jsonObject.get(key);
//                        // recursive call
//                        html.append(jsonToHtml(val));
//                        // close the div
//                        html.append("</div>");
//                    }
//                }
//
//                html.append("</div>");
//
//            } else if (obj instanceof JSONArray) {
//                JSONArray array = (JSONArray) obj;
//                for (int i = 0; i < array.length(); i++) {
//                    // recursive call
//                    html.append(jsonToHtml(array.get(i)));
//                }
//            } else {
//                // print the value
//                html.append(obj);
//            }
//        } catch (JSONException e) {
//            return e.getLocalizedMessage();
//        }
//
//        return html.toString();
//    }

}


