/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bgu.csp.az.dev;

import bgu.csp.az.dev.frm.TestExpirement;
import java.io.File;
import static bc.dsl.XNavDSL.*;
import bgu.csp.az.api.Agent;
import bgu.csp.az.api.AlgorithmMetadata;
import bgu.csp.az.dev.debug.DSAAgent;
import bgu.csp.az.dev.debug.NestedCheckAgent;
import com.j256.ormlite.logger.LocalLog;
import java.io.IOException;
import java.net.MalformedURLException;
import nu.xom.ParsingException;

/**
 *
 * @author bennyl
 */
public class DebugMain {

    public static final Class<? extends Agent> agentToRun = DSAAgent.class;

    public static void main(String[] args) throws ParsingException, IOException, MalformedURLException, ClassNotFoundException {

        TestExpirement expirament = null;

        //TYPE OF EXECUTION
        expirament = new TestExpirement(xload(new File("test.xml")).getRootElement(), loadAlgorithm());
        expirament.run();
    }

    private static AlgorithmMetadata loadAlgorithm() throws MalformedURLException, ClassNotFoundException {
        return new AlgorithmMetadata(agentToRun);
    }
}