/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import connection.Connect;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;
import models.Report;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

/**
 *
 * @author Carlos J Sanchez
 */
public class ConvertToExcel extends SwingWorker<Integer, String> {

    private JLabel lbPrgBar;
    private JProgressBar prgBar;
    private JButton btnDL;
    private Date dateReport;
    private final Connect con;

    public ConvertToExcel(JLabel lbPrgBar, JProgressBar prgBar, JButton btnDL, Date dateReport) {
        this.lbPrgBar = lbPrgBar;
        this.prgBar = prgBar;
        this.btnDL = btnDL;
        this.dateReport = dateReport;
        con = new Connect();
    }

    @Override
    protected Integer doInBackground() throws Exception {

        getLbPrgBar().setVisible(true);
        getPrgBar().setVisible(true);
        getPrgBar().setIndeterminate(true);

        Calendar cal = Calendar.getInstance();
        cal.setTime(getDateReport());
        cal.add(Calendar.DAY_OF_MONTH, -1);
        String dia = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
        String mes = String.valueOf(cal.get(Calendar.MONTH));
        String anio = String.valueOf(cal.get(Calendar.YEAR));
        switch (mes) {
            case "0":
                mes = "Enero";
                break;
            case "1":
                mes = "Febrero";
                break;
            case "2":
                mes = "Marzo";
                break;
            case "3":
                mes = "Abril";
                break;
            case "4":
                mes = "Mayo";
                break;
            case "5":
                mes = "Junio";
                break;
            case "6":
                mes = "Julio";
                break;
            case "7":
                mes = "Agosto";
                break;
            case "8":
                mes = "Septiembre";
                break;
            case "9":
                mes = "Octubre";
                break;
            case "10":
                mes = "Noviembre";
                break;
            case "11":
                mes = "Diciembre";
                break;
        }
        getLbPrgBar().setText("Seleccione la ubicación donde se guardará el archivo");
        System.out.println("Seleccionando ruta donde se guardará el archivo");
        JFileChooser saveDialog = new JFileChooser();
        saveDialog.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        saveDialog.showSaveDialog(null);
        boolean crearArchivo = false;
        if (saveDialog.getSelectedFile() != null) {

            String path = saveDialog.getSelectedFile().getPath() + "\\Ventas CC " + dia + mes + anio + ".xlsx"; //Obtiene la ruta seleccionada
            File fileXLS = new File(path);
            if (fileXLS.exists()) { //Si existe el fichero lo borra y crea uno nuevo
                int option = JOptionPane.showConfirmDialog(null, "El archivo ya existe, ¿desea reemplazarlo?", "Ya existe el archivo", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (option == JOptionPane.YES_OPTION) {
                    fileXLS.delete();
                    fileXLS.createNewFile(); //Crea el fichero
                    crearArchivo = true;
                } else {
                    saveDialog.setSelectedFile(null);
                }
            } else {
                fileXLS.createNewFile(); //Crea el fichero
                crearArchivo = true;
            }

            if (saveDialog.getSelectedFile() != null) {
                try {
//            System.getProperty("user.home") + "/ventas.xls"; //Obtiene la ruta y nombre del fichero
                    getLbPrgBar().setText("Actualizando registros...");
                    System.out.println("Actualizando registros...");
                    if (updateTable(getDateReport())) {
                        if (crearArchivo) {
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(getDateReport());
                            calendar.add(Calendar.DAY_OF_MONTH, -1);
                            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

                            getLbPrgBar().setText("Guardando cambios...");
                            System.out.println("Guardando cambios...");
                            List listaCanceladas = con.searchCanceled(calendar.getTime());
                            int updated = con.updateCanceled(listaCanceladas);
                            System.out.println("Actualizadas: " + updated);//
                            
                            getLbPrgBar().setText("Obteniendo registros...");
                            System.out.println("Obteniendo registros...");

                            List<Report> listaDatos = con.getReport(formato.format(calendar.getTime()));

                            getPrgBar().setIndeterminate(false);
                            getPrgBar().setMinimum(0);
                            getPrgBar().setMaximum(listaDatos.size());
                            getPrgBar().setStringPainted(true);

                            Workbook book = new SXSSFWorkbook(); //Crea el libro
                            FileOutputStream file = new FileOutputStream(fileXLS);
                            Sheet sheet = book.createSheet("Hoja 1"); //Crea la hoja
                            String[] headers = new String[]{"FECHA_FACTURA", "FOLIO_FACTURA", "IMPORTE_TOTAL",
                                "ENGANCHE_CR", "PRIMER_PAGO_CR", "IMPORTE_FINANCIADO", "RECEPTOR_NOMBRE", "RECEPTOR_RFC",
                                "TELEFONO", "EMAIL", "ESQUEMA_CR", "PLAZO", "NUM_TARJETA_CC", "CLAVE_AUT_CC", "FOLIO_PREOP",
                                "FOLIO_FACT_SICO", "OPERACION"};

                            Row headerRow = sheet.createRow(0);

                            getLbPrgBar().setText("Creando cabeceras...");
                            System.out.println("Creando cabeceras...");

                            for (int i = 0; i < headers.length; i++) {
                                Cell cell = headerRow.createCell(i);
                                cell.setCellValue(headers[i]);
                            }

                            for (int j = 0; j < listaDatos.size(); j++) {
                                Row dataRow = sheet.createRow(j + 1);
                                java.util.Date fechaRep = new SimpleDateFormat("yyyy-MM-dd").parse(listaDatos.get(j).getFechaFactura().substring(0, 11));
                                dataRow.createCell(0).setCellValue(formato.format(fechaRep));
                                dataRow.createCell(1).setCellValue(listaDatos.get(j).getFolioFactura());
                                dataRow.createCell(2).setCellValue(listaDatos.get(j).getImporteTotal());
                                dataRow.createCell(3).setCellValue(listaDatos.get(j).getEngancheCr());
                                dataRow.createCell(4).setCellValue(listaDatos.get(j).getPrimerPagoCr());
                                dataRow.createCell(5).setCellValue(listaDatos.get(j).getImporteFinanciado());
                                dataRow.createCell(6).setCellValue(listaDatos.get(j).getReceptorNombre());
                                dataRow.createCell(7).setCellValue(listaDatos.get(j).getReceptorRfc());
                                dataRow.createCell(8).setCellValue(listaDatos.get(j).getTelefono());
                                dataRow.createCell(9).setCellValue(listaDatos.get(j).getEmail());
                                dataRow.createCell(10).setCellValue(listaDatos.get(j).getEsquemaCr());
                                dataRow.createCell(11).setCellValue(listaDatos.get(j).getPlazo());
                                dataRow.createCell(12).setCellValue(listaDatos.get(j).getNumTarjetaCc());
                                dataRow.createCell(13).setCellValue(listaDatos.get(j).getClaveAutCc());
                                dataRow.createCell(14).setCellValue(listaDatos.get(j).getFolioPreop());
                                dataRow.createCell(15).setCellValue(listaDatos.get(j).getFolioFactSico());
                                dataRow.createCell(16).setCellValue(listaDatos.get(j).getOperacion());

                                getPrgBar().setValue(j + 1);
                                getLbPrgBar().setText("Descargando registros " + (j + 1) + " de " + listaDatos.size() + "...");
                                System.out.println("Descargando registros " + (j + 1) + " de " + listaDatos.size());
                            }

                            getPrgBar().setStringPainted(false);
                            getPrgBar().setIndeterminate(true);                            
                            lbPrgBar.setText("Actualizando registros en CrediContino...");
                            System.out.println("Actualizando registros en CrediContino...");
                            List listExistInCC = con.existInCrediContino(calendar.getTime());
                            int inserted = con.insertCrediContino(listExistInCC);
                            System.out.println("Insertadas: " + inserted);
                            int sended = con.sendedCrediContino(listExistInCC);
                            System.out.println("Enviadas: " + sended);
                            
                            book.write(file);
                            file.close();

                            getPrgBar().setIndeterminate(false);
                            getPrgBar().setValue(getPrgBar().getMaximum());
                            getLbPrgBar().setText("El archivo se ha creado.");
                            System.out.println("El archivo se ha creado.");

                            int openFile = JOptionPane.showConfirmDialog(null, "¿Desea abrir el archivo?", "El archivo se genero correctamente", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                            if (openFile == JOptionPane.YES_OPTION) {
                                Desktop.getDesktop().open(fileXLS);
                            }
                        }
                    }
                } catch (Exception ex) {
                    getLbPrgBar().setText("No se pudo generar el archivo.");
                    System.err.println("Error ConvertToExcel.createFile(): " + ex.getMessage());
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "No se pudo generar archivo", JOptionPane.ERROR_MESSAGE);
                    Logs.createFile("Error at ConvertToExcel.doInBackground(): " + ex.getMessage());
                }
            } else {
                getLbPrgBar().setText("El archivo ya existe.");
                System.out.println("El archivo ya existe");
                getPrgBar().setVisible(false);
            }
        } else {
            getLbPrgBar().setText("No se seleccionó la ruta.");
            System.out.println("No se seleccionó la ruta");
            getPrgBar().setVisible(false);
        }
//        getLbPrgBar().setVisible(false);//
//        getPrgBar().setVisible(false);//
        getBtnDL().setEnabled(true);

        return 0;
    }

    public boolean updateTable(Date dateReport) {
        return con.updateReport(dateReport);
    }

    public JLabel getLbPrgBar() {
        return lbPrgBar;
    }

    public void setLbPrgBar(JLabel lbPrgBar) {
        this.lbPrgBar = lbPrgBar;
    }

    public JProgressBar getPrgBar() {
        return prgBar;
    }

    public void setPrgBar(JProgressBar prgBar) {
        this.prgBar = prgBar;
    }

    public Date getDateReport() {
        return dateReport;
    }

    public void setDateReport(Date dateReport) {
        this.dateReport = dateReport;
    }

    public JButton getBtnDL() {
        return btnDL;
    }

    public void setBtnDL(JButton btnDL) {
        this.btnDL = btnDL;
    }

}
