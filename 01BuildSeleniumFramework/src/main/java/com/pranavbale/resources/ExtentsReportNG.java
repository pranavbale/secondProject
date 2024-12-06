package com.pranavbale.resources;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentsReportNG {

    public ExtentReports getReportNG() {
        String path = "./report/index.html";
        ExtentSparkReporter reporter = new ExtentSparkReporter(path);
        reporter.config().setReportName("General Report");

        ExtentReports reports = new ExtentReports();
        reports.attachReporter(reporter);
        reports.setSystemInfo("Tester", "Pranav Bale");

        return reports;
    }
}
