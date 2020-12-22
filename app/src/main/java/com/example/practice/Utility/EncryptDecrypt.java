package com.example.practice.Utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.FileSystemException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class EncryptDecrypt {

    private SecretKeySpec secretKey;
    private Cipher cipher;
    private static Cipher encryptCipher;
    private static Cipher decryptCipher;

    public EncryptDecrypt(String secret, int length, String algorithm) {
        byte[] key = new byte[length];
        try {
            key = fixSecret(secret, length);
            this.cipher = Cipher.getInstance(algorithm);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        this.secretKey = new SecretKeySpec(key, algorithm);
    }
    private byte[] fixSecret(String s, int length) throws UnsupportedEncodingException {
        if (s.length() < length) {
            int missingLength = length - s.length();
            for (int i = 0; i < missingLength; i++) {
                s += " ";
            }
        }
        return s.substring(0, length).getBytes("UTF-8");
    }
    public void encryptFile(File f) {
        System.out.println("Encrypting file: " + f.getName());
        try {
            this.cipher.init(Cipher.ENCRYPT_MODE, this.secretKey);
            this.writeToFile(f);
            boolean isRenamed=f.renameTo(new File(f.getPath() + ".AES"));
            if(!isRenamed)
                throw new FileSystemException("Rename error");
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }

    }

    public void decryptFile(File f) {
        System.out.println("Decrypting file: " + f.getName());

        try {
            this.cipher.init(Cipher.DECRYPT_MODE, this.secretKey);
            this.writeToFile(f);
            boolean isRenamed=f.renameTo(new File(f.getPath().replace(".AES","")));
            if(!isRenamed)
                throw new FileSystemException("Rename error");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
    }
    public void writeToFile(File f) throws IOException, IllegalBlockSizeException, BadPaddingException {
        FileInputStream in = new FileInputStream(f);
        byte[] input = new byte[(int) f.length()];
        in.read(input);

        FileOutputStream out = new FileOutputStream(f);
        byte[] output = this.cipher.doFinal(input);
        out.write(output);
        out.flush();
        out.close();
        in.close();
    }
}
