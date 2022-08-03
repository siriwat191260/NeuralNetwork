import java.io.*;
import java.util.*;

public class main {
    public static void main(String[] args) {
        ArrayList<Double[]> train_dataset = new ArrayList<>();
        ArrayList<Double[]> train_dataset_desired = new ArrayList<>();
        ArrayList<Double[]> test_dataset = new ArrayList<>();
        ArrayList<Double[]> test_dataset_desired = new ArrayList<>();

        try {
            File flood_data = new File("src/Flood_dataset.txt");
            Scanner myReader = new Scanner(flood_data);

            int j = 0;
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] data_splited = data.split("\t");
                int i = 0;



                if (j%10 == 0) {
                    Double[] data_input = new Double[8];
                    Double[] data_desired = new Double[1];

                    for (String d : data_splited) {
                        if (i < 8) {
                            data_input[i] = Double.parseDouble(d)/700;
                            i++;
                        } else {
                            data_desired[0] = Double.parseDouble(d)/700;
                            test_dataset.add(data_input);
                            test_dataset_desired.add(data_desired);
                        }
                    }
                }else{
                    Double[] data_input = new Double[8];
                    Double[] data_desired = new Double[1];

                    for (String d : data_splited) {
                        if (i < 8) {
                            data_input[i] = Double.parseDouble(d)/700;
                            i++;
                        } else {
                            data_desired[0] = Double.parseDouble(d)/700;
                            train_dataset.add(data_input);
                            train_dataset_desired.add(data_desired);
                        }
                    }
                }
                j++;
                }

            myReader.close();

            NeuronNetwork n = new NeuronNetwork(train_dataset,train_dataset_desired);
            n.train();

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
