import rpc.RPC;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Sources: https://www.mkyong.com
 */
public class Main {
    private final static String CSVFILE = System.getProperty("user.home")+ File.separator+
            "Desktop/values.csv";
    public static void main(String[] args) {
        List<Holder> holders = new ArrayList<>();
        int count = -1;
        String line = "";
        String cvsSplitBy = ",";
        try (BufferedReader br = new BufferedReader(new FileReader(CSVFILE))) {

            while ((line = br.readLine()) != null) {
                ++count;
                if(count ==0){
                    continue;
                }
                line = line.replace("\"", "");
                String[] csv= line.split(cvsSplitBy);
                holders.add(new Holder(csv[0], Double.parseDouble(csv[2]), Double.parseDouble(csv[1])));

            }
            //unlock account
            RPC.unlock_rpc_call();
            for (Holder h:holders) {
                RPC.tranfer_rpc_call(h.getName(), Double.toString(h.getNew_balance()));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
