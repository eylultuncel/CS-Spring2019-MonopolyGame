import org.json.simple.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.*;
import java.util.*;


public class Main {

    public static void main(String[] args) {
        monopoly monopolyPlay = new monopoly();
        JSONParser parser = new JSONParser();


        File file = new File("output.txt");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            PrintStream ps = new PrintStream(fos);
            System.setOut(ps);
        }
        catch (Exception e) {
            e.printStackTrace();
        }




        try {
            Object obj = parser.parse(new FileReader("property.json"));

            JSONObject jsonObject = (JSONObject) obj;
            //System.out.println(jsonObject);


            for(int i=1;i<4;i++){
                String j = Integer.toString(i);
                JSONArray jsonarrayproperties = (JSONArray) jsonObject.get(j);

                Iterator<Map.Entry> ijaproperties = jsonarrayproperties.iterator();

                while (ijaproperties.hasNext()==true){
                    Iterator landProperties = ((Map)ijaproperties.next()).entrySet().iterator();
                    monopolyPlay.addProperty(landProperties,i);

                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }



        try {
            Object obj = parser.parse(new FileReader("list.json"));

            JSONObject jsonObject = (JSONObject) obj;

            JSONArray jsonarraycards = (JSONArray) jsonObject.get("chanceList");

            Iterator<Map.Entry> ijacards = jsonarraycards.iterator();

            while (ijacards.hasNext()==true){
                Iterator chancecards = ((Map)ijacards.next()).entrySet().iterator();

                while(chancecards.hasNext()) {
                    monopolyPlay.addChanceCard(chancecards.next().toString());
                }


            }


            JSONArray jsonarraycards1 = (JSONArray) jsonObject.get("communityChestList");
            //System.out.println(jsonarraycards1);

            Iterator<Map.Entry> ijacards1 = jsonarraycards1.iterator();

            while (ijacards1.hasNext()==true){
                Iterator commmunitycard = ((Map)ijacards1.next()).entrySet().iterator();

                while(commmunitycard.hasNext()) {
                    monopolyPlay.addCommunityCard(commmunitycard.next().toString());
                }


            }




        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        monopolyPlay.squares();


        List<String> command =  new ArrayList<String>();
        Scanner scanner = null;
        try { scanner = new Scanner(new File(args[0]));
            while (scanner.hasNext()) {
                command.add(scanner.nextLine());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (scanner != null) scanner.close();
        }


        int sizeofcommands=command.size();
        for(int i=0;i<sizeofcommands;i++) {
            String str;
            str = command.get(i);
            if(monopolyPlay.command(str)==false){
                String str1= command.get(i);
                monopolyPlay.bankrupt(str1);
                break;
            }
        }
        monopolyPlay.show();











    }



}
