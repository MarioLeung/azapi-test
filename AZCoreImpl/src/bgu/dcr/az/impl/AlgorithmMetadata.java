package bgu.dcr.az.impl;

import bgu.dcr.az.api.Agent;
import bgu.dcr.az.api.Agent;
import bgu.dcr.az.api.ProblemType;
import bgu.dcr.az.api.ProblemType;
import bgu.dcr.az.api.SearchType;
import bgu.dcr.az.api.SearchType;
import bgu.dcr.az.api.ano.Register;
import bgu.dcr.az.api.ano.Variable;
import bgu.dcr.az.api.exp.InvalidValueException;
import bgu.dcr.az.api.infra.Configurable;
import bgu.dcr.az.impl.Registery;
import bgu.dcr.az.impl.infra.AbstractConfigurable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * this is a metadata of an algorithm used to create and configure an agent, 
 * the metadata itself read the Algorithm Annotation from the agent and upon agent 
 * creation it configurs with its given configuration.
 * @author bennyl
 */
@Register(name = "algorithm", display="Algorithm")
public class AlgorithmMetadata extends AbstractConfigurable {

    @Variable(name = "name", description = "the name of the algorithm")
    private String name; //the algorithm name
    private Class<? extends Agent> agentClass; //the class of the agent that implements this algorithm
    private ProblemType problemType;
    private boolean useIdleDetector;
    private SearchType searchType;
    private Map<String, Object> agentConfiguration = new HashMap<String, Object>();

    /**
     * constract an algorithm metadata from an agent class 
     * the agent class must be annotated by @Algorithm annotation.
     * @param agentClass
     */
    public AlgorithmMetadata(Class<? extends Agent> agentClass) {
        initialize(agentClass);
    }

    public AlgorithmMetadata() {
    }
    
    private void initialize(Class<? extends Agent> agentClass) throws InvalidValueException {
        bgu.dcr.az.api.ano.Algorithm a = agentClass.getAnnotation(bgu.dcr.az.api.ano.Algorithm.class);
        if (a == null) {
            throw new InvalidValueException("no algorithm annotation used on the given agent class");
        }

        this.name = a.name();
        this.agentClass = agentClass;
        this.problemType = a.problemType();
        this.useIdleDetector = a.useIdleDetector();
        this.searchType = a.searchType();
    }

    /**
     * @return the defined search type 
     */
    public SearchType getSearchType() {
        return searchType;
    }

    /**
     * @return the algorithm name
     */
    public String getName() {
        return name;
    }

    /**
     * @return true if the algorithm implementer request the platform to use idle detection to close its agents
     */
    public boolean isUseIdleDetector() {
        return useIdleDetector;
    }

    /**
     * @return the class of the agent implementation that can run this algorithm
     */
    public Class<? extends Agent> getAgentClass() {
        return agentClass;
    }

    /**
     * @return the problem type that the algorithm implementer declare its algorithm to solve
     */
    public ProblemType getProblemType() {
        return problemType;
    }

    @Override
    protected void configurationDone() {
        Class<? extends Agent> a = Registery.UNIT.getAgentByAlgorithmName(name);
        if (a == null) {
            throw new InvalidValueException("cannot find agent with the given algorithm name: '" + name + "'");
        }
        initialize(a);
    }

    @Override
    public List<Class<? extends Configurable>> provideExpectedSubConfigurations() {
        List<Class<? extends Configurable>> ret = new LinkedList<Class<? extends Configurable>>();
        ret.add(VarAssign.class);
        return ret;
    }

    @Override
    public boolean canAccept(Class<? extends Configurable> cls) {
        return cls == VarAssign.class;
    }

    @Override
    public void addSubConfiguration(Configurable sub) throws InvalidValueException {
        if (canAccept(sub.getClass())) {
            VarAssign va = (VarAssign) sub;
            agentConfiguration.put(va.varName, va.value);
        } else {
            throw new InvalidValueException("can only accept VarAssign");
        }
    }

    public Agent generateAgent() throws InstantiationException, IllegalAccessException {
        Agent agent = agentClass.newInstance();
        Agent.PlatformOps pops = Agent.PlatformOperationsExtractor.extract(agent);
        pops.configure(agentConfiguration);
        return agent;
    }

}