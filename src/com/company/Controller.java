package com.company;

import com.company.classes.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Timer;
import java.util.TimerTask;

public class Controller {
    @FXML
    private TableView<com.company.classes.Process> tableView;
    //@FXML
    //private TableColumn<com.company.classes.Process, Integer> processIdCol;
    @FXML
    private TableColumn<com.company.classes.Process, Integer> processPriorityCol;
    @FXML
    private TableColumn<com.company.classes.Process, String> processNameCol;
    @FXML
    private TableColumn<com.company.classes.Process, Integer> processTimeCol;
    @FXML
    private TableColumn<com.company.classes.Process, Integer> processMemoryCol;
    @FXML
    private TableColumn<com.company.classes.Process, Integer> processArrivalTimeCol;
    @FXML
    private TableColumn<com.company.classes.Process, Integer> processBurstTimeCol;
    @FXML
    private TableColumn<com.company.classes.Process, State> processStateCol;
    @FXML
    private TextField textFieldAddProcesses;
    @FXML
    private TextField textFieldKillProcess;
    @FXML
    private TableView<com.company.classes.MemoryBlock> memoryBlockTableView;
    @FXML
    private TableColumn<com.company.classes.MemoryBlock, Integer> availableMemoryCol;
    @FXML
    private TableView<com.company.classes.Core> cpuTableView;
    @FXML
    private TableColumn<com.company.classes.Core, Integer> availableCpuNumberCol;
    @FXML
    private TableColumn<com.company.classes.Core, Boolean> availableCpuStateCol;
    @FXML
    private TableView<com.company.classes.Resource> resourceTableView;
    @FXML
    private TableColumn<com.company.classes.Resource, Integer> availableResourceNumberCol;
    @FXML
    private TableColumn<com.company.classes.Resource, Boolean> availableResourceStateCol;

    private Main main;

    public Controller() {
    }

    Scheduler scheduler = new Scheduler(4, 4096);
    private ObservableList<com.company.classes.Process> processObservableList = FXCollections.observableArrayList();
    private ObservableList<com.company.classes.MemoryBlock> memoryObservableList = FXCollections.observableArrayList();
    private ObservableList<com.company.classes.Core> cpuObservableList = FXCollections.observableArrayList();
    private ObservableList<com.company.classes.Resource> resourceObservableList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        memoryBlockTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        resourceTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        cpuTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        processPriorityCol.setCellValueFactory(new PropertyValueFactory<>("priority"));
        processNameCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        processTimeCol.setCellValueFactory(new PropertyValueFactory<>("time"));
        processMemoryCol.setCellValueFactory(new PropertyValueFactory<>("memory"));
        processArrivalTimeCol.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));
        processBurstTimeCol.setCellValueFactory(new PropertyValueFactory<>("burstTime"));
        processStateCol.setCellValueFactory(new PropertyValueFactory<>("state"));
        availableMemoryCol.setCellValueFactory(new PropertyValueFactory<>("availableMemory"));

        availableCpuStateCol.setCellValueFactory(new PropertyValueFactory<>("state"));
        availableCpuNumberCol.setCellValueFactory(new PropertyValueFactory<>("number"));

        availableResourceNumberCol.setCellValueFactory(new PropertyValueFactory<>("number"));
        availableResourceStateCol.setCellValueFactory(new PropertyValueFactory<>("state"));

        scheduler.init();

        Thread schedulerThread = new Thread(() -> {
            scheduler.schedule();
        });
        schedulerThread.start();

        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            public void run() {
                // ОбновлениеТаблицы процессов
                tableView.getItems().clear();
                for (int i = 0; i < scheduler.getJobsQueue().getSize(); i++) {
                    processObservableList.add(scheduler.getJobsQueue().get(i));
                }
                tableView.setItems(processObservableList);

                //Обновление таблиццы CPU
                cpuTableView.getItems().clear();
                var cpu = Scheduler.getCpu();
                if(cpu != null) {
                    for (int i = 0; i < cpu.getCores().length; i++) {
                        cpuObservableList.add(cpu.getCore(i));
                    }
                }
                cpuTableView.setItems(cpuObservableList);

                //Обновление таблиццы ресурсов
                resourceTableView.getItems().clear();
                var device = Scheduler.getDevice();
                if(device != null) {
                    for (int i = 0; i < device.getResources().length; i++) {
                        resourceObservableList.add(device.getResource(i));
                    }
                }
                resourceTableView.setItems(resourceObservableList);

                //Обновление таблиццы состояния памяти
                memoryBlockTableView.getItems().clear();
                MemoryScheduler memoryScheduler = scheduler.getMemoryScheduler();
                for (int i = 0; i < memoryScheduler.getSize(); i++) {
                    memoryObservableList.add(memoryScheduler.get(i));
                }
                memoryBlockTableView.setItems(memoryObservableList);


            }
        };
        timer.schedule(timerTask, 1000, 1000);
        /*
        Thread uiThread = new Thread() {
            public void run() {
                //System.out.println("Scheduler thread Running");
                do {
                    //processObservableList.reremoveAll();
                    tableView.getItems().clear();

                    for (int i = 0; i < scheduler.getJobsQueue().getSize(); i++) {
                        processObservableList.add(scheduler.getJobsQueue().get(i));
                    }
                    tableView.setItems(processObservableList);

                    try {
                        this.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }while (true);
            }
        };
        uiThread.start();
        */
    }

    @FXML
    private void addNumberOfProcessesButton() {
        try {
            textFieldAddProcesses.focusedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                    if (t1) {
                        textFieldAddProcesses.setStyle("-fx-control-inner-background: #ffffff");
                    }
                }
            });
            Integer number = Integer.parseInt(textFieldAddProcesses.getText());
            if (number > 0 && number <= 5) {
                scheduler.addProcess(number);
            } else {
                textFieldAddProcesses.setStyle("-fx-control-inner-background: #ff3d3b");
            }
            /*
            for(int i = 0; i < scheduler.getJobsQueue().getSize(); i++){
                processObservableList.add(scheduler.getJobsQueue().get(i));
                tableView.setItems(processObservableList);
            }
            */
        } catch (NumberFormatException exception) {
            textFieldAddProcesses.clear();
        }
    }

    @FXML
    private void addNumberOfProcessesInRandomTimeButton() {
        int procNumber = Utils.getRandomInteger(1, 1);
        int delay = 0;
        for (int i = 0; i < procNumber; i++) {
            delay = Utils.getRandomInteger(10000, 20000);
            scheduler.addProcessRandom(delay);
        }
    }

    @FXML
    private void killProcess() {
        textFieldKillProcess.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (t1) {
                    textFieldKillProcess.setStyle("-fx-control-inner-background: #ffffff");
                }
            }
        });

        int killed = -1;
        try {
            killed = Integer.parseInt(textFieldKillProcess.getText());
        } catch (NumberFormatException e){

            return;
        }

        for (int i = 0; i < scheduler.getJobsQueue().getSize(); i++) {
            if (scheduler.getJobsQueue().get(i).getId() == killed) {
                scheduler.getJobsQueue().remove(i);
                textFieldKillProcess.setStyle("-fx-control-inner-background: #ffffff");
                textFieldKillProcess.clear();
            } else {
                textFieldKillProcess.setStyle("-fx-control-inner-background: #ff3d3b");
            }
        }
        for (int i = 0; i < scheduler.getReadyQueue().getSize(); i++) {
            if (scheduler.getReadyQueue().get(i).getId() == killed) {
                scheduler.getReadyQueue().remove(i);
                textFieldKillProcess.setStyle("-fx-control-inner-background: #ffffff");
                textFieldKillProcess.clear();
            } else {
                textFieldKillProcess.setStyle("-fx-control-inner-background: #ff3d3b");
            }
        }
        for (int i = 0; i < scheduler.getWaitingQueue().getSize(); i++) {
            var waitingProcess = scheduler.getWaitingQueue().get(i);
            if (waitingProcess.getId() == killed) {
                var resourceId = waitingProcess.getResourceId();
                Scheduler.getDevice().getResource(resourceId).setIdle(true);
                scheduler.getWaitingQueue().remove(i);
                textFieldKillProcess.setStyle("-fx-control-inner-background: #ffffff");
                textFieldKillProcess.clear();
            } else {
                textFieldKillProcess.setStyle("-fx-control-inner-background: #ff3d3b");
            }
        }
    }
}

