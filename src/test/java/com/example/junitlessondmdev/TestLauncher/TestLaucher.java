package com.example.junitlessondmdev.TestLauncher;


import org.junit.platform.engine.DiscoverySelector;
import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;

import java.io.PrintWriter;

public class TestLaucher {
    public static void main(String[] args) {
         var launcher =LauncherFactory.create();
//         launcher.registerLauncherDiscoveryListeners();
//        launcher.registerTestExecutionListeners();

        var summary = new SummaryGeneratingListener();
        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder
                .request()
                .selectors(DiscoverySelectors.selectPackage("com.example.junitlessondmdev.user"))
//                .listeners()
                .build();
                launcher.execute(request,summary);//получим boolen значение
        //чтобы оплучить информацию больше -
        /* либо   launcher.registerLauncherDiscoveryListeners(); */
        try (var writer = new PrintWriter(System.out)){
            summary.getSummary().printTo(writer);
        }
    }
}
