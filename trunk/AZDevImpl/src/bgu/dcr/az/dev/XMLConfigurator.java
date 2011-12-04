/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bgu.dcr.az.dev;

import bc.dsl.ReflectionDSL;
import bgu.dcr.az.api.exp.InvalidValueException;
import bgu.dcr.az.api.infra.Experiment;
import bgu.dcr.az.api.infra.Round;
import bgu.dcr.az.api.infra.VariableMetadata;
import bgu.dcr.az.impl.Registary;
import bgu.dcr.az.impl.infra.ExperimentImpl;
import java.io.File;
import static bc.dsl.XNavDSL.*;
import bgu.dcr.az.api.infra.Configureable;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import nu.xom.Attribute;
import nu.xom.Element;
import nu.xom.ParsingException;

/**
 *
 * @author bennyl
 */
public class XMLConfigurator {

    public static void write(Configureable conf, PrintWriter pw) {
        String confName = Registary.UNIT.getEntityName(conf);
        Element e = new Element(confName);
        write(conf, e);
        
        pw.append(e.toXML());
    }

    private static void write(Configureable conf, Element root) {
        for (VariableMetadata v : VariableMetadata.scan(conf)) {
            root.addAttribute(new Attribute(v.getName(), v.getCurrentValue().toString()));
        }
        
        for (Configureable c : conf.getConfiguredChilds()){
            String confName = Registary.UNIT.getEntityName(c);
            Element e = new Element(confName);
            write(c, e);
            root.appendChild(e);
        }
    }

    public static Experiment read(File from) throws IOException, InstantiationException, IllegalAccessException {
        try {
            Experiment exp = new ExperimentImpl();
            Element root = xload(from).getRootElement();

            configure(exp, root);

            return exp;

        } catch (ParsingException ex) {
            Logger.getLogger(XMLConfigurator.class.getName()).log(Level.SEVERE, null, ex);
            throw new IOException("cannot parse file", ex);
        }
    }

    private static void configure(Configureable c, Element root) throws InstantiationException, IllegalAccessException, InvalidValueException {
        Configureable cc;
        for (Element child : childs(root)) {
            String name = child.getLocalName();
            Class cls = Registary.UNIT.getXMLEntity(name);
            if (cls == null) {
                throw new InvalidValueException("cannot parse " + name + " xml entity: no entity with that name on the registery");
            } else if (!c.canAccept(cls)) {
                throw new InvalidValueException("element '" + root.getLocalName() + "' cannot contain child '" + name + "'");
            } else {
                cc = (Configureable) cls.newInstance();
                configure(cc, child);
                c.addSubConfiguration(cc);
            }
        }
        HashMap<String, Object> conf = new HashMap<String, Object>();
        VariableMetadata[] evar = c.provideExpectedVariables();
        Map<String, VariableMetadata> varmap = new HashMap<String, VariableMetadata>();

        for (VariableMetadata v : evar) {
            varmap.put(v.getName(), v);
        }
        VariableMetadata var;

        for (Entry<String, String> a : attributes(root).entrySet()) {
            if (varmap.containsKey(a.getKey())) {
                var = varmap.get(a.getKey());
                Object value = ReflectionDSL.valueOf(a.getValue(), var.getType());
                conf.put(a.getKey(), value);
            } else {
                System.out.println("found attribute '" + a.getKey() + "' in element '" + root.getLocalName() + "' but this element not expecting this attribute - ignoring.");
            }
        }
        c.configure(conf);
    }

    public static void main(String[] args) throws Exception {
        Experiment exp = read(new File("exp.xml"));
        for (Round r : exp.getRounds()) {
            System.out.println(r.toString());
        }
    }
}