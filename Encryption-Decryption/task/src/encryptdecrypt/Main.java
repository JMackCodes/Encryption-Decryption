package encryptdecrypt;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int key = 0;
        String operation = "enc";
        String toEncrypt = "";
        boolean hasData = false;
        boolean fileOut = false;
        File output = null;
        String algorithm ="shift";

        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-key")) {
                key = Integer.parseInt(args[i + 1]);
            }
            if (args[i].equals("-data")) {
                toEncrypt = args[i + 1];
                hasData = true;
            }
            if (args[i].equals("-mode")) {
                operation = args[i + 1];
            }
            if (args[i].equals("-in") && !hasData) {

                try {
                    Scanner in = new Scanner(new File(args[i + 1]));
                    while (in.hasNext()) {
                        toEncrypt = in.nextLine();
                    }
                    in.close();
                } catch (FileNotFoundException exception) {
                    System.out.println(exception.getMessage());
                }
            }
            if (args[i].equals("-out")) {
                fileOut = true;
                output = new File(args[i + 1]);
            }
            if (args[i].equals("-alg")) {
                algorithm = args[i + 1];
            }
        }

        if (operation.equals("enc")) {
            Encryptor encryptor = new Encryptor(toEncrypt, key, algorithm);
            if (!fileOut) {
                System.out.println(encryptor.getString());
            } else {
                try {
                    FileWriter writer = new FileWriter(output);
                    writer.write(encryptor.getString());
                    writer.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } else if (operation.equals("dec")) {
            Dencryptor dencryptor = new Dencryptor(toEncrypt, key, algorithm);
            if (!fileOut) {
                System.out.println(dencryptor.getString());
            } else {
                try {
                    FileWriter writer = new FileWriter(output);
                    writer.write(dencryptor.getString());
                    writer.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private static class Encryptor {
        private String string;
        //may need to make sure char 32 isn't modified since it is Space

        public Encryptor(String input, int key, String algorithm) {
            StringBuilder workingString = new StringBuilder(input);
            for (int i = 0; i < workingString.length(); i++) {
                    char temp = workingString.charAt(i);
                if (algorithm.equals("shift")) {
                    if (temp == 32) {
                        continue;
                    }
                    if (temp < 91) {
                        temp += key;
                        if (temp > 90) {
                            temp -= 26;
                        }
                    }
                    temp += key;
                    if (temp > 122) {
                        temp -= 26;
                    }
                } else {
                    temp += key;
                }
                workingString.setCharAt(i, temp);
                string = workingString.toString();
            }
        }
        public String getString() {
            return string;
        }
    }
    private static class Dencryptor {
        private String string;

        public Dencryptor(String input, int key, String algorithm) {
            StringBuilder workingString = new StringBuilder(input);
            for (int i = 0; i < workingString.length(); i++) {
                    char temp = workingString.charAt(i);
                if (algorithm.equals("shift")) {
                    if (temp == 32) {
                        continue;
                    }
                    if (temp < 91) {
                        temp -= key;
                        if (temp < 65) {
                            temp += 26;
                        }
                    }
                    temp -= key;
                    if (temp < 97) {
                        temp += 26;
                    }

                } else {
                    temp -= key;
                }
                workingString.setCharAt(i, temp);
                string = workingString.toString();
            }
        }
        public String getString() {
            return string;
        }
    }
}
