package adwinddecryptor;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.security.interfaces.RSAPrivateKey;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
        
/**
 *
 * @author Michael Helwig
 */
public class AdWindDecryptor {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        
        Options options = new Options();
        
        Option rsakeyfile = new Option("r", "rsakeyfile", true, "serialized RSA KeyRep file path");
        rsakeyfile.setRequired(true);
        options.addOption(rsakeyfile);
        
        Option aeskeyfile = new Option("a", "aeskeyfile", true, "AES key file path");
        rsakeyfile.setRequired(true);
        options.addOption(aeskeyfile);

        Option input = new Option("i", "input", true, "input file path");
        input.setRequired(true);
        options.addOption(input);

        Option output = new Option("o", "output", true, "decrypted output file path");
        output.setRequired(true);
        options.addOption(output);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd;

        try {
            cmd = parser.parse(options, args);
            
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("java -jar AdWindDecryptor", options);
            System.exit(1);
            return;
        }
        
        String aesKeyFilePath = cmd.getOptionValue("aeskeyfile");
        String rsaKeyFilePath = cmd.getOptionValue("rsakeyfile");
        String inputFilePath = cmd.getOptionValue("input");
        String outputFilePath = cmd.getOptionValue("output");
        
        decrypt(rsaKeyFilePath,aesKeyFilePath,inputFilePath,outputFilePath);
        
    }
    
    public static void decrypt(String rsaKeyFile, String aesKeyFile, String encryptedFile, String outputFile) {
        try {
            
            RSAPrivateKey rsaKeyRep = (RSAPrivateKey) loadObject(rsaKeyFile);
            byte[] aesKey = loadFile(aesKeyFile);
            byte[] encrypted = loadFile(encryptedFile);
            
            if(rsaKeyRep == null || aesKey == null  || encryptedFile == null) {
                System.err.println("Could not read key files or encrypted file. Please check file paths.");
                System.exit(1);
            }
            
            Cipher aesCipher = javax.crypto.Cipher.getInstance("AES");
            Cipher rsaCipher = javax.crypto.Cipher.getInstance("RSA");
            rsaCipher.init(2,  rsaKeyRep);
            
            byte[] decryptedAESKey = rsaCipher.doFinal(aesKey);
            
            SecretKeySpec aesKeySpec = new SecretKeySpec(decryptedAESKey, "AES");
            aesCipher.init(2, aesKeySpec);
            
            byte[] decryptedContent = aesCipher.doFinal(encrypted);
            
            FileUtils.writeByteArrayToFile(new File(outputFile), decryptedContent);
            
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        
    }
    
    public static byte[] loadFile(String path) {
        try {
             InputStream fis = new FileInputStream(path);
             byte[] content = IOUtils.toByteArray(fis);
             return content;
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static Object loadObject(String path) {
        try {
             InputStream fis = new FileInputStream(path);
             ObjectInputStream ois = new ObjectInputStream(fis);
             Object object = ois.readObject();
             return object;
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
}
