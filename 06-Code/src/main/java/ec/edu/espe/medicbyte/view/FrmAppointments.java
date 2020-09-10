package ec.edu.espe.medicbyte.view;

import ec.edu.espe.medicbyte.common.core.View;
import ec.edu.espe.medicbyte.model.Appointment;
import ec.edu.espe.medicbyte.model.Appointment.Status;
import ec.edu.espe.medicbyte.model.Speciality;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

/**
 *
 * @author Andres Jonathan J.
 */
public class FrmAppointments extends View {
    public class StatusListCellRenderer extends DefaultListCellRenderer {

        @Override
        public Component getListCellRendererComponent(
                                       JList list,
                                       Object value,
                                       int index,
                                       boolean isSelected,
                                       boolean cellHasFocus) {
            if (value instanceof Status) {
                value = lang.getString(((Status)value).name().toLowerCase());
            } else if (value == null) {
                value = lang.getString("all");
            }
            
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            return this;
        }
    }
    
    ResourceBundle lang = ResourceBundle.getBundle("ec/edu/espe/medicbyte/view/Bundle");
    private List<Appointment> appointments = new ArrayList<>();
    private List<Speciality> specialties = new ArrayList<>();
    
    /**
     * Creates new form FrmAppointments
     */
    public FrmAppointments() {
        initComponents();
        appointmentsScrollPanel.getVerticalScrollBar().setUnitIncrement(16);
        appointmentsScrollPanel.getViewport().setOpaque(false);
        
        cbxStatuses.setRenderer(new StatusListCellRenderer());
        
        if (cbxStatuses.getItemCount() == 0) {
            cbxStatuses.addItem(null);
        }
        
        if (cbxSpecialties.getItemCount() == 0) {
            cbxSpecialties.addItem(lang.getString("all"));
        }
        
        Arrays.asList(Status.values())
            .forEach(status -> cbxStatuses.addItem(status));
    }
    
    @Override
    protected void onChange(String name, Object oldValue, Object newValue) {
        if (name.equals("appointments")) {
            appointments = (List<Appointment>) newValue;
            appointmentsContainer.removeAll();
            appointments.sort(Comparator.comparing((Appointment a) -> a.getCreatedAt()).reversed());
            appointments.forEach(appointment -> {
                AppointmentItem item = new AppointmentItem(appointment);
                item.onCancel(() -> emit("cancelAppointment", appointment));
                appointmentsContainer.add(item);
            });
            
            if (appointments.isEmpty()) {
                appointmentsContainer.add(pnlEmptyRecords);
            }
        }
        
        if (name.equals("specialities")) {
            specialties = (List<Speciality>) newValue;
            cbxSpecialties.removeAllItems();
            cbxSpecialties.addItem(lang.getString("all"));
            specialties.forEach(specialty -> cbxSpecialties.addItem(specialty.getName()));
        }
        
        repaint();
        validate();
    }
    
    @Override
    protected void onEnter() {}
    
    @Override
    protected void onLeave() {}
    
    private List<Appointment> filterAppointments(List<Appointment> source, Function<Appointment, Boolean> filter) {
        List<Appointment> filtered = source.stream()
            .filter(appointment -> filter.apply(appointment))
            .collect(Collectors.toList());
        
        return new ArrayList<>(filtered);
    }
    
    private void checkAllAppointmentFilters() {
        Status status = (Status)cbxStatuses.getSelectedItem();
        String specialty = cbxSpecialties.getItemCount() == 0
            ? lang.getString("all")
            : ((String)cbxSpecialties.getModel().getSelectedItem()).toLowerCase();
        List<Appointment> filtered = new ArrayList<>(appointments);
        
        boolean unselectedStatus = status == null;
        boolean unselectedSpeciality = cbxSpecialties.getSelectedIndex() == 0;

        if (!unselectedStatus) {
            filtered = filterAppointments(filtered, (appointment) -> {
                return appointment.getStatus() == status;
            });
        }

        if (!unselectedSpeciality) {
            filtered = filterAppointments(filtered, (appointment) -> {
                return appointment.getSpeciality().getName().toLowerCase().equals(specialty);
            });
        }

        if (!txtMedicName.getText().trim().isEmpty()) {
            filtered = filterAppointments(filtered, (appointment) -> {
                if (appointment.getMedic() == null) {
                    return false;
                }

                String medicName = appointment.getMedic().getDisplayName().toLowerCase();
                String term = txtMedicName.getText().toLowerCase();
                return medicName.contains(term);
            });
        }

        appointmentsContainer.removeAll();

        filtered.forEach(appointment -> {
            AppointmentItem item = new AppointmentItem(appointment);
            item.onCancel(() -> emit("cancelAppointment", appointment));
            appointmentsContainer.add(item);
        });

        lblTotalAppointments.setText(String.valueOf(filtered.size()));

        repaint();
        revalidate();
    }
    
    private void sortAppointments() {
        boolean descendant = chkSortDescendant.isSelected();
        String type = ((String) cmbSortType.getSelectedItem()).trim().toLowerCase();
        Comparator<Appointment> comparable = null;
        
        switch (type) {
            case "requested date":
                comparable = Comparator.comparing(Appointment::getCreatedAt);
                break;
            case "scheduled date":
                comparable = Comparator.comparing(
                    Appointment::getDate, Comparator.nullsLast(Comparator.reverseOrder()));
                break;
            case "medic name":
                comparable = Comparator.comparing(a -> {
                    String medicName = (a.getMedic() == null ? "" : a.getMedic().getDisplayName());
                    return medicName;
                });
                break;
            default: break;
        }
        
        if (comparable != null) {
            if (descendant) {
                comparable = comparable.reversed();
            }
            
            appointments.sort(comparable);
            appointmentsContainer.removeAll();
            appointments.forEach(appointment -> {
                AppointmentItem item = new AppointmentItem(appointment);
                appointmentsContainer.add(item);
            });
            
            validate();
            repaint();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT
     * modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        pnlEmptyRecords = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        cbxSpecialties = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        cbxStatuses = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        txtMedicName = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        lblTotalAppointments = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        cmbSortType = new javax.swing.JComboBox<>();
        chkSortDescendant = new javax.swing.JCheckBox();
        appointmentsWrapperPanel = new javax.swing.JPanel();
        appointmentsScrollPanel = new javax.swing.JScrollPane();
        appointmentsContainer = new javax.swing.JPanel();
        paginationPanel = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();

        pnlEmptyRecords.setOpaque(false);
        pnlEmptyRecords.setLayout(new java.awt.BorderLayout());

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(153, 153, 153));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("ec/edu/espe/medicbyte/view/Bundle"); // NOI18N
        jLabel11.setText(bundle.getString("FrmAppointments.jLabel11.text")); // NOI18N
        pnlEmptyRecords.add(jLabel11, java.awt.BorderLayout.CENTER);

        setMinimumSize(new java.awt.Dimension(600, 0));
        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(600, 423));

        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.GridBagLayout());

        jPanel2.setOpaque(false);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText(bundle.getString("FrmAppointments.jLabel2.text")); // NOI18N

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setText(bundle.getString("FrmAppointments.jLabel8.text")); // NOI18N

        cbxSpecialties.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxSpecialtiesActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setText(bundle.getString("FrmAppointments.jLabel9.text")); // NOI18N

        cbxStatuses.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxStatusesActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setText(bundle.getString("FrmAppointments.jLabel10.text")); // NOI18N

        txtMedicName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMedicNameActionPerformed(evt);
            }
        });
        txtMedicName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtMedicNameKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbxSpecialties, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbxStatuses, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtMedicName, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addGap(12, 12, 12)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbxStatuses)
                    .addComponent(txtMedicName)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel8)
                        .addComponent(jLabel9)
                        .addComponent(jLabel10))
                    .addComponent(cbxSpecialties)))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.ABOVE_BASELINE;
        gridBagConstraints.weightx = 1.0;
        jPanel1.add(jPanel2, gridBagConstraints);

        jPanel3.setOpaque(false);
        jPanel3.setLayout(new javax.swing.BoxLayout(jPanel3, javax.swing.BoxLayout.Y_AXIS));

        jPanel7.setOpaque(false);

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText(bundle.getString("FrmAppointments.jLabel6.text")); // NOI18N

        jPanel4.setOpaque(false);
        jPanel4.setLayout(new javax.swing.BoxLayout(jPanel4, javax.swing.BoxLayout.X_AXIS));

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel3.setText(bundle.getString("FrmAppointments.jLabel3.text")); // NOI18N
        jLabel3.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 0, 5));
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jPanel4.add(jLabel3);

        lblTotalAppointments.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblTotalAppointments.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblTotalAppointments.setText(bundle.getString("FrmAppointments.lblTotalAppointments.text")); // NOI18N
        lblTotalAppointments.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 5));
        lblTotalAppointments.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jPanel4.add(lblTotalAppointments);

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel7.setText(bundle.getString("FrmAppointments.jLabel7.text")); // NOI18N
        jLabel7.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 5));
        jLabel7.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jPanel4.add(jLabel7);

        cmbSortType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Requested date", "Scheduled date", "Medic name" }));
        cmbSortType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbSortTypeActionPerformed(evt);
            }
        });

        chkSortDescendant.setSelected(true);
        chkSortDescendant.setText(bundle.getString("FrmAppointments.chkSortDescendant.text")); // NOI18N
        chkSortDescendant.setAutoscrolls(true);
        chkSortDescendant.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkSortDescendantActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 613, Short.MAX_VALUE)
                .addGap(6, 6, 6))
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(32, 32, 32)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbSortType, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkSortDescendant)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6)
                        .addComponent(cmbSortType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(chkSortDescendant)))
                .addGap(3, 3, 3)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13))
        );

        jPanel3.add(jPanel7);

        appointmentsWrapperPanel.setBackground(new java.awt.Color(204, 204, 255));
        appointmentsWrapperPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        appointmentsWrapperPanel.setOpaque(false);
        appointmentsWrapperPanel.setLayout(new java.awt.BorderLayout());

        appointmentsScrollPanel.setBackground(new java.awt.Color(204, 204, 255));
        appointmentsScrollPanel.setBorder(null);
        appointmentsScrollPanel.setOpaque(false);

        appointmentsContainer.setBackground(new java.awt.Color(204, 204, 255));
        appointmentsContainer.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 8));
        appointmentsContainer.setOpaque(false);
        appointmentsContainer.setLayout(new javax.swing.BoxLayout(appointmentsContainer, javax.swing.BoxLayout.Y_AXIS));
        appointmentsScrollPanel.setViewportView(appointmentsContainer);

        appointmentsWrapperPanel.add(appointmentsScrollPanel, java.awt.BorderLayout.CENTER);

        jPanel3.add(appointmentsWrapperPanel);

        paginationPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        paginationPanel.setMaximumSize(new java.awt.Dimension(2147483647, 28));
        paginationPanel.setOpaque(false);
        paginationPanel.setLayout(new java.awt.BorderLayout());

        jLabel4.setText(bundle.getString("FrmAppointments.jLabel4.text")); // NOI18N
        paginationPanel.add(jLabel4, java.awt.BorderLayout.CENTER);

        jLabel5.setText(bundle.getString("FrmAppointments.jLabel5.text")); // NOI18N
        jLabel5.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 5));
        paginationPanel.add(jLabel5, java.awt.BorderLayout.LINE_START);

        jPanel3.add(paginationPanel);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_TRAILING;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(jPanel3, gridBagConstraints);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        jLabel1.setText(bundle.getString("FrmAppointments.jLabel1.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jSeparator2))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 373, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cbxSpecialtiesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxSpecialtiesActionPerformed
        checkAllAppointmentFilters();
    }//GEN-LAST:event_cbxSpecialtiesActionPerformed

    private void cbxStatusesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxStatusesActionPerformed
        checkAllAppointmentFilters();
    }//GEN-LAST:event_cbxStatusesActionPerformed

    private void txtMedicNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMedicNameActionPerformed
        checkAllAppointmentFilters();
    }//GEN-LAST:event_txtMedicNameActionPerformed

    private void txtMedicNameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMedicNameKeyReleased
        checkAllAppointmentFilters();
    }//GEN-LAST:event_txtMedicNameKeyReleased

    private void cmbSortTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbSortTypeActionPerformed
        new Thread(() -> sortAppointments()).start();
    }//GEN-LAST:event_cmbSortTypeActionPerformed

    private void chkSortDescendantActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkSortDescendantActionPerformed
        new Thread(() -> sortAppointments()).start();
    }//GEN-LAST:event_chkSortDescendantActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel appointmentsContainer;
    private javax.swing.JScrollPane appointmentsScrollPanel;
    private javax.swing.JPanel appointmentsWrapperPanel;
    private javax.swing.JComboBox<String> cbxSpecialties;
    private javax.swing.JComboBox<ec.edu.espe.medicbyte.model.Appointment.Status> cbxStatuses;
    private javax.swing.JCheckBox chkSortDescendant;
    private javax.swing.JComboBox<String> cmbSortType;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel lblTotalAppointments;
    private javax.swing.JPanel paginationPanel;
    private javax.swing.JPanel pnlEmptyRecords;
    private javax.swing.JTextField txtMedicName;
    // End of variables declaration//GEN-END:variables
}
