/**
 *
 * @author Christian Jeremy M. Lapuz
 */
import java.io.*;
import java.util.*;

public class Lapuz1_final {
    
    /**
     * Constants to be used
     */
    final static String IS_INITIALIZER = "int";
    final static String[] CONDITIONS = {" < ", " > ", "==", "<=", ">=", "!="};
    final static String[] OPERATORS =  {"++", "--", "+=", "-=", "*=", "/="};         
    final static String[] DIGITS = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0"};
    final static char[] PROCS = {'+', '-', '*', '/', '='};
    final static int PROC_LEN = 5;
    final static int INDEX = 8;
    final static int CHAR_TO_INT = 48;
    
    /**
     * Alterable values to determine Time Complexity
     */
    private static int root = 0;
    private static int value = 0;
    private static int loopOperations = 2;
    private static int extraProcs = 2;
    private static char variable = '\0';
    private static String teeOfEn = "2";
    private static String teeOfEnCase1 = "";
    private static String teeOfEnCase2 = "";
    private static String condCase = "";
    private static String oprtCase = "";
    private static String upperLimit = "";
    private static String denomOrBase = "";
    private static boolean intIsConst = false;
    private static boolean condIsConst = false;
    private static boolean isPartOfLoop = false;
    private static boolean limitless = false;
    private static boolean isInc = true;
    private static boolean inc = true;

    // Resets all Alterable values to their Default State
    public static void resetAll(){
        root = 0;
        value = 0;
        loopOperations = 2;
        extraProcs = 2;
        variable = '\0';
        teeOfEn = "2";
        teeOfEnCase1 = "";
        teeOfEnCase2 = "";
        condCase = "";
        oprtCase = "";
        upperLimit = "";
        denomOrBase = "";
        intIsConst = false;
        condIsConst = false;
        isPartOfLoop = false;
        isInc = true;
        limitless = false;
        inc = true;
    }
    
    // Determines the special cases, if any, for the loop's T(n)
    public static void setCase1() {
        switch(oprtCase){
            case "--":
                isInc = false;
                break;
            case "+=":
                teeOfEnCase1 = "/";
                break;
            case "-=":
                isInc = false;
                teeOfEnCase1 = "/";
                break;
            case "*=":
                teeOfEnCase1 = String.format(" log(%s) ", denomOrBase);
                break;
            case "/=":
                isInc = false;
                teeOfEnCase1 = String.format(" log(%s) ", denomOrBase);
                break;
            default:
                //do nothing, maybe...
        }
    }
    
    // Determines the other special cases, if any, for the loop's T(n)
    public static void setCase2() {
        switch(root){
            case 1:
                teeOfEnCase2 = " sqrt ";
                break;
            case 2:
                teeOfEnCase1 = " cubert " ;
                break;
            //May add more cases    
            default:
                //do nothing, maybe...
        }
    }
    // Sets the default value if the loop is not performed
    public static void setDefault(){
        loopOperations = extraProcs;
    }
    
    // Sets the vakue for the T(n) of a loop wiht constants
    public static void setConstTOfN(int tConst){
        if (tConst == -1){
            teeOfEn = "infinite";
        } else{
            teeOfEn = Integer.toString(tConst);
        } 
    }
    
     // Sets the vakue for the T(n) of a loop wiht at least one variable
    public static void setVariableTOfN(int tConst){
        if (limitless){
            teeOfEn = "infinite";
        } else{
            char operand = setOperand(extraProcs);
            extraProcs = Math.abs(extraProcs);
            if (teeOfEnCase1.equals("/")){
                teeOfEn = String.format("%d%s%s%s%s %c %d", loopOperations, teeOfEnCase2, upperLimit, teeOfEnCase1, denomOrBase, operand, extraProcs);
            }else {
                teeOfEn = String.format("%d%s%s%s %c %d", loopOperations, teeOfEnCase1, teeOfEnCase2, upperLimit, operand, extraProcs);
            }
        } 
    }
    // Sets the operator for the T(n) of a variable infused loop
    public static char setOperand(int val){
        char res = '+';
        if (val < 0){
            return PROCS[1];
        }
        return res;
    }
    
    // Computes the T(n) of a loop with an initializer equal 1
    public static int baseOneConstSummation(int limit, boolean incVal){
        int res = loopOperations;
        if (( !(isInc) && incVal && !condIsConst)
           || ((isInc) &&!incVal && !condIsConst)){
            limitless = true;
        }else {
            res *= limit; 
            res += extraProcs + root;
       }    
       return res; 
    }

    // Function called to start computing the T(n) of a loop not containing variables
    public static void constantTeeOfEn(){
        int limit = Integer.parseInt(upperLimit);
        switch (condCase){
           case " < ":
               if (value < limit){
                   if (value > 1){
                       loopOperations = baseOneConstSummation(limit - value, inc) ;
                   } else {
                       loopOperations = baseOneConstSummation(limit - 1, inc);
                   }
               } else {
                   setDefault();
               }
               break;
           case "<=":
               if (value <= limit){
                   if (value > 1){
                       loopOperations = baseOneConstSummation(limit - value + 1, inc);
                   } else {
                       loopOperations = baseOneConstSummation(limit, inc);
                   }
               } else {
                   setDefault();
               }
               break;
           case " > ":
               if (limit < value) {
                   if (limit > 1){
                       loopOperations = baseOneConstSummation(value - limit, !inc);
                   } else {
                       loopOperations = baseOneConstSummation(value - 1, !inc);
                   }
               } else {
                   setDefault();
               }
               break;
           case ">=":
               if (limit <= value) {
                   if (limit > 1){
                       loopOperations = baseOneConstSummation(value - limit + 1, !inc);
                   } else {
                       loopOperations = baseOneConstSummation(value, !inc);
                   }
               } else {
                   setDefault();
               }
               break;
           default:
                //do nothing, maybe...               
        }
        setConstTOfN(loopOperations);
    }
    
    
    public static void variableTeeOfEn(){
        upperLimit = upperLimit.trim();
        if (intIsConst && !condIsConst){
            switch(condCase){
                case " < ":
                    if (value >= 1){
                        extraProcs -= baseOneConstSummation(value, inc) - 2;
                        extraProcs += root + root;
                    } else {
                    setDefault();
                    }
                    break;
                case "<=":
                    if (value >= 1){
                        extraProcs -= baseOneConstSummation(value - 1, inc) - 2;
                        extraProcs += root + root;
                    } else {
                    setDefault();
                    }                  
                    break;
                case " > ":
                    if (value < 1){
                        extraProcs -= baseOneConstSummation(value, !inc) - 2;
                        extraProcs += root + root;
                    } else {
                    setDefault();
                    }
                    break;
                case ">=":
                    if (value >= 1){
                        extraProcs -= baseOneConstSummation(value - 1, !inc) - 2;
                        extraProcs += root + root;
                    } else {
                    setDefault();
                    }
                    break;
                default:
                     //do nothing, maybe...
            }
            setVariableTOfN(loopOperations);
       } else if (!intIsConst) {
           setDefault();        
       }
    }
    
    
    public static void main(String[] args) {
            
        try {
            /**
             * Reading a text file using Buffered Reader
             */
	    File file = new File("C:\\Users\\Jeremy\\Desktop\\sample-test-cases.txt");
	    FileReader fileReader = new FileReader(file);
            
	    BufferedReader buffReader = new BufferedReader(fileReader);
	    StringBuffer stringBuff = new StringBuffer();
            
	    String line;
            
            // Reading a file line by line until the text file has been exhausted
	    while ((line = buffReader.readLine()) != null){
                
                StringTokenizer tokenS = new StringTokenizer(line, "(;)");
                

		while (tokenS.hasMoreElements()) {

                    String token = tokenS.nextToken();
                    String trim = token.trim(); 
                    
                    // Checks the Initializer's Value
                    if (trim.contains(IS_INITIALIZER) && !(isPartOfLoop)){
                        char i = trim.charAt(INDEX);
                        if (i >= '0' && i <= '9'){
                            intIsConst = true;
                            value = (int)trim.charAt(INDEX) - CHAR_TO_INT;
                       } else {
                            variable = i;
                        }
                    }
                    
                    // Checks the Conditional Statement 
                    for(String cond: CONDITIONS){
                        if (trim.contains(cond) && !(isPartOfLoop)){
                            condCase = cond;
                            for (String digit: DIGITS){
                                if (trim.contains(digit)){
                                    condIsConst = true;
                                }break;
                            }
                            
                            for (int i = 0; i < PROC_LEN - 1; i++){
                            char proc = PROCS[i];
                            for (int j = 0; j < trim.length() - 4; j++){
                                if (trim.charAt(j) == proc)
                               {
                                   loopOperations++;
                                   root++;
                               }
                           }
                           }
                           setCase2();
                           upperLimit = trim.substring(trim.length() - 2, trim.length());
                        } 
                    }
                    
                    // Check the Alterations Done on the Initializer/s
                    for(String oprt: OPERATORS){
                        if (trim.contains(oprt) && !(isPartOfLoop)){
                            oprtCase = oprt;
                            denomOrBase = trim.substring(trim.length() - 2, trim.length());
                            denomOrBase = denomOrBase.trim();
                            setCase1();
                        }   
                    }
                    
                    // States that the loop has begun
                    if (trim.contains("{")){
                        isPartOfLoop = true;        
                    } 
                    
                    // Determines the Number of Operations Done Inside the Loop
                    if (isPartOfLoop) {
                        
                        for (int i = 0; i < PROC_LEN; i++){
                            char proc = PROCS[i];
                           for (int j = 0; j < trim.length() - 1; j++){
                               if (trim.charAt(j) == proc && trim.charAt(j + 1) == proc
                                || trim.charAt(j) == proc && trim.charAt(j + 1) == ' ')
                               {
                                   loopOperations++;
                               }
                           }
                        }
                    }
                    
                     
                    // States that the loop has been assesed 
                    if(trim.contains("}")){
                        if (intIsConst && condIsConst){
                            constantTeeOfEn();
                        } else{
                            variableTeeOfEn();
                        }
                        System.out.println("T(n) = " +teeOfEn);
                        resetAll();
                    }                   
		}		
            }
	    fileReader.close();
	    } catch (IOException e) {
		e.printStackTrace();
	}
    }
}
