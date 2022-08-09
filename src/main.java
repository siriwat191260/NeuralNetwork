import java.io.*;
import java.util.*;

public class main {
    public static void main(String[] args) {
        //problem 1
        ArrayList<Double[]> train_dataset = new ArrayList<>();
        ArrayList<Double[]> train_dataset_desired = new ArrayList<>();
        ArrayList<Double[]> test_dataset = new ArrayList<>();
        ArrayList<Double[]> test_dataset_desired = new ArrayList<>();
        //problem 2
        ArrayList<List<Double>> train_dataset_cross = new ArrayList<>();
        ArrayList<List<Double>> train_dataset_desire_cross = new ArrayList<>();

        ArrayList<List<Double>>[] test_dataset_cross = new ArrayList[10];
        ArrayList<List<Double>>[] test_dataset_desire_cross = new ArrayList[10];




        String cond = "";

        cond = "cross";

        // flood data
        if(cond == "flood"){
        try {
            File flood_data = new File("src/Flood_dataset.txt");
            Scanner myReader_flood = new Scanner(flood_data);

            int j = 0;
            while (myReader_flood.hasNextLine()) {
                String data = myReader_flood.nextLine();
                String[] data_splited = data.split("\t");
                int i = 0;

                if (j%10 == 0) {    //Test
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
                }else{              //Train
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
            myReader_flood.close();
            NeuronNetwork n = new NeuronNetwork(train_dataset,train_dataset_desired,test_dataset,test_dataset_desired);
            n.train();
            n.test();

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        }


        // cross data
        if(cond == "cross"){
            try{
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
                            if(line % 3 == 0){
                                if(line%10 == 0){
                                    test_dataset_desire_cross[0].add(temp);
                                }if(line%10 == 1){
                                    test_dataset_desire_cross[1].add(temp);
                                }if(line%10 == 2){
                                    test_dataset_desire_cross[2].add(temp);
                                }if(line%10 == 3){
                                    test_dataset_desire_cross[3].add(temp);
                                }if(line%10 == 4){
                                    test_dataset_desire_cross[4].add(temp);
                                }if(line%10 == 5){
                                    test_dataset_desire_cross[5].add(temp);
                                }if(line%10 == 6){
                                    test_dataset_desire_cross[6].add(temp);
                                }if(line%10 == 7){
                                    test_dataset_desire_cross[7].add(temp);
                                }if(line%10 == 8){
                                    test_dataset_desire_cross[8].add(temp);
                                }if(line%10 == 9){
                                    test_dataset_desire_cross[9].add(temp);
                                }else {
                                    train_dataset_desire_cross.add(temp);
                                }
                            }else if((line+1)%3==0){
                                if((line+1)%10 == 0){
                                    test_dataset_cross[0].add(temp);
                                }if((line+1)%10 == 1){
                                    test_dataset_cross[1].add(temp);
                                }if((line+1)%10 == 2){
                                    test_dataset_cross[2].add(temp);
                                }if((line+1)%10 == 3){
                                    test_dataset_cross[3].add(temp);
                                }if((line+1)%10 == 4){
                                    test_dataset_cross[4].add(temp);
                                }if((line+1)%10 == 5){
                                    test_dataset_cross[5].add(temp);
                                }if((line+1)%10 == 6){
                                    test_dataset_cross[6].add(temp);
                                }if((line+1)%10 == 7){
                                    test_dataset_cross[7].add(temp);
                                }if((line+1)%10 == 8){
                                    test_dataset_cross[8].add(temp);
                                }if((line+1)%10 == 9){
                                    test_dataset_cross[9].add(temp);
                                }
                                else{
                                    train_dataset_cross.add(temp);
                                }
                            }
                    }
                    line++;
                }
                    myReader_cross.close();

                    for(int i = 0;i<10;i++){
                        NeuronNetwork1 n = new NeuronNetwork1(train_dataset_cross,train_dataset_desire_cross,test_dataset_cross[i],test_dataset_desire_cross[i]);
                        n.train();
                        n.test();
                    }



            }catch (FileNotFoundException e){
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }



    }
}