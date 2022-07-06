/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Carlos J Sanchez
 */
public class Logs {
    
    public static void createFile(String texto){
        FileWriter fw = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();                   
            String path = System.getProperty("user.home") + "/logs/errLogs.txt"; //Obtiene la ruta y nombre del fichero
            fw = new FileWriter(path, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("[" + sdf.format(date) + "]: " + texto + "\n");            
            bw.close();
        } catch(Exception ex){
            ex.printStackTrace();
        } finally {
            if (fw != null) {
                try {
                    fw.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
