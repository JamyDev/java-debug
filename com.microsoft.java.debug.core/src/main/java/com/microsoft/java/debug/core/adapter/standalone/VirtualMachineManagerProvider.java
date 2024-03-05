package com.microsoft.java.debug.core.adapter.standalone;

import com.microsoft.java.debug.core.adapter.IVirtualMachineManagerProvider;
import com.sun.jdi.Bootstrap;
import com.sun.jdi.VirtualMachineManager;

public class VirtualMachineManagerProvider implements IVirtualMachineManagerProvider {
    private VirtualMachineManager manager;
    public VirtualMachineManagerProvider() {
        this.manager = Bootstrap.virtualMachineManager();
    }

    @Override
    public VirtualMachineManager getVirtualMachineManager() {
        return this.manager;
    }
}
