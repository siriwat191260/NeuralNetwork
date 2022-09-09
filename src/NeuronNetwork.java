import java.util.ArrayList;

public class NeuronNetwork {
    ArrayList<Double[]> train_dataset;
    ArrayList<Double[]> train_dataset_desired;
    ArrayList<Double[]> test_dataset;
    ArrayList<Double[]> test_dataset_desired;
    int Neural_Layer[] = {8,16,16,16,1};
    Double Node[][] = new Double[Neural_Layer.length][];
    Double Desire_Node[] = new Double[Neural_Layer[Neural_Layer.length-1]];
    Double Local_Gradient_Node[][] = new Double[Neural_Layer.length][];
    Double Error_Avg = 1000.0;
    Double Min_Error = 0.00001;
    Matrix[] Weight_Layer = new Matrix[Neural_Layer.length - 1];
    Matrix[] Changed_Weight = new Matrix[Neural_Layer.length - 1];
    Double Max_Epoch = 1000.0;
    Double biases = 0.1;
    Double learning_rate = 0.01; //0.01
    Double momentum_rate = 0.01;
    Double error_output[] = new Double[Neural_Layer[Neural_Layer.length-1]];

    public NeuronNetwork(ArrayList a, ArrayList b, ArrayList c, ArrayList d) {
        train_dataset = a;
        train_dataset_desired = b;
        test_dataset = c;
        test_dataset_desired = d;
        //node and local gradient in each layer
        for (int i = 0; i < Neural_Layer.length; i++) {
            Node[i] = new Double[Neural_Layer[i]];
            Local_Gradient_Node[i] = new Double[Neural_Layer[i]];
        }
        //weight and change weight in each layer
        for (int layer = 0; layer < Neural_Layer.length - 1; layer++) {
            Weight_Layer[layer] = new Matrix(Neural_Layer[layer + 1], Neural_Layer[layer], true);
            Changed_Weight[layer] = new Matrix(Neural_Layer[layer + 1], Neural_Layer[layer], false);
        }

    }


    public void train() {
        int n = 0;
        double sse ;
        double avg_error = 1000.0;
        double e = 0.0;
        System.out.println("=======================================================================================");
        System.out.println("                                        Train                                        ");
        System.out.println("=======================================================================================");
        while (n < Max_Epoch && Error_Avg > Min_Error) {
            double sum_error = 0.0;
            for (int data = 0; data < train_dataset.size(); data++) {
                int rand_dataset = (int) (Math.random() * train_dataset.size()-1); //random index data
                for (int data_input = 0; data_input < Neural_Layer[0]; data_input++) { //add data to input Node
                    Node[0][data_input] = train_dataset.get(rand_dataset)[data_input];
                }
                for (int data_input = 0;data_input < Neural_Layer[Neural_Layer.length-1];data_input++){
                    Desire_Node[data_input] = train_dataset_desired.get(rand_dataset)[data_input]; // add data to desire Node
                }
                forward_pass();
                get_error();
                backward_pass();
                weight_new();

                sum_error += Math.pow(error_output[0],2.0); // sum of error^2

            }
            sse = sum_error/2.0;
            avg_error = sse / train_dataset.size(); //average error
            if(n == Max_Epoch-1) {
                System.out.println("=======================================================================================");
                System.out.println("Average final: "+ avg_error);
                System.out.println("=======================================================================================");
            }
            n++;//next epoch
        }

    }

    public void test(){
        double sse ;
        double avg_error = 0.0;
        double sum_error = 0.0;
        System.out.println("                                        Test                                        ");
        System.out.println("=======================================================================================");
        for (int data = 0; data < test_dataset.size(); data++) {

            for (int data_input = 0; data_input < Neural_Layer[0]; data_input++) {
                Node[0][data_input] = test_dataset. get(data)[data_input];
            }

            for (int data_input = 0;data_input < Neural_Layer[Neural_Layer.length-1];data_input++){
                Desire_Node[data_input] = test_dataset_desired.get(data)[data_input];
            }
            forward_pass();
            get_error();

            double d = test_dataset_desired.get(data)[0] * 700.0;
            double g = Node[Neural_Layer.length - 1][0] * 700.0;
            for(int i=0;i<Neural_Layer[Neural_Layer.length-1];i++) {
                sum_error += Math.pow(error_output[i], 2.0);
            }
//            System.out.println("Desire : "+ d + " || Output : " + g + " || Error : " + (d - g) +" || E_Output :" + error_output[0]*700);
        }
        sse = sum_error/2.0; //sum of squared error
        avg_error = sse / test_dataset.size(); //average error
        System.out.println("=======================================================================================");
        System.out.println("Average : "+ avg_error);
        System.out.println("=======================================================================================");
    }

    public void forward_pass() {
        for (int layer = 0; layer < Neural_Layer.length - 1; layer++) {  //loop for each layer

            for (int j = 0; j < Neural_Layer[layer + 1]; j++) {
                Double result = 0.0;
                for (int k = 0; k < Node[layer].length; k++) {  //loop for calculate Vk
                    result += Weight_Layer[layer].data[j][k] * activation_fn(Node[layer][k]);
                }
                Node[layer + 1][j] = result+biases;
            }

        }
    }

    public void get_error() {
        for(int i=0;i<Neural_Layer[Neural_Layer.length-1];i++){
            error_output[i] = Desire_Node[i]- Node[Neural_Layer.length-1][i];
            Local_Gradient_Node[Neural_Layer.length - 1][i] = error_output[i];
        }

    }

    public void backward_pass() {
        //output
        double change_weight_sum;
        for (int output = 0; output < Neural_Layer[Neural_Layer.length - 1]; output++) {//output layer
            for (int c = 0; c < Neural_Layer[Neural_Layer.length -2]; c++) {//hidden layer
                double fn_diff = 1;
                double fn = activation_fn(Node[Neural_Layer.length - 2][c]);
                change_weight_sum = learning_rate * (error_output[output] * fn_diff * fn ); // change weight at output
                Changed_Weight[Weight_Layer.length - 1].data[output][c] = change_weight_sum + (Changed_Weight[Weight_Layer.length - 1].data[output][c] * momentum_rate) ; // add change weight
            }
        }

        //hidden
        find_local_gradient();
        find_delta_weight();

    }

    public void find_local_gradient(){

        for(int layer=Neural_Layer.length-2;layer>0;layer--){
            for(int j=0;j<Neural_Layer[layer];j++){
                Double result_sum = 0.0;
                for(int k=0;k<Neural_Layer[layer+1];k++){
                    result_sum += Local_Gradient_Node[layer+1][k]*Weight_Layer[layer].data[k][j];
                }
                Local_Gradient_Node[layer][j] = activation_fn_diff(Node[layer][j])*result_sum;

            }
        }
    }

    //find delta weight at hidden
    public void find_delta_weight(){
        Double change_weight_sum;

        for(int layer=Neural_Layer.length-3;layer>0;layer--){
            for(int j=0;j<Neural_Layer[layer+1];j++){;
                for(int i=0;i<Neural_Layer[layer];i++){
                    change_weight_sum = learning_rate * Local_Gradient_Node[layer+1][j] * activation_fn(Node[layer][i]);
                    Changed_Weight[layer].data[j][i] = change_weight_sum + (Changed_Weight[layer].data[j][i] * momentum_rate) ;
                }

            }
        }
    }

    //calculate new weight
    public void weight_new() {
        for (int l = 0; l < Weight_Layer.length - 1; l++) {
            for (int a = 0; a < Neural_Layer[l+1]; a++) {
                for (int b = 0; b < Neural_Layer[l]; b++) {
                    Weight_Layer[l].data[a][b] = Weight_Layer[l].data[a][b] + Changed_Weight[l].data[a][b];
                }
            }
        }
    }

    //sigmoid function
    public Double activation_fn(Double a){
        Double result_fn = 1/(1+Math.exp(-a));
        return  Math.max(0.01,a);
    }

    //diff sigmoid function
    public Double activation_fn_diff(Double a) {
        Double result_fn_diff = activation_fn(a)*(1-activation_fn(a));
        if(a<=0){
            return 0.01;
        }else {
            return 1.0;
        }
    }

}