import java.io.*;
import java.util.*;

public class main {
    public static void main(String[] args) {



            String cond = "";

            cond = "flood";

            // flood data
            if (cond == "flood") {

                //problem 1
                for (int round = 0; round < 10; round++) {
                    ArrayList<Double[]> train_dataset = new ArrayList<>();
                    ArrayList<Double[]> train_dataset_desired = new ArrayList<>();
                    ArrayList<Double[]> test_dataset = new ArrayList<>();
                    ArrayList<Double[]> test_dataset_desired = new ArrayList<>();

                    try {
                        File flood_data = new File("src/Flood_dataset.txt");
                        Scanner myReader_flood = new Scanner(flood_data);

                        int j = 0;
                        while (myReader_flood.hasNextLine()) {
                            String data = myReader_flood.nextLine();
                            String[] data_splited = data.split("\t");
                            int i = 0;
                            if (j % 10 == round) {    //Test
                                Double[] data_input = new Double[8];
                                Double[] data_desired = new Double[1];

                                for (String d : data_splited) {
                                    if (i < 8) {
                                        data_input[i] = Double.parseDouble(d) / 700;
                                        i++;
                                    } else {
                                        data_desired[0] = Double.parseDouble(d) / 700;
                                        test_dataset.add(data_input);
                                        test_dataset_desired.add(data_desired);
                                    }
                                }
                            } else {              //Train
                                Double[] data_input = new Double[8];
                                Double[] data_desired = new Double[1];

                                for (String d : data_splited) {
                                    if (i < 8) {
                                        data_input[i] = Double.parseDouble(d) / 700;
                                        i++;
                                    } else {
                                        data_desired[0] = Double.parseDouble(d) / 700;
                                        train_dataset.add(data_input);
                                        train_dataset_desired.add(data_desired);
                                    }
                                }
                            }
                            j++;

                        }
                        myReader_flood.close();
                    } catch (FileNotFoundException e) {
                        System.out.println("An error occurred.");
                        e.printStackTrace();
                    }
                    NeuronNetwork n = new NeuronNetwork(train_dataset, train_dataset_desired, test_dataset, test_dataset_desired);
                    n.train();
                    n.test();
                }


            }


            // cross data
            if (cond == "cross") {
                //problem 2
                for (int round = 0; round < 10; round++) {
                    ArrayList<List<Double>> train_dataset_cross = new ArrayList<>();
                    ArrayList<List<Double>> train_dataset_desire_cross = new ArrayList<>();

                    ArrayList<List<Double>> test_dataset_cross = new ArrayList<>();
                    ArrayList<List<Double>> test_dataset_desire_cross = new ArrayList<>();
                    try {
                        File cross_data = new File("src/cross.txt");
                        Scanner myReader_cross = new Scanner(cross_data);

                        int line = 1;
                        while (myReader_cross.hasNextLine()) {
                            String data = myReader_cross.nextLine();
                            if (line % 3 == 0 || (line + 1) % 3 == 0) {
                                String[] data_splited = data.split("\\s+");
                                List<Double> temp = new LinkedList<>();
                                for (String d : data_splited) {
                                    temp.add(Double.parseDouble(d));
                                }
                                if (line % 3 == 0) {
                                    for (int i = 0; i < 10; i++) {
                                        if (line % 10 == i) {
                                            test_dataset_desire_cross.add(temp);
                                        } else {
                                            train_dataset_desire_cross.add(temp);
                                        }
                                    }

                                } else if ((line + 1) % 3 == 0) {
                                    for (int i = 0; i < 10; i++) {
                                        if ((line + 1) % 10 == i) {
                                            test_dataset_cross.add(temp);
                                        } else {
                                            train_dataset_cross.add(temp);
                                        }
                                    }

                                }
                            }
                            line++;

                        }
                        myReader_cross.close();
                    } catch (FileNotFoundException e) {
                        System.out.println("An error occurred.");
                        e.printStackTrace();
                    }

                    NeuronNetwork1 n = new NeuronNetwork1(train_dataset_cross, train_dataset_desire_cross, test_dataset_cross, test_dataset_desire_cross);
                    n.train();
                    n.test();

                }
            }


        }
    }
