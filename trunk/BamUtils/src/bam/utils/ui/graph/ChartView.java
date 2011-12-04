/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ChartView.java
 *
 * Created on 26/08/2011, 17:00:26
 */
package bam.utils.ui.graph;

import java.awt.BorderLayout;
import java.awt.Color;
import java.text.SimpleDateFormat;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.renderer.xy.XYItemRenderer;

/**
 *
 * @author bennyl
 */
public class ChartView extends javax.swing.JPanel {

    private JFreeChart chart;
    private ChartPanel chartPanel;

    /** Creates new form ChartView */
    public ChartView() {
        initComponents();
    }

    public void setModel(MultiBarChartModel model) {
        loadChart(createMultiBarChart(model), true);
    }

    public void setModel(TimeBasedAreaChartModel model) {
        loadChart(createTimedAreaChart(model), true);
    }

    private void loadChart(JFreeChart chart, boolean useBasicConf) {
        this.removeAll();
        if (useBasicConf) {
            performBasicChartConfiguration(chart);
        }
        setLayout(new BorderLayout());
        chartPanel = new ChartPanel(chart);
        add(chartPanel);
    }

    private JFreeChart createMultiBarChart(MultiBarChartModel model) {
        // create the chart...
        chart = ChartFactory.createBarChart(
                model.getChartTitle(), // chart title
                model.getxAxeTitle(), // domain axis label
                model.getyAxeTitle(), // range axis label
                model.getChartModel(), // data
                PlotOrientation.VERTICAL, // orientation
                model.isHasLegend(), // include legend
                true, // tooltips?
                false // URLs?
                );


        // get a reference to the plot for further customisation...
        CategoryPlot plot = chart.getCategoryPlot();

        // set the range axis to display integers only...
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());


        BarRenderer barrenderer = (BarRenderer) plot.getRenderer();
        barrenderer.setDrawBarOutline(true);
        barrenderer.setBarPainter(new StandardBarPainter());


        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0));
        // OPTIONAL CUSTOMISATION COMPLETED.

        return chart;

    }

    private JFreeChart createTimedAreaChart(TimeBasedAreaChartModel model) {
        chart = ChartFactory.createXYAreaChart(
                model.getChartTitle(),
                model.getxAxeTitle(), model.getyAxeTitle(),
                model.getChartModel(),
                PlotOrientation.VERTICAL,
                model.isHasLegend(), // legend
                true, // tool tips
                false // URLs
                );
        XYPlot plot = chart.getXYPlot();

        DateAxis domainAxis = new DateAxis(model.getxAxeTitle());
        domainAxis.setDateFormatOverride(model.getDateFormatter());
        domainAxis.setLowerMargin(0.0);
        domainAxis.setUpperMargin(0.0);
        plot.setDomainAxis(domainAxis);
        plot.setForegroundAlpha(0.5f);

        return chart;
    }

    public void performBasicChartConfiguration(JFreeChart chart) {
        chart.setBackgroundPaint(getBackground());

        Plot plot = chart.getPlot();
        plot.setBackgroundPaint(Color.WHITE);
        if (plot instanceof CategoryPlot) {
            CategoryPlot cplot = chart.getCategoryPlot();
            cplot.setDomainGridlinePaint(new Color(200, 200, 200));
            cplot.setDomainGridlinesVisible(true);
            cplot.setRangeGridlinePaint(new Color(200, 200, 200));
            cplot.setRangeGridlinesVisible(true);
        } else if (plot instanceof XYPlot) {
            XYPlot xyplot = chart.getXYPlot();
            xyplot.setDomainGridlinePaint(new Color(200, 200, 200));
            xyplot.setDomainGridlinesVisible(true);
            xyplot.setRangeGridlinePaint(new Color(200, 200, 200));
            xyplot.setRangeGridlinesVisible(true);
        }

    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}