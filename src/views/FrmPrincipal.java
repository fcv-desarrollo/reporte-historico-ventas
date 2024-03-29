/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import com.sun.awt.AWTUtilities;
import controllers.ConvertToExcel;
import java.awt.Cursor;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import java.awt.Toolkit;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

/**
 *
 * @author Carlos J Sanchez
 */
public class FrmPrincipal extends javax.swing.JPanel {

    /**
     * Creates new form FrmPrincipa
     */
    public FrmPrincipal() {
        initComponents();
        lbPrgBar.setVisible(false);
        prgBar.setVisible(false);
        setFrame();
        loadLogo();
        setIconsWindowButtons();
        JOptionPane.showMessageDialog(this, "ADVERTENCIA:\n\n Para asegurarse que la información esté completamente \n "
                + "cargada en el sistema, se recomienda que el reporte sea \n "
                + "consultado después de las 11:00 AM. \n\n"
                + "Para continuar, haga clic en aceptar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        pnlWindowBtns = new javax.swing.JPanel();
        lbLogo = new javax.swing.JLabel();
        lbTitleWindow = new javax.swing.JLabel();
        btnMin = new javax.swing.JLabel();
        btnMax = new javax.swing.JLabel();
        btnClose = new javax.swing.JLabel();
        btnDownload = new javax.swing.JButton();
        prgBar = new javax.swing.JProgressBar();
        lbPrgBar = new javax.swing.JLabel();

        jTextField1.setText("jTextField1");

        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 153)));

        pnlWindowBtns.setBackground(new java.awt.Color(0, 102, 153));
        pnlWindowBtns.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        pnlWindowBtns.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pnlWindowBtnsMousePressed(evt);
            }
        });
        pnlWindowBtns.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                pnlWindowBtnsMouseDragged(evt);
            }
        });

        lbTitleWindow.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lbTitleWindow.setForeground(new java.awt.Color(255, 255, 255));
        lbTitleWindow.setText("Ventas CrediContino");

        btnMin.setToolTipText("Minimizar");
        btnMin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnMinMouseClicked(evt);
            }
        });

        btnMax.setToolTipText("Maximizar");
        btnMax.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnMaxMouseClicked(evt);
            }
        });

        btnClose.setToolTipText("Cerrar");
        btnClose.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCloseMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pnlWindowBtnsLayout = new javax.swing.GroupLayout(pnlWindowBtns);
        pnlWindowBtns.setLayout(pnlWindowBtnsLayout);
        pnlWindowBtnsLayout.setHorizontalGroup(
            pnlWindowBtnsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlWindowBtnsLayout.createSequentialGroup()
                .addComponent(lbLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addComponent(lbTitleWindow)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addComponent(btnMin, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnMax, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnClose, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlWindowBtnsLayout.setVerticalGroup(
            pnlWindowBtnsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbTitleWindow, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnMax, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(btnClose, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(btnMin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lbLogo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        btnDownload.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        btnDownload.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/file-download-icon.png"))); // NOI18N
        btnDownload.setText("Haga clic para descargar el archivo");
        btnDownload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDownloadActionPerformed(evt);
            }
        });

        lbPrgBar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lbPrgBar.setText("Generando archivo");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlWindowBtns, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnDownload)
                .addGap(127, 127, 127))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(prgBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lbPrgBar)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlWindowBtns, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnDownload)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addComponent(lbPrgBar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(prgBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnMinMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMinMouseClicked
        // TODO add your handling code here:
        f.setExtendedState(Cursor.CROSSHAIR_CURSOR);
    }//GEN-LAST:event_btnMinMouseClicked

    private void btnMaxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMaxMouseClicked
        // TODO add your handling code here:
        if (isMaximized) {
            ImageIcon iconMaximize = new ImageIcon(getClass().getResource("/img/sz-maximize.png"));
            ImageIcon isMaximize = new ImageIcon(iconMaximize.getImage().getScaledInstance(24, 24, Image.SCALE_DEFAULT));
            btnMax.setIcon(isMaximize);
            btnMax.setToolTipText("Maximizar");
//            f.setExtendedState(JFrame.NORMAL);
            isMaximized = false;
        } else {
            ImageIcon iconMaximize = new ImageIcon(getClass().getResource("/img/sz-restore.png"));
            ImageIcon isMaximize = new ImageIcon(iconMaximize.getImage().getScaledInstance(24, 24, Image.SCALE_DEFAULT));
            btnMax.setIcon(isMaximize);
            btnMax.setToolTipText("Restarurar");
//            f.setExtendedState(JFrame.MAXIMIZED_BOTH);
            isMaximized = true;
        }
    }//GEN-LAST:event_btnMaxMouseClicked

    private void btnCloseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCloseMouseClicked
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_btnCloseMouseClicked

    private void btnDownloadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDownloadActionPerformed
        // TODO add your handling code here:
        btnDownload.setEnabled(false);
        try {
            Date date = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            ConvertToExcel ce = new ConvertToExcel(lbPrgBar, prgBar, btnDownload, calendar.getTime());
            ce.addPropertyChangeListener(new PropertyChangeListener(){

                @Override
                public void propertyChange(PropertyChangeEvent evt) {
                    if (evt.getPropertyName().equalsIgnoreCase("progress")) {
                        setCursor(new Cursor(Cursor.WAIT_CURSOR));
                    } else {
                        if (evt.getPropertyName().equalsIgnoreCase("state")) {
                            switch ((SwingWorker.StateValue) evt.getNewValue()) {
                                case DONE:
                                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                                    break;
                                case STARTED:
                                    setCursor(new Cursor(Cursor.WAIT_CURSOR));
                                    break;
                                case PENDING:
//                                    setCursor(new Cursor(Cursor.));
                                    break;
                            }
                        }
                    }
                }
            });
            ce.execute();
        } catch (Exception ex) {
            System.err.println("Error at FrmPrincipal.btnDownloadActionPerformed(): " + ex.getMessage());
            JOptionPane.showMessageDialog(this, "Debe seleccionar una fecha", "Fecha vacía", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnDownloadActionPerformed

    private void pnlWindowBtnsMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlWindowBtnsMousePressed
        // TODO add your handling code here:
        x = evt.getX();
        y = evt.getY();
    }//GEN-LAST:event_pnlWindowBtnsMousePressed

    private void pnlWindowBtnsMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlWindowBtnsMouseDragged
        // TODO add your handling code here:
        f.setLocation(f.getLocation().x + evt.getX() - x, f.getLocation().y + evt.getY() - y);
    }//GEN-LAST:event_pnlWindowBtnsMouseDragged

    private void loadLogo() {
        ImageIcon icon = new ImageIcon(getClass().getResource("/img/logo_contino.png"));
        ImageIcon iconScaled = new ImageIcon(icon.getImage().getScaledInstance(lbLogo.getWidth(), lbLogo.getHeight(), Image.SCALE_DEFAULT));
        lbLogo.setIcon(iconScaled);
    }

    private void setIconsWindowButtons() {
        ImageIcon iconClose = new ImageIcon(getClass().getResource("/img/sz-close.png"));
        ImageIcon isClose = new ImageIcon(iconClose.getImage().getScaledInstance(24, 24, Image.SCALE_DEFAULT));
        btnClose.setIcon(isClose);

        ImageIcon iconMaximize = new ImageIcon(getClass().getResource("/img/sz-maximize.png"));
        ImageIcon isMaximize = new ImageIcon(iconMaximize.getImage().getScaledInstance(24, 24, Image.SCALE_DEFAULT));
        btnMax.setIcon(isMaximize);

        ImageIcon iconMinimize = new ImageIcon(getClass().getResource("/img/sz-minimize.png"));
        ImageIcon isMinimize = new ImageIcon(iconMinimize.getImage().getScaledInstance(24, 24, Image.SCALE_DEFAULT));
        btnMin.setIcon(isMinimize);
    }

    private void setFrame() {
        f.add(this);
        f.setSize(600, 180);
        f.setLocationRelativeTo(null);
        f.setUndecorated(true);
        AWTUtilities.setWindowOpaque(f, false);
        f.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/app-icon.png")));
        f.setVisible(true);
    }

    private final JFrame f = new JFrame();
    private boolean isMaximized;
    private int x;
    private int y;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel btnClose;
    private javax.swing.JButton btnDownload;
    private javax.swing.JLabel btnMax;
    private javax.swing.JLabel btnMin;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel lbLogo;
    private javax.swing.JLabel lbPrgBar;
    private javax.swing.JLabel lbTitleWindow;
    private javax.swing.JPanel pnlWindowBtns;
    private javax.swing.JProgressBar prgBar;
    // End of variables declaration//GEN-END:variables
}
