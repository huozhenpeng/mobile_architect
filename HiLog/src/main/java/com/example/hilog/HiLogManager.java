package com.example.hilog;

import androidx.annotation.NonNull;

import com.example.hilog.printer.HiLogPrinter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Date: 2021/2/1
 * Author: huozhenpeng
 */
public class HiLogManager {
    private HiLogConfig config;
    private static HiLogManager instance;
    private List<HiLogPrinter> printers = new ArrayList<>();
    private HiLogManager(HiLogConfig config, HiLogPrinter[] printers) {
        this.config = config;
        this.printers.addAll(Arrays.asList(printers));
    }
    public static HiLogManager getInstance() {
        return instance;
    }
    public static void init(@NonNull HiLogConfig config, HiLogPrinter... printers) {
        instance= new HiLogManager(config, printers);
    }
    public HiLogConfig getConfig() {
        return config;
    }
    public void addPrinter(HiLogPrinter printer) {
        printers.add(printer);
    }
    public void removePrinter(HiLogPrinter printer) {
        printers.remove(printer);
    }
    public List<HiLogPrinter> getPrinters() {
        return printers;
    }
}
