/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bgu.csp.az.dev;

import bam.utils.FileUtils;
import bc.dsl.SwingDSL;
import bgu.csp.az.api.infra.EventPipe;
import bgu.csp.az.api.infra.Execution;
import bgu.csp.az.api.pgen.Problem;
import bgu.csp.az.api.tools.Assignment;
import bgu.csp.az.dev.pui.UIController;
import bgu.csp.az.impl.AlgorithmMetadata;
import com.j256.ormlite.logger.LocalLog;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import nu.xom.ParsingException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

/**
 *
 * @author bennyl
 */
public class Agent0Tester  {

    @Option(name = "--es", usage = "opening a [pooling] server that will transmit events in json form.")
    boolean useEventServer;
    @Option(name = "-f", usage = "the file contain the metadata of the test.", required = true)
    File test;
    @Option(name = "--sfp", usage = "suppling directory to save failed problems in", required = false)
    File failedProblemsDir;
    @Option(name = "--gui", usage = "attach gui to view execution status", required = false)
    boolean useGui;
    @Option(name = "--emode", usage = "execution mode (run/debug) default to 'run', debug automaticly use gui and will fail"
    + " if --sfp or --prob option was not given", required = false)
    String executionMode = "run";

    public void go() throws ParsingException, IOException, MalformedURLException, ClassNotFoundException {
        System.setProperty(LocalLog.LOCAL_LOG_FILE_PROPERTY, "log.out");

        ExecutionUnit.UNIT.execute(test, true);
//        TestExpirement expirament = null;

        //TYPE OF EXECUTION
//        if (executionMode.equals("run")) {
//            expirament = new TestExpirement(xload(test).getRootElement(), loadAlgorithm());
//            //FileUtils.delete(new File(TestExpirement.TEMP_SCENARIO_LOG_DB_PATH));
//        } else {
//            if (failedProblemsDir == null && prob == null) {
//                System.out.println("Cannot use debug mode without option --sfp or --prob given");
//                return;
//            }
//            //debug is defaults to use gui
//            useGui = true;
//
//            Problem selectedProblem = (prob == null ? null : FileUtils.unPersistObject(prob, Problem.class));
//
//            if (selectedProblem == null) {
//                SwingDSL.configureUI();
//                ProblemSelectionModel selectPModel = selectProblemByGUI();
//
//                if (selectPModel.isDebugFullTest()) {
//                    expirament = new TestExpirement(xload(test).getRootElement(), loadAlgorithm());
//                } else {
//                    selectedProblem = selectPModel.getSelectedProblemFile() == null ? null : selectPModel.getSelectedProblem();
//                }
//            }
//            if (selectedProblem != null) {
//                expirament = new TestExpirement(selectedProblem, loadAlgorithm());
//            }
//        }
//
//        if (expirament == null) {
//            System.out.println("User Termination.");
//            System.exit(0);
//        }
//
//        expirament.addListener(this);
//        //TEST GUI USAGE
//        EventDistributer edist = null;
//
//        if (useGui) {
//            final EventPipe pipe = new EventPipe();
//            expirament.setEventPipe(pipe);
//            edist = new EventDistributer(pipe);
//            edist.start();
//            new UIController().go(expirament);
//        }
//
//        if (failedProblemsDir != null) {
//            expirament.setFailedProblemsDir(failedProblemsDir);
//        }
//
//        expirament.run();
    }

//    @Override
//    public void onExecutionCrushed(Execution ex, Exception exc) {
//        handleBadEnding(ex);
//    }
//
//    @Override
//    public void onExecutionEndedWithWrongResult(Execution execution, Assignment wrong, Assignment right) {
//        handleBadEnding(execution);
//    }
//
//    private ProblemSelectionModel selectProblemByGUI() {
//        ProblemSelectionModel selectPModel = new ProblemSelectionModel(failedProblemsDir.getAbsolutePath());
//        ProblemSelectionDialog diag = new ProblemSelectionDialog(null, true);
//        diag.setModel(selectPModel);
//        diag.setVisible(true);
//        return selectPModel;
//    }
//
//    private AlgorithmMetadata loadAlgorithm() throws MalformedURLException, ClassNotFoundException {
//        ClassLoader ldr = ClassLoader.getSystemClassLoader();
//        if (agentClassPath != null) {
//            ldr = new URLClassLoader(new URL[]{new File(agentClassPath).toURI().toURL()});
//        }
//
//        Class agt = ldr.loadClass(agentClass);
//        return new AlgorithmMetadata(agt);
//    }

    public static void main(String[] args) {
        Agent0Tester tester = new Agent0Tester();
        CmdLineParser parser = new CmdLineParser(tester);
        try {
            parser.parseArgument(args);
            tester.go();
        } catch (Exception ex) {
            System.err.print("Error: " + ex.getMessage() + "\n\n");

            System.err.print("usage: ");
            parser.setUsageWidth(80);
            parser.printSingleLineUsage(System.err);
            System.err.print("\nDescription:\n");
            parser.printUsage(System.err);

            ex.printStackTrace();

        }
    }

//    private void handleBadEnding(Execution ex) {
//        //SAVE THE BAD PROBLEM AND STOP ...
//        Problem p = ex.getGlobalProblem();
//        SimpleDateFormat sdf = new SimpleDateFormat("dd'-'MM'-'yyyy' 'HH'.'mm'.'ss");
//        String fileName = sdf.format(new Date()) + " " + p.getNumberOfVariables() + "X" + p.getDomainSize(0) + ".prob";
//        System.err.println("Trying to save problematic probem to file " + fileName);
//        try {
//            FileUtils.persistObject(failedProblemsDir, fileName, p);
//            System.err.println("Problem saved successfully - you can now debug it.");
//        } catch (IOException ex1) {
//            Logger.getLogger(Agent0Tester.class.getName()).log(Level.SEVERE, "while trying to save failed problem.", ex1);
//        }
//    }
}