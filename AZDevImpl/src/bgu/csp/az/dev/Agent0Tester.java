/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bgu.csp.az.dev;

import bc.dsl.SwingDSL;
import bgu.csp.az.api.tools.Assignment;
import bgu.csp.az.dev.frm.TestExecution;
import bgu.csp.az.dev.frm.TestExpirement;
import java.io.File;
import static bc.dsl.XNavDSL.*;
import bc.utils.FileUtils;
import bgu.csp.az.api.Algorithm;
import bgu.csp.az.api.Problem;
import bgu.csp.az.api.infra.EventPipe;
import bgu.csp.az.dev.pui.UIController;
import bgu.csp.az.dev.ui.dialogs.ProblemSelectionDialog;
import bgu.csp.az.dev.ui.dialogs.ProblemSelectionModel;
import com.j256.ormlite.logger.LocalLog;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import nu.xom.ParsingException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

/**
 *
 * @author bennyl
 */
public class Agent0Tester extends TestExpirement.Handler{

    public static TestExpirement exp;
    @Option(name = "--es", usage = "opening a [pooling] server that will transmit events in json form.")
    boolean useEventServer;
    @Option(name = "-f", usage = "the file contain the metadata of the test.", required = true)
    File test;
    @Option(name = "-a", usage = "the agent class to test (fully quilified!)", required = true)
    String agentClass;
    @Option(name = "--cp", usage = "supplying optional class path to seek agent classes in", required = false)
    String agentClassPath;
    @Option(name = "--sfp", usage = "suppling directory to save failed problems in", required = false)
    File failedProblemsDir;
    @Option(name = "--gui", usage = "attach gui to view execution status", required = false)
    boolean useGui;
    @Option(name = "--emode", usage = "execution mode (run/debug) default to 'run', debug automaticly use gui and will fail"
    + " if --sfp or --prob option was not given", required = false)
    String executionMode = "run";
    @Option(name = "--prob", usage = "if this execution in debug mode (set by --emode debug) and this option was given "
    + "the problem file will get executed without prompt for selecting problem")
    File prob;

    public void go() throws ParsingException, IOException, MalformedURLException, ClassNotFoundException {
        System.setProperty(LocalLog.LOCAL_LOG_FILE_PROPERTY, "log.out");
        
        TestExpirement expirament = null;

        //TYPE OF EXECUTION
        if (executionMode.equals("run")) {
            expirament = new TestExpirement(xload(test).getRootElement(), loadAlgorithm());
            FileUtils.delete(new File(TestExpirement.TEMP_SCENARIO_LOG_DB_PATH));
        } else {
            if (failedProblemsDir == null && prob == null) {
                System.out.println("Cannot use debug mode without option --sfp or --prob given");
                return;
            }
            //debug is defaults to use gui
            useGui = true;

            Problem selectedProblem = (prob == null ? null : FileUtils.unPersistObject(prob, Problem.class));

            if (selectedProblem == null) {
                SwingDSL.configureUI();
                ProblemSelectionModel selectPModel = selectProblemByGUI();
                selectedProblem = selectPModel.getSelectedProblemFile() == null ? null : selectPModel.getSelectedProblem();
            }
            if (selectedProblem != null) {
                expirament = new TestExpirement(selectedProblem, loadAlgorithm());
            } else {
                System.out.println("User Termination.");
                System.exit(0);
            }
        }

        expirament.addListener(this);
        //TEST GUI USAGE
        EventDistributer edist = null;

        if (useGui) {
            final EventPipe pipe = new EventPipe();
            expirament.setEventPipe(pipe);
            edist = new EventDistributer(pipe);
            edist.start();
            new UIController().go(expirament);
        }

        if (failedProblemsDir != null) {
            expirament.setFailedProblemsDir(failedProblemsDir);
        }

        expirament.run();
    }

    @Override
    public void onExecutionCrushed(TestExecution ex, Exception exc) {
        handleBadEnding(ex);
    }

    @Override
    public void onExecutionEndedWithWrongResult(TestExecution execution, Assignment wrong, Assignment right) {
        handleBadEnding(execution);
    }
    
    private ProblemSelectionModel selectProblemByGUI() {
        ProblemSelectionModel selectPModel = new ProblemSelectionModel(failedProblemsDir.getAbsolutePath());
        ProblemSelectionDialog diag = new ProblemSelectionDialog(null, true);
        diag.setModel(selectPModel);
        diag.setVisible(true);
        return selectPModel;
    }

    private Algorithm loadAlgorithm() throws MalformedURLException, ClassNotFoundException {
        ClassLoader ldr = ClassLoader.getSystemClassLoader();
        if (agentClassPath != null) {
            ldr = new URLClassLoader(new URL[]{new File(agentClassPath).toURI().toURL()});
        }

        Class agt = ldr.loadClass(agentClass);
        return new Algorithm(agt);
    }

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

    private void handleBadEnding(TestExecution ex) {
        FileUtils.delete(new File(TestExpirement.TEMP_SCENARIO_LOG_DB_PATH));
        ex.getLogger().exportToDB(TestExpirement.TEMP_SCENARIO_LOG_DB_PATH);
    }
}