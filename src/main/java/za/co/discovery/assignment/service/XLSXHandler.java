package za.co.discovery.assignment.service;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import za.co.discovery.assignment.entity.Planet;
import za.co.discovery.assignment.entity.Route;
import za.co.discovery.assignment.entity.Traffic;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class XLSXHandler {
    private File file;

    public XLSXHandler() {
    }

    public XLSXHandler(File file) {
        this.file = file;
    }

    public List<Planet> readPlanets() {
        List<Planet> planets = new ArrayList<>();
        try {
            FileInputStream inputStream = new FileInputStream(this.file);

            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet firstSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = firstSheet.iterator();

            while (iterator.hasNext()) {
                Row nextRow = iterator.next();
                // skip header
                if (nextRow.getRowNum() == 0) {
                    continue;
                }
                Iterator<Cell> cellIterator = nextRow.cellIterator();
                Planet planet = new Planet();

                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    int columnIndex = cell.getColumnIndex();
                    switch (columnIndex + 1) {
                        case 1:
                            planet.setPlanetId((String) getCellValue(cell));
                            break;
                        case 2:
                            planet.setName((String) getCellValue(cell));
                            break;
                    }
                }
                planets.add(planet);
            }

            workbook.close();
            inputStream.close();
        } catch (IOException ex) {
            Logger.getLogger("discovery").log(Level.SEVERE, "An Exception occurred while reading vertices data: " + ex);
        }
        return planets;
    }

    public List<Route> readRoutes() {
        List<Route> routes = new ArrayList<>();
        try {
            FileInputStream inputStream = new FileInputStream(this.file);

            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet firstSheet = workbook.getSheetAt(1);
            Iterator<Row> iterator = firstSheet.iterator();

            int recordId = 1;
            while (iterator.hasNext()) {
                Row nextRow = iterator.next();
                // skip header
                if (nextRow.getRowNum() == 0) {
                    continue;
                }
                Iterator<Cell> cellIterator = nextRow.cellIterator();
                Route route = new Route();
                route.setRecordId(recordId);
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    int columnIndex = cell.getColumnIndex();
                    switch (columnIndex + 1) {
                        case 1:
                            route.setRouteId(String.valueOf((int) cell.getNumericCellValue()));
                            break;
                        case 2:
                            route.setSource(cell.getStringCellValue());
                            break;
                        case 3:
                            route.setDestination(cell.getStringCellValue());
                            break;
                        case 4:
                            route.setDistance((float) cell.getNumericCellValue());
                            break;
                    }
                }

                routes.add(route);
                recordId = recordId + 1;
            }

            workbook.close();
            inputStream.close();
        } catch (IOException ex) {
            Logger.getLogger("discovery").log(Level.SEVERE, "An Exception occurred while reading routes data: " + ex);
        }
        return routes;
    }

    public List<Traffic> readTraffics() {
        List<Traffic> traffics = new ArrayList<>();
        try {
            FileInputStream inputStream = new FileInputStream(this.file);

            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet thirdSheet = workbook.getSheetAt(2);
            Iterator<Row> iterator = thirdSheet.iterator();

            while (iterator.hasNext()) {
                Row nextRow = iterator.next();
                // skip header
                if (nextRow.getRowNum() == 0) {
                    continue;
                }
                Iterator<Cell> cellIterator = nextRow.cellIterator();
                Traffic traffic = new Traffic();

                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    int columnIndex = cell.getColumnIndex();
                    switch (columnIndex + 1) {
                        case 1:
                            traffic.setRouteId(String.valueOf((int) cell.getNumericCellValue()));
                            break;
                        case 2:
                            traffic.setSource((String) getCellValue(cell));
                            break;
                        case 3:
                            traffic.setDestination((String) getCellValue(cell));
                            break;
                        case 4:
                            traffic.setDelay((float) cell.getNumericCellValue());
                            break;
                    }
                }
                traffics.add(traffic);
            }

            workbook.close();
            inputStream.close();
        } catch (IOException ex) {
            Logger.getLogger("discovery").log(Level.SEVERE, "An Exception occurred while reading traffics data: " + ex);
        }

        return traffics;
    }

    private Object getCellValue(Cell cell) {
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                return cell.getStringCellValue();

            case Cell.CELL_TYPE_BOOLEAN:
                return cell.getBooleanCellValue();

            case Cell.CELL_TYPE_NUMERIC:
                return cell.getNumericCellValue();
        }

        return null;
    }
}
