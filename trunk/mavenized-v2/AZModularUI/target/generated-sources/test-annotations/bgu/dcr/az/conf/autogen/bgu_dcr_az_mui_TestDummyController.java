

package bgu.dcr.az.conf.autogen;

import bgu.dcr.az.conf.api.JavaDocInfo;
import bgu.dcr.az.conf.utils.JavaDocParser;
import bgu.dcr.az.mui.BaseController;
import bgu.dcr.az.mui.Controller;
import bgu.dcr.az.mui.ControllerManipulator;
import bgu.dcr.az.mui.ControllerRegistery;
import bgu.dcr.az.conf.registery.RegistrationMarker;

public class bgu_dcr_az_mui_TestDummyController implements ControllerManipulator, RegistrationMarker{
    
    //store configured class javadoc
    public static JavaDocInfo DOC = JavaDocParser.parse("@author bennyl");

    //registration on class loading time
    static {
        ControllerRegistery.get().register(new bgu_dcr_az_mui_TestDummyController(), "test.dummy");
    }

    @Override
    public boolean accept(BaseController container) {
        return true;
    }

    @Override
    public Controller create(BaseController c) {
        Controller cc = bgu.dcr.az.mui.TestDummyController.create(bgu.dcr.az.mui.TestDummyController.class);
        if (c != null) c.manage(cc);
        return cc;
    }

    @Override
    public JavaDocInfo doc() {
        return DOC;
    }
    
    @Override
    public String toString() {
        return "bgu.dcr.az.mui.TestDummyController";
    }

    @Override
    public Class controllerClass() {
        return bgu.dcr.az.mui.TestDummyController.class;
    }
    
}