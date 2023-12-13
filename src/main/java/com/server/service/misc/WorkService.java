package com.server.service.misc;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

@Service
public class WorkService {
    private static final int START_ROW = 6;

    private enum DataColumn {
        Vulnerability(1),
        File(2),
        Type(4),
        Explanation(5),
        Creator(6),
        DateFill(7);

        private final int index;
        private DataColumn(int index) {
            this.index = index;
        }
    }

    private enum Category {
        Html,
        JavaScript,
        PLibrary,
        PHPIni,
        OutOfScope,
        Other;
    }

    private enum Scope {
        Kinou("http/kinou/*"),
        Vendor("php/cakephp/app/Vendor/*"),
        Adodb("/data/module/adodb/*"),
        Calendar("/data/module/Calendar/*"),
        SOAP("/data/module/SOAP/*"),
        DB("/data/module/DB/*"),
        Net("/data/module/Net/*"),
        Mail("/data/module/Mail/*"),
        TempDir("/data/Template_TmpDir/*"),
        PHP5("/php/DNP/PHP5/*"),
        Tool("/_tool/*"),
        BasicDB("basic_db/*"),
        CornetTool("/cornet_tools/*"),
        DebugTool("/http/dbg_tool/*"),
        CakeLib("/php/cakephp/lib/*"),
        CakeAppLib("/php/cakephp/app/Lib/*"),
        Undefined(null);

        private final String path;
        private Scope(String path) {
            this.path = path;
        }
        
        public static Stream<Scope> stream() {
            return Stream.of(Scope.values()).filter((item) -> item != Scope.Undefined); 
        }
    }

    public boolean fillCxSAST(Path path) throws IOException {
        Map<Integer, List<String>> workbook = this.processInput(path);

        writeData(workbook, path);


        return false;
    }

    public void writeData(Map<Integer, List<String>> data, Path path) throws IOException {
        FileInputStream fIn = new FileInputStream(path.toFile());
        Workbook workbook =  new XSSFWorkbook(fIn);
        Sheet sheet = this.getLastSheet(workbook);
        for (Integer key : data.keySet()) {
            Cell explainCell = sheet.getRow(key).getCell(DataColumn.Explanation.index);
            Cell typeCell = sheet.getRow(key).getCell(DataColumn.Type.index);

            typeCell.setCellValue(data.get(key).get(0));
            explainCell.setCellValue(data.get(key).get(1));

            sheet.getRow(key).getCell(DataColumn.Creator.index).setCellValue("hauhp");;
            sheet.getRow(key).getCell(DataColumn.DateFill.index).setCellValue(new SimpleDateFormat("yyyy/MM/dd").format(new Date()));;
        }
        fIn.close();

        FileOutputStream outputStream = new FileOutputStream(path.toFile());
        workbook.write(outputStream);
        workbook.close();

        outputStream.close();
    }

    public Map<Integer, List<String>> processInput(Path path) throws IOException {
        FileInputStream file = new FileInputStream(path.toFile());
        int i = 0;

        Map<Integer, List<String>> data = new HashMap<>();

        try(Workbook workbook = new XSSFWorkbook(file)) {
            Sheet sheet = this.getLastSheet(workbook);
            i = sheet.getFirstRowNum();
            for (Row row : sheet) {
                i++;

                if(i >= WorkService.START_ROW) {
                    Category category = process(row).getFirst();
                    
                    switch (category) {
                        case Html:
                            data.put(i, Arrays.asList("対象外(Other)","検査対象がhtmlファイルのため、対象外"));
                            break;
                        case JavaScript:
                            data.put(i, Arrays.asList("対象外(Javascript)","検査対象がJavascriptファイルのため、対象外"));
                            break;
                        case OutOfScope:
                            String outOfScopePath = process(row).getSecond();
                            data.put(i, Arrays.asList("対象外(Other)", "ファイルが「"+ outOfScopePath +"」にあるため、調査範囲外となる。"));
                            break;
                        default:
                            break;
                    }
                }
            }
        }
        
        return data;
    }

    private Sheet getLastSheet(Workbook workbook) {
        Iterator<Sheet> iterable = workbook.sheetIterator();

        Sheet last = workbook.getSheetAt(0);
        while (iterable.hasNext()) {
            last = iterable.next();
        }

        return last;
    }

    private Pair<Category, String> process(Row row) {
        Cell file = row.getCell(DataColumn.File.index);
        
        String filePath = file.getStringCellValue();
        Scope s;

        if(filePath.endsWith("html")) {
            return Pair.of(Category.Html, "");
        } else if(filePath.endsWith("js")) {
            return Pair.of(Category.JavaScript, "");
        } else if((s = scopeOf(filePath))!= Scope.Undefined) {
            return Pair.of(Category.OutOfScope, s.path);
        } else {

        }

        return Pair.of(Category.Other, "");
    }

    private Scope scopeOf(String path) {
        Optional<Scope> find = Scope.stream().filter((compare)-> {
            String comparePath = compare.path.substring(0, compare.path.length() - 1);
            return path.contains(comparePath);
        }).findFirst();

        return find.isPresent() ? find.get() : Scope.Undefined;
    }
}
