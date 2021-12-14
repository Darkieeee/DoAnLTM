package userinterface;
import components.table.AttributiveCellTableModel;
import components.table.MultiSpanCellTable;
import components.table.AttributiveCellRenderer;
import components.event.KeyListenerMoveRow;
import components.table.TableHeaderRenderer;
import connection.ClientExecute;
import java.awt.Color;
import java.awt.event.MouseAdapter;
public class Main extends javax.swing.JFrame {
    private final MultiSpanCellTable tblLichhoc;
    private final ClientExecute client;
    public static final Color df_BG_CELL = Color.decode("#F0F0DC");
    public static final Color df_FG_CELL = Color.WHITE;
    public static final String[] dsThu = new String[]{"Thứ hai","Thứ ba","Thứ tư",
                                                      "Thứ năm","Thứ sáu","Thứ bảy",
                                                      "Chủ nhật"};
    public Main() {
        initComponents();
        AttributiveCellTableModel table = new AttributiveCellTableModel(dsThu,14){
                                                @Override
                                                public boolean isCellEditable(int row,int column)
                                                {
                                                    return false;
                                                }
                                            };
        tblLichhoc = new MultiSpanCellTable(table);
        jScrollPane3.getViewport().add(tblLichhoc);
        addOn();
        client = new ClientExecute();
    }
    private void addOn()
    {
        setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
        setIconImage(new javax.swing.ImageIcon("src/main/resources/images/logoSGU.png").getImage());
        /*--------------------Thiet ke lai Table-------------------*/
        
            tblTiethoc.getTableHeader().setPreferredSize(new java.awt.Dimension(
                                                            tblLichhoc.getTableHeader().getWidth(),
                                                            25
                                                        ));
            tblLichhoc.getTableHeader().setPreferredSize(new java.awt.Dimension(
                                                            tblTiethoc.getTableHeader().getWidth(),
                                                            25
                                                        ));
            tblMonhoc.getTableHeader().setPreferredSize(new java.awt.Dimension(
                                                            tblMonhoc.getTableHeader().getWidth(),
                                                            25
                                                        ));
            java.awt.Color background;
            java.awt.Color foreground;
            java.awt.Font textFont; 
            textFont = new java.awt.Font("Tahoma",java.awt.Font.BOLD,11);
            background = Color.WHITE;
            foreground = Color.WHITE;
            setHeaderCellStyle(tblTiethoc, background, foreground);
            background = Color.decode("#6699CC");
            setHeaderCellStyle(tblLichhoc, background, foreground, textFont);
            setHeaderCellStyle(tblMonhoc,background,foreground, textFont);
            setCenteredAlignmentTableBodyCell(tblTiethoc);
            tblLichhoc.setRowHeight(25);
            setReorderingColumn(tblMonhoc, false);
            setMyCellStyle(tblLichhoc);
            tblLichhoc.setBackground(java.awt.Color.WHITE);
            tblLichhoc.setSelectionBackground(java.awt.Color.decode("#E6E6FA"));
            tblMonhoc.setSelectionBackground(java.awt.Color.decode("#E6E6FA"));
            tblMonhoc.setSelectionForeground(java.awt.Color.BLACK);
            tblMonhoc.setBackground(java.awt.Color.WHITE);
            
        /*------------------------------------------------------------*/
        
        /*-------------------Them su kien-----------------------------*/
        
            tblMonhoc.addKeyListener(new KeyListenerMoveRow(tblMonhoc));
            tblLichhoc.addMouseMotionListener(new MouseAdapter(){
                @Override
                public void mouseMoved(java.awt.event.MouseEvent e)
                {
                    int rowIndex = tblLichhoc.rowAtPoint(e.getPoint());
                    int columnIndex = tblLichhoc.columnAtPoint(e.getPoint());
                    Object object = tblLichhoc.getValueAt(rowIndex, columnIndex);
                    tblLichhoc.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.DEFAULT_CURSOR));
                    if(object != null)
                    {
                        tblLichhoc.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));
                    }
                    tblLichhoc.changeSelection(rowIndex, columnIndex, false, false);                
                }
            });
            tblLichhoc.addMouseListener(new java.awt.event.MouseAdapter(){
                @Override
                public void mouseExited(java.awt.event.MouseEvent e)
                {
                    tblLichhoc.clearSelection();
                }
            });
            tblMonhoc.addMouseMotionListener(new MouseAdapter(){
                @Override
                public void mouseMoved(java.awt.event.MouseEvent e)
                {
                    int rowIndex = tblMonhoc.rowAtPoint(e.getPoint());
                    int columnIndex = tblMonhoc.columnAtPoint(e.getPoint());
                    tblMonhoc.changeSelection(rowIndex, columnIndex, false, false);                
                }
            });
            tblMonhoc.addMouseListener(new java.awt.event.MouseAdapter(){
                @Override
                public void mouseExited(java.awt.event.MouseEvent e)
                {
                    tblMonhoc.clearSelection();
                }
            });
    }
    private void LoadingGIF()
    {
        //Do wait till the server responses back
    }
    private void setReorderingColumn(javax.swing.JTable table, boolean allowReorder)
    {
        table.getTableHeader().setReorderingAllowed(allowReorder);
    }
    private void setHeaderCellStyle(javax.swing.JTable table, java.awt.Color background, java.awt.Color foreground)
    {
        for(int i=0;i<table.getColumnCount();i++)
        {
            table.getColumnModel().getColumn(i).setHeaderRenderer(new TableHeaderRenderer(background,foreground));
            table.getColumnModel().getColumn(i).setResizable(false);
        }
    }
    private void setHeaderCellStyle(javax.swing.JTable table, java.awt.Color background, java.awt.Color foreground, java.awt.Font TextFont)
    {
        for(int i=0;i<table.getColumnCount();i++)
        {
            table.getColumnModel().getColumn(i).setResizable(false);
            table.getColumnModel().getColumn(i).setHeaderRenderer(new TableHeaderRenderer(background,foreground, TextFont));
        }
    }
    private void setMyCellStyle(javax.swing.JTable table)
    {
        for(int i=0;i<table.getColumnCount();i++)
        {
            table.getColumnModel().getColumn(i).setResizable(false);
            table.getColumnModel().getColumn(i).setCellRenderer(new AttributiveCellRenderer());
        }
    }
    private void setCenteredAlignmentTableBodyCell(javax.swing.JTable table){
        javax.swing.table.DefaultTableCellRenderer centerRenderer = new javax.swing.table.DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(javax.swing.JLabel.CENTER);
        for(int i=0;i<table.getColumnCount();i++)
        {
            table.getColumnModel().getColumn(i).setResizable(false);
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        inpSubjectID = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        btnTenMonHoc = new javax.swing.JButton();
        btnMaMonHoc = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblMonhoc = new javax.swing.JTable();
        inpSubjectName = new javax.swing.JTextField();
        btnOption2 = new javax.swing.JButton();
        btnOption3 = new javax.swing.JButton();
        btnOption4 = new javax.swing.JButton();
        btnOption1 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblTiethoc = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        lblLogout = new javax.swing.JLabel();
        lblMinimize = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Xếp thời khoá biểu SGU");
        setBackground(new java.awt.Color(255, 255, 255));
        setLocationByPlatform(true);
        setUndecorated(true);
        setResizable(false);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Chọn môn theo tên:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Chọn tiêu chí TKB:");

        inpSubjectID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inpSubjectIDActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Chọn môn theo mã:");

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setText("XẾP THỜI KHÓA BIỂU");

        btnTenMonHoc.setText("Chọn");

        btnMaMonHoc.setText("Chọn");

        tblMonhoc.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                { new Integer(1), null, null}
            },
            new String [] {
                "Độ ưu tiên", "Mã MH", "Tên môn học"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblMonhoc.setRowHeight(24);
        tblMonhoc.setShowGrid(true);
        jScrollPane1.setViewportView(tblMonhoc);

        btnOption2.setText("Số ngày học ít nhất");
        btnOption2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOption2ActionPerformed(evt);
            }
        });

        btnOption3.setText("Phòng học thấp nhất");

        btnOption4.setText("Xếp ngẫu nhiên");
        btnOption4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOption4ActionPerformed(evt);
            }
        });

        btnOption1.setText("Số tiết học ít nhất");
        btnOption1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOption1ActionPerformed(evt);
            }
        });

        jScrollPane3.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane3.setPreferredSize(new java.awt.Dimension(600, 224));

        jScrollPane2.setToolTipText("");
        jScrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane2.setPreferredSize(new java.awt.Dimension(50, 224));

        tblTiethoc.setBackground(new java.awt.Color(102, 153, 204));
        tblTiethoc.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        tblTiethoc.setForeground(new java.awt.Color(255, 255, 255));
        tblTiethoc.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"Tiết 1"},
                {"Tiết 2"},
                {"Tiết 3"},
                {"Tiết 4"},
                {"Tiết 5"},
                {"Tiết 6"},
                {"Tiết 7"},
                {"Tiết 8"},
                {"Tiết 9"},
                {"Tiết 10"},
                {"Tiết 11"},
                {"Tiết 12"},
                {"Tiết 13"},
                {"Tiết 14"}
            },
            new String [] {
                ""
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblTiethoc.setMinimumSize(new java.awt.Dimension(15, 350));
        tblTiethoc.setPreferredSize(new java.awt.Dimension(75, 350));
        tblTiethoc.setRowHeight(25);
        tblTiethoc.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblTiethoc.setShowGrid(true);
        jScrollPane2.setViewportView(tblTiethoc);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(36, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 938, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(51, 51, 51)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel2))
                                        .addGap(5, 5, 5)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(inpSubjectID, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(inpSubjectName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(btnTenMonHoc)
                                            .addComponent(btnMaMonHoc)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addComponent(btnOption3, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(26, 26, 26))
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                                .addComponent(btnOption1, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(btnOption2)
                                            .addComponent(btnOption4))
                                        .addGap(37, 37, 37)))
                                .addGap(89, 89, 89))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel1)
                                .addGap(204, 204, 204)))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(110, 110, 110))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(inpSubjectID, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnMaMonHoc, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(inpSubjectName, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnTenMonHoc, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnOption2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnOption1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnOption3, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnOption4, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(20, 20, 20)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 382, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 382, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );

        getContentPane().add(jPanel3, java.awt.BorderLayout.CENTER);

        jPanel1.setBackground(new java.awt.Color(102, 153, 204));
        jPanel1.setPreferredSize(new java.awt.Dimension(1084, 65));

        lblLogout.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLogout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/close.png"))); // NOI18N
        lblLogout.setToolTipText("Close");
        lblLogout.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblLogout.setPreferredSize(new java.awt.Dimension(30, 30));
        lblLogout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblLogoutMouseClicked(evt);
            }
        });

        lblMinimize.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/minimize (2).png"))); // NOI18N
        lblMinimize.setToolTipText("Minimize");
        lblMinimize.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblMinimizeMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(996, Short.MAX_VALUE)
                .addComponent(lblMinimize, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblLogout, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblMinimize, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
                    .addComponent(lblLogout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.NORTH);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnOption2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOption2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnOption2ActionPerformed

    private void btnOption1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOption1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnOption1ActionPerformed

    private void btnOption4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOption4ActionPerformed
        if(!client.isConnected())
        {
            javax.swing.JOptionPane.showMessageDialog( null,
                                                       "Không thể kết nối host/port của server",
                                                       "Chương trình",
                                                       javax.swing.JOptionPane.ERROR_MESSAGE);
        }
        
        else{
            client.sendMessage("Hello Server");
            String originalString = client.getMessage();
            System.out.println(originalString);
        }
    }//GEN-LAST:event_btnOption4ActionPerformed

    private void inpSubjectIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inpSubjectIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_inpSubjectIDActionPerformed

    private void lblLogoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLogoutMouseClicked
        int confirmExit = javax.swing.JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn thoát?", "Chương trình", javax.swing.JOptionPane.YES_NO_OPTION);
        if(confirmExit == javax.swing.JOptionPane.YES_OPTION)
        {
//            if(client.isConnected())
//            {
//                client.close();
//            }
            this.dispose();
        }
    }//GEN-LAST:event_lblLogoutMouseClicked

    private void lblMinimizeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMinimizeMouseClicked
        this.setState(javax.swing.JFrame.ICONIFIED);
    }//GEN-LAST:event_lblMinimizeMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Main().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnMaMonHoc;
    private javax.swing.JButton btnOption1;
    private javax.swing.JButton btnOption2;
    private javax.swing.JButton btnOption3;
    private javax.swing.JButton btnOption4;
    private javax.swing.JButton btnTenMonHoc;
    private javax.swing.JTextField inpSubjectID;
    private javax.swing.JTextField inpSubjectName;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblLogout;
    private javax.swing.JLabel lblMinimize;
    private javax.swing.JTable tblMonhoc;
    private javax.swing.JTable tblTiethoc;
    // End of variables declaration//GEN-END:variables
}
