/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bgu.dcr.az.conf.modules.info;

import bgu.dcr.az.conf.modules.ModuleContainer;

/**
 *
 * @author bennyl
 */
public class ModuleContainerParentChangedInfo {

    ModuleContainer moduleContainer;

    public ModuleContainerParentChangedInfo(ModuleContainer moduleContainer) {
        this.moduleContainer = moduleContainer;
    }

    public ModuleContainer getModuleContainer() {
        return moduleContainer;
    }

}
