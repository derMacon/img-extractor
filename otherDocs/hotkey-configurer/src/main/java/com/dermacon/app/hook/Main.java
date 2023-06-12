package com.dermacon.app.hook;

public class Main {
    public static void main(String[] args) {
        System.out.println("starting");
        Thread runner = new Thread(new HookRunner());
        runner.start();
    }
}
