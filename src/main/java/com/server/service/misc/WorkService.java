package com.server.service.misc;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

@Service
public class WorkService {
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
        PerlSub,
        PHPIni,
        OutOfScope,
        Other;
    }

    private enum Scope {
        Kinou("/http/kinou/*"),
        Vendor("/php/cakephp/app/Vendor/*"),
        Adodb("/data/module/adodb/*"),
        Calendar("/data/module/Calendar/*"),
        SOAP("/data/module/SOAP/*"),
        DB("/data/module/DB/*"),
        Net("/data/module/Net/*"),
        Mail("/data/module/Mail/*"),
        TempDir("/data/Template_TmpDir/*"),
        PHP5("/php/DNP/PHP5/*"),
        Tool("/_tool/*"),
        BasicDB("/basic_db/*"),
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

    public boolean fillCxSAST(Path path, Path resourcePath) throws IOException {
        Map<String, List<String>> resourceList = this.getProjectInfoFiles(resourcePath);
        Map<Integer, List<String>> workbook = this.processInput(path, resourceList);

        writeData(workbook, path);

        resourcePath.toFile().delete();
        return false;
    }

    public void writeData(Map<Integer, List<String>> data, Path path) throws IOException {
        FileInputStream fIn = new FileInputStream(path.toFile());
        
        Workbook workbook =  new XSSFWorkbook(fIn);
        Sheet sheet = this.getLastSheet(workbook);
        List<Integer> merged = new ArrayList<>();

        for(CellRangeAddress range : sheet.getMergedRegions()) {
            merged.add(range.getFirstRow());
            System.out.println(range.getFirstRow());
        }
        
        for (Integer key : data.keySet()) {
            // if(merged.contains(key)) {

            // }
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

    public Map<Integer, List<String>> processInput(Path path, Map<String, List<String>> resouceList) throws IOException {
        FileInputStream file = new FileInputStream(path.toFile());
        int i = 0;
        Map<Integer, List<String>> data = new HashMap<>();
        List<Integer> merged = new ArrayList<>();

        try(Workbook workbook = new XSSFWorkbook(file)) {
            Sheet sheet = this.getLastSheet(workbook);
            for(CellRangeAddress range : sheet.getMergedRegions()) {
                merged.add(range.getFirstRow());
            }

            for (Row row : sheet) {
                if(
                    !merged.contains(i) &&
                    row.getCell(DataColumn.File.index) != null &&
                    row.getCell(DataColumn.File.index).getCellType() == CellType.STRING &&
                    row.getCell(DataColumn.File.index).getStringCellValue().contains("/")
                ) {
                    Cell fileCell = row.getCell(DataColumn.File.index);
                    String filePath = fileCell.getStringCellValue();

                    Pair<Category, String> res = process(filePath);
                    switch (res.getFirst()) {
                        case Html:
                            data.put(i, Arrays.asList("対象外(Other)","検査対象がhtmlファイルのため、対象外"));
                            break;
                        case JavaScript:
                            data.put(i, Arrays.asList("対象外(Javascript)","検査対象がJavascriptファイルのため、対象外"));
                            break;
                        case PerlSub:
                            data.put(i, Arrays.asList("対象外(Other)", "検査対象がperlsubライブラリのため、対象外"));
                            break;
                        case OutOfScope:
                            data.put(i, Arrays.asList("対象外(Other)", "ファイルが「"+ res.getSecond() +"」にあるため、調査範囲外となる。"));
                            break;
                        default:
                            if(isResource(filePath, resouceList.get("out"))) {
                                data.put(i, Arrays.asList("対象外(Other)", "Ngoài đối tượng chuyển đổi."));
                            } else {
                                if(!isResource(filePath,  resouceList.get("in"))) {
                                    System.out.println(filePath);
                                }
                                 data.put(i, Arrays.asList("未確認", ""));
                            }

                            break;
                    }
                }
                i++;
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

    private Pair<Category, String> process(String filePath) {
        
        Scope s;

        if(filePath.endsWith("html")) {
            return Pair.of(Category.Html, "");
        } else if(filePath.endsWith("js")) {
            return Pair.of(Category.JavaScript, "");
        } else if(filePath.contains("perl/perlsub/")) {
            return Pair.of(Category.PerlSub, "");
        }
        
        else if((s = scopeOf(filePath))!= Scope.Undefined) {
            return Pair.of(Category.OutOfScope, s.path);
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

    private Map<String, List<String>> getProjectInfoFiles(Path path) throws IOException {
        FileInputStream file = new FileInputStream(path.toFile());
        Map<String, List<String>> data = new HashMap<>();

        List<String> in = new ArrayList<>();
        List<String> out = new ArrayList<>();

        try(Workbook workbook = new XSSFWorkbook(file)) {
            Sheet sheet = workbook.getSheet("調査シート (20220628再)");
            for (Row row : sheet) {
                if(row.getCell(2) == null) {
                    continue;
                }
                
                if(row.getCell(2).getCellType() == CellType.STRING && row.getCell(2).getStringCellValue().contains("/")) {
                    if(row.getCell(0).getStringCellValue().equals("〇")) {
                        out.add(row.getCell(2).getStringCellValue());
                    } else {
                        in.add(row.getCell(2).getStringCellValue());
                    }
                    
                }
            }

            data.put("out", out);
            data.put("in", in);
        }

        return data;
    }

    private boolean isResource(String fileName, List<String> list) {
        for (String file : list) {
            if(file.contains(fileName)) {
                return true;
            }
        }

        return false;
    }
}
