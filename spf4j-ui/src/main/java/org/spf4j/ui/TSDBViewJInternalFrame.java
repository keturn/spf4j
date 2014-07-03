/*
 * Copyright (c) 2001, Zoltan Farkas All Rights Reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
//CHECKSTYLE:OFF
package org.spf4j.ui;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.spf4j.base.Pair;
import org.spf4j.perf.impl.mdb.tsdb.TSDBMeasurementDatabase;
import org.spf4j.perf.tsdb.TSTable;
import org.spf4j.perf.tsdb.TimeSeriesDatabase;

/**
 *
 * @author zoly
 */
@edu.umd.cs.findbugs.annotations.SuppressWarnings({"FCBL_FIELD_COULD_BE_LOCAL"})
public class TSDBViewJInternalFrame extends javax.swing.JInternalFrame {

    private final TimeSeriesDatabase tsDb;

    /**
     * Creates new form TSDBViewJInternalFrame
     */
    public TSDBViewJInternalFrame(final String databaseFile) throws IOException {
        super(databaseFile);
        initComponents();
        tsDb = new TimeSeriesDatabase(databaseFile, null);
        Collection<TSTable> columnsInfo = tsDb.getTSTables();
        Map<String, DefaultMutableTreeNode> gNodes = new HashMap<String, DefaultMutableTreeNode>();
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(databaseFile);
        long startDate = System.currentTimeMillis();
        for (TSTable info : columnsInfo) {
            String groupName = info.getTableName();
            long tableStart = tsDb.readStartDate(groupName);
            if (tableStart < startDate) {
                startDate = tableStart;
            }
            Pair<String, String> pair = Pair.from(groupName);
            if (pair == null) {
                DefaultMutableTreeNode child = new DefaultMutableTreeNode(groupName);
                for (String colName : info.getColumnNames()) {
                    child.add(new DefaultMutableTreeNode(colName));
                }
                root.add(child);
            } else {
                groupName = pair.getFirst();
                DefaultMutableTreeNode gNode = gNodes.get(groupName);
                if (gNode == null) {
                    gNode = new DefaultMutableTreeNode(groupName);
                    gNodes.put(groupName, gNode);
                    root.add(gNode);
                }
                DefaultMutableTreeNode child = new DefaultMutableTreeNode(pair.getSecond());
                for (String colName : info.getColumnNames()) {
                    child.add(new DefaultMutableTreeNode(colName));
                }
                gNode.add(child);
            }
        }
        measurementTree.setModel(new DefaultTreeModel(root));
        measurementTree.setVisible(true);
        this.startDate.setValue(new Date(startDate));
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        rightPanel = new javax.swing.JPanel();
        mainSplitPannel = new javax.swing.JSplitPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        measurementTree = new javax.swing.JTree();
        chartPannel = new javax.swing.JScrollPane();
        jToolBar1 = new javax.swing.JToolBar();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        startDate = new javax.swing.JSpinner();
        endDate = new javax.swing.JSpinner();

        org.jdesktop.layout.GroupLayout rightPanelLayout = new org.jdesktop.layout.GroupLayout(rightPanel);
        rightPanel.setLayout(rightPanelLayout);
        rightPanelLayout.setHorizontalGroup(
            rightPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 448, Short.MAX_VALUE)
        );
        rightPanelLayout.setVerticalGroup(
            rightPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 306, Short.MAX_VALUE)
        );

        setClosable(true);
        setMaximizable(true);
        setResizable(true);

        mainSplitPannel.setDividerSize(5);
        mainSplitPannel.setPreferredSize(new java.awt.Dimension(600, 500));

        measurementTree.setAutoscrolls(true);
        jScrollPane1.setViewportView(measurementTree);

        org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 213, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel2Layout.createSequentialGroup()
                .add(0, 0, Short.MAX_VALUE)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 942, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );

        mainSplitPannel.setLeftComponent(jPanel2);
        mainSplitPannel.setRightComponent(chartPannel);

        jToolBar1.setRollover(true);

        jButton1.setText("Plot");
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton1);

        jButton2.setText("Export");
        jButton2.setFocusable(false);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton2);

        startDate.setModel(new javax.swing.SpinnerDateModel());
        startDate.setEditor(new javax.swing.JSpinner.DateEditor(startDate, "yyyy-MM-dd HH:mm:ss"));
        startDate.setMinimumSize(new java.awt.Dimension(200, 28));
        startDate.setName(""); // NOI18N
        jToolBar1.add(startDate);

        endDate.setModel(new javax.swing.SpinnerDateModel());
        endDate.setEditor(new javax.swing.JSpinner.DateEditor(endDate, "yyyy-MM-dd HH:mm:ss"));
        jToolBar1.add(endDate);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(jToolBar1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 479, Short.MAX_VALUE)
                        .add(236, 236, 236))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                        .add(mainSplitPannel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(jToolBar1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(mainSplitPannel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 667, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    @edu.umd.cs.findbugs.annotations.SuppressWarnings("UP_UNUSED_PARAMETER")
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        TreePath[] selectionPaths = measurementTree.getSelectionPaths();
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        chartPannel.setViewportView(content);
        try {
            List<String> selectedTables = getSelectedTables(selectionPaths);
            for(String tableName : selectedTables) {
                addChartToPanel(tableName, content);
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        chartPannel.repaint();
    }//GEN-LAST:event_jButton1ActionPerformed

        @edu.umd.cs.findbugs.annotations.SuppressWarnings("UP_UNUSED_PARAMETER")
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        TreePath[] selectionPaths = measurementTree.getSelectionPaths();
        List<String> selectedTables = getSelectedTables(selectionPaths);
        if (!selectedTables.isEmpty()) {
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogType(JFileChooser.SAVE_DIALOG);
            int returnVal = chooser.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                try {
                    tsDb.writeCsvTables(selectedTables, file);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }//GEN-LAST:event_jButton2ActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane chartPannel;
    private javax.swing.JSpinner endDate;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JSplitPane mainSplitPannel;
    private javax.swing.JTree measurementTree;
    private javax.swing.JPanel rightPanel;
    private javax.swing.JSpinner startDate;
    // End of variables declaration//GEN-END:variables

    @edu.umd.cs.findbugs.annotations.SuppressWarnings("CLI_CONSTANT_LIST_INDEX")
    private static List<String> getSelectedTables(@Nullable final TreePath[] selectionPaths) {
        if (selectionPaths == null) {
            return Collections.EMPTY_LIST;
        }
        List<String> result = new ArrayList<String>();
        for (TreePath path : selectionPaths) {
            Object[] pathArr = path.getPath();
            if (pathArr.length < 2) {
                continue;
            }
            DefaultMutableTreeNode colNode = (DefaultMutableTreeNode) pathArr[1];
            int depth = colNode.getDepth();
            String tableName;
            if (depth == 1) {
                result.add((String) colNode.getUserObject());
            } else {
                Enumeration childEnum = colNode.children();
                while (childEnum.hasMoreElements()) {
                    DefaultMutableTreeNode child = (DefaultMutableTreeNode) childEnum.nextElement();
                    tableName =
                            Pair.of((String) colNode.getUserObject(), (String) child.getUserObject()).toString();
                    result.add(tableName);
                }
            }
        }
        return result;
    }

    private void addChartToPanel(final String tableName, final JPanel content) throws IOException {
        TSTable info = tsDb.getTSTable(tableName);
         long startTime = ((Date) startDate.getValue()).getTime();
         long endTime = ((Date) endDate.getValue()).getTime();
        if (TSDBMeasurementDatabase.canGenerateHeatChart(info)) {
            JFreeChart chart = tsDb.createHeatJFreeChart(info.getTableName(),
                    startTime, endTime);
            ChartPanel pannel = new ChartPanel(chart);
            pannel.setPreferredSize(new Dimension(600, 1024));
            pannel.setDomainZoomable(false);
            pannel.setMouseZoomable(false);
            pannel.setRangeZoomable(false);
            pannel.setZoomAroundAnchor(false);
            pannel.setZoomInFactor(1);
            pannel.setZoomOutFactor(1);
            content.add(pannel);
        }
        if (TSDBMeasurementDatabase.canGenerateMinMaxAvgCount(info)) {
            JFreeChart chart = tsDb.createMinMaxAvgJFreeChart(info.getTableName(),
                    startTime, endTime);
            ChartPanel pannel = new ChartPanel(chart);
            pannel.setPreferredSize(new Dimension(600, 1024));
            content.add(pannel);

        }
        if (TSDBMeasurementDatabase.canGenerateCount(info)) {
            JFreeChart chart = tsDb.createCountJFreeChart(info.getTableName(),
                    startTime, endTime);
            ChartPanel pannel = new ChartPanel(chart);
            pannel.setPreferredSize(new Dimension(600, 1024));
            content.add(pannel);
        } else {
            List<JFreeChart> createJFreeCharts = tsDb.createJFreeCharts(info.getTableName(), startTime, endTime);
            for (JFreeChart chart : createJFreeCharts) {
                ChartPanel pannel = new ChartPanel(chart);
                pannel.setPreferredSize(new Dimension(600, 1024));
                content.add(pannel);
            }
        }
    }
}
