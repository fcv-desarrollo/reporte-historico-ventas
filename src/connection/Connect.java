/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connection;

import com.sun.javafx.scene.control.skin.VirtualFlow;
import controllers.Logs;
import java.util.List;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.JOptionPane;
import models.Report;
import models.FacturasC;

/**
 *
 * @author Carlos J Sanchez
 */
public class Connect {

    private Connection con = null;
    private Connection conMySQL = null;
    private ResultSet rs = null;
    private PreparedStatement ps = null;

    private final String HOST_ORACLE = "192.168.0.9";
    private final String DB_ORACLE = "facelfc";
    private final String USER_ORACLE = "facel";
    private final String PASS_ORACLE = "F2011FC";
    private final String PORT_ORACLE = "1521";

    private final String HOST_MYSQL = "192.168.0.171";
//    private final String DB_MYSQL = "credicontino_entrena"; //test
    private final String DB_MYSQL = "credicontino"; //prod
    private final String USER_MYSQL = "root";
    private final String PASS_MYSQL = "contino";
    private final String PORT_MYSQL = "";

    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public Connection getConnectionOracle() {
        Connection conOracle = null;
        try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            conOracle = DriverManager.getConnection("jdbc:oracle:thin:@" + HOST_ORACLE + ":" + PORT_ORACLE + ":" + DB_ORACLE, USER_ORACLE, PASS_ORACLE); //prod
//            con = DriverManager.getConnection("jdbc:oracle:thin:@192.168.0.233:1521:prod", "xxcust", "custom"); //prod
        } catch (Exception ex) {
            System.err.println("Error at Connect.getConnectionOracle(): " + ex.getMessage());
            JOptionPane.showMessageDialog(null, ex.getMessage(), "No se pudo conectar al servidor Oracle: " + HOST_ORACLE, JOptionPane.ERROR_MESSAGE);
            Logs.createFile("Error at Connect.getConnectionOracle(): " + ex.getMessage());
        }
        return conOracle;
    }

    public Connection getConnectionMySQL() {
        Connection conMySQL = null;
        try {
            Class.forName("org.gjt.mm.mysql.Driver");
            conMySQL = DriverManager.getConnection("jdbc:mysql://" + HOST_MYSQL + "/" + DB_MYSQL, USER_MYSQL, PASS_MYSQL);
        } catch (Exception ex) {
            System.err.println("Error at Connect.getConnectionMySQL(): " + ex.getMessage());
            JOptionPane.showMessageDialog(null, ex.getMessage(), "No se pudo conectar con el servidor MySQL: " + HOST_MYSQL, JOptionPane.ERROR_MESSAGE);
            Logs.createFile("Error at Connect.getConnectionMySQL(): " + ex.getMessage());
        }
        return conMySQL;
    }

    public List getReport(String date) {
        List<Report> listaRep = new ArrayList<>();
        try {
            String sql = "SELECT DISTINCT x.fecha_factura, x.folio_factura, x.importe_total, "
                    + "x.enganche_cr, x.primer_pago_cr,  xcp.importe_financiado, "
                    + "x.receptor_nombre, x.receptor_rfc, xcp.telefono, xcp.email, "
                    + "x.esquema_cr, xcp.plazo, x.num_tarjeta_cc, x.clave_aut_cc, "
                    + "xcp.folio_preop, "
                    + "REPLACE(x.folio_factura,'-','')||'-'||TO_CHAR(x.fecha_factura,'DDMMRRRR') FOLIO_FACT_SICO , CRV.OPERACION "
                    + "FROM xx_temporal_cc x, xx_credicontino_preop xcp, xx_cr_ventas crv "
                    + "WHERE (x.num_tarjeta_cc =  TO_NUMBER(xcp.num_tarjeta) "
                    + "AND fecha_factura = xcp.fecha) "
                    + "AND GET_ATTR_OPER_CAJA(x.atributos_adicionales) = to_number(substr(xcp.folio_preop,3)) "
                    + "AND substr(x.folio_factura,2,2)||lpad(substr(x.folio_factura,5),6,'0')  = crv.folio "
                    + "AND  x.fecha_factura BETWEEN TO_DATE('19/07/2016','DD/MM/RRRR') AND TO_DATE('" + date + "','DD/MM/RRRR') "
                    + "ORDER BY fecha_factura, X.FOLIO_FACTURA ";
            con = getConnectionOracle();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Report rep = new Report();
                rep.setFechaFactura(rs.getString(1));
                rep.setFolioFactura(rs.getString(2));
                rep.setImporteTotal(rs.getString(3));
                rep.setEngancheCr(rs.getString(4));
                rep.setPrimerPagoCr(rs.getString(5));
                rep.setImporteFinanciado(rs.getString(6));
                rep.setReceptorNombre(rs.getString(7));
                rep.setReceptorRfc(rs.getString(8));
                rep.setTelefono(rs.getString(9));
                rep.setEmail(rs.getString(10));
                rep.setEsquemaCr(rs.getString(11));
                rep.setPlazo(rs.getString(12));
                rep.setNumTarjetaCc(rs.getString(13));
                rep.setClaveAutCc(rs.getString(14));
                rep.setFolioPreop(rs.getString(15));
                rep.setFolioFactSico(rs.getString(16));
                rep.setOperacion(rs.getString(17));
                listaRep.add(rep);
            }
        } catch (Exception ex) {
            System.err.println("Error at Connect.getReport(): " + ex.getMessage());
            JOptionPane.showMessageDialog(null, "Revise archivo de errores para mas detalles. (logs/errLogs.txt)", "Error al obtener registros", JOptionPane.ERROR_MESSAGE);
            Logs.createFile("Error at Connect.getReport(): " + ex.getMessage());
        } finally {
            try {
                if (con != null || !con.isClosed()) {
                    con.close();
                    rs.close();
                    ps.close();
                }
            } catch (SQLException ex) {
                System.err.println("Error al cerrar las conexiones. Connect.getReport(): " + ex.getMessage());
            }
        }
        return listaRep;
    }

    public boolean updateReport(java.util.Date dateReport) {
        boolean isUpdated;
        try {
            Calendar calendarStart = Calendar.getInstance();
            Calendar calendarEnd = Calendar.getInstance();

            calendarStart.setTime(dateReport);
            calendarEnd.setTime(dateReport);
            calendarStart.add(Calendar.DAY_OF_MONTH, -15);
            calendarEnd.add(Calendar.DAY_OF_MONTH, -1);

            String startDate = dateFormat.format(calendarStart.getTime());
            String endDate = dateFormat.format(calendarEnd.getTime());
            String sql = "INSERT INTO XX_TEMPORAL_CC (FECHA_FACTURA, FOLIO_FACTURA, "
                    + "IMPORTE_TOTAL, RECEPTOR_RFC, RECEPTOR_NOMBRE, PRIMER_PAGO_CR, "
                    + "ENGANCHE_CR, ESQUEMA_CR, NUM_TARJETA_CC, CLAVE_AUT_CC, ATRIBUTOS_ADICIONALES) "
                    + "SELECT trunc(x.fecha_expedicion) fecha_factura, x.folio_factura,x.importe_total, "
                    + "x.receptor_rfc, x.receptor_nombre, x.primer_pago_cr, x.enganche_cr, "
                    + "x.esquema_cr, x.num_tarjeta_cc, x.clave_aut_cc, "
                    + "TO_LOB( x.atributos_adicionales ) as atributos_adicionales "
                    + "FROM xx_comprobantes_fae x "
                    + "WHERE condiciones_pago = 'CREDICONTINO' "
                    + "AND x.fecha_expedicion BETWEEN TO_DATE('" + startDate + " 00:00:00','dd/mm/rrrr hh24:mi:ss') "
                    + "AND TO_DATE('" + endDate + " 23:59:59','dd/mm/rrrr hh24:mi:ss') "
                    + "AND NOT EXISTS (SELECT * FROM XX_TEMPORAL_CC y "
                    + "WHERE fecha_factura BETWEEN TO_DATE('" + startDate + " 00:00:00', 'dd/mm/rrrr hh24:mi:ss') "
                    + "AND TO_DATE('" + endDate + " 23:59:59', 'dd/mm/rrrr hh24:mi:ss' ) "
                    + "AND x.folio_factura = y.folio_factura) "
                    + "ORDER BY trunc(x.fecha_expedicion) ";
            con = getConnectionOracle();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            isUpdated = true;
        } catch (Exception ex) {
            isUpdated = false;
            System.err.println("Error at Connect.updateReport(): " + ex.getMessage());
            JOptionPane.showMessageDialog(null, "Revise archivo de errores para mas detalles. (logs/errLogs.txt)", "Error al actualizar tabla", JOptionPane.ERROR_MESSAGE);
            Logs.createFile("Error at Connect.updateReport(): " + ex.getMessage());
        } finally {
            try {
                if (con != null || !con.isClosed()) {
                    con.close();
                    rs.close();
                    ps.close();
                }
            } catch (SQLException ex) {
                System.err.println("Error al cerrar las conexiones. Connect.updateReport(): " + ex.getMessage());
            }
        }
        return isUpdated;
    }

    public List searchCanceled(java.util.Date date) {
        List<FacturasC> listaDatos = new ArrayList<>();

        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();

        startDate.setTime(date);
        endDate.setTime(date);
        startDate.add(Calendar.DAY_OF_MONTH, -15);
//        endDate.add(Calendar.DAY_OF_MONTH, -1);
        try {
            String sql = "SELECT xcf.folio_factura, xcf.fecha_expedicion, xcf.serie, xcf.folio "
                    + "FROM XX_COMPROBANTES_FAE xcf, XX_COMPROBANTES_FAE xcf2 "
                    + "WHERE xcf2.fecha_expedicion BETWEEN TO_DATE(?, 'DD/MM/RRRR') AND TO_DATE(?, 'DD/MM/RRRR') "
                    + "AND xcf2.tipo_comprobante = 'EGRESO' "
                    + "AND xcf.condiciones_pago = 'CREDICONTINO' "
                    + "AND xcf.serie = SUBSTR(REPLACE(xcf2.factura_a_afectar, '-', ''), 1, 3) "
                    + "AND xcf.folio = LTRIM(SUBSTR(xcf2.factura_a_afectar, LENGTH(xcf2.factura_a_afectar)-5, 6), '0') "
                    + "ORDER BY xcf.fecha_expedicion ";
            con = getConnectionOracle();
            ps = con.prepareStatement(sql);
            ps.setString(1, dateFormat.format(startDate.getTime()));
            ps.setString(2, dateFormat.format(endDate.getTime()));
            rs = ps.executeQuery();
            while (rs.next()) {
                FacturasC f = new FacturasC();
                f.setFolioFactura(rs.getString(1));
                f.setFechaExpedicion(rs.getString(2));
                f.setSerie(rs.getString(3));
                f.setFolio(rs.getString(4));
                listaDatos.add(f);
            }
        } catch (Exception ex) {
            System.err.println("Error at Connect.searchCanceled(): " + ex.getMessage());
            JOptionPane.showMessageDialog(null, "Revise archivo de errores para mas detalles. (logs/errLogs.txt)", "Error al obtener cancelados", JOptionPane.ERROR_MESSAGE);
            Logs.createFile("Error at Connect.searchCanceled(): " + ex.getMessage());
        } finally {
            try {
                if (!con.isClosed() || con != null) {
                    con.close();
                    ps.close();
                    rs.close();
                }
            } catch (Exception e) {
                System.err.println("Error at Connect.searchCanceled(): " + e.getMessage());
            }
        }
        return listaDatos;
    }

    public int updateCanceled(List<FacturasC> listaDatos) {
        int counter = 0;
        try {
            conMySQL = getConnectionMySQL();
            for (int i = 0; i < listaDatos.size(); i++) {
                String sql = "UPDATE ventas "
                        + "SET operacion = 'C' WHERE folio_factura = ? AND operacion = 'F'";
                ps = conMySQL.prepareStatement(sql);
                ps.setString(1, listaDatos.get(i).getFolioFactura());
                counter = ps.executeUpdate();
            }
        } catch (Exception ex) {
            System.err.println("Error at Connect.updateCanceled(): " + ex.getMessage());
            JOptionPane.showMessageDialog(null, "Revise archivo de errores para mas detalles. (logs/errLogs.txt)", "Error al actualizar cancelados", JOptionPane.ERROR_MESSAGE);
            Logs.createFile("Error at Connect.updateCanceled(): " + ex.getMessage());
        } finally {
            try {
                if (!con.isClosed() || con != null) {
                    con.close();
                    ps.close();
                    rs.close();
                }
            } catch (Exception e) {
                System.err.println("Error at Connect.updateCanceled(): " + e.getMessage());
            }
        }
        return counter;
    }

    public List existInCrediContino(java.util.Date date) {
        List<Report> listaDatos = new ArrayList<>();
        try {
            String sql = "SELECT DISTINCT x.fecha_factura, x.folio_factura, x.importe_total, "
                    + "x.enganche_cr, x.primer_pago_cr,  xcp.importe_financiado, "
                    + "x.receptor_nombre, x.receptor_rfc, xcp.telefono, xcp.email, "
                    + "x.esquema_cr, xcp.plazo, x.num_tarjeta_cc, x.clave_aut_cc, "
                    + "xcp.folio_preop, "
                    + "REPLACE(x.folio_factura,'-','')||'-'||TO_CHAR(x.fecha_factura,'DDMMRRRR') FOLIO_FACT_SICO , CRV.OPERACION, "
                    + "x.FECHA_CREACION, x.FECHA_ENVIO_CC "
                    + "FROM xx_temporal_cc x, xx_credicontino_preop xcp, xx_cr_ventas crv "
                    + "WHERE (x.num_tarjeta_cc =  TO_NUMBER(xcp.num_tarjeta) "
                    + "AND fecha_factura = xcp.fecha) "
                    + "AND GET_ATTR_OPER_CAJA(x.atributos_adicionales) = to_number(substr(xcp.folio_preop,3)) "
                    + "AND substr(x.folio_factura,2,2)||lpad(substr(x.folio_factura,5),6,'0')  = crv.folio "
                    + "AND  x.fecha_factura BETWEEN TO_DATE('19/07/2016','DD/MM/RRRR') AND TO_DATE(?,'DD/MM/RRRR') "
                    + "AND x.FECHA_ENVIO_CC IS NULL "
                    + "ORDER BY fecha_factura, X.FOLIO_FACTURA ";
            con = getConnectionOracle();
            ps = con.prepareStatement(sql);
            ps.setString(1, dateFormat.format(date));
            rs = ps.executeQuery();
            while (rs.next()) {
                Report r = new Report();
                r.setFechaFactura(rs.getString(1));
                r.setFolioFactura(rs.getString(2));
                r.setImporteTotal(rs.getString(3));
                r.setEngancheCr(rs.getString(4));
                r.setPrimerPagoCr(rs.getString(5));
                r.setImporteFinanciado(rs.getString(6));
                r.setReceptorNombre(rs.getString(7));
                r.setReceptorRfc(rs.getString(8));
                r.setTelefono(rs.getString(9));
                r.setEmail(rs.getString(10));
                r.setEsquemaCr(rs.getString(11));
                r.setPlazo(rs.getString(12));
                r.setNumTarjetaCc(rs.getString(13));
                r.setClaveAutCc(rs.getString(14));
                r.setFolioPreop(rs.getString(15));
                r.setFolioFactSico(rs.getString(16));
                r.setOperacion(rs.getString(17));
                r.setFechaCreacion(rs.getString(18));
                r.setFechaEnvioCC(rs.getString(19));
                listaDatos.add(r);
            }
        } catch (Exception ex) {
            System.err.println("Error at Connect.existsInCrediContino(): " + ex.getMessage());
            JOptionPane.showMessageDialog(null, "Revise archivo de errores para mas detalles. (logs/errLogs.txt)", "Error al obtener registros CrediContino", JOptionPane.ERROR_MESSAGE);
            Logs.createFile("Error at Connect.existsInCrediContino(): " + ex.getMessage());
        } finally {
            try {
                if (!con.isClosed() || con != null) {
                    con.close();
                    ps.close();
                    rs.close();
                }
            } catch (Exception ex) {
                System.err.println("Error at Connect.existCrediContino(): " + ex.getMessage());
            }
        }
        return listaDatos;
    }

    public int insertCrediContino(List<Report> listaDatos) {
        int counter = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            conMySQL = getConnectionMySQL();
            String sql = "INSERT INTO ventas (fecha_factura, folio_factura, importe_total, enganche_cr, primer_pago_cr, "
                    + "importe_financiado, receptor_nombre, receptor_rfc, telefono, email, esquema_cr, plazo, "
                    + "num_tarjeta_cc, clave_aut_cc, folio_preop, folio_fact_sico, operacion, fecha_creacion) VALUES ";
            for (int i = 0; i < listaDatos.size(); i++) {
                java.util.Date date = sdf.parse(listaDatos.get(i).getFechaFactura());
                listaDatos.get(i).setFechaCreacion(sdf.format(date));
                sql += "('" + listaDatos.get(i).getFechaFactura() + "', '" + listaDatos.get(i).getFolioFactura() + "', "
                        + listaDatos.get(i).getImporteTotal() + ", " + listaDatos.get(i).getEngancheCr() + ", "
                        + listaDatos.get(i).getPrimerPagoCr() + ", " + listaDatos.get(i).getImporteFinanciado() + ", '"
                        + listaDatos.get(i).getReceptorNombre() + "', '" + listaDatos.get(i).getReceptorRfc() + "', "
                        + listaDatos.get(i).getTelefono() + ", '" + listaDatos.get(i).getEmail() + "', "
                        + listaDatos.get(i).getEsquemaCr() + ", " + listaDatos.get(i).getPlazo() + ", "
                        + listaDatos.get(i).getNumTarjetaCc() + ", '" + listaDatos.get(i).getClaveAutCc() + "', '"
                        + listaDatos.get(i).getFolioPreop() + "', '" + listaDatos.get(i).getFolioFactSico() + "', '"
                        + listaDatos.get(i).getOperacion() + "', CURRENT_TIMESTAMP)";
                if (i < (listaDatos.size() - 1)) {
                    sql += ", ";
                } else {
                    sql += ";";
                }
                System.out.println((i + 1) + " de " + listaDatos.size());
            }
            ps = conMySQL.prepareStatement(sql);
            counter += ps.executeUpdate();
        } catch (Exception ex) {
            System.err.println("Error at Connect.insertCrediContino(): " + ex.getMessage());
            JOptionPane.showMessageDialog(null, "Revise archivo de errores para mas detalles. (logs/errLogs.txt)", "Error al insertar en CrediContino", JOptionPane.ERROR_MESSAGE);
            Logs.createFile("Error at Connect.insertCrediContino(): " + ex.getMessage());
        } finally {
            try {
                if (conMySQL != null || !conMySQL.isClosed()) {
                    conMySQL.close();
                    ps.close();
                }
            } catch (Exception e) {
                System.err.println("Error al cerrar las conexiones. Connect.insertReport(): " + e.getMessage());
            }
        }
        return counter;
    }

    public int sendedCrediContino(List<Report> listaDatos) {
        int counter = 0;
        try {
            con = getConnectionOracle();
            for (int i = 0; i < listaDatos.size(); i++) {
                String sql = "UPDATE XX_TEMPORAL_CC "
                        + "SET fecha_envio_cc = SYSDATE "
                        + "WHERE folio_factura = ? AND fecha_envio_cc IS NULL ";
                ps = con.prepareStatement(sql);
                ps.setString(1, listaDatos.get(i).getFolioFactura());
                counter += ps.executeUpdate();
            }
        } catch (Exception ex) {
            System.err.println("Error at Connect.sendedCrediContino(): " + ex.getMessage());
            JOptionPane.showMessageDialog(null, "Revise archivo de errores para mas detalles. (logs/errLogs.txt)", "Error al actualizar enviados", JOptionPane.ERROR_MESSAGE);
            Logs.createFile("Error at Connect.sendedCrediContino(): " + ex.getMessage());
        } finally {
            try {
                if (con != null || !con.isClosed()) {
                    con.close();
                    ps.close();
                }
            } catch (Exception e) {
                System.err.println("Error al cerrar las conexiones. Connect.insertReport(): " + e.getMessage());
            }
        }
        return counter;
    }

}
