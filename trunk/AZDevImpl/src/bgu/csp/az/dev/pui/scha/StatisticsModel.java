/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bgu.csp.az.dev.pui.scha;

import bc.dsl.SwingDSL;
import bc.swing.models.chart.ChartModel;
import bc.swing.models.GenericTreeModel.ListNode;
import bc.swing.models.chart.AreaChartModel;
import bc.swing.pfrm.BaseParamModel;
import bc.swing.pfrm.DeltaHint;
import bc.swing.pfrm.FieldParamModel.ChangeListener;
import bc.swing.pfrm.Model;
import bc.swing.pfrm.Page;
import bc.swing.pfrm.ano.PageDef;
import bc.swing.pfrm.ano.Param;
import bc.swing.pfrm.viewtypes.ParamType;
import bgu.csp.az.api.Problem;
import bgu.csp.az.api.Statistic;
import bgu.csp.az.api.tools.Assignment;
import bgu.csp.az.dev.Round;
import bgu.csp.az.dev.frm.TestExecution;
import bgu.csp.az.dev.frm.TestExpirement;
import bgu.csp.az.dev.pui.sgrp.StatisticsView;
import java.util.LinkedList;
import javax.swing.ImageIcon;

/**
 *
 * @author bennyl
 */
@PageDef(icon = "page-execution-statistics", name = "Execution statistics", layout = StatisticsView.class)
public class StatisticsModel extends Model implements TestExpirement.Listener {

    public static final String AVAILABLE_GRAPHS_PARAM = "Available Graphs";
    private StatisticNode currentRoundStatistics;
    private ListNode roundStatistics = new ListNode("Rounds", null);
    private boolean firstStatisticsStractureSynced = false;
    private Model master;

    public StatisticsModel() {
        master = ChartModelProvider.I.provide(null);
    }

    @Param(name = AVAILABLE_GRAPHS_PARAM, type = ParamType.TREE, role = StatisticsView.GRAPHS_TREE_ROLE)
    public ListNode getRoundStatistics() {
        return roundStatistics;
    }

    @Param(name = "Master", type = ParamType.PAGE, role = StatisticsView.MASTER_GRAPH_ROLE)
    public Model getMaster() {
        return master;
    }

    @Override
    public void whenPageCreated(Page page) {
        page.getParam(AVAILABLE_GRAPHS_PARAM).addSelectionListner(new ChangeListener() {

            @Override
            public void onChange(BaseParamModel source, Object newValue, Object deltaHint) {
                if (newValue instanceof StatisticNode) {
                    final StatisticNode stData = (StatisticNode)newValue;
                    master = ChartModelProvider.I.provide(stData);
                } else {
                    master = ChartModelProvider.I.provide(null);
                }
                syncToView("Master");
            }
        });
    }

    @Override
    public ImageIcon provideParamValueIcon(String param, Object value) {
        if (param.equals(AVAILABLE_GRAPHS_PARAM)) {
            if (value instanceof Round) {
                return SwingDSL.resIcon("round");
            }
        }
        return super.provideParamValueIcon(param, value);
    }

    @Override
    public void onExpirementEndedSuccessfully() {
    }

    @Override
    public void onExecutionEndedWithWrongResult(TestExecution execution, Assignment wrong, Assignment right) {
    }

    @Override
    public void onExecutionCrushed(TestExecution ex, Exception exc) {
    }

    @Override
    public void onExpirementStarted() {
    }

    @Override
    public void onNewProblemExecuted(Problem p) {
    }

    @Override
    public void onNewRoundStarted(Round r) {
        currentRoundStatistics = new StatisticNode(r.toString(), roundStatistics);
        roundStatistics.getChildren().add(currentRoundStatistics);
        syncToView(AVAILABLE_GRAPHS_PARAM, DeltaHint.oneItemAdded(currentRoundStatistics));
        this.firstStatisticsStractureSynced = false;
    }

    @Override
    public void onStatisticsRetrived(Statistic root) {
        currentRoundStatistics.addStatisticRoot(root);
        if (!this.firstStatisticsStractureSynced) {
            syncToView(AVAILABLE_GRAPHS_PARAM, DeltaHint.oneItemChanged(currentRoundStatistics));
            this.firstStatisticsStractureSynced = true;
        }
    }
}